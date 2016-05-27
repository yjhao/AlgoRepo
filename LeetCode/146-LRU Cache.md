#146-LRU Cache

##题意
用一个数据结构来模拟LRU cache, 支持 get(key), set(key,value)。

##解答
因为要使用get， 所以自然而然想到用hashmap。

因为要支持LRU, 所以必然得记录 头 和 尾， 所以用double linked list。

用一个hashmap, 来记录 key 和 对应的 node。

当 get 的时候， 用 Map 找到这个节点 然后将这个节点先删除， 然后放在头部。

当 set 的时候， 如果这个节点已经存在， 处理方式与 get 一样。 如果不存在， 当没有超过最大长度的时候， 将这个节点放在最前， 当已经是最大程度的时候， 将尾端删掉， 然后将这个节点放在最前。

## 注意的细节

1. 删除一个节点的时候， 在 hashmap 中也应该**对应的删掉**！
2. 在将一个节点放在最前端的时候， 一定要 **明确的写出** `` node.pre = null; `` 虽然说 当建立一个新的Node的时候， pre 都是 Null, 但是还有另外一种可能， 就是这个node其实是已经存在的， 在这里只是被提到了最前部！此时 pre 显然不是 Null。
3. 在删除中 处理 pre next 的时候， 时刻注意 pre next 是不是 Null。

##代码
```
public class LRUCache {
    ListNode head;
    ListNode tail;
    HashMap<Integer, ListNode> map =  new HashMap<Integer, ListNode>();
    int capacity;
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.head = null;
        this.tail = null;
    }
    
    public void removeNode(ListNode node){
        ListNode pre = node.pre;
        ListNode next = node.next;
        
        if (pre==null){
            head = next;
        }else{
            pre.next = next;
        }
        
        if (next==null){
            tail = pre;
        }else{
            next.pre = pre;
        }
    }
    
    public void setHead(ListNode cur){
        if (head==null){
            head = cur;
            tail = cur;
        }else{
            cur.next = head;
            cur.pre = null;   // this is very important
            head.pre = cur;    
            head = cur;
        }
    }
    
    
    public int get(int key) {
        if (!map.containsKey(key)){
            return -1;
        }else{
            ListNode cur = map.get(key);
            removeNode(cur);
            setHead(cur);
            return cur.val;
        }
    }
    
    public void set(int key, int value) {
        if (map.containsKey(key)){
            ListNode cur = map.get(key);
            cur.val = value;
            removeNode(cur);
            setHead(cur);
        }else{
            ListNode cur = new ListNode(value, key);
            if (capacity>0){
                setHead (cur);
                map.put(key, cur);
                capacity --;
            }else{
                int removeKey = tail.key;
                removeNode(tail);
                map.remove(removeKey);
                setHead(cur);
                map.put(key, cur);
            }
        }
    }
}

class ListNode {
    int val;
    int key;
    ListNode pre;
    ListNode next;
    
    public ListNode(int val, int key){
        this.val = val;
        this.key = key;
        this.pre = null;
        this.next = null;
    }
}
```