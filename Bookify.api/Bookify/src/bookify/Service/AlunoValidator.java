package bookify.Service;


public class AlunoValidator {

    public static boolean validarCamposObrigatorios(
            String nome,
            String telefone,
            String matricula,
            String curso,
            String turma,
            String email) {

        return !nome.isEmpty()
                && !telefone.isEmpty()
                && !matricula.isEmpty()
                && !curso.isEmpty()
                && turma != null
                && !email.isEmpty();
    }

    public static boolean validarEmail(String email) {
        return email.contains("@") && email.contains(".");
    }

    public static boolean validarTelefone(String telefone) {
        return telefone.length() >= 10;
    }
}
