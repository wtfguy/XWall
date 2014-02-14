package de.wtfguy.android.xwall.util;

import static android.util.Log.d;
import static android.util.Log.e;
import static android.util.Log.i;
import static android.util.Log.v;
import static android.util.Log.w;
import static android.util.Log.wtf;
import de.wtfguy.android.xwall.XWall;

public class Log {
	
	private static Log logger;
	private String tag;
	
	public static Log getLogger() {
		return getLogger( XWall.LOG_TAG );
	}
	
	public static Log getLogger( String tag ) {
		if( logger == null )
			logger = new Log( tag );
		return logger;
	}
	
	private Log( String tag ) {
		this.tag = tag;
	}
	
    public int trace( String msg ) {
        return v(tag, msg);
    }

    public int trace(String msg, Throwable tr) {
        return v(tag, msg, tr);
    }
    
    public int debug(String msg) {
        return d(tag, msg);
    }

    public int debug(String msg, Throwable tr) {
        return d(tag, msg, tr);
    }

    public int info(String msg) {
        return i(tag, msg);
    }

    public int info(String msg, Throwable tr) {
        return i(tag, msg, tr);
    }

    public int warn(String msg) {
        return w(tag, msg);
    }

    public int warn(String msg, Throwable tr) {
        return w(tag, tr);
    }

    public int warn(Throwable tr) {
        return w(tag, tr);
    }

    public int error( String msg) {
        return e(tag, msg);
    }

    public int error( String msg, Throwable tr) {
        return e(tag, msg, tr);
    }

    public int whatthefuck( String msg ) {
        return wtf(tag, msg);
    }

    public int whatthefuck( Throwable tr) {
        return wtf(tag, tr);
    }

    public int whatthefuck( String msg, Throwable tr) {
        return wtf(msg, msg, tr);
    }
}
