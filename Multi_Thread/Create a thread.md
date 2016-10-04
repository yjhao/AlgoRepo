#Create a thread

有两种方法创建一个线程。

1. implements runnable.
2. extends Thread.

无论采用哪种方法， 都有三个步骤。

1. 首先， 都要把需要运行的内容， 放在 void run() 方法里面。
2. 都需要实例化一个 Thread 类的对象：
```
Thread t = new Thread(this, "thread name");
```
3. 然后执行这个新建的 thread 的 start() 方法， 这也是一个 thread 的入口。

##为什么要call start(), 而不直接Call run()
如果 Call start(), 新建的一个 thread 会运行 run()

如果直接 call run(), run() 会运行在当前的相同的thread上。


## Runnable

catch interruptedException， 这种情形会在其他线程想要打搅沉睡线程时发生。本例只是打印了它是否被打断的消息

```
class RunnableDemo implements Runnable {
   private Thread t;
   private String threadName;
   
   RunnableDemo( String name) {
      threadName = name;
      System.out.println("Creating " +  threadName );
   }
   
   public void run() {
      System.out.println("Running " +  threadName );
      try {
         for(int i = 4; i > 0; i--) {
            System.out.println("Thread: " + threadName + ", " + i);
            // Let the thread sleep for a while.
            Thread.sleep(50);
         }
      }catch (InterruptedException e) {
         System.out.println("Thread " +  threadName + " interrupted.");
      }
      System.out.println("Thread " +  threadName + " exiting.");
   }
   
   public void start () {
      System.out.println("Starting " +  threadName );
      if (t == null) {
         t = new Thread (this, threadName);
         t.start ();
      }
   }
}

public class TestThread {

   public static void main(String args[]) {
      RunnableDemo R1 = new RunnableDemo( "Thread-1");
      R1.start();
      
      RunnableDemo R2 = new RunnableDemo( "Thread-2");
      R2.start();
   }   
}
```

## Extends Thread
可以看到， 两者几乎一样。
```
class ThreadDemo extends Thread {
   private Thread t;
   private String threadName;
   
   ThreadDemo( String name) {
      threadName = name;
      System.out.println("Creating " +  threadName );
   }
   
   public void run() {
      System.out.println("Running " +  threadName );
      try {
         for(int i = 4; i > 0; i--) {
            System.out.println("Thread: " + threadName + ", " + i);
            // Let the thread sleep for a while.
            Thread.sleep(50);
         }
      }catch (InterruptedException e) {
         System.out.println("Thread " +  threadName + " interrupted.");
      }
      System.out.println("Thread " +  threadName + " exiting.");
   }
   
   public void start () {
      System.out.println("Starting " +  threadName );
      if (t == null) {
         t = new Thread (this, threadName);
         t.start ();
      }
   }
}

public class TestThread {

   public static void main(String args[]) {
      ThreadDemo T1 = new ThreadDemo( "Thread-1");
      T1.start();
      
      ThreadDemo T2 = new ThreadDemo( "Thread-2");
      T2.start();
   }   
}
```

Alternatively，因为这个thread 是  extends Thread， 所以自己本来就是一个 thread， 没有必要重新新建一个 thread 来 start， 我们可以先使用 super constructer， 然后直接 start。

```
super("Demo Thread");
System.out.println("Child thread: " + this);
start(); // Start the thread
``` 




 