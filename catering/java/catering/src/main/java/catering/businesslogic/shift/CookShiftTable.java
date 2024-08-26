package catering.businesslogic.shift;

import catering.businesslogic.event.EventInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.time.LocalTime;
import java.util.Date;

public class CookShiftTable extends ShiftTable{
    private EventInfo event;
    private String type;
    private boolean order;

    private ObservableList<Shift> Shifts = FXCollections.observableArrayList();
    private ObservableMap<ShiftTable,Integer> recurringTable = FXCollections.observableHashMap();



    public CookShiftTable(String type, EventInfo ev, boolean order) {
        if (type.equals("cucina")){
            this.event = ev;
            this.order = order;
            this.type = type;
            this.owner = ev.getOrganizer();
        }
        else {
            System.out.println("Unable to create cook shift table due to wrong type: " + type +"\n\n" + "Try again with matching type and table");
        }
    }

    public ObservableList<Shift> getShifts() {
        return Shifts;
    }


    public ObservableMap<ShiftTable, Integer> getRecurringTables() {
        return recurringTable;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ShiftTable checkTable(ShiftTable st) {
        return null;
    }

    @Override
    public Shift addShift(ShiftTable st, String type, LocalTime startTime, LocalTime endTime,Date jobDate, Date deadline, LocalTime preExt, LocalTime postExt, boolean group, String groupName) {
        Shift newS = new Shift(startTime, endTime, )
        return newS;
    }
}
