<%@page isELIgnored="false" language="java" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" scope="page" value="${pageContext.request.contextPath}"></c:set>

<div class="modal fade" id="excel" data-keyword="false" data-backdrop="false">
    <div class="modal-dialog">

        <div class="modal-content">
            <div class="modal-header">
                <button class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
                <h4>Excle文件上传</h4>
            </div>

            <div class="modal-body">
                <form action="${path}/excel/upload" class="form-inline" method="post" enctype="multipart/form-data"
                      id="form1" onsubmit="">
                    <div class="form-group">
                        <label for="excelfile" class="control-label col-md-offset-6">excel电子表格文件</label>
                        <input type="file" id="excelfile" class="col-md-offset-6" name="excelFile" accept=".xls,.xlsx"/>
                    </div>

                </form>


            </div>

            <div class="modal-footer">
                <button class="btn btn-primary" onclick="formsubmit();">上传</button>
            </div>

        </div>
    </div>

</div>

<table id="jqtable">

</table>
<div id="jqgridtool">

</div>
<style>
    .ui-jqgrid-sortable {
        text-align: center;
    }

    #tr_id {
        display: none;
    }

</style>
<script>
    function formsubmit() {
        $("#form1").submit();
    }

    $(function () {

        $("#jqtable").jqGrid({
            url: "${path}/slideshow/img/queryPage", //数据获取地址
            editurl: "${path}/slideshow/img/update",
            datatype: "json",
            styleUI: "Bootstrap",
            autowidth: true,
            viewrecords: true,
            pager: "#jqgridtool",
            multiselect: true,
            caption: "轮播图管理",
            viewrecords: true,
            height: "400px",
            rowList: [2, 4, 8],
            rowNum: 5,
            colNames: [
                "id",
                "img_Name",
                "is_Delete",
                "img_Path"
            ],
            colModel: [
                {
                    name: "id", editable: true,
                    formatter: function (cellvalue, options, rowObject) {
                        //  var p = " <p style='padding-top: 7%;text-align:center'>" + cellvalue + "</p>";
                        return cellvalue;
                    }
                },
                {
                    name: "img_Name", editable: true, edittype: "text",

                    formatter: function (cellvalue, options, rowObject) {
                        console.log(options);
                        console.log(cellvalue);
                        console.log(rowObject);
                        //   var p = " <p style='padding-top: 7%;text-align:center'>" + cellvalue + "</p>";
                        return cellvalue;
                    }

                },
                {
                    name: "is_Delete", editable: true,
                    formatter: function (cellvalue, options, rowObject) {

                        //   var p = " <p style='padding-top: 7%;text-align:center'>" + cellvalue + "</p>";
                        return cellvalue;
                    },
                    edittype: "select",
                    editoptions: {value: "不显示:不显示;显示:显示"}
                },
                {
                    name: "img_Path", editable: true, edittype: "file",
                    formatter: function (cellvalue, options, rowObject) {
                        var img = "<img width='100%' height='100px' src=${path}/resources/" + cellvalue + "/>"
                        return img;
                    }
                }
            ],
            gridComplete: function () {
                $("td[aria-describedby=jqtable_id]").css({
                    "padding-top": "3%",
                    "text-align": "center"
                });
                $("td[aria-describedby=jqtable_is_Delete]").css({
                    "padding-top": "3%",
                    "text-align": "center"
                });
                $("td[aria-describedby=jqtable_img_Name]").css({
                    "padding-top": "3%",
                    "text-align": "center"
                });

            }


        }).jqGrid("navGrid", '#jqgridtool', {
                edittext: "修改",
                addtext: "添加",
                deltext: "删除",
                /* searchtext: "搜索",*/
                search: false,
                refreshtext: "刷新"


            },
            {
                beforeSubmit: function () {
                    fileUpdateload();

                }
            },
            {
                //增加触发事件

                beforeSubmit: function () {

                    fileUpload();
                }


            }
        ).navButtonAdd("#jqgridtool", {
            caption: "下载excel电子表格",
            buttonicon: "glyphicon glyphicon-cloud-download",
            onClickButton: function () {
                window.open("${path}/excel/export")
            },
            title: "点击下载excel电子表格",
            id: "downExcel"

        }).navButtonAdd("#jqgridtool", {
            caption: "上传excel表格到数据库",
            buttonicon: "glyphicon glyphicon-cloud-upload",
            onClickButton: function () {
                $("#excel").modal("show");

            },
            title: "点击上传excel表格到数据库",
            id: "uploadExcel"
        })


    });

    //添加
    function fileUpload() {

        $.ajaxFileUpload({
            url: "${path}/slideshow/image/upload?img_Name=" + $("#img_Name").val() + "&is_Delete=" + $("#is_Delete").val(),
            secureuri: false,
            type: "post",
            fileElementId: 'img_Path',
            success: function (data, status) {
                $("#cData").click();
                $(".glyphicon-refresh").click();

                console.log("成功");
            },
            error: function (data, status, e) {
                console.log("错误");
            }
        });


        return "1000";
    }

    //修改
    function fileUpdateload() {

        $.ajaxFileUpload({
            url: "${path}/slideshow/img/update?img_Name=" + $("#img_Name").val() + "&is_Delete=" + $("#is_Delete").val() + "&oper=edit" + "&id=" + $("#id").val(),
            secureuri: false,
            type: "post",
            fileElementId: 'img_Path',
            success: function (data, status) {
                $("#cData").click();
                $(".glyphicon-refresh").click();

                console.log("成功");
            },
            error: function (data, status, e) {
                console.log("错误");
            }
        });


        return "1000";
    }
</script>



