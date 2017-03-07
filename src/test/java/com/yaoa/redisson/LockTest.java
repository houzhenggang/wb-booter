package com.yaoa.redisson;

import org.redisson.Redisson;
import org.redisson.RedissonLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * Created by administer on 2017/2/28.
 */
public class LockTest {

    public void testLock(){
        RedissonClient client = Redisson.create();
        RLock lock = client.getLock("ddddd");
        lock.tryLock();
    }
}
