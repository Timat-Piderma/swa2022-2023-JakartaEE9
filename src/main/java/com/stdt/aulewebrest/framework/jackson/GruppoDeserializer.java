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
import com.stdt.aulewebrest.template.model.Gruppo;

public class GruppoDeserializer extends JsonDeserializer<Gruppo>{
     @Override
    public Gruppo deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Gruppo g = new Gruppo();
        
        JsonNode node = jp.getCodec().readTree(jp);

        if (node.has("id")) {
            g.setId(node.get("id").asInt());
        }
        if (node.has("nome")) {
            g.setNome(node.get("nome").asText());
        }
        if (node.has("descrizione")) {
            g.setDescrizione(node.get("descrizione").asText());
        }

        if (node.has("aule")) {
            JsonNode ne = node.get("aule");
            List<Aula> aule = new ArrayList<>();
            g.setAule(aule);
            for (int i = 0; i < ne.size(); ++i) {
                aule.add(jp.getCodec().treeToValue(ne.get(i), Aula.class));
            }           
        }
        return g;
    }
}
