/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookify.Controller;

import bookify.Treinando;
import bookify.model.dao.BookifyDatabase;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 *
 * @author Luis Felippe
 */
public class AlunosCadastroController {

    private BookifyDatabase repository = new BookifyDatabase();

    @FXML
    private TextField aluTextCurso;

    @FXML
    private TextField aluTextEmail;

    @FXML
    private TextField aluTextEndereco;

    @FXML
    private TextField aluTextMatricula;

    @FXML
    private TextField aluTextNome;

    @FXML
    private TextField aluTextSerie;

    @FXML
    private TextField aluTextTelefone;

    @FXML
    protected void cadastrarAluno(ActionEvent e) throws SQLException {
        if (this.aluTextCurso.getText().isEmpty()
                || this.aluTextEmail.getText().isEmpty()
                || this.aluTextEndereco.getText().isEmpty()
                || this.aluTextMatricula.getText().isEmpty()
                || this.aluTextNome.getText().isEmpty()
                || this.aluTextSerie.getText().isEmpty()
                || this.aluTextTelefone.getText().isEmpty()) {
            System.out.print("Não foi possivel cadastrar aluno");
        } else {
            String[] columns = {
                "nome", "telefone", "tipo", "matricula", "turma", "curso", "email"
            };
            String[] values = {
                this.aluTextCurso.getText(),
                this.aluTextEmail.getText(),
                "A",
                this.aluTextMatricula.getText(),
                this.aluTextNome.getText(),
                this.aluTextSerie.getText(),
                this.aluTextTelefone.getText()
            };

            repository.save("usuario", columns, values);
        }
    }

    
    @FXML
    protected void menuProfessor(ActionEvent e){
        Treinando.mudarTela(2);
    }
    
    @FXML
    protected void voltar(ActionEvent e) throws SQLException, IOException {
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
