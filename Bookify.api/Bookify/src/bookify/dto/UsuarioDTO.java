package bookify.dto;

import java.time.LocalDate;
/**
 *
 * @author renan-almeida
 */
public class UsuarioDTO {
    private String id;
    private String nome;
    private String telefone;
    private String turma;
    private LocalDate dataInicio;
    private LocalDate dataDevolucao;

    public UsuarioDTO(String id, String nome, String telefone, String turma) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.turma = turma;
        this.dataInicio = LocalDate.now();
        this.dataDevolucao = LocalDate.now().plusDays(5);
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getTurma() {
        return turma;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }
    
    
}
