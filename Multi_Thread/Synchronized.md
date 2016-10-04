#Synchronized

有些时候， 线程不同步的话会发生某些问题， 例子：

有一个class， 然后建立了若干个 thread， 每一个thread 的 run 里面， 都会对这个class的某一个方法进行访问。 如果不同步的话， 最后的结果就会不一定。 

比如说这个class中的方法 是 打印一个左括号， 一个String， 然后一个右括号。 用三个线程来打印不同的字符， 我们希望的结果是：

[aa], [bb], [cc]。 但是很有可能最后的结果是：

[aa[bb[cc]]]

这就是因为当一个线程进入这个方法之后， 其他的线程也进入了这个方法， 造成了混乱。


## 同步方法
为了避免这个的发生， 我们可以在这个方法前， 加一个 synchronized

```
class Callme {
    synchronized void call(String msg) {
        ...
```

**注意， synchronized 锁的是 这个 object 只被一个线程访问， 而不是 锁的 代码块**

##同步语句
虽然同步方法是可行的， 但是不是任何时候都能使用的， 比如说我们如果没有对这个Class进行修改的权限， 那么我们就不能对这个方法加上synchronized。

为了解决这个方法， 我们可以在线程的 run() 里面， 加入 synchronized(object)。 这样的话， 这个 object 就被锁住给唯一的一个 线程进行访问。

在这个例子中， 拥有这个方法的 Class 被锁住了。

```
class Caller implements Runnable {
    String msg;
    Callme target;
    Thread t;
    public Caller(Callme targ, String s) {
        target = targ;
        msg = s;
        t = new Thread(this);
        t.start();
    }

    // synchronize calls to call()
    public void run() {
        synchronized(target) { // synchronized block
            target.call(msg);
        }
    }
}
```

