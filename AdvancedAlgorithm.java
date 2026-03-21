import java.util.*;

class Job {
    int start, finish, weight;

    public Job(int s, int f, int w) {
        start = s;
        finish = f;
        weight = w;
    }
}

public class AdvancedAlgorithm {

    static int binarySearch(Job[] jobs, int index) {
        int low = 0, high = index - 1;

        while (low <= high) {
            int mid = (low + high) / 2;

            if (jobs[mid].finish <= jobs[index].start) {
                if (mid + 1 <= high && jobs[mid + 1].finish <= jobs[index].start)
                    low = mid + 1;
                else
                    return mid;
            } else {
                high = mid - 1;
            }
        }
        return -1;
    }

    static void findSolution(Job[] jobs) {

        Arrays.sort(jobs, Comparator.comparingInt(j -> j.finish));

        int n = jobs.length;
        int[] dp = new int[n];
        int[] parent = new int[n];

        dp[0] = jobs[0].weight;
        parent[0] = -1;

        for (int i = 1; i < n; i++) {
            int include = jobs[i].weight;
            int l = binarySearch(jobs, i);

            if (l != -1)
                include += dp[l];

            if (include > dp[i - 1]) {
                dp[i] = include;
                parent[i] = l;
            } else {
                dp[i] = dp[i - 1];
                parent[i] = -2;
            }
        }

        System.out.println("\nMaximum weight = " + dp[n - 1]);

        System.out.println("Selected Jobs:");
        List<Job> selected = new ArrayList<>();

        int i = n - 1;
        while (i >= 0) {
            if (parent[i] == -2) {
                i--;
            } else {
                selected.add(jobs[i]);
                i = parent[i];
            }
        }

        Collections.reverse(selected);

        for (Job j : selected) {
            System.out.println("(" + j.start + ", " + j.finish + ", " + j.weight + ")");
        }

        System.out.println("Time Complexity: O(n log n)");
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("請輸入工作數量 n：");
        int n = sc.nextInt();

        Job[] jobs = new Job[n];

        System.out.println("請依序輸入每個工作 (start finish weight)：");

        for (int i = 0; i < n; i++) {
            System.out.print("工作 " + (i + 1) + ": ");
            int s = sc.nextInt();
            int f = sc.nextInt();
            int w = sc.nextInt();
            jobs[i] = new Job(s, f, w);
        }

        findSolution(jobs);

        sc.close();
    }
}