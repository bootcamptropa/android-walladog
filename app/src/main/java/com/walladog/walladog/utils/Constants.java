package com.walladog.walladog.utils;

import java.io.File;

/**
 * Created by hadock on 15/01/16.
 *
 */
public class Constants {

    public static final String APP_EXTDATA_DIR = "Walladog" + File.separator;
    public static final String APP_IMAGES = APP_EXTDATA_DIR + "images" + File.separator;
    public static final String APP_IMAGES_REDUCED = APP_IMAGES + "thumbs" + File.separator;

    public static String getAppExtdataDir() {
        return APP_EXTDATA_DIR;
    }

    public static String getAppImages() {
        return APP_IMAGES;
    }

    public static String getAppImagesReduced() {
        return APP_IMAGES_REDUCED;
    }
}
