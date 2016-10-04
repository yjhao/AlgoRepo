#Thread Priority
```
final void setPriority(int level)
```
Level的值必须在MIN PRIORITY到MAX PRIORITY范围内。通常，它们的值分别是1和10。要返回一个线程为默认的优先级，指定NORM_PRIORITY，通常值为5。这些优先级在Thread中都被定义为final型变量。



```
final int getPriority( )
```