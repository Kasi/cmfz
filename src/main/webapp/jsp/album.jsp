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
    <style>
        #tr_id {
            display: none;
        }

        .ui-jqgrid-resize-mark {
            left: 952.172px !important;
        }

        #tr_audio_Size {
            display: none;
        }

        #tr_audio_Timelong {
            display: none;
        }

        #tr_album_Id {
            display: none;
        }

    </style>
</head>
<body>
<table id="albumtable"></table>
<!-- 翻页栏-->
<div id="albumtableTool"></div>

</body>
<script>
    var rowi;

    function play(eventt) {

        //  alert(rowi);
        var element = $("#" + rowi + ">td:eq(4)>audio")[0];

        if (element.paused) {
            //暂停就播放

            if ($(eventt.target).attr("id") == 'play') {
                $(eventt.target).html("<span class='glyphicon glyphicon-play'>暂停</span>");
            } else {
                $(eventt.target).html('暂停');
            }

            element.play();

        } else {
            //播放就暂停
            if ($(eventt.target).attr("id") == 'play') {
                $(eventt.target).html("<span class='glyphicon glyphicon-play'>播放</span>");
            } else {
                $(eventt.target).html('播放');
            }
            element.pause();

        }

    }

    function down() {
        //获取aduio
        var element = $("#" + rowi + ">td:eq(4)>audio")[0];

        window.open('${path}/albumAudio/albumAudioDownLoad?filePath=' + $(element).attr("filepath"));


    }

    $(function () {
        $("#albumtable").jqGrid({
            url: "${path}/album/selectPage", //数据获取地址
            type: "post",
            editurl: "${path}/album/optionedit",
            datatype: "json",
            styleUI: "Bootstrap",
            autowidth: true,
            viewrecords: true,
            pager: "#jqgridtool",
            multiselect: true,
            caption: "专辑管理",
            viewrecords: true,
            height: "650px",
            rowList: [2, 4, 8],
            rowNum: 5,
            pager: "albumtableTool",
            subGrid: true,

            subGridRowExpanded: function (subgrid_id, row_id) {
                rowi = row_id;
                //子表格id 分页窗口id
                var subgrid_table_id, pager_id;
                subgrid_table_id = subgrid_id + "_t";
                pager_id = "p_" + subgrid_table_id;
                //行id
                /* var rowid = row_id;*/
                //刚创建的子div的ID
                /*var subgrid = subgrid_id;*/

                //创建子table
                $("#" + subgrid_id).html(
                    "<table id=" + subgrid_table_id + "></table>" +
                    "<div id=" + pager_id + "></div>"
                );

                $("#" + subgrid_table_id).jqGrid({
                    url: "${path}/albumAudio/pageAlbumAudio?albumId=" + row_id, //数据获取地址
                    type: "post",
                    editurl: "${path}/albumAudio/optionEdit",
                    datatype: "json",
                    styleUI: "Bootstrap",
                    autowidth: true,
                    viewrecords: true,
                    pager: "#" + pager_id,
                    multiselect: true,
                    caption: "音频管理",
                    viewrecords: true,
                    height: "400px",
                    rowList: [2, 4, 8],
                    rowNum: 5,
                    toolbar: ["true", "top"],

                    gridComplete: function () {
                        //
                        $(".ui-userdata-top").html(
                            "<button id='play' class='btn btn-primary'><span class='glyphicon glyphicon-play'>播放</span></button>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button id='down' class='btn btn-danger'><span class='glyphicon glyphicon-arrow-down'>下载</span></button>"
                        );

                        //绑定事件
                        $("#play").on('click', play);
                        $("#down").on('click', down);
                    },
                    onSelectRow: function (rowid, status) {
                        rowi = rowid;

                    },
                    colNames: [
                        "id",
                        "audio_Size",
                        "audio_Name",
                        "albumMp3",
                        "is_Delete",
                        "audio_Time",
                        "album_Id",
                        "audio_Timelong"

                    ],
                    colModel: [
                        {
                            name: "id",
                            editable: true

                        },
                        {name: "audio_Size", editable: true},
                        {name: "audio_Name", editable: true},
                        {
                            name: "albumMp3", editable: true, width: "300px", edittype: "file",
                            formatter: function (cellvalue, options, rowObject) {

                                console.log($("#" + options.rowId));

                                var audio = "<audio filePath=" + cellvalue + "  src='${path}/resources" + cellvalue + "' controls='controls'></audio>"
                                return audio;
                            }
                        },
                        {
                            name: "is_Delete", editable: true, edittype: "select", editoptions: {value: "1:显示;2:隐藏"},
                            formatter: function (cellvalue, options, rowObject) {
                                if (cellvalue == 1) {
                                    return "显示";
                                } else {
                                    return "隐藏";
                                }
                            }
                        },
                        {name: "audio_Time", editable: true, edittype: "date"},
                        {name: "album_Id", editable: true},
                        {name: "audio_Timelong", editable: true}

                    ]

                }).jqGrid("navGrid", "#" + pager_id, {
                        edittext: "修改",
                        addtext: "添加",
                        deltext: "删除",
                        /* searchtext: "搜索",*/
                        search: false,
                        refreshtext: "刷新"
                    }, {
                        beforeSubmit: function () {
                            //修改音频文件
                            var datad = "id=" + $("#id").val() +
                                "&audio_Size=" + $("#audio_Size").val() + "&audio_Name="
                                + $("#audio_Name").val() + "&audio_Url=" + $("#audio_Url").val() + "&is_Delete=" + $("#is_Delete").val()
                                + "&audio_Time=" + $("#audio_Time").val()
                                + "&album_Id=" + $("#album_Id").val()
                                + "&audio_Timelong=" + $("#audio_Timelong").val();
                            $.ajaxFileUpload({
                                url: "${path}/albumAudio/updateAlbumAudio?" + datad,
                                secureuri: false,
                                type: "post",
                                fileElementId: "albumMp3",
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
                    }, {
                        beforeSubmit: function () {

                            //上传音频文件
                            var datad = "audio_Size=" + $("#audio_Size").val() + "&audio_Name="
                                + $("#audio_Name").val() + "&audio_Url=" + $("#audio_Url").val() + "&is_Delete=" + $("#is_Delete").val()
                                + "&audio_Time=" + $("#audio_Time").val()
                                + "&album_Id=" + rowi
                                + "&audio_Timelong=" + $("#audio_Timelong").val();
                            $.ajaxFileUpload({
                                url: "${path}/albumAudio/albumMp3Upload?" + datad,
                                secureuri: false,
                                type: "post",
                                fileElementId: "albumMp3",
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
                    }
                )
            },

            colNames: [
                "id",
                "album_Name",
                "album_Star",
                "album_Mid",
                "album_Bname",
                "album_Ctime",
                "album_Img_Path1"

            ],
            colModel: [
                {
                    name: "id",
                    editable: true

                },
                {name: "album_Name", editable: true},
                {name: "album_Star", editable: true},
                {name: "album_Mid", editable: true},
                {name: "album_Bname", editable: true},
                {name: "album_Ctime", editable: true, edittype: "date"},
                {
                    name: "album_Img_Path1", editable: true, edittype: "file",
                    formatter: function (cellvalue, options, rowObject) {
                        console.log(cellvalue);
                        var img = "<img width='100%' height='100px' src=${path}/resources/" + cellvalue + "/>"
                        return img;
                    }
                }
            ]

        }).jqGrid("navGrid", "#albumtableTool", {
                edittext: "修改专辑",
                addtext: "添加专辑",
                deltext: "删除专辑",
                search: false,
                refreshtext: "刷新专辑"
            },
            {
                beforeSubmit: function () {
                    //updateAlbum
                    var datad = "id=" + $("#id").val() + "&album_Name=" + $("#album_Name").val() + "&album_Star="
                        + $("#album_Star").val() + "&album_Mid=" + $("#album_Mid").val() + "&album_Bname=" + $("#album_Bname").val()
                        + "&album_Ctime=" + $("#album_Ctime").val()
                    $.ajaxFileUpload({
                        url: "${path}/album/updateAlbum?" + datad,
                        secureuri: false,
                        type: "post",
                        fileElementId: "album_Img_Path1",
                        success: function (data, status) {
                            $("#cData").click();
                            $(".glyphicon-refresh").click();

                            console.log("成功");
                        },
                        error: function (data, status, e) {
                            console.log("错误");
                        }


                    });
                }
            },
            {
                //添加
                beforeSubmit: function () {
                    var datad = "album_Name=" + $("#album_Name").val() + "&album_Star="
                        + $("#album_Star").val() + "&album_Mid=" + $("#album_Mid").val() + "&album_Bname=" + $("#album_Bname").val()
                        + "&album_Ctime=" + $("#album_Ctime").val()
                    $.ajaxFileUpload({
                        url: "${path}/album/albumImgUpload?" + datad,
                        secureuri: false,
                        type: "post",
                        fileElementId: "album_Img_Path1",
                        success: function (data, status) {
                            $("#cData").click();
                            $(".glyphicon-refresh").click();

                            console.log("成功");
                        },
                        error: function (data, status, e) {
                            console.log("错误");
                        }


                    });
                }
            },
            {
                //删除
            }
        );
    });

</script>
</html>



