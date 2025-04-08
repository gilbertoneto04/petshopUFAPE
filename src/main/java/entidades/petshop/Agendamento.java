package entidades.petshop;

import entidades.clientes.Paciente;
import entidades.funcionarios.Veterinario;
import persistencia.JsonRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Agendamento {
    private static final String MSG_SERVICO_INVALIDO = "❌ Serviço inválido! Agendamentos só podem ser do tipo CONSULTA ou EXAME.";
    private static final String MSG_CONFLITO_HORARIO = "⚠️ Veterinário já tem agendamento nesse horário.";
    private static final String MSG_AGENDAMENTO_REALIZADO = "✅ Agendamento realizado.";
    private static final String MSG_AGENDAMENTO_ATUALIZADO = "✅ Agendamento atualizado.";
    private static final String MSG_AGENDAMENTO_NAO_ENCONTRADO = "❌ Agendamento não encontrado.";
    private static final String MSG_AGENDAMENTO_REMOVIDO = "✅ Agendamento removido.";
    private static final String MSG_NENHUM_AGENDAMENTO = "📭 Nenhum agendamento encontrado.";

    private LocalDateTime dataHora;
    private Veterinario veterinario;
    private Servico servico;

    private Consulta consulta;
    private Exame exame;

    public Agendamento(LocalDateTime dataHora, Veterinario veterinario, Servico servico, Paciente paciente) {
        validarServico(servico);
        validarDataHora(dataHora);
        Objects.requireNonNull(veterinario, "Veterinário não pode ser nulo");
        Objects.requireNonNull(paciente, "Paciente não pode ser nulo");

        this.dataHora = dataHora;
        this.veterinario = veterinario;
        this.servico = servico;

        if (servico.getTipo() == TipoServico.CONSULTA) {
            this.consulta = new Consulta(paciente, veterinario, "AGENDADO");
        } else if (servico.getTipo() == TipoServico.EXAME) {
            this.exame = new Exame(paciente, veterinario, "AGENDADO");
        }
    }

    private void validarServico(Servico servico) {
        Objects.requireNonNull(servico, "Serviço não pode ser nulo");
        if (servico.getTipo() != TipoServico.CONSULTA && servico.getTipo() != TipoServico.EXAME) {
            throw new IllegalArgumentException(MSG_SERVICO_INVALIDO);
        }
    }

    private void validarDataHora(LocalDateTime dataHora) {
        Objects.requireNonNull(dataHora, "Data/Hora não pode ser nula");
        if (dataHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("❌ Data/hora não pode ser no passado.");
        }
    }

    // === CRUD ===

    public boolean cadastrar() {
        List<Agendamento> agendamentos = carregarTodos();

        if (existeConflitoAgendamento(agendamentos)) {
            System.out.println(MSG_CONFLITO_HORARIO);
            return false;
        }

        agendamentos.add(this);
        salvarTodos(agendamentos);
        System.out.println(MSG_AGENDAMENTO_REALIZADO);
        return true;
    }

    private boolean existeConflitoAgendamento(List<Agendamento> agendamentos) {
        return agendamentos.stream().anyMatch(a ->
                a.getDataHora().equals(this.dataHora) &&
                        a.getVeterinario().getCpf().equals(this.veterinario.getCpf())
        );
    }

    public boolean atualizar(LocalDateTime novaDataHora) {
        validarDataHora(novaDataHora);
        List<Agendamento> agendamentos = carregarTodos();

        for (Agendamento a : agendamentos) {
            if (a.equals(this)) {
                a.setDataHora(novaDataHora);
                salvarTodos(agendamentos);
                System.out.println(MSG_AGENDAMENTO_ATUALIZADO);
                return true;
            }
        }

        System.out.println(MSG_AGENDAMENTO_NAO_ENCONTRADO);
        return false;
    }

    public boolean remover() {
        List<Agendamento> agendamentos = carregarTodos();
        boolean removido = agendamentos.removeIf(a -> a.equals(this));

        if (removido) {
            salvarTodos(agendamentos);
            System.out.println(MSG_AGENDAMENTO_REMOVIDO);
        } else {
            System.out.println(MSG_AGENDAMENTO_NAO_ENCONTRADO);
        }

        return removido;
    }

    public static void listarTodos() {
        List<Agendamento> agendamentos = carregarTodos();
        if (agendamentos.isEmpty()) {
            System.out.println(MSG_NENHUM_AGENDAMENTO);
        } else {
            agendamentos.forEach(Agendamento::imprimirAgendamento);
        }
    }

    private static void imprimirAgendamento(Agendamento a) {
        String paciente = a.getConsulta() != null
                ? a.getConsulta().getPaciente().getNome()
                : a.getExame() != null ? a.getExame().getPaciente().getNome() : "N/A";

        System.out.printf("📅 %s | Tipo: %s | %s | Vet: %s | Paciente: %s%n",
                a.getDataHora(),
                a.getServico().getTipo(),
                a.getServico().getDescricao(),
                a.getVeterinario().getNome(),
                paciente);
    }

    // === Persistência ===

    public static List<Agendamento> carregarTodos() {
        return JsonRepository.carregarTodos(Agendamento.class);
    }

    public static void salvarTodos(List<Agendamento> lista) {
        JsonRepository.salvarTodos(lista, Agendamento.class);
    }

    // === Getters/Setters ===

    public LocalDateTime getDataHora() { return dataHora; }

    public void setDataHora(LocalDateTime dataHora) {
        validarDataHora(dataHora);
        this.dataHora = dataHora;
    }

    public Veterinario getVeterinario() { return veterinario; }

    public void setVeterinario(Veterinario veterinario) {
        Objects.requireNonNull(veterinario, "Veterinário não pode ser nulo");
        this.veterinario = veterinario;
    }

    public Servico getServico() { return servico; }

    public void setServico(Servico servico) {
        validarServico(servico);
        this.servico = servico;
    }

    public Consulta getConsulta() { return consulta; }

    public Exame getExame() { return exame; }

    // === Equals/Hash ===

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Agendamento other)) return false;
        return dataHora.equals(other.dataHora) &&
                veterinario.getCpf().equals(other.veterinario.getCpf());
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataHora, veterinario.getCpf());
    }

    @Override
    public String toString() {
        return String.format("Agendamento[dataHora=%s, veterinario=%s, servico=%s]",
                dataHora, veterinario.getNome(), servico.getTipo());
    }
}
