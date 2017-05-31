# 146- LRU Cache

## 题意
设计一个LRU cache， 实现get, set

## 解题
使用hashmap 和 doubleLinkedList。

使用hashmap， 可以很快的从key找到这个node， 然后更新这个node的位置。

使用doublelinkedlist， 可以维护 head 和 tail, 就不需要每次都从head开始找到tail了。

要点就是：

1. get 一个key的时候， 先看hashmap里面有没有， 如果没有， 直接返回-1， 如果有， 找到这个node， 然后将这个node先从原来的list中删除掉， 然后再加入在前端。
2. 如果 set一个key， 如果hashmap里面有这个Key, 找到这个node， 改变node的val， 然后将这个Node从原来的list中删除掉， 然后再加入在前端。
3. 如果hashmap里面没有这个node， 说明我们得建立一个新的。

* 如果这个时候LRU的大小还没有到最大容积， 直接将这个新的node放在最前端
* 如果已经达到了最大容积， 那么需要将tail 删除掉， 然后更新tail， 然后将这个新的放在最前端。


**细节**
在加入新node的时候， 需要检查此时有没有 head tail 这两个node了， 如果没有的话， 需要初始化。

在删除node的时候， 注意， 只是删除的node在List中的位置， 而没有删除掉在hashmap中的pair， 所以需要删除hashmap pair

删除node的时候， 需要检查此时node.pre node.next有没有， 如果没有node.pre， 那么说明node肯定是head， 那么删掉node之后的head就变为了node.next；

如果没有node.next， 说明node肯定是tail， 那么下一个tail就是node.pre

## 代码
```
public class LRUCache {
    private int capacity;
    private HashMap<Integer, doubleLinkedList> map = new HashMap<Integer, doubleLinkedList>();
    private doubleLinkedList head;
    private doubleLinkedList tail;
    private int len;
    
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        len = 0;
    }
    
    public void setHead(doubleLinkedList cur) {
        cur.next = head;
        cur.pre = null;
        
        if (head!=null){
            head.pre = cur;
        }
        
        head = cur;
        
        if (tail==null){
            tail = head;
            tail.next = null;
        }
    }
    
    public void removeNode(doubleLinkedList cur) {
        doubleLinkedList pre = cur.pre;
        doubleLinkedList next = cur.next;
        
        if (pre!=null){
            pre.next = next;
        }
        else{
            head = next;        // 非常重要。
        }
        
        if (next!=null){
            next.pre = pre;
        }
        else{
            tail = pre;
        }
    }
    
    public int get(int key) {
        if (!map.containsKey(key)){
            return -1;
        }
        else{
            doubleLinkedList latest = map.get(key);
            removeNode(latest);    // 为什么这两个顺序不能颠倒?
            setHead(latest);
            
            return latest.val;
        }
    }
    
    public void set(int key, int value) {
        if (map.containsKey(key)){
            doubleLinkedList oldNode = map.get(key);
            oldNode.val = value;
            removeNode(oldNode);
            setHead(oldNode);
        }
        else{
            doubleLinkedList newNode = new doubleLinkedList(value, key);
            
            if (len<capacity){
                setHead(newNode);
                len++;
                map.put(key, newNode);
            }
            else{
                int val = tail.key;
                removeNode(tail);
                map.remove(val);
                
                setHead(newNode);
                map.put(key, newNode);
            }
            
        }
    }
}

class doubleLinkedList {
    public int val;
    public int key;
    public doubleLinkedList pre;
    public doubleLinkedList next;
    
    public doubleLinkedList(int val, int key) {
        this.val = val;
        this.key = key;
    }
}
```

