package pack.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

@Service
public class NaverOCRService {

    @Value("${naver.clova.ocr.url}")
    private String ocrApiUrl;

    @Value("${naver.clova.ocr.secretKey}")
    private String apiSecret;

    public Map<String, Object> extractDocumentData(String base64Image) {
        try {
            // 네이버 OCR API 호출
            String response = callNaverOcrApi(base64Image);

            // 전체 응답 출력
            JSONObject jsonResponse = new JSONObject(response);
            System.out.println("Full OCR Response: " + jsonResponse.toString(4));

            // 병원명 및 방문 날짜 추출
            String hospitalName = extractHospitalName(jsonResponse);
            String visitDate = extractVisitDate(jsonResponse);
            Integer price = extractPrice(jsonResponse);

            // 결과 반환
            Map<String, Object> result = new HashMap<>();
            result.put("fullResponse", jsonResponse.toString(4)); // JSON 응답 포매팅
            result.put("hospitalName", hospitalName);
            result.put("visitDate", visitDate);
            result.put("price", price);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return Map.of("error", e.getMessage());
        }
    }

    private String callNaverOcrApi(String base64Image) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-OCR-SECRET", apiSecret);

        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("version", "V2");
            requestBody.put("requestId", "unique-request-id");
            requestBody.put("timestamp", System.currentTimeMillis());

            JSONArray imagesArray = new JSONArray();
            JSONObject imageObject = new JSONObject();
            imageObject.put("name", "receipt_image");
            imageObject.put("format", "png");
            imageObject.put("data", base64Image);
            imagesArray.put(imageObject);

            requestBody.put("images", imagesArray);
        } catch (JSONException e) {
            throw new RuntimeException("JSON request body 생성 실패", e);
        }

        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(
                ocrApiUrl,
                HttpMethod.POST,
                entity,
                String.class
        );

        return response.getBody();
    }

    private String extractHospitalName(JSONObject jsonResponse) {
        try {
            JSONObject imagesObject = jsonResponse.getJSONArray("images").getJSONObject(0);
            if (imagesObject.has("receipt")) {
                JSONObject receipt = imagesObject.getJSONObject("receipt");
                if (receipt.has("result") && receipt.getJSONObject("result").has("storeInfo")) {
                    return receipt.getJSONObject("result")
                                  .getJSONObject("storeInfo")
                                  .getJSONObject("name")
                                  .getString("text");
                }
            }
            return "병원명을 찾을 수 없습니다.";
        } catch (JSONException e) {
            return "JSON 파싱 오류: " + e.getMessage();
        }
    }

    private String extractVisitDate(JSONObject jsonResponse) {
        try {
            JSONObject imagesObject = jsonResponse.getJSONArray("images").getJSONObject(0);
            if (imagesObject.has("receipt")) {
                JSONObject receipt = imagesObject.getJSONObject("receipt");
                if (receipt.has("result") && receipt.getJSONObject("result").has("paymentInfo")) {
                    JSONObject paymentInfo = receipt.getJSONObject("result").getJSONObject("paymentInfo");
                    if (paymentInfo.has("date") && paymentInfo.getJSONObject("date").has("text")) {
                        return paymentInfo.getJSONObject("date").getString("text");
                    }
                }
            }
            return "방문 날짜를 찾을 수 없습니다.";
        } catch (JSONException e) {
            return "JSON 파싱 오류: " + e.getMessage();
        }
    }
    
    private Integer extractPrice(JSONObject jsonResponse) {
        try {
            JSONObject imagesObject = jsonResponse.getJSONArray("images").getJSONObject(0);
            if (imagesObject.has("receipt")) {
                JSONObject receipt = imagesObject.getJSONObject("receipt");
                if (receipt.has("result") && receipt.getJSONObject("result").has("totalPrice")) {
                    // 금액 텍스트 추출
                    String priceText = receipt.getJSONObject("result")
                                              .getJSONObject("totalPrice")
                                              .getJSONObject("price")
                                              .getString("text")
                                              .replace(",", ""); // 쉼표 제거
                    return Integer.parseInt(priceText);
                }
            }
            return null; // 청구금액을 찾을 수 없을 경우 null 반환
        } catch (JSONException e) {
            e.printStackTrace();
            return null; // JSON 파싱 오류 시 null 반환
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null; // 금액 변환 오류 시 null 반환
        }
    }

}
