package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CarDTO;
import dtos.DriverDTO;
import dtos.RaceDTO;
import facades.CarFacade;
import facades.RaceFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/car")
public class CarResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final CarFacade FACADE = CarFacade.getCarFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllCars() {
        List<CarDTO> carDTOS = FACADE.getAllCars();
        return Response.ok().entity(GSON.toJson(carDTOS)).build();
    }

    @GET
    @Path("/{carID}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getCarByBoatID(@PathParam("carID") int carID) {
        CarDTO carDTO = FACADE.getCarByID(carID);
        return Response.ok().entity(GSON.toJson(carDTO)).build();
    }


    @GET
    @Path("/{carID}/drivers/")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllDriversByCarID(@PathParam("carID") int carID) {
        List<DriverDTO> driverDTOS = FACADE.getDriversByCarID(carID);
        return Response.ok().entity(GSON.toJson(driverDTOS)).build();
    }
}
