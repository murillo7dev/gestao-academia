package br.com.academia.gestao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PlanoVIEW extends JFrame {
    
    private JTextField campoTipo, campoValor, campoVencimento, campoAluno;
    private JTable tabela;
    private PlanoDAO planoDAO = new PlanoDAO();
    private AlunoDAO alunoDAO = new AlunoDAO();
    
    public PlanoVIEW() {
        setTitle("Cadastro de Plano");
        setSize(550, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JLabel titulo = new JLabel("Cadastro de Plano", SwingConstants.CENTER);
        titulo.setFont(new Font("Lucida Fax", Font.BOLD, 18));
        
        JPanel painelForm = new JPanel(new GridLayout(4, 2, 10, 10));
        painelForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        campoTipo = new JTextField();
        campoValor = new JTextField();
        campoVencimento = new JTextField();
        campoAluno = new JTextField();
        painelForm.add(new JLabel("Tipo do Plano:"));
        painelForm.add(campoTipo);
        painelForm.add(new JLabel("Valor:"));
        painelForm.add(campoValor);
        painelForm.add(new JLabel("Data de Vencimento:"));
        painelForm.add(campoVencimento);
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
        String tipo = campoTipo.getText();
        String valorTexto = campoValor.getText();
        String vencimento = campoVencimento.getText();
        String nomeAluno = campoAluno.getText();
        
        if (tipo.trim().isEmpty() || valorTexto.trim().isEmpty() || vencimento.trim().isEmpty() || nomeAluno.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }
        
        Aluno alunoEncontrado = alunoDAO.buscarPorNome(nomeAluno.trim());
        
        if (alunoEncontrado == null) {
            JOptionPane.showMessageDialog(this, "Aluno não encontrado. Cadastre o aluno primeiro.");
            return;
        }
        
        double valor;
        try {
            valor = Double.parseDouble(valorTexto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Valor inválido.");
            return;
        }
        
        Plano plano = new Plano(tipo, valor, vencimento, alunoEncontrado);
        boolean sucesso = planoDAO.cadastrarPlano(plano);
        
        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Plano cadastrado com sucesso!");
            campoTipo.setText("");
            campoValor.setText("");
            campoVencimento.setText("");
            campoAluno.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar plano.");
        }
        
        listar();
    }
    
    private void listar() {
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, new String[]{"ID", "Tipo", "Valor", "Vencimento", "Aluno"});
        for (Plano p : planoDAO.listarPlanos()) {
            model.addRow(new Object[]{p.getId(), p.getTipoPlano(), p.getValor(), p.getDataVencimento(), p.getAluno().getNome()});
        }
        tabela.setModel(model);
    }
}