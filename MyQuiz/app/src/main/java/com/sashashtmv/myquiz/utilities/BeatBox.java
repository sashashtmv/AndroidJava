package com.sashashtmv.myquiz.utilities;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//будем использовать для воспроизведения звуков в процессе прохождения теста с помощью системных классов SoundPool и AudioManager
public class BeatBox {
    private static final String SOUNDS_FOLDER = "all_sounds";
    private static final int MAX_SOUNDS = 5;
    private AssetManager mAssets;
    private List<SoundUtilities> mSounds = new ArrayList<>();
    private SoundPool mSoundPool;

    //создаем AssetManager и получаем доступ к ресурсам
    public BeatBox(Context context) {
        mAssets = context.getAssets();
        //подходит для многократного воспроизведения простых небольших файлов, поскольку загружает их в память все сразу
        // и воспроизводит их от туда, это положительно влияет на производительность
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        loadSounds();
    }

    //формирует список звуков для воспроизведения и передает их в SoundPool методом load()
    private void loadSounds() {
        String[] soundNames;
        try {
            soundNames = mAssets.list(SOUNDS_FOLDER);
        } catch (IOException ioe) {
            return;
        }

        for (String filename : soundNames) {
            try {
                String assetPath = SOUNDS_FOLDER + "/" + filename;
                SoundUtilities sound = new SoundUtilities(assetPath);
                load(sound);
                mSounds.add(sound);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void load(SoundUtilities sound) throws IOException {
        AssetFileDescriptor afd = mAssets.openFd(sound.getAssetPath());
        int soundId = mSoundPool.load(afd, 1);
        sound.setSoundId(soundId);
    }

    //служит для воспроизведения звуков
    public void play(SoundUtilities sound) {
        Integer soundId = sound.getSoundId();
        if (soundId == null) {
            return;
        }
        mSoundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    //для освобождения ресурсов проигрывател
    public void release() {
        mSoundPool.release();
    }

    //для получения списка звуковых файлов для воспроизведения
    public List<SoundUtilities> getSounds() {
        return mSounds;
    }
}
