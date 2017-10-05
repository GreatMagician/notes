package notes.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import notes.model.Notes;

import java.sql.*;

/**
 * Работа с БД
 */
public class bd {

    private static Connection getConnection() throws Exception{
        try{
            String driver = "org.postgresql.Driver";
            String url = "jdbc:postgresql://localhost:5432/notes";
            String userName = "user";
            String password = "password";
            Class.forName(driver);
            Connection conn = (Connection) DriverManager.getConnection(url, userName, password);
            conn.setAutoCommit(false);
            return conn;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void closeConnection(Connection conn){
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTable(){
        Connection conn = null;
        try{
            String sql = "DROP TABLE IF EXISTS notes CASCADE;\n" +
                    "DROP SEQUENCE IF EXISTS global_seq;\n" +
                    "\n" +
                    "CREATE SEQUENCE global_seq START 10;\n" +
                    "\n" +
                    "\n" +
                    "CREATE TABLE notes\n" +
                    "(\n" +
                    "  id         int8 PRIMARY KEY DEFAULT nextval('global_seq'),\n" +
                    "  data       VARCHAR NOT NULL,\n" +
                    "  time       VARCHAR NOT NULL,\n" +
                    "  text       VARCHAR(100)\n" +
                    ");\n";
            conn = getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.execute();
            conn.commit();
        } catch(Exception e) {
            e.printStackTrace();
        }finally {
            closeConnection(conn);
        }
    }


    public static ObservableList<Notes> getAll() throws Exception {
        Connection conn = null;
        String sql = "SELECT * FROM notes";
        try{
            conn = getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet result = statement.executeQuery();
            ObservableList<Notes> list = FXCollections.observableArrayList();
            while(result.next()){
                //создаем свой pojo
                Notes notes = new Notes();
                notes.setData(result.getString("data"));
                notes.setTime(result.getString("time"));
                notes.setText(result.getString("text"));

                list.add(notes);
            }
            return list;
        } catch(Exception e) {
            e.printStackTrace();
        }finally {
            closeConnection(conn);
        }
        return null;
    }

    public static void save(Notes notes) throws Exception {
        Connection conn = null;
        String sql = "INSERT INTO notes(data, time, text) VALUES (?,?,?)";
        try{
        conn = getConnection();
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, notes.getData());
            preparedStatement.setString(2, notes.getTime());
            preparedStatement.setString(3, notes.getText());
            preparedStatement.execute();
        conn.commit();
        } catch(Exception e) {
            e.printStackTrace();
        }finally {
            closeConnection(conn);
        }
    }

    public static void delete(Notes notes) throws Exception {
        Connection conn = null;
        String sql = "DELETE FROM notes WHERE data=? and time=? and text=?";
        try{
            conn = getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, notes.getData());
            preparedStatement.setString(2, notes.getTime());
            preparedStatement.setString(3, notes.getText());
            preparedStatement.execute();
            conn.commit();
        } catch(Exception e) {
            e.printStackTrace();
        }finally {
            closeConnection(conn);
        }
    }


}
