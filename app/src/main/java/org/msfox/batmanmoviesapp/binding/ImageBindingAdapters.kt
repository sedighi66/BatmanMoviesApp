package org.msfox.batmanmoviesapp.binding

import android.app.Activity
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.jgabrielfreitas.core.BlurImageView
import org.msfox.batmanmoviesapp.utils.wasted

/**
 * Binding adapters that work with an activity instance.
 */
class ImageBindingAdapters(private val activity: Activity) {
    @BindingAdapter(value = ["imageUrl"], requireAll = false)
    fun bindImage(imageView: ImageView, url: String?) {
        Glide.with(activity).load(url).into(imageView)
    }
}

