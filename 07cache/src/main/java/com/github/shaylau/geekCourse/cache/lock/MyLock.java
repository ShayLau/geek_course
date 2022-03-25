package com.github.shaylau.geekCourse.cache.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 基于 redis 的锁
 *
 * @author ShayLau
 * @date 2022/3/23 17:39
 */
@Component
public class MyLock {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 上锁
     *
     * @param key
     * @param expireTime 失效时间s
     */
    public void lock(String key, long expireTime) {
        String script = "if redis.call(set key[1] 1 NX EX key[2]==ARG[1]) then 1 else return 0 end";
        RedisScript redisScript = new DefaultRedisScript(script, Long.class);
        List<Object> list = new ArrayList<>();
        list.add(key);
        list.add(expireTime);
//        redisTemplate.execute(redisScript, list);

    }

    public void unlock(String key) {


    }
}
