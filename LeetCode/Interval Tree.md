#Interval Tree

类似 bst， 按照 interval.start 来排序， 每一个node还要存 当前node 的子孙中， 最大的 interval.end


##对一个新的 interval x, 判断是否有overlap， 如果有overlap的话， 返回其中一个overlap的interval。
分三个情况：

Interval overlappingIntervalSearch(root, x)

1) If x overlaps with root's interval, return the root's interval.

2) If left child of root is not empty and the max  in left child 
is greater than x's low value, recur for left child

3) Else recur for right child.


---
稍微解释一下：

**Case 1**: When we go to right subtree, one of the following must be true.

a) There is an overlap in right subtree: This is fine as we need to return one overlapping interval.

b) There is no overlap in either subtree: We go to right subtree only when either left is NULL or maximum value in left is smaller than x.low. So the interval cannot be present in left subtree.

**Case 2**: When we go to left subtree, one of the following must be true.

a) There is an overlap in left subtree: This is fine as we need to return one overlapping interval.

b) There is no overlap in either subtree, especially in the right subtree.

Why? Because if there is no overlap in the left subtree and x.low < root.left.max, which means x.high **must be smaller than one of the interval's start in the left subtree**. However, the interval tree are sorted by the start, so all the interval's start in the right subtree is larger than x.high. Thus there won't be any overlap in the right subtree.

time complexity o(logn).


## What about find ALL ovetlaps for a certain interval?
The time complexity would be O(n). Because when we go to left subtree, there is no gurantee that there is no overlap in the right subtree.


## What about find an overlap interval just for a single val q
This could be done in Log(n) time complexity.

1. First check if q is contained in root.
2. Next we check if q is in the left side of the root.low. If so, we go to left subtree. We don't need to go right subtree because the interval start are sorted, q cannot be contained in any interval in the right subtree.
3. What if q is on the right side of the root.low?

	1). if max(left(root)) > q, then there must be a interval in the left subtree that contains q. Because there will be a interval whose start < root.start and whose end > q.
	2). if max(left(root)) < q, then there must not be an overlap in the left subtree, thus go left.
	

At each step, we only go either left or right, thus the time complexity is O(logn).

http://www.bowdoin.edu/~ltoma/teaching/cs231/fall07/Lectures/augtrees.pdf 