package summer_codding.gfriend_yerin.calander.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import summer_codding.gfriend_yerin.calander.Data.ScheduleDatabase

class InitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({

            // Init Database
            ScheduleDatabase.getInstance(this)

            // TODO : Init Database
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1300)
    }
}
