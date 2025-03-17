package com.example.watpato.core.local.appDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.watpato.core.local.chapters.dao.ChapterDAO
import com.example.watpato.core.local.chapters.entities.ChapterEntity

@Database(entities = [ChapterEntity::class], version = 1)
abstract class AppDataBase: RoomDatabase(){
    abstract fun chapterDAO(): ChapterDAO
}