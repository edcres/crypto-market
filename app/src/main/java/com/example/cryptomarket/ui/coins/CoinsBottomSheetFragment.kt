package com.example.cryptomarket.ui.coins

import android.app.Dialog
import androidx.appcompat.R
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.cryptomarket.databinding.ExpandedBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


//private const val COIN_SHEET_STR_KEY = "Coins companion tag"
private const val TAG = "CoinsSheet__TAG"

class CoinsBottomSheetFragment : BottomSheetDialogFragment() {

    // todo: vm here
    private var binding: ExpandedBottomSheetBinding? = null
    private lateinit var dialog: BottomSheetDialog
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<FrameLayout> // BottomSheetBehavior<*>?

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = ExpandedBottomSheetBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val bottomSheetBehavior = (this.dialog).behavior
//        bottomSheetBehavior = BottomSheetBehavior.from(binding!!.root)

        // Setting Peek at the 16:9 ratio keyline of its parent.
        bottomSheetBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO
        // Setting max height of bottom sheet
        binding!!.extraSpace.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels) / 2

        val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        showView(binding!!.appBarLayout, getActionBarSize())
                        hideAppBar(binding!!.profileLayout)
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        hideAppBar(binding!!.appBarLayout)
                        showView(binding!!.profileLayout, getActionBarSize())
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        dismiss()
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> { }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> { }
                    BottomSheetBehavior.STATE_SETTLING -> { }
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) { }
        }
        bottomSheetBehavior.addBottomSheetCallback(bottomSheetCallback)

        //aap bar cancel button clicked
        //aap bar cancel button clicked
        binding!!.cancelBtn.setOnClickListener { dismiss() }

        hideAppBar(binding!!.appBarLayout);

        return dialog
    }

    override fun onStart() {
        super.onStart()
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun hideAppBar(view: View) {
        val params = view.layoutParams
        params.height = 0
        view.layoutParams = params
    }

    private fun showView(view: View, size: Int) {
        val params = view.layoutParams
        params.height = size
        view.layoutParams = params
    }

    private fun getActionBarSize(): Int {
        val array = requireContext()
            .theme.obtainStyledAttributes(intArrayOf(R.attr.actionBarSize))
        return array.getDimension(0, 0f).toInt()
    }
}