package com.ikeengine.audio;

import java.nio.ByteBuffer;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.openal.AL10.alDeleteBuffers;
import static org.lwjgl.openal.ALC10.*;


/**
 *
 * @author Jonathan Elue
 */
public class AudioManager {

    private final static List<Integer> BUFFERS = new ArrayList<>();
    private static long device;
    private static long context;

    public static void init() {
       
        final String defaultDeviceName = alcGetString(0, ALC_DEFAULT_DEVICE_SPECIFIER);
        device = alcOpenDevice(defaultDeviceName);

        //int[] attributes = {0};
        context = alcCreateContext(device, (ByteBuffer) null);
        alcMakeContextCurrent(context);

        AL.createCapabilities(device);
    }

    public static void setListenerData(final float x, final float y, final float z) {
        AL10.alListener3f(AL10.AL_POSITION, x, y, z);
        AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
    }

    public static int loadSound(final String file) {
        final int buffer = AL10.alGenBuffers();
        BUFFERS.add(buffer);
        WaveData wavFile = WaveData.create(file);
        AL10.alBufferData(buffer, wavFile.format, wavFile.data, wavFile.samplerate);
        wavFile.dispose();
        return buffer;
    }

    public static void cleanUp() {
        for (final int buffer : BUFFERS) {
            alDeleteBuffers(buffer);
        }

        //Terminate OpenAL
        //alcDestroyContext(context);
        //alcCloseDevice(device);
    }
}
