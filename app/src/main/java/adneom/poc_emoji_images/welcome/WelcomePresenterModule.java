package adneom.poc_emoji_images.welcome;

import dagger.Module;
import dagger.Provides;

/**
 * Created by gtshilombowanticale on 04-07-17.
 */

@Module
public class WelcomePresenterModule {

    private WelcomeContract.View mView;

    public WelcomePresenterModule(WelcomeContract.View view){
        this.mView = view;
    }

    @Provides
    WelcomeContract.View provideWelcomeView() {
        return this.mView;
    }
}
