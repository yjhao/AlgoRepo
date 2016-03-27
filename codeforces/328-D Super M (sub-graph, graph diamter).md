# 328-D Super M (sub-graph, graph diamter)

# 题意
给一个graph， （其实是一个Tree), 然后在里面圈出来一些点， 求联通这些所有点的最短距离。

# 解题
几个要点。

1. 首先我们要找出包括这些所有目标点的最小的sub-graph。
2. 如果我们从一个点出发， 遍历完所有目标点， 最后再回到这个点， 路线长度为2M, M为这个sub-graph的edge number。
3. 如果不回到这个点， 则路线长度为2M-L, L为这个sub-graph之中， 两个target点相隔的最远距离。
4. 所以这个问题化为： 找sub-graph, 找M和最大的L， 即求得最小的2M-L。


# 如何找到sub-tree(sub-graph）和M
首先找到一个目标点， 然后从这个点开始DFS。(想象把这个点“像一张布一样提起来”

这个目标点的parent设为-1。

完成Dfs之后， 从其它目标点开始， 用while loop track up parent until find -1。 这之中遇到的所有edge， 所有点都会被最后的sub-tree包括。 

技巧： DFS剪枝， 如果遇到一个node已经被"covered"了， 则break；

然后Traverse所有edge, 如果edge的两个端点， 其中有一个没有被covered， 就删掉这个edge.

用Edge的总数， 减去删掉的edge数量， 就是M了。

# 如何找到L
找L, 就是找相邻最远的两个node。

首先从任何一个目标点开始bfs， 求得到各个目标点的距离， 然后找到这个最远的点a。

然后从这个最远的点a， 再做一次bfs， 求得到各个目标点的距离， 然后找到最远的点b。

a和b之间的路径即为最长路径。


# 如何用DFS找到M
限制： 只能是tree, 不能有cycle， 不然DfS不能判断“最短距离”。

这个问题可以理解为， 如何在一个tree中， 圈定一些点， 然后找到连接这些点的edge number.

关键问题就是， 如何确定一个node是在sub-graph中， 还是在sub-graph之外。

如果这个node的之后的所有node都不是目标点，而且他自己也不是目标点， 那么这个node就不用考虑。

代码：

```
private static int NumOfEdges(Node node, boolean[] visited, boolean[] attackedCity) {

		// TODO Auto-generated method stub
		visited[node.index] = true;
		int c = 1;
		boolean requiredPart = attackedCity[node.index];
		for (Node nodeye : node.neighbours)
			if (!visited[nodeye.index]) {
				int temp = NumOfEdges(nodeye, visited, attackedCity);
				if (temp > 0) {
					c += temp;
					requiredPart = true;
				}
			}
		return requiredPart ? c : 0;
	}
```


# 解题代码：

```
static class solver {
        private ArrayList<ArrayList<Integer>> adj;
        private boolean[] attacked;
        private boolean[] removed;

        public void dfs(int v, int[] parent) {
            for (int i = 0; i < adj.get(v).size(); i++) {
                int u = adj.get(v).get(i);
                if (u == parent[v])
                    continue;
                else {
                    parent[u] = v;
                    dfs(u, parent);
                }
            }
        }

        public void findToRemove() {
            int v = 0;
            for (int i = 0; i < attacked.length; i++)
                if (attacked[i]) {
                    v = i;
                    break;
                }
            int[] parent = new int[adj.size()];
            parent[v] = -1;
            dfs(v, parent);
            boolean[] covered = new boolean[adj.size()];

            for (int i = 0; i < attacked.length; i++) {
                if (attacked[i]) {
                    int req = i;
                    while (parent[req] != -1) {

                        req = parent[req];
                        if (covered[req])
                            break;
                        attacked[req] = true;
                        covered[req] = true;
                    }
                }
            }
            for (int i = 0; i < adj.size(); i++)
                if (!attacked[i])
                    removed[i] = true;
        }

        public int[] findShortestPaths(int start) {

            boolean visited[] = new boolean[adj.size()];
            int distance[] = new int[adj.size()];

            for (int i = 0; i < distance.length; i++) {
                distance[i] = Integer.MAX_VALUE;
            }

            distance[start] = 0;
            Queue<Integer> queue = new LinkedList<>();
            queue.add(start);

            while (!queue.isEmpty()) {
                int u = queue.poll();
                visited[u] = true;
                for (int i = 0; i < adj.get(u).size(); i++) {
                    int v = adj.get(u).get(i);
                    if (!removed[v] && !visited[v]) {
                        distance[v] = distance[u] + 1;
                        queue.add(v);
                        visited[v] = true;
                    }
                }
            }
            return distance;

        }

        public void solve(int testNumber, sc in, PrintWriter out) {
            int n = in.nextInt();
            int m = in.nextInt();
            adj = new ArrayList<>();
            for (int i = 0; i < n; i++)
                adj.add(new ArrayList<>());

            ArrayList<p> arrayList = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) {
                int a = in.nextInt();
                int b = in.nextInt();
                a--;
                b--;
                adj.get(a).add(b);
                adj.get(b).add(a);


                arrayList.add(new p(a, b));
            }
            attacked = new boolean[n];
            removed = new boolean[n];
            if (m == 1) {
                out.println(in.nextInt());
                out.println("0");
                return;
            }
            for (int i = 0; i < m; i++)
                attacked[in.nextInt() - 1] = true;


            findToRemove();


            int start = 0;
            for (int i = 0; i < n; i++) {
                if (attacked[i]) {
                    start = i;
                    break;
                }
            }

            int removedEdges = 0;
            for (p ar : arrayList)
                if (removed[ar.x] || removed[ar.y])
                    removedEdges++;


            int distance[] = findShortestPaths(start);
            int maxDistance = 0;
            int farthestNode = 0;
            for (int i = 0; i < n; i++) {
                if (!removed[i]) {
                    if (distance[i] > maxDistance) {
                        maxDistance = distance[i];
                        farthestNode = i;
                    }
                }
            }

            distance = findShortestPaths(farthestNode);

            int diameter = 0;
            int ans1 = 0;
            for (int i = 0; i < n; i++) {
                if (!removed[i]) {
                    if (distance[i] > diameter) {
                        diameter = distance[i];
                        ans1 = i;
                    }
                }
            }

            ans1 = Math.min(ans1, farthestNode);
            int remainingEdges = n - 1 - removedEdges;
            int ans2 = remainingEdges*2 - diameter;
            out.println(ans1 + 1);
            out.println(ans2);

        }

        static class p {
            public int x;
            public int y;

            public p(int x, int y) {
                this.x = x;
                this.y = y;
            }

        }
```

