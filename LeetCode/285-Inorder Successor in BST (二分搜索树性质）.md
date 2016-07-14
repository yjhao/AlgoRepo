#285-Inorder Successor in BST (二分搜索树性质）

##题意
找到比一个node在bst中的下一个更大的Node。

##解题-方案一（不是最优）
要找到下一个inorder 的 node， 我们可以用一个stack 来模拟 Inorder iterator, 然后要么找出 Node 右子数的最左子孙， 要么往 stack上回溯， 找到首个比node大的节点。

###细节
在加入 stack 之中， 不能在 while 中写 while (cur.val!=p.val)。 因为如果是这样的话， 如果 刚好第一个root就正好是p， 这个stack永远不可能装进其他 node 了。

这样的while, 可以保证 能够找到 目标节点的 Index， 比如在 linkedlist 之中使用。 但是这里的目的是**保存路径**， 所以不能这样使用。 正确的写法是 在 while 中 出现 cur=p 的时候实现 break。

##代码
```
public class Solution {
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if (root==null) return null;
        LinkedList<TreeNode> stack = new LinkedList<TreeNode>();
        TreeNode cur = root;
        
        while (root!=null){
            stack.push(root);
            if (root.val<p.val){
                root = root.right;
            }else if (root.val>p.val){
                root = root.left;
            }else{
                break;
            }
        }
        
        if (root==null)     return null;
        
        // check the most left child in the right tree;
        if (root.right!=null){
            root = root.right;
            TreeNode pre = root;
            while (root!=null){
                pre = root;
                root = root.left;
            }
            return pre;
        }else{
            stack.pop();
            if (stack.size()==0)    return null;
            
            TreeNode top = null;
            
            while (stack.size()>0){
                top = stack.pop();
                if (top.val>p.val)
                    return top;
            }
            
            return null;
        }
    }
}
```

##解题， 最优的方法
这个 bst 可以想象成一个 binary search。 越晚出现的 Node， 肯定与目标之间的差距越小。所以我们只需要做二分搜索， 然后返回最后一个比p大的 node 即为答案。 

相应的， 如果是找 precessor, 只需要返回最后一个比 P 小的 node。

###解释
比如a大于p, 那么下一步 往 a.left。 

但无论以后 出现的 node 的值相比 P 有多么的大， **也不会大于a**， 因为这是 bst 的性质决定的。 因为 a 左拐的时候， 就已经决定了之后的 subtree 的右边界 是在a。 

这样右边界可以层层缩小，所以 最后出现的 比 p 大的 node, 一定是大于p 中的最小的一个 node。

##代码
```
public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        TreeNode res = null;
        TreeNode cur = root;
        
        while (cur!=null){
            if (cur.val>p.val){
                res = cur;
                cur = cur.left;
            }else{
                cur = cur.right;
            }
        }
        return res;
    }
```