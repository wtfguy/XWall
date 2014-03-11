package de.wtfguy.android.xwall.hook;

import android.content.Context;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.wtfguy.android.xwall.util.Log;

public abstract class AbstractHook {

	private Log log = Log.getLogger();
	
	private Context context;
	
	protected String className;
	protected String methodName;
	protected Class<?>[] methodParameters;
	
	public AbstractHook( Context context, String className, String methodName, Class<?>... methodParameters ) {
		this.context = context;
		this.className = className;
		this.methodName = methodName;
		this.methodParameters = methodParameters;
	}

	protected Context getContext() {
		return context;
	}
	
	public void beforeHookedMethod( MethodHookParam param ) throws Throwable {
		log.debug( "before method " + param.method.getName() );
	}
	
	
	public void afterHookedMethod( MethodHookParam param ) throws Throwable {
		log.debug( "after method " + param.method.getName() );
	}
	
	public String getClassName() {
		return className;
	}
	
	public String getMethodName() {
		return methodName;
	}
	
	public Class<?>[] getMethodParameters() {
		return methodParameters;
	}

}
