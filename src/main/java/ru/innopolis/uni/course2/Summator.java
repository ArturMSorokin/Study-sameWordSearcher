package ru.innopolis.uni.course2;

/**
 * Created by olymp on 11.11.2016.
 */
public class Summator {
    private int firstOperand;
    private int secondOperand;

    public Summator(int firstOperand, int secondOperand) {
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
    }
    public int sum() {
        return this.firstOperand+this.secondOperand;
    }
    static public boolean test() {
        Summator summator = new Summator(2,2);
        return (summator.sum()==4);
    }
}
