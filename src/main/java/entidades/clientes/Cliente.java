package entidades.clientes;

import persistencia.JsonRepository;

import java.util.*;

public class Cliente {
    private String nome;
    private String telefone;
    private String cpf;  // Identificador único
    private String usuario;
    private String senha;

    private static final Map<String, String> clientesRegistrados = new HashMap<>();
    private static final Map<String, Cliente> clientesPorCpf = new HashMap<>();

    static {
        List<Cliente> clientes = JsonRepository.carregarTodos(Cliente.class);
        for (Cliente c : clientes) {
            clientesRegistrados.put(c.usuario, c.senha);
            clientesPorCpf.put(c.cpf, c);
        }
    }

    public Cliente(String nome, String telefone, String cpf, String usuario, String senha) {
        this.nome = nome;
        this.telefone = telefone;
        this.cpf = cpf;
        this.usuario = usuario;
        this.senha = senha;
    }

    // Metodo de cadastro com validações e persistência
    public static void cadastrarCliente(String nome, String telefone, String cpf, String usuario, String senha) {
        if (cpf == null || cpf.trim().isEmpty()) {
            System.out.println("CPF é obrigatório.");
            return;
        }
        if (clientesPorCpf.containsKey(cpf)) {
            System.out.println("CPF já cadastrado.");
            return;
        }

        Cliente cliente = new Cliente(nome, telefone, cpf, usuario, senha);
        clientesRegistrados.put(usuario, senha);
        clientesPorCpf.put(cpf, cliente);

        List<Cliente> todosClientes = new ArrayList<>(clientesPorCpf.values());
        JsonRepository.salvarTodos(todosClientes, Cliente.class);

        System.out.println("Cliente cadastrado com sucesso.");
    }


    // Listar todos os clientes
    public static void listarClientes() {
        List<Cliente> clientes = JsonRepository.carregarTodos(Cliente.class);
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        for (Cliente c : clientes) {
            System.out.println("Nome: " + c.nome + ", CPF: " + c.cpf + ", Telefone: " + c.telefone);
        }
    }

    // Atualizar um cliente
    public static boolean atualizarCliente(String cpf, Cliente novoCliente) {
        List<Cliente> clientes = JsonRepository.carregarTodos(Cliente.class);
        boolean atualizado = false;

        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).cpf.equals(cpf)) {
                clientes.set(i, novoCliente);
                atualizado = true;
                break;
            }
        }

        if (atualizado) {
            clientesRegistrados.put(novoCliente.usuario, novoCliente.senha);
            clientesPorCpf.put(cpf, novoCliente);
            JsonRepository.salvarTodos(clientes, Cliente.class);
            System.out.println("Cliente atualizado com sucesso.");
        } else {
            System.out.println("Cliente com CPF não encontrado.");
        }

        return atualizado;
    }

    // Remover um cliente
    public static boolean removerCliente(String cpf) {
        List<Cliente> clientes = JsonRepository.carregarTodos(Cliente.class);
        boolean removido = clientes.removeIf(c -> c.cpf.equals(cpf));

        if (removido) {
            Cliente c = clientesPorCpf.remove(cpf);
            if (c != null) clientesRegistrados.remove(c.usuario);
            JsonRepository.salvarTodos(clientes, Cliente.class);
            System.out.println("Cliente removido com sucesso.");
        } else {
            System.out.println("Cliente com CPF não encontrado.");
        }

        return removido;
    }

    // Exibir informações de um cliente
    public static void listarInformacoes(String cpf) {
        Cliente cliente = clientesPorCpf.get(cpf);
        if (cliente != null) {
            System.out.println("Nome: " + cliente.nome);
            System.out.println("Telefone: " + cliente.telefone);
            System.out.println("CPF: " + cliente.cpf);
            System.out.println("Usuário: " + cliente.usuario);
        } else {
            System.out.println("Cliente não encontrado.");
        }
    }

    public static List<Cliente> getListaClientes() {
        return new ArrayList<>(clientesPorCpf.values());
    }

    // Login
    public static boolean login(String usuario, String senha) {
        if (clientesRegistrados.containsKey(usuario)) {
            String senhaArmazenada = clientesRegistrados.get(usuario);
            if (senhaArmazenada.equals(senha)) {
                System.out.println("Login bem-sucedido!");
                return true;
            }
        }
        System.out.println("Usuário ou senha incorretos.");
        return false;
    }

    // Buscar cliente por CPF
    public static Cliente getClientePorCpf(String cpf) {
        return clientesPorCpf.get(cpf);
    }

    // Getters
    public String getNome() { return nome; }
    public String getTelefone() { return telefone; }
    public String getCpf() { return cpf; }
    public String getUsuario() { return usuario; }
    public String getSenha() { return senha; }
}
