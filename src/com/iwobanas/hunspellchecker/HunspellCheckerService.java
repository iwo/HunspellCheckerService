package com.iwobanas.hunspellchecker;

import android.service.textservice.SpellCheckerService;
import android.util.Log;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;

/**
 * HunspellCheckerService is a prototype of Android SpellCheckerService using Hunspell and JNI.
 * Unfortunately the prototype proved that Hunspell suggestions generation is too slow 
 * to be used on Android smartphones. It takes up to few seconds to generate suggestions for a longer word.
 */
public class HunspellCheckerService extends SpellCheckerService {
    private static final String TAG = HunspellCheckerService.class.getSimpleName();
    private static final boolean DBG = true;
    @Override
    public Session createSession() {
        return new AndroidSpellCheckerSession();
    }

    private static class AndroidSpellCheckerSession extends Session {
        private String mLocale;
        private Hunspell hunspell;
        
        @Override
        public void onCreate() {
            mLocale = getLocale();
            if (DBG) {
                Log.d(TAG, "onCreate: " + mLocale);
            }
            //TODO: implement proper file existence checking
            String fileBase = "/sdcard/dictionaries/" + mLocale;
            hunspell = new Hunspell();
            hunspell.create( fileBase + ".aff", fileBase + ".dic");
        }

        @Override
        public SuggestionsInfo onGetSuggestions(TextInfo textInfo, int suggestionsLimit) {
            if (DBG) {
                Log.d(TAG, "onGetSuggestions: " + textInfo.getText());
            }
            final long startTime = System.currentTimeMillis();
            final String input = textInfo.getText();
            
            final int hunspellResult = hunspell.spell(input);
            
            if (hunspellResult != 0)
            {
            	if (DBG)
            	{
            		Log.d(TAG, "in dictionary time: " + (System.currentTimeMillis() - startTime) + "ms");
            	}
            	return new SuggestionsInfo(SuggestionsInfo.RESULT_ATTR_IN_THE_DICTIONARY, new String[]{});
            }
            String[] suggestions = hunspell.getSuggestions(input);
            if (DBG)
        	{
        		Log.d(TAG, suggestions.length + " suggestions time: " + (System.currentTimeMillis() - startTime) + "ms");
        	}
            return new SuggestionsInfo(SuggestionsInfo.RESULT_ATTR_LOOKS_LIKE_TYPO, suggestions);
        }
    }
}
