package tw.com.ischool.oauthclientsample;

/**
 * 
 * @author kevinhuang
 *
 * {"access_token":"0de4378baea09aa6d8b42c54bdb9847b",
 *  "expires_in":3600,
 *  "token_type":"bearer",
 *  "scope":"User.Mail,User.BasicInfo,demo.ischool.j:sakura req_app",
 *  "refresh_token":"90a701cd895819d7b3477ad6af3fee0d"}
 *
 */

public class AccessToken {

	private String access_token="";
	private String refresh_token = "";
	private String scope = "";
	private String token_type = "";
	private int expires_in ;
	
	
	public String getAccessTokenString() {
		return access_token;
	}
	public void setAccessTokenString(String accessTokenString) {
		this.access_token = accessTokenString;
	}
	public String getRefreshTokenString() {
		return refresh_token;
	}
	public void setRefreshTokenString(String refreshTokenString) {
		this.refresh_token = refreshTokenString;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getTokenType() {
		return token_type;
	}
	public void setTokenType(String tokenType) {
		this.token_type = tokenType;
	}
	public int getExpiresIn() {
		return expires_in;
	}
	public void setExpiresIn(int expiresIn) {
		this.expires_in = expiresIn;
	}
	
	
}
