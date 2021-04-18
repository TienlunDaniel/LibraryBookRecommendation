package com.example.librarybookrecommendation.Util

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import com.example.librarybookrecommendation.publicLibrary.NewTaipeiLibrary

fun containingLibrary(isbn : String): List<String>{
    val ret = mutableListOf<String>()

    if (NewTaipeiLibrary.hasBook(isbn))
        ret.add(NewTaipeiLibrary.libraryName)
    return ret
}

fun launchLibraryApp(library : String, activity: Activity){
    val intent = Intent(Intent.ACTION_MAIN)
    intent.component = ComponentName(
        newTaipeiPackageName,
        newTaiepiClassName
    )
    activity.startActivity(intent)
}


