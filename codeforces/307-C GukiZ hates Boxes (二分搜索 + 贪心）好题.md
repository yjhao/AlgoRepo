#307-C GukiZ hates Boxes (二分搜索 + 贪心）好题

##题意
n堆箱子, m个学生要搬走箱子, 每个学生走过在箱子中间移动需要一秒, 移除一个箱子需要一秒, 问最少多少秒搬完.

##解题
对于求解最大最小值的问题，通常有两种解法：

1. 正向求解,即直接根据已知条件求解最大最小值
2. 反向求解,即枚举答案，并判断该答案是否有解，最后再在所有可行解中筛选出最优解（通常这里枚举答案的过程都具有可二分性）

本问题如果采用正向求解的话， 会超时。

所以得选择二分法搜索。

通过二分枚举每个时间戳，并判断是否存在可行解。(关于二分枚举的正确性显然，当10秒内就可以搬完所有箱子，就枚举比10秒更少的时间。否则枚举比10秒多的时间）。

1. 如果在t秒内能完成任务, 那么肯定在大于t秒的时间内也能完成. 
2. 所以先假设一个t， 看在t时间之内， **这些学生最多能够搬多少箱子。** 假设每个学生都搬掉自己能搬掉的最多箱子。
3. 如果箱子都搬完了， 而还有剩下的学生， 说明我们或许还能用更少的时间完成任务。 反之， 如果每个学生在这么多时间之内都搬到他能搬的最大值， 而还有剩下的箱子， 说明我们得增加时间。

4. 所以， 我们派出学生到最远的一个位置， 把t时间都花光， 看他最多搬多少箱子； 然后把第二个学生派到剩下的最后的一个箱子处， 让他继续接着搬箱子。

5. 如果最后箱子都没有了， 那么返回true; 如果学生没有了， 而箱子还没有到最后一个， 就返回False。

##代码
```
public void solve() throws IOException {
        int n = in.nextInt();
        int m = in.nextInt();
        int[] a = new int[n+1];
        long total = 0;
        for (int i=0; i<n; i++){
            a[i+1] = in.nextInt();
            total += a[i+1];
        }

        long l = 2;
        long r = total + n;
        long x = 0;
        while (l<r){
            x = l+(r-l)/2;
            if (valid(a, x, m)){
                r = x;
            }else{
                l = x+1;
            }
        }

        out.print(l);
    }

    public boolean valid(int[] copya, long x, int people){
        int[] a = new int[copya.length];
        for (int i=0; i<copya.length; i++)
            a[i] = copya[i];

        int index = a.length-1;
        long temp = 0;

        for (int i=0; i<people; i++){
            while (index>0 && a[index]==0)
                index--;
            if (index==0)
                return true;

            temp = index;

            while (index>0 && a[index]+temp<=x){
                temp += a[index];
                a[index] = 0;
                index--;
            }

            if (index==0)
                return true;
            a[index] = a[index] - (int)(x-temp);
        }
        return false;
    }
```