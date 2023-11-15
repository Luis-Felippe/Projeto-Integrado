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
                this.aluTextNome.getText(),
                this.aluTextTelefone.getText(),
                "A",
                this.aluTextMatricula.getText(),
                this.aluTextSerie.getText(),
                this.aluTextCurso.getText(),
                this.aluTextEmail.getText()
            };

            repository.save("usuario", columns, values);
        }
    }
   
    @FXML
    protected void alunoMenu(){
       Treinando.mudarTela(1); 
    }
     @FXML
    protected void professorMenu(){
        Treinando.mudarTela(2);
    }
     @FXML
    protected void homeMenu(){
        Treinando.mudarTela(4);
    }
    @FXML
    protected void livroMenu(){
       Treinando.mudarTela(7); 
    }
}
