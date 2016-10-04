# Stack and Heap

## 
每一个thread都有自己的 stack, 不能访问别人的stack。

所有的内容， 不论是 local 的还是全局的， 都放在 heap上。

为了加快速度， heap上的内容常常会被转移到stack上。

但是有时候会转移掉多个thread都需要的内容。‘

所以这个时候， volatile就派上了用场。