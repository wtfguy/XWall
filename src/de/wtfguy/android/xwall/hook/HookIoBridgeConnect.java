package de.wtfguy.android.xwall.hook;

import java.io.FileDescriptor;
import java.net.InetAddress;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class HookIoBridgeConnect extends AbstractHook {

	public HookIoBridgeConnect() {
		super( "libcore.io.IoBridge", "connect", FileDescriptor.class, InetAddress.class, int.class );
	}

	
	@Override
	public void beforeHookedMethod(MethodHookParam param) throws Throwable {
		super.beforeHookedMethod(param);
	}
	
}
