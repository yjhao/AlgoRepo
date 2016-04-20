#98-Validate BST

## 题意
验证一个Tree是不是合法的bst

## 解题
###第一个方法
验证某个node的大小在不在左区间和右区间中

**细节： 最大最小值一定用Long表示， 不然如果root.val在Int的边界上， 就会报错**

```
public class Solution {
    public boolean isValidBST(TreeNode root) {
        if (root==null || root.left==null && root.right==null)  return true;
        return DFS(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    public boolean DFS(TreeNode root, long min, long max){
        if (root.val<=min || root.val>=max) return false;
        if (root.left!=null)
            if (!DFS(root.left, min, (long)root.val)) return false;
        if (root.right!=null)
            if (!DFS(root.right,  (long)root.val, max)) return false;
        
        return true;
    }
}
```

## 第二个方法
inorder 遍历这个数， 正确的bst， 后一个数一定会比其哪一个数大

```
public class Solution {
    public boolean isValidBST(TreeNode root) {
        if (root==null || root.left==null && root.right==null)  return true;
        return Inorder(root, new ArrayList<Integer>());
    }
    
    public boolean Inorder(TreeNode root, ArrayList<Integer> list){
        if (root==null) return true;
        
        if (!Inorder(root.left, list))  return false;
        
        if (list.size()==0){
            list.add(root.val);
        }else{
            if (root.val<=list.get(0))  return false;
            list.set(0, root.val);
        }
        
        if (!Inorder(root.right, list)) return false;
        
        return true;
    }
}
```