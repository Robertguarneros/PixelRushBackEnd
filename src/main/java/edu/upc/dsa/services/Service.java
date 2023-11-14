package edu.upc.dsa.services;
import edu.upc.dsa.Manager;
import edu.upc.dsa.ManagerImpl;
import edu.upc.dsa.TracksManager;
import edu.upc.dsa.TracksManagerImpl;
import edu.upc.dsa.models.User;
import edu.upc.dsa.models.StoreObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Api(value = "/pixelRush", description = "Endpoint to Pixel Rush Service")
@Path("/pixelRush")

public class Service {
    private Manager manager;

    public Service(){
        this.manager = ManagerImpl.getInstance();
        if (manager.sizeUser()==0 && manager.sizeStore()==0){ //we would lack addObject() function
            manager.Register("robertoguarneros11","123","Roberto","Guarneros","roberto@gmail.com",22);
            manager.Register("titi", "456","Carles","Sanchez","titi@gmail.com",22);
            manager.Register("Luxu","789","Lucia","Ocaña","lucia@gmail.com",22);
            manager.Register("Xuculup","000","Ángel","Redondo","angel@gmail.com",21);
            //manager.addObject("123","Poción", 100, "Poción de salto", "imagen.png");
        }
    }

}
