package ie.wit.models

import android.util.Log

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class DonationMemStore : TransferStore {

    var donations = ArrayList<TransferModel>()

        override fun findAll(): List<TransferModel> {
            return donations
        }

        override fun findById(id:String) : TransferModel? {
            val foundDonation: TransferModel? = donations.find { it._id == id }
            return foundDonation
        }

        override fun create(donation: TransferModel) {
            //donation._id = getId()
            donations.add(donation)
            logAll()
        }

        override fun update(donation: TransferModel) {

        }

        override fun delete(donation: TransferModel) {
            donations.remove(donation)
            logAll()
        }

        fun logAll() {
            Log.v("Donate","** Donations List **")
            donations.forEach { Log.v("Donate","${it}") }
        }
    }
