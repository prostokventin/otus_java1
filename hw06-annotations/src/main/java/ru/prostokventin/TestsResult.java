package ru.prostokventin;


public class TestsResult {
    private int failed;
    private int passed;
    private int total;

    public TestsResult(int failed, int passed, int total) {
        this.failed = failed;
        this.passed = passed;
        this.total = total;
    }

    public void addFailed() {
        this.failed++;
    }

    public void addPassed() {
        this.passed++;
    }

    @Override
    public String toString() {
        return  "Total tests count: " + total + "\n" +
                "Passed tests count: " + passed + "\n" +
                "Failed tests count: " + failed
                ;
    }
}
