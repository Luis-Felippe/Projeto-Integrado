/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookify.Models;

import java.util.Date;

/**
 *
 * @author felip
 */
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
