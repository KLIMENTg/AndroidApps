package com.example.faith_volumeprovidercompat;

import android.app.Service;

import android.content.ComponentName;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;

import androidx.media.VolumeProviderCompat;

public class PlayerService extends Service {
    private int volumeButtonDownCounter = 0;
    private boolean inBackground = true;
    private MediaSessionCompat mediaSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaSession = new MediaSessionCompat( this, "PlayerService" );
        mediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS );

        mediaSession.setPlaybackState( new PlaybackStateCompat.Builder()
        .setState( PlaybackStateCompat.STATE_PLAYING, 0, 0 )
        .build() );

        VolumeProviderCompat myVolumeProvider = new VolumeProviderCompat(
                VolumeProviderCompat.VOLUME_CONTROL_RELATIVE,
                100,
                50
        ) {
            @Override
            public void onAdjustVolume(int direction) {
                /*super.onAdjustVolume(direction);*/
                if( direction < 0 ) { // VOLUME DOWN: BRING TO FOREGROUND
                    Log.d("VolumeButton", "Volume Decrease Detected.");


                    volumeButtonDownCounter ++;
                    if ( volumeButtonDownCounter == 3 && inBackground == true){
                        Context myContext = PlayerService.super.getApplicationContext();
                        Intent it = new Intent("intent.my.action");
                        it.setComponent(new ComponentName(
                                myContext.getPackageName(),
                                MainActivity.class.getName())
                        );
                        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        myContext.startActivity(it);

                        volumeButtonDownCounter = 0;
                        inBackground = false;
                    }

                    if ( volumeButtonDownCounter == 3 && inBackground == false) {
                        Intent it = new Intent();
                        it.setAction( Intent.ACTION_MAIN );
                        it.addCategory(Intent.CATEGORY_HOME);
                        PlayerService.super.startActivity(it);

                        volumeButtonDownCounter = 0;
                        inBackground = true;
                    }


                } else if( direction > 0 ) { // VOLUME UP: GO TO BACKGROUND
                    Log.d("VolumeButton", "Volume Increase Detected.");

                    volumeButtonDownCounter = 0;
                }

            }
        };

        mediaSession.setPlaybackToRemote( myVolumeProvider );
        mediaSession.setActive( true );
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaSession.release();
    }

}
