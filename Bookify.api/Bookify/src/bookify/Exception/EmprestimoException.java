package bookify.Exception;

public class EmprestimoException extends Exception {
    
    public EmprestimoException(String mensagem) {
        super(mensagem);
    }
    
    public EmprestimoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
