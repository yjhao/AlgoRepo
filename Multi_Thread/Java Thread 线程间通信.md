#Java Thread 线程间通信

## 案例
先给一个例子， 有一个仓库对象， 支持送货和取货。有两个thread， 一个thread往一个仓库里面送货， 一个thread往一个仓库里面取货。

如果这两个thread没有沟通的话， 有可能发生， 这个仓库其实已经都空了， 而取货那个thread还在继续取货， 这显然是不希望发生的。

所以解决办法就是， 当送货之后， 提示取货的thread现在可以取货了， 当取了货之后， 提示送货的， 现在可以送货了。 这样的话， 就不会发生之前出现的一直不停取货的情况。

## 实现
为了实现 提示 的作用， 采用 notify()。 使一个thread 停止下来来等待另外一个thread的提示， 采用 wait()。 当wait() 的 thread 收到 notify() 之后， 又会继续运行。

1. **wait( )** 告知被调用的线程放弃管程进入睡眠直到其他线程进入相同管程并且调用notify( )。
2. **notify( )** 恢复相同对象中第一个调用 wait( ) 的线程。
3. **notifyAll( )** 恢复相同对象中所有调用 wait( ) 的线程。具有最高优先级的线程最先运行。

## wait and sleep 区别
wait 的时候， 会释放 Lock, 而且 wait notify必须是在 synchronized 的语句下。

为什么 wait notify 必须要在 synchronized下？ 为了避免 race condition。

##代码
首先创建这个仓库对象， 在仓库中， 送货和取货这两个方法， 应该互相可以告知。 

那么如何判断谁该告知谁呢？

我们采用 flag 来区分在一个时间， 谁该等待， 谁该告知。 在同一个时间， 一定有一个等待， 一个告知。 这个flag状态在 0 1 之间切换。

```
class Q {
    int n;
    boolean valueSet = false;
    synchronized int get() {
        while(!valueSet){
            try {
                wait();
            } catch(InterruptedException e) {
                System.out.println("InterruptedException caught");
            }
        }
        System.out.println("Got: " + n);
        valueSet = false;
        notify();
        return n;
    }
    synchronized void put(int n) {
        while(valueSet){
            try {
                wait();
            } catch(InterruptedException e) {
                System.out.println("InterruptedException caught");
            }
        }
        this.n = n;
        valueSet = true;
        System.out.println("Put: " + n);
        notify();
    }
}
```

然后是两个thread， 分别送货， 和分别取货

```
class Producer implements Runnable {
    Q q;
    Producer(Q q) {
        this.q = q;
        new Thread(this, "Producer").start();
    }
    public void run() {
        int i = 0;
        while(true) {
            q.put(i++);
        }
    }
}
class Consumer implements Runnable {
    Q q;
    Consumer(Q q) {
        this.q = q;
        new Thread(this, "Consumer").start();
    }
    public void run() {
        while(true) {
            q.get();
        }
    }
}
```

最后是 main function

```
class PCFixed {
    public static void main(String args[]) {
        Q q = new Q();
        new Producer(q);
        new Consumer(q);
        System.out.println("Press Control-C to stop.");
    }
}
```

##第二种方法
在这里， 没有建立这个仓库模型， 而是直接使用一个数据结构 linkedlist as queue 来代表这个仓库。

两个thread， 一个加入， 一个取货。 分别用 synchronized (queue) 来修饰方法, 因为这是wait notify 的必须要求。

要运行 wait 或者 notify 的时候， 使用 这个 queue.wait() queue.notify(). 因为 wait notify 是针对的相同对象中的下一个 thread。

```
public class InterThreadCommunicationExample {

    public static void main(String args[]) {

        final Queue sharedQ = new LinkedList();

        Thread producer = new Producer(sharedQ);
        Thread consumer = new Consumer(sharedQ);

        producer.start();
        consumer.start();

    }
}

public class Producer extends Thread {
    private static final Logger logger = Logger.getLogger(Producer.class);
    private final Queue sharedQ;

    public Producer(Queue sharedQ) {
        super("Producer");
        this.sharedQ = sharedQ;
    }

    @Override
    public void run() {

        for (int i = 0; i < 4; i++) {

            synchronized (sharedQ) {
                //waiting condition - wait until Queue is not empty
                while (sharedQ.size() >= 1) {
                    try {
                        logger.debug("Queue is full, waiting");
                        sharedQ.wait();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                logger.debug("producing : " + i);
                sharedQ.add(i);
                sharedQ.notify();
            }
        }
    }
}

public class Consumer extends Thread {
    private static final Logger logger = Logger.getLogger(Consumer.class);
    private final Queue sharedQ;

    public Consumer(Queue sharedQ) {
        super("Consumer");
        this.sharedQ = sharedQ;
    }

    @Override
    public void run() {
        while(true) {

            synchronized (sharedQ) {
                //waiting condition - wait until Queue is not empty
                while (sharedQ.size() == 0) {
                    try {
                        logger.debug("Queue is empty, waiting");
                        sharedQ.wait();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                int number = sharedQ.poll();
                logger.debug("consuming : " + number );
                sharedQ.notify();
              
                //termination condition
                if(number == 3){break; }
            }
        }
    }
}
```