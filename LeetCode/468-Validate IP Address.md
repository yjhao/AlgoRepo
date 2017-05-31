# 468-Validate IP Address
## 题意
给一个IP Address, 问是不是 IPV4 或者 IPV6 中的某一个。

## 解题
这个题没有什么难度，但是有几个细节需要注意：

1. 如果一个string要使用 "." 来分割， 不能直接使用 split("."), 因为 Split 支持 regex， 所以我们要告诉 split， 这个Dot， 是一个真正的dot。 所以要使用 split("\\.") 或者 split("[.]").

2. 当String split的时候， 如果 xxx.xxxx.xxx. 以dot来分割， 分割出来的还是只有三个element， 但其实最后还有一个dot。所以我们要手工的判断， 最后一个和最前一个字符， 是不是dot。


## 代码
```
public class Solution {
    public String validIPAddress(String IP) {
        if (IP==null || IP.length()<6)  return "Neither";
        if (IP.contains(".")){
            if (validIPv4(IP))  return "IPv4";
            else return "Neither";
        }else{
            if (validIPv6(IP))  return "IPv6";
            else return "Neither";
        }
    }
    
    public boolean validIPv4(String IP){
        if (IP.charAt(0)=='.' || IP.charAt(IP.length()-1)=='.') return false;
        String[] strs = IP.split("//.");
        if (strs.length!=4) return false;
        for (int i=0; i<4; i++){
            if (!helperIPv4(strs[i]))    return false;
        }
        return true;
    }
    
    public boolean helperIPv4(String str){
        if (str.length()>3 || str.length()==0) return false;
        if (str.charAt(0)=='0' && str.length()>1)   return false;
        
        char[] cs = str.toCharArray();
        for (char c : cs) {
            if (c>'9' || c<'0') return false;
        }
        
        int val = Integer.valueOf(str);
        if (val>255)    return false;
        return true;
    }
    
    public boolean validIPv6(String IP){
        if (IP.charAt(0)==':' || IP.charAt(IP.length()-1)==':') return false;
        String[] strs = IP.split(":");
        if (strs.length!=8)   return false;
        for (int i=0; i<8; i++){
            if (!helperIPv6(strs[i]))   return false;
        }
        return true;
    }
    
    public boolean helperIPv6(String str){
        if (str.length()>4 || str.length()==0)  return false;
        char[] cs = str.toCharArray();
        
        for (char c : cs){
            if (c>='A' && c<='F' || c>='a' && c<='f' || c>='0' && c<='9')   continue;
            else return false;
        }
        return true;
    }
}
``` 

