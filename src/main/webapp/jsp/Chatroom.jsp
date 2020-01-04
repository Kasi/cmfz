<%@page isELIgnored="false" language="java" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set value="${pageContext.request.contextPath}" scope="page" var="path"></c:set>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script src="${path}/resources/js/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script>
        //初始化 goeasy对象

        /*$(function () {
            var goEasy = new GoEasy({
                host: "hangzhou.goeasy.io", //应用所在区域地址
                appkey: "BS-8cbc7f6807b847a29e61d57d0cb2911e" // 应用app key只能订阅
            });

            goEasy.subscribe({
                channel: "goeasyTest", //订阅信道
                onMessage: function (message) {
                    //消息回调
                    console.log(message);
                    console.log(JSON.parse(message.content));
                }
            });

        });*/


    </script>
</head>
<body>

聊天室

</body>
</html>