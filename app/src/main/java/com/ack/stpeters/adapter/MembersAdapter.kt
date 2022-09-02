package com.ack.stpeters.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ack.stpeters.R
import com.ack.stpeters.model.FetchMembers
import com.google.android.material.chip.Chip

class MembersAdapter(private val memberList: ArrayList<FetchMembers>,
                     private val context: Context):RecyclerView.Adapter<MembersAdapter.MemberViewHolder>() {

        inner class MemberViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val memberName: TextView = itemView.findViewById(R.id.tv_name)
            val regNo: TextView = itemView.findViewById(R.id.tv_reg_no)
            val phoneNo: TextView = itemView.findViewById(R.id.tv_phone_no)
            val dob : TextView = itemView.findViewById(R.id.tv_dob)
            val gender : Chip = itemView.findViewById(R.id.chip_gender)
            val view: View = itemView


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
            return MemberViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.members_items,parent,false))
        }

        override fun getItemCount(): Int {
            Log.d("member size", memberList.size.toString())
            return memberList.size

        }

        override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {


            val memberItem = memberList[position]
            val fName = memberItem.fname
            val surname = memberItem.surname
            val otherN = memberItem.otherNames
            val genderSymbol = memberItem.gender


            holder.regNo.text = memberItem.regNo
            "$fName $surname $otherN".also { holder.memberName.text = it }
            holder.phoneNo.text = memberItem.phoneNo
            holder.dob.text = memberItem.dob
            holder.gender.text = genderSymbol.substring(0,1)


        }

    }