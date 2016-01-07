package com.vaiuu.androidtrello.home;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vaiuu.androidtrello.R;
import com.vaiuu.androidtrello.util.Constant;
import com.vaiuu.androidtrello.util.CustomLoaderDialog;
import com.vaiuu.androidtrello.util.IVolleyRespose;
import com.vaiuu.androidtrello.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***********************************
 * Created by Musafir Ali on 8/3/2015.
 **********************/
public class BaseActivity extends Activity {
    /***************************
     * call for volley service
     *****************/
    public RequestQueue queue;
    StringRequest jsStrRequest, addRequest;
    String res = null;
    public CustomLoaderDialog cm = null;
    public static final int RESPONSE_OK = 200;
    public static final int RESPONSE_ERROR = 404;
    BaseActivity mCommonActivity;
    public boolean isanimate = false;
    ToastUtil toastUtil;


    private final Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
        toastUtil = new ToastUtil(BaseActivity.this);
    }

    /***************************************************************
     * @return Return Scaled bitmap as per given maxHieght and maxWidth
     ************************************/
    private Bitmap scaleBitMap(Bitmap targetBitmap, int maxX, int maxY, Display display) {
        int width = targetBitmap.getWidth();
        int height = targetBitmap.getHeight();
        int targetHeigth, targetWidth;
        float widthFactor = (float) maxX / width;
        float heightFactor = (float) maxY / height;
        float scaleFactor;
        if (widthFactor < heightFactor)
            scaleFactor = widthFactor;
        else
            scaleFactor = heightFactor;
        float scaledWidth = width * scaleFactor;
        float scaledHeight = height * scaleFactor;

        targetHeigth = (int) scaledHeight;
        targetWidth = (int) scaledWidth;

        targetWidth = targetWidth - (targetWidth) / 100;
        targetHeigth = targetHeigth - (targetHeigth) / 100;
        return Bitmap.createScaledBitmap(targetBitmap, targetWidth, targetHeigth, true);
    }

    public void printToastAlert(Context context, int id) {
        Toast.makeText(context, "" + context.getResources().getString(id), Toast.LENGTH_SHORT).show();
    }

    /*****************
     * Email Validation
     **********/
    public static boolean isEmailValid(String email) {
        boolean isValid = false;
        //  String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        String expression = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /*************************
     * Hide Keyboard Focus
     **************/
    public void hideKeyBoardFromCurrentFocus(Activity activity) {
        // TODO Auto-generated method stub
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {

        }
    }

    /******************
     * Check Internet
     ***********/
    public boolean isOnline(Context cContext) {
        ConnectivityManager cm = (ConnectivityManager) cContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    /*****************
     * For Custom Font
     *************/
    public Typeface getGE_SS_Text_Light_Typeface(AssetManager assetManager) {
        Typeface font = Typeface.createFromAsset(assetManager, "fonts/GE_SS_Text_Light.otf");
        return font;
    }

    public Typeface getSourceSansPro_Light_0_Typeface(AssetManager assetManager) {
        Typeface font = Typeface.createFromAsset(assetManager, "fonts/SourceSansPro-Light_0.ttf");
        return font;
    }

    /***********************
     * Show Toast Alert
     ****************/
    public void printToastShort(Context context, String string) {
        Toast.makeText(context, "" + string, Toast.LENGTH_SHORT).show();
    }

    public String prepareWebserviceRequestHtml(String finalURL,String methodname, String[] keys, String[] values) {
        String str1 = "=";
        String str2 = "?";
        String str3 = "&";
        String final_request = "";

        if (keys != null) {
            for (int i = 0; i < keys.length; i++) {
                if (i == (keys.length - 1)) {
                    final_request = final_request + keys[i] + str1 + values[i];
                } else {
                    final_request = final_request + keys[i] + str1 + values[i] + str3;
                }
            }
            final_request = finalURL + methodname + str2 + final_request;
        } else {
            final_request = finalURL + methodname;
        }
        if (final_request.contains(" ")) {
            final_request = final_request.replace(" ", "%20");
        }
        return final_request;
    }

    @SuppressWarnings("unused")
    public String prepareWebserviceRequest(String methodname, String[] keys, String[] values) throws JSONException {
        String retStr = null;
        String strParams = null;
        JSONObject json = new JSONObject();
        for (int i = 0; i < keys.length; i++) {
            json.put(keys[i], values[i]);
        }

        JSONObject fJson = new JSONObject();
        fJson.put("method_name", methodname);
        fJson.put("body", json);
        retStr = fJson.toString();
        return retStr;
    }

    /**
     * This method use for get Json request for call web method
     *
     * @param mMethodname Name of web method for call
     * @param mMap        key value pair for preper json request
     */
    public String getWebservicejsObjRequestforvolley(String mMethodname, HashMap<String, String> mMap) throws JSONException {
        String retStr = null;
        //String strParams = null;
        // map.put("device_id", Prefs.getDeviceTokenPref(BaseActivity.this));
        JSONObject json = new JSONObject();

        if (mMap.size() > 0) {
            for (Map.Entry<String, String> e : mMap.entrySet()) {
                String key = e.getKey();
                String value = e.getValue();
                json.put(key, value);
            }
        }
        JSONObject fJson = new JSONObject();
        fJson.put("method_name", mMethodname);
        if (mMap != null)
            fJson.put("body", json);
        retStr = fJson.toString();
        return retStr;
    }

    public String prepareWebserviceRequestforvolly(String methodname, String[] keys, String[] values) throws JSONException {
        String retStr = null;
        //String strParams = null;
        JSONObject json = new JSONObject();
        if (keys != null) {
            for (int i = 0; i < keys.length; i++) {
                json.put(keys[i], values[i]);
            }
        }
        JSONObject fJson = new JSONObject();
        fJson.put("method_name", methodname);
        if (keys != null)
            fJson.put("body", json);
        retStr = fJson.toString();
        return retStr;
    }


    /*************************************************************************************************
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp      A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     *************************************************************************************************/
    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    /*************************************************************************************************
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px      A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     ***************************************************************************************************/
    public static float convertPixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }


    public void hideKeybord(View view) {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.RESULT_UNCHANGED_SHOWN);
        } catch (Exception e) {
        }
    }

    /***************************
     * Button Click Animation
     ****************/
    public static void buttonClickEffect(View v) {
        AlphaAnimation obja = new AlphaAnimation(1.0f, 0.3f);
        obja.setDuration(10);
        obja.setFillAfter(false);
        v.startAnimation(obja);
    }

    /***********************
     * Get Statusbar Height
     ************/
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /*******************************
     * check tablet or phone method
     ************************/
    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
        //..................................
    }


    public void printAlert(Context context) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("" + getResources().getString(R.string.app_name));
        //alert.setMessage(""+getResources().getString(R.string.blockuser));
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.create();
        alert.show();
    }

    /**************************************************************************************************
     * <h1>  Use for calling volley webService </h1>
     *
     * @param cContext         Context of activity from where you call the webService
     * @param mMethodType      Should be POST or GET
     * @param mMethodname      Name of the method you want to call
     * @param URL              URL of your webService
     * @param mMap             Key Values pairs
     * @param initialTimeoutMs Timeout of webService in milliseconds
     * @param shouldCache      Web API response are stored in catch(true) or not(false)
     * @param maxNumRetries    maximum number in integer for Retries to execute or call webService
     * @param isCancelable     set true if you set cancel progressDialog by user event
     * @param aActivity        pass your activity object
     ****************************************************************************************************/
    public void callVolley(final Context cContext, String mMethodType, final String mMethodname, String URL, final HashMap<String, String> mMap, int initialTimeoutMs, boolean shouldCache, int maxNumRetries, final Boolean isProgressDailogEnable, Boolean isCancelable, final BaseActivity aActivity) {
        // TODO Auto-generated method stub
        if (!isOnline(cContext)) {
            printToastShort(aActivity, Constant.PleaseCheckInternetConnection);
        } else {
            int reqType = 0;
            String RequestURL = URL.trim();
            queue = Volley.newRequestQueue(cContext);
            if (cm != null) {
                if (cm.isShowing())
                    cm.hide();
            }
            cm = new CustomLoaderDialog(cContext);
            if (isProgressDailogEnable)
                cm.show(isCancelable);

            if (mMethodType.trim().equalsIgnoreCase("GET"))
                reqType = com.android.volley.Request.Method.GET;
            else if (mMethodType.trim().equalsIgnoreCase("POST"))
                reqType = com.android.volley.Request.Method.POST;

            if (RequestURL.equals(null))
                RequestURL = Constant.WEBSERVICE_URL;
            else
                RequestURL = URL;

            Log.v(Constant.TAG, RequestURL);
            Log.v(Constant.TAG + reqType, reqType + "");

            jsStrRequest = new StringRequest(reqType, RequestURL, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        Log.i(Constant.TAG, "Api Response Are " + response);
                        if (cm.isShowing())
                            cm.hide();
                        if (response == null) {
                            IVolleyRespose iVolleyRespose = (IVolleyRespose) aActivity;
                            iVolleyRespose.onVolleyResponse(RESPONSE_ERROR, response, mMethodname);
                            res = response;
                        } else if (response != null) {
                            IVolleyRespose iVolleyRespose = (IVolleyRespose) aActivity;
                            iVolleyRespose.onVolleyResponse(RESPONSE_OK, response, mMethodname);
                            res = response;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError arg0) {
                    // TODO Auto-generated method stub
                    res = "";
                    if (cm.isShowing())
                        cm.hide();
                    IVolleyRespose iVolleyError = (IVolleyRespose) aActivity;
                    iVolleyError.onVolleyError(RESPONSE_ERROR, "Error", mMethodname);
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    String strRequest = "";
                    try {
                        strRequest = getWebservicejsObjRequestforvolley(mMethodname, mMap);
                        strRequest = strRequest.replace(" ", "%20");
                        Log.d("strRequest", "" + strRequest);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("json", strRequest);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/x-www-form-urlencoded");
                    return params;
                }
            };
            jsStrRequest.setTag(mMethodname);
            jsStrRequest.setShouldCache(shouldCache);
            jsStrRequest.setRetryPolicy(new DefaultRetryPolicy(initialTimeoutMs, maxNumRetries, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(jsStrRequest);
        }
    }

    /***************************************
     * Convert character to unicode formate
     *
     * @return
     *************************/
    public static String unicodeEscaped(String s) {
        s = s.replace("__u000a__", "\n");
        return s.toString();
    }

    /**
     * convert uni-code to sting formate
     *
     * @param s
     * @return
     */
    public static String convertUnicode(String s) {
        s = s.replace("\n", "__u000a__");
        return s.toString();
    }

    /************************************************
     * @param timeStamp   timestamp
     * @param dateFormate Like "yyyy-MM-dd HH:mm:ss"
     * @return
     ****************************/
    @SuppressLint("SimpleDateFormat")
    public String getDate(long timeStamp, String dateFormate) {
        SimpleDateFormat objFormatter = new SimpleDateFormat(dateFormate);
        objFormatter.setTimeZone(TimeZone.getDefault());
        Calendar objCalendar = Calendar.getInstance(TimeZone.getDefault());
        objCalendar.setTimeInMillis(timeStamp * 1000);// edit
        String result = objFormatter.format(objCalendar.getTime());
        objCalendar.clear();
        return result;
    }


    /***********************
     * Show Exit Alert
     *
     * @param activity
     * @param mDisplayMessage
     ***************************/

    public void showTwoButtonExitDailog(final Activity activity, String mDisplayMessage) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setMessage(mDisplayMessage);
        builder1.setCancelable(true);
        builder1.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        builder1.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }


    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(0, 0);

    }

    @Override
    protected void onPause() {
        // this.finish();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        this.finish();
        super.onDestroy();
    }


}