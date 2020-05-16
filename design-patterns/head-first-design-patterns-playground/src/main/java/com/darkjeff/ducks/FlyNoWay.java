package com.darkjeff.ducks;

public class FlyNoWay implements FlyBehavior {
    public void fly() {
        System.out.println("Sorry, I cannot fly :( ");
    }
}
