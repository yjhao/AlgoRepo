# Stratege Design
## 逻辑

SD 是 behavior design 中的一种。 当我们对于某个任务， 有多种不同算法， 然后用户决定使用哪种算法的时候， 我们就可以使用 stratege design

比如说付款， 可以使用 Credit card or paypal， 所以我们先implement 两种付款方式。

```
public interface PaymentStrategy {

	public void pay(int amount);
}

public class CreditCardStrategy implements PaymentStrategy {

	@Override
	public void pay(int amount) {
		System.out.println(amount +" paid with credit/debit card");
	}

}

public class PaypalStrategy implements PaymentStrategy {
	
	@Override
	public void pay(int amount) {
		System.out.println(amount + " paid using Paypal.");
	}

}
```

然后为了付一个 cart 中的钱， 我们实现一个 cart 和其中 的付款 pay method。 注意， 我们传入了一个 argument， 就是这个付款的interface。然后cart会根据这个付款的具体方法， 实现付款。

```
public class ShoppingCart {

	//List of items
	List<Item> items;
	
	public ShoppingCart(){
		this.items=new ArrayList<Item>();
	}
	
	public void addItem(Item item){
		this.items.add(item);
	}
	
	public void removeItem(Item item){
		this.items.remove(item);
	}
	
	public int calculateTotal(){
		int sum = 0;
		for(Item item : items){
			sum += item.getPrice();
		}
		return sum;
	}
	
	public void pay(PaymentStrategy paymentMethod){
		int amount = calculateTotal();
		paymentMethod.pay(amount);
	}
}
```

接下来是如何实现这个方法, 注意， 在 payr 中， 我们传入了两种不同的付款方式， 作为两种不同的算法。

```
public class ShoppingCartTest {

	public static void main(String[] args) {
		ShoppingCart cart = new ShoppingCart();
		
		Item item1 = new Item("1234",10);
		Item item2 = new Item("5678",40);
		
		cart.addItem(item1);
		cart.addItem(item2);
		
		//pay by paypal
		cart.pay(new PaypalStrategy("myemail@example.com", "mypwd"));
		
		//pay by credit card
		cart.pay(new CreditCardStrategy("Pankaj Kumar", "1234567890123456", "786", "12/15"));
	}

}
```

Stratege Design 和 State Design 非常相似， 都是在不同的算法中选择一个算法来进行实现。

但是最大的不同是， 在 Stratege Design 中， 算法是作为 一个 argument 传入， 在 cart 中没有存这个 算法 的 样本。 而在 state design 中， 在 Cart 中要存储这个算法的样本， 所以可以根据具体的情况在这个算法中， 选择不同的版本， 增加灵活性。