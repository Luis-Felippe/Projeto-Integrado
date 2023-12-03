
package bookify.Interface;

import java.io.IOException;
import java.sql.SQLException;

public interface IEditar {
    public void setParametros(Object obj);
    public void atualizar() throws SQLException, IOException;
    public void carregarInformacao();
}
