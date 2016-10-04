# Volatile and 应用(停止一个thread)

volatile 修饰变量， 不能修饰方法。

用 volative 修饰的变量： volatile is used as an indicator to Java compiler and Thread that do not cache value of this variable and always read it from main memory. 

通俗的语言来说， volatile修饰的变量， 在多个thread之间， 总是实时更新的， 不会出现偏差。

**All the reads happen after the write!**

但是 volatile 不保证 atomicity， 比如说 count++ 即使加上了 volatile, 也不能是atomicity的操作。

## thread safe singleton

所以当我们新建 singleton 的时候， 将Instance设置为 volatile， 当一个thread新建了一个instance之后， 其他的所有thread都知道这个变量不再是null了， 因为都会从main memory里面read。


**这里还要注意， 使用了 double checked locking**
In short, 1st check to speed up, 2nd check to avoid thread-safety issue.



```
public class Singleton{ 
	private static volatile Singleton instance;  	
	getInstance(){ 
		if(instance == null)	 
		{
		   synchronized(Singleton.class){ 
		   		if(instance == null){ 
		   			instance = new Singleton();
		   		}
		   		return instance; 
		   	}
		}
	}
}
```

用 volatile 修饰的变量不会有lock存在。

如果没有多线程， 也没有必要使用 volatile。

##应用
我们可以使用 volatile boolean 变量来停止一个 thread。

当 while (flag==false) 的时候， 这个thread 就停止运行。

```
public class ThreadStopDemo { 
	public static void main(String args[]) throws InterruptedException { 
		Server myServer = new Server(); 
		Thread t1 = new Thread(myServer, "T1");
		t1.start(); 
		
		//Now, let's stop our Server thread 		System.out.println(currentThread().getName() + " is stopping Server thread"); 
		myServer.stop(); 
		
		//Let's wait to see server thread stopped 		TimeUnit.MILLISECONDS.sleep(200);
		System.out.println(currentThread().getName() + " is finished now"); 
		} 
	} 
	
	class Server implements Runnable{ 
		private volatile boolean exit = false; 
		public void run() { 
			while(!exit){ 
				System.out.println("Server is running....."); 
			} 
			System.out.println("Server is stopped...."); 
		} 
		
		public void stop(){ 
			exit = true; 
		} 
	}
}
```

