package de.wtfguy.android.xwall;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.view.WindowManager;
import de.wtfguy.android.xwall.util.Log;
import de.wtfguy.android.xwall.util.PopupUtil;

public class XwallService {

	private static Log log = Log.getLogger();
	
	private static boolean registered = false;
	private static Thread worker = null;
	private static Handler handler = null;
	
	private static final int cCurrentVersion = 100;
	private static final String cServiceName = "xwall" + cCurrentVersion;
	private static IXwallService client = null;
	
	public static void register() {
		try {
			// Start a worker thread
			worker = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Looper.prepare();
						handler = new Handler();
						Looper.loop();
					} catch (Throwable ex) {
						log.error(null, ex);
					}
				}
			});
			worker.start();

			// Register xwall service
			// @formatter:off
			// public static void addService(String name, IBinder service)
			// public static void addService(String name, IBinder service, boolean allowIsolated)
			// @formatter:on
			Class<?> cServiceManager = Class.forName("android.os.ServiceManager");
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				Method methodAddService = cServiceManager.getDeclaredMethod( "addService", String.class, IBinder.class, boolean.class );
				log.info( "Invoking " + methodAddService );
				methodAddService.invoke( null, cServiceName, xwallService, true);
			} else {
				Method methodAddService = cServiceManager.getDeclaredMethod( "addService", String.class, IBinder.class );
				log.info( "Invoking " + methodAddService );
				methodAddService.invoke( null, cServiceName, xwallService );
			}

			log.info( "Service registered name=" + cServiceName );
			registered = true;
		} catch (Throwable ex) {
			log.error(null, ex);
		}
	}
	
	
	public static boolean checkClient() {
		// Runs client side
		try {
			IXwallService client = getClient();
			if (client != null)
				return (client.getVersion() == cCurrentVersion);
		} catch (RemoteException ex) {
			log.error(null, ex);
		}
		return false;
	}
	
	
	public static IXwallService getClient() {
		// Runs client side
		if (client == null)
			try {
				// public static IBinder getService(String name)
				Class<?> cServiceManager = Class.forName("android.os.ServiceManager");
				Method mGetService = cServiceManager.getDeclaredMethod("getService", String.class);
				client = IXwallService.Stub.asInterface((IBinder) mGetService.invoke(null, cServiceName));
			} catch (Throwable ex) {
				log.error(null, ex);
			}

		// Disable disk strict mode
		try {
			ThreadPolicy oldPolicy = StrictMode.getThreadPolicy();
			ThreadPolicy newpolicy = new ThreadPolicy.Builder(oldPolicy).permitDiskReads().permitDiskWrites().build();
			StrictMode.setThreadPolicy(newpolicy);
		} catch (Throwable ex) {
			log.error(null, ex);
		}
		return client;
	}
	
	
	
	
	
	
	
	private static final IXwallService.Stub xwallService = new IXwallService.Stub() {
		
		private List<String> blockedURLs = new ArrayList<String>();
		
		private Context getContext() {
			// public static ActivityManagerService self() frameworks/base/services/java/com/android/server/am/ActivityManagerService.java
			try {
				Class<?> cam = Class.forName("com.android.server.am.ActivityManagerService");
				Object am = cam.getMethod("self").invoke(null);
				if (am == null)
					return null;
				return (Context) cam.getDeclaredField("mContext").get(am);
			} catch (Throwable ex) {
				log.error(null, ex);
				return null;
			}
		}

		@Override
		public int getVersion() throws RemoteException {
			return cCurrentVersion;
		}
		
		@Override
		public void showConnectionAlertDialog( final String title, final String positiveValue, final String negativeValue, final String message, final String value ) throws RemoteException {
			final Context context = getContext();
			if (context != null && handler != null) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						AlertDialog.Builder builder = new AlertDialog.Builder( context );
						builder.setTitle( title );
						builder.setIcon( android.R.drawable.ic_dialog_alert );
						builder.setMessage( message );
						builder.setPositiveButton( positiveValue, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								blockedURLs.add( value );
								log.warn( "Number of blocked URLs: " + blockedURLs.size() );
							}
						});
						builder.setNegativeButton( negativeValue, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
							}
						});
						AlertDialog dialog = builder.create();
						
						dialog.getWindow().setType( WindowManager.LayoutParams.TYPE_PHONE );
						dialog.getWindow().addFlags( WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN );
						dialog.getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN );
						dialog.setCancelable( false );
						dialog.setCanceledOnTouchOutside( false );
						dialog.show();
					}
				});
			}
		}
		
		@Override
		public void showToast( final String message, final int duration ) throws RemoteException {
			final Context context = getContext();
			if (context != null && handler != null) {
				handler.post(new Runnable() {
					@Override
					public void run() {
						PopupUtil.showToast(context, message, duration);
					}
				});
			}
		}
		
	};
	
}
