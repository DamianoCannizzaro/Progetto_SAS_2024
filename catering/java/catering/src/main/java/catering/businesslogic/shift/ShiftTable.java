package catering.businesslogic.shift;

import catering.businesslogic.event.EventInfo;
import catering.businesslogic.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.time.LocalTime;
import java.util.Date;

public abstract class ShiftTable {
    private EventInfo event;
    private String type;
    private boolean order;
    User owner;
    private ObservableList<Shift> Shifts = FXCollections.observableArrayList();
    private ObservableMap<ShiftTable,Integer> recurringTables = FXCollections.observableHashMap();


    public boolean isEmpty(){
        return Shifts.isEmpty();
    };

    public ShiftTable checkTable(ShiftTable st){
        return st;
    };

    public abstract Shift addShift(ShiftTable st, String type, LocalTime startTime, LocalTime endTime, Date jobDate, Date deadline, LocalTime preExt, LocalTime postExt, boolean group, String groupName);
}

