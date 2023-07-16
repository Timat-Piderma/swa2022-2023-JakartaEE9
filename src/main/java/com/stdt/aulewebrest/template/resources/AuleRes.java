package com.stdt.aulewebrest.template.resources;

import com.stdt.aulewebrest.framework.security.Logged;
import com.stdt.aulewebrest.template.exceptions.RESTWebApplicationException;
import com.stdt.aulewebrest.template.model.Aula;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.UriInfo;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


@Path("aule")
public class AuleRes {

    
    @Path("{idaula: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public AulaRes getItem(
            @PathParam("idaula") int idaula,
            @Context UriInfo uriinfo,
            @Context SecurityContext sec,
            @Context ContainerRequestContext req)
            throws RESTWebApplicationException, SQLException, ClassNotFoundException {

        Aula aula = new Aula();

        InitialContext ctx;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/progettoDB");
            Connection conn = ds.getConnection();

            PreparedStatement ps = conn.prepareStatement("Select * from Aula where ID = ?");
            ps.setInt(1, idaula);

            ResultSet rs = ps.executeQuery();

            rs.next();

            aula.setNome(rs.getString("nome"));
            aula.setCapienza(rs.getInt("capienza"));
            aula.setEmailResponsabile(rs.getString("emailResponsabile"));
            aula.setNumeroPreseRete(rs.getInt("numeroPreseRete"));
            aula.setNote(rs.getString("note"));
            aula.setNumeroPreseElettriche(rs.getInt("numeroPreseElettriche"));

            ps.close();
        } catch (NamingException ex) {
            Logger.getLogger(AuleRes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new AulaRes(aula);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addItem(
            @Context ContainerRequestContext req,
            @Context UriInfo uriinfo,
            @FormParam("nome") String nome,
            @FormParam("capienza") String capienza,
            @FormParam("emailresponsabile") String emailresponsabile,
            @FormParam("note") String note,
            @FormParam("numeropreseelettriche") String numeropreseelettriche,
            @FormParam("numeropreserete") String numeropreserete,
            @FormParam("idgruppo") String idgruppo,
            @FormParam("idposizione") String idposizione
    ) throws SQLException, NamingException {

        InitialContext ctx;
        ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/progettoDB");
        Connection conn = ds.getConnection();

        PreparedStatement ps = conn.prepareStatement("INSERT INTO aula (nome,capienza,emailResponsabile,note,numeroPreseElettriche,numeroPreseRete,gruppoID,posizioneID) VALUES(?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, nome);
        ps.setString(2, capienza);
        ps.setString(3, emailresponsabile);
        ps.setString(4, note);
        ps.setString(5, numeropreseelettriche);
        ps.setString(6, numeropreserete);
        ps.setString(7, idgruppo);
        ps.setString(8, idposizione);

        if (ps.executeUpdate() == 1) {

            ResultSet keys = ps.getGeneratedKeys();
            keys.next();

            URI uri = uriinfo.getBaseUriBuilder()
                    .path(getClass())
                    .path(getClass(), "getItem")
                    .build(keys.getInt(1));
            ps.close();
            return Response.created(uri).build();
        } else {
            ps.close();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }
}
