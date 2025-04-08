package entidades.petshop;

public class Servico {
	private String descricao;
	private double preco;
	private TipoServico tipo;

	public Servico(String descricao, double preco, TipoServico tipo) {
		this.descricao = descricao;
		this.preco = preco;
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "[" + tipo + "] " + descricao + " - R$" + String.format("%.2f", preco);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public TipoServico getTipo() {
		return tipo;
	}

	public void setTipo(TipoServico tipo) {
		this.tipo = tipo;
	}
}


