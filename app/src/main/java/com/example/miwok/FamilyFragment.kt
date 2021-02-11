package com.example.miwok

import android.content.Context
import android.media.AudioManager
import android.media.AudioManager.OnAudioFocusChangeListener
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.android.miwok.R
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class FamilyFragment : Fragment() {
    private var mediaPlayer: MediaPlayer? = null
    private var audioManager: AudioManager? = null
    private val mOnAudioFocusChangeListener = OnAudioFocusChangeListener { focusChange ->
        if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
            mediaPlayer!!.pause()
            mediaPlayer!!.seekTo(0)
        } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
            mediaPlayer!!.start()
        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
            releaseMediaPlayer()
        }
    }
    private val completionListener = OnCompletionListener { releaseMediaPlayer() }

    override fun onStop() {
        super.onStop()
        releaseMediaPlayer()
    }

    private fun releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer!!.release()
            mediaPlayer = null
            audioManager!!.abandonAudioFocus(mOnAudioFocusChangeListener)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.word_list, container, false)
        val words = ArrayList<Word?>()
        words.add(Word("father", "әpә", R.drawable.family_father, R.raw.family_father))
        words.add(Word("mother", "әṭa", R.drawable.family_mother, R.raw.family_mother))
        words.add(Word("son", "angsi", R.drawable.family_son, R.raw.family_son))
        words.add(Word("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter))
        words.add(Word("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother))
        words.add(Word("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother))
        words.add(Word("older sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister))
        words.add(Word("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister))
        words.add(Word("grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother))
        words.add(Word("grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather))
        val itemsAdapter = WordAdapter(activity!!, words, R.color.category_family)
        val listView = rootView.findViewById<View>(R.id.list) as ListView
        listView.adapter = itemsAdapter
        listView.onItemClickListener = OnItemClickListener { adapterView, view, position, id ->
            val word = words[position]
            audioManager = activity!!.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val result = audioManager!!.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN)
            if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                mediaPlayer = MediaPlayer.create(activity, word!!.audioSrc)
                mediaPlayer!!.start()
                mediaPlayer!!.setOnCompletionListener(completionListener)
            }
        }
        return rootView
    }
}