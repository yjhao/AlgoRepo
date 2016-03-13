/** 求串a对应串b中所有长尾|a|的子串的Hamming距离和。
 *  brutal force O(n2), not good...

 *  a[i]能对答案的贡献，其实就是a[i]与b[i],b[i+1]...b[i+ |b|-|a| ]这些值异或的和，因为a[i]一位一位后移，总体能对应上的也就是b的这些位置。
　　要求a[i]对答案的贡献，也就是如果a[i]=='0'那就要找b[i],b[i+1]...b[i+ |b|-|a| ]这些位置中'1'的个数，反之亦然。

   所以计算一个前缀 dp[i], 表示从0到I, b中有多少个1

   复杂度： o(n)
 */

 public void solve() throws IOException {
     String a = in.next();
     String b = in.next();

     long res = 0;
     int alen = a.length();
     int blen = b.length();

     int[] ones = new int[blen];
     ones[0] = b.charAt(0)=='1' ? 1:0;
     for (int i=1; i<blen; i++){
         ones[i] = b.charAt(i)=='1' ? ones[i-1]+1 : ones[i-1];
     }

     for (int i=0; i<alen; i++){
         int startIdx = i;
         int endIdx   = blen-alen+i;

         int startZero = 0;
         if (b.charAt(startIdx)=='0'){
             startZero = 1;
         }

         int startOne = 0;
         if (b.charAt(startIdx)=='1'){
             startOne = 1;
         }

         if (a.charAt(i)=='1'){
             // number of zero
             res += (endIdx-startIdx+1) - (ones[endIdx] - ones[startIdx] + startOne);
         }else{
             // numer of one
             res += ones[endIdx] - ones[startIdx] + startOne;
         }
     }

     out.print(res);


 }
