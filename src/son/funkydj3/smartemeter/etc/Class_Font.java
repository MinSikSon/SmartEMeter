/*
 * ref about font) http://www.1001freefonts.com/old-english-fonts.php
 * ref about coding) http://app.e-mirim.hs.kr/xe/board_BTqO41/8684
 */
package son.funkydj3.smartemeter.etc;

import java.lang.reflect.Field;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

public final class Class_Font extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		setDefaultFont(this, "DEFAULT", "fonts/Prototype.ttf");
		setDefaultFont(this, "MONOSPACE", "fonts/Prototype.ttf");
		setDefaultFont(this, "SANS_SERIF", "fonts/Prototype.ttf");
		setDefaultFont(this, "SERIF", "fonts/Prototype.ttf");
	}

	public static void setDefaultFont(Context context,
            String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
                fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }

    protected static void replaceFont(String staticTypefaceFieldName,
            final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}