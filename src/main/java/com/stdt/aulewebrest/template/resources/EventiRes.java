package com.stdt.aulewebrest.template.resources;

import com.stdt.aulewebrest.framework.security.Logged;
import com.stdt.aulewebrest.framework.security.iCal4j_Util;
import com.stdt.aulewebrest.template.exceptions.RESTWebApplicationException;
import com.stdt.aulewebrest.template.model.Evento;
import com.stdt.aulewebrest.template.model.Tipologia;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.StreamingOutput;
import jakarta.ws.rs.core.UriInfo;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@Path("eventi")
public class EventiRes {

    @Path("{idevento: [0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public EventoRes getItem(
            @PathParam("idevento") int idevento,
            @Context UriInfo uriinfo,
            //iniettiamo elementi di contesto utili per la verifica d'accesso
            @Context SecurityContext sec,
            @Context ContainerRequestContext req)
            throws RESTWebApplicationException, SQLException, ClassNotFoundException {

        Evento evento = new Evento();

        InitialContext ctx;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/progettoDB");
            Connection conn = ds.getConnection();

            PreparedStatement ps = conn.prepareStatement("Select * from Evento where ID = ?");
            ps.setInt(1, idevento);

            ResultSet rs = ps.executeQuery();

            rs.next();

            evento.setNome(rs.getString("nome"));
            evento.setData(LocalDate.parse(rs.getString("giorno")));
            evento.setOraInizio(LocalTime.parse(rs.getString("oraInizio")));
            evento.setOraFine(LocalTime.parse(rs.getString("oraFine")));
            evento.setDescrizione(rs.getString("descrizione"));
            evento.setTipologia(Tipologia.valueOf(rs.getString("tipologia")));
            evento.setID(idevento);

            ps.close();
        } catch (NamingException ex) {
            Logger.getLogger(EventiRes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new EventoRes(evento);
    }

    @GET
    @Path("{idaula: [0-9]+}/{giorno: [0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEventiSettimana(
            @PathParam("idaula") int idaula,
            @PathParam("giorno") String giorno,
            @Context UriInfo uriinfo,
            //iniettiamo elementi di contesto utili per la verifica d'accesso
            @Context SecurityContext sec,
            @Context ContainerRequestContext req)
            throws RESTWebApplicationException, SQLException, ClassNotFoundException {

        List<Evento> result = new ArrayList();

        InitialContext ctx;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/progettoDB");
            Connection conn = ds.getConnection();

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM evento WHERE WEEK(evento.giorno)=WEEK(?) AND aulaID=?");

            ps.setString(1, giorno);
            ps.setInt(2, idaula);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Evento evento = new Evento();

                evento.setData(LocalDate.parse(rs.getString("giorno")));
                evento.setOraInizio(LocalTime.parse(rs.getString("oraInizio")));
                evento.setOraFine(LocalTime.parse(rs.getString("oraFine")));
                evento.setNome(rs.getString("nome"));
                evento.setDescrizione(rs.getString("descrizione"));
                evento.setTipologia(Tipologia.valueOf(rs.getString("tipologia")));

                result.add(evento);

            }
            ps.close();

        } catch (NamingException ex) {
            Logger.getLogger(EventoRes.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.ok(result).build();
    }

    @GET
    @Path("{idgruppo: [0-9]+}/threehours")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEventiAttuali(
            @Context UriInfo uriinfo,
            @PathParam("idgruppo") int idgruppo,
            @Context SecurityContext sec,
            @Context ContainerRequestContext req)
            throws RESTWebApplicationException, SQLException, ClassNotFoundException {

        List<Evento> result = new ArrayList();

        InitialContext ctx;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/progettoDB");
            Connection conn = ds.getConnection();

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM evento JOIN tiene on evento.ID = tiene.eventoID JOIN aula on aula.ID = tiene.aulaID WHERE evento.oraInizio >= CURRENT_TIMESTAMP AND evento.oraInizio <= CURRENT_TIMESTAMP + INTERVAL 3 HOUR AND aula.gruppoID=? AND evento.giorno= curdate()");

            ps.setInt(1, idgruppo);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Evento evento = new Evento();

                evento.setData(LocalDate.parse(rs.getString("giorno")));
                evento.setOraInizio(LocalTime.parse(rs.getString("oraInizio")));
                evento.setOraFine(LocalTime.parse(rs.getString("oraFine")));
                evento.setNome(rs.getString("nome"));
                evento.setDescrizione(rs.getString("descrizione"));
                evento.setTipologia(Tipologia.valueOf(rs.getString("tipologia")));

                result.add(evento);

            }
            ps.close();

        } catch (NamingException ex) {
            Logger.getLogger(EventoRes.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.ok(result).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    //@Logged
    public Response addItem(
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

        PreparedStatement ps = conn.prepareStatement("INSERT INTO evento (giorno,oraInizio,oraFine,descrizione,nome,tipologia,aulaID,responsabileID,corsoID) VALUES(?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, giorno);
        ps.setString(2, oraInizio);
        ps.setString(3, oraFine);
        ps.setString(4, descrizione);
        ps.setString(5, nome);
        ps.setString(6, tipologia);
        ps.setString(7, idaula);
        ps.setString(8, idresponsabile);

        if (idcorso.isEmpty()) {
            ps.setString(9, null);
        } else {
            ps.setString(9, idcorso);
        }

        if (ps.executeUpdate() == 1) {

            ResultSet keys = ps.getGeneratedKeys();
            keys.next();

            URI uri = uriinfo.getBaseUriBuilder()
                    .build(keys.getInt(1));

            //Aggiunta nella tabella relazione tiene
            PreparedStatement pstiene = conn.prepareStatement("INSERT INTO tiene (aulaID, eventoID) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS);
            pstiene.setString(1, idaula);
            pstiene.setString(2, keys.getString(1));
            pstiene.executeUpdate();

            ps.close();
            return Response.created(uri).build();
        } else {
            ps.close();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @Path("{idaula: [0-9]+}/{giorno: [0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]}/attachment")
    @GET
    @Produces("application/ics")
    public Response getAttachment(
            @PathParam("idaula") int idaula,
            @PathParam("giorno") String giorno) {

        List<Evento> result = new ArrayList<>();

        InitialContext ctx;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/progettoDB");
            Connection conn = ds.getConnection();

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM evento WHERE WEEK(evento.giorno)=WEEK(?) AND aulaID=?");

            ps.setString(1, giorno);
            ps.setInt(2, idaula);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Evento evento = new Evento();

                evento.setData(LocalDate.parse(rs.getString("giorno")));
                evento.setOraInizio(LocalTime.parse(rs.getString("oraInizio")));
                evento.setOraFine(LocalTime.parse(rs.getString("oraFine")));
                evento.setNome(rs.getString("nome"));
                evento.setDescrizione(rs.getString("descrizione"));
                evento.setTipologia(Tipologia.valueOf(rs.getString("tipologia")));

                result.add(evento);

            }

            String s = iCal4j_Util.CalendarUtil(result);

            final byte[] attachment = s.getBytes();

            StreamingOutput out = new StreamingOutput() {
                @Override
                public void write(OutputStream output) throws IOException, WebApplicationException {
                    output.write(attachment);
                }
            };
            return Response
                    .ok(out)
                    .header("content-disposition", "attachment; filename=eventi.ics")
                    .build();
        } catch (NamingException | SQLException | IOException ex) {
            Logger.getLogger(EventiRes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }
}
