package edu.upc.dsa.services;
import edu.upc.dsa.exceptions.*;
import edu.upc.dsa.manager.Manager;
import edu.upc.dsa.manager.ManagerImpl;
import edu.upc.dsa.models.OwnedObjects;
import edu.upc.dsa.models.bodies.LoginCredentials;
import edu.upc.dsa.models.Matches;
import edu.upc.dsa.models.StoreObject;
import edu.upc.dsa.models.Users;
import edu.upc.dsa.models.bodies.RegisterCredentials;
import io.swagger.annotations.*;


import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Api(value = "/pixelRush", description = "Endpoint to Pixel Rush Service")
@Path("/pixelRush")

public class Service {
    private Manager m;

    public Service() throws UsernameDoesNotExistException, UsernameIsInMatchException, UsernameisNotInMatchException, UsernameDoesExist, SQLException {
        this.m = ManagerImpl.getInstance();
    }
    //These have been corrected to use with DB:
    //Get number of users
    @GET
    @ApiOperation(value = "Get number of users", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")
    })
    @Path("/getNumberOfUsers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getNumberOfUsers() {
        int numOfUsers = this.m.numberOfUsers();
        JsonObject jsonResponse = Json.createObjectBuilder().add("number of users", numOfUsers).build();
        return Response.status(200).entity(jsonResponse.toString()).build();
    }
    //get user
    @GET
    @ApiOperation(value = "get a user", notes = "given a username")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Users.class),
            @ApiResponse(code = 404, message = "Username does not exist")
    })
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("username") String username) {
        Users u = null;
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
            @ApiResponse(code = 200, message = "OK", response = Users.class, responseContainer="List"),
    })
    @Path("/getAllUsers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        List<Users> users = this.m.getAllUsers();
        GenericEntity<List<Users>> entity = new GenericEntity<List<Users>>(users) {};
        return Response.status(200).entity(entity).build();
    }
    //register User
    @POST
    @ApiOperation(value = "Register a new user", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User registered successfully")
    })
    @Path("/registerNewUser")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response registerNewUser(RegisterCredentials user){
        try {
            this.m.register(user.getUsername(), user.getPassword(),user.getMail(), user.getName(), user.getSurname(), user.getBirthDate());
            return Response.status(201).build();
        }catch (UsernameDoesExist e){
            return  Response.status(404).build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (UsernameDoesNotExistException e) {
            throw new RuntimeException(e);
        }
    }
    //login
    @POST
    @ApiOperation(value = "Login", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User login successful")
    })
    @Path("/login")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response login(LoginCredentials loginCredentials){
        try {
            this.m.login(loginCredentials.getUsername(), loginCredentials.getPassword());
            return Response.status(201).build();
        } catch (UsernameDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (IncorrectPassword e) {
            throw new RuntimeException(e);
        }
    }
    //get store size
    @GET
    @ApiOperation(value = "Get current store size", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK")
    })
    @Path("/getStoreSize")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStoreSize() {
        int storeSize=this.m.storeSize();
        JsonObject jsonResponse = Json.createObjectBuilder().add("Number of Items on the store", storeSize).build();//we create a new json object to be able to send the integer
        return Response.status(200).entity(jsonResponse.toString()).build();
    }
    //add object to store
    @POST
    @ApiOperation(value = "Add new object to store", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New object added successfully"),
            @ApiResponse(code = 404, message = "objectID already exists")
    })
    @Path("/addObjectToStore")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addObjectToStore(StoreObject object) throws ObjectIDDoesNotExist {
        this.m.addObjectToStore(object.getObjectID(),object.getArticleName(),object.getPrice(),object.getDescription());
        return Response.status(201).build();
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
    //Get object
    @GET
    @ApiOperation(value = "get Object information", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = StoreObject.class),
            @ApiResponse(code = 404, message = "ObjectID does not exist")
    })
    @Path("/getObjectInformation/{objectID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getObjectInformation(@PathParam("objectID") String objectID) throws ObjectIDDoesNotExist {
        try{
            StoreObject object = this.m.getObject(objectID);
            return Response.status(200).entity(object).build();
        } catch(ObjectIDDoesNotExist e) {
            return Response.status(404).build();}
    }
    // Add item to user
    @PUT
    @ApiOperation(value = "Add item to user", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Item added successfully"),
            @ApiResponse(code = 404, message = "Username does not exist or objectID does not exist"),
            @ApiResponse(code = 550, message = "Already Owned"),
            @ApiResponse(code = 551, message = "Not enough Points")
    })
    @Path("/addItemToUser/{username}/{objectID}")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addItemToUser(@PathParam("username")String username,@PathParam("objectID")String objectID){
        try {
            this.m.addItemToUser(username,objectID);
        } catch(UsernameDoesNotExistException | ObjectIDDoesNotExist e){
            return Response.status(404).build();
        }catch (AlreadyOwned e){
            return Response.status(550).build();
        }catch (NotEnoughPoints e){
            return Response.status(551).build();
        }
        return Response.status(201).build();
    }
    //Get List of Owned Objects
    @GET
    @ApiOperation(value = "get list of owned objects", notes = "returns a list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = OwnedObjects.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Username does not exist")
    })
    @Path("/getOwnedObjects/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOwnedObjects(@PathParam("username") String username) throws UsernameDoesNotExistException {
        try{
            List<OwnedObjects> ownedObjects = this.m.getOwnedObjects(username);
            GenericEntity<List<OwnedObjects>> entity = new GenericEntity<List<OwnedObjects>>(ownedObjects){};
            return Response.status(200).entity(entity).build();
        } catch(UsernameDoesNotExistException e) {
            return Response.status(404).build();}
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
            int level = this.m.getLevelFromMatch(username);
            JsonObject jsonResponse = Json.createObjectBuilder().add("username", username).add("level", level).build();
            return Response.status(200).entity(jsonResponse.toString()).build();
        } catch(UsernameDoesNotExistException | UsernameisNotInMatchException e){
            return Response.status(404).build();
        }
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
            int matchPoints = this.m.getMatchTotalPoints(username);
            JsonObject jsonResponse = Json.createObjectBuilder().add("username", username).add("matchPoints", matchPoints).build();
            return Response.status(200).entity(jsonResponse.toString()).build();
        } catch(UsernameDoesNotExistException | UsernameisNotInMatchException e){
            return Response.status(404).build();
        }
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
    public Response endMatch(@PathParam("username")String username){
        try {
            this.m.endMatch(username);
        } catch(UsernameDoesNotExistException | UsernameisNotInMatchException e){
            return Response.status(404).build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Response.status(201).build();
    }
    //Get played matches by username
   @GET
    @ApiOperation(value = "get played matches from user", notes = "return list")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Matches.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Username does not exist")
    })
    @Path("/getPlayedMatchesFromUser/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPlayedMatchesFromUser(@PathParam("username") String username) throws UsernameDoesNotExistException {
        List<Matches> playedMatches = this.m.getPlayedMatches(username);
        GenericEntity<List<Matches>> entity = new GenericEntity<List<Matches>>(playedMatches) {};
        if(!playedMatches.isEmpty()||this.m.getUser(username)!=null) return Response.status(200).entity(entity).build();
        else return Response.status(404).build();
    }
    //Get match
    @GET
    @ApiOperation(value = "get last match", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = StoreObject.class, responseContainer="List"),
            @ApiResponse(code = 404, message = "Username does not exist")
    })
    @Path("/getLastMatch/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLastMatch(@PathParam("username")String username) {
        Matches m = this.m.getLastMatch(username);
        if(m!=null) return Response.status(200).entity(m).build();
        else return Response.status(404).build();
    }
}
