package com.minmin.wenda.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.minmin.wenda.model.User;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ListPosition;
import redis.clients.jedis.Tuple;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: zhoutianbin
 * Date: 2020-09-02
 * Time: 22:23
 */
public class JedisAdapterTest {

    private static void print(int index, Object obj) {
        System.out.println(String.format("%d, %s", index, obj.toString()));
    }

    @Test
    public void practice() {
        Jedis jedis = new Jedis("redis://localhost:6379/1");
        jedis.flushDB();

        /* ************
         **** get set ****
         **************/
        // 用在如浏览数
        jedis.set("foo", "bar");
        String value = jedis.get("foo");
        print(1, value);
        jedis.rename("foo", "minmin");
        print(1, jedis.get("minmin"));

        // 用在如验证码中，或缓存中，文本的操作
        jedis.setex("hello", 5, "world");

        // pv 在内存中运行，速度快
        jedis.set("pv", "100");
        jedis.incr("pv");
        jedis.incrBy("pv", 2);
        jedis.decr("pv");
        jedis.decrBy("pv", 2);
        print(2, jedis.get("pv"));

        /* ************
         **** list ****
         **************/
        String listName = "list";
        jedis.del(listName);
        for (int i = 0; i < 10; i++) {
            jedis.lpush(listName, "a" + i);
        }
        print(3, jedis.lrange(listName, 0, 12));
        print(4, jedis.llen(listName));
        print(5, jedis.lpop(listName));
        print(6, jedis.lpush(listName, "haha"));
        print(7, jedis.lindex(listName, 3));
        print(8, jedis.linsert(listName, ListPosition.AFTER, "a1", "bb"));
        print(8, jedis.linsert(listName, ListPosition.BEFORE, "a1", "cc"));
        print(8, jedis.lrange(listName, 0, 12));

        /* ************
         **** hash ****
         **************/
        // 适合存放不定项的hash值
        String userKey = "user";
        jedis.hset(userKey, "name", "minmin");
        jedis.hset(userKey, "age", "12");
        jedis.hset(userKey, "phone", "888888");
        print(9, jedis.hget(userKey, "name"));
        print(9, jedis.hgetAll(userKey));
        jedis.hdel(userKey, "phone");
        print(10, jedis.hgetAll(userKey));
        print(10, jedis.hexists(userKey, "name"));
        print(10, jedis.hexists(userKey, "age"));
        print(11, jedis.hkeys(userKey));
        print(11, jedis.hvals(userKey));
        // 不存在才会set，存在不会set
        jedis.hsetnx(userKey, "haha", "aa");
        jedis.hsetnx(userKey, "name", "xiaominmin");
        print(12, jedis.hgetAll(userKey));


        /* ************
         **** set ****
         **************/
        String likeKey1 = "commenetLike1";
        String likeKey2 = "commenetLike2";
        for (int i = 0; i < 10; i++) {
            jedis.sadd(likeKey1, String.valueOf(i));
            jedis.sadd(likeKey2, String.valueOf(i * i));
        }
        print(13, jedis.smembers(likeKey1));
        print(13, jedis.smembers(likeKey2));
        // 求并集
        print(14, jedis.sunion(likeKey1, likeKey2));
        // 求不同
        print(15, jedis.sdiff(likeKey1, likeKey2));
        // 求交集
        print(16, jedis.sinter(likeKey1, likeKey2));
        print(17, jedis.sismember(likeKey1, "12"));
        print(17, jedis.sismember(likeKey2, "5"));
        // 删除
        jedis.srem(likeKey1, "5");
        print(17, jedis.smembers(likeKey1));
        // 移动
        jedis.smove(likeKey2, likeKey1, "25");
        print(18, jedis.smembers(likeKey1));
        print(18, jedis.smembers(likeKey2));
        print(19, jedis.scard(likeKey1));
        print(19, jedis.scard(likeKey2));
        // 从集合中随机取值
        print(20, jedis.srandmember(likeKey1, 2));

        /* *************************
         **** sortedSet 优先队列 ****
         ***************************/
        String rankKey = "rankKey";
        jedis.zadd(rankKey, 15, "Jim");
        jedis.zadd(rankKey, 60, "Jay");
        jedis.zadd(rankKey, 70, "Lily");
        jedis.zadd(rankKey, 80, "Zhou");
        // 一共有几个元素
        print(21, jedis.zcard(rankKey));
        // 范围内有几个元素
        print(22, jedis.zcount(rankKey, 60, 100));
        print(23, jedis.zscore(rankKey, "Zhou"));
        jedis.zincrby(rankKey, 2, "Zhou");
        print(23, jedis.zscore(rankKey, "Zhou"));
        jedis.zincrby(rankKey, 2, "Luc");
        // 取范围索引中的值
        print(24, jedis.zrange(rankKey, 0, 100));
        // 逆序
        print(24, jedis.zrevrange(rankKey, 0, 100));
        // 根据分 score 范围来取值
        for (Tuple tuple : jedis.zrangeByScoreWithScores(rankKey, 50, 100)) {
            print(25, tuple.getElement() + ":" + tuple.getScore());
        }
        // 获取排名
        print(26, jedis.zrank(rankKey, "Zhou"));
        print(26, jedis.zrevrank(rankKey, "Zhou"));

        String setKey = "same";
        jedis.zadd(setKey, 1, "a");
        jedis.zadd(setKey, 1, "b");
        jedis.zadd(setKey, 1, "c");
        jedis.zadd(setKey, 1, "d");
        jedis.zadd(setKey, 1, "e");

        print(27, jedis.zlexcount(setKey, "-", "+"));
        print(27, jedis.zlexcount(setKey, "[b", "[d"));
        print(27, jedis.zlexcount(setKey, "(b", "[d"));
        // 删除
        jedis.zrem(setKey, "b");
        print(28, jedis.zrange(setKey, 0, 10));
        // 根据字典序删除
        jedis.zremrangeByLex(setKey, "(c", "+");
        print(28, jedis.zrange(setKey, 0, 10));


        /* *************************
         **** pool redis连接池   ****
         ***************************/
        // 线程池默认8个线程
//        JedisPool pool = new JedisPool();
//        for (int i = 0; i < 100; i++) {
//            Jedis jedis1 = pool.getResource();
//            jedis1.select(1);
//            print(29, jedis1.get("pv"));
//            // 这里一定要关闭
//            jedis1.close();
//        }

        /* *************************
         **** 利用 redis 做缓存   ****
         ***************************/
        User user = new User();
        user.setName("minmin");
        user.setPassword("xx");
        user.setId(1);
        user.setSalt("salt");
        jedis.set("user1", JSONObject.toJSONString(user));
        User user2 = JSON.parseObject(jedis.get("user1"), User.class);
        print(29, user2.toString());
    }

}
