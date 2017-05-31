# 23- Merge K Sorted Lists
## 题意
合并 K sorted lists。  sort(ListNode[] lists)

## 解题
两种方案， 第一种是merge sort， 第二种是 PQ

复杂度第一种为 nk logk， 第二种是 nklogk。 

虽然都一样， 但是Merge sort 要快得多。

## Merge Sort 细节。
这里需要注意的是， 返回的 是一个 listnode, 输入的是一个 listnode 的 array 。

所以， 在每一层中， **不需要额外复制Array**。

因为 array 的数据 没有在merge的时候被改变过。排序的部分为 lists of listnode， 而非 原array中的listnode 的 相对顺序。

### 什么情况下必须保留副本呢？

比如说合并两个Int array， 

merge (int[] a, int[] b）

如果没有复制一个副本的话 ， a , b 会在原 array上覆盖操作。

比如说， 如果记录 a, b 的开始和结束index， 虽然在合并当中， pointer 一直在走， 但是 原来的 array 中， 某一个位置的 值已经发生了变化， 所以必须保留一个副本！ 

