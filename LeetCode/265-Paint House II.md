# 265-Paint House II
## 题意
用k种颜色刷n个房子， 每个颜色对于每个房子都有个价格， 相邻的房子不能用一样的颜色， 求最小花销。

## 解题
DP +  状态转移。

在每一个房子， 对于每一个颜色， 找到前一个房子中除自己外花销最小的即可。

关键是这里有k种颜色， 如果对于每一个颜色都来寻找谁是最小值， 复杂度就很高。

这个问题的本质是找除自己之外的最小的数， 所以保存两个变量 

**最小值， 和次最小值**， 一定有一个用得到

## 代码
```
class Solution {
public:
    int minCostII(vector<vector<int>>& costs) {
        if (costs.empty() || costs[0].empty()) return 0;
        vector<vector<int>> dp = costs;
        int min1 = -1, min2 = -1;
        for (int i = 0; i < dp.size(); ++i) {
            int last1 = min1, last2 = min2;
            min1 = -1; min2 = -1;
            for (int j = 0; j < dp[i].size(); ++j) {
                if (j != last1) {
                    dp[i][j] += last1 < 0 ? 0 : dp[i - 1][last1];
                } else {
                    dp[i][j] += last2 < 0 ? 0 : dp[i - 1][last2];
                }
                if (min1 < 0 || dp[i][j] < dp[i][min1]) {
                    min2 = min1; min1 = j;
                } else if (min2 < 0 || dp[i][j] < dp[i][min2]) {
                    min2 = j;
                }
            }
        }
        return dp.back()[min1];
    }
};
```

