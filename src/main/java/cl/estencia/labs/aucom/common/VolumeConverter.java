package cl.estencia.labs.aucom.common;

import javax.sound.sampled.FloatControl;

import static cl.estencia.labs.aucom.common.AudioConstants.*;

public class VolumeConverter {
    public float volumeToDataLineVolume(float volume, float minLineVol, float maxLineVol) {
        float volRange = maxLineVol - minLineVol;
        float volScale = 1 / (DEFAULT_VOL_RANGE / volRange);
        float result = (volume * volScale) + minLineVol;

        return result < minLineVol ? minLineVol : (Math.min(result, maxLineVol));
    }

    public float dataLineVolumeToVolume(float volume, float minLineVol, float maxLineVol) {
        float volRange = maxLineVol - minLineVol;
        float volScale = 1 / (DEFAULT_VOL_RANGE / volRange);
        float result = (volume - minLineVol) / volScale;

        return result < DEFAULT_MIN_VOL ? DEFAULT_MIN_VOL : (Math.min(result, DEFAULT_MAX_VOL));
    }

    public float volumeToDataLineVolume(float volume) {
        return volumeToDataLineVolume(volume, MIN_VOL, MAX_VOL);
    }

    public float dataLineVolumeToVolume(float volume) {
        return dataLineVolumeToVolume(volume, MIN_VOL, MAX_VOL);
    }

    public float volumeToDataLineVolume(float volume, FloatControl control) {
        return volumeToDataLineVolume(volume, control.getMinimum(), control.getMaximum());
    }

    public float dataLineVolumeToVolume(float volume, FloatControl control) {
        return dataLineVolumeToVolume(volume, control.getMinimum(), control.getMaximum());
    }
}
