package playground.ttl.comparable_task_queue;

import com.alibaba.ttl.TtlUnwrap;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * https://github.com/alibaba/transmittable-thread-local/issues/290
 */
public class Main2 {
    public static void main(String[] args) {
        PriorityBlockingQueue<Runnable> workQueue = new PriorityBlockingQueue<>(42,
                Comparator.comparing(ttlRunnable -> (MyBizTask) TtlUnwrap.unwrap(ttlRunnable))
        );
        // NOTE: one Thread for queue tasks easily
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 42, TimeUnit.SECONDS, workQueue);

        // submit a long-running task to subsequent tasks
        executor.submit(new MyBizTask(0, 100));

        // queue tasks
        executor.submit(new MyBizTask(1, 0));
        executor.submit(new MyBizTask(1000, 0));
    }

    private static class MyBizTask implements Runnable, Comparable<MyBizTask> {
        private final int priority;
        private final long sleepMs;

        public MyBizTask(int priority, long sleepMs) {
            this.priority = priority;
            this.sleepMs = sleepMs;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(sleepMs);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Hello world! priority = " + priority + ", sleepMs = " + sleepMs);
        }

        @Override
        public int compareTo(@NotNull MyBizTask o) {
            return priority - o.priority;
        }
    }
}
