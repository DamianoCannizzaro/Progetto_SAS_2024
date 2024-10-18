package catering.businesslogic.shift;

public interface ShiftEventReceiver {

    public void updateCookShiftCreated(ShiftTable cst);

    public void updateShiftCreated(Shift newShift);
}
