package catering.businesslogic.duty;

import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.event.EventInfo;
import catering.businesslogic.shift.Shift;
import catering.businesslogic.user.User;
import catering.persistence.BatchUpdateHandler;
import catering.persistence.PersistenceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DutySheet {
    protected int id;
    protected EventInfo event;
    protected User owner;
    protected boolean active;
    protected ObservableList<Task> tasks = FXCollections.observableArrayList();
    protected ObservableList<Task> overflowTasks = FXCollections.observableArrayList();




    public DutySheet(EventInfo ev, boolean active) {
        this.event = ev;
        this.active = active;
        this.owner = ev.getOrganizer();
    }


    public int getEventId(){
        return this.event.getId();
    }

    public int getOwnerId(){
        return this.owner.getId();
    }

    public Task addTask(String name, String description, int qty) {
        Task task = new Task(name, description, qty);
        tasks.add(task);
        return task;
    }
    public void moveTask(Task task, int position) {
        tasks.remove(task);
        tasks.add(position, task);
    }

    public void assignTask(Task task, Shift[] shifts, User[] staff) throws UseCaseLogicException {
        task.assignTask(shifts,staff);
    }


    //------------------------persistence methods---------------------------------
    public static void saveNewDutySheet(DutySheet ds) {
        String newDS = "INSERT INTO catering.DutySheets (event_id, owner_id, active) VALUES (?, ?, ?);";
        int[] result = PersistenceManager.executeBatchUpdate(newDS, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, ds.getEventId());
                ps.setInt(2, ds.getOwnerId());
                ps.setBoolean(3, ds.active);
            }
            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                if (count == 0){
                    ds.id = rs.getInt(1);
                }
            }
        });
    }


    public Task setOverflow(ObservableList<Task> ol, Task task, boolean overflow, int qty, int guests) {
        Task ofTask = task.setOverflow(overflow,qty,guests);
        ol.add(ofTask);
        return ofTask;
    }

    public static DutySheet openDutySheetFromDB(DutySheet ds) {
        String strDS = "SELECT * FROM catering.DutySheets WHERE event_id=? AND owner_id=? AND active=?";
        int[] result = PersistenceManager.executeBatchUpdate(strDS,1, new BatchUpdateHandler() {

            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, ds.getEventId());
                ps.setInt(2, ds.getOwnerId());
                ps.setBoolean(3, ds.active);
                ResultSet rs = ps.executeQuery();

                while(rs.next()){
                    int id = rs.getInt("id");
                    int eventId = rs.getInt("event_id");
                    int ownerId = rs.getInt("owner_id");
                    Boolean active = rs.getBoolean("active");

                    System.out.printf("%d | %d | %d | %b\n", id, eventId, ownerId, active);
                }
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {

            }
        });
        return ds;
    }

}
