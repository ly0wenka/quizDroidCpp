#include <jni.h>
#include <string>
#include <vector>

extern "C" JNIEXPORT jobjectArray JNICALL
Java_com_lenterteinment_nativecppapp_MainActivity_getQuizFromJNI(JNIEnv *env, jobject /* this */) {
    std::vector<std::string> quiz = {
            "Question 1: What is the capital of France?",
            "1) Paris", "2) Berlin", "3) Madrid", "4) Rome",
            "Question 2: What is 2 + 2?",
            "1) 3", "2) 4", "3) 5", "4) 6"
    };

    jobjectArray ret = env->NewObjectArray(quiz.size(), env->FindClass("java/lang/String"), env->NewStringUTF(""));
    for (int i = 0; i < quiz.size(); i++) {
        env->SetObjectArrayElement(ret, i, env->NewStringUTF(quiz[i].c_str()));
    }
    return ret;
}