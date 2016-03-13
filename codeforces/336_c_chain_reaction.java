/** 题意：
　　N个标志，第i个信标位置a[i]，能量b[i]， i左边b[i]个单位的标志都被remove掉。
   现在可以从右往左依次去掉标注， 比如第一次去掉n， 第二次去掉n, n-1， 以此类推。
   问去掉这些靠右边的标志之后，总共的最少的被去掉的标志是多少。

 * 因为是从右往左依次去掉， 在删掉n之后， 还剩下1到n-1,在删掉n-1, n之后，还剩下1到n-2。
   所以我们可以从左往右一个一个加入新的标志， 用dp[i]表示到I为止， 现在一共有多少个标志。
   记录一个变量max， 表示最多的标志数量是多少， 然后最少的被去掉的就是n-max.

   很明显， 加入新的之后， dp方程为：

   dp[i] =      dp[i-1]                 如果在I没有标志加入
           or   dp[i-b[i]+1] + 1        如果在I有标志加入， 那么前面b[i]的都会被remove掉
 */

 public void solve() throws IOException {
     int n = in.nextInt();
     int[] dp  = new int[1000001];
     int[] pos = new int[1000001];
     int[] pow = new int[1000001];

     for (int i=0; i<n; i++){
         int idx = in.nextInt();
         int power = in.nextInt();
         pos[idx] = 1;
         pow[idx] = power;
     }
     // get the maximum number of beacons that are not removed
     int maxLeft = 0;
     dp[0] = pos[0] == 1? 1:0;
     for (int i=1; i<1000001; i++){
         if (pos[i]!=1){
             dp[i] = dp[i-1];
         }else{
             int leftIdx = Math.max(-1, i-pow[i]-1);
             if (leftIdx<0){
                 dp[i] = 1;
             }else{
                 dp[i] = dp[leftIdx]+1;
             }
         }
         maxLeft = Math.max(maxLeft, dp[i]);
     }

     int res = n-maxLeft;

     out.print(res);
 }
