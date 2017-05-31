# 546-Remove Boxes (recursion 和 DP 的主要不同）

## 题意
给了一个整数的 Array, 规则是可以去掉连续的几个相同的数字， 假设一连去掉了n个箱子，那么就可以得到n^2 的分数。 

问最后可以取得的最大分数。

## 解题
首先应该要知道， 如果分别一个一个去掉， 获得分数是肯定没有一起去掉的分数高。 

如果他们是相连的， 可以直接去掉， 如果他们是没有相连的，那么把中间的其他数字去掉之后， 他们也就相连了。

所以当遇到一个箱子的时候， 可以选择去掉， 也可以选择不去掉。 

**如果选择去掉**， 那么为了**最大化收益**， 需要知道相连的相同的箱子一共有多少个。 然后一并去掉。

相连的相同的箱子， 如果我们是从左到右的遍历， 那么其实只需要知道在左边的相连的相同的箱子个数就可以了。

也许有人会问， 为什么不看后面有多少个相同的箱子。 因为当visit到后面的箱子的时候， 前面的箱子都会考虑在内， 所以在前面的箱子的这个时候， 就不需要考虑后面的箱子了。

**如果选择不去掉**， 那么为了**最大化收益**， 那么就需要把这个箱子拿到后面去， 给**相同**颜色的箱子使用， 但是在使用之前， 需要把他们之间的其他箱子都去掉。

所以用 dp[i][j][k]来表示， 在 i 到 j 之间， 在 i 前， 有K个与i相同颜色箱子的情况下 的最大收益。

可以用 top-down 或者 bottom-up 的方法来做， 一个是recursion， 一个是dp

## recursion 和 DP 的主要不同
**DP 包含了所有参数的情况， 但是 并不是所有的参数的情况都成立的。但我们并不在意他们成不成立， 我们在意的是，如果他们成立了， 他们的值是多少。** 

**而 recursion 不一样， 因为recursion的所有状态都是成立的， 这是 recursion 和 dp 的主要不同**

## recursion 代码
```
public int removeBoxes(int[] boxes) {
    int n = boxes.length;
    int[][][] dp = new int[n][n][n];
    return removeBoxesSub(boxes, 0, n - 1, 0, dp);
}
    
private int removeBoxesSub(int[] boxes, int i, int j, int k, int[][][] dp) {
    if (i > j) return 0;
    if (dp[i][j][k] > 0) return dp[i][j][k];
        
    int res = (k + 1) * (k + 1) + removeBoxesSub(boxes, i + 1, j, 0, dp);
        
    for (int m = i + 1; m <= j; m++) {
        if (boxes[i] == boxes[m]) {
            res = Math.max(res, removeBoxesSub(boxes, i + 1, m - 1, 0, dp) + removeBoxesSub(boxes, m, j, k + 1, dp));
        }
    }
        
    dp[i][j][k] = res;
    return res;
}
```
在去除两个相同的箱子的中间的箱子的时候， 之前相同的箱子数k为0。 因为， 他们之前的箱子就是 i， 先不管箱子i是不是和中间的相同，最主要的是， i 选择了和 m 一起去掉， 而不是和中间的箱子们。所以中间的箱子不应该考虑i。

## 代码 DP
DP 包含了所有参数的情况， 但是 并不是所有的参数的情况都成立的。但我们并不在意他们成不成立， 我们在意的是，如果他们成立了， 他们的值是多少。

最后的结果是 dp[0][n-1][0]。

```
public class Solution {
    public int removeBoxes(int[] boxes) {
        if (boxes==null || boxes.length==0) return 0;
        int n = boxes.length;
        int[][][] dp = new int[n][n][n];
        
        for (int j = 0; j < n; j++) {
    	    for (int k = 0; k <= j; k++) {
    	        dp[j][j][k] = (k + 1) * (k + 1);  // len = 1
    	    }
        }
        
        for (int len = 2; len<=n; len++){
            for (int left=0; left<=n-len; left++){
                int right = left+len-1;
                
                for (int k=0; k<=left; k++){
                    dp[left][right][k] = (k+1)*(k+1) + dp[left+1][right][0];
                    
                    for (int h=left+1; h<=right; h++){
                        if (boxes[left] != boxes[h])   continue;
                        dp[left][right][k] = Math.max(dp[left][right][k], dp[left+1][h-1][0] + dp[h][right][k+1]);
                    }
                }
                
            }
        }
        
        return dp[0][n-1][0];
    }
    
}
```


