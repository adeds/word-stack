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

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import java.util.*

class StackedLayout(context: Context?) : LinearLayout(context) {
    private val tiles: Stack<View?> = Stack()
    fun push(tile: View?) {
        /**
         *
         * YOUR CODE GOES HERE
         *
         */
    }

    fun pop(): View? {
        /**
         *
         * YOUR CODE GOES HERE
         *
         */
        return null
    }

    fun peek(): View? {
        return tiles.peek()
    }

    fun empty(): Boolean {
        return tiles.empty()
    }

    fun clear() {
        /**
         *
         * YOUR CODE GOES HERE
         *
         */
    }
}