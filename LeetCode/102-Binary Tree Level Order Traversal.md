#102-Binary Tree Level Order Traversal

## 题意
将每一个level的node.val加入一个list，然后再将这个每一个level的list加入一个list之中。

## 解题
BFS

## 要点
加入list的时候， 加入的一定是从 queue 中 Poll 出来的， 这样的话 可以保证这个list一定不会为空。

反之， 如果在每一层先建立一个list, 然后加入Node的话， 最后这个list有可能**为空！**

###比如说下面这个代码就是**不正确**的, 最后会加入空的list

```
while (!queue.isEmpty()){
            list = new ArrayList<Integer>();
            int curCount = 0;
            
            while (count>0){
                TreeNode cur = queue.poll();
                count --;
                if (cur.left!=null){
                    addToList(list, cur.left, queue);
                    curCount ++;
                }
                if (cur.right!=null){
                    addToList(list, cur.right, queue);
                    curCount ++;
                }
            }
            
            count = curCount;
            res.add(list);
        }
```

###以下是正确的代码
```
public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        if (root==null) return res;
        
        LinkedList<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);
        int count = 1;
        
        while (!queue.isEmpty()){
            List<Integer> list = new ArrayList<Integer>();
            int nextLevel = 0;
            
            while (count>0){
                TreeNode cur = queue.poll();
                list.add(cur.val);
                count--;
                if (cur.left!=null){
                    queue.offer(cur.left);
                    nextLevel++;
                }
                if (cur.right!=null){
                    queue.offer(cur.right);
                    nextLevel++;
                }
            }
            
            res.add(list);
            count = nextLevel;
        }
        
        return res;
        
    }
```