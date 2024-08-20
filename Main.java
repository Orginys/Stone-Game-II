class Solution {
    public int stoneGameII(int[] piles) {
        int n = piles.length;

        // dp[i][m] stores the maximum stones Alice can get starting from pile i with M = m
        int[][] dp = new int[n][n + 1];

        // suffixSum[i] is the total stones from pile i to the last pile
        int[] suffixSum = new int[n];

        suffixSum[n - 1] = piles[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            suffixSum[i] = piles[i] + suffixSum[i + 1];
        }

        // The goal is to maximize the stones Alice can get
        return helper(piles, 0, 1, dp, suffixSum);
    }

    private int helper(int[] piles, int i, int M, int[][] dp, int[] suffixSum) {
        int n = piles.length;

        // If all piles from index i to the end are taken
        if (i == n) return 0;

        // If taking all remaining stones
        if (2 * M >= n - i) return suffixSum[i];

        // Return memoized result if already computed
        if (dp[i][M] != 0) return dp[i][M];

        int maxStones = 0;

        // Try taking x piles where x ranges from 1 to 2M
        for (int x = 1; x <= 2 * M; x++) {
            // Opponent's stones calculation
            int opponent = helper(piles, i + x, Math.max(M, x), dp, suffixSum);
            // Update the maximum stones Alice can get
            maxStones = Math.max(maxStones, suffixSum[i] - opponent);
        }

        dp[i][M] = maxStones;
        return maxStones;
    }
}