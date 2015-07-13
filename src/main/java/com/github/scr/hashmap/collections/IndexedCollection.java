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

package com.github.scr.hashmap.collections;

import com.github.scr.hashmap.DataWriter;
import org.jetbrains.annotations.Nullable;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;

/**
 * Created by scr on 7/3/15.
 */
public interface IndexedCollection<E> extends Collection<E>, DataWriter {
    @Nullable E get(int index);

    default byte getByte(int index) {
        throw new NotImplementedException();
    }
    default char getChar(int index) {
        throw new NotImplementedException();
    }
    default double getDouble(int index) {
        throw new NotImplementedException();
    }
    default float getFloat(int index) {
        throw new NotImplementedException();
    }
    default long getLong(int index) {
        throw new NotImplementedException();
    }
    default short getShort(int index) {
        throw new NotImplementedException();
    }
    default int getInt(int index) {
        throw new NotImplementedException();
    }
}
