package catering.persistence;

import catering.businesslogic.shift.ShiftEventReceiver;
import catering.businesslogic.shift.ShiftTable;

public class ShiftPersistence implements ShiftEventReceiver {
    @Override
    public void updateCookShiftCreated(ShiftTable st) {
        ShiftTable.saveNewShiftTable(st);
    }
}
