#294-E A and B and Lecture Rooms (Graph, Tree, LCA)

## 题意
给出一颗n个节点的树，有m次查询，每次查询给出两个节点， 求一共有多少个节点到这两个节点的距离相等。

## 解题
如果在每次查询的时候暴力求解， 肯定超时。

正确的方法， 跟LCA有关系。 最低共同祖先。

首先求得 从a到b的最短距离。 这么多次查询， 难道每次都是dfs么？

不需要!
```
dist(a,b) = depth(a) + depth(b) - 2*depth(LCA(a,b))
```

预处理的时候， 从任何一个点开始dfs, 并记录下每个点的 **深度** 和祖先（ 为了查找祖先方便， 祖先可以根据层数放入hashmap， 或者 “binary raises"， 记录上 1，2，4，8，。。个祖先）， 以及子孙数目。

## 如何快速求LCA
如果 当前节点aa 的深度大于 当前节点bb 的深度， 那么因为最后 aa bb都会上溯到同一个节点 （深度相同）， 所以把 aa 变为 aa 的父亲。 然后继续比较。

当然， 可以用 hashmap 或者 binary raises 进行二分搜索， 复杂度变为 logn

## 得到dist(a,b)之后
如果 dist(a,b) 为偶数， 那么显然不会有答案。

最后的答案与 mid(a,b) 和 lca(a,b) 的相对位置有关系。

1. 如果 mid(a,b) == lca(a,b).
2. mid(a,b) 靠近 a 一侧。
3. mid(a,b) 靠近 b 一侧。

http://www.ithao123.cn/content-9336331.html

###抓住一个要点, Mid
如果是情况一的话， 那么 (mid=) lca(a,b) 的所有“向上的子孙”（最高处是预处理dfs的开始节点） 到 a,b 的距离都相等。所以需要找到 lca 下一层分别靠近 a , b 的节点， 用总结点数 减去 这两个节点的 子孙 就是答案。

```
    o
    |
    |
    lca
   /   \
  o     o
 /       \
a         b
```


如果中点不是 lca，mid 的所有“非 a b 子孙的 子孙” 到 a,b 的距离都相等。 怎么求到个数呢？

num[mid] - nums[L] 即可！ 最后的答案即为 mid 和 mid 向下这条线上的节点数


```
    o
    |
    |   mid L
a---o---o---o---b
   lca  |   |
	    |   |
	    |   |
	    |   |
	    ...
```



