package part1.week2.practice;

/**
 * Created by seven on 2017/1/22.
 */
public class Temperature implements Comparable<Temperature> {
    private final double degrees;

    public Temperature(double degrees) {
        if (Double.isNaN(degrees))
            throw new IllegalArgumentException();
        this.degrees = degrees;
    }

    public int compareTo(Temperature that) {
        double EPSILON = 0.1;
        if (this.degrees < that.degrees - EPSILON) return -1;
        if (this.degrees > that.degrees + EPSILON) return +1;
        return 0;
    }

    public static void main(String []args){
        Temperature a = new Temperature(10.16);
        Temperature b = new Temperature(10.08);
        Temperature c = new Temperature(10.00);
        System.out.println("a:" + a.compareTo(b) + ",b:"
                + b.compareTo(c) + ", c:" + a.compareTo(c));

    }
}