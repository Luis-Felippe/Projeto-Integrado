
package bookify.model.dao;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookifyDatabase implements IRepository {
    
    private DatabasePostgreSQL InstancedDatabasePostgreSQL = new DatabasePostgreSQL();
    private Connection conection = InstancedDatabasePostgreSQL.getConnection();
    
    @Override
    public void save(String table, String[] columns, String[] values) throws SQLException {
        List<String> listaComAspas = new ArrayList<>();
        for (String str : values) {
            String strComAspas = "'" + str + "'";
            listaComAspas.add(strComAspas);
        }

        Statement statement = conection.createStatement();
        String query = String.format("insert into %s (%s) values (%s)",
                table,
                String.join(",", columns),
                String.join(",", listaComAspas));
        statement.execute(query);
        statement.close();
    }

    @Override
    public void delete(String table, String id) throws SQLException{
        
        Statement statement = conection.createStatement();
        String query = String.format("DELETE FROM %s where %s",
            table,
            id);
        statement.execute(query);
        statement.close();
        
        
        
    }

    @Override
    public void update(String table, String[] columns, String[] values, String filter) throws SQLException{
        List<String> listaComAspas = new ArrayList<>();
        for (int i = 0; i < columns.length; i++){
            String formatado = String.format("%s = '%s'", columns[i], values[i]);
//            String strComAspas = columns[i] +  " = " + "'" + values[i] + "'";
            listaComAspas.add(formatado);
        }
        

        Statement statement = conection.createStatement();
        String query = String.format("update %s set %s where %s",
                table,
                String.join(",", listaComAspas), 
                filter
                );
        statement.execute(query);
        statement.close();
    }
    
    
    public void update(String table, String column, String value, String filter) throws SQLException{
        Statement statement = conection.createStatement();
        String query = String.format("update %s set %s = '%s' where '%s'",
                table,
                column,
                value,
                filter
                );
        statement.execute(query);
        statement.close();
    }

    @Override
    public ResultSet get(String table) throws SQLException {
        Statement statement = conection.createStatement();
        String query = String.format("select * from %s", table);
        return statement.executeQuery(query);
    }
    public ResultSet get(String table, String filter) throws SQLException {
        Statement statement = conection.createStatement();
        String query = String.format("select * from %s where %s", table, filter);
        return statement.executeQuery(query);
    }
    
    public ResultSet get(String search, String table, String filter) throws SQLException {
        Statement statement = conection.createStatement();
        String query = String.format("select %s from %s %s", search, table, filter);
        return statement.executeQuery(query);
    }
}
