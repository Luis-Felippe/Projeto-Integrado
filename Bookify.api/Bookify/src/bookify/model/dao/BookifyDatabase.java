
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
        System.out.print(query);
        statement.execute(query);
        statement.close();
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
