package edu.upc.dsa;

import edu.upc.dsa.models.User;
import edu.upc.dsa.models.Store;

import java.util.List;

public interface Manager {
    public User getUser(String username);
    public List<User> getAllUsers();
    public List<Store>  getObjectListFromStore();
    public List<Math> getMatches(String username);
    public void Register(String username, String password,
                         String name, String surname, String mail, int age);
    public void Login (String username, String password);
    public void addItemToUser(); //preguntar al profe

}
