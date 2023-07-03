package com.unexpected.capture.uc;

import android.util.Base64;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import java.io.File;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Locale;


public class MainHook implements IXposedHookLoadPackage {
    public static final String PACKAGE_NAME = "com.UCMobile";

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if(PACKAGE_NAME.contains(lpparam.packageName)) {

            XposedHelpers.findAndHookConstructor("com.uc.browser.business.freeflow.a.b.b",
                lpparam.classLoader, byte[].class, new XC_MethodHook(){
                    @Override
                    protected void afterHookedMethod(final XC_MethodHook.MethodHookParam param) throws Throwable {
                        new Thread(new Runnable(){
                                @Override
                                public void run() {
                                    Object pXP = XposedHelpers.getObjectField(param.thisObject, "pXP");
                                    Object aVar = null;
                                    boolean b = pXP != null && (aVar = XposedHelpers.getObjectField(pXP, "qat")) != null;
                                    IniFile file = new IniFile(getFile("telecom"));
                                    long millis = System.currentTimeMillis();
                                    file.set("time", "" + millis);
                                    file.set("date", String.format(Locale.ENGLISH, "%tF %tT", millis, millis));
                                    if(b) {
                                        file.set("server", (String) XposedHelpers.callMethod(aVar, "getDomain"));
                                        file.set("port", (String) XposedHelpers.callMethod(aVar, "duH"));
                                        file.set("uid", (String) XposedHelpers.callMethod(pXP, "getOrderId"));
                                        file.set("token", (String) XposedHelpers.callMethod(aVar, "duI"));
                                    } else {
                                        file.set("server", "");
                                        file.set("port", "");
                                        file.set("uid", "");
                                        file.set("token", "");
                                    }
                                    file.write();
                                }
                            }).start();
                    }
                });

            XposedHelpers.findAndHookConstructor("com.uc.browser.business.freeflow.a.b.e",
                lpparam.classLoader, byte[].class, new XC_MethodHook(){
                    @Override
                    protected void afterHookedMethod(final XC_MethodHook.MethodHookParam param) throws Throwable {
                        new Thread(new Runnable(){
                                @Override
                                public void run() {
                                    Object pXZ = XposedHelpers.getObjectField(param.thisObject, "pXZ");
                                    IniFile file = new IniFile(getFile("unicom"));
                                    long millis = System.currentTimeMillis();
                                    file.set("time", "" + millis);
                                    file.set("date", String.format(Locale.ENGLISH, "%tF %tT", millis, millis));
                                    if(pXZ != null) {
                                        file.set("server", (String) XposedHelpers.callMethod(pXZ, "getIp"));
                                        file.set("port", String.valueOf(XposedHelpers.getIntField(pXZ, "port")));
                                        String account = "uc".concat(getIp());
                                        String password = (String) XposedHelpers.callMethod(pXZ, "getPassword");
                                        file.set("account", account);
                                        file.set("password", password);
                                        file.set("base64", Base64.encodeToString(account.concat(":").concat(password).getBytes(), Base64.NO_WRAP));
                                    } else {
                                        file.set("server", "");
                                        file.set("port", "");
                                        file.set("account", "");
                                        file.set("password", "");
                                        file.set("base64", "");
                                    }
                                    file.write();
                                }
                            }).start();
                    }
                });
        }
    }

    public static File getFile(String fileName) {
        return new File("/storage/emulated/0/UCDownloads", fileName.concat(".ini"));
    }

    /*public static String getHost(String str) {
     if(!StringUtils.isEmpty(str)){
     if(!str.contains("://")){
     str="http://".concat(str);
     }
     try {
     return new URL(str).getHost();
     } catch(Throwable ignore) {
     }
     }
     return "";
     }*/

    public static String getIp() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while(networkInterfaces != null && networkInterfaces.hasMoreElements()) {
                NetworkInterface nextElement = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses;
                if(nextElement != null && (inetAddresses = nextElement.getInetAddresses()) != null) {
                    while(inetAddresses.hasMoreElements()) {
                        InetAddress nextElement2 = inetAddresses.nextElement();
                        if(nextElement2 != null && !nextElement2.isLoopbackAddress() && nextElement2 instanceof Inet4Address) {
                            return nextElement2.getHostAddress();
                        }
                    }
                }
            }
        } catch(Throwable ignore) {
        }
        return "10.1.10.1";
    }
}
