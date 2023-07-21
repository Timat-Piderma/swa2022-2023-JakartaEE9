package com.stdt.aulewebrest.template.resources;

import com.stdt.aulewebrest.template.exceptions.RESTWebApplicationException;
import com.stdt.aulewebrest.template.model.Aula;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class AulaRes {

    private final Aula aula;

    AulaRes(Aula aula) {
        this.aula = aula;
    }

    @GET
    @Produces("application/json")
    public Response getItem() {

        try {
            return Response.ok(aula).build();
        } catch (Exception e) {
            throw new RESTWebApplicationException(e);
        }

    }

    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response UpdateItem(
            @Context ContainerRequestContext req,
            @Context UriInfo uriinfo,
            @FormParam("idGruppoAula") int idgruppo,
            @FormParam("idAulaGruppo") int idaula
    ) throws SQLException, NamingException {
        
        InitialContext ctx;
        ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/progettoDB");
        Connection conn = ds.getConnection();

        PreparedStatement ps = conn.prepareStatement("UPDATE aula SET  gruppoId=?, version=? WHERE ID=? and version=?");
        ps.setInt(1, idgruppo);

        PreparedStatement psversion = conn.prepareStatement("select version from aula where ID=?");
        psversion.setInt(1, aula.getID());
        ResultSet rsversion = psversion.executeQuery();
        rsversion.next();

        long current_version = rsversion.getInt("version");
        long next_version = current_version + 1;

        ps.setLong(2, next_version);
        ps.setInt(3, aula.getID());
        ps.setLong(4, current_version);

        if (ps.executeUpdate() == 1) {
            ps.close();
            return Response.noContent().build();
        } else {
            ps.close();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

}
