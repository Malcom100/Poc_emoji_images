package adneom.poc_emoji_images.welcome;

import android.util.Log;

import javax.inject.Inject;

/**
 * Created by gtshilombowanticale on 04-07-17.
 */

public class WelcomePresenter implements WelcomeContract.Presenter {

    private WelcomeContract.View welcomeView;

    @Inject
    WelcomePresenter(WelcomeContract.View mView){
        this.welcomeView = mView;
    }

    @Inject
    public void setPresenter(){
            welcomeView.setPresenter(this);
            Log.d("d","---------- set presenetr here !!! --------- ");
    }

    @Override
    public void start() {

    }
}
