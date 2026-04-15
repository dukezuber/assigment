PHASE III — IMPLEMENTATION
Task 5: Java Implementation
Concept for write codea are
->Conflict handling
->Resource tracking
->SLA enforcement
->Penalty computation

import java.util.*;

class Task {
    String id;
    double[] res;
    int l, u;
    double weight;

    Task(String id, double[] res, int l, int u, double weight) {
        this.id = id;
        this.res = res;
        this.l = l;
        this.u = u;
        this.weight = weight;
    }
}

class Scheduler {
    int K;
    double[][] cap;
    double[][] used;
    Map<String, List<String>> graph;
    Map<String, Integer> assign = new HashMap<>();

    Scheduler(int K, double[][] cap, Map<String, List<String>> graph) {
        this.K = K;
        this.cap = cap;
        this.graph = graph;
        used = new double[K][4];
    }

    boolean conflict(String t, int s) {
        if (!graph.containsKey(t)) return false;
        for (String n : graph.get(t)) {
            if (assign.containsKey(n) && assign.get(n) == s)
                return true;
        }
        return false;
    }

    boolean fits(Task t, int s) {
        for (int i = 0; i < 4; i++) {
            if (used[s][i] + t.res[i] > cap[s][i])
                return false;
        }
        return true;
    }

    double penalty(List<Task> tasks) {
        double p = 0;

        for (Task t : tasks)
            p += t.weight * assign.get(t.id);

        double lambda1 = 5, lambda2 = 3;

        for (int s = 0; s < K; s++) {
            double unused = 0, total = 0;
            for (int i = 0; i < 4; i++) {
                unused += cap[s][i] - used[s][i];
                total += cap[s][i];
            }
            p += lambda1 * (unused / total);
        }

        for (Task t : tasks) {
            int s = assign.get(t.id);
            p += lambda2 * ((double)(s - t.l) / (t.u - t.l + 1));
        }

        return p;
    }

    boolean solve(List<Task> tasks) {

        tasks.sort((a, b) -> Double.compare(b.weight, a.weight));

        for (Task t : tasks) {

            int best = -1;
            double bestP = Double.MAX_VALUE;

            for (int s = t.l; s <= t.u; s++) {

                if (conflict(t.id, s)) continue;
                if (!fits(t, s)) continue;

                assign.put(t.id, s);
                for (int i = 0; i < 4; i++)
                    used[s][i] += t.res[i];

                double p = penalty(tasks);

                assign.remove(t.id);
                for (int i = 0; i < 4; i++)
                    used[s][i] -= t.res[i];

                if (p < bestP) {
                    bestP = p;
                    best = s;
                }
            }

            if (best == -1) return false;

            assign.put(t.id, best);
            for (int i = 0; i < 4; i++)
                used[best][i] += t.res[i];
        }

        return true;
    }
}

Task 6: Empirical Analysis & Benchmarking

Explanation of Columns
Penalty → final objective value
Runtime → execution time
Feasible → whether valid assignment found
Approx Ratio → only for small instances (compared with brute force)
Chart 1: Penalty vs n
X-axis → n
Y-axis → penalty
Chart 2: Runtime vs n
X-axis → n
Y-axis → runtime

Linear Runtime Growth
Matches complexity 
𝑂(𝑛⋅𝐾)

Penalty increases with n
More tasks → more delay + resource usage


Sparse Graph Advantage
n=200, K=20, density=0.10

long start = System.currentTimeMillis();

boolean feasible = scheduler.solve(tasks);

long end = System.currentTimeMillis();

System.out.println("Runtime: " + (end - start) + " ms");
System.out.println("Feasible: " + feasible);

if (feasible) {
    System.out.println("Assignment: " + scheduler.assign);
}