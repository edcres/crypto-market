package com.example.cryptomarket.ui.coins

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.example.cryptomarket.databinding.ExpandedBottomSheetBindingImpl
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val COIN_SHEET_STR_KEY = "Coins companion tag"

// todo: probably get rid of this fragment, i don't think this is used in standard bottom sheet

class CoinsBottomSheetFragment : BottomSheetDialogFragment() {

    private var binding: ExpandedBottomSheetBindingImpl? = null
    private lateinit var sheetTxt: TextView
    private lateinit var dialog: BottomSheetDialog
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View> // BottomSheetBehavior<*>?

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getString(COIN_SHEET_STR_KEY)
    }

    companion object {
        const val TAG = "CoinsSheet_TAG"
        fun newInstance(testString: String) = CoinsBottomSheetFragment().apply {
            arguments = Bundle().apply {
                putString(COIN_SHEET_STR_KEY, testString)
            }
        }
    }
}