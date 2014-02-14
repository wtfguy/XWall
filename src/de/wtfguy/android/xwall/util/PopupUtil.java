package de.wtfguy.android.xwall.util;

import android.os.RemoteException;
import android.widget.Toast;
import de.wtfguy.android.xwall.XwallService;

public class PopupUtil {

	public static void showToastShort( String message, int duration ) throws RemoteException {
		XwallService.getClient().showToast( message, duration );
	}
	
	public static void showToastShort( String message ) throws RemoteException {
		XwallService.getClient().showToast( message, Toast.LENGTH_SHORT );
	}
	
	public static void showToastLong( String message ) throws RemoteException {
		XwallService.getClient().showToast( message, Toast.LENGTH_LONG );
	}
	
	public static void showFirewallLearningDialog() throws RemoteException {
		XwallService.getClient().showFirewallLearningDialog();
	}
	
}
