package com.sdk.datastorepref

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sdk.datastorepref.databinding.ActivityMainBinding
import com.sdk.datastorepref.manager.DataStoreManager
import com.sdk.datastorepref.model.User
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var dataStoreManager: DataStoreManager
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataStoreManager = DataStoreManager(this)

        binding.btnSave.setOnClickListener {
            val name = binding.editName.text.toString().trim()
            val lastName = binding.editLastName.text.toString().trim()
            val age = binding.editAge.text.toString().trim()
            GlobalScope.launch(Dispatchers.IO) {
                dataStoreManager.saveUser(
                    user = User(
                        name = name,
                        lastName = lastName,
                        age = age
                    )
                )
            }
        }
        binding.btnGet.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                dataStoreManager.getUser().catch {
                    it.printStackTrace()
                }.collect {
                    withContext(Dispatchers.Main) {
                        binding.textView.text = "${it.name}\n${it.lastName}\n${it.age}"
                    }
                }
            }
        }
    }
}