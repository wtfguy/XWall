package de.wtfguy.android.xwall.hook;

import java.io.FileDescriptor;
import java.net.InetAddress;

import android.content.Context;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;

public class HookIoBridgeConnect extends AbstractHook {

	public HookIoBridgeConnect( Context context ) {
		super( context, "libcore.io.IoBridge", "connect", FileDescriptor.class, InetAddress.class, int.class );
	}

	
	@Override
	public void beforeHookedMethod(MethodHookParam param) throws Throwable {
		super.beforeHookedMethod(param);
	}
	
}
