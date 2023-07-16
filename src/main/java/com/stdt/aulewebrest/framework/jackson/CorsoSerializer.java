package com.stdt.aulewebrest.framework.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import com.stdt.aulewebrest.template.model.Corso;

public class CorsoSerializer extends JsonSerializer<Corso> {

    @Override
    public void serialize(Corso item, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {

        jgen.writeStartObject(); // {
        jgen.writeStringField("nome", item.getNome()); // Sviluppo Web Avanzato, Teoria Della Calcolabilità e Complessità
        jgen.writeNumberField("id", item.getId());
        jgen.writeObjectField("evetni", item.getEventi());
        jgen.writeEndObject(); // }
    }
}
