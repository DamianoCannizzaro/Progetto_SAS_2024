package catering.businesslogic.duty;

import catering.businesslogic.CatERing;
import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.shift.Shift;
import catering.businesslogic.user.User;
import javafx.collections.ObservableList;

import java.util.ArrayList;


public class DutyManager {
    private DutySheet currentDutySheet;
    private ArrayList<DutyEventReceiver> eventReceivers;

    public DutyManager() {
        eventReceivers = new ArrayList<>();
    }

    //TODO: AGGIUNGERE METODI DA DSD SU DRIVE

    public DutySheet createDutySheet(EventInfo ev, boolean active) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (!user.isChef() || !ev.isAssigned(user)) {
            throw new UseCaseLogicException("UseCaseLogicException: User is not chef or not assigned");
        }
        DutySheet ds = new DutySheet(ev, active);
        setCurrentDutySheet(ds);
        notifyDutySheetCreated(ds);
        return ds;
    }

    private void setCurrentDutySheet(DutySheet dutySheet) {
        this.currentDutySheet = dutySheet;
    }
    public Task addTask(String name, String description, int qty ) throws UseCaseLogicException {
        Task task = currentDutySheet.addTask(name,description,qty);
        notifyTaskCreated(task);
        return task;
    }

    public void moveTask(Task task, int position) throws UseCaseLogicException {
        if(task.getPosition() == position) throw new UseCaseLogicException("UseCaseLogicException: Task is already at position");
        if (position <0 ) throw new UseCaseLogicException("UseCaseLogicException: Task position out of bonds");
        currentDutySheet.moveTask(task,position);

        notifyTaskRearranged(task,position);
    }

    public void assignTask(DutySheet ds, Task task, Shift[] shifts,User[] staff) throws UseCaseLogicException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();
        if (!user.isChef()) throw new UseCaseLogicException("UseCaseLogic Exception: user is not chef");
        if(staff.length==0) throw new UseCaseLogicException("UseCaseLogic Exception: staff is empty");
        ds.assignTask(task,shifts,staff);
        notifyTaskAssigned(task,staff);
    }


    public Task setOverflow(ObservableList<Task> ol, DutySheet ds, Task task, boolean overflow, int qty, int guests) throws UseCaseLogicException {
        if(ol== null) throw new UseCaseLogicException("UseCaseLogicException: overflow list is null");
        if(qty <= guests) throw new UseCaseLogicException("UseCaseLogicException: guests more than quantity");
        Task olTask = ds.setOverflow(ol,task,overflow,qty,guests);
        notifyOverflowSet(olTask);
        return olTask;
    }

    public DutySheet openDutySheet(DutySheet ds) throws UseCaseLogicException {
        if(ds == null) throw new UseCaseLogicException("UseCaseLogic Exception: Duty SHeet is null.");
        DutySheet checkedDS = ds.openDutySheetFromDB(ds);
        notifyDutySheetOpened(checkedDS);
        return checkedDS;
    }




    //------------------------NOTIFY METHODS------------------------------

    public void addEventReceiver(DutyEventReceiver rec) {
        this.eventReceivers.add(rec);
    }

    private void notifyTaskCreated(Task task) {
        for (DutyEventReceiver er : eventReceivers) {
            er.updateTaskCreated(task);
        }
    }

    private void notifyDutySheetCreated(DutySheet ds) {
        for(DutyEventReceiver er : eventReceivers) {
            //TODO: implementare persistenza duty sheet
            er.updateDutySheetCreated(ds);
        }
    }

    private void notifyTaskRearranged(Task task, int position) {
        for (DutyEventReceiver er : eventReceivers) {
            er.updateTaskRearranged(task,position);
        }
    }
    private void notifyOverflowSet(Task ofTask) {
        for (DutyEventReceiver er : eventReceivers) {
            er.updateOverflowSet(ofTask);
        }
    }

    private void notifyTaskAssigned(Task task, User[] staff) {
        for (DutyEventReceiver er : eventReceivers) {
            er.updateTaskAssigned(task,staff);
        }
    }

    private void notifyDutySheetOpened(DutySheet checkedDS) {
        for (DutyEventReceiver er : eventReceivers) {
            er.updateDutySheetOpened(checkedDS);
        }
    }
}
