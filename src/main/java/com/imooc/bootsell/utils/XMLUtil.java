package com.imooc.bootsell.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class XMLUtil {

    private final static org.slf4j.Logger logger = LoggerFactory.getLogger(XMLUtil.class);

    private XMLUtil() {
    }

    /**
     * 根据字符串创建 document 对象
     *
     * @param xmlStr
     * @return
     */
    public static Document getDocumentByString(String xmlStr) {
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xmlStr);
        } catch (DocumentException e) {
            logger.error("字符串转换成XML失败:" + e.getMessage());
        }
        return doc;
    }

    /**
     * 设置response的返回信息
     * @param response
     * @param fileName
     * @return    XMLWriter
     */
    public static XMLWriter getXMLWriter(HttpServletResponse response, String fileName) {
        XMLWriter writer = null;
        OutputStream os = null;
        try {
            //定义xml文件的格式
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            //准备输出xml文件
            writer =new XMLWriter(format);
            response.setContentType("application/xml");
            response.setHeader("Content-disposition", "attachment;filename=" + new String(fileName.getBytes(), "iso-8859-1"));
            os = response.getOutputStream();
            writer.setOutputStream(os);
        }catch (Exception e){
            logger.error("导出xml时获取XMLWriter出错："+e);
        }finally {
            if(null != os){
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error("关闭outputStream出错："+e);
                }
            }
        }
        return writer;
    }

    /**
     * 根据file获得Document
     * @param file   MultipartFile
     * @return       Document
     */
    public static Document getDocumentByFile(MultipartFile file){
        File file1 = new File(file.getOriginalFilename());
        Document document = null;
        try {
            file.transferTo(file1);
            //创建SAXReader对象
            SAXReader reader = new SAXReader();
            //读取文件 转换成Document
            document = reader.read(file1);
        } catch (IOException e) {
            document = null;
            logger.error("文件转换出错："+e);
        } catch (DocumentException e) {
            document = null;
            logger.error("读取文件出错："+e);
        }
        return document;
    }
}
