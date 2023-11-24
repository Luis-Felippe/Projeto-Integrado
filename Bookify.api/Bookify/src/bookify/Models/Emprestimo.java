
package bookify.Models;

import java.util.Date;


public class Emprestimo {
    private Date dataInicio;
    private Date dataDevolucao;
    private int numRegistroLivro;

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public int getNumRegistroLivro() {
        return numRegistroLivro;
    }

    public void setNumRegistroLivro(int numRegistroLivro) {
        this.numRegistroLivro = numRegistroLivro;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    private int idUsuario;
}
