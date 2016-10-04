#Race condition
这个发生在两个或多个 thread 访问到同一个方法， 因为这个方法的语句不是 atomic 的， 所以两个thread会做出相同的动作， 造成race。

比如说：

```
if(!hashtable.contains(key)){
	hashtable.put(key,value);
}
```

有可能发生的race是， 两个thread都访问到 if， 然后发现没有这个key， 所以都会选择加入这个key。

两行语句， 分别都是 Atomic 的， 但是放在一起就不是了。

所以为了解决这个问题， 可以把他们放在一个 synchronized 的语句块里面。

另外一个例子， 就是 thread safe singleton 的时候， 当check instance == null之后， 会建立一个新的 instance。 但是如果没有上锁的话， 有可能两个thread同时检测到instance==null。