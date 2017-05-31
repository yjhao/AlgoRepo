# 390-Elimination Game
## 题意
给1到N个数， 第一次 从左边删去 1,3,5,7。。个数， 然后从右边删去 1,3,5,7。。个数。 问最后只剩下一个数， 这个数是几。

## 解题
观察一下规律。

题目问， 最后留下的数是什么，
**我们标记第一个数为head， 最后留下来的数只有一个时， 剩下的一定为head， head就为答案。**

当从左至右删除的时候， head 肯定会被删除掉， 取而代之的是 head+step 为 head。

当从右至左删除的时候， 至右当个数为 奇数的时候， head才会被删除掉， 取而代之是 仍然 是 head+step 为 head。

每一次删除， step 都扩大为原来的 两倍。

每一次删除， 总的留下的个数为原来的一半。


所以 最后算法 复杂度 为 O(logn)。

```
public class Solution {
    public int lastRemaining(int n) {
        boolean left = true;
        int cnt = n;
        int step = 1;
        int head = 1;
        
        while (cnt>1){
            if (left || cnt%2==1){
                head += step;
            }
            cnt /= 2;
            step *= 2;
            left = !left;
        }
        return head;
    }
}
```

