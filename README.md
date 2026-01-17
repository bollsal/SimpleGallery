# 작업 내역 정리

### Day1

- 커밋 1 : [프로젝트 생성]
    1. gitignore 설정
    2. ktlint 적용 후 .editconfig 추가

- 커밋 2 : [모듈 구조화]
    1. 멀티 모듈의 구조 적용
    2. 버전카탈로그에 사용할 의존성을 추가

- 커밋 3 : [UI 작업 1차]
    1. Theme 설정 로직 정리
    2. GalleryScreen과 Item Composable 작성 (이미지 영역은 우선 Box로 대체)
       확장가능성을 염두에 두고 singleType과 multiType(default) 작성

- 커밋 4 : [Hilt & Mavericks 설정]
    1. startup 적용
    2. 공통기능을 포함할 :library:foundation 모듈을 추가
    3. hilt 사용을 위한 의존성 추가 및 적용

### Day2

- 커밋 1 : [domain 로직 작성]
    1. entity, interface, UseCase 작성
    2. mavericks-hilt 의존성 추가 및 필요없는 클래스 제거

- 커밋 2 : [library:network 모듈 추가]
    1. library:network 모듈 추가
    2. Retrofit 설정
    3. Api 호출을 위한 service, resp, interface 구현

- 커밋 3 : [페이징 처리를 위한 클래스 작성]
    1. PageableData 작성
    2. response 맵핑을 위한 interface 추가
    3. timber 적용

- 커밋 4 : [Api, Repository 로직 변경]
    1. Service
    	- 헤더의 정보를 가져오기 위해 Response 지정
    	- query를 queryMap을 사용하여 설정되도록 변경
    2. NextParamMapper 작성 : 읽은 헤더의 정보를 기준으로 nextPage에 대한 정보를 파싱
    3. repository : Map형태의 nextParam을 전달받도록 변경

- 커밋 5 : [Composable]
    1. loadMore 상태일때 보여줄 LoadMoreIndicator 작성
    2. progressColor 추가

- 커밋 6 : [State, ViewModel 작성]
    1. State 작성
    	- 화면 구성과 더보기를 위한 nextParam
    	- configureChange시에 유지하기 위해 columnCount 추가
    2. ViewModel 작성
    	- 초기 데이터 로딩, 더보기
    	- columnCount Toggle

- 커밋 7 : [Error Composable]
    1. 에러발생한 경우 보여줄 컴포저블 작성
    2. PageableData default값 원복

- 커밋 8 : [GalleryScreen, Gallery]
    1. GalleryScreen 작성
    	- lifecycle에 맞춰서 데이터 로딩
    	- 상단 영역은 임의로 scaffold를 활용하여 구성
    	- 로딩 상태에 따라 Indicator 노출, Gallery 노출, Error 노출
    2. Gallery 작성
    	- 로딩된 데이터를 기반으로 LazyGrid 구성
    	- columnCount에 따른 UI 구성
    	- loadMore 적용

### Day3

- 커밋 1 : [ImageLoader, SimpleDiskCache]
    1. ImageLoader 작성
    	- 메모리캐시 -> 디스크캐시 -> 다운로드 순으로 체크
    	- 이미지 다운로드 : HttpURLConnection을 활용
    	- ensureActive를 구간별로 적용하여 스크롤시 안보여지는 (dispose된) 작업들은 바로
    		취소되도록 적용
    	- 메모리캐시에는 리사이즈된 bitmap을 넣도록 적용 (디스크캐시에는 원본)
    2. SimpleDiskCache 작성
    	- lrucache 동작을 참고하여 캐시사이즈가 다 차면 오래된 파일들부터 삭제되도록 적용
    	- 다운로드받은 image의 inputStream을 사용하여 File 추가

- 커밋 2 : [AsyncImage]
    1. AsyncImage Composable 작성
    	- 각 상태에 따른 요소 노출 (로딩/에러/이미지)
    	- crossfade 적용
    	- errorIcon 추가
    	- ImageLoader를 주입받기 위해 EntryPoint 추가

### Day 4

- 커밋 1 : [ImageSize]
    1. memoryCache로 캐싱하는 이미지의 사이즈를 지정할수있도록 반영
    2. hilt, network로 인한 preview 미노출 수정

- 커밋 2 : [Topbar 정리]
    1. Topbar
    	- background가 적용되도록 반영
    	- 스크롤에 따라 elevation이 적용되도록 반영
    	- IconButton 추가 (싱글 모드, 그리드 모드 토글)
    2. lightMode에서 imagePlaceHolder color가 제대로 노출안되는 이슈 수정

### Day 5

- 커밋 1 : [GalleryDetailScreen]
    1. GalleryDetailScreen 작성
       - 전달받은 URL을 통해 웹뷰를 구성
       - 웹뷰의 상태에 따라 상단 back, forward 상태 변경

- 커밋 2 : [Navigation]
    1. compose Navigation을 위한 의존성 추가
    2. GalleryRoute 작성 : 각 페이지 route 추가 및 탐색코드 캡슐화
    3. GalleryNavigation 작성 : 각 페이지에서 수행해야하는 navigation 동작 정의
    4. NavigationModule 작성 : NavHost 구성
    5. MainActivity App모듈로 이동 : NavHost 등록을 위해
    6. 각 screen에 navigationAction 연동

- 커밋 3 : [테스트 작성]
    1. NextParamMapperTest 작성
    2. GetGalleryImageUseCaseTest 작성
    3. 필요한 의존성 및 설정 추가

- 커밋 4 : [테스트]
    1. GalleryScreen에서 Screen에 각각의 state들을 전달하도록 변경
    2. Empty composable을 추가하고 데이터가 없는 경우에 대한 처리 추가
    3. 의존성 정리
