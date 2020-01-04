<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="path" scope="page" value="${pageContext.request.contextPath}"></c:set>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <%-- <meta name="referrer" content="origin">--%>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script></script>

    <script src="${path}/resources/js/jquery-3.4.1.min.js"></script>
    <script src="${path}/resources/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="${path}/resources/css/bootstrap.css"/>
    <script>
        //请求验证码
        function updateValidate() {
            $("#img_yzm").attr("src", "${path}/validate/validateImg?time=" + new Date().getTime());
        }

        //表单验证
        function formCheckAndlogin() {
            //账号只能是数字+英文 长度>=5以上
            var zh = /^[0-9a-zA-Z]{5,}$/ig;
            //密码只能是数字+字母+符号. 长度>=6
            var pwd = /^[0-9a-zA-Z.]{6,}$/ig;
            var us = $("#username").val();
            var pd = $("#password").val();
            var b = zh.test(us);
            var c = pwd.test(pd);
            /*      if (!b) {
                      //账号格式不正确
                      $("#username").attr("data-original-title", "账号格式不正确！");
                      $("#username").tooltip("show");
                      $("#username").attr("data-original-title", "请输入账号");
                  }
                  if (c) {
                      //密码格式正确
                  }
      */

        }

        function login() {
            var us = $("#username").val();
            var pd = $("#password").val();
            var yzm = $("#yzm").val();
            $.ajax({
                url: "${path}/admin/login?userName=" + us + "&passWord=" + pd + "&code=" + yzm,
                type: "get",
                success: function (data) {
                    statusHandle(data);
                },
                error: function (xhr, status, throwex) {
                    console.log("登录请求异常");
                },
                dataType: "json"
            });
        }

        function getCookie() {
            $.ajax({
                url: "${path}/cookie/getCookie",
                type: "get",
                success: function (data, status, xhr) {
                    console.log("cookie获取成功");
                },
                error: function (xhr, status, throwex) {
                    console.log("cookie获取异常");
                },
                dataType: "text"
            });
        }

        //状态处理
        function statusHandle(obj) {
            if (obj.status === 1) {
                window.location.href = '${path}/bgmange';
            }
            if (obj.status === -1) {
                alert("账号或者密码错误！");
            }

            if (obj.status === -2) {
                alert("验证码错误！");

            }
        }

        $(function () {

            //初始化工具提示
            $('[data-toggle="tooltip"]').tooltip();

            $("#my1").modal('show');
            $("#img_yzm").on("click", updateValidate);
            $("#btlogin").on("click", login);
        });
    </script>
    <style>
        /*修改模态框top位置*/
        .modal.fade.in {
            top: 90px;
        }

    </style>
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <%--声明模态--%>
            <div class="modal fade" id="my1" data-backdrop="false"
                 data-keyboard="false">
                <%--创建模态--%>
                <div class="modal-dialog">
                    <%--模态内容--%>
                    <div class="modal-content">
                        <%--模态头部--%>
                        <div class="modal-header">
                            <%--关闭按钮--%>
                            <button type="button" class="close" data-dismiss="modal">
                                <span>&times;</span>
                            </button>
                            <%--模态标题--%>
                            <h4 class="modal-title" id="mytitle">登录后台管理系统</h4>
                        </div>
                        <%--模态主体部分--%>
                        <div class="modal-body">

                            <form class="form-horizontal">
                                <div class="form-group">
                                    <label for="username" class="control-label col-md-3">账号</label>
                                    <div class="col-md-6">
                                        <input type="text" class="form-control col-md-3 " data-toggle="tooltip"
                                               data-placement="top" title="请输入账号" maxlength="10" name="userName"
                                               id="username"
                                               placeholder="username"/>
                                    </div>

                                </div>
                                <div class="form-group">
                                    <label for="password" class="control-label col-md-3">密码</label>
                                    <div class="col-md-6">
                                        <input type="text" class="form-control col-md-3" data-toggle="tooltip"
                                               title="请输入密码" data-placement="top" maxlength="10" name="passWord"
                                               id="password"
                                               placeholder="password"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="yzm" class="control-label col-md-3">验证码</label>
                                    <div class="col-md-6">
                                        <input type="text" class="form-control col-md-3" maxlength="4" title="请输入验证码"
                                               data-toggle="tooltip" data-placement="top" id="yzm"
                                               placeholder="validate"/>
                                        <img src="${path}/validate/validateImg" data-toggle="tooltip" title="点击切换验证码"
                                             data-placement="top" alt="" style="cursor:pointer"
                                             id="img_yzm" class="img-rounded col-md-offset-6"/>
                                    </div>
                                </div>

                            </form>
                        </div>
                        <%--模态底部--%>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                            <button type="button" class="btn btn-primary" id="btlogin">登录</button>
                        </div>

                    </div>

                </div>
            </div>


        </div>

    </div>
</div>

</body>
</html>