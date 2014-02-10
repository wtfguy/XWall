package de.wtfguy.android.xwall.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import de.robv.android.xposed.XposedBridge;

public class StringUtil {

	public static String encodeUrl( String input ) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update( input.getBytes() );
			
			byte byteData[] = md.digest();
			
	        //convert the byte to hex format
	        StringBuffer hexString = new StringBuffer();
	    	for( int i=0; i<byteData.length; i++) {
	    		String hex = Integer.toHexString( 0xff & byteData[i] );
	   	     	if( hex.length()==1 ) 
	   	     		hexString.append('0');
	   	     	hexString.append(hex);
	    	}
			
	    	return hexString.toString();
	    	
		} catch ( NoSuchAlgorithmException e) {
			XposedBridge.log( e );
			return input;
		}
	}
	
}
