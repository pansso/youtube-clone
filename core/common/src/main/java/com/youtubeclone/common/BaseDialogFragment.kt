package com.youtubeclone.common

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

abstract class BaseDialogFragment<T : ViewBinding>(
    private val bindingInflater: (LayoutInflater) -> T
) : DialogFragment() {

    private var _binding: T? = null
    val binding: T
        get() = _binding as T

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = bindingInflater.invoke(layoutInflater)
        val dialog = Dialog(requireContext())
        dialog.setContentView(binding.root)
        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}