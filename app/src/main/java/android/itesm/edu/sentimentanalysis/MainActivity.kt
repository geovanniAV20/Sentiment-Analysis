package android.itesm.edu.sentimentanalysis

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.sql.SQLOutput
import java.util.*
import com.ibm.watson.*


class MainActivity : AppCompatActivity(),TextToSpeech.OnInitListener {


    private var tts: TextToSpeech? = null
    private var buttonSpeak: Button? = null
    private var editText: EditText? = null
    private var buttonSentiment: Button? = null
    var textToAnalyze: String? = null
    var sentimentType: String? = null
    var menu: String? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonSpeak = this.button_speak
        buttonSentiment = this.button_sentiment
        editText = this.edittext_input

        buttonSpeak!!.isEnabled = false;
        tts = TextToSpeech(this, this)

        buttonSpeak!!.setOnClickListener { speakOut("Escribe algo") }

        buttonSentiment!!.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                textToAnalyze = edittext_input.text.toString()
                sentimentA(textToAnalyze!!)
            }
        })
        }



    override fun onInit(status: Int) {

        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val l = Locale("spa", "MEX")
            val result = tts!!.setLanguage(l)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            } else {
                buttonSpeak!!.isEnabled = true
            }

        } else {
            Log.e("TTS", "Initilization Failed!")
        }

    }

    private fun speakOut(sayThis: String) {
        //val text = editText!!.text.toString()
        tts!!.speak(sayThis, TextToSpeech.QUEUE_FLUSH, null,"")
    }

    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

    fun sentimentA(string: String){
        when {
            string.contains("feliz",true) -> sentimentType = "Positive"
            string.contains("alegre",true) -> sentimentType = "Positive"
            string.contains("bien",true) -> sentimentType = "Positive"
            string.contains("contento",true) -> sentimentType = "Positive"
            string.contains("emocionado",true) -> sentimentType = "Positive"
            string.contains("sorpresa",true) -> sentimentType = "Positive"
            string.contains("omg",true) -> sentimentType = "Positive"
            string.contains("inesperado",true) -> sentimentType = "Positive"
            string.contains("regalo",true) -> sentimentType = "Positive"
            string.contains("fiesta",true) -> sentimentType = "Positive"

            string.contains("enojado",true) -> sentimentType = "Neutral"
            string.contains("enojo",true) -> sentimentType = "Neutral"
            string.contains("ira",true) -> sentimentType = "Neutral"
            string.contains("molesto",true) -> sentimentType = "Neutral"
            string.contains("rabia",true) -> sentimentType = "Neutral"

            string.contains("disgusto",true) -> sentimentType = "Neutral"
            string.contains("asco",true) -> sentimentType = "Neutral"
            string.contains("cae mal",true) -> sentimentType = "Neutral"
            string.contains("iugh",true) -> sentimentType = "Neutral"
            string.contains("repugnante",true) -> sentimentType = "Neutral"


            string.contains("miedo",true) -> sentimentType = "Negative"
            string.contains("suspenso",true) -> sentimentType = "Negative"
            string.contains("pálido",true) -> sentimentType = "Negative"
            string.contains("tenebroso",true) -> sentimentType = "Negative"
            string.contains("susto",true) -> sentimentType = "Negative"
            string.contains("triste",true) -> sentimentType = "Negative"
            string.contains("lágrimas",true) -> sentimentType = "Negative"
            string.contains("depre",true) -> sentimentType = "Negative"
            string.contains("mal",true) -> sentimentType = "Negative"
            string.contains("morir",true) -> sentimentType = "Negative"
            else -> sentimentType = "Neutral"
        }
        //textView!!.text = sentimentType
        getMenu(sentimentType!!)
    }

    fun getMenu(sentiment: String)
    {
        when{
            sentiment.equals("Positive") -> menu = "Tómate una Coca cola"
            sentiment.equals("Neutral") -> menu = "Preparate una sopa de tortilla"
            sentiment.equals("Negative") -> menu = "Come unas enchiladas"
        }

        textView!!.text = menu
        speakOut(this!!.menu!!)
    }

}




