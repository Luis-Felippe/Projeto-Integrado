package bookify.dto;

import java.util.List;

/**
 *
 * @author renan-almeida
 */
public class LivroDTO {
    private String codigo;
    private String titulo;
    private String autor;
    private List<String> volumes;
    
    public LivroDTO(String codigo, String titulo, String autor, List<String> volumes){
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.volumes = volumes;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public List<String> getVolumes() {
        return volumes;
    }

   
}
