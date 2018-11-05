-- 限流脚本

local key = KEY[1] -- 限流KEY（1秒1个）
local limite = tonumber(ARGV[1]) -- 限流大小
local current = tonumber(redis.call("GET",key) or "0") -- 之前请求数
if current + 1 > limite then
    return 0
else -- 请求数加1，并设置2秒过期
    redis.call("INCRBY", key, "1")
    redis.call("EXPIRE", key, "2")
    return 1
end
