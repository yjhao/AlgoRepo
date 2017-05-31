## 272-Closest Binary Search Tree Value II （Inorder 截枝）（tree 前驱节点， 后驱节点）

## 题意
在一个二分查找树中， 相对一个target, 找出最相近的 k 个数

## 解题， 方法一， O(n)
第一种方法为 inorder traversal。

因为找到的值是从小到大排列的， 所以我们可以利用这一点。 

最开始，当 list.size 小于 k 的时候， 我们把所有 node.val 都放进去。这样的话， 我们初始化了一个 size 为 K 的list。 从这之后的每一步， 如果下一个值 比最差的 更优的， 只需要把它加入， 并且扔掉最差的答案就可以了。

当 list.size 大于等于 K 的时候， 我们就要取舍 现在遇到的这个value是不是更优的选项。

根据数字都是递增的， 我们可以判断， 如果当前值 离 目标的距离， 比list 中第一个值离目标的距离更近， 那么：

1. 肯定应该加入当前值
2. 目标值肯定不在 list 第一个值的左面， 也不会在 list 中靠近第一个值得一半。 也就是说， List 中的所有值中， 距离离目标最远的肯定是 list 的第一个值。所以把它remove。

如果我们发现现在的值 离 目标的距离， 比list 中第一个值离目标的距离更远的时候， 说明我们已经走远了。。。 以后的值更不能更优， 这个时候， 直接返回。 **截枝**

复杂度 O(n)

## 代码
```
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Solution {
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        List<Integer> res = new ArrayList<Integer>();
        int[] count = {k};
        Inorder(root, target, res, count);
        return res;
    }
    
    public void Inorder(TreeNode root, double target, List<Integer> res, int[] count){
        if (root==null) return;
        
        Inorder(root.left, target, res, count);
        
        if (count[0]>0){
            res.add(root.val);
            count[0]--;
        }else{
            if (Math.abs(res.get(0)-target)>=Math.abs(root.val-target)){
                res.remove(0);
                res.add(root.val);
            }else{
                return;
            }
        }
        
        Inorder(root.right, target, res, count);
    }
}
```

## 方法二， O(klogn)
模拟寻找这个target的过程， 从root开始一直往下，直到遇到 null， 会形成一个path。 使用两个stack 分别存储两个东西。 

如果一个node的值比 目标大， 放入**后驱**这个stack中； 反之， 放入**前驱**这个stack中。

然后从 stack 中取出node， 如果取出的是 后驱， 这个节点的左子孙肯定已经在 Stack 中了， 所以我们不用考虑， 反而考虑 后驱节点的 右子孙 的 所有左子孙， 把它的右子孙和其所有左子孙全部加入 后驱 stack 中。

相似的操作， 可以对 前驱节点进行。

那么问题是， 为什么 stack 后放入的一定比前放入的距离更小呢？

问题可以这样考虑， 二分查找， node与目标的距离， 相比查找的最开始， 肯定是越来越小的。

如果这个值出现过， 那么它会被放在其中一个 Stack 上， 然后搜索过程中剩余的所有node都会被放在 另外一个  Stack 上， 因为一旦在这个点向左（右）， 那么剩下的所有制都会比这个Node小（大）。

最后加入list的时候， 会比较两个stack 的 top， 这个时候， 距离最小的肯定会被发现。

如果这个值没有出现过， 那么它的值一定会 在一根 左-右， 或者 右-左 的线上， 所以它的 上一个节点 和 下一个节点一定会是在两个 stack 上。 同上的理论， 下一个节点的所有子孙节点都只会在相同的 stack 上。 最后一比较两个stack 的 top， 最近的那个就可以找出来。

复杂度 O(klogn) , 取决于 k 的大小 和 n 的关系。

## 代码
https://leetcode.com/discuss/71820/java-5ms-iterative-following-hint-o-klogn-time-and-space

```
public class Solution {
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        List<Integer> result = new LinkedList<Integer>();
        // populate the predecessor and successor stacks 
        Stack<TreeNode> pred = new Stack<TreeNode>();
        Stack<TreeNode> succ = new Stack<TreeNode>();
        TreeNode curr = root;
        while (curr != null) {
            if (target <= curr.val) {
                succ.push(curr);
                curr = curr.left;
            } else {
                pred.push(curr);
                curr = curr.right;
            }
        }
        while (k > 0) {
            if (pred.empty() && succ.empty()) {
                break; 
            } else if (pred.empty()) {
                result.add( getSuccessor(succ) );
            } else if (succ.empty()) {
                result.add( getPredecessor(pred) );
            } else if (Math.abs(target - pred.peek().val) < Math.abs(target - succ.peek().val)) {
                result.add( getPredecessor(pred) );                    
            } else {
                result.add( getSuccessor(succ) );
            }
            k--;
        }
        return result;
     }

    private int getPredecessor(Stack<TreeNode> st) {
        TreeNode popped = st.pop();
        TreeNode p = popped.left;
        while (p != null) {
            st.push(p);
            p = p.right;
        }
        return popped.val;
    }

    private int getSuccessor(Stack<TreeNode> st) {
        TreeNode popped = st.pop();
        TreeNode p = popped.right;
        while (p != null) {
            st.push(p);
            p = p.left;
        }
        return popped.val;
    }
}
```





