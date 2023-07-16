package com.stdt.aulewebrest.framework.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import com.stdt.aulewebrest.template.model.Responsabile;

public class ResponsabileSerializer extends JsonSerializer<Responsabile> {

    @Override
    public void serialize(Responsabile item, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {

        jgen.writeStartObject(); // {
        jgen.writeStringField("nome", item.getNome());
        jgen.writeStringField("emailResponsabile", item.getEmailResponsabile());
        jgen.writeNumberField("id", item.getId());
        jgen.writeObjectField("eventi", item.getEventi());
        jgen.writeEndObject(); // }
    }
}
