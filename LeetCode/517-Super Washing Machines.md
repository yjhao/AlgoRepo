# 517-Super Washing Machines
## 题意
有一个数组， 可以进行若干次操作， 每次操作， 将若干个 某一个位置上的数字， 减小一， 然后转给左边的或者右边的位置上的数字。 问最少多少次操作， 所有位置上的数目都变成一样的。

## 解答
这个题目， 应该用宏观的角度来看待。 对于某一个位置， 最后会变为平均数， 中间的过程的操作数目， 有一个最小值， vi - mean。 那么最后的全局最少操作数， 就是每一个位置上的最小操作数的最大值。

那么对于位置i 上面的数， 他的操作数取决于几个方面。

1. 他左面的现在状态和最终状态。
2. 他右面的现在状态和最终状态。 如果相比最终状态， 左面少了， 右面多了， 那么肯定右面往左边传递的过程， 会使用i位置。

如果左边和右边都达到了最终状态， 那么自己也一定会达到最终状态。

对于i来说， 如果左边和右边都大于最终状态， 那么说明都需要向i来转移， 因为每次操作可以是若干个同时进行， 所以这个时候的最小操作数是左边和右边的操作数的最大值。

如果左边大右边小， 或者左边小右边大， 那么操作的过程就是一个从左到右或者从右到左的过程， 这个时候的最小操作数， 也是左边和右边的操作数的最大值。

如果左边小 右边也小， 说明得需要从i来进行转移， 所以这个时候最小操作数是 左右操作数的 和。

当对所有位置上的数， 都进行了这样的操作之后， 最后的最终状态的最小操作数就可以得到了。

## 代码
```
public class Solution {
    public int findMinMoves(int[] machines) {
        int[] sum = new int[machines.length+1];
        for (int i=0; i<machines.length; i++){
            sum[i+1] = sum[i] + machines[i];
        }
        int total = sum[machines.length];
        if (total%machines.length!=0)   return -1;
        int mean = total / machines.length;
        
        int max = 0;
        
        for (int i=0; i<machines.length; i++){
            int leftSum = sum[i];
            int leftDiff = leftSum - mean * i;
            int rightSum = sum[machines.length] - leftSum - machines[i];
            int rightDiff = rightSum - mean * (machines.length-1-i);
            
            if (leftDiff >=0 && rightDiff >=0) max = Math.max(max, Math.max(leftDiff, rightDiff));
            else if (leftDiff <=0 && rightDiff <=0) max = Math.max(max, Math.abs(leftDiff) + Math.abs(rightDiff));
            else max = Math.max(max, Math.max(Math.abs(leftDiff), Math.abs(rightDiff)));
        }
        
        return max;
    }
}
```