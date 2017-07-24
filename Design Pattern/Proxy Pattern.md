# Proxy Pattern

## 逻辑
如果我们想实现一个 可以控制 的功能， 我们就可以使用proxy pattern。比如说 只能让管理员 run command， 

主要的核心就是在于 **封装**。

我们将这些方法封装在一个 类 中， 当确认是管理员之后， 我们实现一个这样的类的instance， 然后使用这个 类 的方法来实现 run command。

这个类可以有很多方法实现， 可以用interface, 也可以用一个 class 来封装。

实质就是， 我们是需要用 inheritance 还是 encapsulation。 is-a vs has-a。 多数情况下， 我们倾向于 has-a。 因为可以实现更多的控制， 没有这么多的条条框框。

## 例子
首先实现一个 可以 run command 的类

```
public interface CommandExecutor {

	public void runCommand(String cmd) throws Exception;
}

public class CommandExecutorImpl implements CommandExecutor {

	@Override
	public void runCommand(String cmd) throws IOException {
                //some heavy implementation
		Runtime.getRuntime().exec(cmd);
		System.out.println("'" + cmd + "' command executed.");
	}

}
```

然后再检查一下， 是否为 管理员， 如果是的话， 实现这个类。

```
public class CommandExecutorProxy implements CommandExecutor {

	private boolean isAdmin;
	private CommandExecutor executor;
	
	public CommandExecutorProxy(String user, String pwd){
		if("Pankaj".equals(user) && "J@urnalD$v".equals(pwd)) isAdmin=true;
		executor = new CommandExecutorImpl();
	}
	
	@Override
	public void runCommand(String cmd) throws Exception {
		if(isAdmin){
			executor.runCommand(cmd);
		}else{
			if(cmd.trim().startsWith("rm")){
				throw new Exception("rm command is not allowed for non-admin users.");
			}else{
				executor.runCommand(cmd);
			}
		}
	}

}
```

主程序

```
public class ProxyPatternTest {

	public static void main(String[] args){
		CommandExecutor executor = new CommandExecutorProxy("Pankaj", "wrong_pwd");
		try {
			executor.runCommand("ls -ltr");
			executor.runCommand(" rm -rf abc.pdf");
		} catch (Exception e) {
			System.out.println("Exception Message::"+e.getMessage());
		}
		
	}

}
```
