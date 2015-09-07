package com.example.administrator.cropimageinside;

import android.content.Context;
import android.graphics.PointF;

import com.example.administrator.cropimageinside.filter.GPUImageColorInvertFilter;
import com.example.administrator.cropimageinside.filter.GPUImageFalseColorFilter;
import com.example.administrator.cropimageinside.filter.GPUImageFilter;
import com.example.administrator.cropimageinside.filter.GPUImageGrayscaleFilter;
import com.example.administrator.cropimageinside.filter.GPUImageKuwaharaFilter;
import com.example.administrator.cropimageinside.filter.GPUImageSepiaFilter;
import com.example.administrator.cropimageinside.filter.GPUImageSketchFilter;
import com.example.administrator.cropimageinside.filter.GPUImageSmoothToonFilter;
import com.example.administrator.cropimageinside.filter.GPUImageToneCurveFilter;
import com.example.administrator.cropimageinside.filter.GPUImageVignetteFilter;

/**
 * Created by lixi on 2015/9/7.
 */
public class ImageFilterTools {

    private enum FilterType {
        GRAYSCALE, SEPIA, VIGNETTE, TONE_CURVE, SKETCH, SMOOTH_TOON, INVERT, FALSE_COLOR, KUWAHARA, PIXELATION,
    }


    private static GPUImageFilter createFilterForType(final Context context, final FilterType type) {
        switch (type) {
            case INVERT:
                return new GPUImageColorInvertFilter();
            case GRAYSCALE:
                return new GPUImageGrayscaleFilter();
            case SEPIA:
                return new GPUImageSepiaFilter();
            case VIGNETTE:
                PointF centerPoint = new PointF();
                centerPoint.x = 0.5f;
                centerPoint.y = 0.5f;
                return new GPUImageVignetteFilter(centerPoint, new float[] {0.0f, 0.0f, 0.0f}, 0.3f, 0.75f);
            case TONE_CURVE:
                GPUImageToneCurveFilter toneCurveFilter = new GPUImageToneCurveFilter();
                toneCurveFilter.setFromCurveFileInputStream(
                        context.getResources().openRawResource(R.raw.tone_cuver_sample));
                return toneCurveFilter;
            case KUWAHARA:
                return new GPUImageKuwaharaFilter();
            case SKETCH:
                return new GPUImageSketchFilter();
            case SMOOTH_TOON:
                return new GPUImageSmoothToonFilter();

            case FALSE_COLOR:
                return new GPUImageFalseColorFilter();
            default:
                throw new IllegalStateException("No filter of that type!");
        }

    }
}
