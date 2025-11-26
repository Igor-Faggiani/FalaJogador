package fala.jogador.aluno;

import fala.jogador.autenticacao.UtilCriptografia;

import java.util.Optional;
import java.util.regex.Pattern;

public class ServicoAluno {

    private final RepositorioAluno repositorio;
    private final UtilCriptografia utilCriptografia;
    private final Pattern padraoEmail =
            Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    public ServicoAluno(RepositorioAluno repositorio,
                        UtilCriptografia utilCriptografia) {
        this.repositorio = repositorio;
        this.utilCriptografia = utilCriptografia;
    }

    public Aluno cadastrarAluno(String nome, String cpf, String email,
                                String telefone, String endereco,
                                String senhaPura) {

        if (nome == null || nome.trim().isEmpty()
                || email == null || email.trim().isEmpty()) {
            throw new ExcecaoCampoObrigatorio("Nome e e-mail são obrigatórios.");
        }

        String emailTrimmed = email.trim();
        String nomeTrimmed = nome.trim();

        if (!padraoEmail.matcher(emailTrimmed).matches()) {
            throw new ExcecaoFormatoEmailInvalido("Formato de e-mail inválido.");
        }

        if (repositorio.buscarPorEmail(emailTrimmed).isPresent()) {
            throw new ExcecaoCampoDuplicado("E-mail já cadastrado.");
        }
        if (cpf != null && !cpf.trim().isEmpty() && repositorio.buscarPorCpf(cpf).isPresent()) {
            throw new ExcecaoCampoDuplicado("CPF já cadastrado.");
        }

        String hash = utilCriptografia.gerarHashSenha(senhaPura);

        Aluno aluno = new Aluno(null, nomeTrimmed, cpf, emailTrimmed,
                telefone, endereco, hash);

        return repositorio.salvar(aluno);
    }

    public void atualizarAluno(Aluno aluno) {
        if (aluno.getId() == null) {
            throw new IllegalArgumentException("ID do aluno não pode ser nulo para atualização.");
        }

        repositorio.buscarPorId(aluno.getId())
                .orElseThrow(() -> new ExcecaoRegistroNaoEncontrado("Aluno não encontrado para atualização."));

        Optional<Aluno> conflitoEmail = repositorio.buscarPorEmail(aluno.getEmail());
        if (conflitoEmail.isPresent() && !conflitoEmail.get().getId().equals(aluno.getId())) {
            throw new ExcecaoCampoDuplicado("E-mail já cadastrado para outro aluno.");
        }

        if (aluno.getCpf() != null && repositorio.existePorCpfEIdDiferente(aluno.getCpf(), aluno.getId())) {
            throw new ExcecaoCampoDuplicado("CPF já cadastrado para outro aluno.");
        }

        repositorio.salvar(aluno);
    }

    public void excluirAluno(Long idAluno, boolean confirmar) {
        if (!confirmar) {
            throw new IllegalStateException("Exclusão requer confirmação.");
        }

        Aluno existente = repositorio.buscarPorId(idAluno)
                .orElseThrow(() ->
                        new ExcecaoRegistroNaoEncontrado("Aluno não encontrado."));

        repositorio.remover(existente.getId());
    }
}