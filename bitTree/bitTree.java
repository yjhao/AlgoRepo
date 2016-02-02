class bitTree {
  // implementation if BIT
    int[] tree;
    int n;
    int[] input;
    int[] nums;

    public bitTree(int[] nums){
        tree = new int[nums.length+1];
        this.nums = nums;
        n = nums.length;
        for (int i=0; i<nums.length; i++){
            init(i, nums[i]);
        }
    }

    // get the summation from i to the end
    // bit tree index starts from 0001 -> 1, thus i++
    public void init(int i, int val){
        i++;
        while (i<=n){
            tree[i] += val;
            i += i&(-i);
        }
    }

    public void update(int i, int val){
        int diff = val - nums[i];
        nums[i] = val;
        init(i, diff);
    }

    // return the sum of [i,j]
    public int sumRange(int i, int j){
        return getSum(j) - getSum(i-1);
    }

    // return the sum of [0,i]
    public int getSum(int i){
        int res = 0;
        i++;
        while(i>0){
            res += tree[i];
            i -= i&(-i);
        }
        return res;
    }
}
