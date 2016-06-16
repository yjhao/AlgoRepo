#350-D Magic Powder (二分法， 求左边界）
##题意
你的蛋糕需要n个原材料，你现在有k个魔法材料，魔法材料可以转化为任何材料， 1：1的比例转换

现在告诉你蛋糕每个材料需要多少a[i]，以及你现在有多少个b[i]

问你最多能够做出多少个蛋糕来

## 解题， 原始方法
最naiev的解题， 先找到不用魔法材料， 能够得到的蛋糕数， 然后再依次用魔法材料转换为“当前”缺少的材料。

## 解题， 二分法
不过还可以用更好的方法， 二分法。

先假设 我们可以做 n 个蛋糕， 然后分别对每个材料检查， 要做n个蛋糕， 材料够不够， 如果不够的话， 用魔法材料转换。 如果魔法材料都被用完了还没有完全凑齐材料， 那么肯定是做不了 n 个蛋糕的。 所以 r = mid-1;

反之， 如果材料都配齐了， 魔法材料还没有用完， 说明我们还有潜力。所以是 l = mid；

这里， 出现了 l = mid。 如果 mid 每次求出来都为 l 的话， 有可能死循环。

**所以， 每次 mid  = l +（r-l)/2 + 1**

##代码
```
public void solve() throws IOException {
        int n = in.nextInt();
        int k = in.nextInt();
        int[] a = new int[n];
        int[] b = new int[n];
        for (int i=0; i<n; i++){
            a[i] = in.nextInt();
        }
        for (int i=0; i<n; i++){
            b[i] = in.nextInt();
        }

        int min = Integer.MAX_VALUE;
        for(int i=0; i<n; i++){
            min = Math.min(min, b[i]/a[i]);
        }

        int max = min + k + 1;

        while (min<max){
            long mid = min + (max-min)/2+1;

            if (sufficient(a, b, mid, n, k)){
                min = (int)mid;
            }else{
                max = (int)mid-1;
            }
        }
        out.print(min);
    }

    public boolean sufficient(int[] a, int[] b, long mid, int n, int k){
        long count = 0;
        for (int i=0; i<n; i++){
            if (b[i]/a[i]>=mid) continue;
            count += a[i]*mid - b[i];
            if (count>k)    return false;
        }
        return true;
    }
```

