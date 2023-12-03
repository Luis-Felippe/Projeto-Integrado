package bookify.Interface;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IRepository {
    public void save(String table, String[] columns, String[] values) throws SQLException;
    public void delete(String table, String id) throws SQLException;
    public void update(String table, String[] columns, String[] values, String filter) throws SQLException; 
    public ResultSet get(String table) throws SQLException;
    public ResultSet get(String table, String filter) throws SQLException;
    public ResultSet get(String search, String table, String filter) throws SQLException;
}
