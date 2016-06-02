#341-Flatten Nested List Iterator (数据结构 Stack）

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

在探测有没有 hasNext（） 的时候， 只有在Stack 面上的东西 是个 integer 才返回真。 所以在解包一个 Sublist， 并且放上 stack  之后， 还得继续 检测， 看面上的那一个是否为 integer。

**考虑到这点，应该用 while loop 来检测 ， while (!stack.isEmpty())**

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
    LinkedList<NestedInteger> stack = new LinkedList<NestedInteger>();
    
    public NestedIterator(List<NestedInteger> nestedList) {
        for (int i=nestedList.size()-1; i>=0; i--){
            stack.push(nestedList.get(i));
        }    
    }

    @Override
    public Integer next() {
        return stack.pop().getInteger();
    }

    @Override
    public boolean hasNext() {
        if (stack.isEmpty())    return false;
        
        while (!stack.isEmpty()){
            if (stack.peek().isInteger())     return true;
            NestedInteger top = stack.pop();
            List<NestedInteger> list = top.getList();
            for (int i=list.size()-1; i>=0; i--){
                stack.push(list.get(i));
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