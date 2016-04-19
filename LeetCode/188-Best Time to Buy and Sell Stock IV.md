#188-Best Time to Buy and Sell Stock IV

##题意
最多可以交易交易K次， 求最大收益， 一次交易包括一个买和一个卖

##解题
采用DP, 定义两个数组

hold[i][j]: 最多 i 次交易， 在第 j 天的最大的利润， 此时手里还有股票

unhold[i][j]: 最多 i 次交易， 在第 j 天的最大的利润， 此时手里没有股票

转移方程式, 情况分为买和不买， 卖和不卖

**如果有手续费的话， 直接加入转移方程中。**

```
hold[i][j]   = Math.max(unhold[i-1][j] - price[j], hold[i][j-1])

unhold[i][j] = Math.max(    hold[i][j] + price[j], unhold[i][j-1])
```

特别注意， 要初始化 hold[1：k][0] = -price[0]。 在第0天， 对于任何买的情况下， 收益都为 -price[0]。

是为了保证 hold[i][j]   = Math.max(unhold[i-1][j] - price[j], hold[i][j-1]) 的成立， 如果不为-price[0]的话， hold就为0了。

交易次数放在外循环， 可以修改边界条件。

##代码
```
public class Solution {
    //https://leetcode.com/discuss/57669/understanding-easily-modified-different-situations-solution
    public int maxProfit(int k, int[] prices) {
        if(k == 0 || prices.length < 2)
            return 0;
        if(k > prices.length / 2)
            return noLimit(prices);

        int[][] hold = new int[k+1][prices.length];
        int[][] unhold = new int[k+1][prices.length];
        for(int i = 1; i <= k; i++) {
            hold[i][0] = -prices[0];    // init for line 17
            unhold[i][0] = 0;
            for(int j = 1; j < prices.length; j++) {
                hold[i][j] = Math.max(-prices[j] + unhold[i-1][j], hold[i][j-1]); // Buy or not buy
                unhold[i][j] = Math.max(prices[j] + hold[i][j], unhold[i][j-1]); // Sell or not sell
            }
        }
        return unhold[k][prices.length-1];
    }
    private int noLimit(int[] prices) { // Solution from Best Time to Buy and Sell Stock II
        int max = 0;
        for(int i = 0; i < prices.length-1; i++) {
            if(prices[i+1] > prices[i])
                max += prices[i+1] - prices[i];
        }
        return max;
    }
}
```
