#State Design
##逻辑
当一个 object 根据 一个 Internal  state 而改变状态的时候， 我们可以使用 state design。

比如说一个遥控器， 当 On 的时候， 电视会打开， 当 off 的时候， 电视会关闭。 传统的做法是 用 If else 来判断。

更好的方法是 创立 一个 State 的 Interface， 然后分别为 on 和 off 实现这个 state's action。 然后将这个 state 传入 电视， 执行 这个 state 的 action。

遥控器的 State

```
public interface State {

	public void doAction();
}

public class TVStartState implements State {

	@Override
	public void doAction() {
		System.out.println("TV is turned ON");
	}

}

public class TVStopState implements State {

	@Override
	public void doAction() {
		System.out.println("TV is turned OFF");
	}

}
```

然后创建一个 TV， 来接受不同的状态， 然后根据状态， 做出改变。

**注意，在这里的实现中， 这个 TV 也是一个 state subclass, 因为这个电视也需要 do action， 只不过是执行的遥控器的 do action。**

```
public class TVContext implements State {

	private State tvState;

	public void setState(State state) {
		this.tvState=state;
	}

	public State getState() {
		return this.tvState;
	}

	@Override
	public void doAction() {
		this.tvState.doAction();
	}

}
```

最后的main function

```
public class TVRemote {

	public static void main(String[] args) {
		TVContext context = new TVContext();
		State tvStartState = new TVStartState();
		State tvStopState = new TVStopState();
		
		context.setState(tvStartState);
		context.doAction();
		
		
		context.setState(tvStopState);
		context.doAction();
		
	}

}
```
