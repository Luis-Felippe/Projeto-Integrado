
package bookify.model.dao;

import com.sun.jdi.connect.spi.Connection;

public interface Database {
       public Connection conectar();
       public void desconectar(Connection con);
}
