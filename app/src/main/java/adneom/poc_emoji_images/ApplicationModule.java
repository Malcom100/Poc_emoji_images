package adneom.poc_emoji_images;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by gtshilombowanticale on 04-07-17.
 */

@Module
public class ApplicationModule {

    private final Context context;

    ApplicationModule(Context contex){
        this.context = contex;
    }

    @Provides
    Context provideContext() {
        return this.context;
    }
}
