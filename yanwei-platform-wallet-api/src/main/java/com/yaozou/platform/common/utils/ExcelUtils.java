package com.yanwei.platform.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.poi.ss.usermodel.Workbook;
import org.jeecgframework.poi.excel.ExcelExportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * 导出Excel工具类
 * @author luojianhong
 * @version $Id: ExcelUtils.java, v 0.1 2017年11月23日 下午5:21:27 luojianhong Exp $
 */
public class ExcelUtils {

    public static ResponseEntity<Resource> getResource(HttpServletRequest request, String filename,
                                                       @SuppressWarnings("rawtypes") List list,
                                                       Class<?> clas,String title, String sheetName) {
        ByteArrayOutputStream bos = null;
        try {
            bos = new ByteArrayOutputStream();
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(title,sheetName), clas, list);
            workbook.write(bos);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add("charset", "utf-8");
            // 根据浏览器进行转码，使其支持中文文件名
            String browse = BrowserUtils.checkBrowse(request);
            if ("MSIE".equalsIgnoreCase(browse.substring(0, 4))) {
                headers.add("Content-Disposition",
                    "attachment;filename=\"" + java.net.URLEncoder.encode(filename, "UTF-8")
                                                   + "\"");
            } else {
                String newtitle = new String(filename.getBytes("UTF-8"), "ISO8859-1");
                headers.add("Content-Disposition", "attachment;filename=\"" + newtitle + "\"");
            }
            Resource resource = new InputStreamResource(
                new ByteArrayInputStream(bos.toByteArray()));
            return ResponseEntity.ok().headers(headers)
                .contentType(MediaType.parseMediaType("application/x-msdownload")).body(resource);

        } catch (IOException e) {
            if (null != bos) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return null;
    }
}
