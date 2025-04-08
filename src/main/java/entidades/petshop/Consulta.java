package entidades.petshop;

import entidades.clientes.Paciente;
import entidades.funcionarios.Veterinario;

public class Consulta {
    private Paciente paciente;
    private Veterinario veterinario;
    private String status;

    public Consulta(Paciente paciente, Veterinario veterinario, String status) {
        this.paciente = paciente;
        this.veterinario = veterinario;
        this.status = status;
    }

    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }

    public Veterinario getVeterinario() { return veterinario; }
    public void setVeterinario(Veterinario veterinario) { this.veterinario = veterinario; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
