# Introduction #
OpenAL does'nt play MIDI files. In Android, MIDI files are played native, and in Desktop it use a small piece of code.

Create an interface for a midi player. This interface is then used by an Android implementation and a desktop implementation. An instance of an implementation is sent to the application by the starter class.

The solution below plays midi files in the assets dir.

# The Interface #
Create an interface like this:
```
public interface MidiPlayer {
    public void open(String fileName);
    public boolean isLooping();
    public void setLooping(boolean loop);
    public void play();
    public void pause();
    public void stop();
    public void release();
    public boolean isPlaying();
    public void setVolume(float volume);
}
```
Then a desktop implementation:
```
import javax.sound.midi.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import ...MidiPlayer

public class DesktopMidiPlayer implements MidiPlayer {

    private Sequence sequence;
    private Sequencer sequencer;

    public DesktopMidiPlayer() {

        try {
            sequencer = MidiSystem.getSequencer();
        } catch (MidiUnavailableException e) {
            Log.error("Error opening midi device.", e);
        }

    }

    public void open(String fileName) {

        FileHandle file = Gdx.files.internal(fileName);
        try {
            sequence = MidiSystem.getSequence(file.read());
            sequencer.open();
            sequencer.setSequence(sequence);
        } catch (Exception e) {
            Log.error("Error opening midi: " + fileName + ".", e);
        }
    }


    public boolean isLooping() {
        if(sequencer != null){
            return sequencer.getLoopCount() != 0;
        }
        return false;
    }


    public void setLooping(boolean loop) {
        if(sequencer != null){
            if(!loop){
                sequencer.setLoopCount(0);
                return;
            }
            sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
        }
    }


    public void play() {
        if(sequencer != null){
            sequencer.start();
        }
    }


    public void pause() {
        stop();
    }


    public void stop() {
        if(sequencer != null){
            sequencer.stop();
        }
    }

    public void release() {
        if(sequencer != null){
            sequencer.close();
        }
    }

    public boolean isPlaying() {
        return sequencer.isRunning();
    }

    public void setVolume(float volume) {
        //Not implemented
    }
}

```
And the Android implementation:
```
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.FileNotFoundException;
import java.io.IOException;

import ...MidiPlayer 

public class AndroidMidiPlayer implements MidiPlayer {

    private MediaPlayer mediaPlayer;
    private Context context;
    private boolean looping;
    private float volume;

    public AndroidMidiPlayer(Context context) {
        this.context = context;
        this.mediaPlayer = new MediaPlayer();

        this.looping = false;
        this.volume = 1;
    }

    public void open(String fileName) {

        reset();

        try {
            AssetFileDescriptor afd = context.getAssets().openFd(fileName);
            mediaPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
            mediaPlayer.prepare();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //TODO: This should probably be replaced with something better.
    //I had to reset the player to avoid error when
    //opening a second midi file.
    private void reset() {
        mediaPlayer.reset();
        mediaPlayer.setLooping(looping);
        setVolume(volume);
    }


    public boolean isLooping() {
        return mediaPlayer.isLooping();
    }

    public void setLooping(boolean loop) {
        mediaPlayer.setLooping(loop);
    }

    public void play() {
        mediaPlayer.start();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void stop() {
        mediaPlayer.stop();
    }

    public void release() {
        mediaPlayer.release();
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public void setVolume(float volume) {
        mediaPlayer.setVolume(volume, volume);
    }
}
```
Desktop starter example:
```
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.jogl.JoglApplication;
import midi classes ....

public class DesktopStarter {
        
    public static void main(String[] args) {

        MidiPlayer midiPlayer = new DesktopMidiPlayer();
        new JoglApplication(new Main(midiPlayer), "<Title>", 800, 480, false);

    }
    
}

```

Android app example:
```
import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import midi classes ....

public class MyApp extends AndroidApplication
{

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        MidiPlayer midiPlayer = new AndroidMidiPlayer(getApplicationContext());

        initialize(new Main(midiPlayer), true);
    }


}


```

Playing a midi file:
```
    public void create () {

        midiPlayer.setLooping(true);
        midiPlayer.setVolume(0.5f);

        //Play midi assets/data/midi/castlevania_stage1.mid
        midiPlayer.open("data/midi/castlevania_stage1.mid");

        midiPlayer.play();
    }
```