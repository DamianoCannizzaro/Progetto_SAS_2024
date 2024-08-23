package catering.businesslogic.shift;

import catering.businesslogic.event.EventInfo;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.util.Date;

public interface ShiftTable {
    
    ObservableList<Shift> Shifts = null;
    
    ObservableMap<ShiftTable, Integer> recurringTables = null;

    public ShiftTable createShiftTable(String type, EventInfo ev);

    public boolean isEmpty();

    public ShiftTable checkTable(ShiftTable st);

    public Shift addShift(ShiftTable st, String type, double startTime, double endTime, Date deadline, double preExt
            , double postExt, boolean group, String groupName);
}
