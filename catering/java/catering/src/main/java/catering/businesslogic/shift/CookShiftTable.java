package catering.businesslogic.shift;

import catering.businesslogic.event.EventInfo;

import java.util.Date;

public class CookShiftTable implements ShiftTable{
    @Override
    public ShiftTable createShiftTable(String type, EventInfo ev) {
        return null;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public ShiftTable checkTable(ShiftTable st) {
        return null;
    }

    @Override
    public Shift addShift(ShiftTable st, String type, double startTime, double endTime, Date deadline, double preExt, double postExt, boolean group, String groupName) {
        return null;
    }
}
