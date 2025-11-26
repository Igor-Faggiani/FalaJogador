package fala.jogador.aluno;

import java.util.Collection;
import java.util.Optional;

public interface RepositorioAluno {

    Aluno salvar(Aluno aluno);
    Optional<Aluno> buscarPorId(Long id);
    Optional<Aluno> buscarPorEmail(String email);
    Optional<Aluno> buscarPorCpf(String cpf);
    boolean existePorCpfEIdDiferente(String cpf, Long id);
    void remover(Long id);
    Collection<Aluno> listarTodos();
}
