# 662- Maximum Width of Binary Tree

## 题意
给一颗树， 求在其中的， 在同一层的Node中， 最大的宽度。

## 解题
我最开始的方法是， 求出总的height， 然后以最下面的那一层的Node为基准， 画上格子， 每一个left right二分的时候， 就是在格子的mid 和 right idx之间二分。 其实的root 是在格子的最中间。 然后每一层， 单位长度对应的格子个数也是不一样的， 这样的话， 就可以求出在每一层的宽度了。

## 解题二
更好的方法是， 我们可以用Idx来表示一个Node， 而且这个idx对于某一个node是不会随着tree的其他部分的形状发生变化的， left Idx = idx*2, rigth Idx = left Idx + 1。 

为什么不会随着tree的其他部分的形状发生变化呢？ 因为这个方程是一定的， 所以只要他的parent的idx是一定， 他的idx就一定是一定的， 最后base情况下， 因为root的idx是一定的 （为1）， 所以可以用一个idx来表示一个node的位置。

然后因为Idx是 full tree 情况下的， 所以直接用idx相减就可以求出在每一层的宽度了。

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
    public int widthOfBinaryTree(TreeNode root) {
        int height = getHeight(root);
        if (height==0) return 0;
        HashMap<Integer, Integer> minMap = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> maxMap = new HashMap<Integer, Integer>();
        
        helper(root, minMap, maxMap, 0, (int)Math.pow(2, height)-1, 0);
        int res = 1;
        
        for (Integer level : minMap.keySet()){
            if (minMap.get(level) == maxMap.get(level)) continue;
            int unit = (int)Math.pow(2, height) /(int)Math.pow(2, level);
            int space = (maxMap.get(level) - minMap.get(level)) / unit;
            res = Math.max(res, space+1);
        }
        return res;
    }
    
    void helper(TreeNode root, HashMap<Integer, Integer> minMap, HashMap<Integer, Integer> maxMap, int left, int right, int level){
        if (root==null) return;
        int mid = left+(right-left)/2;
        minMap.put(level, Math.min(minMap.getOrDefault(level, Integer.MAX_VALUE), mid));
        maxMap.put(level, Math.max(maxMap.getOrDefault(level, -1), mid));
        
        helper(root.left, minMap, maxMap, left, mid-1, level+1);
        helper(root.right, minMap, maxMap, mid+1, right, level+1);
    }
    
    int getHeight(TreeNode root){
        if (root==null) return 0;
        return 1 + Math.max(getHeight(root.left), getHeight(root.right));
    }
}
```