package com.jtd.ticketing.base.util;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sunll on 2017/3/9.
 */
public class ImportExcel<T> {
    private T t;

    public ImportExcel() {

    }

    public ImportExcel(T t) {
        this.t = t;
    }

    /**
     * 获取实体类
     *
     * @param clazz
     * @param rowNum
     * @param text
     * @param
     * @return
     * @throws Exception
     */
    public T getObject(Class<T> clazz, int rowNum, String text) throws Exception {
       /*
        * 得到类中的方法
        */
        Method[] methods = clazz.getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            if (method.isAnnotationPresent(ImportExcelAnnotation.class)) {
                //获取带注解的方法
                ImportExcelAnnotation fieldAnnotation = method.getAnnotation(ImportExcelAnnotation.class);
                //注解的值等于列的值
                if (fieldAnnotation.fieldNum() == rowNum) {
                    //执行设置方法
                    method.invoke(t, changeType(method.getParameterTypes()[0], text));
                }
            }
        }
        return t;
    }

    public Object changeType(Class<?> clazz, String cellText) {
        if (clazz == String.class) {
            if (StringUtils.endsWith(cellText, ".0")) {
                return StringUtils.substringBefore(cellText, ".0");
            } else {
                return cellText;
            }
        } else if (clazz == Integer.class) {
            if (StringUtils.endsWith(cellText, ".0")) {
                return Integer.parseInt(StringUtils.substringBefore(cellText, ".0"));
            } else {
                return Integer.parseInt(cellText);
            }
            /*return Integer.parseInt(cellText);*/
        } else if (clazz == Long.class) {
            return Long.parseLong(cellText);
        } else if (clazz == Date.class) {
            return validAndFormatDate(cellText);
        } else if (clazz == double.class) {
            return Double.parseDouble(cellText);
        }
        return null;
    }

    public List<T> listObject(Class<T> clazz, MultipartFile file, int startRow) {
        //创建实体类集合
        List<T> list = new ArrayList<T>();
        /*File file = new File(filePath);*/
        String fileName = file.getOriginalFilename();
        try {
            Workbook wb;
            if (fileName.toLowerCase().endsWith("xls")) {
                wb = new HSSFWorkbook(file.getInputStream());
            } else {
                wb = new XSSFWorkbook(file.getInputStream());
            }
            Sheet sheet = wb.getSheetAt(0);
            if (sheet.getPhysicalNumberOfRows() != 0) {
                //获得总列数
                int clos = sheet.getRow(0).getPhysicalNumberOfCells();
                //得到所有的行
                int rows = sheet.getLastRowNum();
                for (int i = startRow; i < rows + 1; i++) {
                    Row row = sheet.getRow(i);
                    //判断是否为空行
                    if (row != null && !isRowEmpty(row)) {
                        t = clazz.newInstance();
                        for (int j = 0; j < clos; j++) {
                            t = getObject(clazz, j, getCellFormatValue(row.getCell(j)));
                        }
                        list.add(t);
                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
                return false;
        }
        return true;
    }

    /**
     * 检查表格表头是否符合规则
     *
     * @param header
     * @param
     * @return
     */
    public boolean checkTableHeader(String[] header, MultipartFile file) {
        /*File file = new File(filePath);*/
        String fileName = file.getOriginalFilename();
        try {
            Workbook wb;
            if (fileName.toLowerCase().endsWith("xls")) {
                wb = new HSSFWorkbook(file.getInputStream());
            } else {
                wb = new XSSFWorkbook(file.getInputStream());
            }
            Sheet sheet = wb.getSheetAt(0);
            //获得总列数
            if (sheet.getPhysicalNumberOfRows() != 0) {
                if (sheet.getRow(0) == null) {
                    return false;
                }
                int clos = sheet.getRow(0).getPhysicalNumberOfCells();
                //检查表头列数是否一致
                if (clos != header.length) {
                    return false;
                }
                for (int i = 0; i < clos; i++) {
                    //获取每个单元格内容进行对比
                    String cellStr = getCellFormatValue(sheet.getRow(0).getCell(i));
                    if (!changeType(String.class, cellStr).equals(header[i])) {
                        return false;
                    }
                }
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 获得表格标题
     * @param
     * @return
     */
    public String[] getTableHeader(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            Workbook wb;
            if (fileName.toLowerCase().endsWith("xls")) {
                wb = new HSSFWorkbook(file.getInputStream());
            } else {
                wb = new XSSFWorkbook(file.getInputStream());
            }
            Sheet sheet = wb.getSheetAt(0);
            //获得总列数
            if (sheet.getPhysicalNumberOfRows() != 0) {
                int clos = sheet.getRow(0).getPhysicalNumberOfCells();
                String[] header = new String[clos];
                if (sheet.getRow(0) == null) {
                    return new String[0];
                }
                for (int i = 0; i < clos; i++) {
                    //获取表格标题
                    header[i] = getCellFormatValue(sheet.getRow(0).getCell(i));
                }
                return header;
            } else {
                return new String[0];
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String[0];
    }


    /**
     * 根据Cell类型获取数据
     *
     * @param cell
     * @return
     */
    private String getCellFormatValue(Cell cell) {
        String cellvalue = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC: // 数字
                    //如果为时间格式的内容
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue = sdf.format(HSSFDateUtil.getJavaDate(cell.
                                getNumericCellValue())).toString();
                        break;
                    } else {
                        cellvalue = new DecimalFormat("0").format(cell.getNumericCellValue());
                    }
                    break;
                case HSSFCell.CELL_TYPE_STRING: // 字符串
                    cellvalue = cell.getStringCellValue();
                    break;
                case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
                    cellvalue = cell.getBooleanCellValue() + "";
                    break;
                case HSSFCell.CELL_TYPE_FORMULA: // 公式
                    cellvalue = cell.getCellFormula() + "";
                    break;
                case HSSFCell.CELL_TYPE_BLANK: // 空值
                    cellvalue = "";
                    break;
                case HSSFCell.CELL_TYPE_ERROR: // 故障
                    cellvalue = "非法字符";
                    break;
                default:
                    cellvalue = "未知类型";
                    break;
            }
        }
        return cellvalue.replaceAll("/s", "");
    }

    /**
     * 验证并格式化字符串格式日期
     * 支持格式：yyyy-MM-dd、yyyy/MM/dd、yyyy.MM.dd
     *
     * @param str
     * @return
     */
    public static Date validAndFormatDate(String str) {
        String rex1 = "^(^(\\d{4})(\\-)\\d{1,2}\\3\\d{1,2}$)$";
        String rex2 = "^(^(\\d{4})(\\/)\\d{1,2}\\3\\d{1,2}$)$";
        String rex3 = "^(^(\\d{4})(\\.)\\d{1,2}\\3\\d{1,2}$)$";
        try {
            if (StringUtils.isBlank(str)) {
                return new SimpleDateFormat("yyyy-MM-dd").parse("1970-01-01");
            }
            if (str.matches(rex1)) {
                return new SimpleDateFormat("yyyy-MM-dd").parse(str);
            } else if (str.matches(rex2)) {
                return new SimpleDateFormat("yyyy/MM/dd").parse(str);
            } else if (str.matches(rex3)) {
                return new SimpleDateFormat("yyyy.MM.dd").parse(str);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }


    public static void main(String[] args) throws Exception {
        String rex = "^(^(\\d{4})年\\d{1,2}月)$";
        String year = "11年1月";
        System.out.println(year.matches(rex));
        /*String[] dateStrArray = new String[]{
                "20-3-2",
                "2014-1-2",
                "2014-03-12",
                "2014/03/12",
                "2014.3.2",
                "2014.03.02",
                "2014年3月12日"
        };
        for (int i = 0; i < dateStrArray.length; i++) {
            System.out.println(dateStrArray[i] + "-----------------------------" + validAndFormatDate(dateStrArray[i]));
        }*/
    }
}
