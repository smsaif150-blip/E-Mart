package com.example.e_mart.dialog


import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.e_mart.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.setUpBottomSheetDialog()
{
    val dialog = BottomSheetDialog(requireContext())
    val view= layoutInflater.inflate(R.layout.reset_password_dialog,null)
    dialog.setContentView(view)
    dialog.show()

//    dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED

    dialog.window?.setSoftInputMode(
        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
    )


    val etEmail = view.findViewById<EditText>(R.id.et_reset_password)
    val btn_Send = view.findViewById<Button>(R.id.reset_button_send)
    val btn_Cancel = view.findViewById<Button>(R.id.reset_button_cancel)

    btn_Cancel.setOnClickListener {
        dialog.dismiss()
    }
    
}