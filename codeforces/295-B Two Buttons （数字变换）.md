#295-B Two Buttons （数字变换）

##题意
给两个数 n, m, 可以对n做两种变换， 乘以2， 或者减一。 问从 n 变换到 m 的最短步数是多少。

##解题, 方法一
可以使用BFS来进行搜索， 每一个数都是一个node。
总的 candidates 可以用 array 来表示。

因为 n, m < 100000， 所以 array 的长度 2*100000+1 就足够了。

复杂度 O(100000)

##解题， 方法二
从 n 变换到 m， 也可以理解为从 m 变换到 n， 两种变换为 除以2 或者 加一。

设想 先 加一， 再加一， 再除以二， 与 除以二， 再加一的答案是一样的。

**这个现象说明： “加一”操作， 如果连续两个或者以上出现， 一定是因为 没有 “除以2” 的操作可以进行了。 不然一定会得到更优的解。** 而这样的情况， 仅仅发生在 n 小于 m 的时候。

这个现象给我们的启发就是：当n>m的时候， 如果可以除以二， 一定先除以二， 如果遇到奇数不能除以2了， 加上1， 再除以二。如果n<m， 直接加上m-n。

所以：
1. 如果 n < m， 直接加上 m-n 即为答案。
2. 否则： 如果 n 为偶数， 除以二； 如果为奇数， 加上一， 再除以二， 操作数分别加一， 与加二。 直到 n<=m;

时间复杂度 O(logn)。

##代码
```
public void solve() throws IOException {
        int n = in.nextInt();
        int m = in.nextInt();

        /* BFS
        int[] level = new int[2*10000+5];
        boolean[] visited = new boolean[2*10000+5];

        LinkedList<Integer> queue = new LinkedList<>();
        queue.offer(n);
        visited[n] = true;
        level[n] = 0;

        while (!queue.isEmpty()){
            int now = queue.poll();
            int next1 = now-1;
            if (next1>0 && next1<=2*10000+5){
                if (!visited[next1]){
                    queue.offer(next1);
                    visited[next1] = true;
                    level[next1] = level[now]+1;
                }
            }


            int next2 = now*2;
            if (next2>0 && next2<=2*10000+5){
                if (!visited[next2]){
                    queue.offer(next2);
                    visited[next2] = true;
                    level[next2] = level[now]+1;
                }
            }


            if (m==next1 || m==next2){
                out.print(level[now]+1);
                return;
            }
        }
        */

        int count = 0;
        while (n!=m){
            if (n>m){
                count += n-m;
                break;
            }else if (n<m){
                if (m%2==0){
                    m = m/2;
                    count++;
                }else{
                    m = (m+1)/2;
                    count = count+2;
                }
            }
        }

        out.print(count);

    }
```

