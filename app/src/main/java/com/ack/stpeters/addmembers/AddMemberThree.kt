package com.ack.stpeters.addmembers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import cn.pedant.SweetAlert.SweetAlertDialog
import com.ack.stpeters.MainActivity
import com.ack.stpeters.databinding.FragmentAddMemberThreeBinding
import com.ack.stpeters.model.Member
import com.ack.stpeters.response.Status
import com.ack.stpeters.utils.InputValidator
import com.ack.stpeters.utils.SweetAlerts
import timber.log.Timber


class AddMemberThree : Fragment() {

    private lateinit var binding:FragmentAddMemberThreeBinding
    val args: AddMemberThreeArgs by navArgs()
    private lateinit var mContext: Context
    private val viewModel: AddMemberViewModel by viewModels()
    private lateinit var sweetAlertDialog: SweetAlertDialog


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddMemberThreeBinding.inflate(inflater,container,false)

        mContext = container!!.context

        // initializing the sweet alert dialog
        sweetAlertDialog = SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE)

        val fName = args.fname
        val surname = args.surname
        val otherName = args.otherName
        val gender = args.gender
        val maritalStatus = args.maritalStatus
        val children = args.children
        val dob = args.dob
        val confirmed = args.confirmed
        val cellGroup = args.cellGroup
        val service = args.service

        binding.save.setOnClickListener(View.OnClickListener { view ->
            inputValidator(
                fName,
                surname,
                otherName,
                gender,
                maritalStatus,
                children,
                dob,
                confirmed,
                cellGroup,
                service
            )

        })



        return binding.root
    }

    private fun inputValidator(
        fName: String,
        surname: String,
        otherName: String,
        gender: String,
        maritalStatus: String,
        children: String,
        dob: String,
        confirmed: String,
        cellGroup: String,
        service: String
    ) {
        val validator = InputValidator()

        if (validator.validateRequired(binding.profession, binding.professionInput) &&
            validator.validateRequired(binding.occupation, binding.occupationInput) &&
            validator.validateRequired(binding.phoneNo, binding.phoneInput)
        ) {
            val profession = binding.professionInput.text.toString()
            val occupation = binding.occupationInput.text.toString()
            val phone = binding.phoneInput.text.toString()
            saveMembers(fName,surname,otherName,gender,maritalStatus,children,dob,confirmed,cellGroup,service,profession,occupation,phone)

        }

    }

    private fun saveMembers(fName: String, surname: String, otherName: String, gender: String, maritalStatus: String, children: String, dob: String, confirmed: String, cellGroup: String, service: String, profession: String, occupation: String, phone: String) {

        val saveMember = Member(fName, surname, otherName, gender, maritalStatus, children, dob, confirmed, cellGroup, service, profession, occupation, phone)

        //viewModel.saveMember(saveMember)
        viewModel.saveMembersData(saveMember)

        viewModel.savedMemberStatus.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    success(
                        mContext, "Success", "Member ${it.data} Saved Successfully",
                        dismiss = {
                            startActivity(Intent(mContext, MainActivity::class.java))

                        })


                }

                Status.LOADING -> {
                    loading(mContext, "Loading")
                    //inputValidation()
                }
                Status.ERROR -> {

                    error(mContext, "Ooops", it.message.toString(),
                        dismiss = { sweetAlertDialog.dismiss() }
                    )
                }

                else -> {

                }
            }
        })
    }

    private fun success(context: Context, title: String, msg: String, dismiss: (() -> Unit)) {
        SweetAlerts.success(
            context = context,
            title = title,
            msg = msg,
            dismiss = dismiss
        )
    }

    private fun error(context: Context, title: String, msg: String, dismiss: (() -> Unit)) {
        SweetAlerts.error(
            context = context,
            title = title,
            msg = msg,
            dismiss = dismiss
        )
    }

    private fun loading(context: Context, msg: String) {

        SweetAlerts.loading(
            context = context,
            msg = msg
        )
    }


}