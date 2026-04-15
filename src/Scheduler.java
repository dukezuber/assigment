import java.util.*;

class Scheduler {
    int K;
    double[][] cap, used;
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
        for (int i = 0; i < 4; i++)
            if (used[s][i] + t.res[i] > cap[s][i])
                return false;
        return true;
    }

    double penalty(List<Task> tasks) {
        double p = 0;
        double lambda1 = 5, lambda2 = 3;

        for (Task t : tasks)
            if (assign.containsKey(t.id))
                p += t.weight * assign.get(t.id);

        for (int s = 0; s < K; s++) {
            double unused = 0, total = 0;
            for (int i = 0; i < 4; i++) {
                unused += cap[s][i] - used[s][i];
                total += cap[s][i];
            }
            p += lambda1 * (unused / total);
        }

        for (Task t : tasks) {
            if (assign.containsKey(t.id)) {
                int s = assign.get(t.id);
                p += lambda2 * ((double)(s - t.l) / (t.u - t.l + 1));
            }
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