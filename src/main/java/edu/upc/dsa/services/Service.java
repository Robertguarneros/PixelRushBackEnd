package edu.upc.dsa.services;
import edu.upc.dsa.exceptions.ObjectIDDoesNotExist;
import edu.upc.dsa.exceptions.UsernameDoesNotExistException;
import edu.upc.dsa.exceptions.UsernameIsInMatchException;
import edu.upc.dsa.exceptions.UsernameisNotInMatchException;
import edu.upc.dsa.manager.Manager;
import edu.upc.dsa.manager.ManagerImpl;
import edu.upc.dsa.models.Match;
import edu.upc.dsa.models.StoreObject;
import edu.upc.dsa.models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.reflections.Store;


import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/pixelRush", description = "Endpoint to Pixel Rush Service")
@Path("/pixelRush")

public class Service {
    private Manager m;

    public Service() throws UsernameDoesNotExistException, UsernameIsInMatchException, UsernameisNotInMatchException {
        this.m = ManagerImpl.getInstance();
        if(m.size()==0){
            m.register("robertoguarneros11","123","Roberto","Guarneros","roberto@gmail.com",22);
            m.createMatch("robertoguarneros11");
            m.endMatch("robertoguarneros11");

            m.register("titi", "456","Carles","Sanchez","titi@gmail.com",22);
            m.createMatch("titi");
            m.register("Luxu","789","Lucia","Ocaña","lucia@gmail.com",22);
            m.register("Xuculup","000","Ángel","Redondo","angel@gmail.com",21);
            m.addObjectToStore("123","Poción", 100, "Poción de salto");
            m.addObjectToStore("222","skin",50,"skin cosmetica");
        }
    }

    //get store size does not work, fix
    @GET
    @ApiOperation(value = "Get current store size", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Integer.class)
    })
    @Path("/getStoreSize")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStoreSize() {
        int storeSize=this.m.storeSize();

        return Response.status(200).entity(storeSize).build()  ;
    }
//Get number of users does not work, fix
    @GET
    @ApiOperation(value = "Get number of users", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Integer.class)
    })
    @Path("/getNumberOfUsers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNumberOfUsers() {
        int numOfUsers = this.m.numberOfUsers();

        return Response.status(200).entity(numOfUsers).build();
    }

