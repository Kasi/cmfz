package com.cmfz.service;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author Quan Xiang Zeng
 * @CreateTime 2019-12-27 17:34
 * @Description TODO
 */
@Repository
public interface ExcelService<T> {

    Map<String, Object> excelExportFile(List<T> datas, HttpServletRequest request, HttpServletResponse response);

    Map<String, Object> excelUPloadFile(MultipartFile excelFile, HttpServletRequest request);

    Map<String, Object> excelToMysql(List<T> datas);


}
