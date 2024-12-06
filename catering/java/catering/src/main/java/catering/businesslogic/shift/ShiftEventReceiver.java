package catering.businesslogic.shift;

public interface ShiftEventReceiver {

    public void updateCShiftTableCreated(ShiftTable cst);

    public void updateShiftCreated(Shift newShift);

    public void updateShiftRemoved(Shift s);
}
