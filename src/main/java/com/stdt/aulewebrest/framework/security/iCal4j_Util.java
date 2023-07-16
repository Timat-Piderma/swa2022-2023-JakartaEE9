package com.stdt.aulewebrest.framework.security;

import com.stdt.aulewebrest.template.model.Evento;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.List;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.component.VEvent;

public class iCal4j_Util {

    //Prende in input una lista di eventi e genera una stringa da restituire con file iCalendar
    public static String CalendarUtil(List<Evento> eventi) throws IOException {
        Calendar calendar = new Calendar();

        for (Evento e : eventi) {

            VEvent event = new VEvent(LocalDate.parse(e.getData().toString()), e.getNome() + e.getDescrizione());

            calendar.add(event);
        }

        CalendarOutputter outputter = new CalendarOutputter();
        StringWriter writer = new StringWriter();
        outputter.output(calendar, writer);
        String iCalendarContent = writer.toString();

        return iCalendarContent;
    }
}
