package com.darkjeff.ducks;

public class DecoyDuck extends Duck {
    public DecoyDuck(String name) {
        super(name);
        this.flyBehavior = new FlyNoWay();
        this.quackBehavior = new MuteQuack();
    }
}
