package br.com.academia.gestao;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class AlunoDAO {
    
    public boolean cadastrarAluno(Aluno aluno){
        boolean sucesso = false;
        Connection conn = new conectaDAO().connectDB();
        
        try {
            String sql = "INSERT INTO aluno (nome, telefone) VALUES (?, ?)";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setString(1, aluno.getNome());
            prep.setString(2, aluno.getTelefone());
            prep.executeUpdate();
            sucesso = true;
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar aluno: " + erro.getMessage());
        }
        return sucesso;
    }
    
    public ArrayList<Aluno> listarAlunos(){
        ArrayList<Aluno> lista = new ArrayList<>();
        Connection conn = new conectaDAO().connectDB();
        
        try {
            String sql = "SELECT * FROM aluno";
            PreparedStatement prep = conn.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            
            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setTelefone(rs.getString("telefone"));
                lista.add(aluno);
            }
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro ao listar alunos: " + erro.getMessage());
        }
        return lista;
    }
    
    public Aluno buscarPorNome(String nome){
        Connection conn = new conectaDAO().connectDB();
        
        try {
            String sql = "SELECT * FROM aluno WHERE nome = ?";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setString(1, nome);
            ResultSet rs = prep.executeQuery();
            
            if (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setTelefone(rs.getString("telefone"));
                return aluno;
            }
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro ao buscar aluno: " + erro.getMessage());
        }
        return null;
    }
}