/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.User;
import errorhandling.API_Exception;
import errorhandling.GenericExceptionMapper;
import facades.UserFacade;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

/**
 * REST Web Service
 *
 * @author christianrosenbaek
 */
@Path("user")
public class UserResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    public static final UserFacade USER_FACADE = UserFacade.getUserFacade(EMF);
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UserResource
     */
    public UserResource() {
    }

    @Path("create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({MediaType.APPLICATION_JSON})
    public Response createUser(String jsonString) throws API_Exception {
        String username;
        String password;
        ArrayList<String> roles = new ArrayList<>();
        roles.add("admin");
        
        GsonBuilder gsonb = new GsonBuilder();
        Gson gson = gsonb.create();
        
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            username = json.get("username").getAsString();
            password = json.get("password").getAsString();
            System.out.println("Roles format "+ json.get("roles") );
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }
        
        try {
            User user = USER_FACADE.createUser(username, password, roles);
            JsonObject responseJson = new JsonObject();
            responseJson.addProperty("username", username);
            return Response.ok(new Gson().toJson(responseJson)).build();
        } catch (Exception ex) {
            Logger.getLogger(GenericExceptionMapper.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new API_Exception("An error occured. Sorry for the inconvinience");
    }
}
