package Algorithms;

import java.util.Comparator;
import java.util.List;

public class BinarySearch {

    /**
     * Performs a binary search on a sorted list of Object arrays.
     *
     * @param sortedList The sorted list of Object arrays to search.
     * @param target The target value to find.
     * @param comparator A comparator to compare Object array elements with target.
     * @return The index of the target value if found, otherwise -1.
     */
    public static int binarySearch(List<Object[]> sortedList, String target, Comparator<Object[]> comparator) {
        int left = 0;
        int right = sortedList.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            Object[] midValue = sortedList.get(mid);

            int comparison = comparator.compare(midValue, null);

            if (comparison == 0) {
                return mid; // Target found
            } else if (comparison < 0) {
                left = mid + 1; // Search in the right half
            } else {
                right = mid - 1; // Search in the left half
            }
        }

        return -1; // Target not found
    }
}