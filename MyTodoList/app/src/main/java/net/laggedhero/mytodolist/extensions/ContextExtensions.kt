package net.laggedhero.mytodolist.extensions

import android.content.Context
import android.util.TypedValue

fun Context.dpToPx(dipValue: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, resources.displayMetrics)
}