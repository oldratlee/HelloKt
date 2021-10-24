package playground.ttl.cme;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.alibaba.ttl.TtlRunnable;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ConcurrentModificationExceptionDemo {
    private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(
            16, 16, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(), Thread::new, new ThreadPoolExecutor.CallerRunsPolicy());

    private static final TransmittableThreadLocal<String> context = new TransmittableThreadLocal<>();
    private static final TransmittableThreadLocal<String> context2 = new TransmittableThreadLocal<>();

    public static void main(String[] args) {
        context.set("set-in-main");
        context2.set("set-in-main2");

        List<Future<?>> futures = IntStream.range(0, 10_000).mapToObj(num -> {
            TtlRunnable task = TtlRunnable.get(() -> {
                long ms = ThreadLocalRandom.current().nextLong(0, 7);
                sleep(ms);
                System.out.println("run in thread[" + Thread.currentThread().getName()
                        + "] with context[" + context.get() + "], num " + num + " sleep " + ms);

                // remove in summited runnable
                context.remove();
            });
            return EXECUTOR.submit(task);
        }).collect(Collectors.toList());

        futures.forEach(f -> {
            try {
                f.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        EXECUTOR.shutdown();
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
