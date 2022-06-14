package com.matheas.pdfrender

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.pdf.PdfRenderer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class PdfPageAdapter(
    private val renderer: PdfRenderer,
) : RecyclerView.Adapter<PdfPageAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(bitmap: Bitmap) = (itemView as ImageView).setImageBitmap(bitmap)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.pdf_page, parent, false))

    override fun getItemCount() = renderer.pageCount

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(renderer.openPage(position).renderAndClose())

    private fun PdfRenderer.Page.renderAndClose() = use {
        val bitmap = createBitmap(width)
        render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        bitmap
    }

    private fun PdfRenderer.Page.createBitmap(bitmapWidth: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(
            bitmapWidth, (bitmapWidth.toFloat() / width * height).toInt(), Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.WHITE)
        canvas.drawBitmap(bitmap, 0f, 0f, null)

        return bitmap
    }
}