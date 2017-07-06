package adneom.poc_emoji_images;

import android.content.Context;

import dagger.Component;

/**
 * Created by gtshilombowanticale on 04-07-17.
 */

@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Context getContext();
}
