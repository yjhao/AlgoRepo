#337 House Robber I, II, III （DP)

## 题意
从一个数列中， 取出一些数字， 来求得最大sum， candidates 不能是紧挨着的。 形式可以是一个 Array, 一个圈圈， 或者 一个 tree。

##解题

典型的DP

##方法一
在每一个i， 可以分为两个情况， 取 或者 不取。 递推式为：

```
if rob current one : 之前一个肯定不能被 rob
	rob[i] = no_rob[i-1] + val[i];
if not rob current one:  之前一个可以是rob， 也可以是没有 rob
	no_rob[i] = Math.max(rob[i-1], no_rob[i-1])

最后的答案为 Math.max(rob[end], no_rob[end])

```
在每一个 i, 收紧 no _ rob[i], 那么 rob[i] 就可以直接 使用 上一个 no_rob[i-1]。

这个方法只使用 i 和 i-1, 可以适用于 tree 



##方法二
dp[i] 为 到 i 处的 **最大值**， 包括取i 或者是 不取 num[i]。

因为保存的只是当前最大值， 所以如果对于i， 如果要取的话， 结果中就必须加上在I-2 的结果。 

所以得知道上两个的结果， 这也是这个方法的局限性， 不能用于 tree。

递推式为：
```
dp[i] = Math.max(dp[i-2]+num[i], dp[i-1]);
```
