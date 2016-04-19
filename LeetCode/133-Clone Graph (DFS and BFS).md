#133-Clone Graph (DFS and BFS)

## 题意
每一个node都有unique的lable, 复制这个graph


## DFS解题
DFS 比 BFS 快得多

此处DFS 夹带了**剪枝**， 当DFS的过程中， 如果碰到了一个之前已经访问过的node， 直接返回这个Node， 因为在这个node的所有添加邻居的操作， **都已经在， 或会在，**  访问这个node的时候进行。

```
public class Solution {
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        HashMap<Integer, UndirectedGraphNode> map = new HashMap<>();
        
        return helper(node, map);
    }
    
    public UndirectedGraphNode helper(UndirectedGraphNode node, HashMap<Integer, UndirectedGraphNode> map){
        if (node==null) return null;
        
        if (map.containsKey(node.label)){
            return map.get(node.label);
        }
        
        UndirectedGraphNode cloneNode = new UndirectedGraphNode(node.label);
        map.put(node.label, cloneNode);
        
        for (UndirectedGraphNode neighbor : node.neighbors){
            UndirectedGraphNode thisCloneNode = helper(neighbor, map);
            cloneNode.neighbors.add(thisCloneNode);
        }
        
        return cloneNode;
    }
}
```

## BFS解题
BFS， 使用queue对Node一个一个复制

**特别注意**

**因为是BFS, 而且是graph 有 Loop， 所以在添加入queue的时候，一定检查之前是否已经添加过！**

```
/**
 * Definition for undirected graph.
 * class UndirectedGraphNode {
 *     int label;
 *     List<UndirectedGraphNode> neighbors;
 *     UndirectedGraphNode(int x) { label = x; neighbors = new ArrayList<UndirectedGraphNode>(); }
 * };
 */
public class Solution {
    public UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
        HashMap<Integer, UndirectedGraphNode> map = new HashMap<>();
        
        if (node==null) return node;
        
        LinkedList<UndirectedGraphNode> queue = new LinkedList<>();
        
        HashSet<Integer> visited = new HashSet<>();
        queue.offer(node);
        visited.add(node.label);
        
        UndirectedGraphNode head = new UndirectedGraphNode(node.label);
        map.put(node.label, head);
        
        
        
        while(!queue.isEmpty()){
            UndirectedGraphNode cur      = queue.poll();
            UndirectedGraphNode curClone = map.get(cur.label);
            
            for (UndirectedGraphNode neighbor : cur.neighbors){
                if (!map.containsKey(neighbor.label)){
                    UndirectedGraphNode newNode = new UndirectedGraphNode(neighbor.label);
                    map.put(neighbor.label, newNode);
                }
                UndirectedGraphNode cloneNeighbor = map.get(neighbor.label);
                curClone.neighbors.add(cloneNeighbor);
                if (!visited.contains(neighbor.label)){
                    queue.offer(neighbor);
                    visited.add(neighbor.label);
                }
                
            }
        }
        
        return head;
    }
}
```