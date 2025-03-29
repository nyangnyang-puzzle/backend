package nyang.puzzlebackend.auth.oauth.kakao;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import nyang.puzzlebackend.auth.oauth.OAuthClient;
import nyang.puzzlebackend.auth.oauth.OAuthCodeExchangeRequest;
import nyang.puzzlebackend.auth.oauth.OAuthMember;
import nyang.puzzlebackend.auth.oauth.OAuthToken;
import nyang.puzzlebackend.auth.oauth.exception.OAuthProcessingException;
import nyang.puzzlebackend.global.logging.LoggingInterceptor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public class KakaoOAuthClient implements OAuthClient {
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String CONTENT_TYPE_VALUE = "application/x-www-form-urlencoded;charset=utf-8";
    private static final String GRANT_TYPE = "authorization_code";
    private static final String OPENID_SCOPE = "openid";

    private final RestClient kakaoRestClient;
    private final KakaoProperties kakaoProperties;
    private final ObjectMapper objectMapper;

    public KakaoOAuthClient(KakaoProperties kakaoProperties, 
                           ObjectMapper objectMapper,
                           LoggingInterceptor loggingInterceptor) {
        this.kakaoProperties = kakaoProperties;
        this.objectMapper = objectMapper;
        this.kakaoRestClient = RestClient.builder()
                .requestInterceptor(loggingInterceptor)
                .build();
    }

    @Override
    public String getAuthorizationUri(String redirectUri) {
        return String.format("%s?response_type=%s&client_id=%s&redirect_uri=%s",
                kakaoProperties.oauthEndpointUri(),
                kakaoProperties.responseType(),
                kakaoProperties.clientId(),
                redirectUri);
    }

    @Override
    public OAuthMember findOAuthMember(OAuthCodeExchangeRequest authRequest, String redirectUri) {
        OAuthToken oAuthToken = exchangeCodeForToken(authRequest.code(), redirectUri);
        return getKakaoMemberInfo(oAuthToken);
    }

    private OAuthMember getKakaoMemberInfo(OAuthToken oAuthToken) {
        String userInfoResponse = requestKakaoUserInfo(oAuthToken.accessToken());
        return parseKakaoUserInfo(userInfoResponse, oAuthToken);
    }

    private String requestKakaoUserInfo(String accessToken) {
        return kakaoRestClient.get()
                .uri(kakaoProperties.oauthUserInfoUri())
                .header(CONTENT_TYPE, CONTENT_TYPE_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .retrieve()
                .body(String.class);
    }

    private OAuthMember parseKakaoUserInfo(String responseBody, OAuthToken oAuthToken) {
        try {
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            String id = String.valueOf(jsonNode.get("id").asLong());
            return OAuthMember.of(id, oAuthToken.accessToken(), oAuthToken.refreshToken());
        } catch (Exception e) {
            throw new OAuthProcessingException("Failed to parse Kakao user info", e);
        }
    }

    private OAuthToken exchangeCodeForToken(String code, String redirectUri) {
        return kakaoRestClient.post()
                .uri(kakaoProperties.oauthTokenIssueUri())
                .header(CONTENT_TYPE, CONTENT_TYPE_VALUE)
                .body(createTokenRequestBody(code, redirectUri))
                .retrieve()
                .body(OAuthToken.class);
    }

    private MultiValueMap<String, String> createTokenRequestBody(String code, String redirectUri) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("code", code);
        formData.add("grant_type", GRANT_TYPE);
        formData.add("client_id", kakaoProperties.clientId());
        formData.add("redirect_uri", redirectUri);
        formData.add("client_secret", kakaoProperties.clientSecret());
        formData.add("scope", OPENID_SCOPE);
        return formData;
    }
}
