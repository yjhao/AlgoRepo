#301-B School Marks (构造题）
##题意
有一串长度为n的数列。给你了其中的k个。让你构造剩下n-k个使得他们总和小于x且中位数不小于y

## 解题
首先看原数列中，有多少个数字， 小于y。

如果超过一半的数 "(n-1)/2" 小于y， 那么无论怎么构造， 在最后的n个数里面， 中位数都是小于y的， 肯定不符合要求。

假设现在有m个数字是大于等于y的， 如果m小于 (n-1)/2+1， 那么就需要补全到 (n-1)/2+1 。 但是补充什么数呢？ 因为最后要求总和越小越好， 所以直接用y来补。

补完y之后 (中位数右边的数都已经构造完毕）， 如果还没有够n个数， 那么就需要再补充其他的小于y的数。 因为要求总和越小越好， 所以补充1。

##代码
```
public void solve() throws IOException {
        int n = in.nextInt();
        int k = in.nextInt();
        int p = in.nextInt();
        int x = in.nextInt();
        int y = in.nextInt();

        int[] a = new int[k];
        int sum = 0;
        for (int i=0; i<k; i++){
            a[i] = in.nextInt();
            sum += a[i];
        }

        if (sum + (n-k) > x){
            out.print("-1");
            return;
        }

        Arrays.sort(a);
        int smallThanY = 0;
        for (int i=0; i<k; i++){
            if (a[i]<y){
                smallThanY++;
            }else if(a[i]>=y){
                break;
            }
        }

        if (smallThanY>(n-1)/2){
            out.print("-1");
            return;
        }

        int greatEqualThanY = k-smallThanY;
        ArrayList<Integer> list = new ArrayList<>();
        for (int i=0; i<(n-1)/2 + 1 - greatEqualThanY; i++){
            list.add(y);
            sum += y;
            k ++;
        }

        for (int i=0; i<n-k; i++){
            list.add(1);
            sum += 1;
        }

        if (sum>x){
            out.print("-1");
            return;
        }

        for (Integer i : list){
            out.print(i + " ");
        }
    }
```

