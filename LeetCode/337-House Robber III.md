# 337-House Robber III
## 题意
在一个树里面， 找到一些不相邻的Node, 使这些node的和最大

## 解题
对于某一个Node, 要么选， 要么不选， 如果选的话， 那么他之前之后的Node都不能选， 反之亦然。

所以我们使用state transfer来解决这个问题, res[0]表示不选当前， res[1]表示选择当前

对于一个node， 如果要选， 那么之前的相邻的一个肯定不能选
```
res[1] = left[0] + right[0] + cur.val
```

如果不选择, 那么前面的相邻一个node， 可以是选择了的， 也可以是没有选择了的。
```
res[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1])
```

## 代码
```
public class Solution {
    public int rob(TreeNode root) {
        int[] num = dfs(root);
        return Math.max(num[0], num[1]);
    }
    private int[] dfs(TreeNode x) {
        if (x == null) return new int[2];
        int[] left = dfs(x.left);
        int[] right = dfs(x.right);
        int[] res = new int[2];
        res[0] = left[1] + right[1] + x.val;
        res[1] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        return res;
    }
}
```

