/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookify.Controller;

import bookify.Treinando;
import bookify.model.dao.BookifyDatabase;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/**
 *
 * @author Luis Felippe
 */
public class AlunosCadastroController {
    
    @FXML
    
    protected void voltar(ActionEvent e) throws SQLException, IOException{
        /*try{
            var n = new BookifyDatabase();
            var x = n.get("Usuario");
            while(x.next()){
                System.out.println(x.getString("nome"));
            }
            if(!x.isClosed()){
                x.close();
            }
            
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        */
        Treinando.mudarTela(1);
    }
    
}
