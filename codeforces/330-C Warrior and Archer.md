#330-C Warrior and Archer
## 题意
Archer和Warrior在PK，有n个位置可以选。每轮他们轮流禁用一个位置，到最后剩下两个位置。Archer希望距离尽量远，Warrior希望距离尽量近。问最后距离是多少

## 解题
首先我们可以一步一步来判断， 不过情况很复杂， 不容易判断。 所以我们直接就从最后的情况来判断： 最后还剩下一左一右两个， 他们直接的差距 叫”范围“

所以 warrior只会ban范围之外的数。 为什么呢？ 因为如果他ban了范围之内的数， 最后的范围只可能比当前范围大

archer只会ban范围之内的数。 为什么呢？ 因为如果他ban了范围之外数， 最后的范围会比当前范围小， 而且archer总是会ban掉距离更近的点， 不会去Ban距离远的点。

所以这样看来， 范围的长度就是每个人ban的次数。

因为Warrior先手，所以答案是arr[R] - arr[L]的**最小值**。 否则是最大值

## 代码
```
public void solve() throws IOException {
        int n = in.nextInt();
        int[] x = new int[n];
        for (int i=0; i<n; i++){
            x[i] = in.nextInt();
        }

        Arrays.sort(x);

        int space = (n-2)/2;
        int res = x[space+1]-x[0];

        for (int i=0; i<n; i++){
            int idx = i+space+1;
            if (idx<n){
                res = Math.min(res, x[idx]-x[i]);
            }
        }

        out.print(res);

    }
```