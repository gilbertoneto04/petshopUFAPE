package service;

import entidades.funcionarios.Administrador;
import entidades.funcionarios.Vendedor;
import entidades.funcionarios.Veterinario;
import entidades.funcionarios.Funcionario;

public class LoginFuncionarioService {

    public static Funcionario login(String cpf, String senha) {
        Funcionario funcionario = buscarFuncionario(cpf);

        if (funcionario != null && funcionario.getSenha().equals(senha)) {
            System.out.println("✅ Login realizado com sucesso! Bem-vindo, " + funcionario.getNome());
            return funcionario;
        }

        System.out.println("❌ CPF ou senha inválidos.");
        return null;
    }

    private static Funcionario buscarFuncionario(String cpf) {
        // Verifica se é um Administrador
        for (Administrador admin : Administrador.getListaAdministradores()) {
            if (admin.getCpf().equals(cpf)) return admin;
        }

        // Verifica se é um Vendedor
        for (Vendedor vendedor : Vendedor.getListaVendedores()) {
            if (vendedor.getCpf().equals(cpf)) return vendedor;
        }

        // Verifica se é um Veterinário
        for (Veterinario vet : Veterinario.getListaVeterinarios()) {
            if (vet.getCpf().equals(cpf)) return vet;
        }

        return null;
    }
}
