package com.sashashtmv.waterorder.model;

public class Order {
    private String countWater1;
    private String countWater2;
    private String countWater3;
    private String countPompa;
    private String countTara;
    private String sum;

    public Order(String countWater1, String countWater2, String countWater3, String countPompa, String countTara, String sum) {
        this.countWater1 = countWater1;
        this.countWater2 = countWater2;
        this.countWater3 = countWater3;
        this.countPompa = countPompa;
        this.countTara = countTara;
        this.sum = sum;
    }

    public String getCountWater1() {
        return countWater1;
    }

    public void setCountWater1(String countWater1) {
        this.countWater1 = countWater1;
    }

    public String getCountWater2() {
        return countWater2;
    }

    public void setCountWater2(String countWater2) {
        this.countWater2 = countWater2;
    }

    public String getCountWater3() {
        return countWater3;
    }

    public void setCountWater3(String countWater3) {
        this.countWater3 = countWater3;
    }

    public String getCountPompa() {
        return countPompa;
    }

    public void setCountPompa(String countPompa) {
        this.countPompa = countPompa;
    }

    public String getCountTara() {
        return countTara;
    }

    public void setCountTara(String countTara) {
        this.countTara = countTara;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }
}
