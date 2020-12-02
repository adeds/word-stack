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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.engedu.wordstack.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val words = ArrayList<String>()
    private val random = Random()
    private lateinit var stackedLayout: StackedLayout
    private var word1: String? = null
    private var guestWord1 = ""
    private var word2: String? = null
    private var guestWord2 = ""
    private var isLastWord1 = false
    private val placedTiles: Stack<LetterTile> = Stack()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val assetManager: AssetManager = assets
        try {
            val inputStream: InputStream = assetManager.open("words.txt")
            val `in` = BufferedReader(InputStreamReader(inputStream))
            var line: String?
            while (`in`.readLine().also { line = it } != null) {
                val word = line!!.trim { it <= ' ' }
                if (word.length <= WORD_LENGTH)
                    words.add(word.toLowerCase(Locale.ROOT))
            }
        } catch (e: IOException) {
            val toast: Toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG)
            toast.show()
        }

        stackedLayout = StackedLayout(this)
        binding.verticalLayout.addView(stackedLayout, 3)
        binding.word1.setOnTouchListener(TouchListener())
        binding.word1.setOnDragListener(DragListener());
        binding.word2.setOnTouchListener(TouchListener())
        binding.word2.setOnDragListener(DragListener());

        initClickListener()
    }

    private fun initClickListener() = binding.apply {
        startButton.setOnClickListener { onStartGame() }
        undoButton.setOnClickListener { onUndo() }
    }

    private inner class TouchListener : View.OnTouchListener {
        override fun onTouch(v: View, event: MotionEvent): Boolean {
            if (event.action == MotionEvent.ACTION_DOWN && !stackedLayout.empty()) {
                setLetter(v, stackedLayout.peek() as LetterTile)
                return true
            }
            return false
        }
    }

    private fun setLetter(v: View, tile: LetterTile) {
        placedTiles.push(tile)
        tile.moveToViewGroup(v as ViewGroup)
        isLastWord1 = v == binding.word1
        if (isLastWord1)
            guestWord1 += tile.text
        else guestWord2 += tile.text

        if (stackedLayout.empty()) {
            val text = if (words.contains(guestWord1) && words.contains(guestWord2)) {
                "Youre Genius, challenge me again!"
            } else "Almost there, try again!"
            binding.messageBox.text = "$guestWord1 & $guestWord2 ?\n$text"
            binding.undoButton.isEnabled = false
        }
    }

    private inner class DragListener : View.OnDragListener {
        override fun onDrag(v: View, event: DragEvent): Boolean {
            when (event.action) {
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
                    setLetter(v, event.localState as LetterTile)
                    return true
                }
            }
            return false
        }
    }

    private fun onStartGame() {
        stackedLayout.clear()
        binding.word1.removeAllViews()
        binding.word2.removeAllViews()
        binding.undoButton.isEnabled = true
        guestWord2 = ""
        guestWord1 = ""

        random.nextInt(words.size).let { word1 = words[it] }
        random.nextInt(words.size).let { word2 = words[it] }

        val scrambleText = scramble(word1, word2, random)
        binding.messageBox.text = scrambleText
        var count = scrambleText.length

        while (count > 0) {
            count--
            stackedLayout.push(LetterTile(this, scrambleText[count]))
        }
    }

    private fun onUndo() {
        if (placedTiles.isEmpty().not()) {
            val tile = placedTiles.pop()
            tile.moveToViewGroup(stackedLayout)
            if (isLastWord1)
                guestWord1 = guestWord1.removeLastChars()
            else guestWord2 = guestWord2.removeLastChars()
        }
    }

    companion object {
        private const val WORD_LENGTH = 3
        val LIGHT_BLUE = Color.rgb(176, 200, 255)
        val LIGHT_GREEN = Color.rgb(200, 255, 200)
    }
}