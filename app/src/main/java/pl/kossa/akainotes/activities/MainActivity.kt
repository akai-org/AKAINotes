package pl.kossa.akainotes.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import pl.kossa.akainotes.R
import pl.kossa.akainotes.adapters.NotesRvAdapter
import pl.kossa.akainotes.data.Note

class MainActivity : AppCompatActivity(R.layout.activity_main)