package portal.goldenmaster;

import org.junit.jupiter.api.Test;

class ModelMetadataTest {

    private static final String GROUP = "metadata";
    private static final String CLASS = ModelMetadataTest.class.getSimpleName();

    private final PortalApiClient client = PortalApiClient.instance();

    @Test
    void model_metadata() {
        var response = client.get("/api/esco/model-metadata");

        GoldenMasterAssertions.assertGoldenMaster(
                GROUP,
                CLASS,
                "model_metadata",
                response,
                200
        );
    }
}