//get user
    @GET
    @ApiOperation(value = "get a user", notes = "given a username")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "OK", response = User.class),
        @ApiResponse(code = 404, message = "Username does not exist")
    })
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("username") String username) {
        User u = null;
        try {
            u = this.m.getUser(username);
        } catch (UsernameDoesNotExistException e) {
            return Response.status(404).build();
        }
        return Response.status(200).entity(u).build();
    }
    //get all users
    @GET
    @ApiOperation(value = "get all users", notes = "return list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = User.class, responseContainer="List"),
    })
    @Path("/getAllUsers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        List<User> users = this.m.getAllUsers();
        GenericEntity<List<User>> entity = new GenericEntity<List<User>>(users) {};
        return Response.status(200).entity(entity).build();
    }
    //get all objects from store
    @GET
    @ApiOperation(value = "get all objects from store", notes = "return list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = StoreObject.class, responseContainer="List"),
    })
    @Path("/getObjectListFromStore")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObjectListFromStore() {
        List<StoreObject> listOfObjects = this.m.getObjectListFromStore();
        GenericEntity<List<StoreObject>> entity = new GenericEntity<List<StoreObject>>(listOfObjects) {};
        return Response.status(200).entity(entity).build();
    }
    //Get played matches by username
    @GET
    @ApiOperation(value = "get played matches from user", notes = "return list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Match.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Username does not exist")
    })
    @Path("/getPlayedMatchesFromUser/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayedMatchesFromUser(@PathParam("username") String username) throws UsernameDoesNotExistException {
        List<Match> playedMatches = this.m.getPlayedMatches(username);
        GenericEntity<List<Match>> entity = new GenericEntity<List<Match>>(playedMatches) {};
        if(!playedMatches.isEmpty()||this.m.getUser(username)!=null) return Response.status(200).entity(entity).build();
        else return Response.status(404).build();
    }
    //Get object
    @GET
    @ApiOperation(value = "get Object information", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = StoreObject.class),
            @ApiResponse(code = 404, message = "ObjectID does not exist")
    })
    @Path("/getObjectInformation/{objectID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObjectInformation(@PathParam("objectID") String objectID) {
        StoreObject object = this.m.getObject(objectID);
        if(object!=null) return Response.status(200).entity(object).build();
        else return Response.status(404).build();
    }
    //Get match
    @GET
    @ApiOperation(value = "get active match", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = StoreObject.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Username does not exist")
    })
    @Path("/getActiveMatch/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObjectListFromStore(@PathParam("username")String username) {
        Match m = this.m.getMatch(username);
        if(m!=null) return Response.status(200).entity(m).build();
        else return Response.status(404).build();
    }
    //register User
    @POST
    @ApiOperation(value = "Register a new user", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User registered successfully")
    })
    @Path("/registerNewUser")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response registerNewUser(User user){
        this.m.register(user.getUsername(), user.getPassword(), user.getName(), user.getSurname(), user.getMail(), user.getAge());
        return Response.status(201).build();
    }
    //login
    @POST
    @ApiOperation(value = "Login", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User login successful")
    })
    @Path("/login")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response login(User user){
        this.m.login(user.getUsername(), user.getPassword());
        return Response.status(201).build();
    }
    // Add item to user
    @PUT
    @ApiOperation(value = "Add item to user", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Item added successfully"),
            @ApiResponse(code = 404, message = "Username does not exist or objectID does not exist")
    })
    @Path("/addItemToUser/{username}/{objectID}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addItemToUser(@PathParam("username")String username,@PathParam("objectID")String objectID){
        try {
            this.m.addItemToUser(username,this.m.getObject(objectID));
        } catch(UsernameDoesNotExistException | ObjectIDDoesNotExist e){
            return Response.status(404).build();
        }
        return Response.status(201).build();
    }
    // Create a new Match
    @PUT
    @ApiOperation(value = "Create a new Match", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Match created successfully"),
            @ApiResponse(code = 404, message = "Username does not exist or user is already in Match")
    })
    @Path("/createMatch/{username}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createMatch(@PathParam("username")String username){
        try {
            this.m.createMatch(username);
        } catch(UsernameDoesNotExistException | UsernameIsInMatchException e){
            return Response.status(404).build();
        }
        return Response.status(201).build();
    }
    // Get Level from active Match
    @GET
    @ApiOperation(value = "Get level from active match", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Username does not exist or user is not in a Match")
    })
    @Path("/getLevelFromActiveMatch/{username}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getLevelFromActiveMatch(@PathParam("username")String username){
        try {
            this.m.getLevelFromMatch(username);
        } catch(UsernameDoesNotExistException | UsernameisNotInMatchException e){
            return Response.status(404).build();
        }
        return Response.status(200).build();
    }
    // Get TotalMatchPoints from active Match
    @GET
    @ApiOperation(value = "Get total match points from active match", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Username does not exist or user is not in a Match")
    })
    @Path("/getMatchPointsFromActiveMatch/{username}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response getMatchPointsFromActiveMatch(@PathParam("username")String username){
        try {
            this.m.getMatchTotalPoints(username);
        } catch(UsernameDoesNotExistException | UsernameisNotInMatchException e){
            return Response.status(404).build();
        }
        return Response.status(200).build();
    }
    // Next Level
    @PUT
    @ApiOperation(value = "Change level for a user", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Level changed successfully"),
            @ApiResponse(code = 404, message = "Username does not exist or user is not in a Match")
    })
    @Path("/nextLevel/{username}/{points}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response nextLevel(@PathParam("username")String username,@PathParam("points")int points){
        try {
            this.m.nextLevel(username,points);
        } catch(UsernameDoesNotExistException | UsernameisNotInMatchException e){
            return Response.status(404).build();
        }
        return Response.status(201).build();
    }
    // End active match
    @PUT
    @ApiOperation(value = "End a match for a user", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Match ended successfully"),
            @ApiResponse(code = 404, message = "Username does not exist or user is not in a Match")
    })
    @Path("/endMatch/{username}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response nextLevel(@PathParam("username")String username){
        try {
            this.m.endMatch(username);
        } catch(UsernameDoesNotExistException | UsernameisNotInMatchException e){
            return Response.status(404).build();
        }
        return Response.status(201).build();
    }
    //add object to store
    @POST
    @ApiOperation(value = "Add new object to store", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New object added successfully"),
            @ApiResponse(code = 404, message = "objectID already exists")
    })
    @Path("/addObjectToStore/")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addObjectToStore(StoreObject object){
        this.m.addObjectToStore(object.getObjectID(),object.getArticleName(),object.getPrice(),object.getDescription());
        return Response.status(201).build();
    }
}
