package edu.upc.dsa.manager;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ManagerImplTest {

    Manager m;

    @BeforeEach
    public void setUp()  {
        this.m = new ManagerImpl();
        m.register("robertoguarneros11","123","Roberto","Guarneros","roberto@gmail.com",22);
        m.register("titi", "456","Carles","Sanchez","titi@gmail.com",22);
        m.register("Luxu","789","Lucia","Ocaña","lucia@gmail.com",22);
        m.register("Xuculup","000","Ángel","Redondo","angel@gmail.com",21);
        m.addObjectToStore("123","Poción", 100, "Poción de salto");
        m.addObjectToStore("222","skin",50,"skin cosmetica");
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
    public void testGetUser() {//Tests to try getting the info of a user.
        Assert.assertEquals("robertoguarneros11",this.m.getUser("robertoguarneros11").getUsername());
        Assert.assertEquals("Roberto",this.m.getUser("robertoguarneros11").getName());
        Assert.assertEquals("123",this.m.getUser("robertoguarneros11").getPassword());
        Assert.assertEquals("Guarneros",this.m.getUser("robertoguarneros11").getSurname());
        Assert.assertEquals("roberto@gmail.com",this.m.getUser("robertoguarneros11").getMail());
        Assert.assertEquals(22,this.m.getUser("robertoguarneros11").getAge());
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
    @Test
    public void testRegister() {
        this.m.register("prueba","123","Roberto","Guarneros","prueba.com",22);
        Assert.assertEquals(5,this.m.numberOfUsers());
        this.m.register("prueba2","123","Roberto","Guarneros","prueba.com",22);
        Assert.assertEquals(6,this.m.numberOfUsers());

        //Test to verify that if we repeat a username, it is not created
        this.m.register("prueba","123","Roberto","Guarneros","prueba.com",22);
        Assert.assertEquals(6,this.m.numberOfUsers());
    }
    @Test
    public void testLogin() {//test to try login, if password is correct it returns true, if it is wrong it returns false
        boolean True;
        Assert.assertTrue(this.m.login("robertoguarneros11","123"));
        Assert.assertFalse(this.m.login("robertoguarneros11","12sdaf3"));
    }
    @Test
    public void testAddItemToUser() {
    }
    @Test
    public void testCreateMatch() {
    }
    @Test
    public void testGetLevelFromMatch() {
    }
    @Test
    public void testGetMatchTotalPoints() {
    }
    @Test
    public void testNextLevel() {
    }
    @Test
    public void testEndMatch() {
    }
    @Test
    public void testAddObjectToStore(){

    }
    @Test
    public void testGetPlayedMatches() {

    }
}