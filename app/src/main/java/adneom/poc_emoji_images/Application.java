package adneom.poc_emoji_images;

/**
 * Created by gtshilombowanticale on 04-07-17.
 */

public class Application extends android.app.Application {

    private ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
    }

    public ApplicationComponent getAppComponent(){
        return this.appComponent;
    }
}
