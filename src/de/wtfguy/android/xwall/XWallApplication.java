package de.wtfguy.android.xwall;

import android.app.Application;
import de.wtfguy.android.xwall.util.Log;

public class XWallApplication extends Application {

	private Log log = Log.getLogger();
	
	@Override
	public void onCreate() {
		log.info( "Ui started" );
	}
	
}
