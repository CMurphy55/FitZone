package ie.wit.main

import android.app.Application
import ie.wit.api.TransferService
import ie.wit.models.TransferModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class TransferApp : Application(), AnkoLogger {

    //lateinit var donationsStore: DonationMemStore
    lateinit var donationService: TransferService
    var donations = ArrayList<TransferModel>()

    override fun onCreate() {
        super.onCreate()
        info("Donation App started")
        donationService = TransferService.create()
        info("Donation Service Created")
    }
}