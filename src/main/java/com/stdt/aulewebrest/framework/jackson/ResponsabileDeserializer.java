package com.stdt.aulewebrest.framework.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.stdt.aulewebrest.template.model.Evento;
import com.stdt.aulewebrest.template.model.Responsabile;

public class ResponsabileDeserializer extends JsonDeserializer<Responsabile> {
    
    @Override
    public Responsabile deserialize (JsonParser jp, DeserializationContext ctxt)throws IOException, JsonProcessingException{
        Responsabile r = new Responsabile();
        
        JsonNode node = jp.getCodec().readTree(jp);

        if(node.has("nome")){
            r.setNome(node.get("nome").asText());
        }
        if(node.has("emailResponsabile")){
            r.setEmailResponsabile(node.get("emailResponsabile").asText());
        }
        if(node.has("id")){
            r.setId(node.get("id").asInt());
        }
             
        if (node.has("eventi")) {
            JsonNode ne = node.get("eventi");
            List<Evento> eventi = new ArrayList<>();
            r.setEventi(eventi);
            for (int i = 0; i < ne.size(); ++i) {
                eventi.add(jp.getCodec().treeToValue(ne.get(i), Evento.class));
            }           
        }
        return r;
    }
}
