# 유튜브 클론 (YouTube Clone)

<!-- 스크린샷 -->

## 목차
1. [소개](##소개)
2. [기능](##기능)
3. [사용 기술](##사용-기술)
4. [아키텍처](##아키텍처)
5. [설치](##설치)
6. [연락처](##연락처)

## 소개
유튜브 기능을 모방한 안드로이드 어플리케이션입니다.
<br>이 프로젝트는 디자인패턴, 앱아키텍처, 멀티모듈 구조 등 통합 기술을 포트폴리오로서 보여주기 위해 제작되었습니다.

## 기능
- 동영상 탐색 및 검색
- 웹뷰를 사용한 동영상 플레이어 재생
- short폼 동영상뷰 구현

## 사용 기술
- 프로그래밍 언어: Kotlin
- 프레임워크: Android SDK
- UI 디자인: Jetpack Compose, XML
- 네트워킹: Retrofit, OkHttp
- 동영상 재생: Iframe API, WebView
- 데이터베이스: Room, SharedPreference
- 이미지 로딩: Coil
- 반응형 프로그래밍: RxJava, Kotlin Flow
- 의존성 주입: Hilt
- Google 아키텍처 컴포넌트: ViewModel, LiveData, Navigation Component, Room, Databinding, Lifecycle

## 아키텍처
이 프로젝트는 Google이 권장하는 App Architecture, MVVM 아키텍처 패턴을 따릅니다.

### Google App Architecture
- 클린 아키텍처 패턴과 다른 ui -> domain -> data 데이터 스트림 형식의 아키텍처 패턴을 사용하였습니다.
- 앱 아키텍처 패턴을 통해 관심사의 분리, 테스트 용이성 및 유지보수성을 높였습니다.
<img src="https://github.com/f-lab-edu/youtube-clone/assets/65775701/d194e457-acfa-4978-8833-8b22ba2ef2ee" width="500px" height="300px"/>

### MVVM
- Model : 앱의 데이터를 관리합니다. Room , Retrofit에서 받아온 데이터와 네트워크 작업을 수행합니다.
- View : 데이터를 표시하고, 유저와의 상호작용을 관리합니다. 동영상을 사용한 부분은 XML, 동영상이 필요없는 곳은 선언형 UI인 Compose를 사용하였습니다.
- ViewModel : 앱의 비즈니스 로직을 처리합니다. flow, Rxjava등을 사용하였습니다.

### Layer
<img src="https://github.com/f-lab-edu/youtube-clone/assets/65775701/bfa5c623-a804-4042-8079-b70d3007362b" width="500px" height="600px"/>

### Foldering
```
.
├── app
│
├── buildSrc
│
├── core
│   ├── common
│   ├── data
│   ├── designsystem
│   ├── model
│   ├── youtubeplayer
│   └── domain
│
└── feature
    ├── main
    ├── home
    ├── shorts
    ├── search
    └── singleplayer
```

## 설치
1. 레포지토리 클론
```
git clone https://github.com/f-lab-edu/youtube-clone.git
cd youtube-clone
```
2. 안드로이드 스튜디오에서 프로젝트 실행

## 연락처
- 이메일: pansso@naver.com
- 포트폴리오: <!--포트폴리오 링크-->
