package com.maxwell.learning.limite;

import com.google.common.collect.Lists;
import com.google.common.io.Files;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author yuezengcun <yuezengcun@meicai.cn>
 * @since
 */
public class RedisLuaLimiter {

    public boolean permit() throws IOException {
        String luaScript = Files.toString(new File("limit_v2.lua"), Charset.defaultCharset());
        String key = "ip:" + System.currentTimeMillis() / 1000;
        String limit = "3";
        Long res = (Long) new Jedis().eval(luaScript, Lists.newArrayList(key), Lists.newArrayList(limit));
        return res == 1;
    }
}
