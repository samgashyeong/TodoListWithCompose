package com.example.myapplication

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {

            val snackbarHostState = remember {
                SnackbarHostState()
            }
            val scope = rememberCoroutineScope()
            val items = remember { mutableStateListOf<String>() }
            val cnt = remember {
                mutableStateOf(1)
            }

            val showDialog = remember { mutableStateOf(false) }
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            items.add(cnt.value.toString())
                            cnt.value++
                            //startActivity(Intent(this, AddDataActivity::class.java))
//                            scope.launch {
//                                snackbarHostState.showSnackbar("test")
//                            }
                            showDialog.value = true

                        }) {
                            Icon(Icons.Filled.Add, contentDescription = "Localized description")
                        }
                    }) {
                    it
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp), ) {
                        recyclerView(items)
                    }
                    Dialog(showDialog)
                }
            }
        }
    }



    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun recyclerView(items: List<String>) {
        LazyColumn {
            items(items.size) { index ->
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(bottom = 10.dp), shape = RoundedCornerShape(8.dp), onClick = {
                }) {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)) {
                        Text(text = items[index], modifier = Modifier
                            .align(Alignment.TopStart))
                        Text(text = "box테스트", modifier = Modifier.align(Alignment.BottomEnd))
                    }
                }
            }
        }
    }


    @Composable
    fun Dialog(showDialog : MutableState<Boolean>){
        var showDialog = showDialog
        Column {
            if(showDialog.value){
                androidx.compose.ui.window.Dialog(onDismissRequest = { showDialog.value = false }) {
                    Card(shape = RoundedCornerShape(8.dp)) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "커스텀 다이얼로그", style = MaterialTheme.typography.bodyLarge)
                            Spacer(modifier = Modifier.height(20.dp))
                            Text("이것은 커스텀 다이얼로그의 내용입니다.")
                            Spacer(modifier = Modifier.height(20.dp))
                            Button(onClick = { showDialog.value = false }) {
                                Text("닫기")
                            }
                        }
                    }
                }
            }
        }
    }

}
