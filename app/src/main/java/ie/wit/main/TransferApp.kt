package ie.wit.main

import android.app.Application
import ie.wit.api.TransferService
import ie.wit.models.MealJSONStore
import ie.wit.models.TransferModel
import ie.wit.models.TransferStore

import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class TransferApp : Application(), AnkoLogger {

    //lateinit var donationsStore: DonationMemStore
    lateinit var donationService: TransferService
    var donations = ArrayList<TransferModel>()
    lateinit var meals : TransferStore

    override fun onCreate() {
        super.onCreate()
        info("Starting")
        donationService = TransferService.create()
        meals = MealJSONStore(applicationContext)
        info("Meal Created")
    }
}