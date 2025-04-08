package entidades.petshop;

import java.util.List;

public class RelatorioVendas {
    public static void gerarRelatorioVendas(List<OrdemServico> ordens) {
        System.out.println("=== Relatório de Vendas ===");
        for (OrdemServico ordem : ordens) {
            System.out.println("Cliente: " + ordem.getCliente().getNome() + 
                               " | Número de serviços: " + ordem.getServicos().size() +
                               " | Concluída: " + (ordem.isConcluida() ? "Sim" : "Não"));
        }
    }
}

