package adneom.poc_emoji_images.welcome;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import adneom.poc_emoji_images.Application;
import adneom.poc_emoji_images.R;

/**
 * Created by gtshilombowanticale on 04-07-17.
 */

public class WelcomeActivity extends AppCompatActivity {

    @Inject WelcomePresenter presenter;

    private WelcomeFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);

        fragment = (WelcomeFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        if(fragment == null){
            fragment = WelcomeFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();
        }

        //create presenter
        //the first takes in parameter the component and second the fragment
        //DaggerWelcomeComponent.builder().applicationComponent(((Application)getApplication()).getAppComponent()).welcomePresenterModule(new WelcomePresenterModule(fragment)).build();
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }else{
            createPresneetr(fragment);
        }
    }

    private void createPresneetr(WelcomeFragment fragment){
        DaggerWelcomeComponent.builder().applicationComponent(((Application)getApplication()).getAppComponent()).welcomePresenterModule(new WelcomePresenterModule(fragment)).build();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    createPresneetr(fragment);
                }
                break;
        }
    }
}
