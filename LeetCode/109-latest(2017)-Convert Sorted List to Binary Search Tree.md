# 109(latest 2017)-Convert Sorted List to Binary Search Tree

## 题意
将一个排好序的 list 变成一个 balance bst。

## 解题
算法没有什么复杂的， 首先找到 root， 然后递归求解 left, right。

但需要注意的是， 正常的递归求left， 传入的是 head。但是如果 root 左边的node为空 （head 即为 root）， 再传入head的话， 就会形成 重复计算， 所以直接返回 left=null。

```
TreeNode left = temp==null ? null : sortedListToBST(head);
```

## 代码
```
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public TreeNode sortedListToBST(ListNode head) {
        if (head==null) return null;
        if (head.next==null)    return new TreeNode(head.val);
        
        ListNode walker = head;
        ListNode runner = head;
        ListNode temp = null;
        
        while (runner.next!=null && runner.next.next!=null){
            runner = runner.next.next;
            temp = walker;
            walker = walker.next;
        }
        
        TreeNode root = new TreeNode(walker.val);
        
        if (temp!=null) temp.next = null;
        ListNode next = walker.next;
        walker.next = null;
        
        TreeNode left = temp==null ? null : sortedListToBST(head);
        TreeNode right = sortedListToBST(next);
        
        root.left = left;
        root.right = right;
        
        return root;
    }
}
```