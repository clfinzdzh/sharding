package com.ems;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @program: ems
 * @description: 字典表代码生成
 * @author: Frank.li
 * @create: 2019-05-30 13:26
 */
public class DictGenerator {

    public static final String DICT_EXCEL_PATH = "C:\\Users\\DELL\\Desktop\\项目资料\\数据库\\字典表20190530.XLSX";

    public static final String DICT_SQL = "INSERT INTO `ems`.`d_common_type`(`dict_code`, `dict_name`, `dict_type_chs`, `dict_type_en`, `parent_code`, `mark`, `sort`) VALUES (?, ?, ?, ?, ?, ?, ?)";

    public static final String[] types = {"性别","民族","学历","政治面貌","健康状况","身份证件类型"," 干部职务名称（行政职务）","职称","职务级别","突发事件","危险源和风险隐患区","防护目标","应急保障资源",
                                            "应急知识","应急预案","应急平台","储备库类型","级别","信息密级","高程基准","防护措施等级","避难级别","抗震设防烈度","生产企业类型","坐标系统",
                                                "危险源级别","经济类型","队伍性质","专兼职","通讯录-人员来源","法律法规类别","汽车运输企业-级别","预案性质","预案发布状态","预案类型"};

    public static void main(String[] args) {
        try {
            String type = "性别";
            if (getIndex(type)!=-1){
                System.out.println("开始执行。。。");
                List<DictModel> dictList = importExcel(DICT_EXCEL_PATH,DictModel.class,getIndex(type),types.length-getIndex(type));
                exeBatch(dictList);
                System.out.println("执行完毕，共执行SQL："+dictList!=null?dictList.size():0);
            }else {
                System.out.println("字典项不存在！");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getIndex(String type){
        for (int i = 0;i<types.length;i++){
            if (type.equals(types[i])){
                return i;
            }
        }
        return -1;
    }


    public static void exeBatch(List<DictModel> dictList) throws SQLException {
        Connection conn = JDBCUtils.getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement(DICT_SQL);
            for (DictModel dm:dictList
                 ) {
                pstmt.setString(1, dm.getCode());
                pstmt.setString(2, dm.getContent());
                pstmt.setString(3, dm.getType());
                pstmt.setString(4, dm.getEnName());
                pstmt.setString(5,dm.getPraCode());
                pstmt.setString(6,dm.getMark());
                pstmt.setString(7,dm.getSort());
                pstmt.addBatch();
            }
            System.out.println("Executing SQL:\r\n"+pstmt.toString());
            pstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeConnection(conn);
        }
    }


    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass,String fileName,boolean isCreateHeader, HttpServletResponse response){
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);

    }

    public static void exportExcel(List<?> list, String title, String sheetName, Class<?> pojoClass,String fileName, HttpServletResponse response){
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }

    public static void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse response){
        defaultExport(list, fileName, response);
    }

    private static void defaultExport(List<?> list, Class<?> pojoClass, String fileName, HttpServletResponse response, ExportParams exportParams) {
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams,pojoClass,list);
        if (workbook != null);
        downLoadExcel(fileName, response, workbook);
    }

    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void defaultExport(List<Map<String, Object>> list, String fileName, HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null);
        downLoadExcel(fileName, response, workbook);
    }

    public static <T> List<T> importExcel(String filePath, Class<T> pojoClass,Integer startSheet,Integer readSheet){
        if (StringUtils.isBlank(filePath)){
            return null;
        }
        ImportParams params = new ImportParams();
        /**
         * 表格标题行数,默认0
         */
        params.setTitleRows(1);
        /**
         * 表头行数,默认1
         */
        params.setHeadRows(1);
        params.setStartRows(0);
        /**
         * 这一列必须有值,不然认为这列为无效数据
         */
        params.setKeyIndex(0);
        /**
         * 开始读取的sheet位置,默认为0
         */
        params.setStartSheetIndex(startSheet);
        /**
         *上传表格需要读取的sheet 数量,默认为1
         */
        params.setSheetNum(readSheet);
        /**
         * 是否需要保存上传的Excel
         */
        params.setNeedSave(false);
        /**
         * 是否需要校验上传的Excel
         */
        params.setNeedVerify(false);

        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static <T> List<T> importExcel(MultipartFile file, Integer titleRows, Integer headerRows, Class<T> pojoClass){
        if (file == null){
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
