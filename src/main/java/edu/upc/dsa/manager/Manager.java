package edu.upc.dsa.manager;

import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.models.OwnedObjects;
import edu.upc.dsa.models.Users;
import edu.upc.dsa.models.StoreObject;
import edu.upc.dsa.models.Matches;

import java.sql.SQLException;
import java.util.List;

public interface Manager {
    public int size();
    public int numberOfUsers();
    public Users getUser(String username)throws UsernameDoesNotExistException;
    public List<Users> getAllUsers();
    public List<StoreObject>  getObjectListFromStore();
   // public List<Matches> getPlayedMatches(String username);
    public void register(String username, String password, String mail, String name, String surname,  String birthDate) throws UsernameDoesExist, SQLException;
    public boolean login (String username, String password) throws UsernameDoesNotExistException, IncorrectPassword;
    public void addItemToUser(String username, String objectID) throws UsernameDoesNotExistException, ObjectIDDoesNotExist,NotEnoughPoints, AlreadyOwned; //add ObjectID to users list of objects
    public void createMatch(String username) throws UsernameDoesNotExistException, UsernameIsInMatchException;
    public int getLevelFromMatch(String username) throws UsernameDoesNotExistException, UsernameisNotInMatchException;
    public int getMatchTotalPoints(String username) throws UsernameDoesNotExistException, UsernameisNotInMatchException;
   // public void nextLevel(String username, int points)throws UsernameDoesNotExistException, UsernameisNotInMatchException;
    //public void endMatch(String username)throws UsernameDoesNotExistException, UsernameisNotInMatchException;
    public int storeSize();
    public void addObjectToStore(String objectID, String articleName, int price, String description);//create a new item available on the store
    public StoreObject getObject(String objectID)throws ObjectIDDoesNotExist;
    public Matches getMatch(String username);
    public List<OwnedObjects> getOwnedObjects(String username)throws UsernameDoesNotExistException;
}