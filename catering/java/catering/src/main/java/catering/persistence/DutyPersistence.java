package catering.persistence;

//TODO: classe DutyPersistence da completare con le dichiarazioni

import catering.businesslogic.duty.DutyEventReceiver;
import catering.businesslogic.duty.DutySheet;
import catering.businesslogic.duty.Task;
import catering.businesslogic.user.User;

public class DutyPersistence implements DutyEventReceiver {

    public void updateDutySheetCreated(DutySheet ds) {DutySheet.saveNewDutySheet(ds); }
    public void updateDutySheetOpened(DutySheet ds) {DutySheet.openDutySheetFromDB(ds);}

    @Override
    public void updateTaskCreated(Task task) {Task.saveNewTask(task);}
    public void updateTaskRearranged(Task task, int position) {Task.updateTaskPosition(task,position);}
    public void updateTaskAssigned(Task task, User[] staff) {Task.saveTaskAssigned(task,staff);}
    public void updateOverflowSet(Task task){Task.updateOverflowTask(task);}
}
