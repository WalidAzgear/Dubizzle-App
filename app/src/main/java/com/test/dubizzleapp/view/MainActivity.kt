package com.test.dubizzleapp.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.dubizzleapp.R
import com.test.dubizzleapp.adapter.ListDataAdapter
import com.test.dubizzleapp.model.DataItem
import com.test.dubizzleapp.repository.Resource
import com.test.dubizzleapp.viewmodel.ListDataViewModel

/**
 * Main Activity on the App, then an API call to get list of items from the server and then set them on list
 * when the user click on item the app will navigate him to details screen.
 */
class MainActivity : AppCompatActivity(), DataItemClickListener {

    lateinit var listDataViewModel: ListDataViewModel
    private lateinit var listDataAdapter: ListDataAdapter
    lateinit var listData: RecyclerView
    lateinit var progressCircular: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressCircular = findViewById(R.id.circular_progress_bar)
        listData = findViewById(R.id.data_list_recycler_view)
        listDataViewModel = ViewModelProvider(this).get(ListDataViewModel::class.java)
        getData()
    }

    private fun getData() {
        listDataViewModel.getData()!!.observe(this, Observer { response ->
            progressCircular.visibility = View.GONE

            when (response.status) {
                Resource.Status.SUCCESS -> {
                    val results = response.data!!.results
                    setData(results)
                }
                Resource.Status.ERROR -> showDialog()
            }
        })
    }

    private fun setData(results: List<DataItem>) {
        listData.visibility = View.VISIBLE
        listDataAdapter = ListDataAdapter(results, this)
        val mLayoutManager = LinearLayoutManager(applicationContext)
        mLayoutManager.orientation = LinearLayoutManager.VERTICAL
        listData.layoutManager = mLayoutManager
        listData.itemAnimator = DefaultItemAnimator()
        listData.adapter = listDataAdapter
    }

    private fun showDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(getString(R.string.dialog_error_message))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.dialog_yes_label)) { _, _ -> getData() }
            .setNegativeButton(getString(R.string.dialog_no_label)) { dialog, _ -> dialog.cancel() }

        val alert = dialogBuilder.create()
        alert.setTitle(getString(R.string.dialog_title))
        alert.show()
    }

    override fun onItemClickListener(item: DataItem) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(ITEM_DETAILS_INTENT_KEY, item)
        startActivity(intent)
    }

    companion object {
        const val ITEM_DETAILS_INTENT_KEY = "ITEM_DETAILS_INTENT_KEY"
    }
}

interface DataItemClickListener {
    fun onItemClickListener(item: DataItem)
}