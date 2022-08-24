package ru.prostokventin;


public class TestsResult {
    private int failed;
    private int succeed;
    private int total;

    public TestsResult(int failed, int succeed, int total) {
        this.failed = failed;
        this.succeed = succeed;
        this.total = total;
    }

    public void addFailed() {
        this.failed++;
    }

    public void addSucceed() {
        this.succeed++;
    }

    public int getFailed() {
        return failed;
    }

    public int getSucceed() {
        return succeed;
    }

    public int getTotal() {
        return total;
    }
}
