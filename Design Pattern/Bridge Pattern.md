#Bridge Pattern

##逻辑
如果我们有一个物体， 分别从多个 interface 继承， 我们就可以使用 Bridge pattern， 将他们链接在一起。

这个pattern的核心在于， Decouple 多个 interface 的行为。

在实现中， prefer Composition over inheritance.

##例子
一个物品， 有一个shape, 有一个颜色。

先来颜色的 interface 和实例。

```
public interface Color {

	public void applyColor();
}

public class RedColor implements Color{

	public void applyColor(){
		System.out.println("red.");
	}
}

public class GreenColor implements Color{

	public void applyColor(){
		System.out.println("green.");
	}
}
```

然后还有一个属性， 就是 shape。 在shape 中， 因为我们使用了 composition， 所以需要直接传入这个 color。

```
public abstract class Shape {
	//Composition - implementor
	protected Color color;
	
	//constructor with implementor as input argument
	public Shape(Color c){
		this.color=c;
	}
	
	abstract public void applyColor();
}
```

然后是 一些形状的 实例， 因为是 extends， 所以要 super（）。

```
public class Triangle extends Shape{

	public Triangle(Color c) {
		super(c);
	}

	@Override
	public void applyColor() {
		System.out.print("Triangle filled with color ");
		color.applyColor();
	} 

}

public class Pentagon extends Shape{

	public Pentagon(Color c) {
		super(c);
	}

	@Override
	public void applyColor() {
		System.out.print("Pentagon filled with color ");
		color.applyColor();
	} 

}
```

最后是一个 主程序， 在 形状中， 传入一个 color。

```
public class BridgePatternTest {

	public static void main(String[] args) {
		Shape tri = new Triangle(new RedColor());
		tri.applyColor();
		
		Shape pent = new Pentagon(new GreenColor());
		pent.applyColor();
	}

}
```
 