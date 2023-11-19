package edu.upc.dsa.manager;

import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.User;
import edu.upc.dsa.models.Match;
import edu.upc.dsa.models.StoreObject;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ManagerImpl implements Manager{
    //HashMaps are more comfortable to use
    HashMap<String,User> users; //Key = username, seems like it inserts in alphabetical order based on username
    HashMap<String, StoreObject> storeObjects; //Key = objectID
    HashMap<String,Match> activeMatches; // Key = username


    private static Manager instance;
    final static Logger logger = Logger.getLogger(ManagerImpl.class);

    public static Manager getInstance(){
        if(instance==null) instance = new ManagerImpl();
        return instance;
    }

    public ManagerImpl(){
        this.users = new HashMap<>();
        this.storeObjects = new HashMap<>();
        this.activeMatches = new HashMap<>();
    }
    public int size(){
        int ret = this.users.size();
        logger.info("Size: " + ret);
        return ret;
    }
    public int storeSize() {
        int ret = this.storeObjects.size();
        logger.info("size " + ret);

        return ret;
    }

    @Override
    public int numberOfUsers(){//this is the same as sizeUsers
        return this.users.size();
    }

    @Override
    public User getUser(String username) throws UsernameDoesNotExistException{
        if(users.get(username)==null){
            throw new UsernameDoesNotExistException("User does not exist");
        }else{
            logger.info("getUser("+username+")");
            return users.get(username);
        }
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
    public StoreObject getObject(String objectID){
        logger.info("Get object ("+objectID+")");
        return storeObjects.get(objectID);
    }
    @Override
    public Match getMatch(String username){
        logger.info("get match for ("+username+")");
        return activeMatches.get(username);
    }

    @Override
    public void register(String username, String password, String name, String surname, String mail, int age) throws UsernameDoesExist {
        logger.info("Create user with ID= "+username);
        if(!users.containsKey(username)){
            User newUser = new User(username, password,mail,name,surname,age);
            users.put(username, newUser); //add new user
            logger.info("User successfully created");
        }
        else {logger.warn("this username already exists");
        throw new UsernameDoesExist("This Username does exist");}
    }

    @Override
    public boolean login(String username, String password) throws UsernameDoesNotExistException, IncorrectPassword {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)){
            logger.info("Welcome User:"+username);
            return true;
        }
        if(!user.getPassword().equals(password)){
            logger.warn("Username or Password was incorrect");
            throw new IncorrectPassword("Username or Password was incorrect");
        }
        if (user == null){
            logger.warn("Username or Password was incorrect");
            throw new UsernameDoesNotExistException("Username or Password was incorrect");
        }
        return false;
    }

    @Override
    public void addItemToUser(String username, StoreObject objectID)throws UsernameDoesNotExistException, ObjectIDDoesNotExist {
        User user = users.get(username);
        if(user == null){
            logger.warn("User does not exist");
            throw new UsernameDoesNotExistException("User does not exist");
        }else if(objectID==null){
            logger.warn("ObjectID does not exist");
            throw new ObjectIDDoesNotExist("ObjectID does not exist");
        } else {
            user.addNewOwnedObject(objectID);
            logger.info("The item:"+objectID+" was added into the User: "+username);
        }
    }

    @Override
    public void createMatch(String username)throws UsernameDoesNotExistException, UsernameIsInMatchException {
        logger.info("Create match for user "+username);
        if (!this.users.containsKey(username)){
            logger.warn("User does not exist");
            throw new UsernameDoesNotExistException("User does not exist");
        }else if (activeMatches.get(username)!=null){
            logger.warn("User is currently in match");
            throw new UsernameIsInMatchException("User is in match");
        }else {
            Match m = new Match(username);
            activeMatches.put(username, m);
            logger.info("Match created");
        }
    }

    @Override
    public int getLevelFromMatch(String username) throws UsernameDoesNotExistException, UsernameisNotInMatchException {
        logger.info("Show level from current match");
        if (!this.users.containsKey(username)){
            logger.warn("User does not exist");
            throw new UsernameDoesNotExistException("User does not exist");
        }else if(activeMatches.get(username)==null){
            logger.warn(username+"is not in a match");
            throw new UsernameisNotInMatchException("User is not in a match");
        } else {
            int level = activeMatches.get(username).getCurrentLVL();
            logger.info("Current level: "+level);
            return level;
        }
    }

    @Override
    public int getMatchTotalPoints(String username)throws UsernameDoesNotExistException, UsernameisNotInMatchException {
        logger.info("Show points from current match");
        if (!this.users.containsKey(username)){
            logger.warn("User does not exist");
            throw new UsernameDoesNotExistException("User does not exist");
        }else if(activeMatches.get(username)==null){
            logger.warn(username+"is not in a match");
            throw new UsernameisNotInMatchException("User is not in a match");
        } else {
            int points = activeMatches.get(username).getTotalPoints();
            logger.info("Total current points: "+points);
            return points;
        }
    }

    @Override
    public void nextLevel(String username, int points) throws UsernameDoesNotExistException, UsernameisNotInMatchException {

        Match activeMatchExists = activeMatches.get(username);
        if (!this.users.containsKey(username)){
            logger.warn("User does not exist");
            throw new UsernameDoesNotExistException("User does not exist");
        }else if(activeMatchExists==null){
            logger.warn(username+"is not in a match");
            throw new UsernameisNotInMatchException("User is not in match");
        }else if(activeMatchExists.getCurrentLVL()< activeMatchExists.getMaxLVL()){
            logger.info("User will try to be changed to the next level");
            activeMatchExists.nextLevel(points);
            logger.info("User "+username+" goes to the next level with "+points);
            logger.info("User has changed to the next level");
        }else{
            //User was in last level
            logger.info("Trying to end match");
            activeMatchExists.endMatchLastLevel(points);
            logger.info("EndMatch Run");
            users.get(username).addNewFinishedMatch(activeMatchExists);
            activeMatches.remove(username);
            logger.info("User has finished match, all levels passed");
        }
    }

    @Override
    public void endMatch(String username) throws UsernameDoesNotExistException, UsernameisNotInMatchException{
        if (!this.users.containsKey(username)){
            logger.warn("User does not exist");
            throw new UsernameDoesNotExistException("User does not exist");
        }else if(activeMatches.get(username)==null){
            logger.warn(username+"is not in a match");
            throw new UsernameisNotInMatchException("User is not in match");
        } else {
            Match m = activeMatches.get(username);
            users.get(username).addNewFinishedMatch(m);
            activeMatches.remove(username);
            logger.info("User has ended match");
        }
    }

    @Override
    public void addObjectToStore(String objectID, String articleName, int price, String description){
        logger.info("Create object with ID= "+objectID);
        if(!storeObjects.containsKey(objectID)){
            StoreObject newObject = new StoreObject(objectID,articleName,price,description);
            storeObjects.put(objectID,newObject);
            logger.info("Object successfully created");
        }
        else logger.warn("this object already exists");
    }

}
