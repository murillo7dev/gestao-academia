package br.com.academia.gestao;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class TreinoDAO {
    
    public boolean cadastrarTreino(Treino treino){
        boolean sucesso = false;
        Connection conn = new conectaDAO().connectDB();
        
        try {
            String sql = "INSERT INTO treino (descricao, data_criacao, aluno_id) VALUES (?, ?, ?)";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setString(1, treino.getDescricao());
            prep.setString(2, treino.getDataCriacao());
            prep.setInt(3, treino.getAluno().getId());
            prep.executeUpdate();
            sucesso = true;
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar treino: " + erro.getMessage());
        }
        return sucesso;
    }
    
    public ArrayList<Treino> listarTreinos(){
        ArrayList<Treino> lista = new ArrayList<>();
        Connection conn = new conectaDAO().connectDB();
        
        try {
            String sql = "SELECT t.id, t.descricao, t.data_criacao, a.id as aluno_id, a.nome, a.telefone " +
                         "FROM treino t JOIN aluno a ON t.aluno_id = a.id";
            PreparedStatement prep = conn.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            
            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("aluno_id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setTelefone(rs.getString("telefone"));
                
                Treino treino = new Treino();
                treino.setId(rs.getInt("id"));
                treino.setDescricao(rs.getString("descricao"));
                treino.setDataCriacao(rs.getString("data_criacao"));
                treino.setAluno(aluno);
                lista.add(treino);
            }
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro ao listar treinos: " + erro.getMessage());
        }
        return lista;
    }
}