package com.iwobanas.hunspellchecker;

public class Hunspell {
	
	public native void create(String aff, String dic);
	
	public native int spell(byte[] word);
	
	public native String[] getSuggestions(byte[] word);
	
	static {
        System.loadLibrary("hunspell-jni");
    }
}
