package com.ack.stpeters.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "member")

class FetchMembers (
    @PrimaryKey
    @ColumnInfo(name = "regNo") var regNo: String = "",
    @ColumnInfo(name = "fname") var fname:String="",
    @ColumnInfo(name = "surname") var surname: String="",
    @ColumnInfo(name = "otherNames") var otherNames:String="",
    @ColumnInfo(name = "gender") var gender:String="",
    @ColumnInfo(name = "maritalStatus") var maritalStatus: String="",
    @ColumnInfo(name = "children") var children: String="",
    @ColumnInfo(name = "dob") var dob: String="",
    @ColumnInfo(name = "confirmed") var confirmed: String="",
    @ColumnInfo(name = "service") var service: String ="",
    @ColumnInfo(name = "profession") var profession: String ="",
    @ColumnInfo(name = "occupation") var occupation: String ="",
    @ColumnInfo(name = "phoneNo") var phoneNo: String ="",
    @ColumnInfo(name = "dateCreated") var dateCreated:String="",

    )

@Dao
interface MembersDao {
    @Query("SELECT * FROM member")
    fun getAllMembers(): LiveData<List<FetchMembers>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(members: List<FetchMembers>)

    @Query("DELETE FROM member")
    fun clearMembers()


    @Transaction
    fun syncMembers(members: List<FetchMembers>) {
        //clearProperty()
        insert(members)
    }


    @Query("Select * from member where fname like '%' || :search || '%' or surname like '%' || :search || '%' or otherNames like '%' || :search || '%' ")
    fun getSearchResult(search:String): LiveData<List<FetchMembers>>
}


