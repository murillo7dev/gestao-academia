package br.com.academia.gestao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TreinoVIEW extends JFrame {
    
    private JTextField campoDescricao, campoData, campoAluno;
    private JTable tabela;
    private TreinoDAO treinoDAO = new TreinoDAO();
    private AlunoDAO alunoDAO = new AlunoDAO();
    
    public TreinoVIEW() {
        setTitle("Cadastro de Treino");
        setSize(550, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JLabel titulo = new JLabel("Cadastro de Treino", SwingConstants.CENTER);
        titulo.setFont(new Font("Lucida Fax", Font.BOLD, 18));
        
        JPanel painelForm = new JPanel(new GridLayout(3, 2, 10, 10));
        painelForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        campoDescricao = new JTextField();
        campoData = new JTextField();
        campoAluno = new JTextField();
        painelForm.add(new JLabel("Descrição:"));
        painelForm.add(campoDescricao);
        painelForm.add(new JLabel("Data de Criação:"));
        painelForm.add(campoData);
        painelForm.add(new JLabel("Nome do Aluno:"));
        painelForm.add(campoAluno);
        
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvar());
        
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> this.dispose());
        
        JPanel painelBotoes = new JPanel();
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnVoltar);
        
        tabela = new JTable();
        JScrollPane scroll = new JScrollPane(tabela);
        
        JPanel painelCentro = new JPanel(new BorderLayout(10, 10));
        painelCentro.add(painelForm, BorderLayout.NORTH);
        painelCentro.add(scroll, BorderLayout.CENTER);
        
        setLayout(new BorderLayout(10, 10));
        add(titulo, BorderLayout.NORTH);
        add(painelCentro, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
        
        listar();
    }
    
    private void salvar() {
        String descricao = campoDescricao.getText();
        String data = campoData.getText();
        String nomeAluno = campoAluno.getText();
        
        if (descricao.trim().isEmpty() || data.trim().isEmpty() || nomeAluno.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }
        
        Aluno alunoEncontrado = alunoDAO.buscarPorNome(nomeAluno.trim());
        
        if (alunoEncontrado == null) {
            JOptionPane.showMessageDialog(this, "Aluno não encontrado. Cadastre o aluno primeiro.");
            return;
        }
        
        Treino treino = new Treino(descricao, data, alunoEncontrado);
        boolean sucesso = treinoDAO.cadastrarTreino(treino);
        
        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Treino cadastrado com sucesso!");
            campoDescricao.setText("");
            campoData.setText("");
            campoAluno.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar treino.");
        }
        
        listar();
    }
    
    private void listar() {
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, new String[]{"ID", "Descrição", "Data", "Aluno"});
        for (Treino t : treinoDAO.listarTreinos()) {
            model.addRow(new Object[]{t.getId(), t.getDescricao(), t.getDataCriacao(), t.getAluno().getNome()});
        }
        tabela.setModel(model);
    }
}