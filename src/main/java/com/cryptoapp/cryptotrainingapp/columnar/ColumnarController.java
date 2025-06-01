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
    public ColumnarResponse encrypt(@RequestBody TextWithKeyRequest request) {
        return columnarService.encryptExplained(request.getText(), request.getKey());
    }

    @PostMapping("/decrypt")
    public ColumnarResponse decrypt(@RequestBody TextWithKeyRequest request) {
        return columnarService.decryptExplained(request.getText(), request.getKey());
    }
}
