#375-Guess Number Higher or Lower II

##题意
从1-n 中猜一个数， 每次猜错之后， 会被告诉是少了还是多了。 如果猜错了， 会被charge 现在猜的数这么多的钱。 问， 如果有多少钱， 就可以保证能够赢这个游戏。

##解题
被问到， 至少需要多少钱， 能够赢这个游戏。

每次可以猜不同的数。猜了不同的数之后， 对于这种情况下， 如果要赢这个游戏， 最坏情况需要多少钱。 

然后在所有的 猜不同的数的 情况中， 如果选到一个最佳的最坏情况， 那么就一定能够保证能够赢这个游戏。

“选到数n， 如果选错了， 我最坏情况要付xxx钱 “， 对于每个不同的n， 最坏情况是不一样的， 我们选一个最佳的最坏情况。

选到一个数之后， 情况莫非有两种， 答案在左面， 或者在右面。 在这两种Cost里面， 选一个max， 就是最坏情况。 

##代码
```
public class Solution {
    public int getMoneyAmount(int n) {
        if (n==0)   return 0;
        int[][] cost = new int[1+n][1+n];
        
        for (int len = 2; len<=n; len++){
            for (int start = 0; start<=n-len+1; start++){
                int end = start + len-1;
                int curCost = start + cost[start+1][end];
                
                for (int mid = start+1; mid<end; mid++){
                    int temp = Math.max(cost[start][mid-1],cost[mid+1][end])  + mid;
                    curCost = Math.min(temp, curCost);
                }
                
                curCost = Math.min(curCost, end+cost[start][end-1]);
                cost[start][end] = curCost;
            }
        }
        
        return cost[1][n];
    }
}
``` 