package entidades.funcionarios;

import persistencia.JsonRepository;
import java.util.*;

public class Administrador extends Funcionario {
    private static final Map<String, Administrador> administradores = new HashMap<>();

    static {
        List<Administrador> lista = JsonRepository.carregarTodos(Administrador.class);
        for (Administrador a : lista) {
            administradores.put(a.getCpf(), a);
        }
    }

    public Administrador(String nome, String cpf, String senha) {
        super(nome, cpf, senha, Cargos.ADMINISTRADOR);
    }

    @Override
    public void exibirInformacoes() {
        System.out.println("🔒 [ADMIN] Nome: " + nome + ", CPF: " + cpf + ", Cargo: " + cargo);
    }

    public static List<Administrador> getListaAdministradores() {
        return new ArrayList<>(administradores.values());
    }


    @Override
    public boolean cadastrar() {
        if (administradores.containsKey(cpf)) {
            System.out.println("CPF já cadastrado.");
            return false;
        }
        administradores.put(cpf, this);
        salvarDados(new ArrayList<>(administradores.values()), Administrador.class);
        System.out.println("Administrador cadastrado com sucesso.");
        return true;
    }

    @Override
    public boolean atualizar() {
        if (!administradores.containsKey(cpf)) {
            System.out.println("Administrador não encontrado.");
            return false;
        }
        administradores.put(cpf, this);
        salvarDados(new ArrayList<>(administradores.values()), Administrador.class);
        System.out.println("Administrador atualizado.");
        return true;
    }

    @Override
    public boolean remover() {
        if (administradores.remove(cpf) != null) {
            salvarDados(new ArrayList<>(administradores.values()), Administrador.class);
            System.out.println("Administrador removido.");
            return true;
        }
        System.out.println("Administrador não encontrado.");
        return false;
    }

    @Override
    public void listar() {
        if (administradores.isEmpty()) {
            System.out.println("Nenhum administrador cadastrado.");
            return;
        }
        for (Administrador a : administradores.values()) {
            a.exibirInformacoes();
        }
    }
}
