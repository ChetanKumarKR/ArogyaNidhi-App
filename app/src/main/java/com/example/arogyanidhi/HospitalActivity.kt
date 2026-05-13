package com.example.arogyanidhi

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.LocationServices
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.Locale

class HospitalActivity : AppCompatActivity() {

    // FIREBASE

    private lateinit var database: DatabaseReference

    // LOCATION

    private lateinit var fusedLocationClient:
            com.google.android.gms.location.FusedLocationProviderClient

    // HOSPITAL LIST

    private var fullList: List<Hospital> = listOf()

    // DOUBLE BACK

    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_hospital)

        // FIREBASE DATABASE

        database =
            FirebaseDatabase.getInstance()
                .getReference("hospitals")

        // LOCATION CLIENT

        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(this)

        // VIEWS

        val recyclerView =
            findViewById<RecyclerView>(R.id.recyclerView)

        val searchBox =
            findViewById<EditText>(R.id.searchEditText)

        val spinner =
            findViewById<AutoCompleteTextView>(
                R.id.searchDistrictEditText
            )

        val logoutBtn =
            findViewById<MaterialButton>(R.id.logoutBtn)

        val profileBtn =
            findViewById<MaterialButton>(R.id.profileBtn)

        val addHospitalBtn =
            findViewById<MaterialButton>(R.id.addHospitalBtn)

        val emptyText =
            findViewById<TextView>(R.id.emptyText)

        val progressBar =
            findViewById<ProgressBar>(
                R.id.hospitalProgressBar
            )

        val locationBtn =
            findViewById<MaterialButton>(
                R.id.locationBtn
            )

        // RECYCLERVIEW

        recyclerView.layoutManager =
            LinearLayoutManager(this)

        // DISTRICTS

        val districts = arrayOf(
            "Bagalkot",
            "Ballari",
            "Bangalore Rural",
            "Bangalore Urban",
            "Belagavi",
            "Bidar",
            "Chamarajanagar",
            "Chikballapur",
            "Chikkamagaluru",
            "Chitradurga",
            "Dakshina Kannada",
            "Davanagere",
            "Dharwad",
            "Gadag",
            "Hassan",
            "Haveri",
            "Kalaburagi",
            "Kodagu",
            "Kolar",
            "Koppal",
            "Mandya",
            "Mysuru",
            "Raichur",
            "Ramanagara",
            "Shivamogga",
            "Tumakuru",
            "Udupi",
            "Uttara Kannada",
            "Vijayanagara",
            "Vijayapura"
        )

        val adapterDistrict =
            android.widget.ArrayAdapter(
                this,
                android.R.layout.simple_dropdown_item_1line,
                districts
            )

        spinner.setAdapter(adapterDistrict)

        // LOCATION BUTTON

        locationBtn.setOnClickListener {

            getCurrentLocation()
        }

        // DISTRICT SELECT

        spinner.setOnItemClickListener { _, _, _, _ ->

            val district = spinner.text.toString()

            // HIDE KEYBOARD

            val imm = getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager

            imm.hideSoftInputFromWindow(
                spinner.windowToken,
                0
            )

            // LOAD HOSPITALS

            loadHospitalsFromFirebase(
                district,
                searchBox,
                emptyText,
                progressBar
            )
        }

        // SEARCH FILTER

        searchBox.addTextChangedListener(

            object : android.text.TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {

                    val filteredList =
                        fullList.filter {

                            it.name.contains(
                                s.toString(),
                                ignoreCase = true
                            )
                        }

                    recyclerView.adapter =
                        HospitalAdapter(filteredList)
                }

                override fun afterTextChanged(
                    s: android.text.Editable?
                ) {
                }
            }
        )

        // ADMIN BUTTON

        val userEmail =
            FirebaseAuth.getInstance()
                .currentUser?.email

        if (userEmail == "admin@gmail.com") {

            addHospitalBtn.visibility =
                android.view.View.VISIBLE
        }

        // ADD HOSPITAL

        addHospitalBtn.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    AddHospitalActivity::class.java
                )
            )
        }

        // PROFILE

        profileBtn.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ProfileActivity::class.java
                )
            )
        }

        // LOGOUT

        logoutBtn.setOnClickListener {

            FirebaseAuth.getInstance().signOut()

            startActivity(
                Intent(
                    this,
                    MainActivity::class.java
                )
            )

            finish()
        }
    }

    // LOAD HOSPITALS

    private fun loadHospitalsFromFirebase(
        district: String,
        searchBox: EditText,
        emptyText: TextView,
        progressBar: ProgressBar
    ) {

        progressBar.visibility =
            android.view.View.VISIBLE

        database.addValueEventListener(

            object : ValueEventListener {

                override fun onDataChange(
                    snapshot: DataSnapshot
                ) {

                    val hospitalList =
                        mutableListOf<Hospital>()

                    // DISTRICT NODE

                    val districtSnapshot =
                        snapshot.child(district)

                    // READ HOSPITALS

                    for (hospitalSnapshot in districtSnapshot.children) {

                        val hospital =
                            hospitalSnapshot.getValue(
                                Hospital::class.java
                            )

                        if (hospital != null) {

                            hospitalList.add(hospital)
                        }
                    }

                    fullList = hospitalList

                    val recyclerView =
                        findViewById<RecyclerView>(
                            R.id.recyclerView
                        )

                    recyclerView.adapter =
                        HospitalAdapter(hospitalList)

                    progressBar.visibility =
                        android.view.View.GONE

                    // EMPTY MESSAGE

                    if (hospitalList.isEmpty()) {

                        emptyText.visibility =
                            android.view.View.VISIBLE

                    } else {

                        emptyText.visibility =
                            android.view.View.GONE
                    }
                }

                override fun onCancelled(
                    error: DatabaseError
                ) {

                    progressBar.visibility =
                        android.view.View.GONE

                    Toast.makeText(
                        this@HospitalActivity,
                        "Failed to load hospitals",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }

    // GPS LOCATION

    private fun getCurrentLocation() {

        // CHECK PERMISSION

        if (
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                100
            )

            return
        }

        // GET LOCATION

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->

                if (location != null) {

                    val geocoder =
                        Geocoder(this, Locale.getDefault())

                    val addresses =
                        geocoder.getFromLocation(
                            location.latitude,
                            location.longitude,
                            1
                        )

                    if (!addresses.isNullOrEmpty()) {

                        var district =
                            addresses[0].adminArea ?: ""

                        // DISTRICT MATCHING

                        district = when {

                            district.contains(
                                "shivamogga",
                                ignoreCase = true
                            ) -> "Shivamogga"

                            district.contains(
                                "mysuru",
                                ignoreCase = true
                            ) -> "Mysuru"

                            district.contains(
                                "tumakuru",
                                ignoreCase = true
                            ) -> "Tumakuru"

                            district.contains(
                                "davanagere",
                                ignoreCase = true
                            ) -> "Davanagere"

                            district.contains(
                                "hassan",
                                ignoreCase = true
                            ) -> "Hassan"

                            else -> "Shivamogga"
                        }

                        val spinner =
                            findViewById<AutoCompleteTextView>(
                                R.id.searchDistrictEditText
                            )

                        spinner.setText(district)

                        // AUTO LOAD HOSPITALS

                        loadHospitalsFromFirebase(
                            district,
                            findViewById(R.id.searchEditText),
                            findViewById(R.id.emptyText),
                            findViewById(R.id.hospitalProgressBar)
                        )

                        Toast.makeText(
                            this,
                            "Location detected: $district",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {

                    Toast.makeText(
                        this,
                        "Unable to detect location",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    // DOUBLE BACK TO EXIT

    @Suppress("DEPRECATION")

    override fun onBackPressed() {

        if (
            backPressedTime + 2000 >
            System.currentTimeMillis()
        ) {

            super.onBackPressed()

            finish()

        } else {

            Toast.makeText(
                this,
                "Press back again to exit",
                Toast.LENGTH_SHORT
            ).show()
        }

        backPressedTime =
            System.currentTimeMillis()
    }
}