<!--#include file="config.inc"-->
<%
'------------------------------------------------------------------------
' 這頁是測試偵錯時進入1Campus的快速網址，在正式上線時本頁應不需要存在
'
' 網址列參數說明：
' ※ testacc = [student | teacher] 直接以學生或老師測試帳號進入系統
' ※ testapp = 您的系統進入OAuth認證及授權的網址
'------------------------------------------------------------------------

Dim testAcc, testapp, url

testAcc = "teacher"
testapp = EntryPoint
url = "https://web2.ischool.com.tw/" &_
            "?testacc=" & testAcc &_
            "&testapp=" & Server.URLEncode(testapp)

Response.Redirect url
%>