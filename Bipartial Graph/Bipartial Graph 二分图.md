#Bipartial Graph 二分图

##有两个例题

##例题一
http://www.tuwenzhai.com/d/94558250-815c-4b50-95f3-68d2ed883cd1/57538994-e86c-442a-9176-be5d84b4dc6e

一个Graph， 可以用两个颜色来给node染色，问可不可以存在一种方案， 使得 每一条边两端的顶点颜色都不相同。

那么，我们不妨将所有的点初始为未染色的状态。随机选择一个点，将其染成白色。再以它为起点，将所有相邻的点染成黑色。再以这些黑色的点为起点，将所有与其相邻未染色的点染成白色。不断重复直到整个图都染色完成。

在染色的过程中，我们应该怎样发现错误的记录呢？相信你一定发现了吧。对于一个已经染色的点，如果存在一个与它相邻的**已染色点**和它的颜色相同，那么就一定存在一条错误的记录。

到此我们就得到了整个图的算法：

1. 选取一个未染色的点u进行染色
2. 遍历u的相邻节点v：若v未染色，则染色成与u不同的颜色，并对v重复第2步；3.
3. 若v已经染色，如果 u和v颜色相同，判定不可行退出遍历。
4. 若所有节点均已染色，则判定可行。 

总结来看， 就是 DFS, 看有没有重复。

##例题二
http://codeforces.com/contest/664/problem/D

给定一个图，最开始每条边都被染成了两种颜色中的一种，每次可以选择一个点，改变与这个点相连的所有边的颜色。问最少花多少次操作可以使所有边的颜色相同。或者不存在这样的操作。

如果一条边的颜色与最终的颜色不同， 那么两个顶点 至多 只能选择一个染色。而且染色结果与顺序无关。

这样我们就把 整个node 分成了 两个Set， 一个 set 是 需要变化的， 一个set是不需要变动的， 所求的即为 最小的 一个 set 的大小。

--
对于每一个 Node， 我们用 array 给它记录一个 set 的 编号 （1 或者 2， 分别代表 改变 和 不改变）， 初始的一个 node  为1。 然后
我们对于这个 Node 开始进行 BFS， 如果 某条 edge 颜色与要求的不一样， 那么 下一个端点 一定是 和 当前 node 的 Set 的编号不一样， 【可以用 3-val 来表示（ 3-1 = 2， 3-2= 1）。】。 如果这个 Node 之前已经访问过了， 而且 Set 编号不是 3-val， 那么说明这样的方案是不存在。 反之， 将这个 Node 加入 set 1 或者 2之中，并加入 queue， 继续进行 bfs。

最后我们可以得到 set 1, set 2 的分别 node 数， 然后我们就可以知道最小的 一个 set 是多少了。

需要注意的是， 我们不知道 最后的一致颜色是红色还是蓝色， 所以对于两种颜色， 需要分别的运行这个 bfs。

而且 grah 不一定是联通的， 所以需要对每个node进行判断。

##代码
```
http://codeforces.com/contest/664/status/D/page/2?order=BY_ARRIVED_DESC
```

alexrcoleman

```
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class D {
	public static void main(String[] args) throws IOException {
		FastScanner scan = new FastScanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		int n = scan.nextInt(), m = scan.nextInt();
		int[] degs = new int[n];
		ArrayList<Edge>[] es = new ArrayList[n];
		for(int i=0;i<n;i++)
			es[i] = new ArrayList<Edge>();
		for (int i = 0; i < m; i++) {
			int u = scan.nextInt()-1, v = scan.nextInt()-1;
			boolean red = scan.next().charAt(0) == 'R';
			degs[u]++;
			degs[v]++;
			Edge e1 = new Edge(u, v, red);
			Edge e2 = new Edge(v, u, red);
			es[u].add(e1);
			es[v].add(e2);
		}
		ArrayList<Integer> ans1 = solve(es,false), ans2 = solve(es,true);
		ArrayList<Integer> ans;
		if(ans1 == null)
			ans = ans2;
		else if(ans2 == null)
			ans = ans1;
		else if(ans1.size() < ans2.size())
			ans = ans1;
		else
			ans = ans2;

		if(ans == null) {
			out.println("-1");
		} else {
			out.println(ans.size());
			for(int i : ans) {
				out.print(i+1);
				out.print(" ");
			}
			out.println();
		}
		out.close();
	}

	public static ArrayList<Integer> solve(ArrayList<Edge>[] es, boolean red) {
		int[] colors = new int[es.length];
		ArrayList<Integer> ans = new ArrayList<>();

		for (int seed = 0; seed < es.length; seed++) {
			if (colors[seed] != 0)
				continue;
			ArrayList<Integer>[] nodesByColor = new ArrayList[3];
			for(int i=0;i<nodesByColor.length;i++)
				nodesByColor[i] = new ArrayList<Integer>();
			ArrayDeque<Integer> bfs = new ArrayDeque<>();
			bfs.offer(seed);
			colors[seed] = 1;
			while (!bfs.isEmpty()) {
				int node = bfs.poll();
				nodesByColor[colors[node]].add(node);
				for (Edge e : es[node]) {
					int tcolor = colors[node];
					if (e.red != red)
						tcolor = 3 - tcolor;
					if (colors[e.v] != tcolor && colors[e.v] != 0) {
						return null;
					}
					if (colors[e.v] != 0)
						continue;
					colors[e.v] = tcolor;
					bfs.offer(e.v);
				}
			}
			if(nodesByColor[1].size() < nodesByColor[2].size()) {
				ans.addAll(nodesByColor[1]);
			} else {
				ans.addAll(nodesByColor[2]);
			}
		}
		return ans;
	}

	static class Edge {
		int u, v;
		boolean red;

		public Edge(int u, int v, boolean red) {
			this.u = u;
			this.v = v;
			this.red = red;
		}
	}
	static class FastScanner {
		BufferedReader br;
		StringTokenizer st;
		public FastScanner(InputStream in) {
			br = new BufferedReader(new InputStreamReader(in));
			st = new StringTokenizer("");
		}
		public String next() throws IOException {
			if(!st.hasMoreTokens()) {
				st = new StringTokenizer(br.readLine());
				return next();
			}
			return st.nextToken();
		}
		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}
	}
}
```