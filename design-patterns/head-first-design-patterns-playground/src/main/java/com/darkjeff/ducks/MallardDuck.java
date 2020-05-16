package com.darkjeff.ducks;

public class MallardDuck extends Duck {

    public MallardDuck(String name) {
        super(name);
        this.flyBehavior = new FlyWithWings();
        this.quackBehavior = new Quack();
    }

}
