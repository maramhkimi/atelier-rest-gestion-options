
package ressources;

import entities.Etudiant;
import entities.EtudiantList;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/etudiants")
@Produces(MediaType.APPLICATION_JSON)
public class EtudiantRessources {

    private static final EtudiantBusiness etudiantBusiness =
            new EtudiantBusiness();
    private static final OptionBusiness optionBusiness =
            new OptionBusiness();

    // B.1 - Créer un étudiant
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response ajouterEtudiant(Etudiant etudiant) {

        if (etudiant == null || etudiant.getOption() == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        boolean ajoute = etudiantBusiness.addEtudiant(etudiant);

        if (ajoute) {
            return Response.ok(etudiant).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // B.2 - Récupérer tous les étudiants
    @GET
    public List<Etudiant> getAllEtudiants() {
        return etudiantBusiness.getAllEtudiants();
    }

    // B.3 - Récupérer un étudiant par identifiant
    @GET
    @Path("/{identifiant}")
    public Response getEtudiantByIdentifiant(
            @PathParam("identifiant") String identifiant) {

        Etudiant etudiant =
                etudiantBusiness.getEtudiantByIdentifiant(identifiant);

        if (etudiant != null) {
            return Response.ok(etudiant).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // B.4 - Supprimer un étudiant
    @DELETE
    @Path("/{identifiant}")
    public Response supprimerEtudiant(
            @PathParam("identifiant") String identifiant) {

        boolean supprime =
                etudiantBusiness.deleteEtudiant(identifiant);

        if (supprime) {
            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // B.5 - Modifier un étudiant
    @PUT
    @Path("/{identifiant}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modifierEtudiant(
            @PathParam("identifiant") String identifiant,
            Etudiant etudiant) {

        boolean modifie =
                etudiantBusiness.updateEtudiant(identifiant, etudiant);

        if (modifie) {
            return Response.ok(etudiant).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // B.6 - Récupérer les étudiants d'une option au format XML
    @GET
    @Path("/option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantsByOption(
            @QueryParam("codeOption") int codeOption) {

        if (optionBusiness.getOptionByCode(codeOption) == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<Etudiant> etudiants =
                etudiantBusiness.getEtudiantsByOption(
                        optionBusiness.getOptionByCode(codeOption));

        return Response.ok(new EtudiantList(etudiants)).build();
    }
}
