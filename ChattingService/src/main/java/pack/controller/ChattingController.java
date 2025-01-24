 package pack.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pack.entity.MedicalItem;
import pack.service.ChattingService;

@RestController
@RequestMapping("/api/chatting")
public class ChattingController {

	@Autowired
    private ChattingService chattingService;

    @PostMapping("/recommend")
    public ResponseEntity<?> getRecommendedMedicalItems(@RequestBody String userInput) {
    	List<MedicalItem> items = chattingService.findAll();
    	System.out.println("컨트롤러 : " + items);
    	
        // 1. MySQL에서 증상에 맞는 진료항목 검색
    	String medicalItems = items.stream()
                .map(MedicalItem::getName) // 진료 항목의 이름만 추출
                .collect(Collectors.joining("\", \"", "[\"", "\"]"));

        // 2. ChatGPT API 호출 (진료항목 포함)
        String gptResponse = chattingService.getChatGptResponse(userInput, medicalItems);

        // 3. 결과 반환
        return ResponseEntity.ok().body(gptResponse);
    }
}
