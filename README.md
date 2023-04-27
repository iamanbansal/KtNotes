
**KtNotes** fullstack Notes📝 taking application to showcase Kotlin comprehensive capabilities in server-side and both iOS & Android platforms.


## 💡About the Project

The project has been split into two separate parts:

## [KtNotes Api](/api)

This is a REST API built using Ktor Framework deployed on *[Railway](https://railway.app/)*.  
Navigate to [`/api`](/api) directory to browse and know more about backend API.

#### Uses:
- [Ktor](https://ktor.io/docs/intellij-idea.html)
- [Postgres Database](https://www.postgresql.org/)
- [Ktor JWT](https://ktor.io/docs/jwt.html)
- [Exposed ORM](https://github.com/JetBrains/Exposed)
- [JBCrypt](https://mvnrepository.com/artifact/org.mindrot/jbcrypt)

## [KtNotes Mobile App](/mobile)

An Android and iOS app built using [Kotlin Multiplatforom](https://kotlinlang.org/docs/multiplatform.html) that consumes KtNotes APIs.

[Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) is a framework of Kotlin that allows for sharing of a single codebase for business logic across different [targets](https://kotlinlang.org/docs/multiplatform-dsl-reference.html#targets)/platforms. Some of the targets supported by kotlin are: Android, iOS, Kotlin/JVM, Kotlin/JS, Android NDK, Windows, Linux, macOS etc.

Navigate to [`/mobile`](/mobile) directory to browse and know more about Noty Android project.

### Modules
The mobile has been split into 3 main modules:

### shared:
This module contains shared kotlin code that holds the domain and data layers and some part of the presentation logic i.e. shared ViewModels
- [Ktor Client](https://ktor.io/docs/getting-started-ktor-client.html)
- [SQLDelight](https://github.com/cashapp/sqldelight)
- [Settings](https://github.com/russhwolf/multiplatform-settings)
- [Kotlinx date-time](https://github.com/Kotlin/kotlinx-datetime)
- [Koin](https://github.com/InsertKoinIO/koin)

### androidApp:
This module contains the android application's UI built using Jetpack Compose UI
- Jetpack Compose UI
- OkHttp engine for Ktor
- Security Crypto for Encrypted Shared Preferences

### iosApp:
This module contains iOS code that holds the iosApp UI built using SwiftUI
- Swift UI
- Darwin engine for Ktor
- User Defaults Preferences

### Architecture
App uses MVVM architecture. ViewModel carries business logic and shared across both platform. Platforms extend respective shared ViewModel and use accordingly
![ViewModel Structure](images/viewmodel%20structure.png)

# Screenshots
<div style="display: flex; justify-content: center;">

<img src="https://user-images.githubusercontent.com/24875315/229297736-0cf94630-a07e-46ac-8369-5271d31f2162.png" width="360" height="640" />
<img src="https://user-images.githubusercontent.com/24875315/229297738-4cc631d1-beec-48f6-95b8-d466b5d5c613.png" width="360" height="640" />
<img src="https://user-images.githubusercontent.com/24875315/229297729-132ce3b0-40ef-4a0e-9109-4298d0f36632.png" width="360" height="640" />
<img src="https://user-images.githubusercontent.com/24875315/229297733-b8531c11-96cc-48a8-b2bb-236665d76349.png" width="360" height="640" />

<img src="https://user-images.githubusercontent.com/24875315/229297541-5418a16b-7920-4068-a963-820bec13f85f.png" width="360" height="777" />
<img src="https://user-images.githubusercontent.com/24875315/229297542-fb4154b8-3512-4101-aa5f-491b0940b8ff.png" width="360" height="777" />
<img src="https://user-images.githubusercontent.com/24875315/229297538-ca990f6c-f901-43f8-90e4-c2d84cf5219f.png" width="360" height="777" />
<img src="https://user-images.githubusercontent.com/24875315/229297540-db88dc6f-ecd0-4c3a-a179-3925e284a9d5.png" width="360" height="777" />

</div>
