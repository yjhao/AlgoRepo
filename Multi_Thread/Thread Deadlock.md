#Thread Deadlock

##概念
假设有两个对象， 线程1进入了对象A, 线程2进入了对象B。 这些对象都是 synchornized的， 所以分别指定给了相应的线程。

这个时候， 如果线程1在对象A里面， 想使用对象B的一个方法， 而且线程2在对象B里面， 想使用对象A的一个方法， 双方就会一直等待， 形成死锁。

虽然这是小概率时间， 两个对象在同一时间使用另外一个对象的方法， 不过还是应该在实际编程中更好的设计。

##例子
在本例子中， main thread 在 call a.foo(b) 之前， 新开了另外一个thread 来 call b.foo(a)。 这样的话， 造成死锁。

关键的理解在于， synchronized **锁的是对象， 而不是代码块**。 所以在这个对象被一个线程占用之后， 另外一个线程是没有办法进入的。

```
class A {
    synchronized void foo(B b) {
        String name = Thread.currentThread().getName();
        System.out.println(name + " entered A.foo");
        try {
            Thread.sleep(1000);
        } catch(Exception e) {
            System.out.println("A Interrupted");
        }
        System.out.println(name + " trying to call B.last()");
        b.last();
    }
    synchronized void last() {
        System.out.println("Inside A.last");
    }
}
class B {
    synchronized void bar(A a) {
        String name = Thread.currentThread().getName();
        System.out.println(name + " entered B.bar");
        try {
            Thread.sleep(1000);
        } catch(Exception e) {
            System.out.println("B Interrupted");
        }
        System.out.println(name + " trying to call A.last()");
        a.last();
    }
    synchronized void last() {
        System.out.println("Inside A.last");
    }
}
class Deadlock implements Runnable {
    A a = new A();
    B b = new B();
    Deadlock() {
        Thread.currentThread().setName("MainThread");
        Thread t = new Thread(this, "RacingThread");
        t.start();
        a.foo(b); // get lock on a in this thread.
        System.out.println("Back in main thread");
    }
    public void run() {
        b.bar(a); // get lock on b in other thread.
        System.out.println("Back in other thread");
    }
    public static void main(String args[]) {
        new Deadlock();
    }
}
```


##另外一个例子

```
public void method1() { 
	synchronized (String.class) {
		System.out.println("Aquired lock on String.class object"); 
		
		synchronized (Integer.class) { 
			System.out.println("Aquired lock on Integer.class object"); 
		} 
	} 
}

public void method2() { 
	synchronized (Integer.class) {
		System.out.println("Aquired lock on Integer.class object"); 
		
		synchronized (String.class) { 
			System.out.println("Aquired lock on String.class object"); 
		} 
	} 
}
```

##解决方案
The easiest way to avoid deadlock is to prevent **Circular wait**, and this can be done by acquiring locks in a **particular order** and releasing them in **reverse order** so that a thread can only proceed to acquire a lock if it held the other one.


