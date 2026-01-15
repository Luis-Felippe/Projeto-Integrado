package bookify.service;

import bookify.dto.LivroDTO;
import bookify.dto.UsuarioDTO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author renan-almeida
 */
public class EmprestimoInfoService {
    
    public LivroDTO montarLivro(ResultSet resLiv) throws SQLException {
        if(resLiv == null || !resLiv.next()){
            return null;
        }
        
        String codigo = resLiv.getString("num_registro");
        String titulo = resLiv.getString("titulo");
        String autor = resLiv.getString("autor");
        
        List<String> volumes = new ArrayList<>();
        String volumeAtual = resLiv.getString("volume");
        volumes.add(volumeAtual);
        
        while(resLiv.next()) {
            if(!volumeAtual.equals(resLiv.getString("volume"))){
                volumeAtual = resLiv.getString("volume");
                volumes.add(volumeAtual);
            }
        }
        
        return new LivroDTO(codigo, titulo, autor, volumes);
    }
    
    public UsuarioDTO montarUsuario(ResultSet resUser) throws SQLException {
        if(resUser == null || !resUser.next()){
            return null;
        }
        
        String turma = resUser.getString("turma");
        if(turma == null) {
            turma = "PROFESSOR!";
        }
        
        return new UsuarioDTO(
                resUser.getString("id_usuario"),
                resUser.getString("nome"),
                resUser.getString("telefone"),
                turma
        );
    }
    
}
