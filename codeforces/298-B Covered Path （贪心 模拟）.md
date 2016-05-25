#298-B Covered Path （贪心 模拟）
## 题意
给你初速度v1和末速度v2，给你运动时间，和最大加速度
当然，每一秒的速度都是整数，然后问你在这种情况下，路程最大是多少。

限制条件： 每两秒之间的速度最大差距为d

## 解题
最开始， 我的想法是从小的v1出发， 一直加d， 直到小于v2的 最接近 的位置， 然后再根据 剩下的时间的奇偶性 来模拟一个“弹簧”。先变大 再缩小， 最后回到v2

不过实现起来确实很麻烦。。


所以用另外一个方法， 贪心。

对于v1， 加上一个加速度j（范围从 d 到 -d），使得 v1+j， 在剩下的时间中， **还能**回到v2。 j 要从大到小的尝试， 保证速度是最优的。 要回到v2， 当前距离 小于等于 v2 + 剩余时间 * d

## 代码
```
public void solve() throws IOException {
        int v1 = in.nextInt();
        int v2 = in.nextInt();
        int t  = in.nextInt();
        int d  = in.nextInt();

        // make sure v1 < v2
        if (v1>v2){
            int temp = v1;
            v1 = v2;
            v2 = temp;
        }

        int count = v1;

        for (int i=2; i<=t; i++){
            for (int j = d; j>=-d; j--){
                if (v1+j <= v2 + (t-i)*d){
                    v1 =  v1+j;
                    break;
                }
            }
            count += v1;
        }
        out.print(count);

    }
```