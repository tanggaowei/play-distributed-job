package util.distributed;

import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author tanggaowei
 */
public class RedissonManager {
    private static final String RAtomicPreName = "play:redisson:";

    private static RedissonClient redisson = null;

    public static RedissonClient getRedisson() {
        if (redisson == null) {
            Config config = new Config();
            config.useSingleServer()
                    .setConnectionPoolSize(10000)
                    .setAddress("redis://127.0.0.1:6379")
                    .setPassword("ShLjyF7VI5wrzNIYjx52zR09CiGWMaGx");
            redisson = Redisson.create(config);
        }
        return redisson;
    }

    /**
     * 获取redis中的原子ID
     */
    public static Long nextID(String key) {
        RAtomicLong atomicLong = getRedisson().getAtomicLong(RAtomicPreName + key);
        atomicLong.incrementAndGet();
        return atomicLong.get();
    }
}
