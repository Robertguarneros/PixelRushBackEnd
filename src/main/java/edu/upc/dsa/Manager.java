package edu.upc.dsa;

import edu.upc.dsa.models.User;
import edu.upc.dsa.models.StoreObject;
import edu.upc.dsa.models.Match;

import java.util.List;

public interface Manager {
    public int numberOfUsers();
    public User getUser(String username);
    public void createUser(String username);
    public List<User> getAllUsers();
    public List<StoreObject>  getObjectListFromStore();
    public List<Match> getPlayedMatches(String username);
    public void Register(String username, String password, String name, String surname, String mail, int age);
    public void Login (String username, String password);
    public void addItemToUser(String username, StoreObject item); //add ObjectID to users list of objects
    public void createMatch(String username);
    public int getLevelFromMatch(String username);
    public int getMatchTotalPoints(String username);
    public void NextLevel(String username, int points);
    public void EndMatch(String username);
    public int sizeUser();
    public int sizeStore();
}
