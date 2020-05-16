package com.darkjeff.ducks;

public abstract class Duck {

    private String name;
    protected FlyBehavior flyBehavior;
    protected QuackBehavior quackBehavior;

    public Duck(String name){
        this.name = name;
    }

    public void display(){
        System.out.println(name);
    }

    public void swim(){
        System.out.println(name + " is now swimming" );
    }

    public void performQuack() {
        quackBehavior.quack();
    }

    public void performFly() {
        flyBehavior.fly();
    }

    public void setFlyBehavior(FlyBehavior flyBehavior) {
        this.flyBehavior = flyBehavior;
    }

    public void setQuackBehavior(QuackBehavior quackBehavior) {
        this.quackBehavior = quackBehavior;
    }
}
