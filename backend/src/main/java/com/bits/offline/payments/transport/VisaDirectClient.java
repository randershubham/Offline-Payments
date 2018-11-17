package com.bits.offline.payments.transport;

import com.bits.offline.payments.request.outbound.common.VisaDirectPaymentRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class VisaDirectClient {

    private static final String POST_URL = "https://developer.visa.com/vdp-api/api_explorer_requests";

    private final RestTemplate restTemplate;
    private final HttpHeaders httpHeaders;

    public VisaDirectClient() {
        this.restTemplate = new RestTemplate();

        this.httpHeaders = new HttpHeaders();
        this.httpHeaders.add("Content-Type", "application/json");
        this.httpHeaders.add("Authorization", "{base64 encoded userid:password}");
    }

    /*public ResponseEntity postToVisaDirect(String jsonPayload) {
        HttpEntity<String> requestHttpEntity = new HttpEntity<>(jsonPayload, httpHeaders);
        return restTemplate.exchange(POST_URL, HttpMethod.POST, requestHttpEntity, String.class);
    }*/

    public ResponseEntity postToVisaDirect(VisaDirectPaymentRequest visaDirectPaymentRequest) throws JsonProcessingException {
        HttpEntity<String> requestHttpEntity = new HttpEntity<>(visaDirectPaymentRequest.toJson(), httpHeaders);
        return restTemplate.exchange(POST_URL, HttpMethod.POST, requestHttpEntity, String.class);
    }

}
