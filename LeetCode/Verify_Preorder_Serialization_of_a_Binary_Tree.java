/*One way to serialize a binary tree is to use pre-order traversal. When we encounter a non-null node, we record the node's value. If it is a null node, we record using a sentinel value such as #.

     _9_
    /   \
   3     2
  / \   / \
 4   1  #  6
/ \ / \   / \
# # # #   # #
For example, the above binary tree can be serialized to the string "9,3,4,#,#,1,#,#,2,#,6,#,#", where # represents a null node.

Given a string of comma separated values, verify whether it is a correct preorder traversal serialization of a binary tree. Find an algorithm without reconstructing the tree.

Each comma separated value in the string must be either an integer or a character '#' representing null pointer.

You may assume that the input format is always valid, for example it could never contain two consecutive commas such as "1,,3".

Example 1:
"9,3,4,#,#,1,#,#,2,#,6,#,#"
Return true

Example 2:
"1,#"
Return false

Example 3:
"9,#,#,1"
Return false
*/

/*my first solution, use a stack, if current node is not null, then push 2, which means 2 slots.
When adding a new node, except the first one, it will bring in 2 slots, but take 1 slot. Thus decrease
the pop-ed value by 1 and then re-push, and push 2 as itself's empty slots.*/

public boolean isValidSerialization(String preorder) {
        if (preorder==null || preorder.length()==0) return true;
        String[] strArr = preorder.split(",");
        LinkedList<Integer> stack = new LinkedList<Integer>();

        if (strArr[0].compareTo("#")!=0)  stack.push(2);

        for (int i=1; i<strArr.length; i++){
            String cur = strArr[i];
            if (cur.compareTo("#")==0){
                if (stack.isEmpty())    return false;
                int top = stack.pop();
                top--;
                if (top<0)  return false;
                if (top>0) stack.push(top);
            }else{
                if (stack.isEmpty())    return false;
                int top = stack.pop();
                top--;
                if (top<0)  return false;
                if (top>0)  stack.push(top);
                stack.push(2);
            }
        }

        return stack.isEmpty();
    }

/* a better solution, just use a counter to track current empty slots, if a valid tree,
empty slots should be 0.

If there are empty slot, and if it is not the end of the tree, then return false.

be careful the 1st node.*/

public boolean isValidSerialization(String preorder) {
        if (preorder==null || preorder.length()==0) return true;
        String[] strArr = preorder.split(",");
        int count = 0;

        if (strArr[0].compareTo("#")==0 && strArr.length==1)    return true;
        if (strArr[0].compareTo("#")==0)    return false;
        if (strArr[0].compareTo("#")!=0)    count = count+2;

        for (int i=1; i<strArr.length; i++){
            String cur = strArr[i];
            if (cur.compareTo("#")==0){
                count--;
            }else{
                count++;
            }

            if (count<0 || count==0 && i!=strArr.length-1)    return false;
        }

        return count==0;
    }
