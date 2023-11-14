package edu.upc.dsa;

import edu.upc.dsa.exceptions.UsernameDoesNotExistException;
import edu.upc.dsa.exceptions.UsernameIsInMatchException;
import edu.upc.dsa.exceptions.UsernameisNotInMatchException;
import edu.upc.dsa.models.Track;
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
    //HashMaps are more comfortable to use
    HashMap<String,User> users; //Key = username
    HashMap<String, StoreObject> storeObjects; //Key = objectID
    HashMap<String,Match> matches; // Key = username


    private static Manager instance;
    final static Logger logger = Logger.getLogger(ManagerImpl.class);

    public static Manager getInstance(){
        if(instance==null) instance = new ManagerImpl();
        return instance;
    }

    private ManagerImpl(){
        this.users = new HashMap<>();
        this.storeObjects = new HashMap<>();
        this.matches = new HashMap<>();
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
    public User getUser(String username) {
        logger.info("getUser("+username+")");
       return users.get(username);
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
        logger.info("Create user with ID= "+username);
        if(!users.containsKey(username)){
            User newUser = new User(username, password,mail,name,surname,age);
            users.put(username, newUser); //add new user
            logger.info("User successfully created");
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
            user.addNewOwnedObject(item);
            logger.info("The item:"+item+"was added into the User:"+username);
        }
        else logger.warn("User don't exit");
    }

    @Override
    public void createMatch(String username)throws UsernameDoesNotExistException, UsernameIsInMatchException {
        logger.info("Create match for user "+username);
        if (!this.users.containsKey(username)){
            logger.warn("User does not exist");
            throw new UsernameDoesNotExistException("User does not exist");
        }else if (matches.get(username).isInMatch()){
            logger.warn("User is currently in match");
            throw new UsernameIsInMatchException("User is in match");
        }else {
            Match m = new Match(username);
            matches.put(username, m);
            logger.info("Match created");
        }
    }

    @Override
    public int getLevelFromMatch(String username) throws UsernameDoesNotExistException, UsernameisNotInMatchException {
        logger.info("Show level from current match");
        if (!this.users.containsKey(username)){
            logger.warn("User does not exist");
            throw new UsernameDoesNotExistException("User does not exist");
        }else if(!matches.get(username).isInMatch()){
            logger.warn(username+"is not in a match");
            throw new UsernameisNotInMatchException("User is not in a match");
        } else {
            int level = matches.get(username).getCurrentLVL();
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
        }else if(!matches.get(username).isInMatch()){
            logger.warn(username+"is not in a match");
            throw new UsernameisNotInMatchException("User is not in a match");
        } else {
            int points = matches.get(username).getTotalPoints();
            logger.info("Total current points: "+points);
            return points;
        }
    }

    @Override
    public void NextLevel(String username, int points) throws UsernameDoesNotExistException, UsernameisNotInMatchException {
        logger.info("Save user "+username+" goes to the next level with "+points);
        if (!this.users.containsKey(username)){
            logger.warn("User does not exist");
            throw new UsernameDoesNotExistException("User does not exist");
        }else if(!matches.get(username).isInMatch()){
            logger.warn(username+"is not in a match");
            throw new UsernameisNotInMatchException("User is not in match");
        } else {
            if(matches.get(matches).getCurrentLVL()<matches.get(username).getMaxLVL()){
                matches.get(username).nextLevel(points);
                logger.info("User has changed to the next level");
            }else{
                //User was in last level
                matches.get(username).endMatch(points);
                Match m = matches.get(username);
                users.get(username).addNewFinishedMatch(m);
                logger.info("User has finished match, all levels passed");
            }
        }
    }

    @Override
    public void EndMatch(String username) throws UsernameDoesNotExistException, UsernameisNotInMatchException{
        if (!this.users.containsKey(username)){
            logger.warn("User does not exist");
            throw new UsernameDoesNotExistException("User does not exist");
        }else if(!matches.get(username).isInMatch()){
            logger.warn(username+"is not in a match");
            throw new UsernameisNotInMatchException("User is not in match");
        } else {
            matches.get(username).endMatchNow();
            Match m = matches.get(username);
            users.get(username).addNewFinishedMatch(m);
            logger.info("User has ended match");
        }
    }

}
