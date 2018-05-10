package test.pivotal.pal.tracker;

import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import io.pivotal.pal.tracker.EnvController;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

import static org.assertj.core.api.Assertions.assertThat;

public class EnvControllerTest {
    private TestRestTemplate restTemplate;
    @LocalServerPort
    private String port;

    @Before
    public void setUp() throws Exception {
        RestTemplateBuilder builder = new RestTemplateBuilder()
                .rootUri("http://localhost:" + port)
                .basicAuthorization("user", "password");

        restTemplate = new TestRestTemplate(builder);
    }

    @Test
    public void getEnv() throws Exception {
        EnvController controller = new EnvController(
            "8675",
            "12G",
            "34",
            "123.sesame.street"
        );

        Map<String, String> env = controller.getEnv();

        assertThat(env.get("PORT")).isEqualTo("8675");
        assertThat(env.get("MEMORY_LIMIT")).isEqualTo("12G");
        assertThat(env.get("CF_INSTANCE_INDEX")).isEqualTo("34");
        assertThat(env.get("CF_INSTANCE_ADDR")).isEqualTo("123.sesame.street");
    }

}
