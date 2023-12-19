package edu.upc.dsa.manager;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import edu.upc.dsa.exceptions.*;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.Date;

public class ManagerImplTest {

    Manager m;

    @BeforeEach
    public void setUp() throws UsernameDoesExist, SQLException, UsernameDoesNotExistException {
        this.m = new ManagerImpl();
        m.register("robertoguarneros11","123","roberto@gmail.com","Guarneros","Roberto","02/11/2002");
        m.register("titi", "456","titi@gmail.com","Carles","Sanchez","02/11/2002");
        m.register("Luxu","789","lucia@gmail.com","Lucia","Ocaña","02/11/2002");
        m.register("Xuculup","000","Ángel","angel@gmail.com","Redondo","02/11/2002");

    }
    @AfterEach
    public void tearDown() {
        this.m = null;
    }
    @Test
    public void testStoreSize() {//test to verify that we only have 2 store items as previously created in the beforeEach.
        Assert.assertEquals(2,this.m.storeSize());
    }
    @Test
    public void testNumberOfUsers() {//test to verify that we only have 4 store items as previously created in the beforeEach.
        Assert.assertEquals(4,this.m.numberOfUsers());
    }
    @Test
    public void testGetUser() throws UsernameDoesNotExistException {//Tests to try getting the info of a user.
        Assert.assertEquals("robertoguarneros11",this.m.getUser("robertoguarneros11").getUsername());
        Assert.assertEquals("Roberto",this.m.getUser("robertoguarneros11").getName());
        Assert.assertEquals("123",this.m.getUser("robertoguarneros11").getPassword());
        Assert.assertEquals("Guarneros",this.m.getUser("robertoguarneros11").getSurname());
        Assert.assertEquals("roberto@gmail.com",this.m.getUser("robertoguarneros11").getMail());
        Assert.assertEquals("02/11/2002",this.m.getUser("robertoguarneros11").getBirthDate());

        //Test exception
        Assert.assertThrows(UsernameDoesNotExistException.class,()->this.m.getUser("dsaas"));
    }
    @Test
    public void testGetAllUsers() {//Tests to verify we get an array of all users
        Assert.assertEquals(4,this.m.getAllUsers().size());
        Assert.assertTrue("Username should be either robertoguarneros11, tit, Luxu, or Xuculp",
                "robertoguarneros11".equals(this.m.getAllUsers().get(0).getUsername()) ||
                        "titi".equals(this.m.getAllUsers().get(0).getUsername()) ||
                        "Luxu".equals(this.m.getAllUsers().get(0).getUsername()) ||
                        "Xuculp".equals(this.m.getAllUsers().get(0).getUsername()));
        Assert.assertEquals("titi",this.m.getAllUsers().get(2).getUsername());

    }
    @Test
    public void testGetObjectListFromStore() {//tests that verify we are getting an array of objects
        Assert.assertEquals(2,this.m.getObjectListFromStore().size());
        Assert.assertEquals("Poción",this.m.getObjectListFromStore().get(0).getArticleName());
        Assert.assertEquals("skin",this.m.getObjectListFromStore().get(1).getArticleName());
    }
    /*@Test
    public void testRegister() throws UsernameDoesExist{
        this.m.register("prueba","123","Roberto","Guarneros","prueba.com","02/11/2002");
        Assert.assertEquals(5,this.m.numberOfUsers());
        this.m.register("prueba2","123","Roberto","Guarneros","prueba.com","02/11/2002");
        Assert.assertEquals(6,this.m.numberOfUsers());

        //Test to verify that if we repeat a username, it is not created

        Assert.assertThrows(UsernameDoesExist.class,()->this.m.register("prueba","123","Roberto","Guarneros","prueba.com","02/11/2002"));
    }*/
    @Test
    public void testLogin() throws UsernameDoesNotExistException, IncorrectPassword {//test to try login, if password is correct it returns true, if it is wrong it returns false
        boolean True;
        Assert.assertTrue(this.m.login("robertoguarneros11","123"));

        Assert.assertThrows(IncorrectPassword.class,()->this.m.login("robertoguarneros11","12sdaf3"));
    }
   /* @Test
    public void testAddItemToUser() throws UsernameDoesNotExistException, ObjectIDDoesNotExist,AlreadyOwned,NotEnoughPoints {//test to verify if we can add items to a user list.
        //We first need to add points to the user so that it can purchase.
        this.m.getUser("robertoguarneros11").setPointsEarned(500);
        Assert.assertEquals(500,this.m.getUser("robertoguarneros11").getPointsEarned());
        //Now we add items to the list
        this.m.addItemToUser("robertoguarneros11",m.getObject("123"));
        Assert.assertEquals(1,this.m.getUser("robertoguarneros11").getOwnedObjects().size());
        this.m.addItemToUser("robertoguarneros11",m.getObject("222"));
        Assert.assertEquals(2,this.m.getUser("robertoguarneros11").getOwnedObjects().size());
        Assert.assertEquals("Poción",this.m.getUser("robertoguarneros11").getOwnedObjects().get(0).getArticleName());
        Assert.assertEquals("skin",this.m.getUser("robertoguarneros11").getOwnedObjects().get(1).getArticleName());

        //Try exceptions
        Assert.assertThrows(UsernameDoesNotExistException.class,()->this.m.addItemToUser("asdasdfa",m.getObject("123")));
        Assert.assertThrows(ObjectIDDoesNotExist.class,()->this.m.addItemToUser("robertoguarneros11",m.getObject("asadf")));
        Assert.assertThrows(AlreadyOwned.class,()->this.m.addItemToUser("robertoguarneros11",m.getObject("123")));
        Assert.assertThrows(NotEnoughPoints.class,()->this.m.addItemToUser("titi",m.getObject("123")));
    }*/
    @Test
    public void testCreateMatch() throws UsernameDoesNotExistException, UsernameIsInMatchException{//test to create a new match, we need to implement a getMatch in the ManagerImpl in order to be able to first see if the user has a current match or not.
        //Verify user is not in match
        Assert.assertNull(this.m.getLastMatch("robertoguarneros11"));

        //Create a match
        this.m.createMatch("robertoguarneros11");
        Assert.assertNotNull(this.m.getLastMatch("robertoguarneros11"));
        Assert.assertEquals(0, this.m.getLastMatch("robertoguarneros11").getTotalPoints());
        Assert.assertEquals(3, this.m.getLastMatch("robertoguarneros11").getMaxLVL());

        //Testing exceptions
        Assert.assertThrows(UsernameDoesNotExistException.class,()->this.m.createMatch("usernoexiste"));
        Assert.assertThrows(UsernameIsInMatchException.class,()->this.m.createMatch("robertoguarneros11"));
    }
    @Test
    public void testGetLevelFromMatch() throws UsernameDoesNotExistException, UsernameisNotInMatchException, UsernameIsInMatchException {
        //Create a match to try it.
        this.m.createMatch("robertoguarneros11");
        //Get level
        Assert.assertEquals(1, this.m.getLevelFromMatch("robertoguarneros11"));

        //Testing exceptions
        Assert.assertThrows(UsernameDoesNotExistException.class,()->this.m.getLevelFromMatch("usernoexiste"));
        Assert.assertThrows(UsernameisNotInMatchException.class,()->this.m.getLevelFromMatch("Luxu"));
    }
    @Test
    public void testGetMatchTotalPoints() throws UsernameDoesNotExistException, UsernameisNotInMatchException, UsernameIsInMatchException {
        //Create a match to try it.
        this.m.createMatch("robertoguarneros11");
        //Get MatchTotalPoints
        Assert.assertEquals(0,this.m.getLastMatch("robertoguarneros11").getTotalPoints());
        //Increase total points to try getting them
        this.m.getLastMatch("robertoguarneros11").setTotalPoints(100);
        Assert.assertEquals(100,this.m.getLastMatch("robertoguarneros11").getTotalPoints());

        //Testing exceptions
        Assert.assertThrows(UsernameDoesNotExistException.class,()->this.m.getMatchTotalPoints("usernoexiste"));
        Assert.assertThrows(UsernameisNotInMatchException.class,()->this.m.getMatchTotalPoints("Luxu"));
    }
    /*@Test
    public void testNextLevel() throws UsernameDoesNotExistException, UsernameisNotInMatchException, UsernameIsInMatchException {
        //Create a match to try it.
        this.m.createMatch("robertoguarneros11");
        //Test changing level from 1 to 2
        this.m.nextLevel("robertoguarneros11",100);
        Assert.assertEquals(100,this.m.getMatch("robertoguarneros11").getTotalPoints());
        Assert.assertEquals(2,this.m.getMatch("robertoguarneros11").getCurrentLVL());
        //Test changing from 2 to 3
        this.m.nextLevel("robertoguarneros11",100);
        Assert.assertEquals(200,this.m.getMatch("robertoguarneros11").getTotalPoints());
        Assert.assertEquals(3,this.m.getMatch("robertoguarneros11").getCurrentLVL());
        //Test changing level from 3, it should end match.
        this.m.nextLevel("robertoguarneros11",100);
        Assert.assertNull(this.m.getMatch("robertoguarneros11"));

        //Testing exceptions
        Assert.assertThrows(UsernameDoesNotExistException.class,()->this.m.nextLevel("usernoexiste",100));
        Assert.assertThrows(UsernameisNotInMatchException.class,()->this.m.nextLevel("Luxu",100));
    }*/
   /* @Test
    public void testEndMatch() throws UsernameDoesNotExistException, UsernameisNotInMatchException, UsernameIsInMatchException {
        //Create a match to try it.
        this.m.createMatch("robertoguarneros11");
        this.m.endMatch("robertoguarneros11");
        Assert.assertNull(this.m.getMatch("robertoguarneros11"));
        Assert.assertEquals(0,this.m.getUser("robertoguarneros11").getMatchesPlayed().get(0).getTotalPoints());//verify our ended match was added to the list and has 0 points
        Assert.assertEquals(1,this.m.getUser("robertoguarneros11").getMatchesPlayed().get(0).getCurrentLVL());
        Assert.assertEquals(0,this.m.getUser("robertoguarneros11").getMatchesPlayed().get(0).getPointsObtainedPerLevel().size());
    }*/
    @Test
    public void testAddObjectToStore() throws ObjectIDDoesNotExist {
        this.m.addObjectToStore("543","Poción", 100, "Poción de salto");
        Assert.assertEquals(3,this.m.getObjectListFromStore().size());
        Assert.assertEquals(100,this.m.getObject("543").getPrice());
    }
    /*@Test
    public void testGetPlayedMatches() throws UsernameDoesNotExistException, UsernameIsInMatchException, UsernameisNotInMatchException {//we could add exception user does not exist
        this.m.createMatch("robertoguarneros11");
        this.m.endMatch("robertoguarneros11");
        Assert.assertEquals(1, this.m.getPlayedMatches("robertoguarneros11").size());
        this.m.createMatch("robertoguarneros11");
        this.m.endMatch("robertoguarneros11");

        Assert.assertEquals(2, this.m.getPlayedMatches("robertoguarneros11").size());
        this.m.createMatch("robertoguarneros11");
        this.m.endMatch("robertoguarneros11");

        Assert.assertEquals(3, this.m.getPlayedMatches("robertoguarneros11").size());
    }*/
}