package com.cryptoapp.cryptotrainingapp.columnar;

import com.cryptoapp.cryptotrainingapp.common.TextWithKeyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/columnar")
public class ColumnarController {

    private final ColumnarService columnarService;

    @Autowired
    public ColumnarController(ColumnarService columnarService) {
        this.columnarService = columnarService;
    }

    @PostMapping("/encrypt")
    public ColumnarResult encrypt(@RequestBody TextWithKeyRequest request) {
        return columnarService.encryptExplained(request.getText(), request.getKey());
    }

    @PostMapping("/decrypt")
    public ColumnarResult decrypt(@RequestBody TextWithKeyRequest request) {
        return columnarService.decryptExplained(request.getText(), request.getKey());
    }
}
