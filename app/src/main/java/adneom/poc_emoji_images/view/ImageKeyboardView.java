package adneom.poc_emoji_images.view;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import adneom.poc_emoji_images.R;
import adneom.poc_emoji_images.model.Image;
import adneom.poc_emoji_images.services.ImagesKeyboardServices;
import adneom.poc_emoji_images.utils.Utils;
import adneom.poc_emoji_images.view.adapter.ImageKeyboardAdapter;

/**
 * Created by gtshilombowanticale on 04-07-17.
 */

public class ImageKeyboardView extends View {

    private LinearLayout linearLayout;
    private ImagesKeyboardServices imagesKeyboardServices;

    private ImageKeyboardAdapter adapter;
    private RecyclerView recyclerView;


    public ImageKeyboardView(Context context) {
        super(context);
        initialize(context);
    }

    public ImageKeyboardView(Context context, AttributeSet attrs) {
        super(context,attrs);
        initialize(context);
    }

    public ImageKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        initialize(context);
    }

    /**
     *  Getting service
     *  Set layout
     *  Set adapter to recycler view
     * @param context
     */
    private void initialize(Context context){
        imagesKeyboardServices = (ImagesKeyboardServices)context;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        linearLayout = (LinearLayout)inflater.inflate(R.layout.keyboard,null);

        recyclerView = (RecyclerView) linearLayout.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context,4);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ImageKeyboardAdapter(createImages(context),context);
        recyclerView.setAdapter(adapter);
    }

    private List<Image> createImages(Context context){
        List<Image> images = Utils.getImages(context);
        return images;
    }

    public View getView(){
        return this.linearLayout;
    }

    private int width;
    private int height;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width = View.MeasureSpec.getSize(widthMeasureSpec);
        height = View.MeasureSpec.getSize(heightMeasureSpec);

        //Log.d("Emoji",width+" "+height);
        setMeasuredDimension(width,height);
    }


}
