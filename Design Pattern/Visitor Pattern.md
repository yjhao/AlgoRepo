#Visitor Pattern
##逻辑

如果我们必须对某一个 group 中的物品依次做出一些operation， 我们就可以使用 visitor pattern

visitor pattern 的好处是， 在实现某一个物品的时候， 不需要考虑 这个操作的 逻辑， 只需要把自己 传给这个 operation， 然后用这个Operation来判断自己应该被怎么操作。

在opearation 中， 对于每一个不同的物品种类， 实现一个方法 （overload)， 来进行操作。

下面我们来看一个例子， 计算书本和水果的 折扣之后的价格。 折扣会根据书本和水果 而不同。

我们先看看 物品的interface 和 实现。主要注意的是， accept这个方法， call visitor 的 visit 方法， 但是要传入自己作为变量， 在visitor中进项逻辑的判断。

```
public interface ItemElement {

	public int accept(ShoppingCartVisitor visitor);
}

public class Book implements ItemElement {

	private int price;
	private String isbnNumber;
	
	public Book(int cost, String isbn){
		this.price=cost;
		this.isbnNumber=isbn;
	}
	
	public int getPrice() {
		return price;
	}

	public String getIsbnNumber() {
		return isbnNumber;
	}

	@Override
	public int accept(ShoppingCartVisitor visitor) {
		return visitor.visit(this);
	}

}

public class Fruit implements ItemElement {
	
	private int pricePerKg;
	private int weight;
	private String name;
	
	public Fruit(int priceKg, int wt, String nm){
		this.pricePerKg=priceKg;
		this.weight=wt;
		this.name = nm;
	}
	
	public int getPricePerKg() {
		return pricePerKg;
	}


	public int getWeight() {
		return weight;
	}

	public String getName(){
		return this.name;
	}
	
	@Override
	public int accept(ShoppingCartVisitor visitor) {
		return visitor.visit(this);
	}

}
```

下面我们来看看 visitor  的 interface 和实例化。注意， 对于每一个不同的物品，有不同的逻辑。

```
public interface ShoppingCartVisitor {

	int visit(Book book);
	int visit(Fruit fruit);
}

public class ShoppingCartVisitorImpl implements ShoppingCartVisitor {

	@Override
	public int visit(Book book) {
		int cost=0;
		//apply 5$ discount if book price is greater than 50
		if(book.getPrice() > 50){
			cost = book.getPrice()-5;
		}else cost = book.getPrice();
		System.out.println("Book ISBN::"+book.getIsbnNumber() + " cost ="+cost);
		return cost;
	}

	@Override
	public int visit(Fruit fruit) {
		int cost = fruit.getPricePerKg()*fruit.getWeight();
		System.out.println(fruit.getName() + " cost = "+cost);
		return cost;
	}

}
```

下面是 一个 main  函数。对于每一个 物品， call accept 方法， 然后在 accept中， 访问 visitor， 进行操作。

```
public class ShoppingCartClient {

	public static void main(String[] args) {
		ItemElement[] items = new ItemElement[]{new Book(20, "1234"),new Book(100, "5678"),
				new Fruit(10, 2, "Banana"), new Fruit(5, 5, "Apple")};
		
		int total = calculatePrice(items);
		System.out.println("Total Cost = "+total);
	}

	private static int calculatePrice(ItemElement[] items) {
		ShoppingCartVisitor visitor = new ShoppingCartVisitorImpl();
		int sum=0;
		for(ItemElement item : items){
			sum = sum + item.accept(visitor);
		}
		return sum;
	}

}
```
