package tw.com.ischool.oauthclientsample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.google.gson.Gson;

public class OAuthCallbackServlet extends HttpServlet {

	public final static String CLIENTID_KEY = "clientID";
	public final static String CLIENTSECRET_KEY = "clientSecret";
	public final static String REDIRECT_URI_KEY = "redirectUri";
	public final static String ENTRYPOINT_KEY = "entryPoint";

	private String redirect_url = "";
	private String client_id = "";
	private String client_secret = "";
	private String targetApp = "";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String code = req.getParameter("code");
		// resp.getWriter().println(code);
		targetApp = req.getParameter("state");

		/*  Get configuration value from the web.xml file */
		client_id = req.getSession().getServletContext()
				.getInitParameter(CLIENTID_KEY);
		client_secret = req.getSession().getServletContext()
				.getInitParameter(CLIENTSECRET_KEY);
		redirect_url = req.getSession().getServletContext()
				.getInitParameter(REDIRECT_URI_KEY);

		try {
			/* Bypass the CA validation */
			trustAllHttpsCertificates();

			// 1. get Access Token
			AccessToken token = getAccessToken(code);
			if (token != null) {

				// 2. get User Info
				UserInfo user = getUserInfo(token.getAccessTokenString(), req);
				
				// 3. get Group List
				getGroupList(token.getAccessTokenString(), req);

				// 4. get Group Members
				getGroupMember(token.getAccessTokenString(),req);

				//5. forward to a view page
				RequestDispatcher view = getServletContext().getRequestDispatcher("/result.jsp");
				view.forward(req, resp);

			} else {

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void getGroupList(String tokenString, HttpServletRequest req)
			throws IOException, ParserConfigurationException, SAXException {
		String url = "https://dsns.1campus.net/%s/sakura/GetMyGroup?stt=PassportAccessToken&AccessToken=%s";
		url = String.format(url, targetApp, tokenString);

		String result = getHTTPResult(url);
		req.setAttribute("mygroup", result);
		
	}

	private void getGroupMember(String tokenString, HttpServletRequest req)
			throws IOException {
		String url = "https://dsns.1campus.net/%s/sakura/GetGroupMember?stt=PassportAccessToken&AccessToken=%s";
		url = String.format(url, targetApp, tokenString);
		String result = getHTTPResult(url);
		req.setAttribute("groupmember", result);
		
	}

	/*
	 * 取得 Access Token
	 */
	private AccessToken getAccessToken(String code) {
		AccessToken result = null;
		// 1. Use authorization code to get an access token.
		String targetUrl = "https://auth.ischool.com.tw/oauth/token.php?"
				+ "grant_type=authorization_code" + "&client_id=%s"
				+ "&redirect_uri=%s" + "&client_secret=%s" + "&code=%s";
		targetUrl = String.format(targetUrl, client_id, redirect_url,
				client_secret, code);

		String tokenString = getHTTPResult(targetUrl);

		if (!tokenString.equals("")) {
			Gson gson = new Gson();
			result = gson.fromJson(tokenString, AccessToken.class);
		}

		return result;
	}

	private UserInfo getUserInfo(String accessTokenString, HttpServletRequest req) {
		String targetUrl = String.format(
				"https://auth.ischool.com.tw/services/me.php?access_token=%s",
				accessTokenString);
		String httpResult = getHTTPResult(targetUrl);
		req.setAttribute("userinfo", httpResult);
		Gson gson = new Gson();
		UserInfo ui = gson.fromJson(httpResult, UserInfo.class);

		return ui;
	}

	/*
	 * Bypass CA Validation
	 */
	private static void trustAllHttpsCertificates() throws Exception {
		javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
		javax.net.ssl.TrustManager tm = new miTM();
		trustAllCerts[0] = tm;
		javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext
				.getInstance("SSL");
		sc.init(null, trustAllCerts, null);
		javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc
				.getSocketFactory());
	}

	/*
	 * 取得 HTTP Get 呼叫的結果
	 */
	private String getHTTPResult(String targetUrl) {
		String result = "";

		URL url;
		try {
			url = new URL(targetUrl);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					url.openStream(), "UTF-8"));
			// String line = reader.readLine();
			String line;
			final StringBuffer stringBuffer = new StringBuffer(255);

			synchronized (stringBuffer) {
				while ((line = reader.readLine()) != null) {
					stringBuffer.append(line);
					stringBuffer.append("\n");
				}
				result = stringBuffer.toString();
			}

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	/*
	 * 實作一個 TrustManager，目的是要 Bypass CA Validation .....
	 * 參考：http://mengyang.iteye.com/blog/575671
	 */
	static class miTM implements javax.net.ssl.TrustManager,
			javax.net.ssl.X509TrustManager {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public boolean isServerTrusted(
				java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public boolean isClientTrusted(
				java.security.cert.X509Certificate[] certs) {
			return true;
		}

		public void checkServerTrusted(
				java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}

		public void checkClientTrusted(
				java.security.cert.X509Certificate[] certs, String authType)
				throws java.security.cert.CertificateException {
			return;
		}
	}
}
