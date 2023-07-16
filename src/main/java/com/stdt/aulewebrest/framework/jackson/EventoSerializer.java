package com.stdt.aulewebrest.framework.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import com.stdt.aulewebrest.template.model.Evento;

public class EventoSerializer extends JsonSerializer<Evento> {

    @Override
    public void serialize(Evento item, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        
        jgen.writeStartObject(); // {
        jgen.writeStringField("data", item.getData().toString());
        jgen.writeStringField("oraInizio", item.getOraInizio().toString());
        jgen.writeStringField("oraFine", item.getOraFine().toString());
        jgen.writeStringField("descrizione", item.getDescrizione());
        jgen.writeStringField("nome", item.getNome());
        jgen.writeObjectField("tipologia", item.getTipologia());
        jgen.writeObjectField("aula", item.getAula());
        jgen.writeObjectField("responsabile", item.getResponsabile());
        jgen.writeEndObject(); // }
    }
}
