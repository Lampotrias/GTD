package com.lampotrias.gtd.tools.obsidian

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

object Obsidian {
    fun execute(
        context: Context,
        url: String,
    ) {
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                addCategory(Intent.CATEGORY_BROWSABLE)
            }

        if (intent.resolveActivity(context.packageManager) != null) {
            try {
                context.startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Toast
                    .makeText(
                        context,
                        "Приложение для обработки этой ссылки не найдено",
                        Toast.LENGTH_LONG,
                    ).show()
            }
        } else {
            Toast
                .makeText(
                    context,
                    "Приложение для обработки этой ссылки не найдено",
                    Toast.LENGTH_LONG,
                ).show()
        }
    }
}
