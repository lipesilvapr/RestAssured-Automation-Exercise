package com.automationexercise.api.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuração por ambiente.
 * <ul>
 *   <li>Arquivo: {@code src/test/resources/config/<env>.properties} (padrão: qa)</li>
 *   <li>Qualquer chave pode ser sobrescrita por {@code -Dchave=valor}</li>
 *   <li>{@code -Denv=qa} escolhe o ambiente; {@code -Dapi.log=true} liga o log completo de request/response</li>
 * </ul>
 */
public final class Config {

    private static final Properties PROPS = load();

    private Config() {
    }

    public static String env() {
        return System.getProperty("env", "qa");
    }

    public static String baseUri() {
        return get("base.uri");
    }

    public static String basePath() {
        return get("base.path");
    }

    public static int connectTimeoutMs() {
        return Integer.parseInt(get("http.connect.timeout.ms"));
    }

    public static int readTimeoutMs() {
        return Integer.parseInt(get("http.read.timeout.ms"));
    }

    public static boolean logEnabled() {
        return Boolean.parseBoolean(System.getProperty("api.log", "false"));
    }

    public static String get(String key) {
        String override = System.getProperty(key);
        if (override != null) {
            return override;
        }
        String value = PROPS.getProperty(key);
        if (value == null) {
            throw new IllegalStateException("Chave de configuração ausente: '" + key + "' (env=" + env() + ")");
        }
        return value;
    }

    private static Properties load() {
        String path = "config/" + env() + ".properties";
        Properties props = new Properties();
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream(path)) {
            if (in == null) {
                throw new IllegalStateException("Arquivo de configuração não encontrado no classpath: " + path);
            }
            props.load(in);
        } catch (IOException e) {
            throw new IllegalStateException("Erro lendo " + path, e);
        }
        return props;
    }
}
