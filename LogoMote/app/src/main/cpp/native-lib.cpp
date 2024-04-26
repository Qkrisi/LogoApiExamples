#include <jni.h>
#include <string>

extern "C"
{
    #include "Clients/SocketLogoClient.hpp"

    SocketLogoClient* client = NULL;

    JNIEXPORT jboolean  JNICALL Java_com_qkrisi_logomote_MainActivity_IsConnected(JNIEnv* env, jobject)
    {
        return client != NULL && client->Connected();
    }

    JNIEXPORT jstring JNICALL Java_com_qkrisi_logomote_MainActivity_Connect(
        JNIEnv* env ,
        jobject obj,
        jstring host,
        jint port,
        jstring name
        )
    {
        if(client == NULL) {
            jsize nameLength = env->GetStringUTFLength(name);
            const char* nameChars = env->GetStringUTFChars(name, 0);
            std::string nameStr(nameChars, nameLength);
            env->ReleaseStringUTFChars(name, nameChars);
            client = new SocketLogoClient(nameStr);
        }
        const char* chost = env->GetStringUTFChars(host, 0);
        int res = client->Connect(chost, (int)port);
        env->ReleaseStringUTFChars(host, chost);
        return env->NewStringUTF(res ? client->GetName().data() : "");
    }

    JNIEXPORT void JNICALL Java_com_qkrisi_logomote_MainActivity_Disconnect(JNIEnv* env, jobject)
    {
            if(client == NULL)
                return;
            client->Stop();
            delete client;
            client = NULL;
    }

    JNIEXPORT void JNICALL Java_com_qkrisi_logomote_MainActivity_SendCommand(JNIEnv* env, jobject, jstring command)
    {
            if(client == NULL)
                return;
            const char* ccommand = env->GetStringUTFChars(command, 0);
            client->SendMessage(SND_COMMAND, ccommand, client->GetServerName());
            env->ReleaseStringUTFChars(command, ccommand);
    }
}