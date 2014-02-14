package de.wtfguy.android.xwall;

interface IXwallService {

	int getVersion();

	void showToast( String text, int duration );
	
	void showFirewallLearningDialog();

}