package ru.calculator;

public class Summator {
    private int sum = 0;
    private int prevValue = 0;
    private int prevPrevValue = 0;
    private int sumLastThreeValues = 0;
    private int someValue = 0;
    private int valuesCount = 0;

    //!!! сигнатуру метода менять нельзя
    public void calc(Data data) {
        int value = data.getValue();

        valuesCount++;
        if (valuesCount % 6_600_000 == 0) {
            valuesCount = 0;
        }
        sum += value;

        sumLastThreeValues = value + prevValue + prevPrevValue;

        prevPrevValue = prevValue;
        prevValue = value;

        for (var idx = 0; idx < 3; idx++) {
            someValue += (sumLastThreeValues * sumLastThreeValues / (value + 1) - sum);
            someValue = Math.abs(someValue) + valuesCount;
        }
    }

    public int getSum() {
        return sum;
    }

    public int getPrevValue() {
        return prevValue;
    }

    public int getPrevPrevValue() {
        return prevPrevValue;
    }

    public int getSumLastThreeValues() {
        return sumLastThreeValues;
    }

    public int getSomeValue() {
        return someValue;
    }
}
