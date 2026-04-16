package portal.goldenmaster;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads test configuration from {@code esco-test.properties} with system-property overrides.
 */
public final class TestConfig {

    private static final TestConfig INSTANCE = new TestConfig();

    private final String baseUrl;
    private final String datasetVersion;
    private final String mode;
    private final long throttleMs;
    private final int performanceThresholdPct;
    private final String cookie;

    private TestConfig() {
        Properties props = new Properties();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("esco-test.properties")) {
            if (in != null) {
                props.load(in);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load esco-test.properties", e);
        }

        this.baseUrl = resolve(props, "esco.api.baseUrl", "https://esco-esp-dev-portal.cogni.zone/esco/portal-api");
        this.cookie = resolve(props, "esco.api.cookie", "");
        this.datasetVersion = resolve(props, "esco.dataset.version", "v1.2.1");
        this.mode = resolve(props, "esco.mode", "auto");
        this.throttleMs = Long.parseLong(resolve(props, "esco.throttle.delayMs", "500"));
        this.performanceThresholdPct = Integer.parseInt(resolve(props, "esco.performance.thresholdPercent", "200"));
    }

    public static TestConfig instance() {
        return INSTANCE;
    }

    public String baseUrl() {
        return baseUrl;
    }

    public String datasetVersion() {
        return datasetVersion;
    }

    public String mode() {
        return mode;
    }

    public long throttleMs() {
        return throttleMs;
    }

    public int performanceThresholdPct() {
        return performanceThresholdPct;
    }

    public String cookie() {
        return cookie;
    }


    private static String resolve(Properties props, String key, String defaultValue) {
        String sys = System.getProperty(key);
        if (sys != null && !sys.isBlank()) {
            return sys;
        }
        return props.getProperty(key, defaultValue);
    }
}

