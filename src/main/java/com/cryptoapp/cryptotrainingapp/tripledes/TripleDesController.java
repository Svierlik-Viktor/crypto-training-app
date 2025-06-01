package com.cryptoapp.cryptotrainingapp.tripledes;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tripledes")
public class TripleDesController {

    private final TripleDesService tripleDesService;

    public TripleDesController(TripleDesService tripleDesService) {
        this.tripleDesService = tripleDesService;
    }

    @PostMapping(value = "/encrypt", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> encrypt(@RequestBody TripleDesRequest request) {
        try {
            String explanation = tripleDesService.encrypt(request.getText(), request.getKey());
            return ResponseEntity.ok(explanation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("<span style='color:red;'>Помилка при шифруванні: " + e.getMessage() + "</span>");
        }
    }

    @PostMapping(value = "/decrypt", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<String> decrypt(@RequestBody TripleDesRequest request) {
        try {
            String explanation = tripleDesService.decrypt(request.getText(), request.getKey());
            return ResponseEntity.ok(explanation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("<span style='color:red;'>Помилка при розшифруванні: " + e.getMessage() + "</span>");
        }
    }
}
