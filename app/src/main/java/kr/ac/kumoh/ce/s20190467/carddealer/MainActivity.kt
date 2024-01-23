package kr.ac.kumoh.ce.s20190467.carddealer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.ac.kumoh.ce.s20190467.carddealer.databinding.ActivityMainBinding
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {
    private lateinit var main: ActivityMainBinding
    private lateinit var model: CardDealerViewModel
    private val res = IntArray(5)
    fun updateRankText(rankResult: String) {
        main.rank?.text = rankResult
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        main= ActivityMainBinding.inflate(layoutInflater)
        setContentView(main.root)

        model = ViewModelProvider(this)[CardDealerViewModel::class.java]


        model.cards.observe(this, Observer{cards ->
            if (cards.none { it == -1 }) { //첫 실행화면 메시지x
                val rankResult = model.rank(cards)
                updateRankText(rankResult)
            } else {
                updateRankText("")
            }

        for (i in cards.indices){
                res[i] = resources.getIdentifier(
                    getCardName(cards[i]),
                    "drawable",
                    packageName
                )
                //Log.d("CardValues", "cards[$i] = ${cards[i]}")
        }
            main.card1.setImageResource(res[0])
            main.card2.setImageResource(res[1])
            main.card3.setImageResource(res[2])
            main.card4.setImageResource(res[3])
            main.card5.setImageResource(res[4])
        })

        main.btnShuffle.setOnClickListener{
            model.shuffle()
        }
    }

    fun getCardName(c: Int) : String {
        //val에서 var로 변경

        var shape = when (c / 13) {
            0 -> "spades"
            1 -> "diamonds"
            2 -> "hearts"
            3 -> "clubs"
            else -> "error"
        }

        val number = when (c % 13) {
            0 -> "ace"
            in 1..9 -> (c % 13 + 1).toString()
            10->{
                shape = shape.plus("2")
                "jack"
            }
            11->{
                shape = shape.plus("2")
                "queen"
            }
            12->{
                shape = shape.plus("2")
                "king"
            }
            else-> "error"


        }

        return "c_${number}_of_${shape}"


    }
}
