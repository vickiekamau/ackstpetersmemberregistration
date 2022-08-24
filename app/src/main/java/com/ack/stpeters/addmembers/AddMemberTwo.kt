package com.ack.stpeters.addmembers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ack.stpeters.R
import com.ack.stpeters.databinding.FragmentAddMemberTwoBinding
import com.ack.stpeters.utils.InputValidator
import kotlinx.coroutines.NonCancellable.children


class AddMemberTwo : Fragment() {

    private lateinit var binding:FragmentAddMemberTwoBinding
    val args :AddMemberTwoArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddMemberTwoBinding.inflate(inflater,container,false)

        //baptized dropdown strings
        val baptized = resources.getStringArray(R.array.yes_no)
        val baptizedStringAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item,baptized)
        binding.baptizedAutoCompleteTextView.setAdapter(baptizedStringAdapter)

        //confirmed dropdown strings
        val confirmed = resources.getStringArray(R.array.yes_no)
        val confirmedStringAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item,confirmed)
        binding.confirmedAutoCompleteTextView.setAdapter(confirmedStringAdapter)

        //cell group dropdown strings
        val cell = resources.getStringArray(R.array.cell_group)
        val cellStringAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item,cell)
        binding.cellAutoCompleteTextView.setAdapter(cellStringAdapter)

        //service dropdown strings
        val service = resources.getStringArray(R.array.service)
        val serviceStringAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item,service)
        binding.serviceAutoCompleteTextView.setAdapter(serviceStringAdapter)

        val fName = args.fname
        val surname = args.surname
        val otherName = args.othername
        val gender = args.gender
        val maritalStatus = args.maritalStatus
        val children = args.children
        val dob = args.dob


        binding.next.setOnClickListener(View.OnClickListener { view -> inputValidator(fName,surname,otherName,gender,maritalStatus,children,dob)})



        return binding.root
    }

    private fun inputValidator(
        fName: String,
        surname: String,
        otherName: String,
        gender: String,
        maritalStatus: String,
        children: String,
        dob: String
    ) {
        val validator = InputValidator()

        if(validator.validateRequired(binding.baptizedLayout,binding.baptizedAutoCompleteTextView) &&
            validator.validateRequired(binding.confirmedLayout,binding.confirmedAutoCompleteTextView) &&
            validator.validateRequired(binding.cellLayout, binding.cellAutoCompleteTextView)&&
            validator.validateRequired(binding.serviceLayout, binding.serviceAutoCompleteTextView))
        {
            val baptized = binding.baptizedAutoCompleteTextView.text.toString()
            val confirmed = binding.confirmedAutoCompleteTextView.text.toString()
            val cellGroup = binding.cellAutoCompleteTextView.text.toString()
            val service = binding.serviceAutoCompleteTextView.text.toString()


            val action =  AddMemberTwoDirections.actionNavigationAddMemberTwoToNavigationAddMemberThree(fName,surname,otherName,gender,maritalStatus,children, dob,baptized,confirmed,cellGroup,service)
            findNavController().navigate(action)
        }
    }


}