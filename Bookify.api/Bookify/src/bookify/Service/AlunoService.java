package bookify.Service;

import bookify.Interface.IRepository;
import java.sql.SQLException;

public class AlunoService {

    private static final String TABELA_ALUNOS = "usuario";
    private static final String TIPO_ALUNO = "A"; //

    private IRepository repositorio;

    public AlunoService(IRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void cadastrarAluno(
            String nome,
            String telefone,
            String matricula,
            String turma,
            String curso,
            String email) throws SQLException {

        String[] colunas = {
                "nome", "telefone", "tipo", "matricula", "turma", "curso", "email"
        };

        String[] valores = {
                nome,
                telefone,
                TIPO_ALUNO,
                matricula,
                turma,
                curso,
                email
        };

        repositorio.save(TABELA_ALUNOS, colunas, valores);
    }
}

