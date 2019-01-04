package com.wensheng.zcc.amc.utils;

import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 * @author chenwei on 1/4/19
 * @project zcc-backend
 */
public class ImageUtils {

  public static String getImageType(String filePath){
    try(ImageInputStream iis = ImageIO.createImageInputStream(filePath)){


      // get all currently registered readers that recognize the image format

      Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);

      if (!iter.hasNext()) {

        throw new RuntimeException("No readers found!");

      }

      // get the first reader

      ImageReader reader = iter.next();


      System.out.println("Format: " + reader.getFormatName());

      return reader.getFormatName().toLowerCase();

    }catch (Exception ex){
      ex.printStackTrace();
      return "unknownType";
    }



  }

}
