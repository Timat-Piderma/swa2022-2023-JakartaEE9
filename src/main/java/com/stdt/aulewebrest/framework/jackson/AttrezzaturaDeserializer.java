/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

/**
 *
 * @author mttpe
 */
public class AttrezzaturaDeserializer extends JsonDeserializer<Attrezzatura> {
    
    @Override
    public Attrezzatura deserialize (JsonParser jp, DeserializationContext ctxt)throws IOException, JsonProcessingException{
        Attrezzatura a = new Attrezzatura();
        
        JsonNode node = jp.getCodec().readTree(jp);

        if (node.has("id")) {
            a.setId(node.get("id").asInt());
        }
        if (node.has("nome")){
            a.setNome(node.get("nome").asText());
        }
        
        if (node.has("aule")) {
            JsonNode ne = node.get("aule");
            List<Aula> aule = new ArrayList<>();
            a.setAule(aule);
            for (int i = 0; i < ne.size(); ++i) {
                aule.add(jp.getCodec().treeToValue(ne.get(i), Aula.class));
            }           
        }
        return a;
    }
    
}
