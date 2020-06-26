//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.snackpub.core.tools.utils;


import com.snackpub.core.tools.support.FastStringWriter;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

public class Exceptions {
    public Exceptions() {
    }

    public static RuntimeException unchecked(Throwable e) {
        if (!(e instanceof IllegalAccessException) && !(e instanceof IllegalArgumentException) && !(e instanceof NoSuchMethodException)) {
            if (e instanceof InvocationTargetException) {
                return new RuntimeException(((InvocationTargetException)e).getTargetException());
            } else {
                return e instanceof RuntimeException ? (RuntimeException)e : new RuntimeException(e);
            }
        } else {
            return new IllegalArgumentException(e);
        }
    }

    public static Throwable unwrap(Throwable wrapped) {
        Throwable unwrapped = wrapped;

        while(true) {
            while(!(unwrapped instanceof InvocationTargetException)) {
                if (!(unwrapped instanceof UndeclaredThrowableException)) {
                    return unwrapped;
                }

                unwrapped = ((UndeclaredThrowableException)unwrapped).getUndeclaredThrowable();
            }

            unwrapped = ((InvocationTargetException)unwrapped).getTargetException();
        }
    }

    public static String getStackTraceAsString(Throwable ex) {
        FastStringWriter stringWriter = new FastStringWriter();
        ex.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }
}
