/**
 * http://codeforces.com/contest/615/problem/C
 * 题意
   给你一个字符串s,然后给你字符串t，让你用s中的子串来构成t.

   求出最小拼接次数，并输出构造方案。

   s可以旋转
 *
 *
 * 题解：
 * 遇到字符串 自然想到DP.
 * The idea is that if can make a substring t[i, j] using k coatings,
   then we can also make a substring t[i + 1, j] using k coatings. So we should
   use the longest substring each time.
 * 首先构造longest common prefix substring lcp[i][j], s的i 和 t的j 之前的 lcp[i][j]
   个字母是一样的。

   for (int i=1; i<=len1; i++)
            for (int j=1; j<=len2; j++)
                if (s.charAt(i-1) == t.charAt(j-1))
                    lcp[i][j] = lcp[i-1][j-1]+1;

 * 之后再用DP构造 minimum step: dp[i][j]。 如果s的i 和 t的j 之前 有lcp成立， 求最小步数：

   for (int j=1; j<=len2; j++){
          for (int i=1; i<=len1; i++){
              if (lcp[i][j]!=0){
                  if (dp[j]>dp[j-lcp[i][j]]+1){
                      dp[j] = dp[j-lcp[i][j]] + 1;
                      step[j][0] = i - lcp[i][j]+1;     // the starting point of this step
                      step[j][1] = i;                   // the ending   point of this step

* 最后backward 找到每个节点 并打印出来。
* 复杂度： O(mn)
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class cf_338_c {
    private FastScanner in;
    private PrintWriter out;

    public void solve() throws IOException {
        String s = in.next();
        String t = in.next();
        int len1 = s.length();
        int len2 = t.length();
        String reverseS = new StringBuilder(s).reverse().toString();

        int[][] lcp    = new int[len1+1][len2+1];
        int[][] lcprev = new int[len1+1][len2+1];

        // get the lcp and lcprev
        for (int i=1; i<=len1; i++){
            for (int j=1; j<=len2; j++){
                if (s.charAt(i-1) == t.charAt(j-1)){
                    lcp[i][j] = lcp[i-1][j-1]+1;
                }
                if (reverseS.charAt(i-1) == t.charAt(j-1)){
                    lcprev[i][j] = lcprev[i-1][j-1]+1;
                }
            }
        }

        long[] dp = new long[len2+1];
        for (int i=1; i<dp.length; i++){
            dp[i] = 1000000000;
        }
        dp[0] = 0;

        int[][] step = new int[len2+1][2];


        // obtain the minimum steps
        for (int j=1; j<=len2; j++){
            for (int i=1; i<=len1; i++){
                if (lcp[i][j]!=0){
                    if (dp[j]>dp[j-lcp[i][j]]+1){
                        dp[j] = dp[j-lcp[i][j]] + 1;
                        step[j][0] = i - lcp[i][j]+1;     // the starting point of this step
                        step[j][1] = i;                   // the ending   point of this step
                    }
                }
                if (lcprev[i][j]!=0){
                    if (dp[j]>dp[j-lcprev[i][j]]+1){
                        dp[j] = dp[j-lcprev[i][j]] + 1;
                        step[j][0] = len1+1 - (i - lcprev[i][j]+1);
                        step[j][1] = len1+1 - (i);
                    }
                }
            }
        }

        if (dp[len2] == 1000000000){
            out.print("-1");
            return;
        }

        long res = dp[len2];
        out.println(dp[len2]);

        // track (backwards) and print the steps,
        int idx = len2;
        int[][] track = new int[len2+1][2];
        int trackCount = 0;
        while (idx>0){
            track[trackCount][0]   = step[idx][0];
            track[trackCount++][1] = step[idx][1];
            idx = idx - (Math.abs(step[idx][0] - step[idx][1])+1);
        }

        trackCount--;
        while (trackCount>=0){
            out.println(track[trackCount][0] + " " + track[trackCount][1]);
            trackCount--;
        }


        return;
    }


    public void run() {
        try {
            in = new FastScanner();
            out = new PrintWriter(System.out);
            solve();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class FastScanner {
        private BufferedReader br;
        private StringTokenizer st;

        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        public String next() {
            while (st == null || !st.hasMoreTokens()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public BigInteger nextBigInteger() { return new BigInteger(next());}

    }

    public static void main(String[] arg) {
        new cf_338_c().run();
    }
}
