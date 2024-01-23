package kr.ac.kumoh.ce.s20190467.carddealer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.random.Random

class CardDealerViewModel : ViewModel() {
    private var _cards = MutableLiveData<IntArray>(IntArray(5){-1})
    val cards: LiveData<IntArray> get() = _cards

    private val _rankResult = MutableLiveData<String>()
    val rankResult: LiveData<String> get() = _rankResult

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

    fun rank(cards: IntArray): String { // 족보를 나타내는 문자열을 반환

        if (hasRoyalStraightFlush(cards)) {
            return "Royal Straight Flush"
        }
        if (hasBackStraightFlush(cards)) {
            return "Back Straight Flush"
        }
        if (hasStraightFlush(cards)) {
            return "Straight Flush"
        }
        if (hasFourCard(cards)) {
            return "Four Card"
        }
        if (hasFullHouse(cards)) {
            return "Full House"
        }
        if (hasFlush(cards)) {
            return "Flush"
        }
        if (hasMountain(cards)) {
            return "Mountain"
        }
        if (hasBackStraight(cards)) {
            return "Back Straight"
        }
        if (hasStraight(cards)) {
            return "Straight"
        }
        if (hasTriple(cards)) {
            return "Triple"
        }
        if (hasTwoPair(cards)) {
            return "Two Pairs"
        }
        if (hasOnePair(cards)) {
            return "One Pair"
        }

        //모두 해당하지 않을때
        val topCardNumber = hasTop(cards)
        return "Top Card: " + getCardName(topCardNumber)

    }

    private fun hasRoyalStraightFlush(cards: IntArray): Boolean { //무늬가 같은 A, K, Q, J, 10
        // 무늬가 모두 같은지 확인
        val suit = cards[0] / 13
        if (cards.any { it / 13 != suit }) {
            return false
        }

        // 필요한 카드의 숫자를 세트로 만듦
        val requiredNumbers = setOf(0, 9, 10, 11, 12)

        // 카드 숫자가 필요한 숫자에 포함되어 있는지 확인
        val cardNumbers = cards.map { it % 13 }.toSet()
        return cardNumbers == requiredNumbers
    }

    private fun hasBackStraightFlush(cards: IntArray): Boolean { //무늬가 같은 A, 2, 3, 4, 5
        // 모든 카드가 같은 무늬를 가지고 있는지 확인
        val suit = cards[0] / 13
        if (cards.any { it / 13 != suit }) {
            return false
        }

        // 백 스트레이트 플러시를 구성하는 카드 숫자들을 설정
        val backStraightNumbers = setOf(0, 1, 2, 3, 4) // A, 2, 3, 4, 5

        // 각 카드의 숫자가 위에서 정의한 숫자들 중 하나인지 확인
        val cardNumbers = cards.map { it % 13 }.toSet()
        return cardNumbers == backStraightNumbers
    }


    private fun hasStraightFlush(cards: IntArray): Boolean { //숫자가 이어지고 무늬가 같은 카드 5장
        // 모든 카드가 같은 무늬인지 확인
        val firstSuit = cards[0] / 13
        if (cards.any { it / 13 != firstSuit }) {
            return false
        }

        // 카드 값의 연속성을 확인
        val cardValues = cards.map { it % 13 }.sorted()

        // A-K-Q-J-10의 경우를 처리
        val highStraight = setOf(0, 9, 10, 11, 12)
        if (cardValues.toSet() == highStraight) {
            return true
        }

        // 일반적인 연속되는 숫자를 확인
        for (i in 0 until cardValues.size - 1) {
            if (cardValues[i + 1] != cardValues[i] + 1) {
                return false
            }
        }

        // 최대 숫자와 최소 숫자의 차이가 4인지 확인
        return cardValues[4] - cardValues[0] == 4
    }


    private fun hasFourCard(cards: IntArray): Boolean { //숫자가 같은 카드 4장
        val countsMap = mutableMapOf<Int, Int>()

        for (card in cards) {
            val cardNumber = card % 13  // 카드 숫자만을 추출
            countsMap[cardNumber] = countsMap.getOrDefault(cardNumber, 0) + 1
        }

        // countsMap에서 값이 4인 것이 하나 이상 있는지 확인
        return countsMap.any { it.value == 4 }
    }


