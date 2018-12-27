package main.java.ua.nure.kn.yesipov.agent;

import jade.core.AID;
import jade.core.Agent;
import main.java.ua.nure.kn.yesipov.User;
import main.java.ua.nure.kn.yesipov.database.DaoFactory;
import main.java.ua.nure.kn.yesipov.database.DatabaseException;

import java.util.Collection;

public class SearchAgent extends Agent {
    private AID[] aids;

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

    public void search(String firstName, String lastName) throws SearchException {
        try {
            Collection users = DaoFactory.getInstance().getUserDao().find(firstName, lastName);
            if (!users.isEmpty()) {
                showUsers(users);
            } else {
                addBehaviour(new SearchRequestBehaviour(aids, firstName, lastName));
            }
        } catch (DatabaseException e) {
            throw new SearchException(e);
        }
    }

    public void showUsers(Collection<User> users) {

    }
}
