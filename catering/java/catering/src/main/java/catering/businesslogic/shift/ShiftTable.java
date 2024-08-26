package catering.businesslogic.shift;

import catering.businesslogic.event.EventInfo;
import catering.businesslogic.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.time.LocalTime;
import java.util.Date;

public abstract class ShiftTable {
    protected EventInfo event;
    protected String type;
    protected boolean order;
    User owner;
    protected ObservableList<Shift> Shifts = FXCollections.observableArrayList();
    protected final ObservableMap<ShiftTable,Date> recurringTable = FXCollections.observableHashMap();


    public boolean isEmpty(){
        return Shifts.isEmpty();
    };

    public ShiftTable checkTable(ShiftTable st){
        return st;
    };

    public Shift addShift(ShiftTable st, String type, LocalTime startTime, LocalTime endTime, Date jobDate, Date deadline, LocalTime preExt, LocalTime postExt, boolean group, String groupName){
        Shift newS = new Shift(startTime, endTime, jobDate, deadline, group, groupName);
        Shifts.add(newS);
        return newS;
    };
}

