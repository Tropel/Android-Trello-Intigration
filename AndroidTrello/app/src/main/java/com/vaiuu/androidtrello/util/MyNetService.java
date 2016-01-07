package com.vaiuu.androidtrello.util;//package com.digitech.foodel.webserviceutil;
//
//import java.io.IOException;
//import java.util.Calendar;
//import java.util.List;
//import java.util.Locale;
//
//import org.json.JSONObject;
//
//import android.app.AlertDialog;
//import android.app.Dialog;
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.PendingIntent;
//import android.app.Service;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.location.Address;
//import android.location.Geocoder;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.IBinder;
//import android.util.Log;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.Toast;
//
//import com.android.volley.DefaultRetryPolicy;
//import com.android.volley.Request.Method;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonObjectRequest;
//import com.google.gson.Gson;
//import com.locallisting.app.R;
//import com.locallisting.app.activities.CustomerHomeActivity;
//import com.locallisting.app.activities.Driver_history_List_Details_Activity;
//import com.locallisting.app.entities.JobDetail;
//import com.locallisting.app.entities.JobDetailHolder;
//import com.locallisting.app.entities.JobDetailListNotificationHolder;
//import com.locallisting.app.entities.JobDetail_Detailed;
//import com.locallisting.app.entities.JobDetail_DetailedListNotificationHolder;
//import com.locallisting.app.utils.CommonTask;
//import com.locallisting.app.utils.CommonURL;
//import com.locallisting.app.utils.CommonValues;
//
//public class MyNetService extends Service implements LocationListener,
//		OnClickListener {
//
//	private static Runnable phoneLocationRunnable;
//	private Handler phoneLocationHandler;
//
//	Intent intent;
//	PendingIntent pendingIntent;
//	int notificationCount = 0, alarmCount = 0, collaborationCount = 0,
//			intServiceState = 0;
//	public static int phoneState = 0, phoneCallId = 0, lastSmsId = 0;
//
//	long previousTime = 0, priviousDownloadData = 0, currentDownloadData = 0,
//			priviousUploadData = 0, currentUploadData = 0,
//			lastPhoneInfoSyncTime = 0;
//
//	public static Location currentLocation;
//	public static String currentLocationName = "";
//	public static String currentCountryName = "";
//	public static int currentSignalStrenght = 31, previousSignal = 0;
//	public static int currentDownloadSpeedInKbPS = 0,
//			currentUploadSpeedInKbPS = 0;
//	public static int phoneId = 0;
//
//	boolean isGetPhoneInfo = false, isDowloadRunning = false,
//			isLocationUpdating = false, isDataInfoUpdating = false,
//			isPhoneInfoUpdating = false;
//
//	boolean isFirstCall = true;
//	public static String LastphoneState = "";
//
//	Button btnSubmit, btnCancel;
//
//	Gson gson = null;
//
//	private static final int NOTIFY_ME_ID = 1337;
//
//	public MyNetService() {
//
//	}
//
//	@Override
//	public IBinder onBind(Intent intent) {
//		throw new UnsupportedOperationException("Not yet implemented");
//	}
//
//	@Override
//	public void onCreate() {
//		phoneLocationHandler = new Handler();
//
//		Calendar cal = Calendar.getInstance();
//		cal.set(Calendar.DAY_OF_YEAR, -365);
//		lastPhoneInfoSyncTime = cal.getTimeInMillis();
//	}
//
//	private void updateCurrentLocation() {
//		isLocationUpdating = true;
//		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//		locationManager.requestLocationUpdates(
//				LocationManager.NETWORK_PROVIDER, 0, 0, this);
//		currentLocation = locationManager
//				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//		isLocationUpdating = false;
//
//		currentLocationName = "";
//		currentCountryName = "";
//		Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//
//		try {
//			List<Address> addresses = geocoder.getFromLocation(
//					currentLocation.getLatitude(),
//					currentLocation.getLongitude(), 1);
//			if (addresses != null && addresses.size() > 0) {
//				Address address = addresses.get(0);
//				for (int lineIndex = 0; lineIndex < address
//						.getMaxAddressLineIndex(); lineIndex++) {
//					currentLocationName = currentLocationName
//							+ address.getAddressLine(lineIndex) + ", ";
//				}
//				currentLocationName = currentLocationName
//						+ address.getLocality() + ", "
//						+ address.getCountryName();
//
//				currentCountryName = address.getCountryName();
//			}
//
//		} catch (IOException e) {
//
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void onStart(Intent intent, int startId) {
//	}
//
//	@Override
//	public int onStartCommand(Intent intent, int flags, int startId) {
//		/*
//		 * if (CommonValues.getInstance().notificationMessageTime == null)
//		 * CommonValues.getInstance().notificationMessageTime = new Date()
//		 * .getTime(); if (CommonValues.getInstance().LastAlarmTime == null)
//		 * CommonValues.getInstance().LastAlarmTime = new Date().getTime();
//		 */
//
//		try {
//			if (CommonTask.isOnline(this)) {
//
//				// We need to integrate this code later on
//				/*
//				 * if (CommonValues.getInstance().CollaborationMessageTime ==
//				 * 0l) { CommonValues.getInstance().CollaborationMessageTime =
//				 * new Date() .getTime(); }
//				 */
//
//				// updateCurrentLocation();
//
//				/*
//				 * CommonValues.getInstance().LastTrafficSnapshot = new
//				 * TrafficSnapshot( MyNetService.this);
//				 */
//
//				isGetPhoneInfo = false;
//
//				initThread();
//
//				Log.e("Restart", "On Restart");
//
//				if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1 + 1) {
//					Log.e("service", "START_REDELIVER_INTENT");
//					return START_REDELIVER_INTENT;
//				}
//				Log.e("service", "START_STICKY");
//
//				return START_REDELIVER_INTENT;
//
//			}
//
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//
//		return 0;
//	}
//
//	private void initThread() {
//		try {
//
//			phoneLocationRunnable = new Runnable() {
//
//				public void run() {
//					updateCurrentLocation();
//					if (CommonTask.isOnline(MyNetService.this)) {
//						isGetPhoneInfo = true;
//						runSync();
//					}
//					phoneLocationHandler.postDelayed(phoneLocationRunnable,
//							1 * 60000);
//
//				}
//			};
//			phoneLocationHandler.postDelayed(phoneLocationRunnable, 1 * 60000);
//
//		} catch (Exception e) {
//			e.getStackTrace();
//		}
//	}
//
//	private void runSync() {
//		if (CommonValues.getInstance().LoginUser != null) {
//			CheckAccptanceRequest();
//		}
//	}
//
//	private void CheckAccptanceRequest() {
//
//		gson = new Gson();
//		// making fresh volley request and getting json
//		if (CommonValues.getInstance().LoginUser != null) {
//
//			String URL_Login = String
//					.format(CommonURL.getInstance().GetAllPedingJobRequest,
//							String.valueOf(CommonValues.getInstance().LoginUser.UserReferenceID),
//							"2");
//
//			JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
//					URL_Login, null, new Response.Listener<JSONObject>() {
//
//						@Override
//						public void onResponse(JSONObject response) {
//							if (response != null) {
//								JobDetail_DetailedListNotificationHolder userRoot = gson.fromJson(
//										response.toString(),
//										JobDetail_DetailedListNotificationHolder.class);
//								if (userRoot != null
//										&& userRoot.JobDetailList != null) {
//									String jobText = "", DriverName = "", CustomerName = "";
//									for (int j = 0; j < userRoot.JobDetailList
//											.size(); j++) {
//
//										JobDetail_Detailed _jobDetail = userRoot.JobDetailList
//												.get(j);
//										if (_jobDetail.JobStatusID == 4) {
//										jobText = "Request confirmed from "
//												+ _jobDetail.FromPostcode
//												+ " to "
//												+ _jobDetail.ToPostcode
//														.toString()+" on "+String.valueOf(_jobDetail.Cost);
//										DriverName = String
//												.valueOf(_jobDetail.DriverMemberFullName);
//
//										final NotificationManager mgr = (NotificationManager) MyNetService.this
//												.getSystemService(Context.NOTIFICATION_SERVICE);
//										Notification note = new Notification(
//												R.drawable.locallisting,
//												jobText, System
//														.currentTimeMillis());
//										CustomerHomeActivity.isNotification = true;
//										CustomerHomeActivity.jobNotification = _jobDetail;
//										note.flags = Notification.DEFAULT_LIGHTS | Notification.FLAG_AUTO_CANCEL;
//										// This pending intent will open after
//										// notification click
//										PendingIntent i = PendingIntent
//												.getActivity(
//														MyNetService.this,
//														0,
//														new Intent(
//																MyNetService.this,
//																CustomerHomeActivity.class),
//														0);
//
//										note.setLatestEventInfo(
//												MyNetService.this,
//												DriverName, jobText, i);
//
//										// After uncomment this line you will
//										// see number of notification arrived
//										// note.number=2;
//										mgr.notify(NOTIFY_ME_ID+j, note);
//										}
//										 else if (_jobDetail.JobStatusID == 6) {
//												jobText = "Request for fare from "
//														+ _jobDetail.FromPostcode
//														+ " to "
//														+ _jobDetail.ToPostcode
//																.toString()
//														+ " on "
//														+ String.valueOf(_jobDetail.Cost)
//														+ " has accepted";
//												DriverName = String
//														.valueOf(_jobDetail.DriverMemberFullName);
//												CustomerName = String
//														.valueOf(_jobDetail.CustomerMemberFullName);
//												final NotificationManager mgr = (NotificationManager) MyNetService.this
//														.getSystemService(Context.NOTIFICATION_SERVICE);
//												Notification note = new Notification(
//														R.drawable.locallisting,
//														jobText,
//														System.currentTimeMillis());
//												// DriverHomeActivity.isNotification
//												// = true;
//												// DriverHomeActivity.jobNotification
//												// = _jobDetail;
//												note.flags = Notification.DEFAULT_LIGHTS
//														| Notification.FLAG_AUTO_CANCEL;
//												// This pending intent will open
//												// after
//												// notification click
//
//
//												Driver_history_List_Details_Activity.jobDetailsId=_jobDetail.JobDetailID;
//
//												PendingIntent i = PendingIntent
//														.getActivity(
//																MyNetService.this,
//																0,
//																new Intent(
//																		MyNetService.this,
//																		Driver_history_List_Details_Activity.class),
//																0);
//
//												note.setLatestEventInfo(
//														MyNetService.this,
//														"Job Accepted By "
//																+ CustomerName,
//														jobText, i);
//
//												// After uncomment this line you
//												// will
//												// see number of notification
//												// arrived
//												// note.number=2;
//												mgr.notify(NOTIFY_ME_ID + j, note);
//											}
//
//
//										 else if (_jobDetail.JobStatusID == 5) {
//												jobText = "Request for fare from "
//														+ _jobDetail.FromPostcode
//														+ " to "
//														+ _jobDetail.ToPostcode
//																.toString()
//														+ " Rejected By "+String
//														.valueOf(_jobDetail.DriverMemberFullName);
//												DriverName = String
//														.valueOf(_jobDetail.DriverMemberFullName);
//												CustomerName = String
//														.valueOf(_jobDetail.CustomerMemberFullName);
//												final NotificationManager mgr = (NotificationManager) MyNetService.this
//														.getSystemService(Context.NOTIFICATION_SERVICE);
//												Notification note = new Notification(
//														R.drawable.locallisting,
//														jobText,
//														System.currentTimeMillis());
//												// DriverHomeActivity.isNotification
//												// = true;
//												// DriverHomeActivity.jobNotification
//												// = _jobDetail;
//												note.flags = Notification.DEFAULT_LIGHTS
//														| Notification.FLAG_AUTO_CANCEL;
//												// This pending intent will open
//												// after
//												// notification click
//
//
//												Driver_history_List_Details_Activity.jobDetailsId=_jobDetail.JobDetailID;
//
//												PendingIntent i = PendingIntent
//														.getActivity(
//																MyNetService.this,
//																0,
//																new Intent(
//																		MyNetService.this,
//																		Driver_history_List_Details_Activity.class),
//																0);
//
//												note.setLatestEventInfo(
//														MyNetService.this,
//														"Job Rejected By "
//																+ DriverName,
//														jobText, i);
//
//												// After uncomment this line you
//												// will
//												// see number of notification
//												// arrived
//												// note.number=2;
//												mgr.notify(NOTIFY_ME_ID + j, note);
//											}
//										// Should Implement here Dialog
//									}
//
//								}
//							}
//						}
//					}, new Response.ErrorListener() {
//
//						@Override
//						public void onErrorResponse(VolleyError error) {
//						}
//					});
//
//			jsonReq.setRetryPolicy(new DefaultRetryPolicy(
//					DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0,
//					DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//			// Adding request to volley request queue
//			LocalListingApplication.getInstance().addToRequestQueue(jsonReq);
//		}
//
//	}
//
//
//	@Override
//	public void onDestroy() {
//		removeAllHandlerCallbacks();
//		// unregisterReceiver(receiver);
//	}
//
//	/**
//	 *
//	 */
//	private void removeAllHandlerCallbacks() {
//
//		phoneLocationHandler.removeCallbacks(phoneLocationRunnable);
//	}
//
//	@Override
//	public void onLocationChanged(Location arg0) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onProviderDisabled(String arg0) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onProviderEnabled(String arg0) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onClick(View arg0) {
//		// TODO Auto-generated method stub
//
//	}
//}
