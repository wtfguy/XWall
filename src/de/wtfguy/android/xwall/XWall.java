package de.wtfguy.android.xwall;

import java.io.FileDescriptor;
import java.net.InetAddress;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import de.wtfguy.android.xwall.config.Configurator;

public class XWall implements IXposedHookZygoteInit, IXposedHookLoadPackage {

	private Configurator configurator;
	
	@Override
	public void initZygote( StartupParam startupParam ) throws Throwable {
		XposedBridge.log( "Module: " + startupParam.modulePath );
	
		configurator = new Configurator();
		configurator.loadRules();
		configurator.logRules();
		
		XposedBridge.log(  "HOOKing " + startupParam.modulePath );
		
//		String className = "libcore.io.IoBridge";
		String className = "libcore.io.ForwardingOs";
		String methodName = "connect";
		
		Class<?> clazz = Class.forName( className );
		XposedHelpers.findAndHookMethod( clazz, methodName, FileDescriptor.class, InetAddress.class, int.class, getXcMethodHook() );
	}

	
	@Override
	public void handleLoadPackage( LoadPackageParam lpparam ) throws Throwable {
		//currently unused
	}

	
	
	private XC_MethodHook getXcMethodHook() {
		 return new XC_MethodHook() {
			 @Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				 XposedBridge.log(  "before connect..." );
//				 throw new SocketException( "Blocked by Firewall" );
			}
			 
			 @Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				 XposedBridge.log(  "after connect..." );
			}
		 };
	}
}
