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
class NumbersFragment : Fragment() {
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.word_list, container, false)
        val words = ArrayList<Word?>()
        words.add(Word("one", "lutti", R.drawable.number_one, R.raw.number_one))
        words.add(Word("two", "otiiko", R.drawable.number_two, R.raw.number_two))
        words.add(Word("three", "tolookosu", R.drawable.number_three, R.raw.number_three))
        words.add(Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four))
        words.add(Word("five", "massokka", R.drawable.number_five, R.raw.number_five))
        words.add(Word("six", "temmokka", R.drawable.number_six, R.raw.number_six))
        words.add(Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven))
        words.add(Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight))
        words.add(Word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine))
        words.add(Word("ten", "ne'aacha", R.drawable.number_ten, R.raw.number_ten))
        val itemsAdapter = WordAdapter(activity!!, words, R.color.category_numbers)
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

    private fun releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer!!.release()
            mediaPlayer = null
            audioManager!!.abandonAudioFocus(mOnAudioFocusChangeListener)
        }
    }
}