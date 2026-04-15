import java.util.*;

public class Main {
    public static void main(String[] args) {

        int K = 4;
        double[][] cap = {
            {32,128,8,6},
            {32,128,8,6},
            {32,128,8,6},
            {32,128,8,6}
        };

        Map<String, List<String>> graph = new HashMap<>();
        graph.put("T1", Arrays.asList("T2","T3"));
        graph.put("T2", Arrays.asList("T1","T4"));
        graph.put("T3", Arrays.asList("T1","T5"));
        graph.put("T4", Arrays.asList("T2","T6"));
        graph.put("T5", Arrays.asList("T3","T6"));
        graph.put("T6", Arrays.asList("T4","T5"));

        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("T1", new double[]{8,32,4,1.5},1,3,5));
        tasks.add(new Task("T2", new double[]{4,16,0,3},1,4,4));
        tasks.add(new Task("T3", new double[]{2,8,0,2},1,4,3));
        tasks.add(new Task("T4", new double[]{16,64,2,0.5},2,4,2));
        tasks.add(new Task("T5", new double[]{8,32,2,1},1,4,3));
        tasks.add(new Task("T6", new double[]{4,16,0,1.5},2,4,2));

        Scheduler s = new Scheduler(K, cap, graph);

        long start = System.currentTimeMillis();
        boolean res = s.solve(tasks);
        long end = System.currentTimeMillis();

        System.out.println("Feasible: " + res);
        System.out.println("Runtime: " + (end - start) + " ms");
        System.out.println("Assignment: " + s.assign);
    }
}