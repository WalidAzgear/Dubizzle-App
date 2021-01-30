package com.test.dubizzleapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.test.dubizzleapp.R
import com.test.dubizzleapp.model.DataItem
import java.text.SimpleDateFormat

/**
 * Details Activity on the App, show the details of the selected item and show the name and price and the created date
 */
class DetailsActivity : AppCompatActivity() {

    private lateinit var item: DataItem
    private lateinit var itemName: TextView
    private lateinit var itemPrice: TextView
    private lateinit var itemDate: TextView
    private lateinit var itemImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        item = intent.getParcelableExtra(ITEM_DETAILS_INTENT_KEY)!!
        prepareView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("SimpleDateFormat")
    private fun prepareView() {
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        itemName = findViewById(R.id.item_name_txt_view)
        itemPrice = findViewById(R.id.item_price_txt_view)
        itemDate = findViewById(R.id.item_date_txt_view)
        itemImage = findViewById(R.id.item_image)

        itemName.text = item.name
        itemPrice.text = item.price
        val parser = SimpleDateFormat(ITEM_DATE_PARSER_FORMAT)
        val formatter = SimpleDateFormat(ITEM_DATE_FORMATTER_FORMAT)
        val formattedDate = formatter.format(parser.parse(item.createdAt))
        itemDate.text = formattedDate

        val url = item.imageUrls[0]
        Glide.with(this)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.default_fallback_image)
            .into(itemImage)
    }

    companion object {
        const val ITEM_DETAILS_INTENT_KEY = "ITEM_DETAILS_INTENT_KEY"
        const val ITEM_DATE_PARSER_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS"
        const val ITEM_DATE_FORMATTER_FORMAT = "yyyy-MM-dd"
    }
}