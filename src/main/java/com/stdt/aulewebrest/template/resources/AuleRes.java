package com.stdt.aulewebrest.template.resources;

import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.opencsv.exceptions.CsvValidationException;
import com.stdt.aulewebrest.template.exceptions.RESTWebApplicationException;
import com.stdt.aulewebrest.template.model.Aula;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
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
            aula.setID(idaula);

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
            @FormParam("idposizione") String idposizione,
            @FormParam("proiettore") String proiettore,
            @FormParam("schermomotorizzato") String schermomotorizzato,
            @FormParam("schermomanuale") String schermomanuale,
            @FormParam("impaudio") String impaudio,
            @FormParam("pcfisso") String pcfisso,
            @FormParam("micfilo") String micfilo,
            @FormParam("micsenzafilo") String micsenzafilo,
            @FormParam("lavagnaluminosa") String lavagnaluminosa,
            @FormParam("visualpresenter") String visualpresenter,
            @FormParam("impelettrico") String impelettrico,
            @FormParam("allestimento") String allestimento,
            @FormParam("lavagna") String lavagna
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

            int aulaID = keys.getInt(1);

            URI uri = uriinfo.getBaseUriBuilder()
                    .path(getClass())
                    .path(getClass(), "getItem")
                    .build(keys.getInt(1));
            ps.close();

            String[] s = new String[12];
            s[0] = proiettore;
            s[1] = schermomotorizzato;
            s[2] = schermomanuale;
            s[3] = impaudio;
            s[4] = pcfisso;
            s[5] = micfilo;
            s[6] = micsenzafilo;
            s[7] = lavagnaluminosa;
            s[8] = visualpresenter;
            s[9] = impelettrico;
            s[10] = allestimento;
            s[11] = lavagna;

            try ( PreparedStatement psattrezzature = conn.prepareStatement("INSERT INTO fornito (aulaID, attrezzaturaID) VALUES(?,?)", Statement.RETURN_GENERATED_KEYS)) {
                for (String string : s) {
                    if (string != null) {

                        psattrezzature.setInt(1, aulaID);
                        psattrezzature.setString(2, string);
                        psattrezzature.executeUpdate();
                    }
                }
            }
            return Response.created(uri).build();
        } else {
            ps.close();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @Path("attachment")
    @GET
    @Produces("application/csv")
    public Response getAttachment() {

        InitialContext ctx;
        try {

            CSVWriter writer = (CSVWriter) new CSVWriterBuilder(new FileWriter("yourfile.csv"))
                    .withSeparator(',')
                    .build();

            // feed in your array (or convert your data to an array)
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/progettoDB");
            Connection conn = ds.getConnection();

            PreparedStatement ps = conn.prepareStatement("SELECT aula.nome as aulagruppo, gruppo.nome as nomegruppo, posizione.luogo, posizione.edificio, posizione.piano from aula join gruppo on aula.gruppoID = gruppo.ID join posizione on posizione.ID = aula.posizioneID");

            ResultSet rs = ps.executeQuery();

            String data = "Aula, Gruppo, Luogo, Edificio, Piano\n";

            while (rs.next()) {

                data = data + rs.getString("aulagruppo") + ","
                        + rs.getString("nomegruppo") + ","
                        + rs.getString("luogo") + ","
                        + rs.getString("edificio") + ","
                        + rs.getString("piano") + "\n";

            }
            ps.close();

            final byte[] attachment = data.getBytes();

            StreamingOutput out = new StreamingOutput() {
                @Override
                public void write(OutputStream output) throws IOException, WebApplicationException {
                    output.write(attachment);
                }
            };

            return Response
                    .ok(out)
                    .header("content-disposition", "attachment; filename=configurazioneaule.csv")
                    .build();

        } catch (NamingException | SQLException | IOException ex) {
            Logger.getLogger(AuleRes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.status(Response.Status.NOT_ACCEPTABLE).build();
    }

    @Path("import")
    @POST
    public Response postConfiguration(
            @FormParam("csv") File csv
    ) {

        try {
            Map<String, String> values = new CSVReaderHeaderAware(new FileReader(csv)).readMap();

            for (Map.Entry<String, String> entry : values.entrySet()) {
                System.out.println("Key = " + entry.getKey()
                        + ", Value = " + entry.getValue());
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(AuleRes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | CsvValidationException ex) {
            Logger.getLogger(AuleRes.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.noContent()
                .build();

    }
}
