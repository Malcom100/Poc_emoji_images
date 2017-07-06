package adneom.poc_emoji_images.welcome;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import adneom.poc_emoji_images.R;
import adneom.poc_emoji_images.utils.Utils;

/**
 * Created by gtshilombowanticale on 04-07-17.
 */

public class WelcomeFragment extends Fragment implements WelcomeContract.View {

    private WelcomeContract.Presenter presenter;
    private List<File> inputs;

    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageView image5;


    public WelcomeFragment(){}

    public static WelcomeFragment newInstance() {
        return new WelcomeFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome,container,false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeView();
    }

    @Override
    public void setPresenter(WelcomeContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private void initializeView(){
        int i = 1;
        image1 = (ImageView)getActivity().findViewById(R.id.image_1);
        image2 = (ImageView)getActivity().findViewById(R.id.image_2);
        image3 = (ImageView)getActivity().findViewById(R.id.image_3);
        image4 = (ImageView)getActivity().findViewById(R.id.image_4);

        inputs = Utils.getFiles(getActivity());
        for(File file : inputs){
            setImageView(i,file);
            i++;
        }

    }

    private void setImageView(int index, File file) {
        switch (index) {
            case 1 :
                Picasso.with(getActivity()).load(file).into(image1);
                break;
            case 2 :
                Picasso.with(getActivity()).load(file).into(image2);
                break;
            case 3 :
                Picasso.with(getActivity()).load(file).into(image3);
                break;
            case 4 :
                Picasso.with(getActivity()).load(file).into(image4);
                break;
        }
    }
}
