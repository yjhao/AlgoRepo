#Observer Design Pattern
##逻辑

当一个对象的属性发生了变化， 要通知另外一个观察者这一变化的时候， 我们就应该使用 observer pattern。

观察者就 Observer, 被观察者叫 subject。

这是一个 one - to - many 的关系， 一个 subject 有可能有多个 observer， 所以对于subject， 必须要有两个method， 一个是 register， 一个是 unregister， 然后发生变化之后， 可以对 registered 的每一个 observer， 进行notify。 还需要有一个方法， 来提取这个变化， 在observer被告知有变化之后， 被observer来使用。

对于 observer, 应该有一个方法， 设置要 watch 的对象， 和一个被对象使用的方法， 来告知observer， 这个对象发生了变化。

首先我们来看看 subject interface 和 observer interface

```
public interface Subject {

	//methods to register and unregister observers
	public void register(Observer obj);
	public void unregister(Observer obj);
	
	//method to notify observers of change
	public void notifyObservers();
	
	//method to get updates from subject
	public Object getUpdate(Observer obj);
	
}

public interface Observer {
	
	//method to update the observer, used by subject
	public void update();
	
	//attach with subject to observe
	public void setSubject(Subject sub);
}

```

下面我们来实例化 subject, 在这个实例中， 用一个 arraylist 来保存所有的 observer， 当这个对象post message之后， 一个状态 flag就发生了变化， 然后 call notifyAll method 来告知每一个 observer 这个对象发生了变化。这个状态变量很重要， 因为如果没有发生变化， 即使 call notifyAll， 也不会真的去告知， 因为根本就没有变化发生。

使用 synchronized 可以保证， notifyAll， 告知的 observer 一定是在 这个变化发生之前就已经注册了的。 因为如果要注册， 也是需要 synchronized 一个object的。

```
public class MyTopic implements Subject {

	private List<Observer> observers;
	private String message;
	private boolean changed;
	private final Object MUTEX= new Object();
	
	public MyTopic(){
		this.observers=new ArrayList<>();
	}
	
	@Override
	public void register(Observer obj) {
		if(obj == null) throw new NullPointerException("Null Observer");
		synchronized (MUTEX) {
			if(!observers.contains(obj)) observers.add(obj);
		}
	}

	@Override
	public void unregister(Observer obj) {
		synchronized (MUTEX) {
			observers.remove(obj);
		}
	}

	@Override
	public void notifyObservers() {
		List<Observer> observersLocal = null;
		//synchronization is used to make sure any observer registered after message is received is not notified
		synchronized (MUTEX) {
			if (!changed)
				return;
			observersLocal = new ArrayList<>(this.observers);
			this.changed=false;
		}
		for (Observer obj : observersLocal) {
			obj.update();
		}

	}

	@Override
	public Object getUpdate(Observer obj) {
		return this.message;
	}
	
	//method to post message to the topic
	public void postMessage(String msg){
		System.out.println("Message Posted to Topic:"+msg);
		this.message=msg;
		this.changed=true;
		notifyObservers();
	}

}
```

下面我们来看看一个 observer 的实例化。

```
public class MyTopicSubscriber implements Observer {
	
	private String name;
	private Subject topic;
	
	public MyTopicSubscriber(String nm){
		this.name=nm;
	}
	
	@Override
	public void update() {
		String msg = (String) topic.getUpdate(this);
		if(msg == null){
			System.out.println(name+":: No new message");
		}else
		System.out.println(name+":: Consuming message::"+msg);
	}

	@Override
	public void setSubject(Subject sub) {
		this.topic=sub;
	}

}
```

下面是一个完整的 Main 方法:

1.首先在 subject 下 注册 observer.

2.然后对于每一个 observer， 注册 subject.

3.然后post message to subject， triger the update method。
  

```
public class ObserverPatternTest {

	public static void main(String[] args) {
		//create subject
		MyTopic topic = new MyTopic();
		
		//create observers
		Observer obj1 = new MyTopicSubscriber("Obj1");
		Observer obj2 = new MyTopicSubscriber("Obj2");
		Observer obj3 = new MyTopicSubscriber("Obj3");
		
		//register observers to the subject
		topic.register(obj1);
		topic.register(obj2);
		topic.register(obj3);
		
		//attach observer to subject
		obj1.setSubject(topic);
		obj2.setSubject(topic);
		obj3.setSubject(topic);
		
		//check if any update is available
		obj1.update();
		
		//now send message to subject
		topic.postMessage("New Message");
	}

}
```
