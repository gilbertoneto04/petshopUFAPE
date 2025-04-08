package entidades.funcionarios;

import exceptions.CpfNotNullOrEmptyException;
import persistencia.JsonRepository;

import java.util.List;
import java.util.Objects;

public abstract class Funcionario {
    protected String nome;
    protected String cpf;
    protected String senha;
    protected Cargos cargo;

    public Funcionario(String nome, String cpf, String senha, Cargos cargo) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new CpfNotNullOrEmptyException();
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser nula ou vazia.");
        }

        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
        this.cargo = cargo;
    }

    // ===== Métodos obrigatórios que TODA subclasse deve implementar =====
    public abstract void exibirInformacoes();
    public abstract boolean cadastrar();
    public abstract boolean atualizar();
    public abstract boolean remover();
    public abstract void listar();

    // ===== Método comum para persistência =====
    protected <T extends Funcionario> void salvarDados(List<T> funcionarios, Class<T> classe) {
        JsonRepository.salvarTodos(funcionarios, classe);
    }

    // ===== Getters/Setters =====
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            throw new CpfNotNullOrEmptyException();
        }
        this.cpf = cpf;
    }

    public Cargos getCargo() { return cargo; }
    public void setCargo(Cargos cargo) { this.cargo = cargo; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha não pode ser nula ou vazia.");
        }
        this.senha = senha;
    }
}
