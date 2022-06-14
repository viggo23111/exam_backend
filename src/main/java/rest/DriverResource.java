package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.DriverDTO;
import dtos.RaceDTO;
import facades.DriverFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/driver")
public class DriverResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final DriverFacade FACADE = DriverFacade.getDriverFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllDrivers() {
        List<DriverDTO> driverDTOS = FACADE.getAllDrivers();
        return Response.ok().entity(GSON.toJson(driverDTOS)).build();
    }

    @GET
    @Path("/{userID}/races")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRacesByDriverID(@PathParam("userID") int userID) {
        List<RaceDTO> raceDTOS = FACADE.getRacesByUserID(userID);
        return Response.ok().entity(GSON.toJson(raceDTOS)).build();
    }
}
