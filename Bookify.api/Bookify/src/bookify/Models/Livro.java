package bookify.Models;

import java.sql.ResultSet;

public class Livro {
    private final String numRegistro;
    private final String titulo;
    private final String autor;
    private final String volume;
    private final String exemplar;
    private final String lugar;
    private final String dataLivro;
    private final String editora;
    private final String anoPublicacao;
    private final String formaAquisicao;
    private final String observacao;
    private final String categoria;

    private Livro(Builder builder) {
        this.numRegistro = builder.numRegistro;
        this.titulo = builder.titulo;
        this.autor = builder.autor;
        this.volume = builder.volume;
        this.exemplar = builder.exemplar;
        this.lugar = builder.lugar;
        this.dataLivro = builder.dataLivro;
        this.editora = builder.editora;
        this.anoPublicacao = builder.anoPublicacao;
        this.formaAquisicao = builder.formaAquisicao;
        this.observacao = builder.observacao;
        this.categoria = builder.categoria;
    }

    public String getNumRegistro() { return numRegistro; }
    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getVolume() { return volume; }
    public String getExemplar() { return exemplar; }
    public String getLugar() { return lugar; }
    public String getDataLivro() { return dataLivro; }
    public String getEditora() { return editora; }
    public String getAnoPublicacao() { return anoPublicacao; }
    public String getFormaAquisicao() { return formaAquisicao; }
    public String getObservacao() { return observacao; }
    public String getCategoria() { return categoria; }

    // --- BUILDER (GoF) ---
    public static class Builder {
        // Atributos do builder
        private String numRegistro;
        private String titulo;
        private String autor;
        private String volume;
        private String exemplar;
        private String lugar;
        private String dataLivro;
        private String editora;
        private String anoPublicacao;
        private String formaAquisicao;
        private String observacao;
        private String categoria;

        public Builder(String numRegistro, String titulo) {
            this.numRegistro = numRegistro;
            this.titulo = titulo;
        }

        public Builder autor(String autor) { this.autor = autor; return this; }
        public Builder volume(String volume) { this.volume = volume; return this; }
        public Builder exemplar(String exemplar) { this.exemplar = exemplar; return this; }
        public Builder lugar(String lugar) { this.lugar = lugar; return this; }
        public Builder dataLivro(String dataLivro) { this.dataLivro = dataLivro; return this; }
        public Builder editora(String editora) { this.editora = editora; return this; }
        public Builder anoPublicacao(String anoPublicacao) { this.anoPublicacao = anoPublicacao; return this; }
        public Builder formaAquisicao(String formaAquisicao) { this.formaAquisicao = formaAquisicao; return this; }
        public Builder observacao(String observacao) { this.observacao = observacao; return this; }
        public Builder categoria(String categoria) { this.categoria = categoria; return this; }

        public Livro build() {
            return new Livro(this);
        }
    }
}
