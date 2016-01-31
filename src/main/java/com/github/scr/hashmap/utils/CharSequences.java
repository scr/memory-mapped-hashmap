/*
The MIT License (MIT)

Copyright (c) 2015 Sheridan C Rawlins

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

package com.github.scr.hashmap.utils;

import java.nio.CharBuffer;
import java.util.Iterator;

/**
 * Created by scr on 7/2/15.
 */
public class CharSequences {
    public static int hashCode(CharSequence charSequence) {
        int length = charSequence.length();
        int h = 0;
        for (int i = 0; i < length; ++i) {
            h = 31 * h + charSequence.charAt(i);
        }
        return h;
    }

    public static boolean equalTo(CharSequence a, CharBuffer b, int bStart, int bLength) {
        int aLength = a.length();
        if (aLength != bLength) {
            return false;
        }
        for (int i = 0; i < aLength; ++i) {
            if (a.charAt(i) != b.get(bStart + i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean equalTo(CharSequence a, CharSequence b) {
        int aLength = a.length();
        if (aLength != b.length()) {
            return false;
        }
        for (int i = 0; i < aLength; ++i) {
            if (a.charAt(i) != b.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static boolean allEqualTo(Iterable<CharSequence> a, Iterable<CharSequence> b) {
        Iterator<CharSequence> aIterator = a.iterator();
        Iterator<CharSequence> bIterator = b.iterator();

        while(aIterator.hasNext()) {
            if (!bIterator.hasNext()) {
                return false;
            }
            if (!equalTo(aIterator.next(), bIterator.next())) {
                return false;
            }
        }
        return !bIterator.hasNext();
    }

    public static int compareTo(CharSequence a, CharSequence b) {
        int aLength = a.length();
        int bLength = b.length();
        for (int i = 0; i < aLength; ++i) {
            if (i >= bLength) {
                return +1;
            }
            int diff = Character.compare(a.charAt(i), b.charAt(i));
            if (diff != 0) {
                return diff;
            }
        }
        return (aLength < bLength) ? -1 : 0;
    }
}
