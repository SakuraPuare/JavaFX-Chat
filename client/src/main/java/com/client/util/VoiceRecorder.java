package com.client.util;

import com.client.chatwindow.Listener;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Dominic
 * @since 16-Oct-16
 * Website: www.dominicheal.com
 * Github: www.github.com/DomHeal
 */
public class VoiceRecorder extends VoiceUtil {
    private static final ExecutorService voiceExecutor = 
        Executors.newSingleThreadExecutor(r -> {
            Thread t = new Thread(r);
            t.setName("Voice-Recording-Thread");
            return t;
        });

    public static void captureAudio() {
        try {
            final AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            final TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
            
            voiceExecutor.execute(new VoiceRecordingTask(line, format));
            
        } catch (LineUnavailableException e) {
            System.err.println("Line unavailable: ");
            e.printStackTrace();
        }
    }

    private static class VoiceRecordingTask implements Runnable {
        private final TargetDataLine line;
        private final AudioFormat format;

        public VoiceRecordingTask(TargetDataLine line, AudioFormat format) {
            this.line = line;
            this.format = format;
        }

        @Override
        public void run() {
            int bufferSize = (int)format.getSampleRate() * format.getFrameSize();
            byte buffer[] = new byte[bufferSize];

            out = new ByteArrayOutputStream();
            isRecording = true;
            try {
                while (isRecording) {
                    int count = line.read(buffer, 0, buffer.length);
                    if (count > 0) {
                        out.write(buffer, 0, count);
                    }
                }
            } finally {
                try {
                    out.close();
                    out.flush();
                    line.close();
                    line.flush();
                    Listener.sendVoiceMessage(out.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void shutdown() {
        voiceExecutor.shutdown();
    }
}
