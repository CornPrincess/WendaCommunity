package com.minmin.wenda.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: zhoutianbin
 * Date: 2020-09-01
 * Time: 23:44
 */
@Slf4j
@Service
public class JedisAdapter implements InitializingBean {

    private JedisPool jedisPool;

    @Override
    public void afterPropertiesSet() throws Exception {
        jedisPool = new JedisPool("redis://localhost:6379/2");
    }

    public long sadd(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.sadd(key, value);
        } catch (Exception e) {
            log.error("jedis sadd error: " + e.getMessage());
        }
        return 0;
    }


    public long srem(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.srem(key, value);
        } catch (Exception e) {
            log.error("jedis srem error: " + e.getMessage());
        }
        return 0;
    }

    public long scard(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.scard(key);
        } catch (Exception e) {
            log.error("jedis scard error: " + e.getMessage());
        }
        return 0;
    }

    public boolean sismember(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.sismember(key, value);
        } catch (Exception e) {
            log.error("jedis sismember error: " + e.getMessage());
        }
        return false;
    }

    public List<String> brpop(int timeout, String key) {
        try (Jedis jedis = jedisPool.getResource();) {
            return jedis.brpop(timeout, key);
        } catch (Exception e) {
            log.error("jedis brpop error: " + e.getMessage());
        }
        return null;
    }

    public long lpush(String key, String value) {
        try (Jedis jedis = jedisPool.getResource();) {
            return jedis.lpush(key, value);
        } catch (Exception e) {
            log.error("jedis brpop error: " + e.getMessage());
        }
        return 0;
    }

    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    // redis 事务的开始
    public Transaction multi(Jedis jedis) {
        try {
            return jedis.multi();
        } catch (Exception e) {
            log.error("发生异常" + e.getMessage());
        } finally {
        }
        return null;
    }

    // 事务的执行
    public List<Object> exec(Transaction tx, Jedis jedis) {
        try {
            return tx.exec();
        } catch (Exception e) {
            log.error("发生异常" + e.getMessage());
            tx.discard();
        } finally {
            if (tx != null) {
                tx.close();
            }

            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public long zadd(String key, double score, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zadd(key, score, value);
        } catch (Exception e) {
            log.error("发生异常" + e.getMessage());
        }
        return 0;
    }

    public Set<String> zrevrange(String key, int start, int end) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zrevrange(key, start, end);
        } catch (Exception e) {
            log.error("发生异常" + e.getMessage());
        }
        return null;
    }

    public long zcard(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zcard(key);
        } catch (Exception e) {
            log.error("发生异常" + e.getMessage());
        }
        return 0;
    }

    public Double zscore(String key, String member) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.zscore(key, member);
        } catch (Exception e) {
            log.error("发生异常" + e.getMessage());
        }
        return null;
    }
}
