import android.net.Uri
import com.example.aapki_awaaz.database.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

// Data class to represent a complaint
@Serializable
data class Complaint(
    val state: String,
    val city: String,
    val complaintTo: String,
    val ministry: String,
    val grievance: String,
    val address: String,
    val pdfUri: String? = null, // Can be null
    val imageUri: String? = null, // Can be null
    val videoUri: String? = null // Can be null
)

// Function to submit a complaint to the 'complaints' table in Supabase
suspend fun submitComplaint(
    state: String,
    city: String,
    complaintTo: String,
    ministry: String,
    grievance: String,
    address: String,
    pdfUri: Uri?,
    imageUri: Uri?,
    videoUri: Uri?
) {
    return withContext(Dispatchers.IO) {
        try {
            // Create a Complaint object
            val complaint = Complaint(
                state = state,
                city = city,
                complaintTo = complaintTo,
                ministry = ministry,
                grievance = grievance,
                address = address,
                pdfUri = pdfUri?.toString(), // Convert Uri to String
                imageUri = imageUri?.toString(), // Convert Uri to String
                videoUri = videoUri?.toString() // Convert Uri to String
            )

            // Insert the complaint into the 'complaints' table
            val response = SupabaseClient.client.from("usercomplaintform").insert(complaint)

            // Check if the insert was successful or if there was an error
            if (response != null) {
                // Successfully inserted the complaint
                println("Complaint submitted successfully!")
                println("Inserted Data: $response")
            } else {
                // If response is null, throw an exception
                throw Exception("Failed to insert complaint.")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Handle error appropriately, e.g., show a user-friendly error message
            throw Exception("An error occurred while submitting the complaint: ${e.message}")
        }
    }
}



