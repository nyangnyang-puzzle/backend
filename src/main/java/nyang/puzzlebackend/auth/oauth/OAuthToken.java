package nyang.puzzlebackend.auth.oauth;

/**
 * google
 * access_token	애플리케이션에서 Google API 요청을 승인하기 위해 전송하는 토큰입니다.
 * expires_in	액세스 토큰의 남은 전체 기간(초)입니다.
 * refresh_token	새 액세스 토큰을 가져오는 데 사용할 수 있는 토큰입니다. 갱신 토큰은 사용자가 액세스 권한을 취소하거나 갱신 토큰이 만료될 때까지 유효합니다. 다시 말하지만 이 필드는 Google의 승인 서버에 대한 초기 요청에서 access_type 매개변수를 offline로 설정한 경우에만 이 응답에 표시됩니다.
 * refresh_token_expires_in	새로고침 토큰의 남은 전체 기간(초)입니다. 이 값은 사용자가 시간 기반 액세스를 부여할 때만 설정됩니다.
 * scope	access_token에서 부여한 액세스 범위로, 공백으로 구분되고 대소문자가 구분되는 문자열 목록으로 표현됩니다.
 *
 * access_token
 * token_type
 * expires_in
 * refresh_token
 * refresh_token_expires_in
 */
public record OAuthToken(
    String accessToken,
    String refreshToken,
    String expiresIn,
    String refreshTokenExpiresIn
) { }