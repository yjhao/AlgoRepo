#321-B Kefa and Company (two pointers)

# 题意
给n,d两个整数,给n个pair<first,second>, 让你找second的和最大的pair组, 满足first之间的差值不大于d. 返回sum.

# 解题
首先想到bucket sort， 因为差值为d。搜寻每一个pair周围范围d之内的pair。

不过， 还有更好的方法， 就是two pointer。

维护一个区间， 保证里面的最大值减去最小值不超过d， 然后递进L， 相应的递减R

#  代码
```
public void solve() throws IOException {
        int n = in.nextInt();
        int d = in.nextInt();
        person_321[] p = new person_321[n];
        for (int i=0; i<n; i++){
            int money = in.nextInt();
            int friend = in.nextInt();
            person_321 cur = new person_321(money, friend);
            p[i] = cur;
        }

        Arrays.sort(p);

        int l = 0;
        long total = 0;
        int r = l;
        long curTotal = 0;

        // initial window
        int lmoney = p[l].money;

        while (r < n && (p[r].money - lmoney < d)) {
            curTotal += p[r].friend;
            r++;
        }
        total = Math.max(curTotal, total);

        if (r==n){
            out.print(total);
            return;
        }

        // increment l, and inrement r accordingly

        while (l<n){
            int minusFriend = p[l].friend;
            curTotal -= minusFriend;
            l++;
            if (l==n){
                break;
            }
            lmoney = p[l].money;
            while (r<n && (p[r].money - lmoney < d)){
                curTotal += p[r].friend;
                r++;
            }
            total = Math.max(curTotal, total);

            if (r==n){
                out.print(total);
                return;
            }
        }

        out.print(total);
    }
    
class person_321 implements Comparable<person_321>{
    int money;
    int friend;

    public person_321(int money, int friend){
        this.money = money;
        this.friend = friend;
    }


    public int compareTo(person_321 obj){
        if (money == obj.money){
            return 0;
        }else if(money >obj.money){
            return 1;
        }else{
            return -1;
        }
    }
}
```