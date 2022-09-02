package com.ack.stpeters.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.ack.stpeters.model.Member
import com.ack.stpeters.repository.SaveMemberRepository
import com.ack.stpeters.response.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = SaveMemberRepository(application)


    val text : MutableLiveData<String> = MutableLiveData()
    val fetchMembers = text.switchMap{
        liveData(Dispatchers.IO) {
            if (it==null||it==""){
                val data = repository.getMembers()
                emitSource(data)
            }
            else{
                val data = repository.searchMembers(it)
                emitSource(data)
            }
        }
    }

    init {
        text.value = ""
    }

    fun search(searchText:String){
        text.value = searchText
        Log.d("searched data",searchText)
    }



}