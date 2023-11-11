package edu.upc.dsa;

import edu.upc.dsa.models.User;
import edu.upc.dsa.models.Match;
import edu.upc.dsa.models.Store;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.List;

public class ManagerImpl implements Manager{
    private static Manager instance;
    protected List<Store> stores;
    protected List<Match> matches;
    protected List<User> users;
    final static Logger logger = Logger.getLogger(ManagerImpl.class);

    private ManagerImpl(){
        this.matches = new LinkedList<>();
        this.stores = new LinkedList<>();
        this.users = new LinkedList<>();
    }
    private static Manager getInstance(){
        if(instance==null) instance = new ManagerImpl();
        return instance;
    }
}
