package playground.ttl.comparable_task_queue;

import com.alibaba.ttl.TtlUnwrap;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Jerry Lee
 */
public class Main1 {
    public static void main(String[] args) {
        PriorityBlockingQueue<Runnable> workQueue = new PriorityBlockingQueue<>(42,
                Comparator.comparingInt(ttlRunnable -> {
                    Runnable unwrap = TtlUnwrap.unwrap(ttlRunnable);
                    if (unwrap instanceof MyBizTask) {
                        MyBizTask myBizTask = (MyBizTask) unwrap;
                        return myBizTask.priority;
                    } else return 1000;
                })
        );
        // NOTE: one Thread for queue tasks easily
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 42, TimeUnit.SECONDS, workQueue);

        // submit a long-running task to subsequent tasks
        executor.execute(new MyBizTask(100));

        // queue tasks
        executor.execute(new MyBizTask(3));
        executor.submit(new MyBizTask(1));
        executor.execute(new MyBizTask(4));
        executor.execute(new MyBizTask(2));
    }

    static class MyBizTask implements Runnable, Comparable<MyBizTask> {
        private final int priority;

        public MyBizTask(int priority) {
            this.priority = priority;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Hello world! " + this);
        }

        @Override
        public int compareTo(MyBizTask o) {
            return priority - o.priority;
        }

        @Override
        public String toString() {
            return "MyBizTask{priority=" + priority + '}';
        }
    }
}
