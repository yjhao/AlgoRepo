#301-C Ice Cave (分情况BFS/DFS)
## 题意
告诉起点终点，踩一次, ‘.‘变成‘X‘，再踩一次，冰块破碎，问是否能使终点冰破碎。

##解题
BFS 或者 DFS 分情况讨论

1. 如果终点和起点重合， 那么只需要邻居中有一个'.'， 那么的话， 可以踩出去， 又踩回来， 从而踩破重点冰。
2. 如果终点冰和起点冰相邻， a). 如果终点是"X", 一定可以踩破. b). 如果是 '.'， 那么终点冰的邻居需要**至少一个**'.'， “踩出去， 又踩回来”
3. 一般情况， 至少需要能够遍历到终点冰， 使用BFS或者DFS

	如果终点是'.', 需要 终点冰的邻居中， 至少有两个‘.'： 其中一个是用来第一次遍历到终点， 第二个是踩上去之后又踩回终点冰， 然后踩破终点冰。
	如果终点是'X'， 那么一定可以踩破。
	
##代码
```
public void solve() throws IOException {
        int[] dx = {1,-1,0,0};
        int[] dy = {0,0,1,-1};
        int n = in.nextInt();
        int m = in.nextInt();
        char[][] c = new char[n][m];
        for (int i=0; i<n; i++){
            char[] cur = in.next().toCharArray();
            for (int j=0; j<m; j++){
                c[i][j] = cur[j];
            }
        }

        int r1 = in.nextInt()-1; int c1 = in.nextInt()-1;
        int r2 = in.nextInt()-1; int c2 = in.nextInt()-1;
        boolean flag = false;

        // check the neighbor of dest
        int intact_count = 0;
        for (int i=0; i<4; i++){
            int x = r2+dx[i];
            int y = c2+dy[i];

            if (x==r1 && y==c1){
                flag = true;
            }

            if (x<n && x>=0 && y<m && y>=0 && c[x][y] == '.')
                intact_count ++;
        }

        // if init == dest
        if (r1==r2 && c1==c2){
            if (intact_count >= 1){
                out.print("YES");
            }else{
                out.print("NO");
            }
            return;
        }

        // if init is dest's neighbor
        if (flag){
            if (c[r2][c2]=='X'){
                out.print("YES");
            }else{
                if (intact_count>=1){
                    out.print("YES");
                }else{
                    out.print("NO");
                }
            }
            return;
        }

        // genera situation
        if (BFS(c, r1, c1, r2, c2)){
            if (c[r2][c2]=='X'){
                out.print("YES");
            }else{
                if (intact_count>=2){
                    out.print("YES");
                }else{
                    out.print("NO");
                }
            }
            return;
        }

        out.print("NO");
    }

    public boolean BFS(char[][] c, int r1, int c1, int r2, int c2){
        int[] dx = {1,-1,0,0};
        int[] dy = {0,0,1,-1};
        int n = c.length;
        int m = c[0].length;
        LinkedList<Integer> list = new LinkedList<>();
        list.add(r1*m + c1);

        while (!list.isEmpty()){
            int cur = list.poll();
            int row = cur/m;
            int col = cur%m;

            for (int i=0; i<4; i++){
                int x = row + dx[i];
                int y = col + dy[i];

                if (x==r2 && y==c2)
                    return true;

                if (x>=0 && x<n && y>=0 && y<m && c[x][y] == '.'){
                    c[x][y] = 'X';
                    list.offer(x*m + y);
                }
            }
        }
        return false;
    }
```