package org.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.concurrent.CompletableFuture;

@Service
public class TranslationService {

    @Autowired
    private RestTemplate restTemplate;

    public String translate(String langFrom, String langTo, String text) throws UnsupportedEncodingException {
        String url = "https://script.google.com/macros/s/AKfycbz9q60cKZf9t9ReN4OgNllt59NPQ5EnPS7epN0GV45q9MM54OTavcMpqsslvhvCsiiQ/exec";

        URI uri = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("q", URLEncoder.encode(text, "UTF-8"))
                .queryParam("target", langTo)
                .queryParam("source", langFrom)
                .build(true)
                .encode()
                .toUri();

        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        return response.getBody();
    }
    public CompletableFuture<String> translateAsync(String langFrom, String langTo, String text) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return translate(langFrom, langTo, text);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
    }

}