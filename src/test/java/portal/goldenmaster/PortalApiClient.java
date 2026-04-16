package portal.goldenmaster;

import java.net.HttpURLConnection;
import java.net.URL;

public class PortalApiClient {

    private static final PortalApiClient INSTANCE = new PortalApiClient();

    private final String baseUrl = TestConfig.instance().baseUrl();
    private final String cookie = TestConfig.instance().cookie();

    public static PortalApiClient instance() {
        return INSTANCE;
    }

    public ApiResponse get(String path) {
        long start = System.currentTimeMillis();

        try {
            var url = new URL(baseUrl + path);
            var connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.setRequestProperty("Cookie", cookie);

            int status = connection.getResponseCode();
            var stream = connection.getInputStream();

            String body = new String(stream.readAllBytes());

            return new ApiResponse(
                    baseUrl + path,
                    status,
                    body,
                    System.currentTimeMillis() - start
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
