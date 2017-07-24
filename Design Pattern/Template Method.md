# Template Method
## 逻辑
比如说修房子， 有一定的顺序， 先修地基， 然后是柱子， 然后是墙壁。 虽然在每一步骤中， 各个房子的细节不一样。

所以为了规范这个顺序， 我们可以用一个template来规范， 各个房子具体的执行中， 可以implement各个细节。

因为我们要规范这个顺序， 所以不能用Interface。 因为有一些method没有implement， 所以我们得使用abstract class。 

因为这个顺序我们不希望被改变， 所以规范这个顺序的方法， 我们使用 final 来修饰。


这个template：

```
public abstract class HouseTemplate {

	//template method, final so subclasses can't override
	public final void buildHouse(){
		buildFoundation();
		buildPillars();
		buildWalls();
		buildWindows();
		System.out.println("House is built.");
	}

	//default implementation
	private void buildWindows() {
		System.out.println("Building Glass Windows");
	}

	//methods to be implemented by subclasses
	public abstract void buildWalls();
	public abstract void buildPillars();

	private void buildFoundation() {
		System.out.println("Building foundation with cement,iron rods and sand");
	}
}
```

某一个房子

```
public class WoodenHouse extends HouseTemplate {

	@Override
	public void buildWalls() {
		System.out.println("Building Wooden Walls");
	}

	@Override
	public void buildPillars() {
		System.out.println("Building Pillars with Wood coating");
	}

}
```