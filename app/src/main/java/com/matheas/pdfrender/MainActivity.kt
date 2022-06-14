package com.matheas.pdfrender

import android.content.Intent
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent()
        intent.action = Intent.ACTION_OPEN_DOCUMENT
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "application/pdf"
        startActivityForResult(intent, 1212)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1212 && resultCode == RESULT_OK) {
            val uri = data?.data;
            if (uri != null) {
                val mFileDescriptor =
                    getApplication().contentResolver.openFileDescriptor(uri, "r")
                if (mFileDescriptor != null) {
                    val renderer = PdfRenderer(mFileDescriptor)
                    val recyclerView = findViewById<RecyclerView>(R.id.pagesRecyclerView)
                    recyclerView.layoutManager = LinearLayoutManager(this)
                    recyclerView.setHasFixedSize(true)
                    recyclerView.adapter = PdfPageAdapter(renderer)
                }
            }
        }
    }
}