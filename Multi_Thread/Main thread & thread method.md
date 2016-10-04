#主线程 和 线程method

## 
主线程在JAVA程序启动时自动创建， 但它可以由一个Thread对象控制。

必须调用方法currentThread()获得它的一个引用，currentThread()是Thread类的公有的静态成员。 该方法返回一个调用它的线程的引用。

```
Thread t = Thread.currentThread();
```

管理线程的方法

| 名字   |      作用      |
|----------|:-------------:|
| getName |  获得线程名称 | 
| getPriority |    获得线程优先级   | 
| isAlive | 判定线程是否仍在运行 | 
| join | 等待一个线程终止 |
| run | 线程的入口点 |
| sleep | 在一段时间内挂起线程 |
| start | 通过调用运行方法来启动线程 |

```
static void sleep(long milliseconds) throws InterruptedException
```

```
final void join() throws InterruptedException

```