package com.stdt.aulewebrest.framework.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.stdt.aulewebrest.template.model.Attrezzatura;
import com.stdt.aulewebrest.template.model.Aula;
import com.stdt.aulewebrest.template.model.Evento;

public class AulaDeserializer extends JsonDeserializer<Aula> {
    
    @Override
    public Aula deserialize (JsonParser jp, DeserializationContext ctxt)throws IOException, JsonProcessingException{
        Aula a = new Aula();
        
        JsonNode node = jp.getCodec().readTree(jp);

        if (node.has("nome")) {
            a.setNome(node.get("nome").asText());
        }

        if (node.has("capienza")) {
            a.setCapienza(node.get("capienza").asInt());
        }
        if (node.has("emailResponsabile")) {
            a.setEmailResponsabile(node.get("emailResponsabile").asText());
        }
        if (node.has("numeroPreseElettriche")) {
            a.setNumeroPreseElettriche(node.get("numeroPreseElettriche").asInt());
        }
        if (node.has("numeroPreseRete")) {
            a.setNumeroPreseRete(node.get("numeroPreseRete").asInt());
        }
        if (node.has("note")) {
            a.setNote(node.get("note").asText());
        }
        
        if (node.has("gruppoID")){
            a.setIdGruppo(node.get("gruppoID").asInt());
        }
        if (node.has("posizioneID")){
            a.setIdPosizione(node.get("posizioneID").asInt());
        }
        if (node.has("eventi")) {
            JsonNode ne = node.get("eventi");
            List<Evento> eventi = new ArrayList<>();
            a.setEventi(eventi);
            for (int i = 0; i < ne.size(); ++i) {
                eventi.add(jp.getCodec().treeToValue(ne.get(i), Evento.class));
            }           
        }
        return a;
    }
}
