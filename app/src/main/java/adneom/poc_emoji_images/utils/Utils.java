package adneom.poc_emoji_images.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.util.Log;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import adneom.poc_emoji_images.model.Image;

/**
 * Created by gtshilombowanticale on 04-07-17.
 */

/**
 * Use to maange files in application
 */
public class Utils {

    public static String[] letters = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","x","y","z"};
    public static List<String> extensionsToDelete = new ArrayList<>();
    public static List<String> savedFileName = new ArrayList<>();
    public static String baseName = "img_poc";
    public static Map<String, File> filesGIF = new HashMap<String,File>();
    private static boolean redundance = false;

    public static void initialeExtensionsToDelete(String[] supported){
        for(String str : supported){
            String[] splitStr = str.split("/");
            extensionsToDelete.add(splitStr[1]);
        }
        extensionsToDelete.add("");
    }

    public static List<File> getFiles (Context context) {
        redundance = false;
        List<File> result = new ArrayList<>();
        AssetManager assetManager = context.getAssets();
        try {
            String[] files = assetManager.list("");
            for(String file : files){
                if(isFile(file)){
                    result.add(createFile(assetManager.open(file), context, file));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("E",e.getMessage());
        }
        return result;
    }

    /**
     * Allows to create a list of images
     * called from Fragment Welcome
     * @param context
     * @return list of image
     */
    public static List<Image> getImages(Context context) {
        redundance = true;
        List<Image> result = new ArrayList<>();
        AssetManager assetManager = context.getAssets();
        try {
            String[] files = assetManager.list("");
            for(String file : files){
                if(isFile(file)){
                    result.add(new Image(file, createFile(assetManager.open(file), context, file)));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("E",e.getMessage());
        }
        return result;
    }

    /**
     * check extension : jpg or png
     * @param name
     * @return
     */
    public static boolean isFile(String name){
        return (name.endsWith("jpg") || name.endsWith("png"));
    }

    /**
     * Get all file from Asset to display in recycler view
     * @param inputStream
     * @param context
     * @param name
     * @return
     */
    public static File createFile(InputStream inputStream,Context context, String name){
        File file = null;
        File fileGIF = null;
        OutputStream output = null;
        OutputStream outputGIF = null;
        if(inputStream != null){

            file = new File(context.getCacheDir(),name);
            //fileGIF = new File(context.getCacheDir(),FilenameUtils.removeExtension(name)+".gif");
            fileGIF = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/"+FilenameUtils.removeExtension(name)+ ".gif");
            try {
                output = new FileOutputStream(file);
                outputGIF = new FileOutputStream(fileGIF);
                byte[] buffer = new byte[4096];
                int read;

                while((read = inputStream.read(buffer)) != -1){
                    output.write(buffer, 0 , read);
                    outputGIF.write(buffer, 0, read);
                }
                output.flush();
                outputGIF.flush();
                if(redundance){
                    filesGIF.put(name,fileGIF);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.d("E","can't create FileOutputStream : "+e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("E","can't read inputstream : "+e.getMessage());
            }finally {
                closeOutputStream(output);
                closeInputStream(inputStream);
                closeOutputStream(outputGIF);
            }
        }
        return file;
    }

    public static void closeOutputStream(OutputStream out){
        if(out != null){
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("E","can't close outputstream "+e.getMessage());
            }
        }
    }

    public static void closeInputStream(InputStream in){
        if(in != null){
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("E","can't close inputstream "+e.getMessage());
            }
        }
    }



    /**
     * Delete all file with extension Gif
     * @param context
     */
    public static void clearCache(Context context){
        File[] files = context.getCacheDir().listFiles();
        for(File file : files){
            if(extensionsToDelete.contains(FilenameUtils.getExtension(file.getName()))){
                try {
                    PrintWriter printWriter = new PrintWriter(file);
                    printWriter.write("");
                    printWriter.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Log.d("d","create a print writer "+e.getMessage());
                }
                boolean deleted = file.delete();
                if(deleted)
                    Log.d("d","file is deleted");
            }
        }
    }

    /**
     * Create a file name in cache
     * Test if the name is not already existed !!
     * @param defaultName
     * @return file name
     */
    public static String createFileName(String defaultName) {
        int nb = 1;
        if(savedFileName != null && savedFileName.size() > 0){
            while (savedFileName.contains(defaultName)){
                defaultName = baseName+nb;
                nb++;
            }
        }
        savedFileName.add(defaultName);
        return defaultName;
    }

    public static void browsCache(Context context){
        AssetManager assetManager = context.getAssets();
        try {
            String[] files = assetManager.list("");
            for(String file : files){
                Log.d("d","file is "+file);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("E",e.getMessage());
        }
    }

    public static void browseMap(){
        for(Map.Entry<String, File> entry : filesGIF.entrySet()){
            File f = entry.getValue();
            Log.d("d",entry.getKey()+" "+f.getName());
        }
    }


    /**
     * Allows to create a file with extension gif to send to whatsapp
     * Get image from asset
     * @param name
     * @param context
     * @param defaultName
     * @return file gif
     */
    public static File newFileGIF(String name, Context context, String defaultName){
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = null;
        try {
            inputStream = assetManager.open(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        OutputStream output = null;
        File file = null;
        if(inputStream != null){
            //file = new File(context.getCacheDir(),createFileName(defaultName) + ".gif");
            file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"/"+createFileName(defaultName) + ".gif");
            try {
                output = new FileOutputStream(file);
                byte[] buffer = new byte[4096];
                int read;
                while((read = inputStream.read(buffer)) != -1){
                    output.write(buffer, 0 , read);
                }
                output.flush();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Log.d("E","can't create FileOutputStream : "+e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("E","can't read inputstream : "+e.getMessage());
            }finally {
                closeOutputStream(output);
                closeInputStream(inputStream);
            }
        }
        return file;
    }
}
