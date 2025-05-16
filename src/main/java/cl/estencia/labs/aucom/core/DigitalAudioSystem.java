/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.estencia.labs.aucom.core;

import javax.sound.sampled.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author martin
 */
public class DigitalAudioSystem {
    private final List<Mixer> listMixers;

    public DigitalAudioSystem() {
        this.listMixers = new ArrayList<>();
        loadMixers();
    }

    private void loadMixers() {
        Mixer.Info[] mixersInfo = AudioSystem.getMixerInfo();

        Mixer.Info mixerInfo;
        for (int i = 0; i < mixersInfo.length; i++) {
            mixerInfo = mixersInfo[i];
            listMixers.add(AudioSystem.getMixer(mixerInfo));
        }
    }

    private boolean isLineSupported(Mixer mixer, Class<? extends Line> infoClass) {
        return mixer.isLineSupported(new Line.Info(infoClass));
    }

    private Line.Info[] findLines(Mixer mixer, Class<? extends Line> infoClass) {
        if (isLineSupported(mixer, infoClass)) {
            Line.Info[] sourceLineInfo = mixer.getSourceLineInfo(new Line.Info(infoClass));
            Line.Info[] targetLineInfo = mixer.getTargetLineInfo(new Line.Info(infoClass));

            Line.Info[] result = new Line.Info[sourceLineInfo.length + targetLineInfo.length];

            int linesCount = 0;
            if (sourceLineInfo.length > 0) {
                for (int i = 0; i < sourceLineInfo.length; i++)
                    result[linesCount++] = sourceLineInfo[i];
            }
            if (targetLineInfo.length > 0) {
                for (int i = 0; i < targetLineInfo.length; i++)
                    result[linesCount++] = targetLineInfo[i];
            }

            return result;

        } else
            return null;
    }

    public Mixer getMixer(String name) {
        return listMixers.parallelStream()
                .filter(mixer -> mixer.getMixerInfo().getName().equals(name))
                .findFirst().orElse(null);
    }

    public List<Line> getMixerLines(Mixer mixer) {
        return getMixerLines(mixer.getMixerInfo().getName());
    }

    public List<Line> getMixerLines(String mixerName) {
        Mixer mixer = getMixer(mixerName);
        Mixer.Info mixerInfo = mixer.getMixerInfo();
        String mixerInfoName = mixerInfo.getName();

        List<Line> listLines = new ArrayList<>();
        listLines.addAll(getMixerTargetLines(mixerInfoName));
        listLines.addAll(getMixerSourceLines(mixerInfoName));
        listLines.addAll(getMixerPorts(mixerInfoName));
        listLines.addAll(getMixerClips(mixerInfoName));

        return listLines;
    }

    public List<SourceDataLine> getMixerSourceLines(Mixer mixer) {
        return getMixerSourceLines(mixer.getMixerInfo().getName());
    }

    public List<SourceDataLine> getMixerSourceLines(String mixerName) {
        Mixer mixer = getMixer(mixerName);
        List<SourceDataLine> listLines = new ArrayList<>();

        if (mixer != null) {
            Line.Info[] lineInfos = findLines(mixer, SourceDataLine.class);
            if (lineInfos != null)
                for (int i = 0; i < lineInfos.length; i++) {
                    try {
                        listLines.add((SourceDataLine) mixer.getLine(lineInfos[i]));
                    } catch (LineUnavailableException e) {
                        e.printStackTrace();
                    }
                }
        }

        return listLines;
    }

    public List<TargetDataLine> getMixerTargetLines(Mixer mixer) {
        return getMixerTargetLines(mixer.getMixerInfo().getName());
    }

    public List<TargetDataLine> getMixerTargetLines(String mixerName) {
        Mixer mixer = getMixer(mixerName);
        List<TargetDataLine> listLines = new ArrayList<>();

        if (mixer != null) {
            Line.Info[] lineInfos = findLines(mixer, TargetDataLine.class);
            if (lineInfos != null)
                for (int i = 0; i < lineInfos.length; i++) {
                    try {
                        listLines.add((TargetDataLine) mixer.getLine(lineInfos[i]));
                    } catch (LineUnavailableException e) {
                        e.printStackTrace();
                    }
                }
        }

        return listLines;
    }

