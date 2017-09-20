# 624-Maximum Distance in Arrays
## 题意
给一个 List<List<Integer>> 每一个list里面， 都是从小到大排好序了的，在这些数中， 选出两个绝对差距最大的数， 要求是这两个数不能来自于一个 sublist。

## 解题
晃眼一看， 这个题只需要取出所有数之中最小的， 和最大的， 然后求一下差值的绝对值就可以了。 但是题目还有要求， 必须不能是来自于同一个List的。

那么如何保证不来自于同一个list呢？可以用 归纳法来解决。

我们可以研究一下其中一个 list n 。

比如我们从第一个List 操作到了 第N个 List, 那么在这个List n之中，它的最大值 l2， 和它的最小值l1 ， 都有可能可以组成最后的结果。 

对于list中的最小值 l1， 它对应的“最大值”一定是之前或者之后的list中的最大值。 我们现在因为是从最开始遍历过来的， 所以我们可以得到在 list n 之前的最大值； 但是我们也需要同时 list n 后面的最大值么？

其实是不需要的， 假设后面的最大值的list位置 是 m， 对于这个最大值， 他所对应的 最小值， 不一定是 list n  的 l1了， 有可能是在 List n 之后的另外的一个list中的更小的数。

**因为我们是从左至右遍历的， 所以我们没有必要非要在 n 处得到m的信息， 因为在 m 处一定可以得到 n 的信息。**


相同的逻辑适用于 list 中的最大值 l2。

所以我们在这里， 只需要用N的最大值， 减去从1-n的最小值； 用N的最小值， 减去从 1-n 的最大值，并与之前的结果更新取最大值，就可以得到  到现在为止的 两个数的最大差值， 而且可以保证这两个数不来自于同一个List。

然后我们在更新 最大值， 最小值， 然后 再对 list n+1 进行操作。 这样， 我们无论遍历到哪个地方， 都可以求出在这之前的答案。

一边更新， 一边操作。 

## 代码
```
class Solution {
    public int maxDistance(List<List<Integer>> arrays) {
        int min = arrays.get(0).get(0);
        int max = arrays.get(0).get(arrays.get(0).size()-1);
        int res = 0;
        
        for (int i=1; i<arrays.size(); i++){
            res = Math.max(res, Math.abs(min - arrays.get(i).get(arrays.get(i).size()-1)));
            res = Math.max(res, Math.abs(max - arrays.get(i).get(0)));
            min = Math.min(min, arrays.get(i).get(0));
            max = Math.max(max, arrays.get(i).get(arrays.get(i).size()-1));
        }
        
        return res;
    }
}
```