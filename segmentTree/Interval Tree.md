#Interval Tree

## Interval tree
很方便处理 一个 Interval, 比如 【0，29】, 与之前的Interval有没有重合的。 或者找出重合部分的大小。 单次的时间复杂度为 O(logn)

http://www.geeksforgeeks.org/interval-tree/

## 如何加入interval， 如何查找overlap
每一个 Node 存两个变量， 一个是 Interval [low, high]， 一个是所有以 本 node  为 root 的interval的最大值 max。

加入新的interval， 根据 low 的大小， 像 bst 一样进行插入。

当检查一个新的 interval i 与之前的 Interval root 有没有重合的时候。 当发生重合了， root.low <= i.high && root.high >= i.low。

如果 Root 左孩子不为空， 而且 root.left.max > i.low, 我们就往左边走， 反之往右走。

“正确性” ： 如果 左面找到了一个overlap， 达到目的， 很好。。。

如果左面没有找到这个overlap, 那么右面更不可能出现这个 overlap, 因为 右面的 所有 low 都是大于 左面的 所有 low 的， 所以如果左面都没有overlap， 那么右面更不可能出现了。
