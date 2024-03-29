
package bookify.Models;

import bookify.Interface.IRepository;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookifyDatabase implements IRepository {
    private static BookifyDatabase instancia = null;
    private DatabasePostgreSQL InstancedDatabasePostgreSQL;
    private Connection conection;
    
    private BookifyDatabase(){
        InstancedDatabasePostgreSQL = new DatabasePostgreSQL();
        conection = InstancedDatabasePostgreSQL.getConnection();
    }
    
    public static BookifyDatabase getInstancia(){
        if(instancia == null) instancia = new BookifyDatabase();
        return instancia;
    } 

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
    
    public ResultSet getFunction(String function) throws SQLException {
        Statement statement = conection.createStatement();
        String query = String.format("select %s", function);
        return statement.executeQuery(query);
    }
}
