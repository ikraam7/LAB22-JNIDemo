#include <jni.h>
#include <string>
#include <algorithm>
#include <vector>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_jnidemo_MainActivity_greetNative(JNIEnv *env, jobject thiz) {
    std::string hello = "Hello from C++!";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_jnidemo_MainActivity_computeFactorial(JNIEnv *env, jobject thiz, jint n) {
    if (n < 0) return -1;
    if (n == 0) return 1;
    jint result = 1;
    for (int i = 1; i <= n; ++i) {
        result *= i;
    }
    return result;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_jnidemo_MainActivity_flipString(JNIEnv *env, jobject thiz, jstring s) {
    const char *nativeString = env->GetStringUTFChars(s, nullptr);
    if (nativeString == nullptr) return nullptr;

    std::string str(nativeString);
    env->ReleaseStringUTFChars(s, nativeString);

    std::reverse(str.begin(), str.end());
    return env->NewStringUTF(str.c_str());
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_jnidemo_MainActivity_arrayTotal(JNIEnv *env, jobject thiz, jintArray data) {
    jsize len = env->GetArrayLength(data);
    jint *body = env->GetIntArrayElements(data, nullptr);
    if (body == nullptr) return 0;

    jint sum = 0;
    for (int i = 0; i < len; ++i) {
        sum += body[i];
    }

    env->ReleaseIntArrayElements(data, body, 0);
    return sum;
}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_example_jnidemo_MainActivity_benchmarkNative(JNIEnv *env, jobject thiz, jint iterations) {
    jlong sum = 0;
    for (int i = 0; i < iterations; ++i) {
        sum += i;
    }
    return sum;
}
