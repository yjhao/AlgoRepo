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