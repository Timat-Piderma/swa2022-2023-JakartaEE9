package com.stdt.aulewebrest.template.resources;

import com.stdt.aulewebrest.template.exceptions.RESTWebApplicationException;
import com.stdt.aulewebrest.template.model.Evento;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
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

public class EventoRes {

    private final Evento evento;

    EventoRes(Evento evento) {
        this.evento = evento;
    }

    @GET
    @Produces("application/json")
    public Response getItem() {

        try {
            return Response.ok(evento).build();
        } catch (Exception e) {
            throw new RESTWebApplicationException(e);
        }

    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response UpdateItem(
            @Context ContainerRequestContext req,
            @Context UriInfo uriinfo,
            @FormParam("giorno") String giorno,
            @FormParam("oraInizio") String oraInizio,
            @FormParam("oraFine") String oraFine,
            @FormParam("descrizione") String descrizione,
            @FormParam("nome") String nome,
            @FormParam("tipologia") String tipologia,
            @FormParam("idaula") String idaula,
            @FormParam("idresponsabile") String idresponsabile,
            @FormParam("idcorso") String idcorso
    ) throws SQLException, NamingException {

        InitialContext ctx;
        ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/progettoDB");
        Connection conn = ds.getConnection();

        PreparedStatement ps = conn.prepareStatement("UPDATE evento SET giorno=?,oraInizio=?,oraFine=?,descrizione=?, nome=?, tipologia=?, aulaID=?, responsabileID=?, corsoID=?, version=? WHERE ID=? and version=?");
        ps.setString(1, giorno);
        ps.setString(2, oraInizio);
        ps.setString(3, oraFine);
        ps.setString(4, descrizione);
        ps.setString(5, nome);
        ps.setString(6, tipologia);
        ps.setString(7, idaula);
        ps.setString(8, idresponsabile);
        ps.setString(9, idcorso);

        PreparedStatement psversion = conn.prepareStatement("select version from evento where ID=?");
        psversion.setInt(1, evento.getID());
        ResultSet rsversion = psversion.executeQuery();
        rsversion.next();

        long current_version = rsversion.getInt("version");
        long next_version = current_version + 1;

        ps.setLong(10, next_version);
        ps.setInt(11, evento.getID());
        ps.setLong(12, current_version);

        if (ps.executeUpdate() == 1) {
            ps.close();
            return Response.noContent().build();
        } else {
            ps.close();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

}