    private fun hasFullHouse(cards: IntArray): Boolean { //숫자가 같은 카드 3장 + 숫자가 같은 카드 2장
        val countsMap = mutableMapOf<Int, Int>()

        // 카드 숫자별로 개수를 세는 맵을 만듦
        for (card in cards) {
            val cardNumber = card % 13
            countsMap[cardNumber] = countsMap.getOrDefault(cardNumber, 0) + 1
        }

        // 트리플과 원 페어가 있는지 확인
        var hasTriple = false
        var hasPair = false
        for ((_, count) in countsMap) {
            if (count == 3) {
                hasTriple = true
            } else if (count == 2) {
                hasPair = true
            }
        }

        // 트리플과 원 페어가 모두 있어야 풀 하우스
        return hasTriple && hasPair
    }

    private fun hasFlush(cards: IntArray): Boolean { //무늬가 같은 카드 5장
        if (cards.size < 5) {
            return false // 카드가 없는 경우
        }

        // 첫 번째 카드의 무늬가 기준
        val suit = cards[0] / 13

        // 모든 카드가 동일한 무늬를 가지고 있는지 확인
        return cards.all { it / 13 == suit }
    }


    private fun hasMountain(cards: IntArray): Boolean { //A, K, Q, J, 10
        val cardNumbers = cards.map { it % 13 }.sorted()

        // 마운틴을 구성하는 카드 숫자들을 정의
        val mountainNumbers = listOf(0, 9, 10, 11, 12) // A, 10, J, Q, K

        // 마운틴 숫자가 모두 존재하는지 확인
        return mountainNumbers.all { cardNumbers.contains(it) }
    }


    private fun hasBackStraight(cards: IntArray): Boolean { // A, 2, 3, 4, 5
        val cardNumbers = cards.map { it % 13 }.sorted()

        // 백 스트레이트를 구성하는 카드 숫자들을 정의
        val backStraightNumbers = listOf(0, 1, 2, 3, 4)

        // 백 스트레이트 숫자가 모두 존재하는지 확인
        return backStraightNumbers.all { cardNumbers.contains(it) }
    }


    private fun hasStraight(cards: IntArray): Boolean { // 숫자가 이어지는 카드 5장
        val sortedCardNumbers = cards.map { it % 13 }.sorted()

        // 에이스를 고려하여 추가 검사를 수행. 에이스는 0 또는 14로 취급 가능.
        val aceAsFourteen = sortedCardNumbers.map { if (it == 0) 14 else it }

        return hasConsecutiveFive(sortedCardNumbers) || hasConsecutiveFive(aceAsFourteen)
    }

    private fun hasConsecutiveFive(numbers: List<Int>): Boolean { //연속되는 숫자인지 판별
        var consecutiveCount = 1

        for (i in 0 until numbers.size - 1) {
            if (numbers[i] + 1 == numbers[i + 1]) {
                consecutiveCount++
                if (consecutiveCount == 5) return true
            } else if (numbers[i] != numbers[i + 1]) {
                consecutiveCount = 1
            }
        }

        return false
    }



    private fun hasTriple(cards: IntArray): Boolean { // 숫자가 같은 카드 3장
        val countsMap = mutableMapOf<Int, Int>()

        // 카드 숫자별로 개수를 세는 맵을 만듦
        for (card in cards) {
            val cardNumber = card % 13
            countsMap[cardNumber] = countsMap.getOrDefault(cardNumber, 0) + 1
        }

        // 카드 숫자 중 3개가 있는 것이 있는지 확인
        return countsMap.any { it.value == 3 }
    }


    private fun hasTwoPair(cards: IntArray): Boolean { // 원 페어가 2개 존재
        val countsMap = mutableMapOf<Int, Int>()

        for (card in cards) {
            val cardNumber = card % 13
            countsMap[cardNumber] = countsMap.getOrDefault(cardNumber, 0) + 1
        }

        return countsMap.count { it.value == 2 } == 2
    }

    private fun hasOnePair(cards: IntArray): Boolean {// 숫자가 같은 카드 2장
        val countsMap = mutableMapOf<Int, Int>()

        for (card in cards) {
            val cardNumber = card % 13
            countsMap[cardNumber] = countsMap.getOrDefault(cardNumber, 0) + 1
        }

        return countsMap.count { it.value == 2 } == 1
    }

    private fun hasTop(cards: IntArray): Int { // 숫자가 높은 카드 1장
        // 각 카드의 숫자 부분만 추출하고 정렬
        val cardNumbers = cards.map { it % 13 }.sortedDescending()

        // 가장 높은 숫자의 카드를 반환
        return cardNumbers.first()
    }


}