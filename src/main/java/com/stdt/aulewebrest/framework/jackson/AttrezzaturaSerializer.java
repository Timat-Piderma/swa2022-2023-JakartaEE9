package com.stdt.aulewebrest.framework.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import com.stdt.aulewebrest.template.model.Attrezzatura;

public class AttrezzaturaSerializer extends JsonSerializer<Attrezzatura> {

    @Override
    public void serialize(Attrezzatura item, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {

        jgen.writeStartObject(); // {
        jgen.writeNumberField("id", item.getId());
        jgen.writeStringField("nome", item.getNome()); // Lavagna, Computer Fisso,
        jgen.writeObjectField("aule", item.getAule());
        jgen.writeEndObject(); // }
    }
}
