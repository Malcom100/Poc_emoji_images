package adneom.poc_emoji_images.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import adneom.poc_emoji_images.R;
import adneom.poc_emoji_images.model.Image;
import adneom.poc_emoji_images.services.ImagesKeyboardServices;

/**
 * Created by gtshilombowanticale on 04-07-17.
 */

public class ImageKeyboardAdapter extends RecyclerView.Adapter<ImageKeyboardAdapter.ImageViewHolder>{

    private List<Image> images;
    private Context context;

    private ImagesKeyboardServices imagesKeyboardServices;

    /**
     * Instantiate the service here
     * @param images
     * @param context
     */
    public ImageKeyboardAdapter(List<Image> images, Context context){
        this.images = images;
        this.context = context;
        imagesKeyboardServices = (ImagesKeyboardServices)context;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(this.context).inflate(R.layout.layout_image_list_row, parent,false);
        return new ImageViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        final Image image = images.get(position);
        Picasso.with(context).load(image.getFile()).into(holder.emoji);
        holder.emoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagesKeyboardServices.manageImage(image.getFile(),image.getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public ImageView emoji;

        public ImageViewHolder(View view){
            super(view);
            emoji = (ImageView) view.findViewById(R.id.emoji);
        }
    }
}
