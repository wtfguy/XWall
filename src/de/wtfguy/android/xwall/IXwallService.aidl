package de.wtfguy.android.xwall;

import android.content.Intent;

interface IXwallService {

	int getVersion();
	
	void showConnectionAlertDialog( in String title, in String positiveValue, in String negativeValue, in String message, in String value );
	
	void showToast( in String message, in int duration );
}