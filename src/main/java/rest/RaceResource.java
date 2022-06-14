package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CarDTO;
import dtos.RaceDTO;
import facades.CarFacade;
import facades.RaceFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
@Path("/race")
public class RaceResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final RaceFacade FACADE = RaceFacade.getRaceFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Path("/all")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllRaces() {
        List<RaceDTO> raceDTOS = FACADE.getAllRaces();
        return Response.ok().entity(GSON.toJson(raceDTOS)).build();
    }
    @POST
    @RolesAllowed("admin")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createRace(String content){
        RaceDTO pdto = GSON.fromJson(content, RaceDTO.class);
        RaceDTO newPdto = FACADE.createRace(pdto);
        return Response.ok().entity(GSON.toJson(newPdto)).build();
    }

}
