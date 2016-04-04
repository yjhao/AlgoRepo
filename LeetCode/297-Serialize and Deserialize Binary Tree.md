#297-Serialize and Deserialize Binary Tree
## 题意
给一个Tree, 将它变为一个String, 再把这个string还原为一个Tree.

## 解题
serialize的关键就是前后要一致。

分解的时候用dfs， 组装的时候需要使用同一个顺序的dfs。

用分隔符分割开来不同的Node的数据。

但是需要注意的是， 如果一个node为空， 需要用一个特殊的字符来代替， 说明这里是有Node的， 不过是为空而已。

如果不这样的话， 会有什么问题呢？ 比如说 1.left = null, 1.right = 2, seri后变为1，2。 但是当de-seri的时候， 1后面的2， 不能够判定是1的左还是1的右， 所以需要用一个特殊字符来占位， 表示这里应该有Node。

## 细节
建树的时候， dfs 的 return 值是node，比如： root.left = dfs(...), root.right = dfs(...)。 这样迭代下去。

比较String的时候， 不能使用“==”

##代码
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
public class Codec {
    String spliter = ",";
    String special = "NN";
    
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        DFS_build(root, sb);
        return sb.toString();
    }
    
    public void DFS_build(TreeNode root, StringBuilder sb){
        if (root==null){
            sb.append(special);
            sb.append(spliter);
            return;
        }
        
        sb.append(root.val);
        sb.append(spliter);
        DFS_build(root.left, sb);
        DFS_build(root.right, sb);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] str = data.split(spliter);
        LinkedList<String> list = new LinkedList<>();
        for (String s: str){
            list.add(s);
        }
        TreeNode root = DFS_de(list);
        return root;
    }
    
    public TreeNode DFS_de(LinkedList<String> list) {
        String cur = list.poll();
        if (cur.equals(special)){
            return null;
        }
        
        TreeNode next = new TreeNode(Integer.valueOf(cur));
        next.left = DFS_de(list);
        next.right = DFS_de(list);
        return next;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.deserialize(codec.serialize(root));
```