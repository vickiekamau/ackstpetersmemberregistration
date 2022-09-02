package com.ack.stpeters.addmembers

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.ack.stpeters.model.Member
import com.ack.stpeters.repository.SaveMemberRepository
import com.ack.stpeters.response.Resource
import kotlinx.coroutines.*

class AddMemberViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = SaveMemberRepository(application)

    var save: Job? = null


    private val _savedMemberStatus = MutableLiveData<Resource<String>>()
    val savedMemberStatus: LiveData<Resource<String>> = _savedMemberStatus


    /**fun saveMember(member: Member){
        viewModelScope.launch {
            _savedMemberStatus.postValue(Resource.loading(null))
            try {
                val names = repository.saveMember(member)

                val fn = names[0]
                val sn = names[1]
                val on = names[2]

                _savedMemberStatus.postValue(Resource.success(fn, ""))
            } catch (e: Exception){
                Log.d("response2", e.message.toString())
                _savedMemberStatus.postValue(Resource.error(null, e.message!!))
            }
        }

    }*/
     fun saveMembersData(member: Member){
         save = CoroutineScope(Dispatchers.IO).launch {
             _savedMemberStatus.postValue(Resource.loading(null))
             val response = repository.saveMembers(member)
             withContext(Dispatchers.Main) {
                 if (!response.fname.isEmpty()) {
                     _savedMemberStatus.postValue(Resource.success(response.fname, ""))
                 } else {
                     _savedMemberStatus.postValue(Resource.error(null, "Error Occured"))
                 }
             }
         }
     }
}