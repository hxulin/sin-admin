package tech.ldxy.sin.system.util;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import tech.ldxy.sin.ApplicationTests;
import tech.ldxy.sin.system.model.entity.User;

import java.util.concurrent.TimeUnit;

/**
 * 功能描述:
 *
 * @author hxulin
 */
public class RedisTest extends ApplicationTests {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testSave() {

        User user = new User("loginName", "password");

//        stringRedisTemplate.opsForValue().set("aaaa", "111");
//
//
//        stringRedisTemplate.expire("aaaa", 10, TimeUnit.SECONDS);

        redisTemplate.opsForValue().set("1234567", "user", 100, TimeUnit.SECONDS);

        Object user1 = redisTemplate.opsForValue().get("user");

        System.out.println(user1);
        System.out.println(user1.getClass().getName());

//        redisTemplate.opsForSet().add("user5:123", "/add", "/edit", "/test");

//
//        ValueOperations valueOperations = redisTemplate.opsForValue();
//
//        stringRedisTemplate.opsForValue().set("aaa", "111", 2000);
//        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
//        System.out.println(1222);

    }

    @Test
    public void testExpire() {
        redisTemplate.expire("1234567", 30, TimeUnit.MINUTES);
    }

    @Test
    public void testSet() {

        Boolean member = redisTemplate.opsForSet().isMember("user:157:resource", 2);

        System.out.println(member);

//        redisTemplate.opsForSet().add("user:157:resource", 1, 2, 3);
//        redisTemplate.opsForSet().add("user:158:resource", 1, 2, 3);
//        redisTemplate.opsForSet().add("user:157:resource", 2, 3, 4);
    }
}
