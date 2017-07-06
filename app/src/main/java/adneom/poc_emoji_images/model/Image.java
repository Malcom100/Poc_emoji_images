package adneom.poc_emoji_images.model;

import java.io.File;

/**
 * Created by gtshilombowanticale on 04-07-17.
 */

public class Image {

    private String name;
    private File file;

    public Image(){}


    public Image(String name, File file){
        this.name = name;
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String toString() {
        return name;
    }
}
