# Thread "suspend, resume" 

##旧方法
在最新的Java中， 已经不再使用 suspend(), resume() 来暂停和恢复一个线程。因为suspend()有时会造成严重的系统故障。假定对关键的数据结构的一个线程被锁定的情况，如果该线程在那里挂起，这些锁定的线程并没有放弃对资源的控制。其他的等待这些资源的线程可能死锁。

##新方法
新的实现暂停、再启、停止线程的方法就是围绕一个suspendFlag(布尔变量)， 结合 wait(), notify() 来实现的。

如果这个suspendFlag==true， 那么就wait。 

如果要恢复工作， 在一个方法之中， 将 suspendFlag 设置为 false， 并且 notify()。

当wait结束之后， 此时的suspendFlag==false。如果要再次suspend， 将 suspendFlag 设置为 true。 这样的话， 当检测到suspendFlag==true之后， 就会自动进入wait状态。 

