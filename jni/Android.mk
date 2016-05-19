LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := mbmiddle
LOCAL_SRC_FILES := mbmiddle.cpp

LOCAL_LDLIBS    := -lm -llog 

include $(BUILD_SHARED_LIBRARY)
