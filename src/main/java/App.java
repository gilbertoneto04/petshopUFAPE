import entidades.clientes.Cliente;
import entidades.funcionarios.*;
import service.LoginFuncionarioService;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean executando = true;

        while (executando) {
            System.out.println("\n=== CLÍNICA VETERINÁRIA ===");
            System.out.println("1. Login como Funcionário");
            System.out.println("2. Login como Cliente");
            System.out.println("3. Cadastrar Cliente");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            String escolha = sc.nextLine();

            switch (escolha) {
                case "1":
                    System.out.print("Digite o CPF: ");
                    String cpfFunc = sc.nextLine();
                    System.out.print("Digite a senha: ");
                    String senhaFunc = sc.nextLine();
                    Funcionario funcionario = LoginFuncionarioService.login(cpfFunc, senhaFunc);

                    if (funcionario != null) {
                        menuFuncionario(funcionario, sc);
                    }
                    break;

                case "2":
                    System.out.print("Digite o usuário: ");
                    String usuario = sc.nextLine();
                    System.out.print("Digite a senha: ");
                    String senhaCliente = sc.nextLine();
                    if (Cliente.login(usuario, senhaCliente)) {
                        System.out.println("Bem-vindo, cliente!");
                        // Aqui você pode colocar mais funcionalidades para o cliente se quiser
                    }
                    break;

                case "3":
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("Telefone: ");
                    String telefone = sc.nextLine();
                    System.out.print("CPF: ");
                    String cpf = sc.nextLine();
                    System.out.print("Usuário: ");
                    String user = sc.nextLine();
                    System.out.print("Senha: ");
                    String senha = sc.nextLine();
                    Cliente.cadastrarCliente(nome, telefone, cpf, user, senha);
                    break;

                case "0":
                    executando = false;
                    break;

                default:
                    System.out.println("Opção inválida.");
            }
        }

        sc.close();
        System.out.println("Sistema encerrado.");
    }

    private static void menuFuncionario(Funcionario f, Scanner sc) {
        boolean logado = true;
        while (logado) {
            System.out.println("\n== MENU FUNCIONÁRIO ==");
            System.out.println("1. Listar Funcionários");
            System.out.println("2. Atualizar Dados");
            System.out.println("3. Remover");
            System.out.println("0. Sair");
            System.out.print("Escolha: ");
            String escolha = sc.nextLine();

            switch (escolha) {
                case "1":
                    f.listar();
                    break;
                case "2":
                    // Aqui você pode pedir novos dados e chamar f.atualizar()
                    System.out.println("Atualização não implementada aqui ainda.");
                    break;
                case "3":
                    f.remover();
                    logado = false;
                    break;
                case "0":
                    logado = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}
