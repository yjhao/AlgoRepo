#275-B Friends and Presents 容斥定理
##题意
有两个 friends，需要将 cnt1 个不能整除 x 的数分给第一个friend，cnt2 个不能整除 y 的数分给第二个friend。x 和 y 都是素数。要求求出最小的 v，v 表示可以从1，2，...，v 中取数。

##解题
这个题可以用二分来做， 每次二分的时候， 判断是否valid， 如果valid的话 ， r=m， 否则， l=m+1.

关键就是如何判断 是否 valid。

对于一个v来说， 这之中不能被x整除的数有 v-v/x， 不能被y整除的数有 v-v/y个， 他们分别必须至少大于等于 cnt1, cnt2。

我们可以把这个 set 想象成这样。
 
![alt tag](http://images.cnitblog.com/blog/520218/201410/282253406127871.jpg)

分别的个数现在是有保证了， 但是我们必须还得考虑有可能会出现重复的情况。 因为 v-v/x, 和 v-v/y 之中， 有可能会取到重复的， 所以我们必须得确保这个 set 的总的大小 是至少大于等于 cnt1+cnt2 的。

所以在这里， 要用一下 容斥定理。

这个 set 的大小为 h = v-f1-f2+(f1f2)

所以  h 必须 >= cnt1+cnt2

##代码
```
public void solve() throws IOException {
        cnt1 = in.nextLong();
        cnt2 = in.nextLong();
        x = in.nextLong();
        y = in.nextLong();

        long r = Long.MAX_VALUE;
        long l = 1;

        while (l<r){
            long m = l + (r-l)/2;
            if (valid(m)){
                r = m;
            }else{
                l = m+1;
            }
        }

        out.print(l);

    }

    boolean valid (long m){
        long c1 = m - m/x;
        long c2 = m - m/y;
        long both = m/(x*y);

        long total = m-both;

        if (c1>=cnt1 && c2>=cnt2 && total>=cnt1+cnt2)  return true;
        return false;
    }
```