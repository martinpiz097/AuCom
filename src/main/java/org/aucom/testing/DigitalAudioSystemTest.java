package org.aucom.testing;

import org.aucom.sound.DigitalAudioSystem;

import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DigitalAudioSystemTest {
    public static void main(String[] args) {
        DigitalAudioSystem system = new DigitalAudioSystem();
        HashMap<Mixer, ArrayList<Line>> mapMixers = system.getCompleteMixers();
        Set<Map.Entry<Mixer, ArrayList<Line>>> entries = mapMixers.entrySet();
        entries.stream().forEach(e->{
            System.out.println(e.getKey().getMixerInfo().getName()+"; ");
            e.getValue().stream()
                    .forEach(line-> System.out.println("\t"+line.getLineInfo().toString()));
            System.out.println("---------------------------");
        });
        //Mixer mixer = system.getMixer("Port PCH [hw:0]");
        //Port master = system.findPort(mixer, "Master", false);
        //System.out.println("Mixer: "+master.getLineInfo().toString());
    }
}
