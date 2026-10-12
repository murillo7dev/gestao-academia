package br.com.academia.gestao;
import javax.swing.*;
import java.awt.*;

public class MenuVIEW extends JFrame {
    
    public MenuVIEW() {
        setTitle("Sistema de Gestão de Academia");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel titulo = new JLabel("Sistema de Gestão de Academia", SwingConstants.CENTER);
        titulo.setFont(new Font("Lucida Fax", Font.BOLD, 16));
        
        JButton btnAluno = new JButton("Cadastro de Alunos");
        JButton btnPlano = new JButton("Cadastro de Planos");
        JButton btnTreino = new JButton("Cadastro de Treinos");
        
        btnAluno.addActionListener(e -> new AlunoVIEW().setVisible(true));
        btnPlano.addActionListener(e -> new PlanoVIEW().setVisible(true));
        btnTreino.addActionListener(e -> new TreinoVIEW().setVisible(true));
        
        JPanel painel = new JPanel(new GridLayout(3, 1, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));
        painel.add(btnAluno);
        painel.add(btnPlano);
        painel.add(btnTreino);
        
        setLayout(new BorderLayout(10, 10));
        add(titulo, BorderLayout.NORTH);
        add(painel, BorderLayout.CENTER);
    }
    
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new MenuVIEW().setVisible(true));
    }
}