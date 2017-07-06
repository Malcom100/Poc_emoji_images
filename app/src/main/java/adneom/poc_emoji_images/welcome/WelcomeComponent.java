package adneom.poc_emoji_images.welcome;

import adneom.poc_emoji_images.ApplicationComponent;
import adneom.poc_emoji_images.utils.FragmentScoped;
import dagger.Component;

/**
 * Created by gtshilombowanticale on 04-07-17.
 */
@FragmentScoped
@Component(dependencies = ApplicationComponent.class, modules = WelcomePresenterModule.class)
public interface WelcomeComponent {

    void inject(WelcomeActivity activity);
}
