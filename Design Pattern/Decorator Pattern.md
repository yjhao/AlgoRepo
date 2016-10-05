#Decorator Pattern
##逻辑
如果有一个蛋糕， 然后有很多种属性， 都可以在 run time 加在这个蛋糕上， 比如说 是 红色的啊， 是方形的， 是高的是矮的， 就可以用这个decorator pattern 来实现， 不然的话， 如果采用constructer来实现的话， 就会很混乱。 而且如果还要加上另外一个属性， 就会重现新建一个 constructer。

##例子，步骤
我们用形状来举例子， 有很多种形状， 不过都是从基本的形状引申开来的。

首先要有一个 形状 的 interface， 然后为每一个属性， 实现这个interface。

```
public interface Shape {
   void draw();
}

public class Rectangle implements Shape {

   @Override
   public void draw() {
      System.out.println("Shape: Rectangle");
   }
}

public class Circle implements Shape {

   @Override
   public void draw() {
      System.out.println("Shape: Circle");
   }
}

```

然后我们需要建立一个 decorator class， 所有的其他的 decorator 都是建立在它的基础上。 如果有了它， 要加什么属性， 直接新建一个 Decorator 就可以了。

在这个 base class 里面， 我们需要传入一个基本的形状。 其他的形状都是在它的基础上 extend 的。

因为这个 shape 本身应该被其所有的子孙都可以使用， 所以在这里将它设置为 protected。然后传入一个 基本 的shape。

注意， 在这里， draw 中执行的是  当前Decorateshape 的 draw 的方法， 所以这里就实现了多态， 子孙在draw中， 在做出添加之前， 只需要 call super.draw(shape)， 就一定会先实现之前的基础形状应该有的 draw。

```
public abstract class ShapeDecorator implements Shape {
   protected Shape decoratedShape;

   public ShapeDecorator(Shape decoratedShape){
      this.decoratedShape = decoratedShape;
   }

   public void draw(){
      decoratedShape.draw();
   }	
}
```

如果我们现在想将这个 shape 染成红色的， 只需要建立一个 extends a decorator 在原基础上染色。 因为实在原基础上， 所以我们要使用 super constucter， 这也是为什么我们要将原来的 shape 设置为 private。

**在这里 原来的 Decorateshape 经过了 Constucter， 返回的是当前的新 redShapeDecorator。所以在 Call draw 的时候， 就实现了多态**

在方法中， 也要引用原方法的相同方法， 因为是在原方法的基础上的。

```
public class RedShapeDecorator extends ShapeDecorator {

   public RedShapeDecorator(Shape decoratedShape) {
      super(decoratedShape);		
   }

   @Override
   public void draw() {
      decoratedShape.draw();	       
      setRedBorder(decoratedShape);
   }

   private void setRedBorder(Shape decoratedShape){
      System.out.println("Border Color: Red");
   }
}
``` 

下面是一个主函数。先建立了一个 基本的形状， 然后用一个 Decorator 在基本的形状上做出改变。


```
public class DecoratorPatternDemo {
   public static void main(String[] args) {

      Shape circle = new Circle();

      Shape redCircle = new RedShapeDecorator(new Circle());

      Shape redRectangle = new RedShapeDecorator(new Rectangle());
      System.out.println("Circle with normal border");
      circle.draw();

      System.out.println("\nCircle of red border");
      redCircle.draw();

      System.out.println("\nRectangle of red border");
      redRectangle.draw();
   }
}
```

##例子二， 层层添加
在这里， 先实现了一个 基本的 car

```
public interface Car {

	public void assemble();
}

public class BasicCar implements Car {

	@Override
	public void assemble() {
		System.out.print("Basic Car.");
	}

}
```

 然后是一个 carDecorator， 注意 assemble中， call 的是 this.car.assemble， 实现了多态
 
 
```
 public class CarDecorator implements Car {

	protected Car car;
	
	public CarDecorator(Car c){
		this.car=c;
	}
	
	@Override
	public void assemble() {
		this.car.assemble();
	}

}
```

然后是一些 添加的属性， 在基础的 Decorator上添加， 所以用 extends， 而且 assemble中， 都是先call了 super.assemble， 回到了 basic 的 decorator， 在basic的decorator中， 根据多态性来执行应该有的行为。

这样做的话， 可以实现 嵌套， 比如说

```
Car sportsLuxuryCar = new SportsCar(new LuxuryCar(new BasicCar()));
sportsLuxuryCar.assemble();
```

sports car CALL super --> luxury call CALL super --> basic car


就先实现了basic car assemble， 然后是 luxury car assemble， 最后才是 sportscar assmeble。 有点像recursion。

```
public class SportsCar extends CarDecorator {

	public SportsCar(Car c) {
		super(c);
	}

	@Override
	public void assemble(){
		super.assemble();
		System.out.print(" Adding features of Sports Car.");
	}
}

public class LuxuryCar extends CarDecorator {

	public LuxuryCar(Car c) {
		super(c);
	}
	
	@Override
	public void assemble(){
		super.assemble();
		System.out.print(" Adding features of Luxury Car.");
	}
}
```
主函数

```
public class DecoratorPatternTest {

	public static void main(String[] args) {
		Car sportsCar = new SportsCar(new BasicCar());
		sportsCar.assemble();
		System.out.println("\n*****");
		
		Car sportsLuxuryCar = new SportsCar(new LuxuryCar(new BasicCar()));
		sportsLuxuryCar.assemble();
	}

}
```

最后的输出

```
Basic Car. Adding features of Sports Car.
*****
Basic Car. Adding features of Luxury Car. Adding features of Sports Car.
```