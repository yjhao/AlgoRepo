# Heap Sort, 空间复杂度 O(1)

## 特点
堆排 的最大优点就是， 额外的空间需求为 O(1)， 是一个 in-place 的排序。
 
而且复杂度为排序的标准复杂度 O(nlogn)。 可以用在一些对内存要求很严格的地方。 比如说 distrubuted machine。

## 算法
有两部分构成。

### heaptify
在第一部分中， 建立一个 heap。 类似一个树。 从序列末尾的数字 loop 到序列的最前面。 对每一个item i来说， 如果它的 left or right child is larger， then swap the larger one with i， and recursively trackle down。

heaptify 时间复杂度为 O(n)， 

At the bottommost level, there are 2^(h)nodes, but we do not call heapify on any of these, so the work is 0. At the next to level there are 2^(h − 1) nodes, and each might move down by 1 level. At the 3rd level from the bottom, there are 2^(h − 2) nodes, and each might move down by 2 levels.

As you can see not all heapify operations are O(log n), this is why you are getting O(n).

### swap from the end
Swap the first element of the list with the final element. Decrease the considered range of the list by one. 这样可以保证， 最大的值在序列的最尾端。

然后对交换之后的 第一个元素， 进行 trackle down， 完成 update。 这样的话， 第二大的元素又会换到一个的位置， 下次与尾端swap的时候， 就可以交换到倒数第二的位置。


## 在长度为 N 的序列中找到 TopK
可以先对前k个元素 heaptify。 如果是要找前K大的数字， 那么 这个heap的头部就为**最小值**。 

然后visit 第 k+1 个元素， 如果其比heap[0]大， 那么就交换 第一个和当前元素 （因为第一个肯定不是topK了）。 然后对 第一个元素再次实现 heaptify trackle down。

时间复杂度为 O(nlogk)。


## 代码
```
public class HeapSort {
	private static int[] sort = new int[]{1,0,10,20,3,5,6,4,9,8,12,17,34,11};
	public static void main(String[] args) {
		buildMaxHeapify(sort);
		heapSort(sort);
		print(sort);
	}

	private static void buildMaxHeapify(int[] data){
		//没有子节点的才需要创建最大堆，从最后一个的父节点开始
		int startIndex = getParentIndex(data.length - 1);
		//从尾端开始创建最大堆，每次都是正确的堆
		for (int i = startIndex; i >= 0; i--) {
			maxHeapify(data, data.length, i);
		}
	}
	
	/**
	 * 创建最大堆
	 * @param data
	 * @param heapSize需要创建最大堆的大小，一般在sort的时候用到，因为最多值放在末尾，末尾就不再归入最大堆了
	 * @param index当前需要创建最大堆的位置
	 */
	private static void maxHeapify(int[] data, int heapSize, int index){
		// 当前点与左右子节点比较
		int left = getChildLeftIndex(index);
		int right = getChildRightIndex(index);
		
		int largest = index;
		if (left < heapSize && data[index] < data[left]) {
			largest = left;
		}
		if (right < heapSize && data[largest] < data[right]) {
			largest = right;
		}
		//得到最大值后可能需要交换，如果交换了，其子节点可能就不是最大堆了，需要重新调整
		if (largest != index) {
			int temp = data[index];
			data[index] = data[largest];
			data[largest] = temp;
			maxHeapify(data, heapSize, largest);
		}
	}
	
	/**
	 * 排序，最大值放在末尾，data虽然是最大堆，在排序后就成了递增的
	 * @param data
	 */
	private static void heapSort(int[] data) {
		//末尾与头交换，交换后调整最大堆
		for (int i = data.length - 1; i > 0; i--) {
			int temp = data[0];
			data[0] = data[i];
			data[i] = temp;
			maxHeapify(data, i, 0);
		}
	}
	
	/**
	 * 父节点位置
	 * @param current
	 * @return
	 */
	private static int getParentIndex(int current){
		return (current - 1) >> 1;
	}
	
	/**
	 * 左子节点position注意括号，加法优先级更高
	 * @param current
	 * @return
	 */
	private static int getChildLeftIndex(int current){
		return (current << 1) + 1;
	}
	
	/**
	 * 右子节点position
	 * @param current
	 * @return
	 */
	private static int getChildRightIndex(int current){
		return (current << 1) + 2;
	}
	
	private static void print(int[] data){
		int pre = -2;
		for (int i = 0; i < data.length; i++) {
			if (pre < (int)getLog(i+1)) {
				pre = (int)getLog(i+1);
				System.out.println();
			} 
			System.out.print(data[i] + " |");
		}
	}
	
	/**
	 * 以2为底的对数
	 * @param param
	 * @return
	 */
	private static double getLog(double param){
		return Math.log(param)/Math.log(2);
	}
}
```


