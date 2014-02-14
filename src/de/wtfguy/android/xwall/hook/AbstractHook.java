package de.wtfguy.android.xwall.hook;

import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.wtfguy.android.xwall.util.Log;

public abstract class AbstractHook {

	private Log log = Log.getLogger();
	
	protected String className;
	protected String methodName;
	protected Class<?>[] methodParameters;
	
	public AbstractHook( String className, String methodName, Class<?>... methodParameters ) {
		this.className = className;
		this.methodName = methodName;
		this.methodParameters = methodParameters;
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
