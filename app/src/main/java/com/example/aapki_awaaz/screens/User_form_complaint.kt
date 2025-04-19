package com.example.aapki_awaaz.screens

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aapki_awaaz.Boom.AnimatedButton
import fetchCities
import fetchStates
import submitComplaint
import kotlinx.coroutines.launch



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComplaintFormScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // State for managing UI loading/error states
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Form Data
    var selectedState by remember { mutableStateOf("") }
    var selectedCity by remember { mutableStateOf("") }
    var complaintTo by remember { mutableStateOf("") }
    var ministry by remember { mutableStateOf("") }
    var grievance by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var selectedPdfUri by remember { mutableStateOf<Uri?>(null) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedVideoUri by remember { mutableStateOf<Uri?>(null) }

    // File Selection
    val pdfPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri -> selectedPdfUri = uri }
    )
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )
    val videoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedVideoUri = uri }
    )

    // States and Cities Data
    var states by remember { mutableStateOf<List<String>>(emptyList()) }
    var cities by remember { mutableStateOf<List<String>>(emptyList()) }
    var filteredStates by remember { mutableStateOf<List<String>>(emptyList()) }
    var filteredCities by remember { mutableStateOf<List<String>>(emptyList()) }


    // Expanded State for dropdowns
    var expandedState by remember { mutableStateOf(false) }
    var expandedCity by remember { mutableStateOf(false) }

    // Manage query of State and City
    var stateQuery by remember { mutableStateOf("") }
    var cityQuery by remember { mutableStateOf("") }


    // Fetch States and Cities on initial composition
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            isLoading = true
            errorMessage = null
            try {
                states = fetchStates()
                filteredStates = states
                cities = fetchCities()
                filteredCities = cities
            } catch (e: Exception) {
                errorMessage = "Failed to load states and cities."
            } finally {
                isLoading = false
            }
        }
    }

    // Filtering logic (now part of the UI logic)
    fun filterStates(query: String) {
        filteredStates =
            if (query.isBlank()) states else states.filter { it.contains(query, ignoreCase = true) }
    }

    fun filterCities(query: String) {
        filteredCities =
            if (query.isBlank()) cities else cities.filter { it.contains(query, ignoreCase = true) }
    }

    // Submit Function (unchanged)
    fun submitForm() {
        coroutineScope.launch {
            try {
                isLoading = true
                errorMessage = null
                // Call the submitComplaint function
                println("Submitting complaint: $selectedState, $selectedCity, $complaintTo")
                submitComplaint(
                    state = selectedState,
                    city = selectedCity,
                    complaintTo = complaintTo,
                    ministry = ministry,
                    grievance = grievance,
                    address = address,
                    pdfUri = selectedPdfUri,
                    imageUri = selectedImageUri,
                    videoUri = selectedVideoUri
                )
                // Optionally clear the form or show a success message
                selectedState = ""
                selectedCity = ""
                complaintTo = ""
                ministry = ""
                grievance = ""
                address = ""
                selectedPdfUri = null
                selectedImageUri = null
                selectedVideoUri = null
                stateQuery = ""
                cityQuery = ""

                Toast.makeText(context, "Complaint submitted successfully!", Toast.LENGTH_SHORT)
                    .show()
            } catch (e: Exception) {
                errorMessage = "Failed to submit complaint."
            } finally {
                isLoading = false
            }
        }
    }

    // Main Content
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else if (errorMessage != null) {
            Text(text = "Error: $errorMessage", color = MaterialTheme.colorScheme.error)
        } else {
            Text(
                text = "File Complaint",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Complaint ID: CMP1980", fontSize = 16.sp)
            Spacer(modifier = Modifier.height(16.dp))

            // State Dropdown
            ExposedDropdownMenuBox(
                expanded = expandedState,
                onExpandedChange = { expandedState = !expandedState }
            ) {
                TextField(
                    value = stateQuery,
                    onValueChange = { newQuery ->
                        stateQuery = newQuery
                        filterStates(newQuery)
                        expandedState = true
                    },
                    label = { Text("State") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedState) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = expandedState,
                    onDismissRequest = {
                        expandedState = false
                    }
                ) {
                    filteredStates.forEach { state ->
                        DropdownMenuItem(
                            text = { Text(text = state) },
                            onClick = {
                                selectedState = state
                                stateQuery = state
                                expandedState = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // City Dropdown
            ExposedDropdownMenuBox(
                expanded = expandedCity,
                onExpandedChange = { expandedCity = !expandedCity }
            ) {
                TextField(
                    value = cityQuery,
                    onValueChange = { newQuery ->
                        cityQuery = newQuery
                        filterCities(newQuery)
                        expandedCity = true
                    },
                    label = { Text("City") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCity) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = expandedCity,
                    onDismissRequest = {
                        expandedCity = false
                    }
                ) {
                    filteredCities.forEach { city ->
                        DropdownMenuItem(
                            text = { Text(text = city) },
                            onClick = {
                                selectedCity = city
                                cityQuery = city
                                expandedCity = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Complaint To TextField
            OutlinedTextField(
                value = complaintTo,
                onValueChange = { complaintTo = it },
                label = { Text("Complaint To") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Ministry TextField
            OutlinedTextField(
                value = ministry,
                onValueChange = { ministry = it },
                label = { Text("Ministry") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Grievance TextField
            OutlinedTextField(
                value = grievance,
                onValueChange = { grievance = it },
                label = { Text("Grievance") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Address TextField
            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text("Address") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
            )
            Spacer(modifier = Modifier.height(16.dp))

            //FileSelectAndUploadScreen(context = context)
            // File Select Buttons
            /*Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { pdfPickerLauncher.launch(arrayOf("application/pdf")) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Select PDF")
                }
                Button(
                    onClick = {
                        imagePickerLauncher.launch(
                            PickVisualMediaRequest(
                                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Select Image")
                }
                Button(
                    onClick = {
                        videoPickerLauncher.launch(
                            PickVisualMediaRequest(
                                mediaType = ActivityResultContracts.PickVisualMedia.VideoOnly
                            )
                        )
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Select Video")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Submit Button
            Button(
                onClick = { submitForm() },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Submit Complaint")
            }*/
            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AnimatedButton(
                    text = "Select PDF",
                    onClickAction = {
                        pdfPickerLauncher.launch(arrayOf("application/pdf"))
                    },
                    modifier = Modifier.weight(1f).height(60.dp)
                )

                AnimatedButton(
                    text = "Select Image",
                    onClickAction = {
                        imagePickerLauncher.launch(
                            PickVisualMediaRequest(
                                mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    },
                    modifier = Modifier.weight(1f).height(60.dp)
                )

                AnimatedButton(
                    text = "Select Video",
                    onClickAction = {
                        videoPickerLauncher.launch(
                            PickVisualMediaRequest(
                                mediaType = ActivityResultContracts.PickVisualMedia.VideoOnly
                            )
                        )
                    },
                    modifier = Modifier.weight(1f).height(60.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            AnimatedButton(
                text = "Submit Complaint",
                onClickAction = { submitForm() },
                modifier = Modifier.fillMaxWidth().height(60.dp),
                enabled = !isLoading
            )
        }
    }
}
