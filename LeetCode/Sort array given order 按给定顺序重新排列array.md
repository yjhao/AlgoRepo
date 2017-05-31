# Sort array given order 按给定顺序重新排列array

## 题意
给定两个array，一个是actual numbers, 另一个是position array, 按照position array将actual number array排序。

例子：
actual number array : [4 2 1 5 3]

position array : [3 1 0 4 2]

=>

actual number array变成 [1 2 3 4 5]

## 解题
floyed cycle : 当 I == order[i] 的时候， 说明回到了cycle 起点。 

所以一直swap val and idx， 直到 i==order[i]为止。

### follow up
还可以计算 每个 cycle  的大小。 不需要 visited 来存储， 如果第一次遇到就是  I == order[i]， 那么说明 要么 不在 cycle 中， 要么已经计算在 cycle 中了。

## 代码
```
public void sortOrder(int[] array, int[] order){
        if(array == null || order == null){
            throw new IllegalArgumentException();
        }
        if(array.length == 0 && order.length == 0){
            return;
        }
        if(array.length != order.length){
            return;
        }
         
        int N = array.length;
        for(int i = 0; i < N; i++){
            while(i != order[i]){
                swap(array, i, order[i]);
                swap(order, i, order[i]);
            }
        }
    }
     
    private void swap(int[] num, int i, int j){
        int temp = num[i];
        num[i] = num[j];
        num[j] = temp;
    }
```

