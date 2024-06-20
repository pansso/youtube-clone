# 유튜브 클론 (YouTube Clone)

<!-- 스크린샷 -->

## 목차
1. [소개](#소개)
2. [핵심 기능](#핵심-기능)
3. [브랜치 관리](#브랜치-관리)
4. [사용 기술](#사용-기술)
5. [아키텍처](#아키텍처)
6. [연락처](#연락처)

## 소개
유튜브 기능을 모방한 안드로이드 어플리케이션입니다.
<br>이 프로젝트는 디자인패턴, 앱아키텍처, 멀티모듈 구조 등 통합 기술을 포트폴리오로서 보여주기 위해 제작되었습니다.

## 핵심 기능
1. YouTube Player 모듈 라이브러리화
   - WebView를 사용하여 YouTube Player 모듈을 ExoPlayer2와 유사한 모듈로 구현했습니다.
   - YouTube API의 특성상 실제 비디오 주소를 제공하지 않아 WebView를 사용했습니다.
   - 이를 통해 webView를 통해 재생되는 영상 플레이어를 라이브러리처럼 사용하였습니다.

2. JavaScript 기반 이벤트 처리 및 콜백 인터페이스
   - 제작한 Player 모듈을 컨트롤할 웹뷰 이벤트 핸들러를 추가하였습니다.
   - ExoPlayer2와 유사한 명령어와 비디오 상태값을 표시하기 위해 JavaScript를 활용한 이벤트 핸들러와 콜백 인터페이스를 구현했습니다.
   - callback, eventhandler, state 등으로 플레이어를 추가로 관리할 수 있습니다.
  
4. Shorts 뷰 구현
   - 실제 YouTube Shorts 뷰와 비슷한 기능을 구현했습니다.

   가. 화면 절반 기준 자동 재생 기능
      - RecyclerView의 `onScrolled` 이벤트 리스너를 사용해 화면에 반을 넘어가는 아이템을 감지하였습니다.
      - 감지된 아이템의 위치에 따라 해당 아이템의 동영상을 자동 재생하거나 일시정지하는 로직을 구현하였습니다.
      - 이를통해 다음과 같은 이점을 얻을수 있었습니다.
         - 사용자 경험 향상: youtube 앱의 shorts 기능과 유사한 자동재생 기능을 제공
         - 비디오 재생 최적화: 화면에 보이는 비디오만 재생해 리소스 낭비를 줄였습니다.
      - 구현방법:
        - RecyclerView의 `onScrolled` 메서드 내에 `computeVerticalScrollExtent()` 와 `computeVerticalScrollOffset()` 메서드를 사용해 현재 스크롤 위치와 화면에 보이는 항목의 높이를 계산하였습니다.
        - 이 값들을 활용해 화면 절반을 기준으로 재생할 위치를 결정하는 `playablePosition`을 산출하였습니다.
        - `scrollEvent` 라이브 데이터를 통해 재생 위치 변경 사항을 감지하고 필요한 동작을 수행하였습니다.
       
   나. 페이지네이션 기능
      - paging 라이브러리를 사용하지 않고, 직접 페이지네이션 기능을 구현하였습니다.
           
   다. Adapter 이벤트 핸들러 커스터마이징
      - Shorts Adapter에 pagination, lifecycle, scrollEvent등 이벤트 핸들러를 삽입하여 커스터마이징했습니다.
  
   라. ListAdapter와 DiffUtil 적용
      - 전체 목록을 다시 그리지않고, 변경된 부분만 업데이트해 성능을 향상시켰습니다.
      - 불필요한 ViewHolder 인스턴스 생성을 방지해 메모리 사용량을 줄였습니다.
      - 데이터 변경시 수동으로 adapter를 업데이트하는 과정을 줄일수 있었습니다.
```
interface ShortsInterface {
    val owner: LifecycleOwner
    val observeScrollEvent: Flow<Int>
}

class ShortsAdapter(private val shortsInterface: ShortsInterface) :
    ListAdapter<YoutubeItem, ShortsAdapter.ViewHolder>(
        object : DiffUtil.ItemCallback<YoutubeItem>() {
            override fun areItemsTheSame(oldItem: YoutubeItem, newItem: YoutubeItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: YoutubeItem, newItem: YoutubeItem): Boolean {
                return true
            }
        }
    )
```

4. WebView 메모리관리
   - 사용하지 않는 WebView가 메모리에 누적되는 문제를 해결하기 위해 생명주기에 따라 WebView를 자동으로 관리하는 기능을 구현하였습니다.
   - 이를 통해 수동으로 WebView를 해제해야 하는 번거로움을 해결하였습니다.
  
   - 적용전 : 스크롤링 하는만큼 웹뷰가 추가로 생성됨
   <img src="https://github.com/f-lab-edu/youtube-clone/assets/65775701/0bb7c071-f1a0-46bb-920b-ec54d6a73a79" width="400px">

   - 적용후
   <img src="https://github.com/f-lab-edu/youtube-clone/assets/65775701/b868db7d-ac82-4333-aa58-a6848910e7ae" width="400px">

  
4. 메모리 관리
   - 사용하지 않는 webview를 종료하지않으면 메모리에 쌓이는 문제가 있어 이를 수동으로 해제해 주어야 하는대, 이를 생명주기에 따라 자동으로 관리하도록 기능을 구현하였습니다.


5. 로컬 DB를 활용한 데이터 캐싱
   - API 호출 실패 시 로컬 DB에 저장된 이전 데이터를 불러와 UI에 적용했습니다.

6. 검색 로직 최적화
   - Debounce를 사용하여 중복 API 호출을 방지했습니다.
   - 이전 검색 기록을 저장하고, 비슷한 키워드가 입력되면 검색 기록을 추가했습니다.
    
7. Room DB와 ViewModel 분리
   - 속도 최적화를 위해 Room DB는 간단한 저장 및 호출만 구현했습니다.
   - ViewModel에서 중복 제거, 기록 순서 정렬 등의 작업을 수행했습니다.
  
8. r8 빌드를 통한 앱 경량화
   - r8 빌드를 사용해 앱 크기를 최적화하고 경량화 하였습니다.
   - 불필요한 코드와 리소스를 제거해 앱 크기를 줄였습니다.
   - 적용전
   <img src="https://github.com/f-lab-edu/youtube-clone/assets/65775701/f0bbc67b-e986-4c75-92fb-1c75160d2d0c" width="400px">

   - 적용후
   <img src="https://github.com/f-lab-edu/youtube-clone/assets/65775701/0295b978-5ad1-49f7-a3e2-757660431374" width="400px">

## 브랜치 관리
- main - develop - feature 의 Git Flow 전략을 사용하고있습니다.
- issue를 통해 feature 브랜치를 생성, Pull Request를 통해 리뷰 후 병합합니다.
 <img src="https://github.com/f-lab-edu/youtube-clone/assets/65775701/0ac9eae2-48e4-47b9-a3e3-a4927bb91f01" width="400px">

 https://github.com/f-lab-edu/youtube-clone/pull/8

- feature -> develop 브랜치로 병합할때는 Squash merge 를 사용해 기능단위로 커밋을 히스토리를 관리하고있습니다.
<img src="https://github.com/f-lab-edu/youtube-clone/assets/65775701/29043397-24dc-450d-b880-c874271baebd" width="400px">

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
<img src="https://github.com/f-lab-edu/youtube-clone/assets/65775701/d194e457-acfa-4978-8833-8b22ba2ef2ee" width="500px" />

### MVVM
- Model : 앱의 데이터를 관리합니다. Room , Retrofit에서 받아온 데이터와 네트워크 작업을 수행합니다.
- View : 데이터를 표시하고, 유저와의 상호작용을 관리합니다. 동영상을 사용한 부분은 XML, 동영상이 필요없는 곳은 선언형 UI인 Compose를 사용하였습니다.
- ViewModel : 앱의 비즈니스 로직을 처리합니다. flow, Rxjava등을 사용하였습니다.

### Layer
<img src="https://github.com/f-lab-edu/youtube-clone/assets/65775701/bfa5c623-a804-4042-8079-b70d3007362b" width="500px" />

### 모듈화 구조
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

## 연락처
- 이메일: pansso@naver.com
- 포트폴리오: <!--포트폴리오 링크-->
