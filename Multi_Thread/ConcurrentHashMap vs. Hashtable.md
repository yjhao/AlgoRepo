#ConcurrentHashMap vs. Hashtable

都是 thread safe。

但是 ConcurrentHashMap 要更快。

ConcurrentHashMap 将整个map分块上锁。而 hashtable 对整个map上锁。

而且ConcurrentHashMap对 读 是没有锁的。 所以如果有大量read， 速度就会很快。