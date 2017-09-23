package com.intendia.rxgwt2.client;

import static java.lang.Math.min;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

import io.reactivex.CompletableTransformer;
import io.reactivex.Flowable;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RxGwt {
    public static long MAX_RETRY_TIME = MINUTES.toSeconds(1);

    public static <T> ObservableTransformer<T, T> logInfo(Logger log, Function<? super T, String> msg) {
        if (!log.isLoggable(Level.INFO)) return o -> o;
        else return o -> o.doOnNext(n -> log.info(msg.apply(n)));
    }

    public static CompletableTransformer retryDelay(Logger log, Level level, String msg) {
        return retryDelay(a -> log.log(level, msg + " (" + a.idx + " attempt)", a.err));
    }

    public static CompletableTransformer retryDelay(Consumer<Attempt> onAttempt) {
        return retryDelay(onAttempt, Integer.MAX_VALUE);
    }

    public static CompletableTransformer retryDelay(Consumer<Attempt> onAttempt, int maxRetry) {
        return o -> o.retryWhen(attempts -> attempts
                .zipWith(Flowable.range(1, maxRetry), (err, i) -> new Attempt(i, err))
                .flatMap((Attempt x) -> {
                    if (x.idx > maxRetry) return Flowable.error(x.err);
                    onAttempt.accept(x);
                    return Flowable.timer(min(x.idx * x.idx, MAX_RETRY_TIME), SECONDS);
                }));
    }

    public static class Attempt {
        public final int idx;
        public final Throwable err;
        public Attempt(int idx, Throwable err) {
            this.idx = idx;
            this.err = err;
        }
    }
}
