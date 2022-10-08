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
- REST APIs
  - Retrofit 2
    - For the coinpaprika and the cryptopanic APIs
    - Complex GET queries
  - Moshi
- LiveData
  - Livedata Observers
  - Kotlin Flow
- Kotlin coroutines (for synchronous executions)
- Custom line and pie charts using the MPAndroidChat library
- RecyclerView

---

## **There are 3 main screens**

### Coins Screen:

<p align="left" style="display:flex">
    <a href="https://github.com/edcres/crypto-market">
        <img width=180 src="https://user-images.githubusercontent.com/79296181/194726761-c1a3f668-d1e0-4353-a97a-a50eff434dff.jpg" />
    </a>
    <a href="https://github.com/edcres/crypto-market">
        <img width=180 src="https://user-images.githubusercontent.com/79296181/189295746-f73b6773-87c7-4308-8afe-d12a157b3bfe.gif" />
    </a>
</p>

- The app first displays a long list of crypto coins ordered by rank with charted data and the percentage changed within a specific time frame.
- There is a collapsed bottom sheet displaying the top ranked coin by default
- Clicking a list item or the collapsed sheet expands the sheet revealing a more detailed interactive price chart and more information for the selected coin

---

### Overall Market:

<img align="center" width=180 src="https://user-images.githubusercontent.com/79296181/189296157-e96e17b0-d196-4dcd-a146-c42b94512bc3.gif" />

- Displays broad information about the overall cryptocurrency market
- Contains interactive pie charts comparing some of the top 10 coins on different categories

---

### News Screen:

<img align="center" width=180 src="https://user-images.githubusercontent.com/79296181/189296181-aa964fde-711c-4b79-8fdb-5f4b82e21969.gif" />

- Displays a list of recent news about cryptocurrency
- Each is clickable and opens a webview containing a post of the article with a link to the article
