#259 Walls and Gates (BFS with multipel starts)

# 题意
You are given a m x n 2D grid initialized with these three possible values.

-1 - A wall or an obstacle.

0 - A gate.

INF - Infinity means an empty room. We use the value 231 - 1 = 2147483647 to represent INF as you may assume that the distance to a gate is less than 2147483647.

Fill each empty room with the distance to its nearest gate. If it is impossible to reach a gate, it should be filled with INF.

For example, given the 2D grid:

```
INF  -1  0  INF

INF INF INF  -1

INF  -1 INF  -1

  0  -1 INF INF
```

After running your function, the 2D grid should be:

```
  3  -1   0   1
  
  2   2   1  -1
  
  1  -1   2  -1
  
  0  -1   3   4
```

## 解题
BFS, 找最近距离， 但是有多个起点， 难道要对每一个起点BFS一次， 求最小值么？

不需要！ **只需要一个queue, 将每一个起点都放入这个queue中进行一次的BFS,** 在递进的时候， 如果下一个点的距离大于等于当前点的距离， 那么就不需要访问， 因为不可能得到更短的距离。

## 代码

```
class Solution {
public:
    void wallsAndGates(vector<vector<int>>& rooms) {
        if(rooms.size() == 0)
            return;
        queue<pair<int, int>> q;
        vector<pair<int, int>> dir = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for(int i = 0; i < rooms.size(); i++) {
            for(int j = 0; j < rooms[0].size(); j++) {
                if(rooms[i][j] == 0) {
                    q.push(pair<int, int>(i, j));
                }
            }
        }
        while(!q.empty()) {
            int x = q.front().first, y = q.front().second;
            q.pop();
            for(auto d : dir) {
                int i = x + d.first, j = y + d.second;
                if(i < 0 || i >= rooms.size() || j < 0 || j >= rooms[0].size() || rooms[i][j] <= rooms[x][y] + 1)
                    continue;
                rooms[i][j] = rooms[x][y] + 1;
                q.push(pair<int, int>(i, j));
            }
        }
    }
};
```
