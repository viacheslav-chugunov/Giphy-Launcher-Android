package viacheslav.chugunov.core.extensions

import android.content.res.Configuration

val Configuration.isLandscape: Boolean
    get() = orientation == Configuration.ORIENTATION_LANDSCAPE