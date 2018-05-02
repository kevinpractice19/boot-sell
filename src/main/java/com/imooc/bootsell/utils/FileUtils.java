package com.imooc.bootsell.utils;

import com.imooc.bootsell.vo.ResultVo;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

public class FileUtils {

//    public static String makeFileName(String fileName) {
//        return UUID.randomUUID().toString() + "_" + fileName;
//    }
//
//    public static ResultVo createExcel(String fileNmae, List<?> list, Class<?> clazz) {
//        if (list.isEmpty()) {
//            return new ResultVo(300, "没有数据", list);
//        } else {
//            boolean operateSign = false;
//            String fileName = fileNmae + ".xlsx";
//            fileName = makeFileName(fileName);
//            try {
//                String path = ResourceUtils.getURL("classpath:").getPath() + "static/file/" + fileName;
//                FileOutputStream fileOutputStream = null;
////                fileOutputStream = ExcelU
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//
//    }
}
