package com.darkjeff.ducks;

public class MiniDuckSimulator {

    public static void main(String[] args) {

        Duck mallardDuck = new MallardDuck("Mallard");
        Duck rubberDuck = new RubberDuck("Rubber");
        Duck decoyDuck = new DecoyDuck("Decoy");

        mallardDuck.performFly();
        mallardDuck.performQuack();

        rubberDuck.performFly();
        rubberDuck.performQuack();

        decoyDuck.performFly();
        decoyDuck.performQuack();

        decoyDuck.setFlyBehavior(new FlyRocketPowered());

        decoyDuck.performFly();

    }

}
