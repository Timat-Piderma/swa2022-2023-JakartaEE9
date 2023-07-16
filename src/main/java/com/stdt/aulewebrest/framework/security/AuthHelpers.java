package com.stdt.aulewebrest.framework.security;

import com.stdt.aulewebrest.template.model.Amministratore;
import jakarta.ws.rs.core.UriInfo;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * Una classe di utilità di supporto all'autenticazione
 * qui tutto è finto, non usiamo JWT o altre tecnologie
 *
 */
public class AuthHelpers {

    private static AuthHelpers instance = null;

    public AuthHelpers() {

    }

    public boolean authenticateUser(String username, String password) {

        InitialContext ctx;
        try {
            ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/progettoDB");
            Connection conn = ds.getConnection();

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM amministratore WHERE username=?");

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Amministratore a = new Amministratore();
                a.setUsername(rs.getString("username"));
                a.setPassword(rs.getString("password"));

                if (SecurityHelpers.checkPasswordHashPBKDF2(password, a.getPassword())) {
                    ps.close();
                    return true;
                }
            }

        } catch (NamingException | SQLException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(AuthHelpers.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public String issueToken(UriInfo context, String username) {        
        String token = username + UUID.randomUUID().toString();
        return token;
    }

    public void revokeToken(String token) {
        /* invalidate il token */
    }

    public String validateToken(String token) {
        return "pippo"; //lo username andrebbe derivato dal token!
    }

    public static AuthHelpers getInstance() {
        if (instance == null) {
            instance = new AuthHelpers();
        }
        return instance;
    }

}
