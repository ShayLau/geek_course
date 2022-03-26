package com.github.shaylau.geekCourse.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author ShayLau
 * @date 2022/3/26 6:27 PM
 */
@Component
public class StockService {


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 分布式锁失效时间
     */
    private final String expireTime = "1000";

    /**
     * 静态商品库存数据
     */
    private static final Map<String, Integer> productStockMap = new HashMap<>();

    static {
        productStockMap.put("p1", 200);
        productStockMap.put("p2", 200);
    }

    @PostConstruct
    public void initProductStock() {
        productStockMap.forEach((key, value) -> redisTemplate.opsForValue().set(key, value));
    }

    /**
     * 销售商品
     * 在 Java 中实现一个分布式计数器，模拟减库存
     *
     * @param productId 商品id
     */
    public boolean saleProduct(String productId) {
        if (productStockMap.containsKey(productId)) {
            return false;
        }
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(productId) && Long.parseLong(valueOperations.get(productId).toString()) > 0L) {
            valueOperations.decrement(productId);
            return true;
        }
        return false;
    }

    /**
     * 在 Java 中实现一个简单的分布式锁
     *
     * @param productId 商品id
     * @return
     */
    public void limitSaleProduct(String productId) {
        if (saleProductLock(productId, expireTime)) {
            System.out.println("商品抢购成功，正在下单！！！");
        } else {
            System.out.println("商品抢购失败，请重试！");
        }
    }


    /**
     * 销售商品上锁
     *
     * @param key
     * @param expireTime 失效时间s
     */
    private boolean saleProductLock(String key, String expireTime) {
        //问题脚本 ????  String script = "if redis.call('set', KEYS[1] KEYS[2] NX PX KEYS[3] ) == ARGV[1] then return 1 else return 0 end";
        String script = "if redis.call('set', KEYS[1] KEYS[2] NX PX KEYS[3] ) == ARGV[1] then return 1 else return 0 end";
        RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
        List<String> list = new ArrayList<>();
        list.add(key);
        list.add(key);
        list.add(expireTime);
        long result = redisTemplate.execute(redisScript, list);
        return Optional.of(result > 0L).orElse(false);
    }
}
