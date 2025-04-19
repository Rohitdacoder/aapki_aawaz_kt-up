package com.example.aapki_awaaz.bucket

import android.content.Context
//import android.icu.util.TimeUnit
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.InputStream
import java.util.*
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

@Composable
fun UploadScreen(context: Context) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UploadButton(context, "application/pdf", "PDF", coroutineScope)
        Spacer(modifier = Modifier.height(20.dp))
        UploadButton(context, "video/*", "Video", coroutineScope)
        Spacer(modifier = Modifier.height(20.dp))
        UploadButton(context, "image/*", "Image", coroutineScope)
    }
}

@Composable
fun UploadButton(context: Context, mimeType: String, fileType: String, coroutineScope: CoroutineScope) {
    var selectedUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedUri = uri
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { launcher.launch(mimeType) }) {
            Text("Select $fileType")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                selectedUri?.let { uri ->
                    coroutineScope.launch {
                        uploadFileToSupabase(context, uri)
                    }
                }
            },
            enabled = selectedUri != null
        ) {
            Text("Upload $fileType")
        }
    }
}

suspend fun uploadFileToSupabase(context: Context, uri: Uri) {
    withContext(Dispatchers.IO) {
        val supabaseUrl = "https://iapdsorocwpducvnmwxj.supabase.co/storage/v1/object/pdf/yourfilename.pdf" // No "/buckets/YOUR_BUCKET_NAME/o"
        val supabaseApiKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImlhcGRzb3JvY3dwZHVjdm5td3hqIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTc0NDgxMDc1NCwiZXhwIjoyMDYwMzg2NzU0fQ.JgfUAEdcrVXosOF8oWT2g8Emcov1GsDhHTRghznv5z4"
        val bucketName = "pdf" // Set your real bucket name!

        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val fileBytes = inputStream?.readBytes()
        inputStream?.close()

        if (fileBytes == null) {
            println("Error: Could not read file bytes")
            return@withContext
        }

        val fileExtension = when {
            context.contentResolver.getType(uri)?.contains("pdf") == true -> "pdf"
            context.contentResolver.getType(uri)?.contains("video") == true -> "mp4"
            context.contentResolver.getType(uri)?.contains("image") == true -> "jpg"
            else -> "bin"
        }

        val fileName = "${UUID.randomUUID()}.$fileExtension" // or .jpg/.mp4 based on selected file

        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        val requestBody = RequestBody.create(
            "application/octet-stream".toMediaTypeOrNull(), fileBytes
        )

        val request = Request.Builder()
            .url("$supabaseUrl/$bucketName/$fileName") // Correct URL
            .header("Authorization", "Bearer $supabaseApiKey")
            .header("Content-Type", "application/octet-stream")
            .header("x-upsert", "true") // Important! allow overwrite or upload
            .post(requestBody) // Use POST not PUT
            .build()

        val response = client.newCall(request).execute()

        if (response.isSuccessful) {
            println("✅ File uploaded successfully: $fileName")

            val fileUrl = "$supabaseUrl/$bucketName/$fileName"
            saveFileUrlToDatabase(fileUrl,client,supabaseApiKey,supabaseUrl)
        } else {
            println("❌ Upload failed: ${response.code} ${response.message}")
            println("❌ Error body: ${response.body?.string()}")
        }
    }
}

suspend fun CoroutineScope.saveFileUrlToDatabase(fileUrl: String, client: OkHttpClient, supabaseApiKey: String, supabaseUrl: String) {
    val json = """
        {
            "file_url": "$fileUrl"
            }
            """.trimIndent()

    val requestBody = RequestBody.create(
        "application/json".toMediaTypeOrNull(), json
    )

    val request = Request.Builder()
        .url("$supabaseUrl/rest/pdfuri") // Change 'your_table_name'
        .header("Authorization", "Bearer $supabaseApiKey")
        .header("Content-Type", "application/json")
        .header("Prefer", "return=representation") // Optional: returns inserted row
        .post(requestBody)
        .build()

    val response = client.newCall(request).execute()

    if (response.isSuccessful) {
        println("✅ URL saved to database successfully")
    }
    else {
        println("❌ Error saving URL to database: ${response.code} ${response.message}")
        println("❌ Error body: ${response.body?.string()}")
    }
}


// Extension function for OkHttpClient.Builder to set the connectTimeout
fun OkHttpClient.Builder.connectTimeout(
    timeout: Long,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): OkHttpClient.Builder {
    return this.connectTimeout(timeout, timeUnit)
}

fun OkHttpClient.Builder.readTimeout(
    timeout: Long,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): OkHttpClient.Builder {
    return this.readTimeout(timeout, timeUnit)
}

fun OkHttpClient.Builder.setwriteTimeout(
    timeout: Long,
    timeUnit: TimeUnit = TimeUnit.SECONDS
): OkHttpClient.Builder {
    return this.writeTimeout(timeout, timeUnit)
}
