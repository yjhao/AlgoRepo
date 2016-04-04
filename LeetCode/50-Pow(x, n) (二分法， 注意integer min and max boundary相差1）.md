#50-Pow(x, n) (二分法， 注意integer min and max boundary相差一）

##解题
注意integer min and max boundary相差1

##代码
```
public class Solution {
    public double myPow(double x, int n) {
        
        if (x==0) return 0;
        if (x==1 || n==0) return 1.0;
        if (n==1) return x;
        
        if (n<0){
            if (n!=Integer.MIN_VALUE){
                return 1.0/myPow(x, -n);
            }else{
                double cur = 1.0/myPow(x, Integer.MAX_VALUE);
                cur = cur/x ;
                return cur;
            }
        }

        if (n%2==0){
            double half = myPow(x,n/2);
            return half * half;
        }else{
            double half = myPow(x,n/2);
            return half*half*x;
        }

    }
}
```