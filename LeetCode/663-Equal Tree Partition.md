# 663-Equal Tree Partition

## 题意
给一颗树， 问能不能砍断一个edge， 然后使两个subtree的总和相同。

## 解题
题目其实就是就是， 是否找得到一颗 subtree， 他的和正好是总和的一半。

题目没有什么难点， 但特别应该注意的是， 检验subtree的时候 (left or right)， 一定要保证， 这颗subtree不能为null。

相似的， 如果不看 left or right， 而是要看 root.val + left + right 是否为总和的一半的话， 一定要保证， root不能是这颗树的root， 不然的话， 如果总和为0的话， 那么0的一半正好也是0。 所以他刚好满足条件， 而他却并不是一个子树。

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
class Solution {
    public boolean checkEqualTree(TreeNode root) {
        int totalSum = getSum(root);
        if (totalSum % 2==1)    return false;
        boolean[] res = {false};
        helper(root, res, totalSum);
        return res[0];
    }
    
    int helper(TreeNode root, boolean[] res, int totalSum){
        if (root==null) return 0;
        if (res[0]) return 0;
        
        int left = helper(root.left, res, totalSum);
        int right = helper(root.right, res, totalSum);
        
        if (left*2 == totalSum && root.left!=null || right*2 == totalSum && root.right!=null){
            res[0] = true;
            return 0;
        }
        
        return root.val + left + right;
    }
    
    int getSum(TreeNode root){
        if (root==null) return 0;
        return root.val + getSum(root.left) + getSum(root.right);
    }
}
```