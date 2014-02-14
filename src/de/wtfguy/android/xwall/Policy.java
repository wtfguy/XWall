package de.wtfguy.android.xwall;

public class Policy {

	private static final int RULE_ALLOW_ONCE = 0;
	private static final int RULE_ALLOW_ALWAYS= 1;
	private static final int RULE_DENY_ONCE = 2;
	private static final int RULE_DENY_ALWAYS = 3;
	
	public static Policy ALLOW_ONCE = getInstance( RULE_ALLOW_ONCE );
	public static Policy ALLOW_ALWAYS = getInstance( RULE_ALLOW_ALWAYS );
	public static Policy DENY_ONCE = getInstance( RULE_DENY_ONCE );
	public static Policy DENY_ALWAYS = getInstance( RULE_DENY_ALWAYS );
	
	private int rule;
	
	private Policy( int rule ) {
		this.rule = rule;
	}
	
	private static Policy getInstance( int rule ) {
		return new Policy( rule );
	}
	
	public int getPolicy() {
		return rule;
	}
}
