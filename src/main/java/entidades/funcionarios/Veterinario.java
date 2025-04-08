package entidades.funcionarios;

import persistencia.JsonRepository;
import java.util.*;

public class Veterinario extends Funcionario {
    private static final Map<String, Veterinario> veterinarios = new HashMap<>();

    static {
        List<Veterinario> lista = JsonRepository.carregarTodos(Veterinario.class);
        for (Veterinario v : lista) {
            veterinarios.put(v.getCpf(), v);
        }
    }

    public Veterinario(String nome, String cpf, String senha) {
        super(nome, cpf, senha, Cargos.VETERINARIO);
    }

    public static List<Veterinario> getListaVeterinarios() {
        return new ArrayList<>(veterinarios.values());
    }


    @Override
    public void exibirInformacoes() {
        System.out.println("🐾 [VETERINÁRIO] Nome: " + nome + ", CPF: " + cpf + ", Cargo: " + cargo);
    }

    @Override
    public boolean cadastrar() {
        if (veterinarios.containsKey(cpf)) {
            System.out.println("CPF já cadastrado.");
            return false;
        }
        veterinarios.put(cpf, this);
        salvarDados(new ArrayList<>(veterinarios.values()), Veterinario.class);
        System.out.println("Veterinário cadastrado com sucesso.");
        return true;
    }

    @Override
    public boolean atualizar() {
        if (!veterinarios.containsKey(cpf)) {
            System.out.println("Veterinário não encontrado.");
            return false;
        }
        veterinarios.put(cpf, this);
        salvarDados(new ArrayList<>(veterinarios.values()), Veterinario.class);
        System.out.println("Veterinário atualizado.");
        return true;
    }

    @Override
    public boolean remover() {
        if (veterinarios.remove(cpf) != null) {
            salvarDados(new ArrayList<>(veterinarios.values()), Veterinario.class);
            System.out.println("Veterinário removido.");
            return true;
        }
        System.out.println("Veterinário não encontrado.");
        return false;
    }

    @Override
    public void listar() {
        if (veterinarios.isEmpty()) {
            System.out.println("Nenhum veterinário cadastrado.");
            return;
        }
        for (Veterinario v : veterinarios.values()) {
            v.exibirInformacoes();
        }
    }

    public void realizarConsulta(String paciente) {
        System.out.println("Consulta realizada com o paciente: " + paciente);
    }

    public void prescreverMedicamento(String paciente, String medicamento) {
        System.out.println("Medicamento '" + medicamento + "' prescrito para " + paciente);
    }
}
