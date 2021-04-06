package org.example;

public class Main {

    public static final Shop shop = new Shop();

    public static void main(String[] args) {
        System.out.println("A = " + shop.processEvent('A'));
        System.out.println("A = " + shop.processEvent('A'));
        System.out.println("A = " + shop.processEvent('A'));
        System.out.println("A = " + shop.processEvent('A'));
        System.out.println("A = " + shop.processEvent('A'));
        System.out.println("4 = " + shop.processEvent('4'));
        System.out.println("4 = " + shop.processEvent('4'));
        System.out.println("A = " + shop.processEvent('A'));
    }
}
