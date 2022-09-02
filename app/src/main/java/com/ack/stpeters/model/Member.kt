package com.ack.stpeters.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

data class Member(

	@field:SerializedName("fname")
	val fname:String,

	@field:SerializedName("surname")
	val surname: String,

	@field:SerializedName("otherNames")
	val otherNames: String,

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("maritalStatus")
	val maritalStatus: String,

	@field:SerializedName("children")
	val children: String,

	@field:SerializedName("dob")
	val dob: String,

	@field:SerializedName("confirmed")
	val confirmed: String,

	@field:SerializedName("cellGroup")
	val cellGroup: String,

	@field:SerializedName("service")
	val service: String,


	@field:SerializedName("profession")
	val profession: String,


	@field:SerializedName("occupation")
	val occupation: String,

	@field:SerializedName("phoneNo")
	val phoneNo: String
	)
