# 212-Word Search II (Trie)

## 题意
在一个2D grid里面找一个List中的string存不存在。

## 解题
如果只需要寻找一个String， 简单的对每一个grid cell 进行DFS就可以了， 复杂度为O(nmnm)。

然后这里有很多String需要寻找，很自然的， 就想到了trie！！！

建立trie的时候， 要在有单词的节点， 加入单词。

**用一个指针array保存一个节点的子孙，如果一个字母存在于子孙之中， 将这个字母在指针array中实例化， 否则默认为Null。** 

DFS的时候， 先看这个地方在trie中有没有节点， 如果没有节点， 不用继续寻找了， 直接返回

再看trie这个节点上有没有单词， 有单词的话， 加入答案之中。 为了避免重复， 将这个节点上的单词删掉。

DfS递进的时候， 把当前trie节点放到下一层中， 就不用每次重复搜索了。

## 代码
```
public class Solution {
    int[] dx = {0,0,1,-1};
    int[] dy = {1,-1,0,0};
    
    public List<String> findWords(char[][] board, String[] words) {
        ArrayList<String> res = new ArrayList<String>();
        TrieNode root = buildTrie(words);
        
        for (int i=0; i<board.length; i++){
            for (int j=0; j<board[0].length; j++){
                boolean[][] visited = new boolean[board.length][board[0].length];
                visited[i][j] = true;
                DFS(board, i, j, root, res, visited);
            }
        }
        
        return res;
        
    }
    
    public void DFS(char[][] board, int row, int col, TrieNode root, ArrayList<String> res, boolean[][] visited){
        char c = board[row][col];
        if (root.next[c-'a']==null) return;
        TrieNode p = root.next[c-'a'];
        if (p.word!=null){
            res.add(p.word);
            p.word = null;
        }
        
        for (int i=0; i<4; i++){
            int x = row+dx[i];
            int y = col+dy[i];
            
            if (x<0 || x>=board.length || y<0 || y>=board[0].length || visited[x][y]) continue;
            
            visited[x][y] = true;
            DFS(board, x, y, p, res, visited);
            visited[x][y] = false;
        }
    }
    
    
    public TrieNode buildTrie(String[] words){
        TrieNode root = new TrieNode();
        for (String str : words){
            TrieNode p = root;
            for (Character c : str.toCharArray()){
                int i = c-'a';
                if(p.next[i] == null) 
                    p.next[i] = new TrieNode();
                p = p.next[i];
            }
            p.word = str;
        }
        return root;
    }
    
}

class TrieNode{
    TrieNode[] next = new TrieNode[26];
    String word;
}
```

