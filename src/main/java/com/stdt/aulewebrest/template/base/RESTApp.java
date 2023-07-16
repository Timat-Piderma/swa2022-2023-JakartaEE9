package com.stdt.aulewebrest.template.base;

import com.fasterxml.jackson.jakarta.rs.json.JacksonJsonProvider;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import com.stdt.aulewebrest.framework.jackson.ObjectMapperContextResolver;
import com.stdt.aulewebrest.template.exceptions.AppExceptionMapper;
import com.stdt.aulewebrest.framework.security.CORSFilter;
import com.stdt.aulewebrest.template.resources.SimpleRes;
import com.stdt.aulewebrest.framework.security.AuthenticationRes;
import com.stdt.aulewebrest.framework.security.AuthLoggedFilter;
import com.stdt.aulewebrest.template.exceptions.JacksonExceptionMapper;
import com.stdt.aulewebrest.template.resources.AttrezzatureRes;
import com.stdt.aulewebrest.template.resources.AulaRes;
import com.stdt.aulewebrest.template.resources.AuleRes;
import com.stdt.aulewebrest.template.resources.EventiRes;
import com.stdt.aulewebrest.template.resources.EventoRes;

/**
 *
 * @author didattica
 */
@ApplicationPath("rest")
public class RESTApp extends Application {

    private final Set<Class<?>> classes;

    public RESTApp() {
        HashSet<Class<?>> c = new HashSet<Class<?>>();
        //aggiungiamo tutte le *root resurces* (cioè quelle
        //con l'annotazione Path) che vogliamo pubblicare
        c.add(AuthenticationRes.class);
        c.add(AttrezzatureRes.class);
        c.add(AuleRes.class);
        c.add(AulaRes.class);
        c.add(EventiRes.class);
        c.add(EventoRes.class);
        c.add(SimpleRes.class);

        //aggiungiamo il provider Jackson per poter
        //usare i suoi servizi di serializzazione e 
        //deserializzazione JSON
        c.add(JacksonJsonProvider.class);

        //necessario se vogliamo una (de)serializzazione custom di qualche classe    
        c.add(ObjectMapperContextResolver.class);

        //esempio di autenticazione
        c.add(AuthLoggedFilter.class);
        
        //aggiungiamo il filtro che gestisce gli header CORS
        c.add(CORSFilter.class);

        //esempi di exception mapper, che mappano in Response eccezioni non già derivanti da WebApplicationException
        c.add(AppExceptionMapper.class);
        c.add(JacksonExceptionMapper.class);

        classes = Collections.unmodifiableSet(c);
    }

    //l'override di questo metodo deve restituire il set
    //di classi che Jersey utilizzerà per pubblicare il
    //servizio. Tutte le altre, anche se annotate, verranno
    //IGNORATE
    @Override
    public Set<Class<?>> getClasses() {
        return classes;
    }
}
