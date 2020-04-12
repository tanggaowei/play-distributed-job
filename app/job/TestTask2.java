package job;

import play.Logger;
import play.jobs.Every;

@Every(value = "1s")
public class TestTask2 extends DistributedJob {
    @Override
    public int getTimeout() {
        return 30;
    }

    @Override
    public void todo() {
        Logger.info("task2 todo:" + Thread.currentThread().getId());
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
