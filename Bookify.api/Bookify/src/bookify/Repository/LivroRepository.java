package bookify.Repository;

import bookify.Models.BookifyDatabase;
import bookify.Models.Livro;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivroRepository {
    private BookifyDatabase repositorio = BookifyDatabase.getInstancia();

    public void salvar(Livro livro) throws SQLException {
        String[] columns = {
                "num_registro", "titulo", "autor", "volume", "exemplar", "lugar", "data_livro", "editora",
                "ano_publicacao", "forma_aquisicao", "observacao", "categoria"
        };

        String[] values = {
                livro.getNumRegistro(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getVolume(),
                livro.getExemplar(),
                livro.getLugar(),
                livro.getDataLivro(),
                livro.getEditora(),
                livro.getAnoPublicacao(),
                livro.getFormaAquisicao(),
                livro.getObservacao() != null ? livro.getObservacao() : "",
                livro.getCategoria()
        };

        repositorio.save("livro", columns, values);
    }

    public void atualizar(Livro livro, String idOriginal, String volumeOriginal, String exemplarAlvo) throws SQLException {
        String[] columns = {
                "num_registro", "titulo", "autor", "volume", "exemplar", "lugar", "data_livro", "editora",
                "ano_publicacao", "forma_aquisicao", "observacao", "categoria"
        };

        String[] values = {
                livro.getNumRegistro(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getVolume(),
                livro.getExemplar(),
                livro.getLugar(),
                livro.getDataLivro(),
                livro.getEditora(),
                livro.getAnoPublicacao(),
                livro.getFormaAquisicao(),
                livro.getObservacao() != null ? livro.getObservacao() : "",
                livro.getCategoria()
        };

        String filtro;
        if ("TODOS".equals(exemplarAlvo)) {
            filtro = String.format("num_registro = '%s' and volume = '%s'", idOriginal, volumeOriginal);
        } else {
            filtro = String.format("num_registro = '%s' and volume = '%s' and exemplar = '%s'",
                    idOriginal, volumeOriginal, exemplarAlvo);
        }

        repositorio.update("livro", columns, values, filtro);
    }

    public void deletar(String id, String volume, String exemplar) throws SQLException {
        if (exemplar == null || exemplar.equals("TODOS")) {
            repositorio.delete("livro", String.format("num_registro = '%s' and volume = '%s'", id, volume));
        } else {
            repositorio.delete("livro", String.format("num_registro = '%s' and volume = '%s' and exemplar = '%s'", id, volume, exemplar));
        }
    }

    public List<String> buscarExemplares(String id, String volume) throws SQLException {
        List<String> exemplares = new ArrayList<>();
        String filtro = String.format("num_registro = '%s' and volume = '%s' ORDER BY exemplar ASC", id, volume);
        ResultSet result = repositorio.get("Livro", filtro);

        while (result.next()) {
            exemplares.add(result.getString("exemplar"));
        }
        return exemplares;
    }

    public Livro buscarPorId(String id, String volume, String exemplarAlvo) throws SQLException {
        String filtro = String.format("num_registro = '%s' and volume = '%s'", id, volume);
        ResultSet result = repositorio.get("Livro", filtro);

        while (result.next()) {
            // Se o exemplar for "TODOS" ou o específico, retorna este objeto
            if (exemplarAlvo.equals("TODOS") || exemplarAlvo.equals(result.getString("exemplar"))) {
                return mapearResultSetParaLivro(result);
            }
        }
        return null;
    }

    public List<Livro> buscar(String termo, boolean apenasEmprestados) throws SQLException {
        String tabela = apenasEmprestados ? "livros_emprestados" : "Livro";
        String filtro = String.format("(UPPER (autor) like '%%%s%%' ) OR (UPPER(titulo) like '%%%s%%') OR (UPPER (categoria) like '%%%s%%') "
                + "ORDER BY titulo ASC, num_registro ASC, volume ASC, exemplar ASC", termo, termo, termo);

        ResultSet result = repositorio.get(tabela, filtro);
        List<Livro> lista = new ArrayList<>();

        while (result.next()) {
            lista.add(mapearResultSetParaLivro(result));
        }
        return lista;
    }

    private Livro mapearResultSetParaLivro(ResultSet res) throws SQLException {
        return new Livro.Builder(res.getString("num_registro"), res.getString("titulo"))
                .autor(res.getString("autor"))
                .volume(res.getString("volume"))
                .exemplar(res.getString("exemplar"))
                .lugar(res.getString("lugar"))
                .dataLivro(res.getString("data_livro"))
                .editora(res.getString("editora"))
                .anoPublicacao(res.getString("ano_publicacao"))
                .formaAquisicao(res.getString("forma_aquisicao"))
                .observacao(res.getString("observacao"))
                .categoria(res.getString("categoria"))
                .build();
    }
}
