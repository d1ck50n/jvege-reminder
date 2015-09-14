package com.jvege.util;

import java.util.ResourceBundle;

/**
 *
 * @author Dickson
 */
public class ResourceBundleUtil {

    private ResourceBundleUtil(){}
    
    public static String getText(String bundle, String code) {
        return ResourceBundle.getBundle(bundle).getString(code);
    }

    public static String getText(String code) {
        return ResourceBundle.getBundle("label").getString(code);
    }
}
