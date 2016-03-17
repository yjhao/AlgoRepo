# 334C-Alternative Thinking

## 题意：
01串，可选一段进行取反，求取反之后01间隔出现的子序列最长能有多长。

## 解答
对一段字符取反， 当计算0101 pattern 的时候，改变的其实只会有**边界**。那么最大利用取反 就转化为了如何最优化边界。

很容易想到， 如果两个数字本来相同， 取反左边的一个或右边的一个数字， 就会出现10或者01。

所以我们就找两个像00, 11的子串， 然后对其中间的部分取反， 最后的结果会比不取反多2， 正好是边界的两个。

**当然，由于不影响内部，这个串当然是越长越好。所以用two pointer从左右分别搜索**

从左向右找到第一个00或11，靠右的位置为l；从右向左找到第一个00或11，靠左的位置为r；[l,r]取反，扫描一遍统计01子序列长度。复杂度O(N)。

**例子**： --11--11---  
左边的11表明： 到第一个1的时候，最长的子序列肯定落脚到1（尾是1，但不一定是第一个1）； 所以此时将第二个1变为0是变废为宝的；同理右边的11也是如此。除了第二个和第三个1以外，取反的子串内部可取的01序列式不会受到任何影响的, 取反而已，无非是0101变成1010，

**特殊情况**
如果整个字符串只有一个像00或者11这样的子串，(比如： 010010)  

这说明这个子串左边的substring和右边的substring都是0101010的排列， 只需要取反左右其中任何一个substring加上相邻的一个边界, 就可以把aa变废为宝。

## 代码
```
public void solve() throws IOException {
        int n = in.nextInt();
        String str = in.next();
        char[] num = str.toCharArray();

        int left = -1;
        int right = -1;

        // search from left;
        for (int i=1; i<num.length; i++){
            if (num[i]==num[i-1]){
                left = i;
                break;
            }
        }

        // search from right;
        for (int i=num.length-2; i>=0; i--){
            if (num[i]==num[i+1]){
                right = i;
                break;
            }
        }

        // flip
        if (left!=-1){
            if (left>right) {
                right = n - 1;
            }
            for (int i=left; i<=right; i++){
                if (num[i]=='1')
                    num[i] = '0';
                else
                    num[i] = '1';
            }
        }

        // calculate 0101, 1010
        int oneMax = 0;
        int zeroMax = 0;

        for (int i=0; i<n; i++){
            if (num[i]=='1'){
                oneMax = zeroMax+1;
            }else{
                zeroMax = oneMax+1;
            }
        }

        int res = Math.max(zeroMax, oneMax);

        out.print(res);

    }
```

