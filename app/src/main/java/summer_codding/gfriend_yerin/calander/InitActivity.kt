package summer_codding.gfriend_yerin.calander

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class InitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({
            // TODO : Init Database
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 1300)
    }
}
