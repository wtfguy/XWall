package de.wtfguy.android.xwall.config;

import java.util.HashMap;
import java.util.Map;

import de.robv.android.xposed.XposedBridge;
import de.wtfguy.android.xwall.util.StringUtil;

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
