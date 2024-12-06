package catering.persistence;

// import com.sun.javafx.binding.StringFormatter;

import java.sql.*;

public class PersistenceManager {
    private static String url = "jdbc:mysql://localhost:3306/catering?serverTimezone=UTC";
    private static String username = "root";
    private static String password = "admin";

    private static int lastId;

    public static String escapeString(String input) {
        input = input.replace("\\", "\\\\");
        input = input.replace("\'", "\\\'");
        input = input.replace("\"", "\\\"");
        input = input.replace("\n", "\\n");
        input = input.replace("\t", "\\t");
        return input;
    }
    public static void testSQLConnection() {
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM Users where true");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("username");
                System.out.println(name + " ha id = " + id);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void clearAllTables() {
        // Lista delle tabelle da svuotare
        String[] tables = { "catering.Shifts", "catering.shifttables", "catering.menus", "catering.menufeatures", "catering.menuitems" };
        for (String table : tables) {
            String deleteQuery = "DELETE FROM " + table + ";";
            int[] result = PersistenceManager.executeBatchUpdate(deleteQuery, 1, new BatchUpdateHandler() {
                @Override
                public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                }

                @Override
                public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                }
            });

            // Opzionale: gestione del risultato dell'operazione
            System.out.println("Tabella " + table + ": " + result[0] + " record eliminati.");
        }
    }

    /**
     *  metodo che permette di eseguire una query mandata in input
     * */
    public static void executeQuery(String query, ResultHandler handler) {
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                handler.handle(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static int[] executeBatchUpdate(String parametrizedQuery, int itemNumber, BatchUpdateHandler handler) {
        int[] result = new int[0];
        try (
                Connection conn = DriverManager.getConnection(url, username, password);
                PreparedStatement ps = conn.prepareStatement(parametrizedQuery, Statement.RETURN_GENERATED_KEYS);
        ) {
            for (int i = 0; i < itemNumber; i++) {
                handler.handleBatchItem(ps, i);
                ps.addBatch();
            }
            result = ps.executeBatch();
            ResultSet keys = ps.getGeneratedKeys();
            int count = 0;
            while (keys.next()) {
                handler.handleGeneratedIds(keys, count);
                count++;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return result;
    }

    public static int executeUpdate(String update) {
        int result = 0;
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = conn.prepareStatement(update, Statement.RETURN_GENERATED_KEYS)) {
            result = ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                lastId = rs.getInt(1);
            } else {
                lastId = 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public static int getLastId() {
        return lastId;
    }
}
