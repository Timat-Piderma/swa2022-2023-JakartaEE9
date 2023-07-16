package com.stdt.aulewebrest.framework.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.stdt.aulewebrest.template.model.Aula;
import com.stdt.aulewebrest.template.model.Posizione;

public class PosizioneDeserializer extends JsonDeserializer<Posizione> {

    @Override
    public Posizione deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Posizione p = new Posizione();
        
        JsonNode node = jp.getCodec().readTree(jp);

        if(node.has("id")){
            p.setId(node.get("id").asInt());
        }
        if(node.has("nome")){
            p.setNome(node.get("nome").asText());
        }
        if(node.has("edificio")){
            p.setEdificio(node.get("edificio").asText());
        }
        if(node.has("piano")){
            p.setPiano(node.get("piuano").asText());
        }
     
        if (node.has("aule")) {
            JsonNode ne = node.get("aule");
            List<Aula> aule = new ArrayList<>();
            p.setAule(aule);
            for (int i = 0; i < ne.size(); ++i) {
                aule.add(jp.getCodec().treeToValue(ne.get(i), Aula.class));
            }           
        }

        return p;
    }
}
