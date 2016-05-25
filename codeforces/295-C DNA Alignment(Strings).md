#295-C DNA Alignment(Strings)
##题意
http://codeforces.com/contest/520/problem/C

构建一个新的string t， 使其与原来的String s的距离最短。

比较每一个 旋转过的t 和每一个 旋转过的 s。

## 解题
要让答案最大， 就使得**t的每一位**的贡献都最大， 也就是让每一位都出现次数最多的那个字母。

假设有n位， 有k个字母的频率都为最高， 那么在每一位上就有k个选择， 总共的选择个数就是 k^n

##代码
```
public void solve() throws IOException {
        int n = in.nextInt();
        char[] s = in.next().toCharArray();
        int[] count = new int[4];

        for (int i=0; i<n; i++){
            char cur = s[i];
            if (cur=='A')
                count[0]++;
            if (cur=='C')
                count[1]++;
            if (cur=='G')
                count[2]++;
            if (cur=='T')
                count[3]++;
        }

        int max = count[0];
        for (int i=1; i<4; i++){
            max = Math.max(max, count[i]);
        }

        int num = 0;
        for (int i=0; i<4; i++){
            if (count[i] == max)
                num++;
        }

        int res = pow(num, 1000000007, n);
        out.print(res);
    }

    public int pow(int num, int mod, int n){
        long res = 1;
        for (int i=0; i<n; i++){
            res = (res*num)%mod;
        }
        return (int)res;
    }
```