# 341-Flatten Nested List Iterator (数据结构 Stack）

## 题意
Given a nested list of integers, implement an iterator to flatten it.

Each element is either an integer, or a list -- whose elements may also be integers or other lists.

Example 1:
Given the list [[1,1],2,[1,1]],

By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,1,2,1,1]

## 解题
本题的关键就是如何处理 层层包裹的 list

简单 测试一下， 就知道 不能用 queue， 因为 在其中一层“解包”之后， 其中的内容应该是就近读取的， 而 加入queue之后 变成了“最后读取”

所以应该用 stack。

反向加入每一个item。

关键是 如何 实现 hasNext（）.

基本逻辑是： 如果 stack 的下一个出来的是 integer， 那么就是 true。 如果出来的还是 list， 那么就把这个 list 解包， 然后再放入stack。

如果 stack 都变成空了， 还没有找到这样的 Integer， 那么就返回 false。

### 细节
如果只有最后一个 Item 了， pop() 这个 item 之后， 这个stack就是空了， 所以这个时候不能使用 while (!stack.isEmpty())。 因为还没有解包这个item之前， 这个 stack 就已经是空的了。

## 代码
```
/**
 * // This is the interface that allows for creating nested lists.
 * // You should not implement it, or speculate about its implementation
 * public interface NestedInteger {
 *
 *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
 *     public boolean isInteger();
 *
 *     // @return the single integer that this NestedInteger holds, if it holds a single integer
 *     // Return null if this NestedInteger holds a nested list
 *     public Integer getInteger();
 *
 *     // @return the nested list that this NestedInteger holds, if it holds a nested list
 *     // Return null if this NestedInteger holds a single integer
 *     public List<NestedInteger> getList();
 * }
 */
public class NestedIterator implements Iterator<Integer> {
    LinkedList<NestedInteger> stack = new LinkedList<NestedInteger>();
    NestedInteger res = null;
    
    public NestedIterator(List<NestedInteger> nestedList) {
        for (int i=nestedList.size()-1; i>=0; i--){
            NestedInteger cur = nestedList.get(i);
            if (cur!=null)  stack.push(cur);
        }    
    }

    @Override
    public Integer next() {
        return res.getInteger();
    }

    @Override
    public boolean hasNext() {
        if (stack.isEmpty())    return false;
        while (!stack.isEmpty()){
            res = stack.pop();
            if (res.isInteger())    return true;
            List<NestedInteger> curList = res.getList();
            for (int i=curList.size()-1; i>=0; i--){
                if (curList.get(i)!=null)   stack.push(curList.get(i));
            }
        }
        
        return false;
    }
}

/**
 * Your NestedIterator object will be instantiated and called as such:
 * NestedIterator i = new NestedIterator(nestedList);
 * while (i.hasNext()) v[f()] = i.next();
 */
```

