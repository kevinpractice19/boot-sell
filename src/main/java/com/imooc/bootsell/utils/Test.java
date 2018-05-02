package com.imooc.bootsell.utils;

import org.dom4j.Document;
import org.dom4j.io.XMLWriter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        String header = "test";
        HttpServletResponse httpServletResponse = null;
        httpServletResponse.setContentType("text/html;charset=UTF-8");
        httpServletResponse.setHeader("Content-disposition", header);
        Document document = null;
        XMLWriter xmlWriter = null;
        xmlWriter = XMLUtil.getXMLWriter(httpServletResponse,"导出文件名");
        xmlWriter.write(document);
    }
}
