#317-B Order Book (aggregate + sort)

## 题意
对股票价格aggregate, 然后排序

## 解题
aggregate + sort ==》 treemap, 自带排序， firstKey(), lastKey()

## 代码
```
public static void main(String[] args) throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        StringTokenizer ns = new StringTokenizer(bf.readLine());    
        int n=Integer.parseInt(ns.nextToken());
        int s=Integer.parseInt(ns.nextToken());
        TreeMap <Integer,Integer>tb=new TreeMap<Integer, Integer>();
        TreeMap <Integer,Integer>ts=new TreeMap<Integer, Integer>();
        for(int i=0;i<n;i++){
            ns = new StringTokenizer(bf.readLine());
            String c=ns.nextToken();
            int p=Integer.parseInt(ns.nextToken());
            int q=Integer.parseInt(ns.nextToken());
            if(c.equals("B"))
                if(tb.containsKey(p)){
                	int v=tb.get(p);
                	tb.remove(p);
                	tb.put(p, q+v);
                }else
                	tb.put(p, q);
            else
            	if(ts.containsKey(p)){
                	int v=ts.get(p);
                	ts.remove(p);
                	ts.put(p, q+v);
                }else
                	ts.put(p, q);        
        }
        int k=s;
        String r="";
        while(!ts.isEmpty()&&s-->0){  
        	int v=ts.firstKey();
        	r="S "+v+" "+ts.get(v)+"\n"+r;
        	ts.remove(v);
        }
        out.print(r);
        while(!tb.isEmpty()&&k-->0){  
        	int v=tb.lastKey();
        	out.println("B "+v+" "+tb.get(v));
        	tb.remove(v);
        }
 
     out.close();   
    }
````