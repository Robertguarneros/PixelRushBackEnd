package edu.upc.dsa.services;
import edu.upc.dsa.manager.Manager;
import edu.upc.dsa.manager.ManagerImpl;
import io.swagger.annotations.Api;

import javax.ws.rs.*;

@Api(value = "/pixelRush", description = "Endpoint to Pixel Rush Service")
@Path("/pixelRush")

public class Service {
    private Manager manager;

    public Service(){
        this.manager = ManagerImpl.getInstance();
        if (manager.numberOfUsers()==0 && manager.storeSize()==0){ //we would lack addObject() function
            manager.register("robertoguarneros11","123","Roberto","Guarneros","roberto@gmail.com",22);
            manager.register("titi", "456","Carles","Sanchez","titi@gmail.com",22);
            manager.register("Luxu","789","Lucia","Ocaña","lucia@gmail.com",22);
            manager.register("Xuculup","000","Ángel","Redondo","angel@gmail.com",21);
            //manager.addObject("123","Poción", 100, "Poción de salto", "imagen.png");
        }
    }

}
