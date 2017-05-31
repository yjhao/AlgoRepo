# 25-Reverse Nodes in K-group
## 题意
在一个list当中，翻转每K个node， 如果不够K个， 则不翻转

## 解题
因为题目要求“不够K个， 则不翻转”， 所以我们先用一个指针往前探索每k个node， 看有没有这么多， 如果没有这么多， 说明我们已经到了List的末端， 可以直接退出了。

**然后我们对k个Node进行k-1次的插入**：

pre-1-2-3-(4) : pre-2-1-3-(4) : pre-3-2-1-(4)

记录下来一个first指针（这里是1， 1最后成为tail）。每次插入的时候， 将first指针1接到下一个要traverse的node的前面， 然后每次读取first.next就可以了 （当然， 也可以不这样做， 最后将first接到List里面也是可以的）

## 细节
1. while（true）loop 直到break。 
2. 在第二个while loop中， 一定要将break的语句放到while之外， 不然只是break内层的while。

## 我写的代码 注意break的时候， count和Cur可能同时为K和null， 所以都要检查

```
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head==null || head.next == null || k==0)    return head;
        ListNode fakehead = new ListNode(0);
        fakehead.next = head;
        ListNode cur = fakehead;
        
        while (cur!=null){
            ListNode curTail = cur.next;
            ListNode curPre  = cur;
            int count = 0;
            while (count<k && cur!=null){
                count++;
                cur = cur.next;
            }
            
            if (count!=k || cur==null) break;
            
            ListNode nextHead = cur.next;
            reverse(curPre, nextHead);
            cur = curTail;
        }
        
        return fakehead.next;
    }
    
    public void reverse(ListNode pre, ListNode nextHead){
        ListNode cur = pre.next;
        ListNode tail = cur;
        while (cur != nextHead){
            ListNode next = cur.next;
            cur.next = pre.next;
            pre.next = cur;
            cur = next;
        }
        tail.next = nextHead;
    }
}
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
public class Solution {
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head==null || head.next==null || k==1)  return head;
        
        ListNode fakehead = new ListNode(-1);
        fakehead.next = head;
        
        ListNode pre = fakehead;
        ListNode first = head;
        ListNode next, temp, tail;
        
        while (true){
            tail = pre;
            int count = k;
            
            // find the group of k nodes, break the true loop if reach the end
            while (count>0 && tail!=null){
                tail = tail.next;
                count--;
            }
            if (tail==null) break;
            
            // reverse the k nodes from first.
            
            for (int i=0; i<k-1; i++){
                next = first.next;
                first.next = next.next;
                next.next = pre.next;
                pre.next = next;
            }
            
            pre = tail = first;
            first = first.next;
        }
        
        return fakehead.next;
    }
}
```

