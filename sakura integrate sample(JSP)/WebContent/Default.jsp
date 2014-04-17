<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.net.URLEncoder;" %>

<%
/*
'------------------------------------------------------------------------
' 這頁是測試偵錯時進入1Campus的快速網址，在正式上線時本頁應不需要存在
'
' 網址列參數說明：
' ※ testacc = [student | teacher] 直接以學生或老師測試帳號進入系統
' ※ testapp = 您的系統進入OAuth認證及授權的網址
'------------------------------------------------------------------------
*/

String testAcc = "teacher";
String testapp = request.getSession().getServletContext().getInitParameter("entryPoint");
String url= String.format("https://web2.ischool.com.tw/?testacc=%s&testapp=%s" ,
							testAcc, URLEncoder.encode(testapp));

response.sendRedirect(url);
//response.getWriter().println(url);
%>