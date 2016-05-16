#209-B Tavas and SaDDas

## 题意
只由4和7组成的数字是幸运数字，然后给你个幸运数字，问你是第几个

## 解题
因为只有4和7， 所以对于每一位数 （ <10, <100, <1000), 可以得到一共有多少个幸运数。

每一位只能为4或者7， 所以小于10 有2个， 小于100 有2*2个， 以此类推。

比如这个数为 4abcd, 分解为 小于40000 + abcd, 对于40000， 一共有 2 * 2 * 2 * 2, 16个数字， 然后再计算 a bcd中， bcd的数目。

如果是7abcd， 那么至少有 2 * 2^4 32个数字 (小于10000， 和40000与50000之间）， 然后再计算 a bcd中， bcd的数目。

##代码
```
public void solve() throws IOException {
        char[] s = in.next().toCharArray();
        int res = 0;
        int len = s.length-1;
        int cur = (int)Math.pow(2, len);
        for (int i=0; i<s.length; i++){
            if (s[i]=='4'){
                res += cur;
            }else{
                res += cur*2;
            }
            cur = cur/2;
        }
        out.print(res);
    }
```