package com.example.myapplication.activity

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.service.dto.BookingDto
import com.example.myapplication.service.dto.ChoiceDto
import com.example.myapplication.service.dto.SearchRoomDto
import com.example.myapplication.service.dto.UserDto
import com.example.myapplication.user.BookRoomViewModel
import com.example.myapplication.user.adapter.RoomAdapter
import com.example.myapplication.utilities.BookRoomInjectorUtils
import com.example.myapplication.utilities.convertDateAndTimeToTimestamp
import com.example.myapplication.utilities.formatDayFr
import com.example.myapplication.utilities.formatTime
import java.util.*

class BookRoomActivity : AppCompatActivity() {

    private val tag = "CONSOLE"
    val context: Context = this@BookRoomActivity

    private lateinit var nameView: TextView
    private lateinit var usernameView: TextView
    private lateinit var emailView: TextView

    private lateinit var previewSelectedStartTime: TextView
    private lateinit var buttonPickStartTime: Button

    private lateinit var previewSelectEndTime: TextView
    private lateinit var buttonPickEndTime: Button

    private lateinit var previewSelectedDate: TextView
    private lateinit var buttonPickDateStart: Button

    private lateinit var homeButton: Button
    private lateinit var emailText: TextView

    private lateinit var searchRoomButton: Button

    // choice
    private lateinit var choiceTime: TextView
    private lateinit var choiceRoom: TextView
    private lateinit var choiceDay: TextView
    private lateinit var buttonBackChoice: Button
    private lateinit var choiceValide: Button

    //recycler view
    private lateinit var adapter: RoomAdapter
    private lateinit var recyclerView: RecyclerView

    // viewFlipper
    private lateinit var viewFlipper: ViewFlipper

    //Booking
    private var booking: BookingDto = BookingDto()

    private val viewModel by viewModels<BookRoomViewModel> {
        BookRoomInjectorUtils.provideBookRoomViewModelFactory(context)
    }

    lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val LIMIT_MINUTE = 10
        private const val HOUR_OF_DAY = 24
    }

    private val startTimePickerDialogListener: TimePickerDialog.OnTimeSetListener =
        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            val formattedTime: String = formatTime("$hourOfDay:$minute")
            previewSelectedStartTime.text = formattedTime
        }

    private val endTimePickerDialogListener: TimePickerDialog.OnTimeSetListener =
        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            val formattedTime: String = formatTime("$hourOfDay:$minute")
            previewSelectEndTime.text = formattedTime
        }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.book_room)

        sharedPreferences = getSharedPreferences("PREFS_KEY", Context.MODE_PRIVATE)
        val email: String? = sharedPreferences.getString("email", "")

        if (email != null && email != "") {
            onObserve(email)
            setupUI()
        } else {
            val intent = Intent(context, MainActivity::class.java)
            val editor = sharedPreferences.edit()
            editor.remove("email")
            editor.apply()
            context.startActivity(intent)
        }
    }

    private fun onObserve(email: String) {
        viewModel.loadUser(email)
        viewModel.user.observe(this) { user -> showUser(user) }
    }

    private fun setupUI() {
        previewSelectedStartTime = findViewById(R.id.pick_time_start_textView)
        previewSelectEndTime = findViewById(R.id.pick_time_end_textView)
        previewSelectedDate = findViewById(R.id.pick_date_start_textView)

        viewFlipper = findViewById(R.id.idViewFlipper)

        val mImagePickTime = getDrawable(R.drawable.ic_twotone_calendar_today_24)
        buttonPickStartTime = findViewById(R.id.pick_time_start)
        buttonPickStartTime.setCompoundDrawablesWithIntrinsicBounds(
            mImagePickTime,
            null,
            null,
            null
        )
        buttonPickStartTime.setOnClickListener {
            val timePicker: TimePickerDialog = TimePickerDialog(
                this,
                startTimePickerDialogListener,
                HOUR_OF_DAY,
                LIMIT_MINUTE,
                false
            )
            timePicker.show()
        }
        buttonPickEndTime = findViewById(R.id.pick_time_end)
        buttonPickEndTime.setCompoundDrawablesWithIntrinsicBounds(mImagePickTime, null, null, null)
        buttonPickEndTime.setOnClickListener {
            val timePicker: TimePickerDialog = TimePickerDialog(
                this,
                endTimePickerDialogListener,
                HOUR_OF_DAY,
                LIMIT_MINUTE,
                false
            )
            timePicker.show()
        }

        buttonPickDateStart = findViewById(R.id.pick_date_start)
        val mImagePickDate = getDrawable(R.drawable.ic_twotone_calendar_month_24)
        buttonPickDateStart.setCompoundDrawablesWithIntrinsicBounds(
            mImagePickDate,
            null,
            null,
            null
        )
        buttonPickDateStart.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    previewSelectedDate.text =
                        formatDayFr(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        emailText = findViewById(R.id.email)
        homeButton = findViewById(R.id.home)
        homeButton.setOnClickListener {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }

        searchRoomButton = findViewById(R.id.search_room)
        searchRoomButton.setOnClickListener {
            viewModel.searchRoom(
                previewSelectedDate.text.toString(),
                previewSelectedStartTime.text.toString(),
                previewSelectEndTime.text.toString()
            )
        }

        recyclerView = findViewById(R.id.recycler_view_room)

        buttonBackChoice = findViewById(R.id.button_come_back)
        buttonBackChoice.setOnClickListener {
            viewFlipper.showPrevious()
        }
        val listener = object : RoomAdapter.OnItemClickListener {
            override fun onItemClick(room: SearchRoomDto) {

                onObserverChoice(
                    ChoiceDto(
                        room.nameRoom,
                        previewSelectedStartTime.text.toString(),
                        previewSelectEndTime.text.toString(),
                        previewSelectedDate.text.toString(),
                        room.idRoom
                    )
                )

                viewFlipper.showNext()
            }
        }

        choiceValide = findViewById(R.id.valide_choice)
        choiceValide.setOnClickListener {
            val email = sharedPreferences.getString("email", "")
            createBooking()
        }

        val mImagePickRoom = getDrawable(R.drawable.ic_sharp_open_in_new_24)!!
        adapter = RoomAdapter(viewModel.rooms.value ?: emptyList(), this, listener, mImagePickRoom)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        viewModel.rooms.observe(this, renderSearchRoomDto)


    }

    private fun createBooking() {
        viewModel.createBooking(booking)
        val intent = Intent(context, HomeActivity::class.java)
        context.startActivity(intent)
    }

    //observers
    private val renderSearchRoomDto = Observer<List<SearchRoomDto>> {
        adapter.update(it)
    }

    private fun onObserverChoice(choice: ChoiceDto) {
        viewModel.loadChoice(choice)
        viewModel.choice.observe(this) { choice -> showChoice(choice) }
    }

    @SuppressLint("SetTextI18n")
    private fun showUser(user: UserDto) {
        nameView = findViewById(R.id.name)
        usernameView = findViewById(R.id.username)
        emailView = findViewById(R.id.email)

        nameView.text = user.firstName
        usernameView.text = user.lastName
        emailView.text = user.email

        booking.email = user.email
        booking.firstName = user.firstName
        booking.lastName = user.lastName
        booking.idUser = user.idUser
    }

    @SuppressLint("SetTextI18n")
    private fun showChoice(choiceDto: ChoiceDto) {
        choiceTime = findViewById(R.id.choice_time)
        choiceDay = findViewById(R.id.choice_day)
        choiceRoom = findViewById(R.id.choice_room)

        booking.endTime = convertDateAndTimeToTimestamp(choiceDto.day, choiceDto.end)
        booking.startTime = convertDateAndTimeToTimestamp(choiceDto.day, choiceDto.start)
        booking.roomName = choiceDto.nameRoom
        booking.idRoom = choiceDto.idRoom

        choiceTime.text = "Horaire -> " + formatTime(choiceDto.start) + " - " + formatTime(choiceDto.end)
        choiceDay.text = "Jour ->  " + formatDayFr(choiceDto.day)
        choiceRoom.text = "Salle sélectionnée -> " + choiceDto.nameRoom



    }
}