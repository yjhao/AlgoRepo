# 652-Find Duplicate Subtrees

## 题意
给一颗树， 找出其中的相同的subtree， 并且返回这个subtree的root。

## 解题。

肯定是使用hash的方法来表示一个tree。 但是需要注意的是， **一定要使用 pre order 来表示这个tree， 这个tree才能是一个唯一的， 不能用其他的顺序来表示， 不然会有多个树对应于一个hash**

另外需要注意的是， 为了避免重复加入一个node， 所以当这个node已经被加入了， 我们应该更新这个map， 表示这个tree已经被考虑过了。

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
    String empty = "$";
    String divide = "%";
    public List<TreeNode> findDuplicateSubtrees(TreeNode root) {
        List<TreeNode> res = new ArrayList<TreeNode>();
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        helper(root, res, map);
        return res;
    }
    
    String helper(TreeNode root, List<TreeNode> res, HashMap<String, Integer> map){
        if (root==null) return empty;
        StringBuilder sb = new StringBuilder();
        
        sb.append(root.val).append(divide);
        sb.append(helper(root.left, res, map)).append(divide);    //here have to use preorder as string, cannot use inorder, there will be exceptions 
        sb.append(helper(root.right, res, map));
        
        String str = sb.toString();
        if (map.getOrDefault(str,0)==1)  res.add(root);
        
        map.put(str, map.getOrDefault(str,0)+1);
        
        return str;
    }
}
```