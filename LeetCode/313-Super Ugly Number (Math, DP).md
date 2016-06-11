#313-Super Ugly Number (Math, DP)
##题意
给出一个质数的list, 写出 第n个 ugly number。 ugly number 是所有质因数都来自于这个 list。

##解题
采用 induction 来解题。**一个Ugly number， 肯定是由另外一个 ugly number 乘上一个list 中的质数来形成的。**

假设我现在有一个 ugly number 了， 那么通过这个 ugly number， 分别乘以 list 中的其他质数， 那么我们可以得到 新的 ugly number， 然后再在 这些 ugly number 中选择小的一个。

问题是， 比如说我们已经得到3了， 那么下一步： 3 * 2 = 6， 3 * 5 = 15， 但是他们都是大于 1 * 5 = 5的， 那么该如何快速得到这个信息呢？

**我们保存 对应每个在list中的质数的 当前最小 Ugly number 数（其实就是一个 Array 中的 index)**

每次比较这个最小的数， 取得最小值， 然后递增。

该怎么递增呢？ 比如 m*n， n 是质数， m 是前面的一个因子， 也是取得的一个ugly number。 那么我们就 递增 m 的大小，把m赋值为它的下一个 Ugly number。

##代码
```
public class Solution {
    public int nthSuperUglyNumber(int n, int[] primes) {
        int[] res = new int[n];
        res[0] = 1;
        int[] index = new int[primes.length];
        
        for (int i=1; i<n; i++){
            int min = Integer.MAX_VALUE;
            for (int j=0; j<primes.length; j++){
                min = Math.min(min, primes[j]*res[index[j]]);
            }
            
            for (int j=0; j<primes.length; j++){
                if (primes[j]*res[index[j]] == min){
                    index[j]++;
                }
            }
            res[i] = min;
        }
        return res[n-1];
    }
}
``` 