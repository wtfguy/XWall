package de.wtfguy.android.xwall.util;

import android.content.Context;
import android.widget.Toast;

public class PopupUtil {
	
	public static void showToast( Context context, String message, int duration ) {
		Toast.makeText(context, message, duration).show();
	}
	
	public static void showToastShort( Context context, String message ) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT ).show();
	}
	
	public static void showToastLong( Context context, String message ) {
		Toast.makeText(context, message, Toast.LENGTH_LONG ).show();
	}
	
	public static void showFirewallLearningDialog( Context context ) {
		//TODO
	}
	
}
