package service;

import entidades.clientes.Cliente;

public class LoginClienteService {

    public static Cliente login(String usuario, String senha) {
        boolean autenticado = Cliente.login(usuario, senha);

        if (autenticado) {
            for (Cliente cliente : Cliente.getListaClientes()) {
                if (cliente.getUsuario().equals(usuario)) {
                    return cliente;
                }
            }
        }

        return null;
    }
}
