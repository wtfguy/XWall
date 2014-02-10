package de.wtfguy.android.xwall;

import java.io.FileDescriptor;
import java.lang.reflect.Method;
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
		Class[] methodParams = new Class[]{ FileDescriptor.class, InetAddress.class, Integer.class };
		
		
		/* revo89 suggested not to use findMethodBestMatch but this is currently the only way to not get MethodNotFoundException */
		try {
			Class<?> clazz = XposedHelpers.findClass( className, null);
//			Class<?> clazz = XposedHelpers.findClass("libcore.io.IoBridge", null);
			XposedBridge.log(  clazz.getName() + " found!" );
			
			Method method = XposedHelpers.findMethodBestMatch( clazz, methodName, methodParams );
//			Method method = clazz.getDeclaredMethod( methodName, methodParams );
//			method.setAccessible( true );
			XposedBridge.log( method.getName() + " found!" );
			
			XposedBridge.hookMethod( method, getXcMethodHook() );
			
		} catch (Throwable ex) {
			XposedBridge.log( ex );
		}
		
		
//		Class clazz = Class.forName( "libcore.io.ForwardingOs" );
//		String methodName = "connect";
//		XposedHelpers.findAndHookMethod( clazz, methodName, FileDescriptor.class, InetAddress.class, Integer.class, getXcMethodHook() );
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
