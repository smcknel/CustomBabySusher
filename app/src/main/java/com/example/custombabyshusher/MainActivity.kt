package com.example.custombabyshusher

import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import android.media.MediaRecorder
import androidx.compose.foundation.background
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.custombabyshusher.R.raw.shush1


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Calling the composable function
            // to display element and its contents
           RunApp()
        }
    }
}

@Preview
@Composable
fun RunApp(){
    generateButtons(modifier = Modifier)
}

// Creating a composable
// function to display Top Bar
@Composable
fun generateButtons(modifier: Modifier = Modifier) {
    val mContext = LocalContext.current
    var mediaPlayer = MediaPlayer.create(mContext, shush1)
    mediaPlayer.setOnCompletionListener{mp -> mp.start()}
    mediaPlayer.setVolume(1F, 1F)
    var recorder: MediaRecorder? = null
    var isRecording by remember { mutableStateOf(false) }
    val imageResource = when (isRecording) {
        true -> R.drawable.notrecording
        else -> R.drawable.recording
    }
    Box (modifier){
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.background(White)
        ) {
            IconButton(
                onClick = {
                    if (!mediaPlayer.isPlaying && !isRecording) {
                        mediaPlayer.start()
                    }
                },
                modifier = Modifier.size(250.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.play),
                    contentDescription = "Play"
                )
            }
            IconButton(onClick = { mediaPlayer.pause() },
                modifier = Modifier.size(250.dp)) {
                Image(
                    painter = painterResource(R.drawable.pause),
                    contentDescription = "Pause"
                )
            }
            IconButton(onClick = {if (isRecording == false){
                                        mediaPlayer.stop()
                                        mediaPlayer.release()
                                        recorder = MediaRecorder().apply {
                                            setAudioSource(MediaRecorder.AudioSource.MIC)
                                            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                                            setOutputFile(shush1)
                                            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                                        }
                                    }
                                    else if (isRecording == true && recorder != null) {
                                        recorder.stop()
                                        recorder.release()
                                    }
                                    isRecording = !isRecording
                                 },
                modifier = Modifier.size(250.dp)){
                Image(painter = painterResource(imageResource),
                    contentDescription = "recording button")
            }
        }
    }
}
