package com.stdt.aulewebrest.framework.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import com.stdt.aulewebrest.template.model.Posizione;

public class PosizioneSerializer extends JsonSerializer<Posizione> {

    @Override
    public void serialize(Posizione item, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {

        jgen.writeStartObject(); // {
        jgen.writeNumberField("id", item.getId());
        jgen.writeStringField("nome", item.getNome());
        jgen.writeStringField("edificio", item.getEdificio());
        jgen.writeStringField("piano", item.getPiano());
        jgen.writeObjectField("aule", item.getAule());
        jgen.writeEndObject(); // }
    }
}
