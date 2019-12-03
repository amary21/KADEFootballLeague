package com.amary.kade_footballeague.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.amary.kade_footballeague.data.local.model.NextMatch
import com.amary.kade_footballeague.data.local.model.PrevMatch
import org.jetbrains.anko.db.*

class DBHelper(context: Context) : ManagedSQLiteOpenHelper(context, "Favorite.db", null, 1) {

    companion object{
        private var instance : DBHelper? = null

        @Synchronized
        fun getInstance(context: Context) : DBHelper{
            if (instance == null){
                instance = DBHelper(context.applicationContext)
            }

            return instance as DBHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(NextMatch.TABLE_NEXT, true,
            NextMatch.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            NextMatch.ID_EVENT to TEXT + UNIQUE,
            NextMatch.STR_EVENT to TEXT,
            NextMatch.STR_HOME_TEAM to TEXT,
            NextMatch.STR_AWAY_TEAM to TEXT,
            NextMatch.DATE_EVENT to TEXT,
            NextMatch.STR_TIME to TEXT,
            NextMatch.IMG_HOME_BADGE to TEXT,
            NextMatch.IMG_AWAY_BADGE to TEXT)

        db.createTable(PrevMatch.TABLE_PREV, true,
            PrevMatch.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            PrevMatch.ID_EVENT to TEXT + UNIQUE,
            PrevMatch.STR_EVENT to TEXT,
            PrevMatch.STR_HOME_TEAM to TEXT,
            PrevMatch.STR_AWAY_TEAM to TEXT,
            PrevMatch.INT_HOME_SCORE to TEXT,
            PrevMatch.INT_AWAY_SCORE to TEXT,
            PrevMatch.DATE_EVENT to TEXT,
            PrevMatch.STR_TIME to TEXT,
            PrevMatch.IMG_HOME_BADGE to TEXT,
            PrevMatch.IMG_AWAY_BADGE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(NextMatch.TABLE_NEXT, true)
        db.dropTable(PrevMatch.TABLE_PREV, true)
    }

}

val Context.database : DBHelper
    get() = DBHelper.getInstance(applicationContext)