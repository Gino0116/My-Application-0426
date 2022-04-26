package tw.edu.pu.gm.myapplication_0426

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.cancel
import tw.edu.pu.gm.myapplication_0426.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var secondsLeft:Int = 1000  //倒數
    lateinit var job: Job
    override fun onCreate(savedInstanceState: Bundle?) {
        //lateinit var job: Job
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.txv.text = secondsLeft.toString()
        binding.btnstart.isEnabled = true
        binding.btnstop.isEnabled = false
        binding.btnstart.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                binding.txv.text="開始倒數計時"
                job = GlobalScope.launch(Dispatchers.Main) {
                    while(secondsLeft > 0) {
                        secondsLeft--
                        binding.txv.text = secondsLeft.toString()
                        binding.btnstart.isEnabled = false
                        binding.btnstop.isEnabled = true
                        delay(25)
                    }
                    secondsLeft=100
                    binding.btnstart.isEnabled = true
                    binding.btnstop.isEnabled = false
                }
            }
        })
        binding.btnstop.setOnClickListener(object :View.OnClickListener{
           override fun onClick(p0:View?){
               binding.txv.text="開始倒數計時"
               job.cancel()
               binding.btnstart.isEnabled = true
               binding.btnstop.isEnabled = false
            }
        })
    }

    override fun onPause() {
        super.onPause()
        job.cancel()
    }

    override fun onResume() {
        super.onResume()
        if (binding.btnstart.isEnabled == false){
            job = GlobalScope.launch(Dispatchers.Main) {
                while(secondsLeft > 0) {
                    secondsLeft--
                    binding.txv.text = secondsLeft.toString()
                    delay(25)
                }
            }
        }
    }

}