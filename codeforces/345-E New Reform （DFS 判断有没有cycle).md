#345-E New Reform （DFS 判断有没有cycle）
## 题意
有一个graph, 定义一种vertex为只有出没有进， 求这种vertex最少有多少个。

## 解题
1. 如果这个graph没有cycle， 那么这种vertex有一个， 在首尾中的一个。
2. 如果这个graph有cycle, 那么我们先去掉cycle中的a-b，然后从a或者B中的一点dfs, 最后会发现a或者b只有出没有进， 那么把a和b一相连， 这个vertex就没有了。 所以这种情况下， 不会有这种Vertex.

把graph分成若干个connected component， 然后依次进行DFS, 判断有没有cycle。

## 代码

```
ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>();
    boolean[] visited;

    public void solve() throws IOException {
        int n = in.nextInt();
        int m = in.nextInt();

        for (int i=0; i<n; i++){
            ArrayList<Integer> list = new ArrayList<>();
            graph.add(list);
        }

        for (int i=0; i<m; i++){
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            graph.get(a).add(b);
            graph.get(b).add(a);
        }

        int res = 0;

        visited = new boolean[n];
        for (int i=0; i<n; i++){
            if (visited[i]) continue;
            if (!DFS_hasCycle(i, -1)){
                res++;
            }
        }
        out.print(res);
    }

    public boolean DFS_hasCycle(int root, int parent){
        if (visited[root]){
            return true;
        }else{
            visited[root] = true;
        }

        for (int i=0; i<graph.get(root).size(); i++){
            int j = graph.get(root).get(i);
            if (parent!=j && DFS_hasCycle(j, root)){
                return true;
            }
        }

        return false;
    }
```

 