package kr.ac.kumoh.ce.s20190467.carddealer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class CardDealerViewModel : ViewModel() {
    private var _cards = MutableLiveData<IntArray>(IntArray(5){-1})
    val cards: LiveData<IntArray> get() = _cards
    fun shuffle(){
        var num = 0
        val newCards = IntArray(5){-1}

        for(i in newCards.indices){
            //중복검사
            do {
                num = Random.nextInt(52)
            }while (newCards.contains(num))
            newCards[i] = num

        }

        //정렬
        newCards.sort()
        _cards.value = newCards
    }



    fun rank(cards: IntArray): String {
        // 여기에 족보 판별 로직을 구현합니다.
        // cards 배열을 기반으로 족보를 결정하고 족보를 나타내는 문자열을 반환합니다.
        // 예를 들어, "로열 플러시" 또는 "원 페어"와 같은 문자열을 반환할 수 있습니다.
        // 필요에 따라 다양한 족보를 판별하는 로직을 구현하세요.

        // 족보를 나타내는 문자열을 반환합니다.

        if (hasRoyalStraightFlush(cards)) {
            return "Royal Straight Flush"
        }
        if (hasBackStraightFlush(cards)) {
            return "BackS traight Flush"
        }
        if (hasStraightFlush(cards)) {
            return "Straight Flush"
        }
        if (hasFourCard(cards)) {
            return "Four Card"
        }
//        if (hasFullHouse(cards)) {
//            return "Full House"
//        }
//        if (hasFlush(cards)) {
//            return "Flush"
//        }
//        if (hasMountain(cards)) {
//            return "Mountain"
//        }
//        if (hasBackStraight(cards)) {
//            return "Back Straight"
//        }
//        if (hasStraight(cards)) {
//            return "Straight"
//        }
//        if (hasTriple(cards)) {
//            return "Triple"
//        }
//        if (hasTwoPair(cards)) {
//            return "Two Pairs"
//        }
//        if (hasOnePair(cards)) {
//            return "One Pair"
//        }
//        if (hasTop(cards)) {
//            return "Flush"
//        }
//        else {
//            return "High Card" // 어떤 족보에도 해당하지 않을 때
//        }
        return "No Match"

    }

    private fun hasRoyalStraightFlush(cardCounts: IntArray): Boolean { //무늬가 같은 A, K, Q, J, 10
//        val shape = cardCounts[0] / 13
//        val allShape = cardCounts.all{ it / 13 == shape}
//
//        val number = cardCounts.map{ it % 13}.toSet()
//        val royalStraightFlush = setOf(0,9,10,11,12)
//        return true
        // A, K, Q, J, 10에 해당하는 카드 값
        val royalCardValues = intArrayOf(0, 9, 10, 11, 12)

        // A, K, Q, J, 10이 모두 존재하는지 확인
        for (cardValue in royalCardValues) {
            if (cardCounts[cardValue] == 0) {
                return false
            }
        }

        // 모든 카드가 같은 무늬인지 확인 (13으로 나눈 몫으로 무늬를 판별)
        val firstSuit = cardCounts[0] / 13
        for (cardValue in royalCardValues) {
            if (cardCounts[cardValue] / 13 != firstSuit) {
                return false
            }
        }

        return true
    }

    private fun hasBackStraightFlush(cardCounts: IntArray): Boolean { //무늬가 같은 A, 2, 3, 4, 5
        // A, 2, 3, 4, 5에 해당하는 카드 값
        val backStraightCardValues = intArrayOf(0, 1, 2, 3, 4)

        // A, 2, 3, 4, 5가 모두 존재하는지 확인
        for (cardValue in backStraightCardValues) {
            if (cardCounts[cardValue] == 0) {
                return false
            }
        }

        // 모든 카드가 같은 무늬인지 확인 (13으로 나눈 몫으로 무늬를 판별)
        val firstSuit = cardCounts[0] / 13
        for (cardValue in backStraightCardValues) {
            if (cardCounts[cardValue] / 13 != firstSuit) {
                return false
            }
        }

        return true
    }

    private fun hasStraightFlush(cardCounts: IntArray): Boolean { //숫자가 이어지고 무늬가 같은 카드 5장
        // 모든 카드가 같은 무늬인지 확인 (13으로 나눈 몫으로 무늬를 판별)
        val firstSuit = cardCounts[0] / 13
        for (i in 1 until cardCounts.size) {
            if (cardCounts[i] / 13 != firstSuit) {
                return false
            }
        }

        // 카드 값의 연속성을 확인
        val cardValues = cardCounts.map { it % 13 }.toIntArray()
        cardValues.sort()

        // 연속되는 숫자가 5개인지 확인
        for (i in 0 until cardValues.size - 1) {
            if (cardValues[i + 1] != cardValues[i] + 1) {
                return false
            }
        }

        // 최대 숫자와 최소 숫자의 차이가 4인지 확인
        return cardValues[4] - cardValues[0] == 4
    }

    private fun hasFourCard(cardCounts: IntArray): Boolean { //숫자가 같은 카드 4장
        val countsMap = mutableMapOf<Int, Int>()

        for (count in cardCounts) {
            countsMap[count] = countsMap.getOrDefault(count, 0) + 1
        }

        // countsMap에서 값이 4인 것이 하나 이상 있는지 확인
        return countsMap.any { it.value == 4 } //맞으면 t 아니면 f

    }
//
//    private fun hasFullHouse(cardCounts: IntArray): Boolean { //숫자가 같은 카드 3장 + 숫자가 같은 카드 2장
//
//    }
//
//    private fun hasFlush(cards: IntArray): Boolean { //무늬가 같은 카드 5장
//
//    }
//
//    private fun hasMountain(cards: IntArray): Boolean { //A, K, Q, J, 10
//
//    }
//
//    private fun hasBackStraight(cards: IntArray): Boolean { // A, 2, 3, 4, 5
//
//    }
//
//    private fun hasStraight(cards: IntArray): Boolean { // 숫자가 이어지는 카드 5장
//
//    }
//
//    private fun hasTriple(cards: IntArray): Boolean { // 숫자가 같은 카드 3장
//
//    }
//
//    private fun hasTwoPair(cards: IntArray): Boolean { // 원 페어가 2개 존재
//
//    }
//
//    private fun hasOnePair(cards: IntArray): Boolean { // 숫자가 같은 카드 2장
//
//    }
//
//    private fun hasTop(cards: IntArray): Boolean { // 숫자가 높은 카드 1장
//
//    }

}