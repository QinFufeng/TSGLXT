package com.example.wind.liberarymanege;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by wind on 2018/1/7.
 */

public class Test {
    public Bitmap goog(Bitmap bm) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bytes = baos.toByteArray();
        return Bytes2Bimap(bytes);
    }

    public Bitmap Bytes2Bimap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }


    public Bitmap stringToBitmap1(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bytes = org.apache.ws.commons.util.Base64.decode(string);
            //YuvImage yuvImage=new YuvImage(bytes, ImageFormat.NV21,200,200,null);
            //ByteArrayOutputStream boos=new ByteArrayOutputStream();
            //yuvImage.compressToJpeg(new Rect(0,0,0,0),80,boos);
            //byte[] b=boos.toByteArray();
            bitmap= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        } catch (org.apache.ws.commons.util.Base64.DecodingException e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}
