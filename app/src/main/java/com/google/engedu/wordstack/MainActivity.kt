/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.engedu.wordstack

import android.content.res.AssetManager
import android.graphics.Color
import android.os.Bundle
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

class MainActivity : AppCompatActivity() {
    private val words = ArrayList<String>()
    private val random = Random()
    private var stackedLayout: StackedLayout? = null
    private val word1: String? = null
    private val word2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val assetManager: AssetManager = getAssets()
        try {
            val inputStream: InputStream = assetManager.open("words.txt")
            val `in` = BufferedReader(InputStreamReader(inputStream))
            var line: String? = null
            while (`in`.readLine().also { line = it } != null) {
                val word = line!!.trim { it <= ' ' }
                /**
                 *
                 * YOUR CODE GOES HERE
                 *
                 */
            }
        } catch (e: IOException) {
            val toast: Toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG)
            toast.show()
        }
        val verticalLayout: LinearLayout = findViewById(R.id.vertical_layout) as LinearLayout
        stackedLayout = StackedLayout(this)
        verticalLayout.addView(stackedLayout, 3)
        val word1LinearLayout: View = findViewById(R.id.word1)
        word1LinearLayout.setOnTouchListener(TouchListener())
        //word1LinearLayout.setOnDragListener(new DragListener());
        val word2LinearLayout: View = findViewById(R.id.word2)
        word2LinearLayout.setOnTouchListener(TouchListener())
        //word2LinearLayout.setOnDragListener(new DragListener());
    }

    private inner class TouchListener : View.OnTouchListener {
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            if (event.getAction() == MotionEvent.ACTION_DOWN && !stackedLayout!!.empty()) {
                val tile = stackedLayout!!.peek() as LetterTile
                tile.moveToViewGroup(v as ViewGroup)
                if (stackedLayout!!.empty()) {
                    val messageBox: TextView = findViewById(R.id.message_box) as TextView
                    messageBox.setText("$word1 $word2")
                }
                /**
                 *
                 * YOUR CODE GOES HERE
                 *
                 */
                return true
            }
            return false
        }
    }

    private inner class DragListener : View.OnDragListener {
        override fun onDrag(v: View, event: DragEvent): Boolean {
            val action: Int = event.getAction()
            when (event.getAction()) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    v.setBackgroundColor(LIGHT_BLUE)
                    v.invalidate()
                    return true
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    v.setBackgroundColor(LIGHT_GREEN)
                    v.invalidate()
                    return true
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                    v.setBackgroundColor(LIGHT_BLUE)
                    v.invalidate()
                    return true
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    v.setBackgroundColor(Color.WHITE)
                    v.invalidate()
                    return true
                }
                DragEvent.ACTION_DROP -> {
                    // Dropped, reassign Tile to the target Layout
                    val tile = event.getLocalState() as LetterTile
                    tile.moveToViewGroup(v as ViewGroup)
                    if (stackedLayout!!.empty()) {
                        val messageBox: TextView = findViewById(R.id.message_box) as TextView
                        messageBox.setText("$word1 $word2")
                    }
                    /**
                     *
                     * YOUR CODE GOES HERE
                     *
                     */
                    return true
                }
            }
            return false
        }
    }

    fun onStartGame(view: View?): Boolean {
        val messageBox: TextView = findViewById(R.id.message_box) as TextView
        messageBox.setText("Game started")
        /**
         *
         * YOUR CODE GOES HERE
         *
         */
        return true
    }

    fun onUndo(view: View?): Boolean {
        /**
         *
         * YOUR CODE GOES HERE
         *
         */
        return true
    }

    companion object {
        private const val WORD_LENGTH = 5
        val LIGHT_BLUE = Color.rgb(176, 200, 255)
        val LIGHT_GREEN = Color.rgb(200, 255, 200)
    }
}