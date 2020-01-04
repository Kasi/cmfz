package com.cmfz.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.cmfz.dao.SlideShowDao;
import com.cmfz.entity.SlideShow;
import com.cmfz.service.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @className: ExcelServiceImpl
 * @Description: TODO
 * @Author : Quan  Xiang Zeng
 * @Date: 2019-12-27 17:48
 * @Version 1.0
 */
/*
 * 声明组件
 * */
@Service
@Slf4j
@Transactional
public class ExcelServiceImpl implements ExcelService<SlideShow> {
    @Autowired
    private SlideShowDao slideShowDao;

    @Override
    public Map<String, Object> excelExportFile(List<SlideShow> datas, HttpServletRequest request, HttpServletResponse response) {
        for (SlideShow data : datas) {
            //取得服务器资源路径
            String realPath = request.getServletContext().getRealPath("resources");

            //指定图片绝对路径
            data.setImg_Path(realPath + "/" + data.getImg_Path());
        }
        //导出参数模型
        ExportParams exportParams = new ExportParams("轮播图统计", "轮播图工作簿");

        //创建工作簿模型
        Workbook sheets = ExcelExportUtil.exportExcel(exportParams, SlideShow.class, datas);

        try {
            //设置响应头

            response.setHeader("content-Disposition", "attachment;filename=" + new String("轮播图统计.xls".getBytes(), "iso8859-1"));

            //写出excel 流

            ServletOutputStream outputStream = response.getOutputStream();
            sheets.write(outputStream);

            //释放资源
            sheets.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
            log.error("导出轮播图发生异常！代码在: 55 行");
        }

        //创建消息载体
        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("msg", "导出轮播图excel ok!");

        return hashMap;
    }

    @Override
    public Map<String, Object> excelUPloadFile(MultipartFile excelFile, HttpServletRequest request) {
        //校验文件是否空
        long size = excelFile.getSize();
        if (!(size > 1)) {

            HashMap<String, Object> map = new HashMap<>();
            map.put("status", -1);

            return map;
        }

        //创建导入数据模型
        ImportParams importParams = new ImportParams();

        //设置表格标题行数
        importParams.setTitleRows(1);

        //设置列头行数
        importParams.setHeadRows(1);

        try {
            List<SlideShow> list = ExcelImportUtil.importExcel(excelFile.getInputStream(), SlideShow.class, importParams);


            slideShowDao.inserts(list);
        } catch (Exception e) {
            e.printStackTrace();

            log.error("表格数据导入异常IO！");
        }








     /*   //接收单元格对象

        ArrayList<HSSFCell> hssfCells = new ArrayList<>();

        //接收实体对象

        ArrayList<SlideShow> slideShows = new ArrayList<>();

        //创建excel文件模型
        HSSFWorkbook workbook = null;
        try {
            workbook = new HSSFWorkbook(excelFile.getInputStream());

            //获得数据格式化模型
            HSSFDataFormat format = workbook.createDataFormat();

            //日期格式
            short dateFormat = format.getFormat("yyyy-MM-dd");

            //单元格样式模型
            HSSFCellStyle cellStyle = workbook.createCellStyle();

            //设置日期格式
            cellStyle.setDataFormat(dateFormat);


            //获取工作簿数
            int numberOfSheets = workbook.getNumberOfSheets();

            for (int i = 0; i < numberOfSheets; i++) {

                //获取工作簿模型
                HSSFSheet sheetAt = workbook.getSheetAt(i);

                //获取行数量
                int numberOfRows = sheetAt.getLastRowNum();


                for (int j = 1; j < numberOfRows; j++) {
                    //获取行对象
                    HSSFRow row = sheetAt.getRow(j);

                    //获取单元格对象
                    int numberOfCells = row.getPhysicalNumberOfCells();

                    //清空单元格集合
                    hssfCells.clear();
                    for (int k = 0; k < numberOfCells; k++) {

                        //获取单元格
                        HSSFCell cell = row.getCell(k);

                        if (cell == null) {
                            continue;
                        }

                        //接收格式化后的日期
                        Date AfterformatDate = null;
                        if (cell != null) {

                            //是否数字类型
                            if (HSSFCell.CELL_TYPE_NUMERIC == cell.getCellType()) {

                                //单元格是否日期类型
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {

                                    //获得单元格值
                                    Date date = cell.getDateCellValue();

                                    //获得Date转换模型
                                    SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

                                    //输出格式后的日期
                                    String format1 = dateFormat1.format(date);

                                    //返回
                                    AfterformatDate = new Date(format1);
                                }
                            }
                        }

                        //储存
                        hssfCells.add(cell);

                    }
                    SlideShow slideShow = new SlideShow(hssfCells.get(0).getStringCellValue(), hssfCells.get(1).getStringCellValue(), hssfCells.get(2).getStringCellValue(), hssfCells.get(3).getStringCellValue());

                    slideShows.add(slideShow);



                }


            }


        } catch (IOException e) {
            e.printStackTrace();

            log.error("excel文件读取错误！");
        }
*/


        return null;
    }

    @Override
    public Map<String, Object> excelToMysql(List<SlideShow> datas) {
        return null;
    }
}
