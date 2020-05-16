package com.darkjeff.ducks;

public class RubberDuck extends Duck {
    public RubberDuck(String name) {
        super(name);
        this.flyBehavior = new FlyNoWay();
        this.quackBehavior = new Squeak();
    }

}
