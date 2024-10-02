package catering.businesslogic.shift;

import catering.businesslogic.event.EventInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.time.LocalTime;
import java.util.Date;

public class CookShiftTable extends ShiftTable{

    //Costruttore per tabella turni di cucina
    public CookShiftTable(String typ, EventInfo ev, boolean ord) {
        if (typ.equals("cucina")){
            event = ev;
            order = ord;
            type = "cucina";
            this.owner = ev.getOrganizer();
        }
        else { //TODO gestire errore con UseCaseLogicException
            System.out.println("Unable to create cook shift table due to wrong type: " + type +"\n\n" + "Try again with matching type and table");
        }
    }

    public ObservableList<Shift> getShifts() { return Shifts; }
    public ObservableMap<ShiftTable, Date> getRecurringTable() { return recurringTable; }


}
