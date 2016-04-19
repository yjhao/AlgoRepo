#109-Convert Sorted List to Binary Search Tree

## 题意
把一个linked list 转换为平衡BST

## 解题
如果本体是转换Array, 直接提取每一段的中点， 然后递归就可以了。 因为访问数列的某一点的复杂度为 O(1)

这里是list, 不能直接访问某个点。

不过我们可以做相似于Array的“截断成两半， 然后递归”

关键问题就是如何每次访问一个sublist的时候， 不从list的起点开始访问 --》 解决方案就是传递进当前sublist的起点和终点， 然后用double pointer来搜索中点。

特别注意， 如果Head==tail， 那么应该返回一个Null。

复杂度为 O(nlogn), 类似于merge sort

#代码
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
public class Solution {
    public TreeNode sortedListToBST(ListNode head) {
        return helper(head, null);
    }
    
    public TreeNode helper(ListNode head, ListNode end){
        if (head==null) return null;
        if (head==end) return null;
        
        ListNode slow = head;
        ListNode fast = head;
        
        while (fast!=end && fast.next!=end){
            slow = slow.next;
            fast = fast.next.next;
        }
        
        TreeNode root = new TreeNode(slow.val);
        
        root.left = helper(head, slow);
        root.right = helper(slow.next, end);
        return root;
    }
    
    
}
```

## O(n) 解法
还有一个O(n)的解法。。。

关键就是如何利用好 sequtial traverse the list and seruqntial traverse the tree

所以我们想到了， inorder traverse！

所以tree traverse使用inorder， 每次操作Listnode的时候， 就把这个Node的值建一个新的treeNode。

关键问题是如何确定， 一个listnode， 对应的treenode在哪个位置， 哪个深度?

**截半， 如果l>r了， 说明我们已经超出最大的可以每次“除以2”的次数了， 也就是深度！**

##代码
```
public class Solution {
    public TreeNode sortedListToBST(ListNode head) {
        if (head==null) return null;
        ArrayList<ListNode> list = new ArrayList<ListNode>();
        list.add(head);
        int count = 0;
        while (head!=null)
        {
            head = head.next;
            count++;
        }
        
        return helper(list, 0, count-1);
    }
    
    private TreeNode helper(ArrayList<ListNode> list, int left, int right) {
        if (left>right) return null;
        int mid = (left+right)/2;
        TreeNode leftNode = helper(list,left,mid-1);
        TreeNode root = new TreeNode(list.get(0).val);
        list.set(0,list.get(0).next);
        root.left = leftNode;
        root.right = helper(list,mid+1,right);
        
        return root;
    }
}
```

