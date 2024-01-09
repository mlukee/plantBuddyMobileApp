package com.example.plantbuddy

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.lib.Plant
import com.example.plantbuddy.databinding.ActivityMainBinding
import com.example.plantbuddy.fragments.add.ARGUMENTS
import com.example.plantbuddy.fragments.add.InputFragment
import com.example.plantbuddy.fragments.list.PlantFragment
import com.example.plantbuddy.viewmodel.PlantViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var plantFragment: PlantFragment? = null
    private var mPlantViewModel: PlantViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mPlantViewModel = ViewModelProvider(this)[PlantViewModel::class.java]

        if(mPlantViewModel?.readAllData?.value?.size == 0) {
            binding.cardViewAddFirstPlant.visibility = View.VISIBLE
            binding.buttonAdd.visibility = View.VISIBLE
        } else {
            binding.cardViewAddFirstPlant.visibility = View.GONE
            binding.buttonAdd.visibility = View.GONE
        }

        createNotificationChannel()



        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, PlantFragment())
            .commit()

        binding.buttonAdd.setOnClickListener {
            hideFirstPlantCardView()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, InputFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.fabAdd.setOnClickListener {
            hideFirstPlantCardView()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, InputFragment())
                .addToBackStack(null)
                .commit()
        }

        supportFragmentManager.setFragmentResultListener(
            "addRequestKey",
            this
        ) { requestKey, bundle ->
            val name = bundle.getString(ARGUMENTS.PLANT_NAME.key)
            val plantType = bundle.getString(ARGUMENTS.PLANT_TYPE.key)
            val wateringSchedule = bundle.getString(ARGUMENTS.PLANT_SCHEDULE.key)
            val lastWatering = bundle.getLong(ARGUMENTS.PLANT_LAST_WATERING.key)
            val plant = Plant(name!!, plantType!!, wateringSchedule!!, lastWatering)
            scheduleNotification(plant)
            hideFirstPlantCardView()
        }

        binding.fabHome.setOnClickListener {
            if (plantFragment == null) {
                plantFragment = PlantFragment()
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, plantFragment!!)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun scheduleNotification(plant: Plant) {
        try {
            val intent = Intent(applicationContext, Notification::class.java)
            intent.putExtra(titleExtra, "Watering Time!")
            intent.putExtra(messageExtra, "Your ${plant.name} - ${plant.plantType} needs watering!")

            val pendingIntent = PendingIntent.getBroadcast(
                applicationContext,
                notificationID,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                plant.lastWatering + 5_000,
                pendingIntent
            )
        }catch (e: SecurityException){
            Toast.makeText(this, "Please grant permission to set alarm", Toast.LENGTH_SHORT).show()
        }
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Watering Plants Channel"
            val descriptionText = "Channel for watering plants"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun hideFirstPlantCardView() {
        if(mPlantViewModel?.readAllData?.value?.size == 0) return
        binding.cardViewAddFirstPlant.visibility = View.GONE
        binding.buttonAdd.visibility = View.GONE
    }

    fun onFabMapsClick(view: View) {
        startActivity(Intent(this, MapsActivity::class.java));
    }
}