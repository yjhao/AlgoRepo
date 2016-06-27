#290-D Fox And Jumping（DP GCD 滚动更新值）
##题意
有N个数字，下面分别代表这N个数字的值 和 购买N个数字的费用，要使数字的组合(+x 或者 -x)后，能够组成任意数字，

求最小的花费。

##解题
其实就是组合成 1 就可以了。
而获得1的话 只需要我们选的数的gcd = 1即可。
设 有整数x，y，要使得x y能构造任意一个整数，充要条件就是gcd(x, y)=1

**不断的求与 之前的值的gcd(),滚动更新最小费用**。

##代码
```
public void solve() throws IOException {
        int n = in.nextInt();
        int[] jump = new int[n];
        int[] costs = new int[n];
        for (int i=0; i<n; i++) jump[i] = in.nextInt();
        for (int i=0; i<n; i++) costs[i] = in.nextInt();

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i=0; i<n; i++){
            HashMap<Integer,Integer> next = new HashMap<Integer,Integer>();
            next.putAll(map);

            Iterator<Integer> ite = map.keySet().iterator();
            while (ite.hasNext()){
                int curr = ite.next();
                int cost = map.get(curr);
                curr = gcd(jump[i], curr);
                cost += costs[i];
                if (next.containsKey(curr)){
                    next.put(curr, Math.min(next.get(curr), cost));
                }else{
                    next.put(curr, cost);
                }
            }
            if (next.containsKey(jump[i])){
                next.put(jump[i], Math.min(costs[i], next.get(jump[i])));
            }else{
                next.put(jump[i], costs[i]);
            }
            map.clear();
            map.putAll(next);
        }

        if (map.containsKey(1)){
            out.print(map.get(1));
        }else{
            out.print("-1");
        }
        return;

    }

    int gcd(int a, int b){
        if (a<b)    return gcd(b,a);
        if (a%b==0) return b;
        return gcd(b, a%b);
    }
```