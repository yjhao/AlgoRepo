# Three-way partition, quick sort

## Three way sorting
Three way sort is originally used to sort 0,1,2 in space. It tracks the start of 1 as i, and the end of 2 as k.

When a new numebr comes in at idx j.

1. if it is 0, then swap(i++, j++)
2. if it is 2, then swap(k--, j)
3. if it is 1, then just increment j.

## Applying in quick sort (partition)
The advantage of the three way sorting is that all the values of mid will group each other.

We can think the mid as "pivot", such that if it is used in the partition of quick sort, all pivot will be grouped.

like:

3,1,4,4,4,8,7



