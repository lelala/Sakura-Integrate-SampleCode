<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder"%>
<%@ page import="tw.com.ischool.oauthclientsample.OAuthCallbackServlet" %>

<%
/*
'------------------------------------------------------------------------
' 本頁是進入系統的第一個頁面，由本頁面進入OAuth的認證及授權的流程。
'
' Request("application")：
' 代表與本App進行整合的"學校"主機(ex: demo.ischool.j)，
' 我們需要在OAuth取得授權時要求使用者對此學校主機的授權。
'
' 要求使用者授權的內容：
' ※ User.Mail 使用者的email資訊
' ※ User.BasicInfo 使用者的基本資料
' ※ 使用者在學校的群組相關資料 ex:demo.ischool.j:sakura
'------------------------------------------------------------------------
*/

// 取得由1Campus進入本頁時所帶的application參數
String req_app = request.getParameter("application");

// 註冊的ClientID 資料
String client_id = request.getSession().getServletContext().getInitParameter(OAuthCallbackServlet.CLIENTID_KEY) ;

// 註冊的RedirectURI 資料
String redirect_uri = request.getSession().getServletContext().getInitParameter(OAuthCallbackServlet.REDIRECT_URI_KEY);

// 要求授權的內容，以「,」分隔
String scope = String.format("User.Mail,User.BasicInfo,%s:sakura req_app", req_app);

//進行http redirect進入OAuth流程
String url = "https://auth.ischool.com.tw/oauth/authorize.php" +
    "?response_type=code" +
    "&client_id=%s" +
    "&redirect_uri=%s" + 
    "&scope=%s" +
    "&state=%s";

url = String.format(url, 
						client_id, 
						redirect_uri, 
						scope, req_app);
//response.getWriter().println(url);
response.sendRedirect(url);

%>