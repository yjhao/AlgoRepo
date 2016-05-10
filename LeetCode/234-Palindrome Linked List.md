#234-Palindrome Linked List
## 题意
检测一个List是不是palindrome
##解题
首先找到中点， 然后翻转第二个half, 然后再比较
##细节
翻转的时候， 有一个最开始的“head1”， 他会一直接在下一个NexT上面， 还有一个是每次都更新的“head2”， 插在上一个“head2"的前面

##代码
```
public class Solution {
    public boolean isPalindrome(ListNode head) {
        if (head==null || head.next==null)  return true;
        ListNode walker = head;
        ListNode runner = head;
        
        while (runner!=null && runner.next!=null){
            walker = walker.next;
            runner = runner.next.next;
        }
        
        if (runner!=null)   walker = walker.next;
        
        // reverse the 2nd half
        ListNode headSecond = walker;
        ListNode cur = headSecond.next;
        
        while (cur!=null){
            ListNode next = cur.next;
            cur.next = headSecond;
            walker.next = next;
            headSecond = cur;
            cur = next;
        }
        
        cur = headSecond;
        ListNode cur1 = head;
        while (cur!=null){
            if (cur1.val != cur.val)    return false;
            cur = cur.next;
            cur1 = cur1.next;
        }
        
        return true;
    }
}
```