# 160-Intersection of Two Linked Lists

## 题意
给两个List， 问他们的交点在哪里， 如果没有的话， 返回null。

## 题解
如果我们知道 两个List的长度分别是多少的话， 就可以从他们相同深度的地方一起visit， 那么就可以找到交点了。

如果不记录长度的话， 可以用一个巧妙的方法找到相同深度的地方。 当a到底的时候， 将a变为b的head， 相似的， 当b到底的时候， 将b变为a的head。


```
while (a!=b){
      a = a==null ? headB : a.next;
      b = b==null ? headA : b.next;
}
return a;
```

那么为什么不能用 a = a.next==null ? headB : a.next 呢？ 因为这样的话， 也可以找到相同深度的Node。

因为如果这样的话， a永远到不了一个null的node， 那么如果两个list没有交点的话， a b 永远就不可能相等了。


## 代码
```
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA==null || headB==null) return null;
        ListNode a = headA;
        ListNode b = headB;
        
        while (a!=b){
            a = a==null ? headB : a.next;
            b = b==null ? headA : b.next;
        }
        
        return a;
    }
}
```
