#321-C Kefa and Park (Tree DFS with adjacency list)

# 题意
给一个Tree， 有些node上有标记。求一些leaf node， 从这些leaf node到root的path上面， 连续的有标记的node不超过m个。

# 解题
很明显， 用DFS

不过需要注意的是， 这个tree用的是adjacency list来表示的， 所以并不知道谁是parent,谁是子孙。

所以：

1. 判断leaf node的时候， 条件为：**这个node的邻居只能有一个， 其次， 这个邻居必然是parent**
2. 当DFS遍历的时候， 遍历的下一个Node, 一定不能是当前Node的parent， 不然就又遍历回去了， 会形成Cycle。

# 代码
```
public void solve() throws IOException {
        int n = in.nextInt();
        int m = in.nextInt();
        int[] cat = new int[n];
        for (int i=0; i<n; i++){
            cat[i] = in.nextInt();
        }

        ArrayList<ArrayList<Integer>> tree = new ArrayList<ArrayList<Integer>>();
        for (int i=0; i<n; i++){
            ArrayList<Integer> list = new ArrayList<>();
            tree.add(list);
        }

        for (int i=0; i<n-1; i++){
            int a = in.nextInt()-1;
            int b = in.nextInt()-1;
            tree.get(a).add(b);
            tree.get(b).add(a);
        }

        DFS(tree, 0, m, cat, 0, -1);

        out.print(res);
    }

    public void DFS(ArrayList<ArrayList<Integer>> tree, int idx, int m, int[] cat, int curCat, int parent){
        // leaf node
        if (tree.get(idx).size()==0 || (tree.get(idx).size()==1 && tree.get(idx).get(0)==parent)){
            if (curCat + cat[idx] <= m ){
                res++;
                //out.println("the node is " + (idx+1));
            }
            return;
        }

        if (cat[idx]==1){
            curCat++;
            if (curCat>m){
                return;
            }
        }else{
            curCat = 0;
        }

        for (int i=0; i<tree.get(idx).size(); i++){
            int next = tree.get(idx).get(i);
            if (next==parent) continue;
            DFS(tree, next, m, cat, curCat, idx);
        }

        return;
    }
```