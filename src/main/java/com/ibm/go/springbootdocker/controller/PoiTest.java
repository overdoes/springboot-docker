package com.ibm.go.springbootdocker.controller;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PoiTest {

    public static void main(String[] args) {

        FileOutputStream fileOut = null;
        BufferedImage bufferImg = null;
        //先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray
        try {
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            bufferImg = ImageIO.read(new File("/Users/other/ideaworkspace/giao.jpeg"));
            ImageIO.write(bufferImg, "jpg", byteArrayOut);

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet("test picture");

            sheet.setDefaultColumnWidth(20);
            sheet.setDefaultRowHeightInPoints(35);



            //画图的顶级管理器，一个sheet只能获取一个
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            //anchor主要用于设置图片的属性

            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0,(short) 1, 2, (short) 2, 3);
            HSSFClientAnchor anchor2 = new HSSFClientAnchor(0, 0, 0,0,(short) 2, 2, (short)3, 3);

            //插入图片
            patriarch.createPicture(anchor, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
            patriarch.createPicture(anchor2, wb.addPicture(byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
            fileOut = new FileOutputStream("/Users/other/ideaworkspace/测试Excel6.xls");
            // 写入excel文件
            wb.write(fileOut);
            System.out.println("----Excle文件已生成------");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(fileOut != null){
                try {
                    fileOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }


}
