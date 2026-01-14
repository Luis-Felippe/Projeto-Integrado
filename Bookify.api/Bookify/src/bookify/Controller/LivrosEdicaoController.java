package bookify.Controller;

import bookify.DAO.LivroDAO;
import bookify.Interface.IEditar;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import bookify.Models.Livro;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LivrosEdicaoController extends TelasLivrosController implements IEditar{
    private LivroDAO livroDAO = new LivroDAO();
    
    private Object params;
    private Object params2;
    
    
    @FXML
    private ChoiceBox<String> exemplar;
    
    @FXML
    private TextField livroTextAnoPublicacao;
    
    @FXML
    private Text erro;

    @FXML
    private TextField livroTextAutor;

    @FXML
    private TextField livroTextCategoria;
    
    @FXML
    private DatePicker livroTextData;

    @FXML
    private TextField livroTextEditora;

    @FXML
    private TextField livroTextFormaAquisicao;

    @FXML
    private TextField livroTextLocal;

    @FXML
    private TextField livroTextNumReg;

    @FXML
    private TextField livroTextObservacao;

    @FXML
    private TextField livroTextTitulo;

    @FXML
    private TextField livroTextVolume;

    // seta a variável parâmetros contendo o id e chama carregarInformação()
    public void setParametros(Object id, Object volume){
        this.params = id;
        this.params2 = volume;
        carregarExemplares(id.toString(), volume.toString());
        carregarInformacao();
        exemplar.setOnAction(event ->{
            if (exemplar.getValue() != null) carregarInformacao();
        });
    }
    
    public void setParametros(Object obj){
        this.params = obj;
        carregarInformacao();
    }
    
    
    private void carregarExemplares(String id, String volume){
        try {
            exemplar.getItems().clear();
            exemplar.getItems().add("TODOS");
            exemplar.setValue("TODOS");

            List<String> lista = livroDAO.buscarExemplares(id, volume);
            exemplar.getItems().addAll(lista);

        } catch (SQLException ex) {
            erro.setText("Erro ao carregar exemplares.");
        }
    }

    @FXML
    public void atualizar() throws IOException{
        try {
            Livro livroEditado = new Livro.Builder(livroTextNumReg.getText(), livroTextTitulo.getText())
                    .autor(livroTextAutor.getText())
                    .volume(livroTextVolume.getText())
                    .exemplar(exemplar.getValue())
                    .lugar(livroTextLocal.getText())
                    .dataLivro(livroTextData.getEditor().getText())
                    .editora(livroTextEditora.getText())
                    .anoPublicacao(livroTextAnoPublicacao.getText())
                    .formaAquisicao(livroTextFormaAquisicao.getText())
                    .observacao(livroTextObservacao.getText())
                    .categoria(livroTextCategoria.getText())
                    .build();

            livroDAO.atualizar(livroEditado,
                    params.toString(),
                    params2.toString(),
                    exemplar.getValue());

            listarLivro();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            erro.setText("Verifique se todos os dados estão preenchidos corretamente!");
        }
    }

    public void carregarInformacao(){
        try {
            Livro livro = livroDAO.buscarPorId(params.toString(), params2.toString(), exemplar.getValue());

            if (livro != null) {
                preencherCampos(livro);
            }
        } catch (SQLException ex) {
            erro.setText("Erro ao buscar informações do livro.");
        }
    }

    private void preencherCampos(Livro livro) {
        livroTextAnoPublicacao.setText(livro.getAnoPublicacao());
        livroTextAutor.setText(livro.getAutor());
        livroTextData.getEditor().setText(formataData(livro.getDataLivro()));
        livroTextEditora.setText(livro.getEditora());
        livroTextFormaAquisicao.setText(livro.getFormaAquisicao());
        livroTextLocal.setText(livro.getLugar());
        livroTextNumReg.setText(livro.getNumRegistro());
        livroTextObservacao.setText(livro.getObservacao());
        livroTextTitulo.setText(livro.getTitulo());
        livroTextVolume.setText(livro.getVolume());
        livroTextCategoria.setText(livro.getCategoria());
    }
    
    private String formataData(String dataString){
        SimpleDateFormat formatoBanco = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatoTela = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date data = formatoBanco.parse(dataString);
            return formatoTela.format(data);
        } catch(ParseException e) {
            return dataString;
        }
    }
}
