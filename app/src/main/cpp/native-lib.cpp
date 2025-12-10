#include <jni.h>
#include <string>
#include <jni.h>


extern "C"
JNIEXPORT jstring JNICALL
Java_com_muindi_stephen_co_1opbankapp_domain_utils_constants_Constants_getStringBaseUrl(
        JNIEnv *env, jclass clazz) {
    return env->NewStringUTF("https://test-04.free.beeceptor.com/");
}
