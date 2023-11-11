package edu.upc.dsa;

import edu.upc.dsa.models.User;
import edu.upc.dsa.models.Match;
import edu.upc.dsa.models.StoreObject;
import org.apache.log4j.Logger;
import org.reflections.Store;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ManagerImpl implements Manager{
    //Lo hable con el profe y es mejor usar HashMaps
    HashMap<String,User> users; //Key = username
    HashMap<String, StoreObject> storeObjects; //Key = objectID
    HashMap<String,Match> matches; // Key = username


    private static Manager instance;
    final static Logger logger = Logger.getLogger(ManagerImpl.class);

    private static Manager getInstance(){
        if(instance==null) instance = new ManagerImpl();
        return instance;
    }

    private ManagerImpl(){
        this.users = new HashMap<>();
        this.storeObjects = new HashMap<>();
        this.matches = new HashMap<>();
    }
    @Override
    public int numberOfUsers(){
        return this.users.size();
    }

    //Faltan por hacer los de abajo
    @Override
    public User getUser(String username) {
        return null;
    }

    @Override
    public void createUser(String username) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public List<StoreObject> getObjectListFromStore() {
        return null;
    }

    @Override
    public List<Math> getPlayedMatches(String username) {
        return null;
    }

    @Override
    public void Register(String username, String password, String name, String surname, String mail, int age) {

    }

    @Override
    public void Login(String username, String password) {

    }

    @Override
    public void addItemToUser() {

    }

    @Override
    public void createMatch(String username) {

    }

    @Override
    public int getLevelFromMatch(String username) {
        return 0;
    }

    @Override
    public int getMatchTotalPoints(String username) {
        return 0;
    }

    @Override
    public void NextLevel(String username, int points) {

    }

    @Override
    public void EndMatch(String username) {

    }

}
