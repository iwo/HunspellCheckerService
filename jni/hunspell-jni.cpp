#include <string.h>
#include <jni.h>

#include "hunspell/hunspell.hxx"

#ifdef __cplusplus
extern "C" {
#endif

//TODO: there are quite a few memory leaks here so clean it up if you'd like to use it for real

Hunspell* hunspell;

void Java_com_iwobanas_hunspellchecker_Hunspell_create( JNIEnv* env,
                                                  jobject thiz, jstring jaff, jstring jdic )
{
	jboolean isCopy;
	const char *aff = env->GetStringUTFChars(jaff, &isCopy);
	const char *dic = env->GetStringUTFChars(jdic, &isCopy);

	delete hunspell;
	hunspell = new Hunspell(aff, dic);
}

jobjectArray Java_com_iwobanas_hunspellchecker_Hunspell_getSuggestions( JNIEnv* env,
                                                  jobject thiz, jstring jword )
{
    jclass jcls = env->FindClass("java/lang/String");

    jboolean isCopy;
    const char *word = env->GetStringUTFChars(jword, &isCopy);
    char **suggestions;
    int len = hunspell->suggest(&suggestions, word);

    jobjectArray jsuggestions = env->NewObjectArray(len, jcls, 0);

    for (int i = 0; i < len; i++)
    {
        env->SetObjectArrayElement(jsuggestions, i, env->NewStringUTF(suggestions[i]));
    }
    hunspell->free_list(&suggestions, len);
    return jsuggestions;
}

jint Java_com_iwobanas_hunspellchecker_Hunspell_spell( JNIEnv* env,
                                                  jobject thiz, jstring jword )
{
	jboolean isCopy;
	const char *word = env->GetStringUTFChars(jword, &isCopy);

	int result = hunspell->spell(word);
	return result;
}

#ifdef __cplusplus
}
#endif
