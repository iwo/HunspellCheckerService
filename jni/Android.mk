LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := hunspell-jni
LOCAL_SRC_FILES := hunspell-jni.cpp hunspell/affentry.cxx hunspell/affixmgr.cxx hunspell/csutil.cxx \
                   hunspell/dictmgr.cxx hunspell/hashmgr.cxx hunspell/hunspell.cxx \
                   hunspell/suggestmgr.cxx hunspell/phonet.cxx hunspell/filemgr.cxx \
                   hunspell/hunzip.cxx hunspell/replist.cxx

LOCAL_CPP_EXTENSION := .cxx .cpp .cc

include $(BUILD_SHARED_LIBRARY)
