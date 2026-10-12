package gestaoacademia;

import br.com.academia.gestao.Aluno;
import br.com.academia.gestao.Plano;
import br.com.academia.gestao.Treino;

public class GestaoAcademia {

    public static void main(String[] args) {
        Aluno aluno = new Aluno("João Silva", "11999990000");
        aluno.setId(1);

        System.out.println("=== Cadastro de Aluno ===");
        System.out.println("ID: " + aluno.getId());
        System.out.println("Nome: " + aluno.getNome());
        System.out.println("Telefone: " + aluno.getTelefone());

        Plano plano = new Plano("Mensal", 120.00, "10/10/2026", aluno);
        plano.setId(1);

        System.out.println("\n=== Cadastro de Plano ===");
        System.out.println("ID: " + plano.getId());
        System.out.println("Tipo: " + plano.getTipoPlano());
        System.out.println("Valor: R$ " + plano.getValor());
        System.out.println("Vencimento: " + plano.getDataVencimento());
        System.out.println("Aluno vinculado: " + plano.getAluno().getNome());

        Treino treino = new Treino("Treino de força", "05/09/2026", aluno);
        treino.setId(1);

        System.out.println("\n=== Cadastro de Treino ===");
        System.out.println("ID: " + treino.getId());
        System.out.println("Descrição: " + treino.getDescricao());
        System.out.println("Data de criação: " + treino.getDataCriacao());
        System.out.println("Aluno vinculado: " + treino.getAluno().getNome());
    }
}