import java.util.ArrayList;
import java.util.List;

// LeetCode 46 - Permutations
// Given an array of distinct integers, return all possible permutations.
// Time Complexity : O(n! * n)
// Space Complexity: O(n)  [recursion stack + freq array]

class Permutations {

    private void recurPermute(int[] nums, List<Integer> ds, List<List<Integer>> ans, boolean[] freq) {
        if (ds.size() == nums.length) {
            ans.add(new ArrayList<>(ds));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (!freq[i]) {
                freq[i] = true;
                ds.add(nums[i]);
                recurPermute(nums, ds, ans, freq);
                ds.remove(ds.size() - 1);
                freq[i] = false;
            }
        }
    }

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> ds = new ArrayList<>();
        boolean[] freq = new boolean[nums.length];
        recurPermute(nums, ds, ans, freq);
        return ans;
    }

    // Quick smoke-test
    public static void main(String[] args) {
        Permutations sol = new Permutations();
        System.out.println(sol.permute(new int[]{1, 2, 3}));
        // Expected: [[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
    }
}
