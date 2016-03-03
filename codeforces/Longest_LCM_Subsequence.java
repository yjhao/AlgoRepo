/** Problem Statements   http://codeforces.com/contest/632/problem/D
 *  For an array, find the maximum subsequnce that has a LCM that is smaller than a certain value m.
 */

 /* Solution
  * 1. count the frequency of each value in the array.
    2. for value x in the array, it has a count of cnt(x).
    3. The total number of factor 因子 of each beta of (1:k)*x is incremented by x.
    4. repeat step 2 and 3 for each number from 1:m
    5. Obtain beta that has the maximum count of factors.

  * Time complexity is n + n/2 + n/3 + n/4 + ... + n/n = O(nlogn)
  */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.Map;
import java.util.HashMap;


/**
 * Created by yujiahao on 3/1/16.
 */
public class cf_632_d {
    private FastScanner in;
    private PrintWriter out;

    public void solve() throws IOException {
        int n = in.nextInt();
        int m = in.nextInt();
        int size = 1000002;

        int[] cnt = new int[size];
        int[] arr = new int[n];

        for (int i=0; i<n; i++) {
            int cur = in.nextInt();
            arr[i] = cur;
            if (cur<=m)
                cnt[cur]++;
        }

        int[] ans = new int[m+1];
        for (int i=1; i<=m; i++){
            for (int j=i; j<=m; j=j+i){
                ans[j] += cnt[i];
            }
        }

        int max = 0;
        int lcm = 1;
        for (int i=1; i<=m; i++){
            if (ans[i]>max){
                max = ans[i];
                lcm = i;
            }
        }

        out.print(lcm + " ");
        out.println(max);

        for (int i=0; i<n; i++){
            if (lcm % arr[i]==0) {
                out.print(i + 1);
                out.print(" ");
            }
        }

    }


    public void solve2() throws IOException {
        int n = in.nextInt();
        int m = in.nextInt();
        int[] a = new int[n];
        boolean[] b = new boolean[n];

        for (int i = 0; i < n; i++) {
            int cur = in.nextInt();
            if (cur <= m) {
                a[i] = cur;
                b[i] = true;
            }
        }

        HashMap<Long, Integer> map_l = new HashMap<Long, Integer>();

        HashMap<Long, int[]> map = new HashMap<Long, int[]>();

        for (int i = 0; i < n; i++) {
            if (b[i] == false) continue;
            for (int j = i + 1; j < n; j++) {
                if (b[j] == false) continue;
                long curLcm = lcm(a[i], a[j]);
                if (curLcm>m)   continue;

                if (map.containsKey(curLcm)) {
                    int[] idx = map.get(curLcm);
                    idx[i] = 1;
                    idx[j] = 1;
                } else {
                    int[] idx = new int[n];
                    idx[i] = 1;
                    idx[j] = 1;
                    map.put(curLcm, idx);
                }
            }
        }

        int max = 0;
        long resKey = 0;

        for (Map.Entry<Long, int[]> entry : map.entrySet()) {
            Long key = entry.getKey();
            if (key>m) continue;
            int[] arr = entry.getValue();
            int count = 0;
            for (int i=0; i<n; i++){
                if (arr[i] == 1){
                    count++;
                }
            }

            if (count>max){
                max = count;
                resKey = key;
            }

        }

        int[] res_arr = map.get(resKey);

        out.print(resKey);
        out.print(" ");
        out.println(max);

        for (int i = 0; i < n; i++) {
            if (res_arr[i] == 1) {
                out.print(i+1);
                out.print(" ");
            }
        }


    }

    private int gcd(int a, int b) {
        while (b > 0) {
            int temp = b;
            b = a % b; // % is remainder
            a = temp;
        }
        return a;
    }

    private long lcm(int a, int b) {
        return a * (b / gcd(a, b));
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

    }

    public static void main(String[] arg) {
        new cf_632_d().run();
    }
}
