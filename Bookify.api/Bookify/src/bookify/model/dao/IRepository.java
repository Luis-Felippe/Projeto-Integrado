package bookify.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IRepository {
    public void save(String table, String[] columns, String[] values) throws SQLException;
    public void delete();
    public void update();
    public ResultSet get(String table) throws SQLException;
}