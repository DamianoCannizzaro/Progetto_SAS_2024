package catering.businesslogic.duty;

import catering.businesslogic.user.User;

public interface DutyEventReceiver {
    void updateDutySheetCreated(DutySheet ds);

    void updateDutySheetOpened(DutySheet checkedDS);

    void updateTaskCreated(Task task);

    void updateTaskRearranged(Task task, int position);

    void updateTaskAssigned(Task task, User[] staff);

    void updateOverflowSet(Task ofTask);


}
