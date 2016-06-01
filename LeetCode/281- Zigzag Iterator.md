#281- Zigzag Iterator

## 题意
构造一个 iterator， 能够轮流输出 一组 list 中的数字。

## 解题
题目的关键是， 每一个 list  **都有自己的 iterator!!!!!**

将每一个 list 的 iterator 依次放入一个 queue 中， 然后每次提出一个 iterator， 得到当前应该返回的值。

然后如果这个 iterator 还有 next， 就将它继续加入 queue 中， 反之则放弃。

要检查这组 list  是否还有下一个数， 只需要检查 这个  queue 是不是 空的就行了。

## 细节
Iterator.next ()  返回的数据 一定要用 （int) 来强制转型。 因为 next() **返回的是一个 Object**！

##代码
```
public class ZigzagIterator {
    LinkedList<Iterator> queue = new LinkedList<Iterator>();
    public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
        if (v1.size()>0)
            queue.offer(v1.iterator());
        if (v2.size()>0)
            queue.offer(v2.iterator());
    }

    public int next() {
        Iterator cur = queue.poll();
        int res = (int) cur.next();
        if (cur.hasNext())  queue.offer(cur);
        return res;
    }

    public boolean hasNext() {
        return queue.isEmpty()? false:true;
    }
}

/**
 * Your ZigzagIterator object will be instantiated and called as such:
 * ZigzagIterator i = new ZigzagIterator(v1, v2);
 * while (i.hasNext()) v[f()] = i.next();
 */
 ```