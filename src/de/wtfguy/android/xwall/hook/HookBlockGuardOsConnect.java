package de.wtfguy.android.xwall.hook;

import java.io.FileDescriptor;
import java.net.InetAddress;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.wtfguy.android.xwall.util.Log;
import de.wtfguy.android.xwall.util.PopupUtil;

/**
 * https://android.googlesource.com/platform/libcore/+/master/luni/src/main/java/libcore/io/BlockGuardOs.java
 * public void connect(FileDescriptor fd, InetAddress address, int port) throws ErrnoException, SocketException;
 */
public class HookBlockGuardOsConnect extends AbstractHook {

	private Log log = Log.getLogger();
	
	public HookBlockGuardOsConnect() {
		super( "libcore.io.BlockGuardOs", "connect", FileDescriptor.class, InetAddress.class, int.class );
	}

	
	@Override
	public void beforeHookedMethod( MethodHookParam param ) throws Throwable {
		super.beforeHookedMethod(param);
		FileDescriptor fd = (FileDescriptor)param.args[0];
		InetAddress address = (InetAddress)param.args[1];
		int port = (Integer)param.args[2];
		log.warn( className + " is trying to connect to " + address.toString() + ":" + port );
		PopupUtil.showToastLong( className + " is trying to connect to " + address.toString() + ":" + port );

		if( address.toString().contains("137.116.209.193") ) {
			PopupUtil.showToastLong( "test.de is blocked" );
			param.setResult(null);	//should prevent the call to the original method but this seems to be not enough :/
		}
	}
	
	
}