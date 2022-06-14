package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CarDTO;
import dtos.DriverDTO;
import dtos.RaceDTO;
import entities.Driver;
import facades.DriverFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
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
    @Path("/{driverID}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getDriverByID(@PathParam("driverID") int driverID) {
        DriverDTO driverDTO = FACADE.getDriverByID(driverID);
        return Response.ok().entity(GSON.toJson(driverDTO)).build();
    }

    @GET
    @Path("/{userID}/races")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRacesByDriverID(@PathParam("userID") int userID) {
        List<RaceDTO> raceDTOS = FACADE.getRacesByUserID(userID);
        return Response.ok().entity(GSON.toJson(raceDTOS)).build();
    }

    @POST
    @RolesAllowed("admin")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createDriver(String content){
        DriverDTO pdto = GSON.fromJson(content, DriverDTO.class);
        DriverDTO newPdto = FACADE.createDriver(pdto);
        return Response.ok().entity(GSON.toJson(newPdto)).build();
    }

    @PUT
    @Path("/edit/")
    @RolesAllowed("admin")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateDriver(String content){
        DriverDTO driverDTO = GSON.fromJson(content, DriverDTO.class);
        DriverDTO updatedDriver = FACADE.updateDriver(driverDTO);
        return Response.ok().entity(GSON.toJson(updatedDriver)).build();
    }
}
