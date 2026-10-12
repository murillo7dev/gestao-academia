package br.com.academia.gestao;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class PlanoDAO {
    
    public boolean cadastrarPlano(Plano plano){
        boolean sucesso = false;
        Connection conn = new conectaDAO().connectDB();
        
        try {
            String sql = "INSERT INTO plano (tipo_plano, valor, data_vencimento, aluno_id) VALUES (?, ?, ?, ?)";
            PreparedStatement prep = conn.prepareStatement(sql);
            prep.setString(1, plano.getTipoPlano());
            prep.setDouble(2, plano.getValor());
            prep.setString(3, plano.getDataVencimento());
            prep.setInt(4, plano.getAluno().getId());
            prep.executeUpdate();
            sucesso = true;
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar plano: " + erro.getMessage());
        }
        return sucesso;
    }
    
    public ArrayList<Plano> listarPlanos(){
        ArrayList<Plano> lista = new ArrayList<>();
        Connection conn = new conectaDAO().connectDB();
        
        try {
            String sql = "SELECT p.id, p.tipo_plano, p.valor, p.data_vencimento, a.id as aluno_id, a.nome, a.telefone " +
                         "FROM plano p JOIN aluno a ON p.aluno_id = a.id";
            PreparedStatement prep = conn.prepareStatement(sql);
            ResultSet rs = prep.executeQuery();
            
            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("aluno_id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setTelefone(rs.getString("telefone"));
                
                Plano plano = new Plano();
                plano.setId(rs.getInt("id"));
                plano.setTipoPlano(rs.getString("tipo_plano"));
                plano.setValor(rs.getDouble("valor"));
                plano.setDataVencimento(rs.getString("data_vencimento"));
                plano.setAluno(aluno);
                lista.add(plano);
            }
        } catch (SQLException erro){
            JOptionPane.showMessageDialog(null, "Erro ao listar planos: " + erro.getMessage());
        }
        return lista;
    }
}