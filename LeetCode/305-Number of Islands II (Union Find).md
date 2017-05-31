# 305-Number of Islands II (Union Find)

## 题意
一个一个加入小岛， 小岛就与邻近的小岛连成大岛。 问岛的数量。

## 解题
很典型的 union find 问题

处理 union find：

1. 压缩路径 parent[i] = parent[parent[i]]
2. weighted merging, 将子孙少的那一个 node 的 parent 设成 子孙多的 node, 并更新 子孙多的那个node的子孙数量


## 细节
最开始初始化的时候， 如果用一维数列表示 parents， 默认的数量是 "0"， 但是由于 0 的真正 parent 也是0， 而其他的node的真正parent都不是0， 所以更新的过程中就会出问题。

解决方法， 倾向于第一个。

1. 只对 地图中 值为 1 的 cell 进行 merge。
2. 或者 将每一个 cell  的 idx 都加上一， 这样就没有 cell 的 index 为 0 了。

## 代码
```
public class Solution {
    int[] dx = {0,0,-1,1};
    int[] dy = {1,-1,0,0};
    
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        List<Integer> res = new ArrayList<Integer>();
        if (positions==null || positions.length==0)     return res;
        Union union = new Union(m, n);
        int[] value = new int[m*n];
        
        for (int i=0; i<positions.length; i++){
            int row = positions[i][0];
            int col = positions[i][1];
            union.add(row, col);
            value[row*n + col] = 1;
            
            for (int j=0; j<4; j++){
                int x = row+dx[j];
                int y = col+dy[j];
                if (x<0 || y<0 || x>=m || y>=n || value[x*n+y]!=1) continue;
                
                if (union.parent(x,y) != union.parent(row, col)){
                    union.merge(x, y, row, col);
                }
            }
            res.add(union.count);
        }
        return res;
    }
}

class Union{
    int[] size;
    int[] parents;
    int row;
    int col;
    int count = 0;
    
    public Union(int row, int col){
        this.row = row;
        this.col = col;
        size = new int[row*col];
        parents = new int[row*col];
    }
    
    public void add(int x, int y){
        size[x*col + y] = 1;
        parents[x*col+y] = x*col+y;
        count++;
    }
    
    public void merge(int x1, int y1, int x2, int y2){
        int idx1 = parent(x1, y1);
        int idx2 = parent(x2, y2);
        
        if (size[idx1]>size[idx2]){
            parents[idx2] = idx1;
            size[idx1] += size[idx2];
        }else{
            parents[idx1] = idx2;
            size[idx2] += size[idx1];
        }
        count--;
    }
    
    
    
    public int parent(int x, int y){
        int idx = x*col+y;
        while (parents[idx]!=idx){
            parents[idx] = parents[parents[idx]];
            idx = parents[idx];
        }
        return idx;
    }
}
```

