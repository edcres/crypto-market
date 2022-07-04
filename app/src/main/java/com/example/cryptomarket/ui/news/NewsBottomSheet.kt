package com.example.cryptomarket.ui.news

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.cryptomarket.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val NEWS_SHEET_STR_KEY = "Coins companion tag"

// todo: probably get rid of this fragment, i don't think this is used in standard bottom sheet

class NewsBottomSheet : BottomSheetDialogFragment() {

    private lateinit var sheetTxt: TextView
    private lateinit var dialog: BottomSheetDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.news_bottom_sheet, container, false)
        sheetTxt = view.findViewById(R.id.news_sheet_txt)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(NEWS_SHEET_STR_KEY)
    }

    companion object {
        const val TAG = "NewsSheet_TAG"
        fun newInstance(testString: String) = NewsBottomSheet().apply {
            arguments = Bundle().apply {
                putString(NEWS_SHEET_STR_KEY, testString)
            }
        }
    }
}