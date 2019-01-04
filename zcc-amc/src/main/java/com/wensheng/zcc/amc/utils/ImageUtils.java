package com.wensheng.zcc.amc.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

/**
 * @author chenwei on 1/4/19
 * @project zcc-backend
 */
public class ImageUtils {

  public static final String UNKNOWNTYPE = "unknownType";

  public static String getImageType(String filePath){

    try{
      File fileLocal = new File(filePath);

      ImageInputStream iis = ImageIO.createImageInputStream(fileLocal);
      // get all currently registered readers that recognize the image format

      Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);

      if (!iter.hasNext()) {

        throw new RuntimeException("No readers found!");

      }

      // get the first reader

      ImageReader reader = iter.next();


      System.out.println("Format: " + reader.getFormatName());
      String ext = reader.getFormatName().toLowerCase();
      iis.close();
      return ext;

    }catch (Exception ex){
      ex.printStackTrace();
      return UNKNOWNTYPE;
    }
  }


  public static String getCheckSum(String filePath) throws NoSuchAlgorithmException, IOException {
    MessageDigest md = MessageDigest.getInstance("SHA-1");
    try (DigestInputStream dis = new DigestInputStream(new FileInputStream(filePath), md)) {
      while (dis.read() != -1) ; //empty loop to clear the data
      md = dis.getMessageDigest();
    }

    // bytes to hex
    StringBuilder result = new StringBuilder();
    for (byte b : md.digest()) {
      result.append(String.format("%02x", b));
    }
    return result.toString();
  }

}
