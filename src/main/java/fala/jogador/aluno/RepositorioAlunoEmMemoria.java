package fala.jogador.aluno;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class RepositorioAlunoEmMemoria implements RepositorioAluno {

    private final Map<Long, Aluno> banco = new HashMap<>();
    private final AtomicLong geradorId = new AtomicLong(1);

    @Override
    public Aluno salvar(Aluno aluno) {
        if (aluno.getId() == null) {
            aluno.setId(geradorId.getAndIncrement());
        }
        banco.put(aluno.getId(), aluno);
        return aluno;
    }

    @Override
    public Optional<Aluno> buscarPorId(Long id) {
        return Optional.ofNullable(banco.get(id));
    }

    @Override
    public Optional<Aluno> buscarPorEmail(String email) {
        return banco.values().stream()
                .filter(a -> a.getEmail() != null &&
                        a.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    @Override
    public Optional<Aluno> buscarPorCpf(String cpf) {
        return banco.values().stream()
                .filter(a -> a.getCpf() != null &&
                        a.getCpf().equals(cpf))
                .findFirst();
    }

    @Override
    public boolean existePorCpfEIdDiferente(String cpf, Long id) {
        return banco.values().stream()
                .anyMatch(a -> a.getCpf() != null &&
                        a.getCpf().equals(cpf) &&
                        !Objects.equals(a.getId(), id));
    }

    @Override
    public void remover(Long id) {
        banco.remove(id);
    }

    @Override
    public Collection<Aluno> listarTodos() {
        return new ArrayList<>(banco.values());
    }
}