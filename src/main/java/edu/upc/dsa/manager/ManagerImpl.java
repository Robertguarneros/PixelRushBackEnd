package edu.upc.dsa.manager;

import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.OwnedObjects;
import edu.upc.dsa.models.Users;
import edu.upc.dsa.models.Matches;
import edu.upc.dsa.models.StoreObject;
import org.apache.log4j.Logger;
import session.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import session.FactorySession;

public class ManagerImpl implements Manager{
    //HashMaps are more comfortable to use
    HashMap<String, Users> users; //Key = username, seems like it inserts in alphabetical order based on username
    HashMap<String, StoreObject> storeObjects; //Key = objectID
    HashMap<String, Matches> activeMatches; // Key = username

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
    public int numberOfUsers() {
        return 0; // Return 0 if there is an error or no users
    }

    @Override
    public List<Users> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public List<StoreObject> getObjectListFromStore() {
        return new ArrayList<>(storeObjects.values());
    }

   /* @Override
    public List<Matches> getPlayedMatches(String username) {
        User user = users.get(username);
        if(user != null){
            return user.getMatchesPlayed();
        }else return null;
    }*/

    @Override
    public Matches getMatch(String username){
        logger.info("get match for ("+username+")");
        return activeMatches.get(username);
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

    /*@Override
    public void nextLevel(String username, int points) throws UsernameDoesNotExistException, UsernameisNotInMatchException {

        Matches activeMatchExists = activeMatches.get(username);
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
    }*/

   /* @Override
    public void endMatch(String username) throws UsernameDoesNotExistException, UsernameisNotInMatchException{
        if (!this.users.containsKey(username)){
            logger.warn("User does not exist");
            throw new UsernameDoesNotExistException("User does not exist");
        }else if(activeMatches.get(username)==null){
            logger.warn(username+"is not in a match");
            throw new UsernameisNotInMatchException("User is not in match");
        } else {
            Matches m = activeMatches.get(username);
            users.get(username).addNewFinishedMatch(m);
            activeMatches.remove(username);
            logger.info("User has ended match");
        }
    }*/


// These functions have been implemented with Databases and are working correctly
    @Override
    public void addObjectToStore(String objectID, String articleName, int price, String description){
        Session session = null;
        try{
            if(storeObjects.containsKey(objectID)){
                logger.info("Object already exists");
            }
            StoreObject storeObject = new StoreObject(objectID, articleName, price, description);
            session = FactorySession.openSession();
            session.save(storeObject, objectID); //username is the primaryKey value
            storeObjects.put(objectID,storeObject);
        } finally {
            // Close the session
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void register(String username, String password,String mail, String name, String surname,  String birthDate) throws UsernameDoesExist,SQLException {
        Session session = null;
        try{
            if(this.users.containsKey(username)){
                throw new UsernameDoesExist("This username already exist");
            }
            Users newUser = new Users(username,password,mail,name,surname,birthDate);
            session = FactorySession.openSession();
            session.save(newUser, username); //username is the primaryKey value
            this.users.put(username, newUser);
        } finally {
            // Close the session
            if (session != null) {
                session.close();
            }
        }
    }
    @Override
    public void createMatch(String username)throws UsernameDoesNotExistException, UsernameIsInMatchException {
        Session session = null;
        logger.info("Create match for user "+username);
        Users userExists = null;
        userExists = getUser(username);
        if (userExists==null){
            logger.warn("User does not exist");
            throw new UsernameDoesNotExistException("User does not exist");
        }else if (activeMatches.get(username)!=null){
            logger.warn("User is currently in match");
            throw new UsernameIsInMatchException("User is in match");
        }else {
            Matches m = new Matches(username);
            session = FactorySession.openSession();
            session.save(m, username); //username is the primaryKey value
            activeMatches.put(username, m);
            logger.info("Match created");
        }
    }
    @Override
    public Users getUser(String username) throws UsernameDoesNotExistException {
        logger.info("getUser(" + username + ")");
        Session session = null;
        Users foundUser = null;

        session = FactorySession.openSession();
        foundUser = (Users)session.get(Users.class, "username", username);
        session.close();

        if (foundUser == null) {
            logger.info("Username: (" + username + ") does not exist");
            throw new UsernameDoesNotExistException("User does not exist");
        }
        return foundUser;
    }
    @Override
    public boolean login(String username, String password) throws UsernameDoesNotExistException, IncorrectPassword {
        boolean loggedIn = false;
        Users userInDB = null;
        userInDB =getUser(username);

        logger.info("username: "+ userInDB.getUsername());
        logger.info("Password: "+ userInDB.getPassword());

        if ((userInDB != null) && userInDB.getPassword().equals(password)){
            logger.info("Welcome User:"+username);
            loggedIn = true;
        }else if(!userInDB.getPassword().equals(password)){
            logger.warn("Username or Password was incorrect");
            throw new IncorrectPassword("Username or Password was incorrect");
        }else if(userInDB == null){
            logger.warn("Username or Password was incorrect");
            throw new UsernameDoesNotExistException("Username or Password was incorrect");
        }
        return loggedIn;
    }

    @Override
    public StoreObject getObject(String objectID)throws ObjectIDDoesNotExist{
        logger.info("getObject(" + objectID + ")");
        Session session = null;
        StoreObject objectInDB = null;

        session = FactorySession.openSession();
        objectInDB = (StoreObject) session.get(StoreObject.class, "objectID", objectID);
        session.close();

        if (objectInDB == null) {
            logger.info("Object: (" + objectID + ") does not exist");
            throw new ObjectIDDoesNotExist("Object does not exist");
        }
        return objectInDB;
    }
    @Override
    public List<OwnedObjects> getOwnedObjects(String username)throws UsernameDoesNotExistException{
        logger.info("getOwnedObjects for(" + username + ")");
        List<OwnedObjects> ownedObjects;
        Users userInDB = null;
        userInDB =getUser(username);

        Session session = null;
        session = FactorySession.openSession();
        ownedObjects = (List<OwnedObjects>) session.getList(OwnedObjects.class, "username", username);
        session.close();

        if(userInDB == null){
            logger.warn("User does not exist");
            throw new UsernameDoesNotExistException("User does not exist");
        }
        return ownedObjects;
    }
    @Override
    public void addItemToUser(String username, String objectID) throws UsernameDoesNotExistException, ObjectIDDoesNotExist, NotEnoughPoints, AlreadyOwned {
        Users userInDB = getUser(username);
        StoreObject objectInDB = getObject(objectID);

        if (userInDB == null) {
            logger.warn("User does not exist");
            throw new UsernameDoesNotExistException("User does not exist");
        } else if (objectInDB == null) {
            logger.warn("ObjectID does not exist");
            throw new ObjectIDDoesNotExist("ObjectID does not exist");
        } else if (objectInDB.getPrice() > userInDB.getPointsEarned()) {
            logger.warn("Not enough points");
            throw new NotEnoughPoints("Not enough points");
        } else {
            List<OwnedObjects> ownedObjects = getOwnedObjects(username);
            for (OwnedObjects ownedObject : ownedObjects) {
                if (ownedObject.getObjectID().equals(objectID)) {
                    logger.warn("Already Owned");
                    throw new AlreadyOwned("Already Owned");
                }
            }

            // If the object is not already owned, proceed with adding it to the user
            OwnedObjects ownedObject = new OwnedObjects(username, objectID);
            Session session = FactorySession.openSession();
            session.save(ownedObject, username);
            session.close();
            logger.info("Item bought");
        }
    }
}