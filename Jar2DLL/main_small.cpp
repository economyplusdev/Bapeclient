#include "main.h"
#include "JNI/jni.h"
#include "JNI/jvmti.h"
#include "classes.h"
#include "loader.h"

DWORD WINAPI MainThread(CONST LPVOID lpParam)
{
    HMODULE jvmDll = GetModuleHandleA("jvm.dll");
    if (!jvmDll)
    {
        DWORD lastError = GetLastError();
        MessageBoxA(NULL, "Error: 0x00000001", "Bape", MB_OK | MB_ICONERROR);
        ExitThread(0);
    }
    FARPROC getJvmsVoidPtr = GetProcAddress(jvmDll, "JNI_GetCreatedJavaVMs");
    if (!getJvmsVoidPtr)
    {
        DWORD lastError = GetLastError();
        MessageBoxA(NULL, "Error: 0x00000002", "Bape", MB_OK | MB_ICONERROR);
        ExitThread(0);
    }

    typedef jint (JNICALL * GetCreatedJavaVMs)(JavaVM **, jsize, jsize *);
    GetCreatedJavaVMs jni_GetCreatedJavaVMs = (GetCreatedJavaVMs)getJvmsVoidPtr;
    jsize nVMs;
	jni_GetCreatedJavaVMs(NULL, 0, &nVMs);
	JavaVM **buffer = new JavaVM *[nVMs];
	jni_GetCreatedJavaVMs(buffer, nVMs, &nVMs);
	if (nVMs == 0)
    {
        MessageBoxA(NULL, "Error: 0x00000003", "Bape", MB_OK | MB_ICONERROR);
        ExitThread(0);
	}
    if (nVMs > 0)
    {
        for (jsize i = 0; i < nVMs; i++)
        {
            JavaVM* jvm = buffer[i];
			JNIEnv* jniEnv = NULL;
			jvmtiEnv* jvmTiEnv = NULL;
            jvm->AttachCurrentThread((void **)(&jniEnv), 0);
            jvm->GetEnv((void **)(&jniEnv), JNI_VERSION_1_8);
            if (!jniEnv)
            {
                MessageBoxA(NULL, "Error: 0x00000004", "Bape", MB_OK | MB_ICONERROR);
                jvm->DetachCurrentThread();
                break;
            }
            jclass classLoaderClazz = NULL;
            classLoaderClazz = jniEnv->DefineClass(NULL, NULL, (jbyte*)classLoaderClass, classLoaderClassSize);
            if (!classLoaderClazz)
            {
                MessageBoxA(NULL, "Error: 0x00000005", "Bape", MB_OK | MB_ICONERROR);
                jvm->DetachCurrentThread();
                break;
            }
            jobjectArray classesData = (jobjectArray)jniEnv->CallStaticObjectMethod(classLoaderClazz, jniEnv->GetStaticMethodID(classLoaderClazz, "getByteArray", "(I)[[B"), (jint)classCount);//jniEnv->NewObjectArray(classCount, jniEnv->FindClass("[B"), NULL);
            int cptr = 0;
            for (jsize j = 0; j < classCount; j++)
            {
                jbyteArray classByteArray = jniEnv->NewByteArray(classSizes[j]);
                jniEnv->SetByteArrayRegion(classByteArray, 0, classSizes[j], (jbyte *)(classes + cptr));
                cptr += classSizes[j];
                jniEnv->SetObjectArrayElement(classesData, j, classByteArray);
            }
            jint injectResult = jniEnv->CallStaticIntMethod(classLoaderClazz, jniEnv->GetStaticMethodID(classLoaderClazz, "injectCP", "([[B)I"), classesData);
            if (injectResult)
            {
                MessageBoxA(NULL, "Error: 0x00000006", "Bape", MB_OK | MB_ICONERROR);
                jvm->DetachCurrentThread();
                break;
            }
            jvm->DetachCurrentThread();
        }
    }
    ExitThread(0);
}

extern "C" DLL_EXPORT BOOL APIENTRY DllMain(HINSTANCE hinstDLL, DWORD fdwReason, LPVOID lpvReserved)
{
    switch (fdwReason)
    {
        case DLL_PROCESS_ATTACH:
            CreateThread(NULL, 0, &MainThread, NULL, 0, NULL);
            break;
    }
    return TRUE; // succesful
}
