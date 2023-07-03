package com.unexpected.capture.uc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Enumeration;
import java.util.Properties;


public class IniFile {
    private final Properties mProperties = new Properties();
    private final File mFile;

    public IniFile(@NonNull File file) {
        mFile = file;
    }

    @Nullable
    public Object set(@NonNull String key, @NonNull String value) {
        return mProperties.setProperty(key, value);
    }

    public boolean write() {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mFile);
            store(mProperties, fos, null);
            return true;
        } catch(IOException e) {
            XposedBridge.log("Failed to write .ini file " + mFile + " error: " + e);
        } finally {
            if(fos != null) {
                try {
                    fos.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    public static void store(@NonNull Properties properties, @NonNull OutputStream out, @Nullable String comments) throws IOException {
        store0(properties,
            new BufferedWriter(new OutputStreamWriter(out, "8859_1")),
            comments,
            true);
    }

    private static void store0(@NonNull Properties properties, @NonNull BufferedWriter bw, @Nullable String comments, boolean escUnicode) throws IOException {
        if(comments != null) {
            XposedHelpers.callStaticMethod(Properties.class, "writeComments", bw, comments);
        }
        //bw.write("#" + new Date().toString());
        //bw.newLine();
        synchronized(properties) {
            for(Enumeration<?> e = properties.keys(); e.hasMoreElements();) {
                String key = (String) e.nextElement();
                String val = (String) properties.get(key);
                key = (String) XposedHelpers.callMethod(properties, "saveConvert", key, true, escUnicode);
                /* No need to escape embedded and trailing spaces for value, hence
                 * pass false to flag.
                 */
                val = (String) XposedHelpers.callMethod(properties, "saveConvert", val, false, escUnicode);
                bw.write(key + "=" + val);
                bw.newLine();
            }
        }
        bw.flush();
    }
}

