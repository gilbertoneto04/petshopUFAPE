package persistencia;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class JsonRepository {
    private static final String CAMINHO_BASE = "src/main/resources/";
    private static final Gson gson = new Gson();

    static {
        // Garante que a pasta resources exista
        File pasta = new File(CAMINHO_BASE);
        if (!pasta.exists()) {
            pasta.mkdirs();
        }
    }

    private static <T> String getCaminhoArquivo(Class<T> classe) {
        return CAMINHO_BASE + classe.getSimpleName().toLowerCase() + ".json";
    }

    public static <T> List<T> carregarTodos(Class<T> classe) {
        String caminho = getCaminhoArquivo(classe);
        Type tipoLista = TypeToken.getParameterized(List.class, classe).getType();

        try (Reader reader = new FileReader(caminho)) {
            List<T> dados = gson.fromJson(reader, tipoLista);
            return dados != null ? dados : new ArrayList<>();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo JSON", e);
        }
    }

    public static <T> void salvarTodos(List<T> lista, Class<T> classe) {
        String caminho = getCaminhoArquivo(classe);
        Type tipoLista = TypeToken.getParameterized(List.class, classe).getType();

        try (Writer writer = new FileWriter(caminho)) {
            gson.toJson(lista, tipoLista, writer);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao escrever no arquivo JSON", e);
        }
    }

    public static <T> void adicionar(T entidade, Class<T> classe) {
        List<T> lista = carregarTodos(classe);
        lista.add(entidade);
        salvarTodos(lista, classe);
    }
}
