package util.distributed;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/**
 * @author tanggaowei
 */
public class DistributedLock {
    private static int AUTO_EXIT_TIME = 30;
    private static RedissonClient redisson = RedissonManager.getRedisson();
    private static final String LOCK_TITLE = "play:redisson:lock:";

    public static void lock(String lockName, int timeout) {
        String key = LOCK_TITLE + lockName;
        RLock rLock = redisson.getLock(key);
        rLock.lock(timeout, TimeUnit.MINUTES);
        System.err.println("======lock======" + Thread.currentThread().getName());
    }

    public static void lock(String lockName) {
        String key = LOCK_TITLE + lockName;
        RLock rLock = redisson.getLock(key);
        rLock.lock(AUTO_EXIT_TIME, TimeUnit.MINUTES);
        System.err.println("======lock======" + Thread.currentThread().getName());
    }

    public static void unlock(String lockName) {
        String key = LOCK_TITLE + lockName;
        RLock mylock = redisson.getLock(key);
        mylock.unlock();
        System.err.println("======unlock======" + Thread.currentThread().getName());
    }
}
