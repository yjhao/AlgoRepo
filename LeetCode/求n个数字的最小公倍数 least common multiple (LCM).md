# 求n个数字的最小公倍数 least common multiple (LCM)

## 解题
lcmm (a,b,c) = lcmm(a, lcm(b,c))

lcm(a, b) = a * b * gcd(a,b)

## 代码
```
   public static int lcm(int a, int b){
		return a*b/gcd(a,b);
	}
	
	public static int lcmm(int[] arr){
		int res = 1;
		
		// 1st and 2nd
		res = arr[0]*arr[1]/gcd(arr[0], arr[1]);
		
		for (int i=2; i<arr.length; i++){
			res = res*arr[i]/gcd(res, arr[i]);
		}
		
		return res;
	}
	
	
	public static int gcd(int a, int b){
		if (a%b==0){
			return b;
		}else{
			return gcd(b, a%b);
		}
	}
```