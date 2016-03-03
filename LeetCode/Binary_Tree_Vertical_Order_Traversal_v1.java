/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */


/* the solution1 is slow, because it uses a wrapper class, and sort the idx from small to large

   it is improved in version 2, where
   1. min and max vertical location is obtained in the while loop, every idx between min and max
      is not EMPTY.
   2. instead of wrapper class, two queue is offering or polling at the same time, one queue stores
      the node, and another stores the idx, making it a one to one match.
*/

 class wrapper{
     TreeNode node;
     int idx;
     public wrapper(TreeNode node, int idx){
         this.node = node;
         this.idx = idx;
     }
 }

public class Solution {
    public List<List<Integer>> verticalOrder_2(TreeNode root) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        if (root==null) return res;
        HashMap<Integer, LinkedList<Integer>> map = new HashMap<Integer, LinkedList<Integer>>();
        LinkedList<TreeNode> nodeQueue = new LinkedList<TreeNode>();
        LinkedList<Integer> idxQueue = new LinkedList<Integer>();

        nodeQueue.offer(root);
        idxQueue.offer(0);
        int min = 0; int max = 0;

        while(!nodeQueue.isEmpty()){
            TreeNode curNode = nodeQueue.poll();
            int curIdx = idxQueue.poll();
            min = Math.min(min, curIdx);
            max = Math.max(max, curIdx);

            if (map.containsKey(curIdx)){
                map.get(curIdx).add(curNode.val);
            }else{
                LinkedList<Integer> list = new LinkedList<Integer>();
                list.add(curNode.val);
                map.put(curIdx, list);
            }

            if (curNode.left!=null){
                nodeQueue.offer(curNode.left);
                idxQueue.offer(curIdx-1);
            }

            if (curNode.right!=null){
                nodeQueue.offer(curNode.right);
                idxQueue.offer(curIdx+1);
            }
        }

        for (int i=min; i<=max; i++){
            res.add(map.get(i));
        }

        return res;
    }


    public List<List<Integer>> verticalOrder_1(TreeNode root) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();
        if (root==null) return res;
        HashMap<Integer, LinkedList<Integer>> map = new HashMap<Integer, LinkedList<Integer>>();
        LinkedList<wrapper> queue = new LinkedList<wrapper>();
        LinkedList<Integer> allLoc = new LinkedList<Integer>();
        queue.offer(new wrapper(root,0));

        while(!queue.isEmpty()){
            wrapper cur = queue.poll();
            int loc = cur.idx;
            if (map.containsKey(loc)){
                map.get(loc).add(cur.node.val);
            }else{
                LinkedList<Integer> list = new LinkedList<Integer>();
                list.add(cur.node.val);
                map.put(loc, list);
                allLoc.add(loc);
            }

            if (cur.node.left!=null){
                queue.offer(new wrapper(cur.node.left, loc-1));
            }

            if (cur.node.right!=null){
                queue.offer(new wrapper(cur.node.right, loc+1));
            }
        }

        Collections.sort(allLoc);

        for (int i=0; i<allLoc.size(); i++){
            int curLoc = allLoc.get(i);
            res.add(map.get(curLoc));
        }

        return res;
    }
}
