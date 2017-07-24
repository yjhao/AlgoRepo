## Dependency Injection

## 逻辑

首先建立一个 interface 代表一些服务， 比如说音乐播放器， 然后 implements 一些实体的音乐播放器， 比如说索尼， 松下。

```
public interface MessageService {

	void sendMessage(String msg, String rec);
}

public class EmailServiceImpl implements MessageService {

	@Override
	public void sendMessage(String msg, String rec) {
		//logic to send email
		System.out.println("Email sent to "+rec+ " with Message="+msg);
	}
}
```


然后为了使用这些音乐播放器， 建立一个电台， 将这些音乐播放器的Class， 用 argument 的方式传入这个电台。 也可以建立一个电台的 Interface, 然后Implement各个牌子的电台 （取决于音乐播放器）。

```
public interface Consumer {

	void processMessages(String msg, String rec);
}

public class MyDIApplication implements Consumer{

	private MessageService service;
	
	public MyDIApplication(MessageService svc){
		this.service=svc;
	}
	
	@Override
	public void processMessages(String msg, String rec){
		//do some msg validation, manipulation logic etc
		this.service.sendMessage(msg, rec);
	}

}
```


最后建立一个injector class, 返回电台父类。

```
public interface MessageServiceInjector {

	public Consumer getConsumer();
}

public class EmailServiceInjector implements MessageServiceInjector {

	@Override
	public Consumer getConsumer() {
		return new MyDIApplication(new EmailServiceImpl());
	}

}
```