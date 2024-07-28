package com.gualoto.pfinaldm.ui.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore

import com.google.firebase.ktx.Firebase
import com.gualoto.pfinaldm.R

import com.gualoto.pfinaldm.databinding.FragmentSaveFireStoreBinding
import com.gualoto.pfinaldm.ui.core.ManageUIStates
import com.gualoto.pfinaldm.ui.entities.users.UserLogin
import com.gualoto.pfinaldm.ui.viewmodels.login.SaveFireStoreVM


class SaveFireStoreFragment : Fragment() {

    private lateinit var binding: FragmentSaveFireStoreBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var auth:FirebaseAuth

    private lateinit var manageUIState: ManageUIStates


    //Hago la referencia al VM
    private val saveFireStoreVM : SaveFireStoreVM by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSaveFireStoreBinding.bind(inflater.inflate(
            R.layout.fragment_save_fire_store
        ,container,false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db= Firebase.firestore
        auth=Firebase.auth

        //Inicializo
        manageUIState = ManageUIStates(requireActivity(),binding.lytLoading.mainLayout)

        initListeners()
        initObservers()
    }

    private fun initObservers() {
        saveFireStoreVM.userUI.observe(viewLifecycleOwner){state->

            manageUIState.invoke(state)

        }

        saveFireStoreVM.userLogin.observe(viewLifecycleOwner){
                binding.txtData.text=it.uuid
           //   binding.txtData.text=it.name
            //  binding.txtData.text=it.lastName


        }
    }

    private fun initListeners() {
        binding.btnSave.setOnClickListener{
            val user = UserLogin(
                auth.currentUser!!.uid, //Con esto anexo el email con los demas dato del usuario.
                binding.Name.text.toString(),
                binding.LastName.text.toString()
            )

            saveFireStoreVM.saveUserFireStore(user)

        }
        binding.btnGet.setOnClickListener{
            saveFireStoreVM.getUserByIdFireStore(auth.currentUser!!.uid)

        }

    }
}