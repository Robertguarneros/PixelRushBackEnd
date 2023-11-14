package edu.upc.dsa.manager;

import edu.upc.dsa.exceptions.UsernameDoesNotExistException;
import edu.upc.dsa.exceptions.UsernameIsInMatchException;
import edu.upc.dsa.exceptions.UsernameisNotInMatchException;
import edu.upc.dsa.models.User;
import edu.upc.dsa.models.StoreObject;
import edu.upc.dsa.models.Match;

import java.util.List;

public interface Manager {
    public int numberOfUsers();
    public User getUser(String username);
    public List<User> getAllUsers();
    public List<StoreObject>  getObjectListFromStore();
    public List<Match> getPlayedMatches(String username);
    public void register(String username, String password, String name, String surname, String mail, int age);
    public boolean login (String username, String password);
    public void addItemToUser(String username, StoreObject item); //add ObjectID to users list of objects
    public void createMatch(String username) throws UsernameDoesNotExistException, UsernameIsInMatchException;
    public int getLevelFromMatch(String username) throws UsernameDoesNotExistException, UsernameisNotInMatchException;
    public int getMatchTotalPoints(String username) throws UsernameDoesNotExistException, UsernameisNotInMatchException;
    public void NextLevel(String username, int points)throws UsernameDoesNotExistException, UsernameisNotInMatchException;
    public void EndMatch(String username)throws UsernameDoesNotExistException, UsernameisNotInMatchException;
    public int storeSize();
    public void addObjectToStore(String objectID, String articleName, int price, String description);//create a new item available on the store
}
