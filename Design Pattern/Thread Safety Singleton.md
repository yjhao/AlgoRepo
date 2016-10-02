#Thread Safety Singleton

##逻辑
有些时候， 在整个系统中， 我们只需要有且唯一的一个实例， 比如说连接数据库。

所以我们需要建立， 而且只能建立一个实例。

为了建立唯一的一个实例，我们得防止别人使用 constructer， 所以把constructer变为 private 的。

为了实例化这个 Class， 使用一个 method 来实例化。 因为是有且唯一的， 所以使用 static 来修饰这个method。

当使用这个 method 的时候， 如果 这个 static 的实例不是 null， 就说明之前已经实例化了， 不做任何事情。 如果这个实例是null， 我们就需要把它 new。

##多线程
在多线程环境中， 有可能多个线程同时访问这个方法， 同时检测到 这个实例 不是 null， 所以就会同时建立多个实例。

为了解决这个问题， 我们采用 synchronized 来修饰。

但使用 synchornized 的缺点是， 影响速度。所以 synchoronized 放的位置很重要。 如果把 它 的位置放的太外层， 那么就会很影响速度。 所以我们把它放在 能够最内层的部位： 在 check 实例 == null 之后， 这样的话， 影响的线程只会包括程序刚开始运行的时候的那几个检测到没有新建实例的线程。

synchornized 一个 object。

##代码
```
public class ASingleton{

	private static ASingleton instance= null;
	private static Object mutex= new Object();
	
	private ASingleton(){
	}

	public static ASingleton getInstance(){
		if(instance==null){
			synchronized (mutex){
				if(instance==null) instance= new ASingleton();
			}
		}
		return instance;
	}

}
``` 