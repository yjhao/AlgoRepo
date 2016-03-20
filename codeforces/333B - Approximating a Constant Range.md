# 333B - Approximating a Constant Range

## 题意：
求最大区间长度  区间要求满足：区间最大值与最小值的差小于等于1。相邻两个数相差<=1

## 解题：
相邻两个数的Difference 只能是1 -1 或者0。 区间最大值与最小值相差小于等于1， 那么difference数列中，不能连续出现1，1或者 -1 -1。 可以1 -1 1 -1, 中间也可以夹杂无限多0。

所以当连续的两个差值相等时，该段区间结束，更新最大值。

类似于double pointer window， 更新最大值后， 将左pointer往右移。 那么移到哪里么？ 移到 连续的两个差值中 第一个差值结束的地方。

### 注意的地方
1. 所有数字相同
2. double pointer 求窗口大小的时候， 右窗口指针最后会跳出while循环， 此时一定记住还要最后再计算一次当前窗口。

##  代码
```
public void solve() throws IOException {
    int n = in.nextInt();
    int[] a = new int[n];
    for (int i=0; i<n; i++){
        a[i] = in.nextInt();
    }

    int prev_diff = 0;
    int cur_diff  = 0;
    int l = 0;
    int r = 0;
    int res = 0;
    boolean allSame = true;

    for (int i=1; i<n; i++){
        cur_diff = a[i] - a[i-1];

        if (cur_diff==0){
            continue;
        }

        allSame = false;

        if (cur_diff!=prev_diff){
            r = i;
            prev_diff = cur_diff;
        }else{ // the same difference: -1 -1 or +1 +1
            res = Math.max(res, i-l);
            l = r;
            r = i;
        }
    }

    if (allSame){
        out.print(n);
    }else{
        out.print(Math.max(res, n-l));
    }
}
```
