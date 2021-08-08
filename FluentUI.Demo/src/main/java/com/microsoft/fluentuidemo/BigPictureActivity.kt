package com.microsoft.fluentuidemo

import android.app.Activity
import android.app.ProgressDialog
import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.ImageView


class BigPictureActivity : Activity() {
    var uploadactivitystart: ImageView? = null
    var Path: String? = ""
    var pic: Bitmap? = null
    var matrix = Matrix()
    var scale = 1f
    var SGD: ScaleGestureDetector? = null
    var image: ImageView? = null
    var progressDialog: ProgressDialog? = null
    private var mScaleGestureDetector: ScaleGestureDetector? = null
    private var mScaleFactor = 1.0f
    private var mImageView: ImageView? = null
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bigpicture)
        val bundle = intent.extras
        Path = bundle!!.getString("Path")
        uploadactivitystart = findViewById(R.id.download)
        image = findViewById(R.id.bigpicture)
        mImageView = findViewById<View>(R.id.bigpicture) as ImageView
        mScaleGestureDetector = ScaleGestureDetector(this, ScaleListener())
        /*  DownloadImage().execute(Path)
          uploadactivitystart.setClickable(true)
          uploadactivitystart.setOnClickListener(View.OnClickListener { SaveAsImage().execute() })*/
    }

    override fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        mScaleGestureDetector!!.onTouchEvent(motionEvent)
        return true
    }

    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(scaleGestureDetector: ScaleGestureDetector): Boolean {
            mScaleFactor *= scaleGestureDetector.scaleFactor
            mScaleFactor = Math.max(
                0.1f,
                Math.min(mScaleFactor, 10.0f)
            )
            mImageView!!.scaleX = mScaleFactor
            mImageView!!.scaleY = mScaleFactor
            return true
        }
    }


    companion object {
        private val TAG = HTTPConnection::class.java.simpleName
    }
}