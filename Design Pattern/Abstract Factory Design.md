# Abstract Factory Design
## 逻辑
很相似与 factory design。但是不会再有 if else， 而是根据 Input factory class， 直接返回相应的 subclass。

首先， 还是 computer superclass and one subclass as an instance。

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
    ...
}

public class Server extends Computer {
    ...
}
```

接下来， 建立一些 实体。

在 factory pattern 中， 我们采用 if else。 而在这里， 我们采用的是 factory of factory。

首先为各个实例创建分别的 factory， 然后再创建一个总的 factory， 用这个总的factory 来 call 各个小的 factory， 来创建实例。

首先创建一个小的factory interface， 因为要用大的factory call小的factory， 所以将其变为一个interface。

```
public interface ComputerAbstractFactory {

	public Computer createComputer();

}

public class PCFactory implements ComputerAbstractFactory {
	@Override
	public Computer createComputer() {
		return new PC(ram,hdd,cpu);
	}

}

public class ServerFactory implements ComputerAbstractFactory {	
	@Override
	public Computer createComputer() {
		return new Server(ram,hdd,cpu);
	}

}
```

然后再建立一个大的 factory， 来使用各个小的 factory

```
public class ComputerFactory {

	public static Computer getComputer(ComputerAbstractFactory factory){
		return factory.createComputer();
	}
}
```

在使用中， 在 大的 factory 中的 argument 中， 传入一个小的factory。

```
public class TestDesignPatterns {

	public static void main(String[] args) {
		testAbstractFactory();
	}

	private static void testAbstractFactory() {
		Computer pc = ComputerFactory.getComputer(new PCFactory("2 GB","500 GB","2.4 GHz"));
		Computer server = ComputerFactory.getComputer(new ServerFactory("16 GB","1 TB","2.9 GHz"));
	}
}
```