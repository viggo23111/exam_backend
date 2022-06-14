package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CarDTO;
import dtos.DriverDTO;
import dtos.RaceDTO;
import entities.Car;
import facades.CarFacade;
import facades.RaceFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
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
    public Response getCarByID(@PathParam("carID") int carID) {
        CarDTO carDTO = FACADE.getCarByID(carID);
        return Response.ok().entity(GSON.toJson(carDTO)).build();
    }


    @GET
    @Path("/{carID}/drivers/")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getDriversByCarID(@PathParam("carID") int carID) {
        List<DriverDTO> driverDTOS = FACADE.getDriversByCarID(carID);
        return Response.ok().entity(GSON.toJson(driverDTOS)).build();
    }

    @POST
    @RolesAllowed("admin")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createCar(String content){
        CarDTO pdto = GSON.fromJson(content, CarDTO.class);
        CarDTO newPdto = FACADE.createCar(pdto);
        return Response.ok().entity(GSON.toJson(newPdto)).build();
    }

    @PUT
    @RolesAllowed("admin")
    @Path("/remove/{carID}/{driverID}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response removeCarFromRace(@PathParam("carID") int carID, @PathParam("driverID") int driverID) {
        CarDTO updated = FACADE.removeDriverFromCar(carID,driverID);
        return Response.ok().entity(GSON.toJson(updated)).build();
    }

    @PUT
    @RolesAllowed("admin")
    @Path("/add/{carID}/{driverID}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response addCarToRace(@PathParam("carID") int carID, @PathParam("driverID") int driverID) {
        CarDTO updated = FACADE.addDriverToCar(carID,driverID);
        return Response.ok().entity(GSON.toJson(updated)).build();
    }

    @PUT
    @Path("/edit/")
    @RolesAllowed("admin")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateCar(String content){
        CarDTO carDTO = GSON.fromJson(content, CarDTO.class);
        CarDTO updatedCar = FACADE.updateCar(carDTO);
        return Response.ok().entity(GSON.toJson(updatedCar)).build();
    }

}
