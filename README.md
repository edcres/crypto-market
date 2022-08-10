# crypto-market

Android App that shows recent data on the cryptocurrency market, including coins, overall market data, and current news

---

### Tools and skills used:

- MVVM architecture
  - Shared ViewModel
- Material Design
- Jetpack Navigation Component
- Bottom navigation
- Standard Bottom Sheet
- SQLite Room local storage
- REST APIs
  - Retrifit 2
    - For the coinpaprika and the cryptopanic APIs
    - Complex GET quieries
  - Moshi
- LiveData
  - Livedata Observers
  - Kotlin Flow
- Kotlin coroutines (for synchronous excecutions)
- Custom line and pie charts using the MPAndroidChat library
- RecyclerView

---

## **There are 3 main screens**

### Coins Screen:

<img align="center" width=132 src="https://user-images.githubusercontent.com/79296181/183276703-95e14097-c6f2-42f5-9e6f-37fc187833d4.gif" />

- The app first displays a long list of crypto coins ordered by rank with charted data and the percentage changed within a specific time frame.
- There is a collapsed bottom sheet displaying the top ranked coin by default
- Clicking a list item or the collapsed sheet expands the sheet revealing a more detailed interactive price chart and more information for the selected coin

---

### Overall Market:

<img align="center" width=132 src="https://user-images.githubusercontent.com/79296181/183534949-a1ab13be-1a95-46a8-b762-fa28bfc2455b.gif" />

- Displays broad information about the overall cryptocurrency market
- Contains interactive pie charts comparing some of the top 10 coins on different categories

---

### News Screen:

<img align="center" width=132 src="https://user-images.githubusercontent.com/79296181/183535995-dff45901-6c09-448e-ba6d-d406a1a36508.gif" />

- Displays a list of recent news about cryptocurrency
- Each is clickable and opens a webview containing a post of the article with a link to the article
