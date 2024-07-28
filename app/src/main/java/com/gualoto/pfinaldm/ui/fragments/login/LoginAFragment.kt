package com.gualoto.pfinaldm.ui.fragments.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.gualoto.pfinaldm.R
import com.gualoto.pfinaldm.databinding.FragmentLoginaBinding
import com.gualoto.pfinaldm.ui.activities.MainActivity
import com.gualoto.pfinaldm.ui.core.ManageUIStates
import com.gualoto.pfinaldm.ui.core.UIStates
import com.gualoto.pfinaldm.ui.fragments.main.MenuFragment
import com.gualoto.pfinaldm.ui.viewmodels.login.LoginFragmentVM
import java.util.concurrent.Executor

class LoginAFragment : Fragment() {

    private lateinit var binding: FragmentLoginaBinding
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    private lateinit var biometricManager: BiometricManager
    private lateinit var managerUIStates: ManageUIStates
    private val loginFragmentVM: LoginFragmentVM by viewModels()
    private lateinit var auth: FirebaseAuth

    private lateinit var sharedPreferences: SharedPreferences

    private val sharedPreferencesEditor: SharedPreferences.Editor by lazy {
        sharedPreferences.edit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initVariables()
        initListeners()
        initiObservers()
    }

    private fun initVariables() {
        auth = FirebaseAuth.getInstance()
        managerUIStates = ManageUIStates(requireActivity(), binding.lytLoading.mainLayout)
        sharedPreferences = requireContext().getSharedPreferences("login_prefs", Context.MODE_PRIVATE)
    }

    /*private fun initiObservers() {
        loginFragmentVM.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIStates.Success -> fetchNicknameAndNavigate()
                is UIStates.Error -> {
                    Snackbar.make(binding.root, state.message, Snackbar.LENGTH_LONG).show()
                    managerUIStates.invoke(state)
                }
                else -> managerUIStates.invoke(state)
            }
        }
    }*/

    private fun initiObservers() {
        loginFragmentVM.uiState.observe(viewLifecycleOwner) { state ->
            if (state is UIStates.Success) {
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            } else {
                managerUIStates.invoke(state)
            }
        }

        loginFragmentVM.idUser.observe(viewLifecycleOwner) { id ->
            startActivity(
                Intent(
                    requireActivity(),
                    MenuFragment::class.java
                )
            )
            requireActivity().finish()
        }
    }

    private fun initListeners() {
        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.etxtUser.text.toString()
            val password = binding.etxtPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginFragmentVM.authWhitFireBase(email, password, auth, requireActivity())
            } else {
                Toast.makeText(requireContext(), "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

        // Carga las credenciales guardadas
        loadSavedCredentials()
    }

    private fun saveCredentials(email: String, password: String) {
        sharedPreferences.edit().apply {
            putString("email", email)
            putString("password", password)
            apply()
        }
    }

    private fun clearCredentials() {
        sharedPreferences.edit().apply {
            remove("email")
            remove("password")
            apply()
        }
    }

    private fun loadSavedCredentials() {
        val savedEmail = sharedPreferences.getString("email", "")
        val savedPassword = sharedPreferences.getString("password", "")
        binding.etxtUser.setText(savedEmail)
        binding.etxtPassword.setText(savedPassword)
    }


    private fun fetchNicknameAndNavigate() {
        val user = auth.currentUser ?: return
        val userId = user.uid

        val firestore = FirebaseFirestore.getInstance()
        val userRef = firestore.collection("users").document(userId)

        userRef.get().addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val nickname = document.getString("nickname") ?: "Nickname"
                val intent = Intent(requireActivity(), MainActivity::class.java)
                intent.putExtra("USER_NICKNAME", nickname)
                startActivity(intent)
                requireActivity().finish()
            } else {
                Toast.makeText(requireContext(), "No nickname found", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { e ->
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
        }
    }
}
