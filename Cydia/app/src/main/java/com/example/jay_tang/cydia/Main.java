package com.example.jay_tang.cydia;

import android.util.Log;

import com.saurik.substrate.MS;

import java.lang.reflect.Method;


/**
 * Created by Jay-Tang on 2016/10/3.
 */
public class Main {


        static void initialize() {
            MS.hookClassLoad("android.widget.TextView", new MS.ClassLoadHook() {

                public void classLoaded(Class<?> arg0) {
                    // TODO Auto-generated method stub

                    Method smstest;

                    try {

                        smstest = arg0.getMethod("setText", CharSequence.class);

                    } catch (SecurityException e) {
                        // TODO Auto-generated catch block
                        smstest = null;
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        // TODO Auto-generated catch block
                        smstest = null;
                        e.printStackTrace();
                    }

                    if (smstest != null) {

                        final MS.MethodPointer old = new MS.MethodPointer();

                        MS.hookMethod(arg0, smstest, new MS.MethodHook() {

                            @Override
                            public Object invoked(Object arg0, Object... arg1)
                                    throws Throwable {
                                // TODO Auto-generated method stub

                                Log.d("ggz", "i am hook in------->");

                                String bb = (String) arg1[0];

                                Log.d("ggz", "string is----->" + bb);

                                bb = "hahah";

                                Log.d("ggz", "now string is --->" + bb);

                                return old.invoke(arg0, bb);

                            }

                        }, old);

                    }

                }

            });

        }

    }


