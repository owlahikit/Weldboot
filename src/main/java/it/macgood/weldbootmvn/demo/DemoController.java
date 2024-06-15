package it.macgood.weldbootmvn.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.macgood.weldbootmvn.user.presentation.CallAuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/demo")
public class DemoController {

    private final RestClient restClient;
    @GetMapping("/hello")
    public String hello() {
        var url = "https://sms.ru/sms/send?api_id=EE77F6B6-2A58-44F4-C31F-352431287B35&to=79616608690&msg=1111&json=1";
        var response = restClient.post()
                .uri(url)
                .exchange(((clientRequest, clientResponse) -> Objects.requireNonNull(clientResponse.bodyTo(String.class))));
        return response;
    }

    @GetMapping("/parsing")
    public ResponseEntity<?> parse() throws JsonProcessingException {
        var response = "{\n" +
                "    \"status\": \"OK\",\n" +
                "    \"code\": 7303,\n" +
                "    \"call_id\": \"202424-1000005\",\n" +
                "    \"balance\": 0.96,\n" +
                "    \"cost\": 0.4\n" +
                "}";
        var objectMapper = new ObjectMapper();
        CallAuthResponse response1 = objectMapper.readValue(response, CallAuthResponse.class);
        return ResponseEntity.ok(response1);
    }
}
