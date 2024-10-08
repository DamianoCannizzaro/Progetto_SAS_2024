package catering.businesslogic.shift;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.user.User;
import catering.businesslogic.user.UserManager;
import catering.persistence.ShiftPersistence;

import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

public class ShiftManager {
    private ShiftTable currCookSTable;
    private ShiftTable currServiceSTable;
    private ArrayList<ShiftEventReceiver> eventReceivers;

    public ShiftManager() { eventReceivers = new ArrayList<>();}

    /**
     * CIAO
     * FABIO
     * BEN
     * TROVATO
     * TI
     * VEDO
     * LAVORARE
     * ...
     * <3
     * */


    public ShiftTable createCookShiftTable(String type, EventInfo ev) throws UseCaseLogicException {
        if (type.equals("c")) {
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
        this.currCookSTable = currentST;
    }
    public void setCurrentSShiftTable(ShiftTable currentST) {
        this.currServiceSTable = currentST;
    }

 /** Metodo per aggiungere un turno alla tabella corrispettiva.
  * Si effettua un controllo sul parametro type e viene aggiornata
  * la tabella di riferimento
  * */
public Shift addShift(ShiftTable st , String type, LocalTime startTime, LocalTime endTime, Date jobDate, Date deadline, LocalTime preExt, LocalTime postExt, boolean group, String groupName) throws UseCaseLogicException {
        if (type.equals("cucina")) {
            return currCookSTable.addShift(st, startTime, endTime, jobDate, deadline, preExt, postExt, group, groupName );
        }else if(type.equals("servizio")) {
            return currServiceSTable.addShift(st, startTime, endTime, jobDate, deadline, preExt, postExt, group, groupName );
        }
        else throw new UseCaseLogicException();
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
