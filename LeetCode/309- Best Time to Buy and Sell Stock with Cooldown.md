# 309- Best Time to Buy and Sell Stock with Cooldown

## 题意
还是买卖股票， 不过这次是卖了之后的 next day 不能交易。

## 解题
用三个变量表示， buy, sell, cooldown

买肯定是从cooldown那里买。

sell肯定是从buy那里sell的。

cooldown的价格应该是前两天的

a-b-c-d-e

如果在c点买入， cooldown 应该在a

所以处理B点的时候， 更新cooldown的时候， cooldown = max (colldown, sell）， 此时的sell一定要是在a点的。 （那么在c点的时候， cooldown就一定是在a点的值。）。

**所以更新cooldown一定要在更新sell之前（保证Cooldown是前一天的）。更新buy一定要在更新cooldown之前（保证用的Cooldown是前两天的）**


```
public int maxProfit(int[] prices) {
        if (prices.length<2) return 0;
        int buy = -prices[0], sell = 0, cooldown = 0;
        for(int i=1; i<prices.length; i++) {
            int temp = buy;
            buy = Math.max(buy, cooldown-prices[i]);
            cooldown = Math.max(sell, cooldown);
            sell = Math.max(sell, temp+prices[i]);          
        }
        return sell>cooldown?sell:cooldown;
    }
```

