
package ressources;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/options")
@Produces(MediaType.APPLICATION_JSON)
public class OptionRessources {

    private static final OptionBusiness optionBusiness = new OptionBusiness();


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response ajouterOption(Option option) {
        boolean ajoute = optionBusiness.addOption(option);

        if (ajoute) {
            return Response.ok(option).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }


    @GET
    public List<Option> getOptions(
            @QueryParam("domaine") String domaine) {

        if (domaine == null || domaine.trim().isEmpty()) {
            return optionBusiness.getListeOptions();
        }

        return optionBusiness.getOptionsByDomaine(domaine);
    }


    @DELETE
    @Path("/{code}")
    public Response supprimerOption(@PathParam("code") int code) {
        boolean supprime = optionBusiness.deleteOption(code);

        if (supprime) {
            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }


    @PUT
    @Path("/{code}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifierOption(
            @PathParam("code") int code,
            Option option) {

        boolean modifie = optionBusiness.updateOption(code, option);

        if (modifie) {
            return Response.ok(option).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }


    @GET
    @Path("/{code}")
    public Response getOptionByCode(@PathParam("code") int code) {
        Option option = optionBusiness.getOptionByCode(code);

        if (option != null) {
            return Response.ok(option).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
