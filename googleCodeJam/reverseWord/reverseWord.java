package codingSrc;
import java.io.*;
import java.util.Scanner;

public class reverseWord {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try{
			  // read in the text file
			  File file = new File("/Users/yujiahao/Dropbox/codingSrc/googleCodeJam/reverseWord/B-large-practice.in");
			  PrintWriter writer = new PrintWriter("/Users/yujiahao/Dropbox/codingSrc/googleCodeJam/reverseWord/large_res.txt", "UTF-8");

			  Scanner in = new Scanner(file);

			  int totalNum = in.nextInt();
			  in.nextLine();

			  for (int i=0; i<totalNum; i++){
			    String curStr = in.nextLine();
			    String[] str = curStr.split("\\s+");
			    String res = solver(str);
			    // write output to file
			    int num = i+1;
			    writer.println("Case #" + num + ": " + res);
			  }

			  in.close();
			  writer.close();
		  } catch (Exception e){
			  e.printStackTrace();
		  }
	}

	public static String solver(String[] str){
		int l=0, r=str.length-1;
		while(l<r){
			String temp = str[l];
			str[l] = str[r];
			str[r] = temp;
			l++;
			r--;
		}

		StringBuilder sb = new StringBuilder();
		for (int i=0; i<str.length-1; i++){
			sb.append(str[i]);
			sb.append(" ");
		}
		sb.append(str[str.length-1]);
		return sb.toString();
	}

}
