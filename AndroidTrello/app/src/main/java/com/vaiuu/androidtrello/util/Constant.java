package com.vaiuu.androidtrello.util;

public class Constant {
	public static String FACEBOOKID="";
	//public static String WEBSERVICE_URL="http://82.165.197.66:3000/foodelapi/";
	public static String WEBSERVICE_URL="https://api.trello.com/1/";
	public static String BASE_URL="";
	public static final String PleaseCheckInternetConnection = "Please check internet connection.";
	public static final String POST_REQUEST="POST";
	public static final String GET_REQUEST="GET";
	/**
	 * VOLLEY_RETRY_COUNT set count no. of call web method with volley by Default is 1.
	 * */
	public static final int VOLLEY_RETRY_COUNT=1;
	public static String GETLOGIN_ACTION="driverlogin";
	public static String GETLOGIN_ACCESS_ACTION="driverloginsuccess";
	public static String GETLOGOUT_ACTION="driverlogout";
	public static String GETVIHICLEINFO_ACTION="vehicleinfo";
	public static String GETBANKINFO_ACTION="bankinfo";
	public static String GETOUTLET_ACTION="outletinfo";
	public static String SETDROPPED_ACTION="order";
	public static String ORDER_CENCEL="order";
	public static String GETPROFILE_ACTION="drivernfo";
	public static String UPDATE_ACTION="driverprofileupdate";
	public static String UPDATEBANK_ACTION="bankinfoupdate";
	public static String TRELLO_BOARD="all";
	public static String ACEPT_ACTION="jobid";
	public static String UPDATE_PROFILE_IMAGE_ACTION="driverimageupdate";
	public static String VIHICLE_INFO_UPDATE="vehicleinfoupdate";
	public static String SET_VEHICLE_INFO="vehicleinfoinsert";
	public static String ACCEPT_REQUEST="job";
	public static String GETFORGOT_PASSWORD="forgetpin";
	public static String REGISTRATION_UPDATE="driverregistration";


	public static String DRIVER_CANCEL_REQUEST = "driverjobcancel";
	/**
	 * CALL_TIME_OUT set web service calling time out
	 * */
	public static final int CALL_TIME_OUT=50000;
	public static final int CALL_TIME_OUT_LONG=100000;
	/**
	 * @param  shouldCache   webapi response are stored in catch(true) or not(false)
	 **/
	public static final Boolean SHOULD_CACHE=false;
	/***
	 * @param 	IS_PROGRESSDAILOG_SHOW  set true if you set progressDialog by default true
	 * */
	public static final Boolean IS_PROGRESSDAILOG_SHOW=true;
	/**
	 *  @param 	IS_PROGRESSDAILOG_CANCELABLE  set true if you set calcel progressDialog by user event
	 * */
	public static final Boolean IS_PROGRESSDAILOG_CANCELABLE=false;
	public static final String TAG = "FOODEL DRIVER";
	public static String HEADER="";
	

}
