#331-C Wilbur and Points

# 题意
题目大意：给n对数 a (xi, yi)每对都不同且，每对都要赋一个值vi(1-n)，给n个值，现在问能否存在一种顺序使得yi - xi = wi，且对于任意的x[i] <= x[j]且y[i] <= y[j], 在v中对应的顺序， i要比j要小。

# 题解
w顺序是固定的， 所以就想办法怎么变换a序列， 让他满足w的顺序， 还要满足其应对应的顺序。

对于每一个y-x的值， 维护一个PriorityQueue， 把靠近原点的放前面。 这样的话，如果在w遇到一个对应的y-x的值， 就可以greedy的取靠近原点的值。

得到a的顺序之后， 还要检查一遍， 是否符合规矩。

###两个方法###
1)
题目中说道： Also, it is guaranteed that if some point (x, y) is present in the input, then all points (x', y'), such that 0 ≤ x' ≤ x and 0 ≤ y' ≤ y, are also present in the input.


所以对于(x,y), 如果(x-1,y) (x,y-1)不在之前的结果中， 那么肯定是错误的。
	
2)
取两个连续的一前一后的pair, 如果后面那个的y x **都** 小于前面这个的y x， 那么肯定是错误的。 

**有点类似 寻找递增数列的断点**。 如果哪一个数比前一个数字小， 那么一定不是递增的。 反过来说， 如果是递增的， 一定不会有哪一个数比前一个小。

比如三个数， a, b, c.

**c不小于b， b不小于c， 那么c一定不会小于a**

那么有没有可能前面的判断没有找到False, 但是最终仍然不满足呢？ 不会的， 如果a<b b<c, c不会<a


"然后进行判断，因为数据点没有突变，只要严格意义上不比前一个数小就行，因为都是连续的数据段。"

## 代码
```
public void solve() throws IOException {
        int n = in.nextInt();
        HashMap<Integer, PriorityQueue<node>> map = new HashMap<Integer, PriorityQueue<node>>();
        node[] p = new node[n];
        int[] w = new int[n];

        for (int i=0; i<n; i++){
            int x  = in.nextInt();
            int y  = in.nextInt();
            node cur = new node(x,y);
            int c = y-x;
            if (map.containsKey(c)){
                map.get(c).offer(cur);
            }else{
                PriorityQueue<node> queue = new PriorityQueue<>(new Comparator<node>(){
                   public int compare(node a, node b){
                       return a.y - b.y;
                   }
                });
                queue.offer(cur);
                map.put(c, queue);
            }
        }



        List<node> res = new ArrayList<>(100000);
        for (int i=0; i<n; i++) {
            int wval = in.nextInt();
            PriorityQueue<node> pq = map.get(wval);
            if (pq == null || pq.isEmpty() || Math.abs(wval) > i){
                System.out.println("NO");
                return;
            } else {
                res.add(pq.poll());
            }
        }

        for (int i=0; i<n-1; i++){
            node a = res.get(i);
            node b = res.get(i+1);
            if (b.x < a.x && b.y < a.y) {
                System.out.println("NO");
                return;
            }
        }

        System.out.println("YES");
        for (int i=0; i<n; i++)
            System.out.println(res.get(i));


    }
```