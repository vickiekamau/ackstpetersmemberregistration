package com.ack.stpeters.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.ack.stpeters.db.AppDatabase
import com.ack.stpeters.model.FetchMembers
import com.ack.stpeters.model.Member
import com.ack.stpeters.network.ApiInterface

class SaveMemberRepository(context: Context){

    private val db: AppDatabase = AppDatabase. getDB(context)
    private val apiInterface = ApiInterface.invoke()

    suspend fun saveMembers(member: Member) = apiInterface.saveMembers(member)

    suspend fun getMembers(): LiveData<List<FetchMembers>> {

        val members = apiInterface.getMembers()
        val membersDao = db.membersDao()

        try{
            membersDao.clearMembers()
            membersDao.syncMembers(members)
            Log.d("Members results", members.toString())
        }catch (e:Exception){
            Log.d("Exception", e.message.toString())
        }
        return db.membersDao().getAllMembers()
    }

    fun searchMembers(text:String): LiveData<List<FetchMembers>>{
        Log.d("searched record",db.membersDao().getSearchResult(text).toString() )
        return db.membersDao().getSearchResult(text)
    }





}