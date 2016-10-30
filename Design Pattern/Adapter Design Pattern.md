#Adapter Design Pattern
##逻辑

adapter 就是提供一个interface 或者 object， 将两个不相关的Interface联系在一起。

##例子
比如说墙上的插座 socket 提供 120 v， 但是我们想将其转化为几种不同的电压， 我们就需要使用 adapter。

首先是一个 socket, 和 电压
 
```
public class Socket {

	public Volt getVolt(){
		return new Volt(120);
	}
}

public class Volt {

	private int volts;
	
	public Volt(int v){
		this.volts=v;
	}

	public int getVolts() {
		return volts;
	}

	public void setVolts(int volts) {
		this.volts = volts;
	}
	
}
```

然后是一个 adapter 的 interface， 我们要将电压转化为 120， 12， 3。


##实现 adapter
在这里， 有两种方法实现这个 adapter。

实质上， 区别在于， 我们使用 inheritance 还是 encapsulation （composition）。

在 inheritance 中， 我们继承 原来的socket， 然后使用它的方法。 这个方法称为 class adapter

在 encapsulation中， 我们新建一个 socket方法， 然后使用它的方法。 这个方法称为 object adapter

## class adapter -- inheritance
注意， 有一个 extends socket, 直接使用了 getVolt()

```
public class SocketClassAdapterImpl extends Socket implements SocketAdapter{

	@Override
	public Volt get120Volt() {
		return getVolt();
	}

	@Override
	public Volt get12Volt() {
		Volt v= getVolt();
		return convertVolt(v,10);
	}

	@Override
	public Volt get3Volt() {
		Volt v= getVolt();
		return convertVolt(v,40);
	}
	
	private Volt convertVolt(Volt v, int i) {
		return new Volt(v.getVolts()/i);
	}

}
```

## object adapter -- encapsulation
```
public class SocketObjectAdapterImpl implements SocketAdapter{

	//Using Composition for adapter pattern
	private Socket sock = new Socket();
	
	@Override
	public Volt get120Volt() {
		return sock.getVolt();
	}

	@Override
	public Volt get12Volt() {
		Volt v= sock.getVolt();
		return convertVolt(v,10);
	}

	@Override
	public Volt get3Volt() {
		Volt v= sock.getVolt();
		return convertVolt(v,40);
	}
	
	private Volt convertVolt(Volt v, int i) {
		return new Volt(v.getVolts()/i);
	}
}
```

