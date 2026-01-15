package bookify.dto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class EmprestimoDTO {
    private String idEmprestimo;
    private String tituloLivro;
    private String autorLivro;
    private String numRegistroLivro;
    private String volumeLivro;
    private String exemplarLivro;
    private String nomeUsuario;
    private String identificadorUsuario;
    private String idUsuario;
    private String turmaUsuario;
    private String telefoneUsuario;
    private LocalDate dataInicio;
    private LocalDate dataDevolucao;
    private boolean atrasado;

    public EmprestimoDTO() {
    }

    public String getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(String idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public String getTituloLivro() {
        return tituloLivro;
    }

    public void setTituloLivro(String tituloLivro) {
        this.tituloLivro = tituloLivro;
    }

    public String getAutorLivro() {
        return autorLivro;
    }

    public void setAutorLivro(String autorLivro) {
        this.autorLivro = autorLivro;
    }

    public String getNumRegistroLivro() {
        return numRegistroLivro;
    }

    public void setNumRegistroLivro(String numRegistroLivro) {
        this.numRegistroLivro = numRegistroLivro;
    }

    public String getVolumeLivro() {
        return volumeLivro;
    }

    public void setVolumeLivro(String volumeLivro) {
        this.volumeLivro = volumeLivro;
    }

    public String getExemplarLivro() {
        return exemplarLivro;
    }

    public void setExemplarLivro(String exemplarLivro) {
        this.exemplarLivro = exemplarLivro;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getIdentificadorUsuario() {
        return identificadorUsuario;
    }

    public void setIdentificadorUsuario(String identificadorUsuario) {
        this.identificadorUsuario = identificadorUsuario;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTurmaUsuario() {
        return turmaUsuario;
    }

    public void setTurmaUsuario(String turmaUsuario) {
        this.turmaUsuario = turmaUsuario;
    }

    public String getTelefoneUsuario() {
        return telefoneUsuario;
    }

    public void setTelefoneUsuario(String telefoneUsuario) {
        this.telefoneUsuario = telefoneUsuario;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public boolean isAtrasado() {
        return atrasado;
    }

    public void setAtrasado(boolean atrasado) {
        this.atrasado = atrasado;
    }

    public static EmprestimoDTO fromResultSet(ResultSet rs) throws SQLException {
        EmprestimoDTO dto = new EmprestimoDTO();
        
        dto.setIdEmprestimo(rs.getString("id_emprestimo"));
        dto.setTituloLivro(rs.getString("titulo_livro"));
        dto.setAutorLivro(rs.getString("autor_livro"));
        dto.setNumRegistroLivro(rs.getString("num_registro_livro"));
        dto.setNomeUsuario(rs.getString("nome_usuario"));
        dto.setIdentificadorUsuario(rs.getString("identificador_usuario"));
        dto.setIdUsuario(rs.getString("id_usuario"));
        dto.setDataInicio(LocalDate.parse(rs.getString("data_inicio")));
        dto.setDataDevolucao(LocalDate.parse(rs.getString("data_devolucao")));
        dto.setVolumeLivro(rs.getString("volume_livro"));
        dto.setExemplarLivro(rs.getString("exemplar_livro"));
        dto.setTurmaUsuario(rs.getString("turma_usuario"));
        dto.setTelefoneUsuario(rs.getString("telefone_usuario"));
        
        dto.setAtrasado(LocalDate.now().isAfter(dto.getDataDevolucao()));
        
        return dto;
    }

    public String getCpf() {
        return identificadorUsuario;
    }

    public String getMatricula() {
        return identificadorUsuario;
    }
}
