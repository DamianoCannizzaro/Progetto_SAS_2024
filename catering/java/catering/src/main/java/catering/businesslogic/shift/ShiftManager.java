package catering.businesslogic.shift;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.user.User;
import catering.businesslogic.user.UserManager;
import catering.persistence.ShiftPersistence;
import catering.businesslogic.shift.ShiftManager;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class ShiftManager {
    private ShiftTable currCookSTable;
    private ShiftTable currServiceSTable;
    private ArrayList<ShiftEventReceiver> eventReceivers;

    public ShiftManager() { eventReceivers = new ArrayList<>();}



    public ShiftTable createCookShiftTable(String type, EventInfo ev) throws UseCaseLogicException {
        if (type.equals("c")) {
            User user = CatERing.getInstance().getUserManager().getCurrentUser();
            if (!user.isManager() || !ev.isAssigned(user)) throw new UseCaseLogicException();

            ShiftTable cst = new CookShiftTable(type, ev, false);
            this.setCurrentCShiftTable(cst);
            this.notifyCookShiftTableCreated(cst);

            return cst;
        }
        else throw new UseCaseLogicException();
    }

    public ShiftTable createServiceShiftTable(String type, EventInfo ev) throws UseCaseLogicException {
        User currentUser = CatERing.getInstance().getUserManager().getCurrentUser();

        if (!currentUser.isManager() || !ev.isAssigned(currentUser)) {
            //"L'utente è nu ricchiuuun."
            throw new UseCaseLogicException();
        }

        ShiftTable serviceShiftTable = new ServiceShiftTable(type, ev, false);

        this.setCurrentSShiftTable(serviceShiftTable);
        this.notifyCookShiftTableCreated(serviceShiftTable);

        return serviceShiftTable;
    }

    public void setCurrentCShiftTable(ShiftTable currentST) {
        this.currCookSTable = currentST;
    }
    public void setCurrentSShiftTable(ShiftTable currentST) {
        this.currServiceSTable = currentST;
    }

 /** Metodo per aggiungere un turno alla tabella corrispettiva.
  * Si effettua un controllo sul parametro type e viene aggiornata
  * la tabella di riferimento
  * */
public Shift addShiftToTable(ShiftTable st , Time startTime, Time endTime, Date jobDate, Date deadline, boolean group, String groupName) throws UseCaseLogicException {
    Shift newShift;
    if (st.type.equals("c")) {
            newShift = currCookSTable.addShift(st, startTime, endTime, jobDate, deadline, group, groupName );
    }else if(st.type.equals("s")) {
            newShift = currServiceSTable.addShift(st, startTime, endTime, jobDate, deadline, group, groupName );
    } else throw new UseCaseLogicException();

        notifyShiftCreated(newShift);
        return newShift;
}

    private void notifyShiftCreated(Shift newShift) {
    for (ShiftEventReceiver er : eventReceivers) {
        er.updateShiftCreated(newShift);
    }
    }

    //--------------------Notify methods-----------------------------

    private void notifyCookShiftTableCreated(ShiftTable cst) {
        for (ShiftEventReceiver er: eventReceivers) {
            er.updateCookShiftCreated(cst);
        }
    }

    public void addEventReceiver(ShiftEventReceiver rec) { this.eventReceivers.add(rec); }
    public void removeEventReceiver(ShiftEventReceiver rec) { this.eventReceivers.remove(rec); }
}
