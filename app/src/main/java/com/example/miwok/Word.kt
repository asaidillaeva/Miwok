package com.example.miwok

class Word {
    var defaultTranslation: String
    var miwokTranslation: String
    val NO_IMG_PROVIDED = -1
    var audioSrc: Int
    var imageSrc = NO_IMG_PROVIDED

    constructor(mDefaultTranslation: String, mMiwokTranslation: String, imageSrc: Int, audioSrc: Int) {
        defaultTranslation = mDefaultTranslation
        miwokTranslation = mMiwokTranslation
        this.imageSrc = imageSrc
        this.audioSrc = audioSrc
    }

    constructor(mDefaultTranslation: String, mMiwokTranslation: String, audioSrc: Int) {
        defaultTranslation = mDefaultTranslation
        miwokTranslation = mMiwokTranslation
        this.audioSrc = audioSrc
    }

    fun hasImage(): Boolean {
        return imageSrc != NO_IMG_PROVIDED
    }

    override fun toString(): String {
        return "Word{" +
                "mDefaultTranslation='" + defaultTranslation + '\'' +
                ", mMiwokTranslation='" + miwokTranslation + '\'' +
                ", NO_IMG_PROVIDED=" + NO_IMG_PROVIDED +
                ", audioSrc=" + audioSrc +
                ", imageSrc=" + imageSrc +
                '}'
    }
}