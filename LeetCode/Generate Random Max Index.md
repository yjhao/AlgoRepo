#Generate Random Max Index
##题意
给一个全是数字的数组，随机返回0到当前位置中最大值得坐标
比如【1,2,3,3,3,3,1,2】
在最后一个2的时候有4个3都是最大值，要按1/4的概率返回其中一个3的index
[1, 2, 3, 3, 3, 3]

##解题
要求 一次 pass, 而且少用memory。

所以我们记录一个当前 max value and count， 然后每次生成一个 random number between [0, count)， if this random number is equal to 0, then assign the answer to the current index。

重点就是 “等概率”

##代码
```
public static int sampleIdx(int[] array){
        if(array == null || array.length == 0){
            throw new IllegalArgumentException();
        }
        Random rnd = new Random();
        int res = 0, max = Integer.MIN_VALUE, count = 0;
        for(int i = 0; i < array.length; i++){
            if(max < array[i]){
                max = array[i];
                res = i;
                count = 1;
            }else if(max == array[i]){
                count++;
                int idx = rnd.nextInt(count); //(0, k - 1)
                if(idx == 0){
                    res = i;
                }
            }
        }
        return res;
    }
```