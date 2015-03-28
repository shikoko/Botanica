package com.softvision.botanica.utils.injection;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Injector {

    public static void setupActivity(Activity activity) {
        activity.setContentView(getLayoutResource(activity));
        attachAnnotatedViews(activity);
    }

    public static View setupFragment(Object fragment, LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(getLayoutResource(fragment), container, false);
        Injector.attachAnnotatedViews(fragment, rootView);
        return rootView;
    }

    public static void attachAnnotatedViews(final Object object) {
        attachAnnotatedViews(object, object);
    }

    public static void attachAnnotatedViews(final Object object, final Object rootViewProvider) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            InjectView viewAnnotation = field.getAnnotation(InjectView.class);
            if (viewAnnotation != null) {
                field.setAccessible(true);
                try {
                    field.set(object, getViewFromProvider(rootViewProvider, viewAnnotation.value()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        Method[] methods = object.getClass().getDeclaredMethods();
        for (final Method method : methods) {
            AttachClick clickAnnotation = method.getAnnotation(AttachClick.class);
            final AttachMultiClick multiClickAnnotation = method.getAnnotation(AttachMultiClick.class);
            if (clickAnnotation != null || multiClickAnnotation != null) {
                method.setAccessible(true);
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            method.invoke(object);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                };
                if (clickAnnotation != null) {
                    getViewFromProvider(rootViewProvider, clickAnnotation.value()).setOnClickListener(listener);
                }
                if (multiClickAnnotation != null) {
                    for (int viewId : multiClickAnnotation.value()) {
                        getViewFromProvider(rootViewProvider, viewId).setOnClickListener(new AttachedClickListener(method, object, viewId));
                    }
                }
            }
        }
    }

    private static class AttachedClickListener implements View.OnClickListener {
        private Method method;
        private Object object;
        private int viewId;

        private AttachedClickListener(Method method, Object object, int viewId) {
            this.method = method;
            this.object = object;
            this.viewId = viewId;
        }

        @Override
        public void onClick(View view) {
            try {
                method.invoke(object, viewId);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private static int getLayoutResource(Object o) {
        InjectLayout layoutAnnotation = o.getClass().getAnnotation(InjectLayout.class);
        return layoutAnnotation != null ? layoutAnnotation.value() : -1;
    }

    private static View getViewFromProvider(Object rootViewProvider, int viewId) {
        if (Activity.class.isAssignableFrom(rootViewProvider.getClass())) {
            //noinspection ConstantConditions
            return ((Activity) rootViewProvider).findViewById(viewId);
        } else if (View.class.isAssignableFrom(rootViewProvider.getClass())) {
            //noinspection ConstantConditions
            return ((View) rootViewProvider).findViewById(viewId);
        }
        return null;
    }

}
