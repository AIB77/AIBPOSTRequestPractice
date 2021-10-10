package ran.am.AIBPOSTRequestPractice

import AIBPOSTRequestPractice.R
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewUser : AppCompatActivity()
{
    lateinit var UserName:EditText
    lateinit var UserLocation:EditText
    lateinit var SaveBTN:Button
    lateinit var ViewBTN:Button


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

        UserName = findViewById(R.id.editTextName)
        UserLocation = findViewById(R.id.editTextLocation)
        SaveBTN = findViewById(R.id.buttonSave)
        ViewBTN=findViewById(R.id.buttonView)



        SaveBTN.setOnClickListener{

           if(UserLocation.text.isNotBlank()&&UserName.text.isNotBlank())
           {



               var addinguser = Users.UserDetails(UserName.text.toString(), UserLocation.text.toString())

               addSingleuser(addinguser, onResult =
               {
                   UserName.setText("")
                   UserLocation.setText("")
                   Toast.makeText(applicationContext, "The User Save Success!", Toast.LENGTH_SHORT).show();
               })

           }
            else
           {
               Toast.makeText(applicationContext, "Enter User Information", Toast.LENGTH_LONG).show()
           }

        }
    }

    private fun addSingleuser(addinguser: Users.UserDetails, onResult: () -> Unit)
    {

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val progressDialog = ProgressDialog(this@NewUser)
        progressDialog.setMessage("Please wait")
        progressDialog.show()


        if (apiInterface != null)
        {
            apiInterface.addUser(addinguser).enqueue(object : Callback<Users.UserDetails>
            {
                override fun onResponse(
                    call: Call<Users.UserDetails>,
                    response: Response<Users.UserDetails>
                ) {

                    onResult()
                    progressDialog.dismiss()
                }

                override fun onFailure(call: Call<Users.UserDetails>, t: Throwable)
                {
                    onResult()
                    Toast.makeText(applicationContext, "Error!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss()

                }
            })
        }
    }

    fun addnew(view: android.view.View)
    {
        intent = Intent(applicationContext, NewUser::class.java)
        startActivity(intent)
    }

    fun viewusers(view: android.view.View)
    {
        intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}