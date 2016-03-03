/*
Given a binary tree, find the largest subtree which is a Binary Search Tree (BST), where largest means subtree with largest number of nodes in it.

Note:
A subtree must include all of its descendants.
Here's an example:
    10
    / \
   5  15
  / \   \
 1   8   7
The Largest BST Subtree in this case is the highlighted one.
The return value is the subtree's size, which is 3.
*/

//================================================================
/**
Solution 2:
in brute-force solution, we get information in a top-down manner.
for O(n) solution, we do it in bottom-up manner, meaning we collect information during
backtracking.

example : 1-2-3-4-5-6
                 -8-9
if we use the recursion from top to bottom, then at 4, we do 2 calls, 456 and 489.
at 3, we do 3456 and 3489. thus it is O(nlogn).

But we can from bottom to top, VERY LIKE THE MOMORIZATION (we don't need to go down twice),
then at 4, we dont need to do any call, just collect the results from 56 and 89. At 3,
just collect previuos resutls. Thus the time complexity is O(n).

We use post-order to achieve this bottom-up effect.

for each node, we store information:
current subtree min val, current subtree max val, and the max BST node number up to now.

for each node, we compare if currennode value is larger than max of left subtree, and smaller
than min of the right subtree.
*/

//================================================================
/**
 * solution 1: like check valid BST, recursively call function at each node of the tree,
 * which will result in O(nlogn) time complexity.
 *
 * why is it O(nlogn): SIMILAR TO QUICK SORT AND MERGE SORT
 * In helper function, it visits every node; for every node, in the DFS function, it visits
 * all of its children. However, the entier children number of a node is reduced by half each
 * time, very like how the quicksort works. 1 + 1/2 + 1/2 + 1/4 + 1/4 + 1/4 + 1/4.
 */


public class Solution {
    class result{
        int min;   // the min value of this subtree
        int max;   // the max value of this subtree
        int treesize; // the total count of BST so far
        public result(int min, int max, int treesize){
            this.min = min; this.max = max; this.treesize = treesize;
        }
    }
    // solution 2.
    public int largestBSTSubtree2(TreeNode root) {
        if (root==null) return 0;
        if (root.left == null && root.right==null)  return 1;

        result base = new result(0,0,0);
        post_order(root, base);
        return base.treesize;
    }

    public boolean post_order(TreeNode root, result cur){
        if (root==null) return true;
        result left = new result(0,0,0);
        result right = new result(0,0,0);
        boolean l = post_order(root.left, left);
        boolean r = post_order(root.right, right);

        if (l && r && (root.left==null || root.val>left.max) && (root.right==null || root.val < right.min)){
            cur.treesize = left.treesize + right.treesize + 1;
            if (root.left==null) cur.min = root.val;
            else cur.min = left.min;
            if (root.right==null)   cur.max = root.val;
            else cur.max = right.max;
            return true;
        }else{
            cur.treesize = left.treesize>right.treesize ? left.treesize:right.treesize;
            return false;
        }
    }

    // solution 1.
    public int largestBSTSubtree1(TreeNode root) {
        if (root==null) return 0;
        if (root.left==null && root.right==null)    return 1;
        int[] res = {0};

        helper(root, res);
        return res[0];
    }

    public void helper(TreeNode root, int[] res){
        if (root==null)     return;

        int curSum = DFS(root, Long.MIN_VALUE, Long.MAX_VALUE);
        if (curSum!=-1){
            res[0] = Math.max(res[0], curSum);
        }

        helper(root.left, res);
        helper(root.right, res);
    }

    public int DFS(TreeNode root, Long min, Long max){
        if (root==null) return 0;

        if (root.val<=min || root.val>=max) return -1;

        int left = DFS(root.left, min, Long.valueOf(root.val));
        if (left==-1)   return -1;

        int right = DFS(root.right, Long.valueOf(root.val), max);
        if (right==-1)  return -1;

        return 1+left+right;
    }
}
