package fala.jogador;

import fala.jogador.aluno.*;
import fala.jogador.autenticacao.*;
import fala.jogador.desempenho.*;
import fala.jogador.feedback.*;
import fala.jogador.sincronizacao.*;
import fala.jogador.treino.*;

import java.util.List;

class ServidorRemotoSimulacao implements ServidorRemoto {
    @Override
    public boolean enviar(ItemOffline item) {
        System.out.println("   [Simulação] Enviando item ID " + item.getId() + " para o servidor...");
        return true;
    }
    @Override
    public List<ItemOffline> buscarAtualizados(List<Long> idsLocais) {
        System.out.println("   [Simulação] Buscando atualizações no servidor...");
        return List.of();
    }
}

public class App {

    public static void main(String[] args) {

        System.out.println("\n===== INICIANDO SISTEMA FalaJogador =====\n");

        // ------------------------------------------
        //   Inicialização dos repositórios
        // ------------------------------------------
        RepositorioAluno repoAluno = new RepositorioAlunoEmMemoria();
        RepositorioUsuario repoUsuario = new RepositorioUsuarioEmMemoria();
        RepositorioTreino repoTreino = new RepositorioTreinoEmMemoria();
        RepositorioAtribuicaoTreino repoAtribuicao = new RepositorioAtribuicaoTreinoEmMemoria();

        RepositorioFeedback repoFeedback = new RepositorioFeedbackEmMemoria();
        RepositorioAvaliacaoFisica repoAvaliacao = new RepositorioAvaliacaoFisicaEmMemoria();

        UtilCriptografia cript = new UtilCriptografia();
        UtilIMC utilIMC = new UtilIMC();

        // ------------------------------------------
        //   Criação de serviços
        // ------------------------------------------
        ServicoAluno servAluno = new ServicoAluno(repoAluno, cript);
        ServicoAutenticacao servAuth = new ServicoAutenticacao(repoUsuario, cript);
        ServicoTreino servTreino = new ServicoTreino(repoTreino, repoAluno, repoAtribuicao);
        ServicoAvaliacaoFisica servAvaliacao = new ServicoAvaliacaoFisica(repoAvaliacao, repoAluno, utilIMC);
        ServicoFeedback servFeedback = new ServicoFeedback(repoFeedback, repoAluno);

        ServidorRemoto servRemoto = new ServidorRemotoSimulacao();
        ServicoSincronizacao servSync = new ServicoSincronizacao(servRemoto);


        // ------------------------------------------
        //   Execução de Demonstrações
        // ------------------------------------------

        System.out.println("→ Cadastrando aluno (no RepositorioAluno)…");
        Aluno aluno = servAluno.cadastrarAluno(
                "João da Silva",
                "12345678900",
                "joao@email.com",
                "999999999",
                "Rua A, 100",
                "senha123"
        );
        System.out.println("Aluno cadastrado: ID = " + aluno.getId() +
                "\nNome: " + aluno.getNome() +
                "\nCPF: " + aluno.getCpf());

        // Linha perdida que precisa ser re-inserida
        System.out.println("\n→ Registrando credenciais (no RepositorioUsuario)…");
        servAuth.registrarUsuario(aluno.getEmail(), "senha123");


        System.out.println("\n→ Testando login…");
        boolean loginSucesso = servAuth.login(aluno.getEmail(), "senha123");
        System.out.println("Login bem-sucedido? " + loginSucesso);


        System.out.println("\n→ Criando treino…");
        Treino treino = new Treino(null, "Treino de Peito e Tríceps", 60, 1L);
        treino.adicionarExercicio(new Exercicio("Supino Reto", 4, 10));
        treino.adicionarExercicio(new Exercicio("Tríceps Corda", 3, 12));
        treino = servTreino.criarTreino(treino);

        System.out.println("Treino criado: ID = " + treino.getId());


        System.out.println("\n→ Atribuindo treino para aluno…");
        servTreino.atribuirTreinoParaAlunos(treino.getId(), List.of(aluno.getId()));
        System.out.println("Treino atribuído!");


        System.out.println("\n→ Concluindo exercício (Teste Crítico: ID existe)…");
        Exercicio ex = treino.getExercicios().get(0);
        servTreino.marcarExercicioConcluido(ex.getId(), true);

        System.out.println("Exercício '" + ex.getNome() + "' concluído? " + ex.isConcluido());


        System.out.println("\n→ Avaliação física (Atualiza Desempenho)…");
        servAvaliacao.salvarAvaliacao(
                aluno.getId(),
                80.0,
                1.80,
                18.0,
                "Primeira avaliação"
        );

        System.out.println("IMC atual do João: " +
                repoAvaliacao.listarPorAluno(aluno.getId()).get(0).getImc());


        System.out.println("\n→ Enviando feedback (Teste Crítico: Aluno existe)…");
        servFeedback.enviarFeedback(aluno.getId(), "Treino excelente!");

        System.out.println("Feedbacks do aluno: " +
                repoFeedback.listarPorAluno(aluno.getId()).size());


        System.out.println("\n===== FIM DO SISTEMA FalaJogador =====");
    }
}