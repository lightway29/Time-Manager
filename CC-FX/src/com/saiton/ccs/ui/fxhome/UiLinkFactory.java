package com.saiton.ccs.ui.fxhome;

/**
 * UiLink Creator Factory
 *
 * @author Saiton
 */
public class UiLinkFactory {

    /**
     * The factory method
     *
     * @param name name of the UiLink
     * @param imgResPath resource path of the image
     * @param callback callback object
     * @return UiLink object
     */
    public static UiLink createUiLink(String name, String imgResPath, UiLink.SimpleCallback callback) {
        return new UiLinkImpl(name, imgResPath, callback);
    }


    /**
     * concrete UiLink
     */
    private static class UiLinkImpl implements UiLink {

        private final String name;
        private final String imgResPath;
        private final SimpleCallback callback;

        public UiLinkImpl(String name, String imgResPath, SimpleCallback callback) {
            this.name = name;
            this.imgResPath = imgResPath;
            this.callback = callback;
        }

        @Override
        public String getUiName() {
            return name;
        }

        @Override
        public String getUiIconResPath() {
            return imgResPath;
        }

        @Override
        public SimpleCallback getCallback() {
            return callback;
        }
    }
}
