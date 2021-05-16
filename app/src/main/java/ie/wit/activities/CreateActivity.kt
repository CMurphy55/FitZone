package ie.wit.activities

import android.content.Intent
import android.location.Location
import android.media.Image
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ie.wit.R
import ie.wit.main.TransferApp
import ie.wit.models.TransferModel
import ie.wit.utils.readImageFromPath
import kotlinx.android.synthetic.main.card_donation.*
import kotlinx.android.synthetic.main.fragment_donate.*
import kotlinx.android.synthetic.main.fragment_donate.chooseImage
import kotlinx.android.synthetic.main.fragment_donate.enterButton
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import android.graphics.Bitmap
import ie.wit.utils.showImagePicker

class CreateActivity :  AppCompatActivity(), AnkoLogger {
    var meals = TransferModel()
    lateinit var app: TransferApp
    val IMAGE_REQUEST = 1
    val LOCATION_REQUEST = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)
        info("App Started")

        app = application as TransferApp
        var edit = false




        if (intent.hasExtra("Dog dec")) {
            edit = true
            meals = intent.extras?.getParcelable<TransferModel>("cat dec")!!
            calories.setText(meals.calories)
            description.setText(meals.description)

            if (meals.image != null) {
                chooseImage.setText(R.string.change_meal_image)
            }
            enterButton.setText(R.string.save_meals)
        }

        enterButton.setOnClickListener() {
            meals.calories = calories.text.toString()
            meals.description = description.text.toString()
            if (meals.description.isEmpty()) {
                toast(R.string.enter_desc)
            } else {
                if (edit) {
                    app.meals.update(meals.copy())
                } else {
                    app.meals.create(meals.copy())
                }
            }
            info("add Button Pressed: $description")
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }

        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

        }
        return super.onOptionsItemSelected(item)
    }


}
