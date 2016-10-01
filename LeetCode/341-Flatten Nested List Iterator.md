#341-Flatten Nested List Iterator

##题意
Given the list [[1,1],2,[1,1]],

By calling next repeatedly until hasNext returns false, the order of elements returned by next should be: [1,1,2,1,1].

##解题
有两种方法， 第一种是 将 list 用 Stack 展开， 然后一个一个取出来。 这个方法的问题是 需要的空间是 O(n), 因为最坏情况是， 每一个元素都需要存一下。

更好一点的方法是， 节省空间， 用 iterator.next() 来取得下一个元素。因为有层层嵌套的情况， 所以 iterator 不止一个， 那么怎么存储之前的iterator情况呢? 将 iterator 放入  stack 之中。

用iterator.next() 来取得下一个元素的时候， 如果下一个元素m经判断是 integer, 那么说明如果call next()， 返回的就是它。

**所以需要用一个变量储存一下m， 这里很重要， 因为 iterator 不能倒转回去， 以便于 call next()的时候返回答案**

如果判断出来不是 integer， 那么就把这个 list 的 iterator 重新放在这个 stack 上面。

peek stack 顶端的时候， 有可能当前的list的iterator已经没有next了， 所以这种情况下， 需要Pop出。

##代码
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
    NestedInteger nextInt;
    LinkedList<Iterator<NestedInteger>> stack = new LinkedList<Iterator<NestedInteger>>();
    public NestedIterator(List<NestedInteger> nestedList) {
        stack.push(nestedList.iterator());
    }

    @Override
    public Integer next() {
        return nextInt.getInteger();
    }

    @Override
    public boolean hasNext() {
        while (!stack.isEmpty()){
            if (!stack.peek().hasNext())    stack.pop();
            else{
                nextInt = stack.peek().next();        // 下一个已经储存在 nextInt 中了
                if (nextInt.isInteger())   return true;
                else stack.push(nextInt.getList().iterator());
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