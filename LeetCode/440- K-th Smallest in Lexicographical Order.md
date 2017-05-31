# 440- K-th Smallest in Lexicographical Order

## 题意
给两个数 n 和 k， 求出在1-n之间的， 第k  Lexicographical 大的数字。

## 解题
这个题得使用 denary tree 来做

![alt text](https://discuss.leetcode.com/uploads/files/1477293057263-upload-40379731-118a-4753-bed9-1cb372790d4b.png)

对于1开头的数， 我们计算出来在 n 的范围之内， 向下的tree node 一共有多少个， 假设为m。 如果k是在这个m范围内， 那么我们就向下一层， 将1变为10, 然后递减K by one，然后再次计算， 以10开头的数， 一共有多少。 反之， 我们将“1” 变为“2”， 然后在剩余步数中， 减去 以“1”开始的步数， 然后计算以“2”开头的数一共有多少。

最后K=1的时候， 我们就找到了最后的答案了。

## 如何计算Node个数。

比如说我们要计算对于以1开头的数，一共有多少个， 那么在每一层， 我们分别计算出此时的起点，和终点， 然后计算差值， 就可以知道一共有多少个Node。

如何计算起点？ 地点就是10， 100， 1000之类的数。

如何计算终点？ 终点就是 以2开头的数， 在当前层的第一个数， 减去一。

再比如 如果计算以11开头的数一共有多少个， 那么终点就是以12开头的数， 在当前层的第一个数， 减去1。

## 代码
```
public class Solution {
    public int findKthNumber(int n, int k) {
       int cur = 1;

       while (k>1){
           int next = cur+1;
           int step = calStep(cur, next, n);
           if (k>step){
               k -= step;
               cur += 1;
           }else{
               k -= 1;
               cur *= 10;
           }
       }
       return cur;  
    }

    int calStep(long left, long right, long n){
        int step = 0;
        while (left<=n){
            step += Math.min(right, n+1) - left;
            left *= 10;
            right *= 10;
        }
        return step;
    }
}
```
