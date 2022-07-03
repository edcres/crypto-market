package com.example.cryptomarket.ui.news

import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NewsBottomSheet : BottomSheetDialogFragment() {

    private lateinit var sheetTxt: TextView
    private lateinit var dialog: BottomSheetDialog

    companion object {
        const val TAG = "NewsSheet_TAG"
        fun newInstance(testString: String) = ModalBottomSheet().apply {
            arguments = Bundle().apply {
                putString(SHEET_STR_KEY, testString)
            }
        }
    }
}