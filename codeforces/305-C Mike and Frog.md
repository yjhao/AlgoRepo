#305-C Mike and Frog
## 题意
给出递推关系 kx+y=z, x = z%m, 起始值和目标值，求出最小的时刻两个同时到达目标值。

## 解题
首先可以看出， 因为是取模， 所以至多M次循环， 数字X就会出现循环, 所以， 如果在M次循环下， 还没有出现目标值， 那么目标值就一定不会出现。

那么如果 x1  x2 在同一时刻都达到了目标值， 那么最短时间就是这个时刻。

反之， 如果他们在不同时刻达到的目标值，时刻标注为 a1, a2， 那么继续求出， 他们分别的第二次的达到目标值的时刻， 第一次和第二次的差值即为 循环大小， 标注为 d1, d2。

那么现在我们就要求出 最小的一个数 h, h=a1+k1d1, h = a2+k2d2。

我们分别对a1 , a2 加上 d1, d2。 但是不是同时加的， 否则会可能出现两个的距离越来越远的情况。

我们只对**当前更小的那一个加上循环值**, 这样的话， 可以保证一定会相遇， 而且相遇的时间即为最小值。

##代码
```public void solve() throws IOException {
        long m = in.nextLong();
        long h1 = in.nextLong();
        long a1 = in.nextLong();
        long x1 = in.nextLong();
        long y1 = in.nextLong();
        long h2 = in.nextLong();
        long a2 = in.nextLong();
        long x2 = in.nextLong();
        long y2 = in.nextLong();

        ArrayList<Long> t1 = new ArrayList<Long>();
        ArrayList<Long> t2 = new ArrayList<Long>();

        for (int i=0; i<2*m; i++){
            if (h1==a1){
                t1.add((long)i);
            }

            if (h2==a2){
                t2.add((long)i);
            }

            if (h1==a1 && h2==a2){
                out.print(i);
                return;
            }
            h1 = (x1*h1 + y1)%m;
            h2 = (x2*h2 + y2)%m;
        }

        if (t1.size()==0 || t2.size()==0){
            out.print("-1");
            return;
        }

        long d1 = t1.get(1) - t1.get(0);
        long d2 = t2.get(1) - t2.get(0);

        long tt1 = t1.get(0); long tt2 = t2.get(0);

        for(int i=0;i<5000000;i++){
            if (tt1==tt2){
                out.print(tt1);
                return;
            }

            if (tt1<tt2){
                tt1 += d1;
            }else{
                tt2 += d2;
            }
        }
        out.print("-1");
    }
```