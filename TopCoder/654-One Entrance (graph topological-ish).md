#654-One Entrance （graph topological-ish)

##题意
给n个房间， 这n个房间组成了一个tree， 然后有一个入口。

现在往这n个房间里面搬家具，从入口出发。 最终的要求是每一个房间都有一个家具。

限制条件是， 如果某个房间放了一个家具， 那么另外一个家具就不能穿过这个房间到达更远的房间。

问题是 一共有多少种不同的 房间排序方法， 可以一一放家具之后， 最后达到条件的要求。 房间最多9个

##解题
9的全排列有 362880 个， 所以对于每一个房间的排列方式， 检测是否合格。

如果某个房间排在某个位置的话， 说明其下面的 child 都已经放满了。如果下面还没有满， 说明如何这个房间排在这里肯定是错误的。

我们从第一个房间出发。

第一个房间肯定是一个 leaf child， 如果 edge num 不为一的话， 直接返回false。

这个房间放满之后， 他唯一的 neighbor， 也就是parent， 现在就不再需要考虑这个分支了。

如何告知 它的 parent 不需要再考虑这个分支了呢？ 我们可以将 parent 的 edge num 减一。 经过更新之后， 如果某个房间的 edge num 为一了， 说明现在他成了 leaf node，可以放家具了。 否则如果家具放在一个不是leaf node的地方， 会堵塞下面的房间。

