package ie.wit.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import ie.wit.R
import ie.wit.main.TransferApp
import ie.wit.models.TransferModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.toast
import javax.security.auth.callback.Callback







class HomeFragment : Fragment(), AnkoLogger, Callback {

    lateinit var app: TransferApp
    private var meal = TransferModel()
    private var description = TransferModel()
    lateinit var loader : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as TransferApp
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {}
            }
    }



     fun setOnClickListener( layout: View) {
         layout.enterButton.setOnClickListener { it: View? ->
             val enter =
                 if (layout.AddName.text.isNotEmpty() || AddDescription.text.isNotEmpty() || calories.text.isNotEmpty()) {
                     meal.name = AddName.text.toString()
                     activity?.toast("You have entered a meal into the application!")
                 } else {

                         if (layout.AddName.text.isEmpty() || AddDescription.text.isEmpty() || calories.text.isEmpty()) {
                             activity?.toast("There are some fields you have not entered")
                         } else {
                             activity?.toast("Thank you!")
                         }
                 }
         }
     }

                     override fun onCreateView(
                         inflater: LayoutInflater, container: ViewGroup?,
                         savedInstanceState: Bundle?
                     ): View? {
                         // Inflate the layout for this fragment
                         return inflater.inflate(R.layout.fragment_home, container, false)
                     }
                 }





