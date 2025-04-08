package entidades.petshop;

import persistencia.JsonRepository;

import java.util.ArrayList;
import java.util.List;

public class Loja {
    private List<Produto> produtos;

    public Loja() {
        this.produtos = carregarProdutos();
    }

    // === Operações ===

    public void adicionarProduto(Produto produto) {
        produtos.add(produto);
        salvarProdutos();
        System.out.println("✅ Produto adicionado: " + produto);
    }

    public void listarProdutos() {
        if (produtos.isEmpty()) {
            System.out.println("📭 Nenhum produto disponível.");
            return;
        }

        System.out.println("📦 Produtos disponíveis:");
        for (int i = 0; i < produtos.size(); i++) {
            System.out.println((i + 1) + ". " + produtos.get(i));
        }
    }

    public void venderProduto(String nomeProduto) {
        Produto encontrado = produtos.stream()
                .filter(p -> p.getNome().equalsIgnoreCase(nomeProduto))
                .findFirst()
                .orElse(null);

        if (encontrado != null) {
            produtos.remove(encontrado);
            salvarProdutos();
            System.out.println("💸 Venda realizada: " + encontrado);
        } else {
            System.out.println("❌ Produto não encontrado.");
        }
    }

    // === Persistência ===

    private List<Produto> carregarProdutos() {
        List<Produto> lista = JsonRepository.carregarTodos(Produto.class);
        return lista != null ? lista : new ArrayList<>();
    }

    private void salvarProdutos() {
        JsonRepository.salvarTodos(produtos, Produto.class);
    }

    // Getter se precisar acessar os produtos de fora
    public List<Produto> getProdutos() {
        return produtos;
    }
}
