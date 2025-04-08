package entidades.funcionarios;

import persistencia.JsonRepository;
import java.util.*;

public class Vendedor extends Funcionario {
	private static final Map<String, Vendedor> vendedores = new HashMap<>();

	static {
		List<Vendedor> lista = JsonRepository.carregarTodos(Vendedor.class);
		for (Vendedor v : lista) {
			vendedores.put(v.getCpf(), v);
		}
	}

	public Vendedor(String nome, String cpf, String senha) {
		super(nome, cpf, senha, Cargos.VENDEDOR);
	}

	public static List<Vendedor> getListaVendedores() {
		return new ArrayList<>(vendedores.values());
	}

	@Override
	public void exibirInformacoes() {
		System.out.println("💰 [VENDEDOR] Nome: " + nome + ", CPF: " + cpf + ", Cargo: " + cargo);
	}

	@Override
	public boolean cadastrar() {
		if (vendedores.containsKey(cpf)) {
			System.out.println("CPF já cadastrado.");
			return false;
		}
		vendedores.put(cpf, this);
		salvarDados(new ArrayList<>(vendedores.values()), Vendedor.class);
		System.out.println("Vendedor cadastrado com sucesso.");
		return true;
	}

	@Override
	public boolean atualizar() {
		if (!vendedores.containsKey(cpf)) {
			System.out.println("Vendedor não encontrado.");
			return false;
		}
		vendedores.put(cpf, this);
		salvarDados(new ArrayList<>(vendedores.values()), Vendedor.class);
		System.out.println("Vendedor atualizado.");
		return true;
	}

	@Override
	public boolean remover() {
		if (vendedores.remove(cpf) != null) {
			salvarDados(new ArrayList<>(vendedores.values()), Vendedor.class);
			System.out.println("Vendedor removido.");
			return true;
		}
		System.out.println("Vendedor não encontrado.");
		return false;
	}

	@Override
	public void listar() {
		if (vendedores.isEmpty()) {
			System.out.println("Nenhum vendedor cadastrado.");
			return;
		}
		for (Vendedor v : vendedores.values()) {
			v.exibirInformacoes();
		}
	}

	// Métodos específicos
	public void registrarVenda(double valor) {
		System.out.println("Venda registrada no valor de R$" + valor);
	}

	public void registrarVenda(String produto, double valor) {
		System.out.println("Venda registrada: " + produto + " - R$" + valor);
	}

	public void criarOrdemServico(String descricao) {
		System.out.println("Ordem de serviço criada: " + descricao);
	}
}
