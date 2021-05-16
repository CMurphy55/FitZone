package ie.wit.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TransferModel(var _id: String = "N/A",
                         var calories: String = "N/A",
                         var amount: Int = 0,
                         var image: String = "",
                         var name: String = "",
                         var description: String = "N/A",
                         var message: String = "a message") : Parcelable


