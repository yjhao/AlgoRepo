#446-Arithmetic Slices II - Subsequence

##题意
给一个数列， 求出一共有多少个 等差 subsequence。 最少的长度为3， subsequence不需要是连续的， 只要是等差的就可以。

##解题
首先可以想到， 当处理i数的时候， 可以一一检查i之前的数字j， 在j的时候， 有没有形成等差为 A[i] - A[j] 的数列， 如果有的话， 那么加上i之后， 就会形成更多的等差数列。

如果这样计算， 计算的 subsequence 肯定是“连续的”， 比如说 2 4 6 8 10。 在10的时候， 检查到8， 那么等差为2， 之前的部分就为 2 4 6 8 10， 如果检查到6， 那么等差为4， 那么之前的部分为2 6。 

所以我们可以很方便的计算， 加上i之后， 添加的等差数列个数。 因为是连续的， 所以添加的等差数列的个数 为在 j 处的等差数列个数+1。

比如2 4 6 8 ， 如果计算等差为2， 那么这个时候有2个等差数列， 4 6 8， 2 4 6 8， 如果加上10 之后， 添加的等差数列为 4 6 8 （10）， 2 4 6 8 （10）， 6 8 （10）。

##几个注意的地方
1. 有可能出现 2 2 4 6 这样的有重复的情况， 所以在 4 的时候，不能储存 等差为2 的数列的长度 （不然就为3, 其实为2）。而应该把长度这个信息， **转化为更加直接的信息** （因为长度就是拿来计算等差数列的个数的， 假设没有重复的话）。 

2. 当等差数列只有两个数的时候， 不能存 “0”， 因为如果 有重复的情况， 比如说 2,2,4,6, 如果存“0”的话， 就体现不出区别了， 所以所以我们储存的是， 如果加上 下一个合理的数字之后， 以下一个数字为结尾，一共有多少个等差数列。

2. 在每一个数字的时候， hashmap 不一定每一个数字都有Key， 所以使用： getOrDefault(diff,0)
3. 为了避免Overflow， 使用long来计算差值。
4. 当前的map的某一个key， 有可能会出现更新， 因为前面的数字有可能有重复的， 所以在更新的时候， **要加上之前的值**。

##代码
```
public class Solution {
    public int numberOfArithmeticSlices(int[] A) {
        if (A==null || A.length<=2) return 0;
        int res = 0;
        HashMap<Integer,Integer>[] maps = new HashMap[A.length];
        
        maps[0] = new HashMap<Integer, Integer>();
        maps[1] = new HashMap<Integer, Integer>();
        if ((long)A[1]-A[0] <= Integer.MAX_VALUE && (long)A[1]-A[0] >= Integer.MIN_VALUE)  maps[1].put(A[1]-A[0], 1);
        
        for (int i=2; i<A.length; i++){
            maps[i] = new HashMap<Integer, Integer>();
            for (int j=0; j<i; j++){
                if ((long)A[i]-A[j]>Integer.MAX_VALUE || (long)A[i]-A[j]<Integer.MIN_VALUE) continue;
                int diff = A[i]-A[j];
                if (maps[j].containsKey(diff)){
                    maps[i].put(diff, maps[i].getOrDefault(diff,0) + maps[j].get(diff)+1);
                    res += maps[j].get(diff);
                }else{
                    maps[i].put(diff, maps[i].getOrDefault(diff,0) + 1);
                }
            }
        }
        
        return res;
    }
}
```
 