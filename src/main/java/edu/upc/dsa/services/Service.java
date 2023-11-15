package edu.upc.dsa.services;
import edu.upc.dsa.exceptions.UsernameDoesNotExistException;
import edu.upc.dsa.manager.Manager;
import edu.upc.dsa.manager.ManagerImpl;
import edu.upc.dsa.models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Api(value = "/pixelRush", description = "Endpoint to Pixel Rush Service")
@Path("/pixelRush")

public class Service {
    private Manager m;

    public Service(){
        this.m = ManagerImpl.getInstance();
        m.register("robertoguarneros11","123","Roberto","Guarneros","roberto@gmail.com",22);
        m.register("titi", "456","Carles","Sanchez","titi@gmail.com",22);
        m.register("Luxu","789","Lucia","Ocaña","lucia@gmail.com",22);
        m.register("Xuculup","000","Ángel","Redondo","angel@gmail.com",21);
        m.addObjectToStore("123","Poción", 100, "Poción de salto");
        m.addObjectToStore("222","skin",50,"skin cosmetica");
    }

    //get store size
    @GET
    @ApiOperation(value = "Get current store size", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Integer.class)
    })
    @Path("/getStoreSize")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStoreSize() {
        int storeSize=this.m.storeSize();

        return Response.status(201).entity(storeSize).build()  ;
    }
//Get number of users
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
            if (m == null) return Response.status(404).build();
        }
        return Response.status(200).entity(u).build();
    }

}
