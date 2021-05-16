package ie.wit.models

import android.content.Context
import android.system.Os.read
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import ie.wit.models.TransferModel
import ie.wit.models.TransferStore
import ie.wit.utils.exists
import ie.wit.utils.read
import ie.wit.utils.write
import org.jetbrains.anko.AnkoLogger
import java.lang.reflect.Type
import java.nio.file.Files.exists
import java.nio.file.Files.write
import java.util.*

const val JSON_FILE = "Meals.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType : Type = object : TypeToken<java.util.ArrayList<TransferModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class MealJSONStore (val context: Context) : TransferStore, AnkoLogger {

    private var meals = mutableListOf<TransferModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<TransferModel> {
        return meals
    }

    override fun findById(id: String): TransferModel? {
        TODO("Not yet implemented")
    }

    override fun create(meal: TransferModel) {

        meals.add(meal)
        serialize()
    }

    override fun update(meal: TransferModel) {
        val MealsList = findAll() as ArrayList<TransferModel>
        var foundMeal: TransferModel? = MealsList.find { p -> p._id == meal._id }
        if (foundMeal != null) {
            foundMeal.description = meal.description
            foundMeal.calories = meal.calories

        }
        serialize()
    }

    override fun delete(meal: TransferModel) {
        TODO("Not yet implemented")
    }



    private fun serialize() {
        val jsonString = gsonBuilder.toJson(meals, listType)
        write (context, JSON_FILE, jsonString)
    }



    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        meals = Gson().fromJson(jsonString, listType)
    }
}


