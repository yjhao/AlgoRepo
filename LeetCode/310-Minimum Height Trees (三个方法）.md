# 310-Minimum Height Trees (三个方法）
## 题意
给一个 undirected graph， 找出一个node， 当这个node为root的时候， 这个tree的高度最小。

## 解题, 方法一， 层层删去leaf node
有点类似于 toplogical sort

从 leaf 开始一个一个删除， （可以看成是抛去最外面的一层）。 当只剩下 <=2 node 的时候， 即为最后的答案。

leaf ： edge number = 1

## 细节
因为 是 undirected graph， 所有不能像 course number 这样， 只记录 in edge 或者 out edge，然后只更新 in edge or out edge number。 这里 [a,b]， 对于 a, b， 都需要保存其邻居节点信息。 

而且 对于 更新某一node来说， 当 edge number 只剩下一个的时候， 不能确定到底是哪一个。 （但是如果是 course number， 因为删除的是 in 或者 out 那一面， 所以剩下的是 out 或者 in 那一面， 所以可以很快的找到是哪个邻居节点）

所以， 当删除一个 邻居节点的时候， 在本节点上需要的不是更新节点数， 而是更新 邻居节点 的这个集合。

因为要实现删除， 所以为了快速操作， 使用Set来存储 邻居节点的信息。

## 代码
```
public class Solution {
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> leaves = new ArrayList<Integer>();
        
        if (n==0)    return leaves;
        if (n==1)    return Collections.singletonList(0);
        
        ArrayList<HashSet<Integer>> list = new ArrayList<HashSet<Integer>>();
        for (int i=0; i<n; i++)     list.add(new HashSet<Integer>());
        for (int i=0; i<edges.length; i++){
            list.get(edges[i][0]).add(edges[i][1]);
            list.get(edges[i][1]).add(edges[i][0]);
        }
        
        for (int i=0; i<n; i++){
            if (list.get(i).size()==1){
                leaves.add(i);
            }
        }
        
        int total = n;
        while (total>2){
            total -= leaves.size();
            List<Integer> tempLeaves = new ArrayList<Integer>();
            for (int cur: leaves){
                //remove edges
                int curTo = list.get(cur).iterator().next();
                list.get(curTo).remove(cur);
                if (list.get(curTo).size()==1)
                    tempLeaves.add(curTo);
            }
            leaves = tempLeaves;
        }
        
        return leaves;
    }
}
```

## 方法二， 找到 tree 的直径， root一定在这个直径的path的中点。

使用2个 DFS, 或者 2个 BFS 找到这个树的直径以及path。

BFS 优点：

1. 不存在 stackoverflow
2. 对每一个Node记录pre。
3. 一直操作到 queue.isEmpty（）

DFS 优点：

 1. 快

## BFS代码
```
public class _310 {
    int n;
    List<Integer>[] e;

    private void bfs(int start, int[] dist, int[] pre) {
        boolean[] visited = new boolean[n];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(start);
        dist[start] = 0;
        visited[start] = true;
        pre[start] = -1;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v : e[u])
                if (!visited[v]) {
                    visited[v] = true;
                    dist[v] = dist[u] + 1;
                    queue.add(v);
                    pre[v] = u;
                }
        }
    }

    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        if (n <= 0) return new ArrayList<>();
        this.n = n;
        e = new List[n];
        for (int i = 0; i < n; i++)
            e[i] = new ArrayList<>();
        for (int[] pair : edges) {
            int u = pair[0];
            int v = pair[1];
            e[u].add(v);
            e[v].add(u);
        }

        int[] d1 = new int[n];
        int[] d2 = new int[n];
        int[] pre = new int[n];
        bfs(0, d1, pre);
        int u = 0;
        for (int i = 0; i < n; i++)
            if (d1[i] > d1[u]) u = i;

        bfs(u, d2, pre);
        int v = 0;
        for (int i = 0; i < n; i++)
            if (d2[i] > d2[v]) v = i;

        List<Integer> list = new ArrayList<>();
        while (v != -1) {
            list.add(v);
            v = pre[v];
        }

        if (list.size() % 2 == 1) return Arrays.asList(list.get(list.size() / 2));
        else return Arrays.asList(list.get(list.size() / 2 - 1), list.get(list.size() / 2));
    }
}
```

## DFS代码
```public class Solution {
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> res = new ArrayList<Integer>();
        
        if (n==0)    return res;
        if (n==1)    return Collections.singletonList(0);
        
        ArrayList<ArrayList<Integer>> adj = new ArrayList<ArrayList<Integer>>();
        for (int i=0; i<n; i++){
            ArrayList<Integer> list = new ArrayList<Integer>();
            adj.add(list);
        }
        for (int i=0; i<edges.length; i++){
            int a = edges[i][0];
            int b = edges[i][1];
            adj.get(a).add(b);
            adj.get(b).add(a);
        }
        
        ArrayList<Integer> path = new ArrayList<Integer>();
        ArrayList<Integer> tempPath = new ArrayList<Integer>();
        boolean[] visited = new boolean[n];
        int[] len = {1};
        
        DFS(0, adj, visited, path, tempPath, len, 1);
        
        int endNode = path.get(path.size()-1);
        
        Arrays.fill(visited, false);
        len[0] = 1;
        path = new ArrayList<Integer>();
        tempPath = new ArrayList<Integer>();
        
        DFS(endNode, adj, visited, path, tempPath, len, 1);
        
        int startNode = path.get(path.size()-1);
        
        if (path.size()%2==0){
            res.add(path.get(path.size()/2));
            res.add(path.get(path.size()/2-1));
        }else{
            res.add(path.get(path.size()/2));
        }
        return res;
    }
    
    
    public void DFS(int node, ArrayList<ArrayList<Integer>> adj, boolean[] visited, ArrayList<Integer> path, ArrayList<Integer> tempPath, int[] len, int curLen){
        
        visited[node] = true;
        tempPath.add(node);
        ArrayList<Integer> list = adj.get(node);
        boolean flag = false;
        
        for (int i=0; i<list.size(); i++){
            int nextNode = list.get(i);
            if (visited[nextNode])   continue;
            flag = true;
            DFS(nextNode, adj, visited, path, tempPath, len, curLen+1);
        }
        
        if (!flag && curLen>len[0]){
            path.addAll(tempPath);
            len[0] = curLen;
        }
        
        visited[node] = false;
        tempPath.remove(tempPath.size()-1);
    }
}
```


