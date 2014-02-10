package de.wtfguy.android.xwall;

import java.io.FileDescriptor;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.SocketException;

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
	
		if( startupParam.modulePath.contains( XWall.class.getPackage().getName() ) ) {

			configurator = new Configurator();
			configurator.loadRules();
			configurator.logRules();
			
			XposedBridge.log(  "HOOKing " + startupParam.modulePath );
			
			try {
//				Class<?> clazz = XposedHelpers.findClass("libcore.io.ForwardingOs", null);
				Class<?> clazz = XposedHelpers.findClass("libcore.io.IoBridge", null);
				XposedBridge.log(  clazz.getName() + " found!" );
				Method method = XposedHelpers.findMethodBestMatch( clazz, "connect", FileDescriptor.class, InetAddress.class, Integer.class );
				XposedBridge.log(  method.getName() + " found!" );
				XposedBridge.hookMethod( method, getXcMethodHook() );
			} catch (Throwable ex) {
				XposedBridge.log( ex );
			}
			
		}
	}

	
	private XC_MethodHook getXcMethodHook() {
		 return new XC_MethodHook() {
			 @Override
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
				 XposedBridge.log(  "before connect..." );
				 throw new SocketException( "Blocked by Firewall" );
			}
			 
			 @Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				 XposedBridge.log(  "after connect..." );
			}
		 };
	}
	
	
	
	@Override
	public void handleLoadPackage( LoadPackageParam lpparam ) throws Throwable {
		
	}
	
}
