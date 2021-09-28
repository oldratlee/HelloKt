package playground.ttl.comparable_task_queue;

import java.util.concurrent.*;

/**
 * @author qian.zhi
 */
public class TestQueue {
    public static void main(String[] args) {
        BlockingQueue<Runnable> queue = new PriorityBlockingQueue<>();
        // 模拟任务提交速度大于线程处理速度
        queue.add(new Task(2));
        queue.add(new Task(1));
        queue.add(new Task(4));
        queue.add(new Task(3));

        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1L, TimeUnit.SECONDS, queue);
        executor.execute(new Task(400));
        executor.execute(new Task(5));
    }

    private static class Task implements Runnable, Comparable<Task> {
        int priority;

        public Task(int priority) {
            this.priority = priority;
        }

        @Override
        public int compareTo(Task o) {
            return this.priority - o.priority;
        }

        @Override
        public String toString() {
            return "Task{priority=" + priority + '}';
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(this);
        }
    }
}
