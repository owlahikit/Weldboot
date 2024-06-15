package it.macgood.weldbootmvn.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.macgood.weldbootmvn.user.presentation.AuthExtra;
import it.macgood.weldbootmvn.user.presentation.CallAuthResponse;
import it.macgood.weldbootmvn.user.presentation.SmsAuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final RestClient restClient;

//    SHA256:GJQxp8SA/bPkvnVr1m8t0unTu+m3pEHBd5RuCeDOPJ0 a1111@MacBook-Pro-1111.local
    public CallAuthResponse auth(String phone) {

        var call = "https://sms.ru/code/call?phone="
                + phone
                +"&ip=-1&api_id=EE77F6B6-2A58-44F4-C31F-352431287B35";

        var response = restClient.post()
                .uri(call)
                .exchange(((clientRequest, clientResponse) -> clientResponse.bodyTo(String.class)));
        var objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(response, CallAuthResponse.class);
        } catch (Exception e) {
            return null;
        }
    }
}
