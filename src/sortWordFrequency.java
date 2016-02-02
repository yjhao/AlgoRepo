import java.util.*;
/* 1. calculate the frequency of each word in a paragraph
   2. then extract the top n frequency word from the paragraph

   TO NOTE:   PriorityQueue's intial size will increase, see line 47
*/


class wordPair{
	String word;
	int count;
	public wordPair(String word, int count){
		this.word = word;
		this.count = count;
	}
}

public class sortWordFrequency {
	public static ArrayList<String> solution(String str, int n){
		String[] strArr = str.split("\\s+");
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		// calcualte the frequency of each word
		for (String s : strArr){
			if (map.containsKey(s)){
				map.put(s, map.get(s)+1);
			}else{
				map.put(s, 1);
			}
		}

		// build a priority queue
		PriorityQueue<wordPair> queue = new PriorityQueue<wordPair>(n, new Comparator<wordPair>(){
			public int compare(wordPair i1, wordPair i2){
				return i2.count-i1.count;
			}
		});

		// put items in the queue
		for (String key : map.keySet()){
			wordPair curPair = new wordPair(key, map.get(key));
			queue.offer(curPair);
		}

		// put the item in the queue to the arraylist
		ArrayList<String> list = new ArrayList<String>();
		for (int i=0; i<n; i++){     // can't use queue.size() here, because queue's size is larger than n
			list.add(queue.poll().word);
		}

		return list;


	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "b b b b b b b b c c c c c c c d d d d d d e e e e e f f f f g g g h h a a a a a a a a a a";
		solution(s, 3);

	}

}
