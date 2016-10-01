#Factory Design
##逻辑
如果根据Input的不同， 要返回各种的不同的 subclass，这个时候就最好使用 factory， 因为 factory 提供了 具体的 subclass 的实现， 和 建立 的分离， 和容易进行 debug 等操作。

首先建立一个 大类， 比如说computer，然后实体化各个类型的电脑， 作为subclass。

```
public abstract class Computer {
	
	public abstract String getRAM();
	public abstract String getHDD();
	public abstract String getCPU();
	
	@Override
	public String toString(){
		return "RAM= "+this.getRAM()+", HDD="+this.getHDD()+", CPU="+this.getCPU();
	}
}

public class PC extends Computer {

	private String ram;
	private String hdd;
	private String cpu;
	
	public PC(String ram, String hdd, String cpu){
		this.ram=ram;
		this.hdd=hdd;
		this.cpu=cpu;
	}
	@Override
	public String getRAM() {
		return this.ram;
	}

	@Override
	public String getHDD() {
		return this.hdd;
	}

	@Override
	public String getCPU() {
		return this.cpu;
	}

}
```

然后， 建立一个 factory， 根据输入的不同， 返回各种不同类型的 computer 子类。

在这个地方， 我们可以将这个 factory 实现为 singleton 的， 用 static 表示， 保证其只有一个 instance。

注意， 返回的类型是父类。

```
public class ComputerFactory {

	public static Computer getComputer(String type, String ram, String hdd, String cpu){
		if("PC".equalsIgnoreCase(type)) return new PC(ram, hdd, cpu);
		else if("Server".equalsIgnoreCase(type)) return new Server(ram, hdd, cpu);
		
		return null;
	}
}
```

factory 的缺点： 在 factory 中， 使用了 if else， 所以使用者必须知道 具体的 if else 的输入的情况， 才能得到相应的输出。
为了解决这个问题， 我们可以使用 abstract factory。