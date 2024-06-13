package com.example.clientservertesttask.client

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.clientservertesttask.client.ui.theme.ClientServerTestTaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ClientServerTestTaskTheme {
                /*Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                }*/
            }
        }
    }
}