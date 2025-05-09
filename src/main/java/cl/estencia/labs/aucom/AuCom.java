package cl.estencia.labs.aucom;

import cl.estencia.labs.aucom.cmd.Mode;
import cl.estencia.labs.aucom.audio.device.AudioDevice;
import cl.estencia.labs.aucom.audio.device.Microphone;
import cl.estencia.labs.aucom.audio.device.Speaker;

import javax.sound.sampled.LineUnavailableException;

public class AuCom {

    private static final String MODE_OPT = "-m";

    public static void main(String[] args) throws LineUnavailableException {
//        if (args != null && args.length > 1) {
//            val optArg = args[0];
//            if (optArg.equals(MODE_OPT)) {
//                val interfaceOpt = args[1];
//                val mode = Mode.valueOf(interfaceOpt);
//                AudioInterface audioInterface = null;
//
//                switch (mode) {
//                    case MICRO -> {
//                        audioInterface = new Microphone();
//                    }
//                    case SPEAKER -> {
//                        audioInterface = new Speaker();
//                    }
//                }
//
//                if (audioInterface != null) {
//                    audioInterface.open();
//                }
//            }
//        }
        AudioDevice microphone = new Microphone();
        AudioDevice speaker = new Speaker();

        microphone.start();
        speaker.start();
    }
}
