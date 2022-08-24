package com.ack.stpeters.addmembers

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ack.stpeters.R
import com.ack.stpeters.databinding.FragmentAddMemberOneBinding
import com.ack.stpeters.utils.InputValidator
import java.lang.String
import java.util.*


class AddMemberOne : Fragment() {
    private lateinit var binding : FragmentAddMemberOneBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddMemberOneBinding.inflate(inflater,container,false)



        //gender dropdown strings
        val gender = resources.getStringArray(R.array.gender)
        val genderStringAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item,gender)
        binding.genderAutoCompleteTextView.setAdapter(genderStringAdapter)

        //marital dropdown strings
        val marital = resources.getStringArray(R.array.marital_status)
        val maritalStringAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item,marital)
        binding.maritalAutoCompleteTextView.setAdapter(maritalStringAdapter)

        binding.next.setOnClickListener(View.OnClickListener { view -> inputValidation() })
        binding.dob.setEndIconOnClickListener { v ->
            val mCalendar: Calendar = Calendar.getInstance(TimeZone.getDefault())
            DatePickerDialog(
                requireContext(),
                { mView: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                    Objects.requireNonNull(binding.dob.editText)!!.setText(String.format(
                        Locale.getDefault(),
                        "%02d%02d%d",
                        dayOfMonth,
                        month + 1,
                        year
                    )
                    )
                },
                mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }




        return binding.root
    }

    private fun inputValidation() {
        val validator = InputValidator()

        if(validator.validateRequired(binding.fName,binding.fNameInput) &&
            validator.validateRequired(binding.surname,binding.surnameInput) &&
            validator.validateRequired(binding.otherName, binding.otherNameInput)&&
            validator.validateRequired(binding.genderLayout, binding.genderAutoCompleteTextView)&&
            validator.validateRequired(binding.maritalLayout, binding.maritalAutoCompleteTextView)&&
            validator.validateRequired(binding.children, binding.childrenInput)&&
            validator.validateRequired(binding.dob, binding.dobInput))
        {
            val fName = binding.fNameInput.text.toString()
            val surname = binding.surnameInput.text.toString()
            val otherName = binding.otherNameInput.text.toString()
            val gender = binding.genderAutoCompleteTextView.text.toString()
            val maritalStatus = binding.maritalAutoCompleteTextView.text.toString()
            val children = binding.childrenInput.text.toString()
            val dob = validator.getDOB(binding.dobInput.text.toString())


            val action =  AddMemberOneDirections.actionNavigationAddMemberToNavigationAddMemberTwo(fName,surname,otherName,gender,maritalStatus, children, dob)
            findNavController().navigate(action)
        }
    }


}