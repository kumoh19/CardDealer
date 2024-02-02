## 1. 프로젝트 개요
본 프로젝트는 포커 족보 판별을 위한 안드로이드 앱을 개발한 과정과 결과를 기술한다. <br> 
앱은 조커를 제외한 52장의 playing cards 중에서 5장을 중복없이 랜덤하게 분배하고, 5장 카드의 포커 족보를 판별한다. <br>
또한, CardDealerViewModel을 사용하고 가로/세로 다른 레이아웃을 적용하였다. <br>
<br>

**개발 환경** <br>
•	개발 언어: Kotlin <br>
•	개발 프레임워크: Android Jetpack <br>
•	개발 도구: Android Studio <br>
<br>
**개발 과정**<br>
1.	CardDealerViewModel 구현<br>
CardDealerViewModel은 52장의 playing cards를 관리하고, 5장을 중복없이 랜덤하게 분배하는 역할을 한다. ViewModel은 데이터를 저장하고 관리하는 역할을 하며, View와 데이터를 분리하는 역할을 한다.<br>
2.	포커 족보 판별 알고리즘 구현<br>
동아시아 지역의 포커 족보는 다음과 같다.<br>
•	로열 스트레이트 플러시: 무늬가 같은 A, K, Q, J, 10. <br>
•	백 스트레이트 플러시: 무늬가 같은 A, 2, 3, 4, 5<br>
•	스트레이트 플러시: 숫자가 이어지고 무늬가 같은 카드 5장<br>
•	포카드: 같은 숫자 4장<br>
•	풀하우스: 같은 숫자 3장과 같은 숫자 2장. 즉, 원 페어 + 트리플 조합<br>
•	플러시: 같은 무늬의 5장<br>
•	마운틴: A, K, Q, J, 10<br>
•	백 스트레이트: A, 2, 3, 4, 5<br>
•	스트레이트: 연속된 숫자 5장<br>
•	트리플: 같은 숫자 3장<br>
•	투페어: 원 페어가 2개 존재<br>
•	원페어: 같은 숫자 2장<br>
•	탑: 가장 높은 숫자 카드<br>
<br>
3.	레이아웃 구현<br>
앱의 레이아웃은 가로/세로 두 가지 모드로 지원한다. 가로 모드에서는 녹색 바탕화면에 5장의 카드가 한 줄로 표시되고, 세로 모드에서는 노란 바탕화면에 5장의 카드가 한 줄로 표시된다.<br>

## 2.실행 화면 및 기능 설명
세로 화면<br>
![image](https://github.com/kumoh19/CardDealer/assets/104006988/ca7a5b4b-b53b-4f03-a6f6-6591c423f40e)
![image](https://github.com/kumoh19/CardDealer/assets/104006988/584fecca-2078-4808-bd12-65e543ab47bc)
![image](https://github.com/kumoh19/CardDealer/assets/104006988/4802da8e-23e0-418a-8000-394c857eb809)<br><br>
가로 화면<br>
![image](https://github.com/kumoh19/CardDealer/assets/104006988/8100eecb-2b42-42f9-828a-bfbb132b0125)<br>
![image](https://github.com/kumoh19/CardDealer/assets/104006988/8348ad99-c90c-4e3f-9478-eaaad75124fd)<br>
![image](https://github.com/kumoh19/CardDealer/assets/104006988/ffd6328f-09f7-4726-b415-a978a0671280)<br>


