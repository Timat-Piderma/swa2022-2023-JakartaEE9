package com.stdt.aulewebrest.template.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import com.stdt.aulewebrest.template.exceptions.RESTWebApplicationException;

@Path("attrezzature")
public class AttrezzatureRes {
    
    @GET
    @Path("{idaula: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAttrezzature(
            @PathParam("idaula") int idaula,
            @Context UriInfo uriinfo,
            //iniettiamo elementi di contesto utili per la verifica d'accesso
            @Context SecurityContext sec,
            @Context ContainerRequestContext req)
            throws RESTWebApplicationException, SQLException, ClassNotFoundException {
        
        List<String> l = new ArrayList();
        
        InitialContext ctx;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/progettoDB");
            Connection conn = ds.getConnection();
            
            PreparedStatement ps = conn.prepareStatement("Select Attrezzatura.nome as Attrezzature from Fornito join Attrezzatura on Attrezzatura.ID = attrezzaturaID where aulaID = ?");
            ps.setInt(1, idaula);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                
                l.add(rs.getString("attrezzature"));
                
            }
            ps.close();
            
        } catch (NamingException ex) {
            Logger.getLogger(AttrezzatureRes.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return Response.ok(l).build();
    }
    
}
