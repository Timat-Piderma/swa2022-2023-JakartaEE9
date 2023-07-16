package com.stdt.aulewebrest.framework.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.stdt.aulewebrest.template.model.Corso;
import com.stdt.aulewebrest.template.model.Evento;

public class CorsoDeserializer extends JsonDeserializer<Corso> {

    @Override
    public Corso deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        Corso c = new Corso();

        JsonNode node = jp.getCodec().readTree(jp);

        if (node.has("nome")) {
            c.setNome(node.get("nome").asText());
        }
        if (node.has("id")) {
            c.setId(node.get("nome").asInt());
        }

        if (node.has("eventi")) {
            JsonNode ne = node.get("eventi");
            List<Evento> eventi = new ArrayList<>();
            c.setEventi(eventi);
            for (int i = 0; i < ne.size(); ++i) {
                eventi.add(jp.getCodec().treeToValue(ne.get(i), Evento.class));
            }
        }

        return c;
    }
}
