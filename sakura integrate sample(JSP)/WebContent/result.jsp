<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title></title>
    <style type="text/css">
        #TextArea1 {
            height: 238px;
            width: 905px;
        }

        #TextArea2 {
            height: 238px;
            width: 905px;
        }

        #TextArea3 {
            height: 238px;
            width: 905px;
        }
    </style>
</head>
<body>
        <div>
            UserInfo:<br />
            <textarea id="TextArea1" name="S1" ><%= request.getAttribute("userinfo") %></textarea><br />
            Group:<br />
            <textarea id="TextArea2" name="S2" ><%= request.getAttribute("mygroup") %></textarea><br />
            GroupMember:<br />
            <textarea id="TextArea3" name="S3" ><%= request.getAttribute("groupmember") %></textarea>
        </div>
</body>
</html>