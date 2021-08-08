import com.google.gson.annotations.SerializedName

data class customersModel (

	@SerializedName("id") val id : Int,
	@SerializedName("Name") val name : String,
	@SerializedName("Adress") val adress : String,
	@SerializedName("VergiNo") val vergiNo : String,
	@SerializedName("VergiDairesi") val vergiDairesi : String
)