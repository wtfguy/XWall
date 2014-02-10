package de.qbit.android.xwall.config;

import java.util.HashMap;
import java.util.Map;

import de.qbit.android.xwall.util.StringUtil;
import de.robv.android.xposed.XposedBridge;

public class Configurator {

	private Map<String,String> forbiddenOnceUrls = new HashMap<String,String>();
	private Map<String,String> forbiddenAlwaysUrls = new HashMap<String,String>();
	
	public void loadRules() {
		
		forbiddenAlwaysUrls.put( StringUtil.encodeUrl( "http://www.google.de" ), "http://www.google.de" );
		
	}
	
	
	public void logRules() {
		//log adresses
		XposedBridge.log( "forbidden urls are:" );
		for( String key : forbiddenAlwaysUrls.keySet() ) {
			XposedBridge.log( forbiddenAlwaysUrls.get(key) );
		}
	}
	
}
