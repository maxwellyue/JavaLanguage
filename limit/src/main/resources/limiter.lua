-- 限流脚本

local key = KEY[1] -- 限流KEY（1秒1个）
local limite = tonumber(ARGV[1]) -- 限流大小
local current = tonumber(redis.call("INCRBY",key, "1")) -- 请求数加1
if current > limite then
    return 0
elseif current == 1 then
    redis.call("EXPIRE", key, "2")
end
return 1