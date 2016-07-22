#Construct BST from given preorder traversal， 限制 DFS 深度来构造树
{10, 5, 1, 7, 40, 50}

##方法一。 截断区域
第一个肯定是root， 第二个肯定是root.left， 然后第一个比root大的肯定是root.right。 使用这样的逻辑， 可以将array分成三部分， idx, idx+1 ~ right-1, right ~ end。 所以可以递归下去。

但是为了找到第一个比root大的值， 每次都需要traverse， 所以average case time complexity is o(nlog）， like mergesort, and the worst case is o(n^2) when the tree is skewd。

不过， 可以预处理一下， 对每一个数字， 找到下一个比其大的数字的index， 这样的话， 复杂度就是 O(n) 了。 不过如何在 o(n) 内找到每一个数的下一个比其大的数？？


##方法二， 限制 DFS 深度。
可以模仿一个普通的 pre order traversal， 不过加入 min max 来限制区间， 如果当前 node[idx] 的值不在这个区间内， 就return null, 否则返回一个node, 并步进 node.idx。

这样的方法， 很类似于 “Convert Sorted List to Binary Search Tree”

限制 DFS 深度， 如果达到深度了， 就返回。
