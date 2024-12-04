package catering.persistence;

import catering.businesslogic.shift.ShiftEventReceiver;
import catering.businesslogic.shift.ShiftTable;
import catering.businesslogic.shift.Shift;
public class ShiftPersistence implements ShiftEventReceiver {
    @Override
    public void updateCShiftTableCreated(ShiftTable st) {
        ShiftTable.saveNewShiftTable(st);
    }

    @Override
    public void updateShiftCreated(Shift newShift) {
        Shift.saveNewShift(newShift);
    }


}
