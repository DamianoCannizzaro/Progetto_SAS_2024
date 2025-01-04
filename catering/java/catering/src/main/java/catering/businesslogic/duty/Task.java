package catering.businesslogic.duty;

import catering.businesslogic.UseCaseLogicException;
import catering.businesslogic.shift.Shift;
import catering.businesslogic.user.User;
import catering.persistence.BatchUpdateHandler;
import catering.persistence.PersistenceManager;
import javafx.collections.ObservableList;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;

public class Task {
    private String name;
    private String description;
    private int qty;
    private boolean overflow;
    private int position;
    private ObservableList<Shift> designedShifts;
    private ObservableList<User> designedStaff;

    public Task(String name, String description, int qty) {
        this.name = name;
        this.description = description;
        this.qty = qty;
        this.overflow = false;
    }

    public int getPosition() { return position; }



    public void assignTask(Shift[] shift, User[] staff) throws UseCaseLogicException {
        LocalDate dateNow = LocalDate.now();
        Date date = Date.valueOf(dateNow);
        for(Shift s : shift){
            if (s.getJobDate().equals(date)){throw new UseCaseLogicException("UseCaseLogic Exception: cannot assign task scheduled for today");}

            designedShifts.add(s);
        }
        designedStaff.addAll(Arrays.asList(staff));
    }


    public Task setOverflow(boolean overflow, int qty, int guests) {
        this.overflow = overflow;
        this.qty = qty -guests;
        return this;
    }

//--------------------------PERSISTENCE METHODS-------------------------------
    //TODO TODO TODO: Metodi di persistence relativi alle task
    public static void saveNewTask(Task task) {
        String newT = "INSERT INTO catering.Tasks (name, description, qty, overflow) VALUES (?,?,?,?);";
        int[] result = PersistenceManager.executeBatchUpdate(newT, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setString(1, task.name);
                ps.setString(2, task.description);
                ps.setInt(3, task.qty);
                ps.setBoolean(4, task.overflow);
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                if (count == 0){
                    task.position = rs.getInt(1);
                }
            }
        });
    }

    public static void updateTaskPosition(Task task,int position) {
        String update = "UPDATE catering.Tasks SET position = ? WHERE name = ? AND position = ?;" ;
        int[] result = PersistenceManager.executeBatchUpdate(update, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, position);
                ps.setString(2, task.name);
                ps.setInt(1,task.position);
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {

            }
        });
    }

    public static int getTaskId(Task task) {
        String strID = "SELECT id FROM catering.tasks WHERE name = ? AND description = ? AND qty = ? AND overflow = ? AND position = ?";
        int[] result = PersistenceManager.executeBatchUpdate(strID, 1, new BatchUpdateHandler() {

            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setString(1, task.name);
                ps.setString(2, task.description);
                ps.setInt(3, task.qty);
                ps.setBoolean(4, task.overflow);
                ps.setInt(5, task.getPosition());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {

            }
        });

                return 1;
    }

    public static void saveTaskAssigned(Task task, User[] staff) {
        for (User user : staff) {
            String newQ = "INSERT INTO catering.TaskAssignment (task_id, user_id) VALUES (?,?);";
            int task_id = getTaskId(task);
            int user_id = user.getId();
            int[] result = PersistenceManager.executeBatchUpdate(newQ, 1, new BatchUpdateHandler() {

                @Override
                public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                    ps.setInt(1, task_id);
                    ps.setInt(2, user_id);

                }

                @Override
                public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {

                }
            });
        }
    }
    public static void updateOverflowTask(Task task) {
        String update = "UPDATE catering.Tasks SET overflow = ? WHERE name = ? AND position = ?;";
        int[] result = PersistenceManager.executeBatchUpdate(update, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setBoolean(1, true);
                ps.setString(2, task.name);
                ps.setInt(1, task.position);
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {

            }
        });

    }

}
