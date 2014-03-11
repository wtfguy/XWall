package de.wtfguy.android.xwall.hook;

import java.io.FileDescriptor;
import java.net.InetAddress;

import android.content.Context;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.wtfguy.android.xwall.util.Log;

/**
 * https://android.googlesource.com/platform/libcore/+/master/luni/src/main/java/libcore/io/Os.java
 * public void connect(FileDescriptor fd, InetAddress address, int port) throws ErrnoException, SocketException;
 */
public class HookOsConnect extends AbstractHook {

	private Log log = Log.getLogger();
	
	public HookOsConnect( Context context ) {
		super( context, "libcore.io.Os", "connect", FileDescriptor.class, InetAddress.class, int.class );
	}

	
	@Override
	public void beforeHookedMethod( MethodHookParam param ) throws Throwable {
		super.beforeHookedMethod(param);
		FileDescriptor fd = (FileDescriptor)param.args[0];
		InetAddress address = (InetAddress)param.args[1];
		int port = (Integer)param.args[2];
		
		log.warn( className + " is trying to connect to " + address.toString() + ":" + port );
	}
	
}
