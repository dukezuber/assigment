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