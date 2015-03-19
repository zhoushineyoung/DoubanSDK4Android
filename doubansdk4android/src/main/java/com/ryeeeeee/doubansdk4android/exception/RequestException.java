/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015. Ryeeeeee
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.ryeeeeee.doubansdk4android.exception;

import com.ryeeeeee.doubansdk4android.api.ErrorResponse;
import com.ryeeeeee.doubansdk4android.exception.DoubanException;

/**
 * @author Ryeeeeee
 * @since 2015-02-25
 */
public class RequestException extends DoubanException {
    private ErrorResponse mResponse;

    public RequestException(Throwable throwable, ErrorResponse response) {
        super(throwable);
        mResponse = response;
    }

    public RequestException(ErrorResponse response) {
        super();
        mResponse = response;
    }

    public ErrorResponse getResponse() {
        return mResponse;
    }

    public void setResponse(ErrorResponse response) {
        mResponse = response;
    }

    @Override
    public String toString() {
        return "RequestException{" +
                "mResponse=" + mResponse +
                '}';
    }
}
