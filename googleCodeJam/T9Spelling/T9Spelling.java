package codingSrc;
import java.io.*;
import java.util.Scanner;

public class T9Spelling {
	static String[] map = setMap();

	public static void main(String[] args) {


		// TODO Auto-generated method stub
		try{
			  // read in the text file
			  File file = new File("/Users/yujiahao/Dropbox/codingSrc/googleCodeJam/T9Spelling/C-large-practice.in");
			  PrintWriter writer = new PrintWriter("/Users/yujiahao/Dropbox/codingSrc/googleCodeJam/T9Spelling/large_res.txt", "UTF-8");

			  Scanner in = new Scanner(file);

			  int totalNum = in.nextInt();
			  in.nextLine();

			  for (int i=0; i<totalNum; i++){
			    String curStr = in.nextLine();

			    String res = solver(curStr);
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

	public static String[] setMap(){
		String[] map = new String[27];
		map[0] = "2"; map[1] = "22"; map[2] = "222";
		map[3] = "3"; map[4] = "33"; map[5] = "333";
		map[6] = "4"; map[7] = "44"; map[8] = "444";
		map[9] = "5"; map[10] = "55"; map[11] = "555";
		map[12] = "6"; map[13] = "66"; map[14] = "666";
		map[15] = "7"; map[16] = "77"; map[17] = "777"; map[18] = "7777";
		map[19] = "8"; map[20] = "88"; map[21] = "888";
		map[22] = "9"; map[23] = "99"; map[24] = "999"; map[25] = "9999";
		map[26] = "0";

		return map;
	}

	public static String solver(String str){
		char[] arr = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		int lastIdx = -1;
		int idx;

		for (int i=0; i<arr.length; i++){
			if (i==0){
				if (arr[i] == ' '){
					sb.append(map[26]);
					lastIdx = 26;
				}else{
					sb.append(map[arr[i]-'a']);
					lastIdx = arr[i]-'a';
				}
			}else{
				if (arr[i] == ' '){
					idx = 26;
				}else{
				    idx = arr[i]-'a';
				}

				if (map[idx].charAt(0)==map[lastIdx].charAt(0)){
					sb.append(' ');
				}
				sb.append(map[idx]);
				lastIdx = idx;
			}
		}

		return sb.toString();
	}

}
