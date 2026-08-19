/**
 * День 8 — Easy 3/3
 * LeetCode: https://leetcode.com/problems/search-insert-position/
 *
 * Отсортированный массив nums (по возрастанию, без дубликатов) и target.
 * Верни индекс target, если он есть.
 * Если нет — индекс, куда его нужно вставить, чтобы порядок сохранился.
 *
 * Пример: nums = [1,3,5,6], target = 5 → 2
 * nums = [1,3,5,6], target = 2 → 1
 * nums = [1,3,5,6], target = 7 → 4
 *
 * Класс SearchInsertPosition. На LeetCode → Solution.
 * done / pick
 */
public class SearchInsertPosition {

    public int searchInsert(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target)
                return i;
            else if (nums[i] >= target)
                return i;
        }
        return nums.length;
    }

    public static void main(String[] args) {
        SearchInsertPosition s = new SearchInsertPosition();
        int[] nums = { 1, 3, 5, 6 };
        System.out.println(s.searchInsert(nums, 5)); // 2
        System.out.println(s.searchInsert(nums, 2)); // 1
        System.out.println(s.searchInsert(nums, 7)); // 4
        System.out.println(s.searchInsert(nums, 0)); // 0
    }
}
