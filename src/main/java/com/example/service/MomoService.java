package com.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class MomoService {

    @Value("${momo.endpoint}")
    private String endpoint;

    @Value("${momo.partnerCode}")
    private String partnerCode;

    @Value("${momo.accessKey}")
    private String accessKey;

    @Value("${momo.secretKey}")
    private String secretKey;

    @Value("${momo.redirectUrl}")
    private String redirectUrl;

    @Value("${momo.notifyUrl}")
    private String notifyUrl;

    private final RestTemplate rest = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    public String createPaymentRequest(long amount, String orderInfo, Long orderId) throws Exception {
        String orderIdStr = (orderId == null)
                ? String.valueOf(System.currentTimeMillis())
                : String.valueOf(orderId);
        String requestId = String.valueOf(System.currentTimeMillis());
        String requestType = "payWithATM";

        String extraData = "";
        String amountStr = String.valueOf(amount);

        String rawSignature = "accessKey=" + accessKey +
                "&amount=" + amountStr +
                "&extraData=" + extraData +
                "&ipnUrl=" + notifyUrl +
                "&orderId=" + orderIdStr +
                "&orderInfo=" + orderInfo +
                "&partnerCode=" + partnerCode +
                "&redirectUrl=" + redirectUrl +
                "&requestId=" + requestId +
                "&requestType=" + requestType;

        String signature = hmacSHA256(rawSignature, secretKey);

        Map<String, Object> payload = new HashMap<>();
        payload.put("partnerCode", partnerCode);
        payload.put("partnerName", "EV MARKET");
        payload.put("storeId", "EVMarketStore");
        payload.put("requestId", requestId);
        payload.put("amount", amountStr);
        payload.put("orderId", orderIdStr);
        payload.put("orderInfo", orderInfo);
        payload.put("redirectUrl", redirectUrl);
        payload.put("ipnUrl", notifyUrl);
        payload.put("lang", "vi");
        payload.put("extraData", extraData);
        payload.put("requestType", requestType);
        payload.put("signature", signature);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(mapper.writeValueAsString(payload), headers);

        ResponseEntity<String> resp = rest.postForEntity(endpoint, request, String.class);

        Map<String, Object> respMap = mapper.readValue(
                resp.getBody(), new TypeReference<Map<String, Object>>() {});

        if (respMap.containsKey("payUrl")) {
            return respMap.get("payUrl").toString();
        }

        System.out.println("MoMo Error: " + resp.getBody());
        return null;
    }

    public boolean verifySignature(Map<String, Object> data) {
        try {
            String accessKey = String.valueOf(data.get("accessKey"));
            String amount = String.valueOf(data.get("amount"));
            String extraData = String.valueOf(data.get("extraData"));
            String message = String.valueOf(data.get("message"));
            String orderId = String.valueOf(data.get("orderId"));
            String orderInfo = String.valueOf(data.get("orderInfo"));
            String orderType = String.valueOf(data.get("orderType"));
            String partnerCode = String.valueOf(data.get("partnerCode"));
            String payType = String.valueOf(data.get("payType"));
            String requestId = String.valueOf(data.get("requestId"));
            String responseTime = String.valueOf(data.get("responseTime"));
            String resultCode = String.valueOf(data.get("resultCode"));
            String transId = String.valueOf(data.get("transId"));

            String raw = "accessKey=" + accessKey +
                    "&amount=" + amount +
                    "&extraData=" + extraData +
                    "&message=" + message +
                    "&orderId=" + orderId +
                    "&orderInfo=" + orderInfo +
                    "&orderType=" + orderType +
                    "&partnerCode=" + partnerCode +
                    "&payType=" + payType +
                    "&requestId=" + requestId +
                    "&responseTime=" + responseTime +
                    "&resultCode=" + resultCode +
                    "&transId=" + transId;

            String expected = hmacSHA256(raw, secretKey);
            String provided = String.valueOf(data.get("signature"));

            return expected.equals(provided);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String hmacSHA256(String data, String key) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKeySpec =
                new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        mac.init(secretKeySpec);
        byte[] digest = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
