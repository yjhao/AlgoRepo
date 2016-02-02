public static int[] duplicateSort(int[] arr1, int[] arr2){

    // since we have to sort anyway, thus we use two pointers
    // time complexity : O(nlogn), space complexity: O(n)
    
		ArrayList<Integer> list = new ArrayList<Integer>();
		Arrays.sort(arr1);
		Arrays.sort(arr2);

		//ArrayList<Integer> arraylist = new ArrayList<Integer>(Arrays.asList(arr1));

		//Integer[] myArr = new Integer[arr1.length];
		//for (int i=0; i<arr1.length; i++){
		//	myArr[i] = (Integer)arr1[i];
		//}

		//Arrays.sort(myArr, Collections.reverseOrder());    // Ascending order use "reverseOrder", due to Collections, --> must not be primitive type

		//ArrayList<Integer> arraylist = new ArrayList<Integer>(Arrays.asList(myArr));    // transfer array to arraylist, must be the same type, can't be "int"

		//Collections.reverse(Arrays.asList(myArr));    // Collections : must not be primitive data type

		int l1=0; int l2=0;
		while (l1<arr1.length && l2<arr2.length){
			if (arr1[l1] == arr2[l2]){
				list.add(arr1[l1]);
				l1++; l2++;
			}else if (arr1[l1] < arr2[l2]){
				l1++;
			}else if (arr1[l1] > arr2[l2]){
				l2++;
			}
		}

		int[] res = new int[list.size()];
		for (int i=0; i<res.length; i++){
			res[i] = list.get(i);
		}

		return res;

	}
