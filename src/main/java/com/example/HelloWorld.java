package com.example;

import com.amazonaws.services.lambda.runtime.*;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.util.*;
import org.springframework.web.client.*;

import java.util.*;

import static org.springframework.http.HttpMethod.POST;

public class HelloWorld implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    /**
     * AWS Lambda のハンドラーメソッドです。
     *
     * @param input   入力データ
     * @param context AWS Lambda Context オブジェクト
     * @return 出力データ
     */

    private static final String ACCESS_TOKEN = "3xe8B2eGJbS4KGcKt74dcocXxhvfPXZTxrVtjJWyOMw";

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> input, Context context) {
        Map<String, Object> output = new HashMap<>();
        output.put("foo", "Hello, world");
        output.put("bar", "Goodbye, world");
        output.put("input", input); // 入力情報を見たいので出力に含める
        output.put("context", context); // コンテキスト情報を見たいので出力に含める

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization",
                    "Bearer " + ACCESS_TOKEN);
        headers.add("Content-Type",
                    "application/x-www-form-urlencoded");
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("message", "message_from_lambda_java");
        HttpEntity<MultiValueMap<String, String>> httpEntity =
                new HttpEntity<>(map, headers);
        ResponseEntity<String> res =
                restTemplate.exchange("https://notify-api.line.me/api/notify",
                                      POST, httpEntity, String.class);
        output.put("res", res);
        System.out.println("res=" + res);
        return output;
    }
}