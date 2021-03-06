/*
 * Copyright (C) 2012-2014 OUYA, Inc.
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

package tv.ouya.sdk;

import tv.ouya.console.api.DebugInput;
import tv.ouya.console.api.OuyaController;
import tv.ouya.console.api.OuyaInputMapper;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

public class OuyaInputView extends View {

	private static final String TAG = OuyaInputView.class.getSimpleName();

    public OuyaInputView(Context context, AttributeSet attrs) {
    	super(context, attrs);
		//Log.d(TAG, "OuyaInputView(Context context, AttributeSet attrs)");
        init();
    }

    public OuyaInputView(Context context, AttributeSet attrs, int defStyle) {
    	super(context, attrs, defStyle);
		//Log.d(TAG, "OuyaInputView(Context context, AttributeSet attrs, int defStyle)");
        init();
    }

    public OuyaInputView(Context context) {
        super(context);
		//Log.d(TAG, "OuyaInputView(Context context)");
        init();
    }

    private void init() {
		Activity activity = ((Activity)getContext());
		if (null != activity) {
				
			FrameLayout content = (FrameLayout)activity.findViewById(android.R.id.content);
			if (null != content) {
				content.addView(this);
				//Log.d(TAG, "Added view");
			} else {
				Log.e(TAG, "Content view is missing");
			}

			OuyaInputMapper.init(activity);

			activity.takeKeyEvents(true);

			setFocusable(true);
			requestFocus();
		} else {
			Log.e(TAG, "Activity is null");
		}
	}

	public void shutdown() {
		Activity activity = ((Activity)getContext());
		if (null != activity) {
    		OuyaInputMapper.shutdown(activity);
    	} else {
    		Log.e(TAG, "Activity was not found.");
    	}
    }

	@Override
    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
    	//Log.i(TAG, "dispatchGenericMotionEvent");
    	//DebugInput.debugMotionEvent(motionEvent);
    	Activity activity = ((Activity)getContext());
		if (null != activity) {
		    if (OuyaInputMapper.shouldHandleInputEvent(motionEvent)) {
		    	return OuyaInputMapper.dispatchGenericMotionEvent(activity, motionEvent);
		    }
	    } else {
	    	Log.e(TAG, "Activity was not found.");
	    }
    	return super.dispatchGenericMotionEvent(motionEvent);
    }

	@Override
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
		Activity activity = ((Activity)getContext());
		if (null != activity) {
	    	if (OuyaInputMapper.shouldHandleInputEvent(keyEvent)) {
	    		return OuyaInputMapper.dispatchKeyEvent(activity, keyEvent);
	    	}
	    } else {
	    	Log.e(TAG, "Activity was not found.");
	    }
	    return super.dispatchKeyEvent(keyEvent);
    }

	@Override
	public boolean onGenericMotionEvent(MotionEvent motionEvent) {
		//Log.i(TAG, "onGenericMotionEvent");
		//DebugInput.debugMotionEvent(motionEvent);
		DebugInput.debugOuyaMotionEvent(motionEvent);
		return true;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent keyEvent) {
		Log.i(TAG, "onKeyUp keyCode=" + DebugInput.debugGetButtonName(keyEvent.getKeyCode()));
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent keyEvent) {
		Log.i(TAG, "onKeyDown keyCode=" + DebugInput.debugGetButtonName(keyEvent.getKeyCode()));
		return true;
	}
}
