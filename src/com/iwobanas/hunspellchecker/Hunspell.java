package com.iwobanas.hunspellchecker;

public class Hunspell {
	
	public native void create(String aff, String dic);
	
	public native int spell(String word);
	
	public native String[] getSuggestions(String word);
	
	static {
        System.loadLibrary("hunspell-jni");
    }
}
