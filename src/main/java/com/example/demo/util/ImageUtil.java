package com.example.demo.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.core.io.ByteArrayResource;

import com.example.demo.exception.InternalServerException;


public class ImageUtil {
    public static final int BITE_SIZE = 4 * 1024;

    public static byte[] compressImage(byte[] data)  {
        // if (true) {
        //     throw new InternalServerException("Error compressing image");
        // }

        Deflater deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        while (!deflater.finished()) {
            int size = deflater.deflate(tmp);
            outputStream.write(tmp, 0, size);

        }
        try {
            outputStream.close();
        } catch (Exception e) {
            
        }
        return outputStream.toByteArray();
    }

    public static byte[] decompressImage(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] tmp = new byte[4*1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(tmp);
                outputStream.write(tmp, 0, count);
            }
            outputStream.close();
        } catch (Exception exception) {
        }
        return outputStream.toByteArray();
    }

    // public static ByteArrayResource byteToImage(byte[] data) {
    //     final ByteArrayResource inputStream = new ByteArrayResource(Files.readAllBytes(data));
    // }
}
