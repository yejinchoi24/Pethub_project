package pack.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import pack.entity.MedicalItem;
import pack.repository.MedicalItemRepository;

@Service
public class ChattingService {

	@Value("${openai.secretKey}")
    private String apiKey;
	
	@Autowired
	MedicalItemRepository medicalItemRepository;
	
	public List<MedicalItem> findAll() {
		List<MedicalItem> list = medicalItemRepository.findAll();
		return list;
	}

    public String getChatGptResponse(String userMessage, String medicalItems) {
    	System.out.println("!! medicalItems : " + medicalItems);
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.openai.com/v1/chat/completions";

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey); // API 키 설정

        // 요청 본문
        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-4");
        body.put("messages", new Object[] {
                Map.of("role", "system", "content", "You are a helpful medical assistant."),
                Map.of("role", "user", "content", 
                		"증상 : " + userMessage + 
                		"\n진료 항목 리스트 : [ " + medicalItems + " ]" +
                		"\n응답 규칙 : " +
                		"\n 1) 우선 증상에 대해 간단한 처치 방법이 있다면 해당 방법에 대해 설명" +
                		"\n 2) 그 다음 '추천 진료 항목 : [ 진료항목1, 진료항목2, ...]'의 형식으로 추천하는 진료 항목 리스트에 대해 답변." +
                		"\n 3) [] 안에 추천 진료항목을 적을 때는 반드시 내가 제공한 진료 항목 리스트에 있는 그대로의 데이터를 1~3개 골라야 해. 리스트에 없는 항목은 절대로 추가하지 마.")
        });

        // 요청 보내기
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        try {
            String response = restTemplate.postForObject(url, request, String.class);

            // JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response);
            return root.path("choices").get(0).path("message").path("content").asText();
        } catch (Exception e) {
            e.printStackTrace();
            return "ChatGPT API 호출 중 오류가 발생했습니다.";
        }
    }
}
