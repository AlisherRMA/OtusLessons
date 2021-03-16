package ru.otus.otushometask1.presentation.view.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import ru.otus.otushometask1.R


class CustomDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog)
        findViewById<Button>(R.id.dialog_dismiss).setOnClickListener {
            dismiss()

        }

        findViewById<Button>(R.id.dialog_hide).setOnClickListener {
            hide()
        }

    }

}