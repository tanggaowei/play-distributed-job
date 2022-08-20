package job;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import play.Logger;
import play.jobs.Job;
import util.distributed.RedissonManager;

import java.util.concurrent.TimeUnit;

/**
 * 分布式任务
 *
 * @author tanggaowei
 */
public abstract class DistributedJob extends Job {
    private static int WAIT_TIME = 1;
    private static RedissonClient redisson = RedissonManager.getRedisson();

    /**
     * 实际的执行代码
     */
    protected abstract void todo();

    /**
     * 设置非阻塞锁的自动释放时间
     */
    protected abstract int getTimeout();

    @Override
    public void doJob() {
        try {
            RLock lock = redisson.getLock(lockKey());
            boolean res = lock.tryLock(WAIT_TIME, getTimeout(), TimeUnit.SECONDS);

            if (!res) {
                Logger.info("lock fail: %s %d", getClass().getSimpleName(), Thread.currentThread().getId());
                return;
            }

            try {
                todo();
            } finally {
                lock.unlock();
            }
        } catch (Exception e) {
            Logger.error(e, "play job run fail.");
        }
    }

    /**
     * 锁名称
     */
    protected String lockKey() {
        return "play:lock:" + getClass().getName();
    }
}
