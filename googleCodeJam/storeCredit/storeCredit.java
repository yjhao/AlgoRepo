package codingSrc;

import java.io.*;
import java.util.Scanner;

public class storeCredit {
  public static void main(String[] args){

	  try{
		// read in the text file
		  PrintWriter writer = new PrintWriter("/Users/yujiahao/Dropbox/codingSrc/googleCodeJam/storeCredit/large_res.txt", "UTF-8");

		  File file = new File("/Users/yujiahao/Dropbox/codingSrc/googleCodeJam/storeCredit/A-large-practice.in");
		  Scanner in = new Scanner(file);

		  int totalNum = in.nextInt();

		  for (int i=0; i<totalNum; i++){
		    int credit = in.nextInt();
		    int curNum = in.nextInt();
		    int[] price = new int[curNum];
		    for (int j=0; j<curNum; j++){
		      price[j] = in.nextInt();
		    }

		    int[] res = new int[2];
		    res = solver(credit, price);
		    int num = i+1;
		    writer.println("Case #" + num + ": " + res[0] + " " + res[1]);
		  }

		  in.close();
		  writer.close();
	  } catch (Exception e){
		  e.printStackTrace();
	  }
  }

  public static int[] solver(int credit, int[] price){
	  int[] res = {-1, -1};

	  for (int i=0; i<price.length-1; i++){
	  int curP = price[i];
	  for (int j=i+1; j<price.length; j++){
		  if (curP + price[j]==credit){
			  res[0] = i+1;
	          res[1] = j+1;
	          return res;
	          }
		  }
	  }

	  return res;
	  }
}
