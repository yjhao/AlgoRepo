#275 H-Index II (二分法）

## 题意
求H-Index， 引用已经按大小顺序排好了

## 解题
使用二分法， 比较右侧的论文数量和Mid的引用 a， 如果论文数量大于a， 那么说明a还可以提升， 区间右移； 反之区间左移

最后的返回， 应该是在Mid右侧的论文数量， 而不是mid的引用

所以如果二分法中使用 While (left<=right)的话， 最后left会在mid的右边， 求这个论文数量， 直接len-left；

反之， 如果是 while (left<right）的话，left最后会停在mid， 求论文数量 需要 len-left-1；

最大的Corner case:  【1】, 如果采取 while (left<right)， 最后的答案会为 1-0-1 = 0； 所以显然错误。

所以得使用 while (left<=right)。

#代码
```
public class Solution {
    public int hIndex(int[] citations) {
        if (citations==null || citations.length==0)
            return 0;
        int left = 0;
        int right = citations.length-1;
        int len = citations.length;
        
        while (left<=right){
            int mid = left + (right-left)/2;
            if (citations[mid]==len-mid)
                return len-mid;
            
            if (len-mid>citations[mid]){
                left = mid+1;
            }else{
                right = mid-1;
            }
        }
        
        return len-left;
    }
}
```