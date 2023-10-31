package bookify.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IRepository {
    public void save();
    public void delete();
    public void update();
    public ResultSet get(String table) throws SQLException;
}
