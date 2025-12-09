package com.muindi.stephen.co_opbankapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.muindi.stephen.co_opbankapp.navigation.AppNavGraph
import com.muindi.stephen.co_opbankapp.presentation.ui.theme.COOPBankAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            COOPBankAppTheme {
                AppNavGraph()
            }
        }
    }
}
