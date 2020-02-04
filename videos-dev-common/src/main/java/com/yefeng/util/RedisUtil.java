package com.yefeng.util;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisUtil {

    private static final Integer EXPIRE = 3600 * 24 * 7;

    private static final String TOKEN_PREFIX = "yefeng_token_";

    @Autowired
    private JedisPool jedisPool;

    private static Logger logger = Logger.getLogger(RedisUtil.class);
    //存数据
    public void set(String key,Integer expire,String value) throws Exception {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            if(expire == null) {
                jedis.setex(TOKEN_PREFIX +":"+ key,EXPIRE,value);
            } else {
                jedis.setex(TOKEN_PREFIX +":"+ key,expire,value);
            }
        }catch (Exception e) {
            throw new Exception("新增redis数据出错");
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }
    //取数据
    public String get(String key) throws Exception {
        Jedis jedis = null;
        String value = null;
        try{
            jedis = jedisPool.getResource();
            value = jedis.get(TOKEN_PREFIX +":"+ key);
        } catch (Exception e) {
            throw new Exception("获取redis数据出错");
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
        return value;
    }
    //删除数据
    public void delete(String key) throws Exception {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.del(TOKEN_PREFIX +":"+ key);
        } catch (Exception e) {
            throw new Exception("删除redis数据出错");
        } finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }

    public void expireTime(String key) throws Exception {
        Jedis jedis = null;
        try{
            jedis = jedisPool.getResource();
            jedis.expire(TOKEN_PREFIX +":"+ key,EXPIRE);
        }catch (Exception e) {
            throw new Exception("刷新redis过期时间出错");
        }finally {
            if(jedis != null) {
                jedis.close();
            }
        }
    }




}
