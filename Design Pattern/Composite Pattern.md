#Composite Pattern
##逻辑
我们有一些东西， 然后要对每个东西做出相同的操作， 我们就可以用composite pattern， 关键的关键就是在于， 在这个集合中的每一个物品， behave as a single object。

可以被用来构造 tree structure。

##例子
有一些图形， 圆形 或者 方形， 然后要上颜色， 每一个颜色都被上在了所有的Object上， 所以可以使用 composite pattern

首先是图形interface， 和每一个图形

```
public interface Shape {
	
	public void draw(String fillColor);
}

public class Triangle implements Shape {

	@Override
	public void draw(String fillColor) {
		System.out.println("Drawing Triangle with color "+fillColor);
	}

}

public class Circle implements Shape {

	@Override
	public void draw(String fillColor) {
		System.out.println("Drawing Circle with color "+fillColor);
	}

}
```

然后建立一个集合， 应该有加入图形， 和删除图形的方法。因为这个几个有 draw 这个方法， 所以可以从interface继承过来。

当使用了 draw 这个方法之后， 集合中的所有图形都被涂上了相同的颜色。

```
public class Drawing implements Shape{

	//collection of Shapes
	private List<Shape> shapes = new ArrayList<Shape>();
	
	@Override
	public void draw(String fillColor) {
		for(Shape sh : shapes)
		{
			sh.draw(fillColor);
		}
	}
	
	//adding shape to drawing
	public void add(Shape s){
		this.shapes.add(s);
	}
	
	//removing shape from drawing
	public void remove(Shape s){
		shapes.remove(s);
	}
	
	//removing all the shapes
	public void clear(){
		System.out.println("Clearing all the shapes from drawing");
		this.shapes.clear();
	}
}
```