    public List<Port> getMixerPorts(Mixer mixer) {
        return getMixerPorts(mixer.getMixerInfo().getName());
    }

    public List<Port> getMixerPorts(String mixerName) {
        Mixer mixer = getMixer(mixerName);
        List<Port> listLines = new ArrayList<>();

        if (mixer != null) {
            Line.Info[] lineInfos = findLines(mixer, Port.class);
            if (lineInfos != null)
                for (int i = 0; i < lineInfos.length; i++) {
                    try {
                        listLines.add((Port) mixer.getLine(lineInfos[i]));
                    } catch (LineUnavailableException e) {
                        e.printStackTrace();
                    }
                }
        }

        return listLines;
    }

    public List<Clip> getMixerClips(Mixer mixer) {
        return getMixerClips(mixer.getMixerInfo().getName());
    }

    public List<Clip> getMixerClips(String mixerName) {
        Mixer mixer = getMixer(mixerName);
        List<Clip> listLines = new ArrayList<>();

        if (mixer != null) {
            Line.Info[] lineInfos = findLines(mixer, Clip.class);
            if (lineInfos != null)
                for (int i = 0; i < lineInfos.length; i++) {
                    try {
                        listLines.add((Clip) mixer.getLine(lineInfos[i]));
                    } catch (LineUnavailableException e) {
                        e.printStackTrace();
                    }
                }
        }
        return listLines;
    }

    public Port findPort(Mixer mixer, String portName, boolean input) {
        return findPort(mixer.getMixerInfo().getName(), portName, input);
    }

    // en los ports el concepto de source y target es al reves
    public Port findPort(String mixerName, String portName, boolean input) {
        Mixer mixer = getMixer(mixerName);
        if (mixer == null)
            return null;

        List<Port> mixerPorts = getMixerPorts(mixerName);
        return mixerPorts.parallelStream()
                .filter(p -> {
                    final String name = p.getLineInfo().toString().toLowerCase();
                    boolean contains = name.contains(input ? "source" : "target");
                    return contains && name.contains(portName.toLowerCase());
                })
                .findFirst().orElse(null);
    }

    private String[] getControlNames(Class<? extends Control.Type> clazz) throws
            NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Field[] declaredFields = clazz.getDeclaredFields();
        List<String> listNames = new ArrayList<>();

        Field field;
        Constructor<? extends Control.Type> constructor = clazz.getConstructor(String.class);
        constructor.setAccessible(true);
        Control.Type type = constructor.newInstance("test");
        for (int i = 0; i < declaredFields.length; i++) {
            field = declaredFields[i];
            if (field.getModifiers() == Modifier.STATIC)
                listNames.add((String) field.get(null));
        }
        String[] array = new String[listNames.size()];
        return listNames.toArray(array);
    }

    /*public List<Control> getControls(Line line) {
        List<Control> listControls = new ArrayList<>();

        try {
            String[] floatNames = getControlNames(FloatControl.Type.class);
            String[] booleanNames = getControlNames(BooleanControl.Type.class);
            String[] enumNames = getControlNames(EnumControl.Type.class);

            for (int i = 0; i < floatNames.length; i++) {
                FloatControl.Type type = new FloatControl.Type(floatNames[i]);
                listControls.add(line.getControl(type));
            }

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return listControls;

    }*/

    public Map<Mixer, List<Line>> getCompleteMixers() {
        Map<Mixer, List<Line>> mapMixers = new HashMap<>();
        listMixers.forEach(mixer -> mapMixers.put(mixer, getMixerLines(mixer)));
        return mapMixers;
    }

    public List<Mixer> getMixers() {
        return listMixers;
    }



}
