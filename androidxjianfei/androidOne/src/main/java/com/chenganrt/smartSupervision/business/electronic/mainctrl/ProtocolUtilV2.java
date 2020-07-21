package com.chenganrt.smartSupervision.business.electronic.mainctrl;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Set;

public class ProtocolUtilV2 {
    final static private String PARAMS_DIVIDER_QUESTION = "?";
    final static private String PARAMS_DIVIDER_AND = "&";
    final static private String PARAMS_DIVIDER_EQU = "=";
    public final static ProtocalUnitParse OLD_BANNER_PARSE = new ProtocalUnitParse() {

        @Override
        public ProtocalUnit parseUnit(String url) throws URISyntaxException {
            ProtocalUnit ret = new ProtocalUnit();
            ret.self = url;
            String targetUrl = url.replace(" ", "%20");
            URI myurl = new URI(targetUrl);
            ret.aciton = myurl.getScheme();
            ret.target = myurl.getHost();
            ret.arg = parseUrl(url);
            return ret;
        }
    };
    public final static ProtocalUnitParse BANNER_PARSEV2 = new ProtocalUnitParse() {

        @Override
        public ProtocalUnit parseUnit(String url) throws URISyntaxException {
            ProtocalUnit ret = new ProtocalUnit();
            ret.self = url;
            String targetUrl = url.replace(" ", "%20");
            URI myurl = new URI(targetUrl);
            ret.aciton = myurl.getHost();
            ret.target = myurl.getPath();
            if (ret.target != null) {
                ret.target = ret.target.replace("/", "");
            }
            ret.arg = parseUrl(url);
            return ret;
        }
    };
    public final static ProtocalUnitParse MLINK_PARSE = new ProtocalUnitParse() {

        @Override
        public ProtocalUnit parseUnit(String url) throws URISyntaxException {
            ProtocalUnit ret = new ProtocalUnit();
            ret.self = url;
            HashMap<String, String> arg = parseUrl(url);
            ret.aciton = arg.get("action");
            ret.target = arg.get("target");
            String paramString = arg.get("param");
            if (paramString != null) {
                try {
                    ret.arg = parseUrl(URLDecoder.decode(paramString, "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    ret.arg = parseUrl(paramString);
                }
            }

            return ret;
        }
    };
    final static private String KEY_URI = "Uri";
    private static final ProtocolUtilV2 self = new ProtocolUtilV2();
    final private String SCHEME_PAGE = "page";
    final private String SCHEME_CALLAPP = "callapp";

    private FFtBannerExcutorProtocal PAGE_PROTOCAL = new FFtBannerExcutorProtocal() {

        @Override
        public void excute(ProtocalUnit unit, final Activity activity) {
            String classname = unit.target;
            Intent intent = new Intent();
            intent.setClassName(activity, classname);
            if (unit.arg != null) {
                Set<String> argKeys = unit.arg.keySet();
                for (String key : argKeys) {
                    intent.putExtra(key, unit.arg.get(key));
                }
            }
            if (intent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivity(intent);
            }

        }

        @Override
        public boolean isMatchProtocal(ProtocalUnit unit) {
            return SCHEME_PAGE.equalsIgnoreCase(unit.aciton);
        }
    };

    private FFtBannerExcutorProtocal CALL_APP_PROTOCAL = new FFtBannerExcutorProtocal() {

        @Override
        public void excute(ProtocalUnit unit, final Activity activity) {
            String action = unit.target;
            Intent intent = new Intent(action);
            Set<String> argKeys = unit.arg.keySet();
            if (unit.arg.containsKey(KEY_URI)) {
                String uri = unit.arg.get(KEY_URI);
                intent.setData(Uri.parse(uri));
            }
            for (String key : argKeys) {
                intent.putExtra(key, unit.arg.get(key));
            }
            intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NEW_TASK);
            if (intent.resolveActivity(activity.getPackageManager()) != null) {
                activity.startActivity(intent);
            }
        }

        @Override
        public boolean isMatchProtocal(ProtocalUnit unit) {
            return SCHEME_CALLAPP.equalsIgnoreCase(unit.aciton);
        }
    };


    private final FFtBannerExcutorProtocal[] protocals = {
            PAGE_PROTOCAL,
            CALL_APP_PROTOCAL,
    };

    public static boolean parseFftBannerUrl(Activity activity, String url) {
        if (TextUtils.isEmpty(url)) {
            return false;
        }
        return self.parseUrl(activity, url);
    }

    public static boolean parseOtherUnit(Activity activity, ProtocalUnit unit) {
        if (unit == null) {
            return false;
        }
        return self.parseUnit(activity, unit);
    }

    public static boolean canDeal(ProtocalUnit unit) {
        return self.getProtocalFromUrl(unit) != null;
    }

    public static HashMap<String, String> parseUrl(String url) {
        String values = null;
        HashMap<String, String> ret = new HashMap<>();
        if (url == null) {
            return ret;
        }
        try {
            int pos = url.indexOf(PARAMS_DIVIDER_QUESTION);// 获取包名,用split会报错
            if (-1 != pos) {
                values = url.substring(pos + 1);
                ret.put("hello_url", url.substring(0, pos));
            } else {
                values = url;
            }


            String[] paramStrings = values.split(PARAMS_DIVIDER_AND);// 获取参数

            for (int i = 0; i < paramStrings.length; i++) {
                String[] subParam = paramStrings[i].split(PARAMS_DIVIDER_EQU);
                if (subParam == null || subParam.length != 2) {

                } else {
                    String uncodeParam = subParam[1];
                    try {
                        uncodeParam = URLDecoder.decode(uncodeParam, "UTF-8");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ret.put(subParam[0], uncodeParam);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    public ProtocalUnitParse chooseParse(String url) {
        if (url.startsWith("elect://")) {
            return BANNER_PARSEV2;
        } else {
            return OLD_BANNER_PARSE;
        }
    }

    private FFtBannerExcutorProtocal getProtocalFromUrl(ProtocalUnit unit) {

        for (FFtBannerExcutorProtocal tmp : protocals) {
            if (tmp.isMatchProtocal(unit)) {
                return tmp;
            }
        }
        return null;
    }

    private boolean parseUnit(Activity activity, ProtocalUnit unit) {
        try {
            FFtBannerExcutorProtocal protocal = getProtocalFromUrl(unit);
            if (protocal != null) {
                protocal.excute(unit, activity);
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean parseUrl(Activity activity, String url) {
        try {
            ProtocalUnit unit = chooseParse(url).parseUnit(url);
            return parseUnit(activity, unit);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return false;
    }

    private interface ProtocalUnitParse {
        ProtocalUnit parseUnit(String url) throws URISyntaxException;
    }

    private interface FFtBannerExcutorProtocal {
        void excute(ProtocalUnit unit, final Activity activity);

        boolean isMatchProtocal(ProtocalUnit unit);
    }

    public static class ProtocalUnit {
        protected String aciton;
        protected String target;
        protected HashMap<String, String> arg;
        protected String self;
    }


}
