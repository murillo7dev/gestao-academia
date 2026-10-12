package br.com.academia.gestao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AlunoVIEW extends JFrame {
    
    private JTextField campoNome, campoTelefone;
    private JTable tabela;
    private AlunoDAO alunoDAO = new AlunoDAO();
    
    public AlunoVIEW() {
        setTitle("Cadastro de Aluno");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JLabel titulo = new JLabel("Cadastro de Aluno", SwingConstants.CENTER);
        titulo.setFont(new Font("Lucida Fax", Font.BOLD, 18));
        
        JPanel painelForm = new JPanel(new GridLayout(2, 2, 10, 10));
        painelForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        campoNome = new JTextField();
        campoTelefone = new JTextField();
        painelForm.add(new JLabel("Nome:"));
        painelForm.add(campoNome);
        painelForm.add(new JLabel("Telefone:"));
        painelForm.add(campoTelefone);
        
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
        String nome = campoNome.getText();
        String telefone = campoTelefone.getText();
        
        if (nome.trim().isEmpty() || telefone.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }
        
        Aluno aluno = new Aluno(nome, telefone);
        boolean sucesso = alunoDAO.cadastrarAluno(aluno);
        
        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso!");
            campoNome.setText("");
            campoTelefone.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar aluno.");
        }
        
        listar();
    }
    
    private void listar() {
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, new String[]{"ID", "Nome", "Telefone"});
        for (Aluno a : alunoDAO.listarAlunos()) {
            model.addRow(new Object[]{a.getId(), a.getNome(), a.getTelefone()});
        }
        tabela.setModel(model);
    }
}