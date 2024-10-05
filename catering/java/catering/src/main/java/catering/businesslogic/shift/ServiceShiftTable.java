package catering.businesslogic.shift;

import catering.businesslogic.event.EventInfo;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.time.LocalTime;
import java.util.Date;

public class ServiceShiftTable extends ShiftTable{

    public ServiceShiftTable(String typ, EventInfo ev, boolean ord) {
        if (typ.equals("servizio")){
            event = ev;
            order = ord;
            type = "servizio";
            this.owner = ev.getOrganizer();
        }
        else {
            System.out.println("Unable to create service shift table due to wrong type: " + type +"\n\n" + "Try again with matching type and table");
        }
    }

}
