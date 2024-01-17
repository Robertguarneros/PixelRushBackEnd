package edu.upc.dsa.manager;

import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.*;
import org.apache.log4j.Logger;
import session.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import session.FactorySession;

public class ManagerImpl implements Manager{
    private static Manager instance;
    HashMap<String,Matches> activeMatches; // Key = username
    HashMap<String,UserBadges> userBadgesHashMap; // Key = username
    private List<Message> messages;
    final static Logger logger = Logger.getLogger(ManagerImpl.class);

    public static Manager getInstance(){
        if(instance==null) instance = new ManagerImpl();
        return instance;
    }

    public ManagerImpl(){
        this.activeMatches = new HashMap<>();
    }

// These functions have been implemented with Databases and are working correctly
@Override
public int numberOfUsers() {
    List<Users> usersList = getAllUsers();
    if (usersList.size()!=0){
        return usersList.size();
    }else{
        return 0; // Return 0 if there is an error or no users
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

        if (foundUser.getUsername() == null) {
            logger.info("Username: (" + username + ") does not exist");
            throw new UsernameDoesNotExistException("User does not exist");
        }
        return foundUser;
    }
    @Override
    public List<Users> getAllUsers() {
        Session session = null;
        session = FactorySession.openSession();
        List<Users> usersList = (List<Users>) session.getListAll(Users.class);
        session.close();
        return usersList;
    }
    @Override
    public void register(String username, String password,String mail, String name, String surname,  String birthDate) throws UsernameDoesExist, SQLException, UsernameDoesNotExistException {

        Users foundUser = null;
        try{
            foundUser = getUser(username);
        }catch(UsernameDoesNotExistException e){
            foundUser = null;
        }

        Session session = null;
        try{
            if(foundUser!=null){
                throw new UsernameDoesExist("This username already exist");
            }
            Users newUser = new Users(username,password,mail,name,surname,birthDate);
            session = FactorySession.openSession();
            session.save(newUser, username); //username is the primaryKey value
        } finally {
            // Close the session
            if (session != null) {
                session.close();
            }
        }
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
    public int storeSize() {
        List<StoreObject> storeObjectList = getObjectListFromStore();
        if (storeObjectList.size()!=0){
            return storeObjectList.size();
        }else{
            return 0; // Return 0 if there is an error or no users
        }
    }
    @Override
    public void addObjectToStore(String objectID, String articleName, int price, String description) throws ObjectIDDoesNotExist {
        StoreObject foundObject = null;
        foundObject = getObject(objectID);
        Session session = null;
        try{
            if(foundObject.getObjectID()!=null){
                logger.info("Object already exists");
            }
            StoreObject storeObject = new StoreObject(objectID, articleName, price, description);
            session = FactorySession.openSession();
            session.save(storeObject, objectID); //username is the primaryKey value
        } finally {
            // Close the session
            if (session != null) {
                session.close();
            }
        }
    }
    @Override
    public List<StoreObject> getObjectListFromStore() {
        Session session = null;
        session = FactorySession.openSession();
        List<StoreObject> storeObjectList = (List<StoreObject>) session.getListAll(StoreObject.class);
        session.close();
        return storeObjectList;
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
    public int getLevelFromMatch(String username) throws UsernameDoesNotExistException, UsernameisNotInMatchException {
        logger.info("Show level from current match");
        Users foundUser = null;
        foundUser = getUser(username);
        if (foundUser==null){
            logger.warn("User does not exist");
            throw new UsernameDoesNotExistException("User does not exist");
        }else if(activeMatches.get(username)==null){
            logger.warn(username+" is not in a match");
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
        Users foundUser = null;
        foundUser = getUser(username);
        if (foundUser==null){
            logger.warn("User does not exist");
            throw new UsernameDoesNotExistException("User does not exist");
        }else if(activeMatches.get(username)==null){
            logger.warn(username+" is not in a match");
            throw new UsernameisNotInMatchException("User is not in a match");
        } else {
            int points = activeMatches.get(username).getTotalPoints();
            logger.info("Total current points: "+points);
            return points;
        }
    }
    @Override
    public void nextLevel(String username, int points) throws UsernameDoesNotExistException, UsernameisNotInMatchException, SQLException {
        Users foundUser = null;
        foundUser = getUser(username);
        Matches activeMatchExists = activeMatches.get(username);
        if (foundUser==null){
            logger.warn("User does not exist");
            throw new UsernameDoesNotExistException("User does not exist");
        }else if(activeMatchExists==null){
            logger.warn(username+" is not in a match");
            throw new UsernameisNotInMatchException("User is not in match");
        }else if(activeMatchExists.getCurrentLVL()< activeMatchExists.getMaxLVL()){
            logger.info("User will try to be changed to the next level");
            activeMatchExists.nextLevel(points);
            Session session = null;
            session = FactorySession.openSession();
            Matches matchForID =(Matches) session.getMatch(Matches.class,username);
            session.update(Matches.class, "id", "totalPoints", matchForID.getId(),activeMatchExists.getTotalPoints());
            session.update(Matches.class, "id", "currentLVL", matchForID.getId(),activeMatchExists.getCurrentLVL());
            session.close();
            logger.info("User "+username+" goes to the next level with "+points);
            logger.info("User has changed to the next level");
        }else{
            //User was in last level
            logger.info("Trying to end match");
            activeMatchExists.endMatchLastLevel(points);
            Session session = null;
            session = FactorySession.openSession();
            Matches matchForID =(Matches) session.getMatch(Matches.class,username);
            session.update(Matches.class, "id", "totalPoints", matchForID.getId(),activeMatchExists.getTotalPoints());
            int userPoints = foundUser.getPointsEarned()+activeMatchExists.getTotalPoints();
            session.update(Users.class,"username","pointsEarned",username,userPoints);
            session.close();
            logger.info("EndMatch Run");
            activeMatches.remove(username);
            logger.info("User has finished match, all levels passed");
        }
    }
    @Override
    public void endMatch(String username) throws UsernameDoesNotExistException, UsernameisNotInMatchException, SQLException {
        logger.info("End match for ("+username+")");
        Users foundUser = null;
        foundUser = getUser(username);
        if (foundUser==null){
            logger.warn("User does not exist");
            throw new UsernameDoesNotExistException("User does not exist");
        }else if(activeMatches.get(username)==null){
            logger.warn(username+" is not in a match");
            throw new UsernameisNotInMatchException("User is not in match");
        } else {
            Matches m = activeMatches.get(username);
            logger.info("get match for ("+username+")");
            Session session = null;
            session = FactorySession.openSession();
            Matches matchForID =(Matches) session.getMatch(Matches.class,username);
            session.update(Matches.class, "id", "totalPoints", matchForID.getId(), m.getTotalPoints());
            session.update(Matches.class, "id", "currentLVL", matchForID.getId(), m.getCurrentLVL());
            int userPoints = foundUser.getPointsEarned()+m.getTotalPoints();
            session.update(Users.class,"username","pointsEarned",username,userPoints);
            session.close();
            activeMatches.remove(username);
            logger.info("User has ended match");
        }
    }
   @Override
    public List<Matches> getPlayedMatches(String username) throws UsernameDoesNotExistException{
       logger.info("Get played matches for(" + username + ")");
       List<Matches> playedMatches;
       Users userInDB = null;
       userInDB =getUser(username);

       Session session = null;
       session = FactorySession.openSession();
       playedMatches = (List<Matches>) session.getList(Matches.class, "username", username);
       session.close();

       if(userInDB == null){
           logger.warn("User does not exist");
           throw new UsernameDoesNotExistException("User does not exist");
       }
       return playedMatches;
   }
    @Override
    public Matches getLastMatch(String username){
        logger.info("get match for ("+username+")");
        Session session = null;
        session = FactorySession.openSession();
        Matches matches = (Matches) session.getMatch(Matches.class, username);
        session.close();
        return  matches;
    }

    //Minimo 2
    @Override
    public void askQuestion(Question question){
        System.out.println("Date: "+question.getDate()+"\n");
        System.out.println("Title: "+question.getTitle()+"\n");
        System.out.println("Message: "+question.getMessage()+"\n");
        System.out.println("Sender: "+question.getSender()+"\n");
    }
    @Override
    public void sendReport(Report report){
        System.out.println("Date: "+report.getDate()+"\n");
        System.out.println("Title: "+report.getTitle()+"\n");
        System.out.println("Message: "+report.getMessage()+"\n");
        System.out.println("Sender: "+report.getSender()+"\n");
    }
    @Override
    public void addMessage(String message) {
        Message newMessage = new Message(message);
        // Agregar el mensaje a la lista de mensajes.
        this.messages.add(newMessage);
    }
    @Override
    public List<Message> getMessages() { return new ArrayList<>(messages); }
    @Override
    public void addBadge(String user,String name, String avatar){
        Badge badge = new Badge(name,avatar);
        UserBadges userBadges = userBadgesHashMap.get(user);
        if (userBadges == null){
            UserBadges newuserBadges = new UserBadges(user);
            newuserBadges.addBadge(badge);
            userBadgesHashMap.put(user, newuserBadges);
        }else{
            userBadges.addBadge(badge);
        }
    }
    @Override
    public List<Badge> getBadges(String user){
        UserBadges userBadges = userBadgesHashMap.get(user);
        return new ArrayList<>(userBadges.getBadges());
    }
}