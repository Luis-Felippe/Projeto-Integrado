
package bookify.model.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookifyDatabase implements IRepository {
    
    private DatabasePostgreSQL InstancedDatabasePostgreSQL = new DatabasePostgreSQL();
    private Connection conection = InstancedDatabasePostgreSQL.getConnection();
    
    @Override
    public void save() {
        
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        
    }

    @Override
    public void delete() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ResultSet get(String table) throws SQLException {
        Statement statement = conection.createStatement();
        String query = String.format("select * from %s", table);
        return statement.executeQuery(query);
    }
    
}
