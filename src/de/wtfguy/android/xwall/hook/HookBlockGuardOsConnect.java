package de.wtfguy.android.xwall.hook;

import java.io.FileDescriptor;
import java.net.InetAddress;

import android.content.Context;
import android.widget.Toast;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.wtfguy.android.xwall.XwallService;
import de.wtfguy.android.xwall.util.Log;

/**
 * https://android.googlesource.com/platform/libcore/+/master/luni/src/main/java/libcore/io/BlockGuardOs.java
 * public void connect(FileDescriptor fd, InetAddress address, int port) throws ErrnoException, SocketException;
 */
public class HookBlockGuardOsConnect extends AbstractHook {

	private Log log = Log.getLogger();
	
	public HookBlockGuardOsConnect( Context context ) {
		super( context, "libcore.io.BlockGuardOs", "connect", FileDescriptor.class, InetAddress.class, int.class );
	}

	
	@Override
	public void beforeHookedMethod( MethodHookParam param ) throws Throwable {
		super.beforeHookedMethod(param);
		FileDescriptor fd = (FileDescriptor)param.args[0];
		InetAddress address = (InetAddress)param.args[1];
		int port = (Integer)param.args[2];
		
		String msg = className + " is trying to connect to " + address.toString() + ":" + port;
		
		log.warn( msg );
		XwallService.getClient().showConnectionAlertDialog(
				/*R.string.dialog_alert_title_connect,*/"Connection Alert", 
				/*R.string.dialog_button_block,*/"Block",
				/*R.string.dialog_button_allow,*/"Allow",
				msg, 
				address.toString()+":"+port);
		
		if( address.toString().contains("137.116.209.193") ) {
			XwallService.getClient().showToast( "137.116.209.193 is blocked", Toast.LENGTH_LONG );
			param.setResult(null);
			param.setThrowable( new Exception( "The URL 137.116.209.193 is blocked!" ) );
		}
	}
	
	
}