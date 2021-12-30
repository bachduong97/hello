package vn.com.viettel.vds.benchmark.rest.router;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import vn.com.viettel.vds.arch.constant.ResponseStatusCode;
import vn.com.viettel.vds.arch.controller.restful.BaseController;
import vn.com.viettel.vds.arch.factory.response.GeneralResponse;
import vn.com.viettel.vds.arch.factory.response.ResponseFactory;

@RestController
@RequestMapping("${app.name-context}/v1/api")
@Slf4j
public class DefaultRouter extends BaseController {
    private final ResponseFactory responseFactory;
    @Value("${partner.spring-b.url}")
    private String springBUrl;

    public DefaultRouter(ResponseFactory responseFactory) {
        this.responseFactory = responseFactory;
    }

    @GetMapping(
            value = "/ping"
    )
    public ResponseEntity<GeneralResponse<String>> ping(
    ) {
        try {
            return responseFactory.success("ok");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return responseFactory.fail("nok", ResponseStatusCode.builder().code("ERR").httpCode(400).build());
        }
    }

    @GetMapping(
            value = "/ping-b"
    )
    public ResponseEntity<GeneralResponse<String>> pingB(
    ) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(
                    springBUrl,
                    HttpMethod.GET,
                    null,
                    String.class
            );
            HttpStatus responseCode = response.getStatusCode();
            if (responseCode == HttpStatus.OK) {
                return responseFactory.success("ok");
            }
            return responseFactory.success("nok");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return responseFactory.fail("exception", ResponseStatusCode.builder().code("ERR").httpCode(400).build());
        }
    }
}
