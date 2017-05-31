# 466-Count the Repotitions

## 题意
给两个String s1 s2, 和整数 n1, n2。 [s1,n1]代表有n1个s1重复， 问 [s1, n1]中最多可以包含多少个 [s2, n2]。

## 解题, 方法一
假设 [s1, n1] 中有 m 个 s2, 那么[s1, n1]中就包含了 m/n2 个 [s2, n2]。

brutal force 就是一个一个 two pointer的走， 得到m， 然后求得 m/n2。

但是可以发现， 这之中有重复的pattern。 所以可以用map来记录。将每一个s1的扫描看成独立的cycle。 

to match s2 in one s1 cycel， 当开始这个过程的时候， 从某一个s2的idx开始， 最后可以得到 在当前 s1 中可以match到的 s2的个数， 和开始下一个 s1扫描的时候， s2中的起始idx。 这个信息是可以重复使用的。

所以可以先 pre process 这个信息， 然后就不用一个一个扫描s1的字符了， 从开始扫描的时候， 查阅map， 就知道下一个扫描的s2起点， 和当前这个cycle的扫描， 一共有多少个s2。

```
public class Solution {
    public int getMaxRepetitions(String s1, int n1, String s2, int n2) {
        int len1 = s1.length(); 
        int len2 = s2.length();
        int[] increment = new int[len2];
        int[] nextIdx   = new int[len2];
        
        for (int i=0; i<len2; i++){
            int curCnt = 0;
            int curIdx = i;
            int s1Idx = 0;
            while (s1Idx<len1){
                if (s1.charAt(s1Idx) == s2.charAt(curIdx)){
                    curIdx++;
                    if (curIdx==len2){
                        curIdx=0;
                        curCnt++;
                    }
                }
                s1Idx++;
            }
            nextIdx[i] = curIdx;
            increment[i] = curCnt;
        }
        
        int idx = 0;
        int res = 0;
        
        for (int i=0; i<n1; i++){
            res += increment[idx];
            idx = nextIdx[idx];
        }
        
        return res/n2;
        
    }
}
```

# 解题， 方法二
在方法一中， 我们使用map来查阅信息， 但是共需要查阅n1次。

但其实这是不需要的， 因为之后的查阅， 和之前的查阅， 也是有cycle的。

我们在方法一中， 虽然使用了 map， 但没有使用 [s1, n1]的重复性。

具体的来说， 假设扫描到了 第 i 个 s1, 发现此时的 nextIdx 之前在第 j 个 s1 的时候出现过， 那么我们就找到了一个大循环。

假设在 第 j 个的时候， 一共有 p 个 s2， 在 第 i 个的时候， 一共有 q 个 s2。 那么从 j 到 i， 一共增长了 q-p 个 s2， 我们可以计算一下， 剩下来一共还有多少个 (j 到 i）这样长度的 cycle， 就可以得到一共还有多少个 （q-p)*s2。

但是这只是整数倍的 Cycle， 后面还有余数的长度， 余数的长度， 也是cycle的一部分， 也可以使用 余数的长度 和（i-j）的长度 的比例， 来确定一共有多少个 (q-p)*s2。

需要注意的是， 计算总长度的时候， 要与 i j 使用相同的坐标系统。

## 代码
```
public class Solution {
    public int getMaxRepetitions(String s1, int n1, String s2, int n2) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        
        int[] nextIdx = new int[n1];
        int[] cnt  = new int[n1];
        
        int idx = 0;
        int total = 0;
        
        for (int i=0; i<n1; i++){
            for (int j=0; j<s1.length(); j++){
                if (s1.charAt(j) ==  s2.charAt(idx)){
                    idx++;
                }
                if (idx==s2.length()){
                    total++;
                    idx = 0;
                }
            }
            
            nextIdx[i] = idx;
            cnt[i] = total;
            
            if (map.containsKey(idx)){
                int preIdx = map.get(idx);
                
                int preCnt = cnt[preIdx];
                int repeatCnt = (cnt[i] - cnt[preIdx]) * (n1-1-preIdx)/(i-preIdx);
                int postCnt = cnt[preIdx + (n1-1-preIdx) % (i-preIdx)] - cnt[preIdx];      // 最大值是n1-1, 而不是 n1， 因为 Index是以0为起点的。算的是差值， 所以 Preidx 和 n1 应该要使用相同的坐标系统
                
                return (preCnt + repeatCnt + postCnt)/n2;
            }
            
            map.put(idx, i);
        }
        return total/n2;
    }
}
```