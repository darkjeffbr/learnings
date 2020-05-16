package com.darkjeff.ducks;

public class MuteQuack implements QuackBehavior {
    public void quack() {
        System.out.println("Silence");
    }
}
