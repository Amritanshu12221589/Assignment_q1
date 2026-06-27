package com.example.studentregistration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StudentRegistrationScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentRegistrationScreen() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var rollNumber by remember { mutableStateOf("") }
    var selectedGender by remember { mutableStateOf("") }
    var agreeTerms by remember { mutableStateOf(false) }
    var hostelRequired by remember { mutableStateOf(false) }

    var nameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var rollError by remember { mutableStateOf("") }
    var genderError by remember { mutableStateOf("") }
    var termsError by remember { mutableStateOf("") }

    var submittedInfo by remember { mutableStateOf("") }
    var showResult by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Student Registration",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        // Name Field
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                nameError = ""
            },
            label = { Text("Full Name *") },
            isError = nameError.isNotEmpty(),
            supportingText = {
                if (nameError.isNotEmpty()) Text(nameError, color = MaterialTheme.colorScheme.error)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Roll Number Field
        OutlinedTextField(
            value = rollNumber,
            onValueChange = {
                rollNumber = it
                rollError = ""
            },
            label = { Text("Roll Number *") },
            isError = rollError.isNotEmpty(),
            supportingText = {
                if (rollError.isNotEmpty()) Text(rollError, color = MaterialTheme.colorScheme.error)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        // Email Field
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = ""
            },
            label = { Text("Email Address *") },
            isError = emailError.isNotEmpty(),
            supportingText = {
                if (emailError.isNotEmpty()) Text(emailError, color = MaterialTheme.colorScheme.error)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        )

        // Gender Radio Buttons
        Text(
            text = "Gender *",
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            listOf("Male", "Female", "Other").forEach { gender ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(end = 12.dp)
                ) {
                    RadioButton(
                        selected = selectedGender == gender,
                        onClick = {
                            selectedGender = gender
                            genderError = ""
                        }
                    )
                    Text(text = gender)
                }
            }
        }

        if (genderError.isNotEmpty()) {
            Text(
                text = genderError,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Checkboxes
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = hostelRequired,
                onCheckedChange = { hostelRequired = it }
            )
            Text("Hostel Required")
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = agreeTerms,
                onCheckedChange = {
                    agreeTerms = it
                    termsError = ""
                }
            )
            Text("I agree to the Terms & Conditions *")
        }

        if (termsError.isNotEmpty()) {
            Text(
                text = termsError,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Submit Button
        Button(
            onClick = {
                var valid = true

                if (name.trim().isEmpty()) {
                    nameError = "Name is required"
                    valid = false
                }
                if (rollNumber.trim().isEmpty()) {
                    rollError = "Roll number is required"
                    valid = false
                }
                if (email.trim().isEmpty()) {
                    emailError = "Email is required"
                    valid = false
                } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailError = "Enter a valid email"
                    valid = false
                }
                if (selectedGender.isEmpty()) {
                    genderError = "Please select a gender"
                    valid = false
                }
                if (!agreeTerms) {
                    termsError = "You must agree to the terms"
                    valid = false
                }

                if (valid) {
                    submittedInfo = buildString {
                        appendLine("Registration Successful!")
                        appendLine("─────────────────────────")
                        appendLine("Name       : $name")
                        appendLine("Roll No.   : $rollNumber")
                        appendLine("Email      : $email")
                        appendLine("Gender     : $selectedGender")
                        appendLine("Hostel     : ${if (hostelRequired) "Yes" else "No"}")
                    }
                    showResult = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Submit Registration", fontSize = 16.sp)
        }

        // Result Card
        if (showResult) {
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE8F5E9)
                )
            ) {
                Text(
                    text = submittedInfo,
                    modifier = Modifier.padding(16.dp),
                    fontSize = 14.sp,
                    color = Color(0xFF1B5E20)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
