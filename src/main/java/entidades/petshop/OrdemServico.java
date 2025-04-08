package entidades.petshop;

import entidades.clientes.Cliente;
import persistencia.JsonRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrdemServico {
	private Cliente cliente;
	private List<Servico> servicos;
	private boolean concluida;
	private LocalDateTime data;

	public OrdemServico(Cliente cliente) {
		Objects.requireNonNull(cliente, "Cliente não pode ser nulo");
		this.cliente = cliente;
		this.servicos = new ArrayList<>();
		this.concluida = false;
		this.data = LocalDateTime.now();
	}

	public void adicionarServico(Servico servico) {
		Objects.requireNonNull(servico, "Serviço não pode ser nulo");
		servicos.add(servico);
	}

	public void pagarOrdem() {
		if (servicos.isEmpty()) {
			System.out.println("❌ Não é possível pagar uma ordem sem serviços.");
			return;
		}

		if (concluida) {
			System.out.println("⚠️ Ordem já foi paga anteriormente.");
			return;
		}

		System.out.printf("💳 Processando pagamento de R$ %.2f...%n", calcularValorTotal());

		this.concluida = true;
		salvar();
		System.out.println("✅ Pagamento realizado com sucesso. Ordem concluída!");
		imprimirRecibo();
	}


	public double calcularValorTotal() {
		return servicos.stream()
				.mapToDouble(Servico::getPreco)
				.sum();
	}

	public void imprimirRecibo() {
		if (!concluida) {
			System.out.println("⚠️ Ordem ainda não foi concluída.");
			return;
		}

		System.out.println("\n🧾 === Recibo da Ordem de Serviço ===");
		System.out.println("Cliente: " + cliente.getNome());
		System.out.println("Data: " + data);
		System.out.println("\nServiços realizados:");
		for (Servico s : servicos) {
			System.out.printf("- [%s] %s: R$ %.2f%n", s.getTipo(), s.getDescricao(), s.getPreco());
		}
		System.out.printf("\n💰 Total: R$ %.2f%n", calcularValorTotal());
		System.out.println("====================================\n");
	}

	// === Persistência simples ===

	public void salvar() {
		List<OrdemServico> todas = carregarTodas();
		todas.add(this);
		JsonRepository.salvarTodos(todas, OrdemServico.class);
	}

	public static List<OrdemServico> carregarTodas() {
		List<OrdemServico> lista = JsonRepository.carregarTodos(OrdemServico.class);
		return lista != null ? lista : new ArrayList<>();
	}

	public static void listarTodas() {
		List<OrdemServico> todas = carregarTodas();
		if (todas.isEmpty()) {
			System.out.println("📭 Nenhuma ordem de serviço encontrada.");
			return;
		}
		for (OrdemServico ordem : todas) {
			ordem.imprimirRecibo();
		}
	}

	// === Getters e Setters ===

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	public boolean isConcluida() {
		return concluida;
	}

	public void setConcluida(boolean concluida) {
		this.concluida = concluida;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}
}
