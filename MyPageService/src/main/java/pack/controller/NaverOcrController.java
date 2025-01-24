package pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import pack.service.NaverOCRService;

import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/api/mypage")
public class NaverOcrController {

    @Autowired
    private NaverOCRService ocrService;

    @PostMapping("/addReview/extract")
    public ResponseEntity<Map<String, Object>> extractDocument(@RequestParam("image") MultipartFile file) {
        try {
            String base64Image = Base64.getEncoder().encodeToString(file.getBytes());

            // OCR 서비스 호출
            Map<String, Object> extractedData = ocrService.extractDocumentData(base64Image);

            return ResponseEntity.ok(extractedData);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}

