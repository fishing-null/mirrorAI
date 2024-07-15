package com.example.aidemo.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * 格式化图片为Base64
 */
public class PhotoFormatUtils {
    public static String encodeImageToBase64Binary(String imagePath) {
        // 指定图片路径
        try {
            File inputFile = new File(imagePath);
            FileInputStream imageInFile = new FileInputStream(inputFile);
            byte imageData[] = new byte[(int) inputFile.length()];
            imageInFile.read(imageData);

            // 编码并转换为字符串
            String imageDataString = Base64.getEncoder().encodeToString(imageData);

            return imageDataString;

        } catch (IOException io) {
            System.out.println(io.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(encodeImageToBase64Binary("E:\\photos\\R-C.jpg"));
    }

}
