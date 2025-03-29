package nyang.puzzlebackend.auth.oauth.google;

import java.util.Base64;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.json.JSONException;

public class JwtDecoderTest {

  @Test
  void test() throws JSONException {
    String jwtkakao = "eyJraWQiOiI5ZjI1MmRhZGQ1ZjIzM2Y5M2QyZmE1MjhkMTJmZWEiLCJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiI2NzNlYjc5YjcyMjRlYzJlNjA4ZDVkMTg5NzBmZmFlYSIsInN1YiI6IjM5NzU4NzAyNDciLCJhdXRoX3RpbWUiOjE3NDI4MDM0MjcsImlzcyI6Imh0dHBzOi8va2F1dGgua2FrYW8uY29tIiwiZXhwIjoxNzQyODI1MDI3LCJpYXQiOjE3NDI4MDM0Mjd9.pCSawop0tDW7VY9lDhcm2GB_ksOPa1eCuGhknjfwTzfrB8AlNTyg993Lvs0ncISRvMMHxv9BW4T3QVm3355Q1grRu0idNDLkJU_KqNRnfvRIf09oK5WemD5W2JpTMFCQNo2O-TKrPlSR8TnwZAvKIiMXoMBUQqvNHTJlv0w3WdduaW4ABQPQVWtja635JpAKpGGDejtevpINxKX_fpPjBJsXgK1Oo3cYY67l8Pyxy41dmtWtChNEoemo7eZgL5DGjGNzXTo1a7OaYraW5cpsG16snMkPl_wSWW4wUY-ujg7iyYOYXKWxmXV3RCU1lA6irm6Lg1tys8IeNNlyswRqow";
    String[] chunks = jwtkakao.split("\\.");
    JSONObject header = new JSONObject(decode(chunks[0]));
    JSONObject payload = new JSONObject(decode(chunks[1]));
    String signature = decode(chunks[2]);

    String sub = payload.getString("sub");
    System.out.println(header);
    System.out.println(payload);
    System.out.println(signature);
    System.out.println(sub);
  }

  private static String decode(String encodedString) {
    return new String(Base64.getUrlDecoder().decode(encodedString));
  }
}
