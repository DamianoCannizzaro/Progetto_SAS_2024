package catering.businesslogic.shift;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.user.User;
import catering.businesslogic.user.UserManager;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class ShiftManager {
    private ShiftTable currentCShiftTable;
    private ShiftTable currentSShiftTable;
    private ArrayList<ShiftEventReceiver> eventReceivers;

    public ShiftManager() { eventReceivers = new ArrayList<>();}

    public ShiftTable createCookShiftTable(String type, EventInfo ev) throws UseCaseLogicException {
        if (type.equals("cucina")) {
            User user=  CatERing.getInstance().getUserManager().getCurrentUser();
            if(!user.isManager() || !ev.isAssigned(user)) throw new UseCaseLogicException();
               else {
                ShiftTable cst = new CookShiftTable(type, ev, false);
                this.setCurrentCShiftTable(cst);
                this.notifyCookShiftTableCreated(cst);

                return cst;
            }
        }else return null;
    }



    public void setCurrentCShiftTable(ShiftTable currentST) {
        this.currentCShiftTable = currentST;
    }
    public void setCurrentSShiftTable(ShiftTable currentST) {
        this.currentSShiftTable = currentST;
    }

/*
    public Shift addShift(ShiftTable st, String type, LocalTime preExt, LocalTime postExt, Date jobDate, Date deadline, LocalTime startTime, LocalTime endTime, boolean gruop, String gruopName){
        return currentShiftTable.addShift(st, type, preExt, postExt, jobDate, deadline, startTime, endTime, gruop, gruopName );
    }
*/
    //Notify methods

    private void notifyCookShiftTableCreated(ShiftTable cst) {
        for (ShiftEventReceiver er: eventReceivers) {
            er.updateCookShiftCreated(cst);
        }
    }
}
