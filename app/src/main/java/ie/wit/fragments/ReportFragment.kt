package ie.wit.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import ie.wit.R
import ie.wit.adapters.TransferAdapter

import ie.wit.api.TransferWrapper
import ie.wit.main.TransferApp
import ie.wit.models.TransferModel
import ie.wit.utils.*
import kotlinx.android.synthetic.main.fragment_report.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportFragment : Fragment(), AnkoLogger, Callback<List<TransferModel>> {

    lateinit var app: TransferApp
    lateinit var loader : AlertDialog
    lateinit var root: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as TransferApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_report, container, false)
        activity?.title = getString(R.string.action_report)

        root.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        root.recyclerView.adapter = TransferAdapter(app.donations)
        loader = createLoader(activity!!)
        setSwipeRefresh()

        val swipeDeleteHandler = object : SwipeToDeleteCallback(activity!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = root.recyclerView.adapter as TransferAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                deleteMeal(viewHolder.itemView.tag as String)
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(root.recyclerView)

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ReportFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    fun setSwipeRefresh() {
        root.swiperefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                root.swiperefresh.isRefreshing = true
                getAllMeals()
            }
        })
    }

    fun checkSwipeRefresh() {
        if (root.swiperefresh.isRefreshing) root.swiperefresh.isRefreshing = false
    }

    override fun onFailure(call: Call<List<TransferModel>>, t: Throwable) {
        info("Retrofit Error : $t.message")
        serviceUnavailableMessage(activity!!)
        checkSwipeRefresh()
        hideLoader(loader)
    }

    override fun onResponse(call: Call<List<TransferModel>>,
                            response: Response<List<TransferModel>>
    ) {
        serviceAvailableMessage(activity!!)
        info("Retrofit JSON = ${response.body()}")
        app.donations = response.body() as ArrayList<TransferModel>
        root.recyclerView.adapter = TransferAdapter(app.donations)
        root.recyclerView.adapter?.notifyDataSetChanged()
        checkSwipeRefresh()
        hideLoader(loader)
    }

    fun getAllMeals() {
        showLoader(loader, "Loading")
        var callGetAll = app.donationService.getall()
        callGetAll.enqueue(this)
    }

    fun deleteMeal(id: String) {
        showLoader(loader, "Deleting Meal $id")
        var callDelete = app.donationService.delete(id)
        callDelete.enqueue(object : Callback<TransferWrapper> {
            override fun onFailure(call: Call<TransferWrapper>, t: Throwable) {
                info("Retrofit Error : $t.message")
                serviceUnavailableMessage(activity!!)
                hideLoader(loader)
            }

            override fun onResponse(
                call: Call<TransferWrapper>,
                response: Response<TransferWrapper>
            ) {
                //app.meals.remove(app.meals.find { p -> p._id == id })
                //root.recyclerView.adapter?.notifyDataSetChanged()
                hideLoader(loader)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        getAllMeals()
    }
}

