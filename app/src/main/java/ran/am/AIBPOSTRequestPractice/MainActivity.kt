package ran.am.AIBPOSTRequestPractice

import AIBPOSTRequestPractice.R
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity()
{

    lateinit var Thetext:TextView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        Thetext = findViewById(R.id.DatatextView)
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)


        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage("Please wait")
        progressDialog.show()


        if (apiInterface != null)
        {
            apiInterface.getUser()?.enqueue(object :Callback<List<Users.UserDetails>>
            {
                override fun onResponse(
                    call: Call<List<Users.UserDetails>>,
                    response: Response<List<Users.UserDetails>>)
                {
                    progressDialog.dismiss()
                    var stringToBePritined:String? = "";
                    for(User in response.body()!!)
                    {
                        stringToBePritined = stringToBePritined +User.name+ "\n"+User.location + "\n"+"\n"
                    }
                    Thetext.text= stringToBePritined
                }
                override fun onFailure(call: Call<List<Users.UserDetails>>, t: Throwable)
                {

                    progressDialog.dismiss()
                    Toast.makeText(applicationContext, ""+t.message, Toast.LENGTH_SHORT).show();
                }
            })
        }

    }

    fun addnew(view: android.view.View)
    {
        intent = Intent(applicationContext, NewUser::class.java)
        startActivity(intent)
    }
}