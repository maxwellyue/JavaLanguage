-- Lua限流脚本:::Nginx版本
local locks = require "resty.lock"
local function permit()
    local lock = locks:new("locks")
    local elapsed, err = lock:lock("limit_key") -- 互斥锁
    local limit_counter = ngx.shared.limit_counter -- 计数器
    local key = "ip:" .. os.time()
    local limit = 5 -- 限流大小
    local current = limit_counter:get(key)

    if current == nil then
        limit_counter:set(key, 1, 1) -- 允许访问，第一次需要设置过期时间
        lock:unlock()
        return 1
    elseif current + 1 > limit then -- 超出限流大小
        lock:unlock()
        return 0
    else -- 允许访问
        limit_counter:incr(key, 1)
        lock:unlock()
        return 1
    end
end
ngx.print(permit())