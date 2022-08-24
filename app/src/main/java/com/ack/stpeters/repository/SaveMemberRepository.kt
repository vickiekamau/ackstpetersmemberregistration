package com.ack.stpeters.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ack.stpeters.model.Member
import com.ack.stpeters.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class SaveMemberRepository(context: Context){

    private val apiInterface = ApiInterface.invoke()

    suspend fun saveMembers(member: Member) = apiInterface.saveMembers(member)


    fun saveMember(member: Member): MutableList<String>{
        val mutableLiveData = mutableListOf<Member>()
        var names = mutableListOf<String>()


        val apiInterface: ApiInterface = ApiInterface.invoke()
        val call: Call<Member> = apiInterface.saveData(member)



        call.enqueue(object : Callback<Member> {
            override fun onResponse(call: Call<Member>, response: Response<Member>) {
                if (response.body() != null && response.isSuccessful) {
                    //list = (response.body() as ArrayList<WorldResponseItem>?)!!
                    //countryView.onGetResult(response.body()!!)
                    val fName = response.body()!!.fname
                    val sName = response.body()!!.surname
                    val oName = response.body()!!.otherNames

                    names.add(fName)
                    names.add(sName)
                    names.add(oName)

                    Log.d("created users name", "$fName, $sName,$oName")
                    //return

                    mutableLiveData.add(response.body()!!)
                    response.body()?.let { list ->
                        Log.d("member inserted",list.toString())

                    }

                }
            }


            override fun onFailure(call: Call<Member>, t: Throwable) {
                if (t is Exception) {
                    //Toast.makeText(Dashboard@this, "this is an actual network failure :( inform the user and possibly retry", Toast.LENGTH_SHORT).show();
                    Timber.tag("Error2").d(t.localizedMessage)
                } else {
                    //Toast.makeText(Dashboard@this, "conversion issue! big problems :(", Toast.LENGTH_SHORT).show();
                    // todo log to some central bug tracking service
                    Timber.tag("Error").d("Data conversion issue!")
                }
            }

        })
        Log.e("member inserted2", names.toString())
        return names
    }

}