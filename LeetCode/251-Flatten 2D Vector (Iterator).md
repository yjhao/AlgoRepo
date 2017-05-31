# 251-Flatten 2D Vector (Iterator)
## 题意
对于一个 2D 的 list，实现一个 iterator。

## 解题
每一个 sublist  都有自己的 iterator

对于原list<list<>>, 可以使用一个 iterator 来得到 下一个 sublist。

需要注意的是， 此时的 iterator 需要赋予类型：

```
Iterator<List<Integer>> totalIterator;
totalIterator = vec2d.iterator();
```

然后针对每一个的 Sublist， 得到他的 iterator， 如果这个 iterator 没有 Next了， 就从 totalIterator 中得到下一个 iterator。

需要注意的是， 如果是一个空的 list， 此时 iterator 这个对象是 **null**

**所以在检查 iterator.hasNext()之前， 应该也检查一下 iterator 是不是为 Null。**

## 代码
```
public class Vector2D implements Iterator<Integer> {
    Iterator<List<Integer>> totalIterator;
    Iterator curIterator;
    public Vector2D(List<List<Integer>> vec2d) {
        totalIterator = vec2d.iterator();
        if (totalIterator.hasNext())
            curIterator = totalIterator.next().iterator();
    }

    @Override
    public Integer next() {
        Integer res = (Integer) curIterator.next();
        return res;
    }

    @Override
    public boolean hasNext() {
        while (totalIterator.hasNext() && (curIterator==null || !curIterator.hasNext())){
            curIterator = totalIterator.next().iterator();
        }
        //if (queue.isEmpty())    return false;
        if (curIterator==null)    return false;
        return  curIterator.hasNext();
    }
}

/**
 * Your Vector2D object will be instantiated and called as such:
 * Vector2D i = new Vector2D(vec2d);
 * while (i.hasNext()) v[f()] = i.next();
 */
```

