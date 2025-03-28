package nyang.puzzlebackend.auth.oauth.google;

import java.util.Base64;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

public class JwtDecoderTest {

  @Test
  void test() {
    String jwt = "eyJhbGciOiJSUzI1NiIsImtpZCI6ImVlMTkzZDQ2NDdhYjRhMzU4NWFhOWIyYjNiNDg0YTg3YWE2OGJiNDIiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiI0MDQxNTgyNzU2NjItOGI1aDBjcm4zbGRpdTl2OHI4NTBzb2xpNW1kbWlzczQuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI0MDQxNTgyNzU2NjItOGI1aDBjcm4zbGRpdTl2OHI4NTBzb2xpNW1kbWlzczQuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDIzNjA1ODYxOTM5OTc2NTcyMzkiLCJlbWFpbCI6Im1pbnNoaWs2OTA1QGdtYWlsLmNvbSIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJhdF9oYXNoIjoiSEQxdmh5NGh0OW9xX3hVa2tCWDAtZyIsImlhdCI6MTc0MjgwMTc5MiwiZXhwIjoxNzQyODA1MzkyfQ.n3QsSmV94dzQSvd-UWnpCS4MByI2H3TX7FnaZ3UIlc6H2usjCQhlAbYl-FP-AA8x8QufkZGZ2IvEX_0pYMIf8fzKinq98f4w7eLM9M_Uzun4hQAMxn9LAUbzTH-ZV4DIEhaQhFnpGU9wTlgpbTjLZzDFpzQQiYwIkXiPEKNpPM1SrgQ7ND3k_S8YYThe8a5e0Yhr6R-VVPE62tfveEN05FaJw8hEIsamCg2yWh70OPqyGo4Mt1BYC1HJ_ypQqGS1xenYycP3pAjUQfDIlltPyRO1iVxtyZMVvdZmLYDbjXlVfq0LMqAQ_4HXWWCeAUw38hFQSRxpRMrmUE3b3Z0U9Q";
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
