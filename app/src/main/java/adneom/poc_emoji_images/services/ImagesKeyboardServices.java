package adneom.poc_emoji_images.services;

import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.KeyboardView;
import android.net.Uri;
import android.os.Build;
import android.support.v13.view.inputmethod.EditorInfoCompat;
import android.support.v13.view.inputmethod.InputConnectionCompat;
import android.support.v13.view.inputmethod.InputContentInfoCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import adneom.poc_emoji_images.R;
import adneom.poc_emoji_images.utils.Utils;
import adneom.poc_emoji_images.view.ImageKeyboardView;

import static android.R.attr.description;

/**
 * Created by gtshilombowanticale on 04-07-17.
 */

public class ImagesKeyboardServices extends InputMethodService{

    private InputConnection inputConnection;
    private static Context context;

    private ImageKeyboardView imageKeyboardView;

    private File outputFile;
    private EditorInfo editorInfo;

    private String[] mimeTypes;
    private String single;
    private String tmp_file = "img_poc";

    public ImagesKeyboardServices(){
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        single = "";
    }

    @Override
    public View onCreateInputView() {
        context = getApplicationContext();
        imageKeyboardView = (ImageKeyboardView) getLayoutInflater().inflate(R.layout.keyboad_view,null);
        Utils.clearCache(context);
        return imageKeyboardView.getView();
    }

    @Override
    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInputView(attribute, restarting);
        inputConnection = getCurrentInputConnection();
        editorInfo = attribute;
        supportedMimeTypes(editorInfo);
    }


    /**
     * Method called by adapter to display image in field EditText
     * @param file
     * @param name
     */
    public void manageImage(File file, String name){
        int flag;

        renamefile(file);
        Log.d("d","file is "+outputFile.getName());
        Uri contentUri = FileProvider.getUriForFile(context, getApplicationContext().getPackageName(),outputFile);
        if(Build.VERSION.SDK_INT >= 25) {
            flag = InputConnectionCompat.INPUT_CONTENT_GRANT_READ_URI_PERMISSION;

        }else {
            flag = 0;
            try {
                // TODO: Use revokeUriPermission to revoke as needed.
                grantUriPermission(editorInfo.packageName, contentUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } catch (Exception e) {
                Log.d("d", "grantUriPermission failed packageName=" + editorInfo.packageName
                        + " contentUri=" + contentUri, e);
            }
        }

        Log.d("d","content uri is "+contentUri.getPath()+" - "+outputFile.getPath());
        final InputContentInfoCompat inputContentInfoCompat = new InputContentInfoCompat(contentUri, new ClipDescription("", mimeTypes), null /* linkUrl */);

        InputConnectionCompat.commitContent(getCurrentInputConnection(), getCurrentInputEditorInfo(), inputContentInfoCompat, flag, null);
    }

    /**
     * Give the list of supported mime types for edit text
     * @param editorInfo
     */
    private void supportedMimeTypes(EditorInfo editorInfo) {
       mimeTypes = EditorInfoCompat.getContentMimeTypes(editorInfo);
        Utils.initialeExtensionsToDelete(mimeTypes);
        Log.d("d","size is "+mimeTypes.length);
        for(String str : mimeTypes){
            Log.d("d",str);
        }
        if(mimeTypes.length == 1 && mimeTypes[0].contains("gif"))
            single = mimeTypes[0];
        Log.i("d","value is "+single);
    }

    /**
     * Rename if the mime types is just gif
     *  ex for Whatapps !!
     */
    private void renamefile(File f){
        //supportedMimeTypes(editorInfo);

        //Utils.clearCache(context);
        //FileUtils.deleteQuietly(context.getCacheDir());
        Utils.browsCache(context);
        Utils.browseMap();
        if(single.isEmpty()) {
            outputFile = f;
        }else{
            //outputFile = Utils.newFileGIF(f.getName(),context,tmp_file);
            outputFile = Utils.filesGIF.get(f.getName());
        }

    }
}
