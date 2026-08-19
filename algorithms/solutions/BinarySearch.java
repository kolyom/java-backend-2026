
/**
 * Binary Search — локально класс BinarySearch.
 * На LeetCode переименуй в Solution.
 *
 * done / pick — в чат Cursor.
 */
public class BinarySearch {

    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int mid = 0;
        while (left <= right) {
            mid = (left + right) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            }

        }
        return -1;
    }

    public static void main(String[] args) {
        BinarySearch s = new BinarySearch();
        System.out.println(s.search(new int[]{-1, 0, 3, 5, 9, 12}, 9)); // 4
        System.out.println(s.search(new int[]{-1, 0, 3, 5, 9, 12}, 2)); // -1
    }
}
