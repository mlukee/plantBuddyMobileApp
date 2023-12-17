package com.example.plantbuddy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.plantbuddy.databinding.ActivityStoreDetailBinding
import org.json.JSONObject

class StoreDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoreDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStoreDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra("store_details")) {
            val storeDetails = intent.getStringExtra("store_details")?.let { JSONObject(it) }

            // Assuming you have a field "image_name" in your JSON which corresponds to drawable names
            storeDetails?.let {
                val imageName = it.optString("imgSrc")
                val resourceId = resources.getIdentifier(imageName, "drawable", packageName)
                if (resourceId != 0) { // Resource ID will be 0 if not found
                    binding.storeImg.setImageResource(resourceId)
                }

                binding.storeAddress.text = it.optString("address")
                binding.storeName.text = it.optString("name")
                binding.storeWorkingHours.text = it.optString("working_hours")
                binding.storeContact.text = it.optString("contact")
            }
        }
    }
}
