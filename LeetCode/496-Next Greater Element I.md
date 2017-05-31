# 496-Next Greater Element I
## 题意
在一个数列里面， 找出每一个数字的右面的第一个比其大的数字。在这个数列中， 没有重复的数字。

## 解题
这道题， 如果focus在 对于**每一个数字**， 来找到其**右边**的第一个比其大的数， 复杂度好像会是 n^2。

所以换一个方法， 当我们拿到某一个数之后， 应该找一下**左面的比其小的数**， 那么对于这些比其小的数， 他们右边的第一个比他们大的数字， 就是现在的这个数字。

要实现这个功能， 我们应该采用 stack， 此题有点像另外一道题， largest histogram。

对于每一个数，我们先从stack 里面 Pop出比这个数小的数， 然后对于这些pop出来的数， 他们的右边的第一个比他们大的数就找到了。 然后我们再把当前数字加入stack之中。

所以这个stack 最后应该是一个“递减stack”。

## 代码
```
public class Solution {
    public int[] nextGreaterElement(int[] findNums, int[] nums) {
        if (nums.length==0 || findNums.length==0)   return new int[0];
        int[] res = new int[findNums.length];
        LinkedList<Integer> stack = new LinkedList<Integer>();
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

        for (int num : nums){
            while (!stack.isEmpty() && stack.peek()<num){
                int cur = stack.pop();
                map.put(cur, num);
            }
            stack.push(num);
        }

        for (int i=0; i<findNums.length; i++){
            if (!map.containsKey(findNums[i]))  res[i] = -1;
            else{
                res[i] = map.get(findNums[i]);
            }
        }
        return res;
    }
}
```

