<%@page isELIgnored="false" language="java" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="path" scope="page" value="${pageContext.request.contextPath}"></c:set>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <!-- 导入 kind editor s d k-->

    <script src="${path}/kindeditor/kindeditor-all.js"></script>

    <script src="${path}/kindeditor/lang/zh-CN.js"></script>

    <script src="${path}/resources/js/jquery-3.4.1.min.js"></script>
    <script src="${path}/resources/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="${path}/resources/css/bootstrap.css"/>
    <script src="https://cdn.goeasy.io/goeasy-1.0.3.js">

    </script>

    <script>

        //初始化 goeasy对象

        var goEasy = new GoEasy({
            host: "hangzhou.goeasy.io",
            appkey: "BC-c5d32e6f4b5d43f39bda54d7d8ed8086"
        });

        console.log(goEasy);

        //接收消息

        goEasy.subscribe({
            channel: "goeasyTest",
            onMessage: function (message) {
                // 编辑器显示文本
                window.editor1.appendHtml("</br>" + message.content + "</br>");

                //获得iframe 框架内容对象
                var content = $(".ke-edit-iframe:eq(0)")[0].contentWindow;

                //内容对象取得 document 对象
                var doc = content.document;

                // 滚动条下拉以查看内容
                setTimeout(function () {

                    /*
                    * 定时器执行1 次
                    * scroll 高度 = b o d y 对象的高度
                    * */
                    content.scroll(0, doc.body.scrollHeight);
                }, 100)
            }
        });

    </script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3 " style="top:90px">
            <textarea name="content" id="client3"></textarea>
            <textarea name="content" id="client2"></textarea>
        </div>
    </div>
</div>


<script>
    window.editor = KindEditor.create('#client2', {
        width: '200px',
        height: '200px',
        items: [
            'fontname', "|", "fontsize", "|", "emoticons", "<button class='btn btn-primary'>xxx</button>"
        ],
        resizeType: 0
    });

    window.editor1 = KindEditor.create('#client3', {
        width: '300px',
        height: '500px',
        items: [],
        resizeType: 0
    });
    $("<button class='btn btn-primary pull-right' id='send'>发送</button>").prependTo(".ke-statusbar:eq(1)");


</script>
<script>
    $("#send").on('click', sendMessage);

    function sendMessage() {
        //发送消息
        var date = new Date();

        var fullYear = date.getFullYear();

        var month = date.getMonth() + 1;

        var date1 = date.getDate();

        var hours = date.getHours();

        var minutes = date.getMinutes();

        var seconds = date.getSeconds();

        var dat = fullYear + ":" + month + ":" + date1 + ":" + hours + ":" + minutes + ":" + seconds;
        goEasy.publish({
            channel: "goeasyTest",
            message: "小哥&lt;" + dat + "&gt;:</br>" + window.editor.html()
        });
        //清空发送框
        window.editor.html("");
    }
</script>

</body>
</html>