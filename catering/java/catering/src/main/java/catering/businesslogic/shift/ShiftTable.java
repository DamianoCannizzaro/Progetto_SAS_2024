package catering.businesslogic.shift;

import catering.businesslogic.event.EventInfo;
import catering.businesslogic.user.User;
import catering.persistence.BatchUpdateHandler;
import catering.persistence.PersistenceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;
import java.util.Map;

public abstract class ShiftTable {
    private static Map<Integer, ShiftTable> loadedTables = FXCollections.observableHashMap();
    protected int id;
    protected EventInfo event;
    protected String type;
    protected boolean order;
    User owner;
    protected ObservableList<Shift> Shifts = FXCollections.observableArrayList();
    protected final ObservableMap<ShiftTable, Date> recurringTable = FXCollections.observableHashMap();

    public boolean isEmpty() {
        return Shifts.isEmpty();
    }

    public ShiftTable checkTable(ShiftTable st) {
        return st;
    }

    public Shift addShift(ShiftTable st, Time startTime, Time endTime, Date jobDate, Date deadline, boolean group, String groupName) {
        Shift newS = new Shift(st.event.getId(), startTime, endTime, jobDate, deadline, group, groupName);
        Shifts.add(newS);
        return newS;

    }

    public Shift updateShift(Shift s, Time startTime, Time endTime, Date jobDate, Date deadline, boolean group, String groupName) {
        return s.UpdateShift(s, startTime, endTime, jobDate, deadline, group, groupName);
    }
//FIXME: grande errore porcoddio i turni sono tutti mischiati e non vanno nelle tabelle specifiche diocanaglia cazzo io de dio
    //TODO: aggiungere metodo elimina turno; done
    public void deleteShift(Shift s) {
        Shifts.remove(s);
    }
    //TODO: aggiungere metodo tabella ricorrente; done
    public ShiftTable addRecurringTable(ShiftTable st, Date[] dates) {
        for (int i = 0; i < dates.length; i++) {
            recurringTable.put(st, dates[i]);
        }
        return st;
    }
    //TODO:

    public ObservableList<Shift> getShifts() {
        return Shifts;
    }

    public ObservableMap<ShiftTable, Date> getRecurringTable() {
        return recurringTable;
    }


    //PERSISTENCE AND FORMAT METHODS

    public static void saveNewShiftTable(ShiftTable st) {
        String stInsert = "INSERT INTO catering.ShiftTables (event, ev_type, owner_id, `order`) VALUES (?, ?, ?, ?);";
        int[] result = PersistenceManager.executeBatchUpdate(stInsert, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setString(1, PersistenceManager.escapeString(st.event.getName()));
                ps.setString(2, PersistenceManager.escapeString(st.type));
                ps.setInt(3, st.owner.getId());
                ps.setBoolean(4, st.order);
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                if (count == 0) {
                    st.id = rs.getInt(1);
                }
            }
        });
        //TODO: altri salvataggi da aggiungere allo shift (singoli turni e altro poi vedi)

        if (result[0] > 0) {
            loadedTables.put(st.id, st);
        }

    }


    public String toString() {
        return event.getName() + " | " + type + " | " + owner.getUserName() + " | " + order;
    }

    public void testString() {
        System.out.printf("%-10s  | %-20s | %-1s | %-10s | %s\n  ", "id", "Evento", "tipo", "proprietario", "ordine");
        System.out.println("--------------------------------------------");
        for (Map.Entry<Integer, ShiftTable> entry : loadedTables.entrySet()) {
            int chiave = entry.getKey();
            ShiftTable val = entry.getValue();
            for (Shift shift : Shifts) {
                System.out.println(shift.toString());
            }
            System.out.printf("%-10s | %s\n", chiave, val.toString());
        }
    }



}

