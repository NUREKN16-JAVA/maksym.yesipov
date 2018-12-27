package main.java.ua.nure.kn.yesipov.agent;

import jade.core.Agent;

public class SearchAgent extends Agent {
    @Override
    protected void setup() {
        super.setup();
        System.out.println(getAID().getName() + " agent started");
    }

    @Override
    protected void takeDown() {
        super.takeDown();
        System.out.println(getAID().getName() + " agent finished");
    }
}
