package de.wtfguy.android.xwall;

import static de.robv.android.xposed.XposedHelpers.findClass;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodHook.Unhook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import de.wtfguy.android.xwall.hook.AbstractHook;
import de.wtfguy.android.xwall.hook.HookBlockGuardOsConnect;
import de.wtfguy.android.xwall.util.Log;

public class XWall implements IXposedHookZygoteInit, IXposedHookLoadPackage {

	public static final String LOG_TAG = "XWall";
	
	private Log log = Log.getLogger();
	
	@Override
	public void initZygote( StartupParam startupParam ) throws Throwable {
		log.info( "Init hooking for module " + startupParam.modulePath );
		
		registerServices();
		
		hook( new HookBlockGuardOsConnect( null ) );	//TODO get a context from somewhere
	}

	
	@Override
	public void handleLoadPackage( LoadPackageParam lpparam ) throws Throwable {
		//currently unused
	}

	
	private void registerServices() {
		// System server
		try {
			// frameworks/base/services/java/com/android/server/SystemServer.java
			Class<?> cSystemServer = findClass("com.android.server.SystemServer", null);
			Method mMain = cSystemServer.getDeclaredMethod("main", String[].class);
			XposedBridge.hookMethod(mMain, new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					XwallService.register();
				}
			});
		} catch (Throwable ex) {
			log.error(null, ex);
		}
	}
	
	
	private Unhook hook( final AbstractHook hook ) {
		try {
			List<Object> xposedParameters = new ArrayList<Object>();
			xposedParameters.addAll( Arrays.asList( hook.getMethodParameters() ) );
			xposedParameters.add( new XC_MethodHook() {
				@Override
				protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
					log.debug( "delegating beforeHookedMethod!" );
					hook.beforeHookedMethod(param);
				}
				
				@Override
				protected void afterHookedMethod(MethodHookParam param) throws Throwable {
					log.debug( "delegating afterHookedMethod!" );
					hook.afterHookedMethod(param);
				}
			} );
			return XposedHelpers.findAndHookMethod( Class.forName( hook.getClassName() ), hook.getMethodName(), xposedParameters.toArray() );
		} catch( ClassNotFoundException e ) {
			log.error( "Unable to register Hook", e );
			return null;
		}
	}
	
}
