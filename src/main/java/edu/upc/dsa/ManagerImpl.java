package edu.upc.dsa;

import edu.upc.dsa.models.User;
import edu.upc.dsa.models.Match;
import edu.upc.dsa.models.StoreObject;
import org.apache.log4j.Logger;
import org.reflections.Store;

import java.util.ArrayList;
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
    public User getUser(String username) {logger.info("getUser("+username+")");
       return users.get(username);
    }

    @Override
    public void createUser(String username) {


    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public List<StoreObject> getObjectListFromStore() {
        return new ArrayList<>(storeObjects.values());
    }

    @Override
    public List<Match> getPlayedMatches(String username) {
        User user = users.get(username);
        if(user != null){
            return user.getMatchesPlayed();
        }else return null;
    }

    @Override
    public void Register(String username, String password, String name, String surname, String mail, int age) {
        if(!users.containsKey(username)){
            User newUser = new User(username, password,mail,name,surname,age);
            users.put(username, newUser); //add new user
            logger.info("new user added");
        }
        else logger.warn("this username already exit");
    }

    @Override
    public void Login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)){
            logger.info("Welcome User:"+username);
        }
        else{logger.warn("Username or Password was incorrect");}
    }

    @Override
    public void addItemToUser(String username, StoreObject item) {
        User user = users.get(username);
        if(user != null){
            user.addObeject(item);
            logger.info("The item:"+item+"was added into the User:"+username);
        }
        else logger.warn("User don't exit");
    }

    @Override
    public void createMatch(String username) {
        User user = users.get(username);
        Match match = new Match(username);
        if(user != null){
            matches.put(username, match);
            logger.info("The User:"+user+ "was started new game");
        }
        else logger.warn("User don't exist");
    }

    @Override
    public int getLevelFromMatch(String username) {
        Match match = matches.get(username);
        if(match != null){
            logger.info("The level you are is:"+match.getCurrentLVL());
            return match.getCurrentLVL();
        }
        return -1;
    }

    @Override
    public int getMatchTotalPoints(String username) {
        Match match = matches.get(username);
        if(match != null){
            logger.info("You have:"+match.SumAllPoints()+"total points");
            return match.SumAllPoints();
        }
        return -1;
    }

    @Override
    public void NextLevel(String username, int points) {

    }

    @Override
    public void EndMatch(String username) {

    }

}
