package catering.businesslogic.shift;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class ShiftManager {
    private ShiftTable currentShiftTable;
    private ArrayList<ShiftEventReceiver> eventReceivers;

    public ShiftTable createCookShiftTable(){
        return st;
    }

    public void setCurrentShiftTable(ShiftTable currentST) {
        this.currentShiftTable = currentST;
    }

    public Shift addShift(ShiftTable st, String type, LocalTime preExt, LocalTime postExt, Date jobDate, Date deadline, LocalTime startTime, LocalTime endTime, boolean gruop, String gruopName){
        return currentShiftTable.addShift(st, type, preExt, postExt, jobDate, deadline, startTime, endTime, gruop, gruopName );
    }


}
