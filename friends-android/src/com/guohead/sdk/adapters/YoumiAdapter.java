/*
 * Copyright (c) 2011, GuoheAd Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'GuoheAd Inc.' nor the names of its contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.guohead.sdk.adapters;

import net.youmi.android.AdManager;
import net.youmi.android.AdView;
import net.youmi.android.AdViewListener;

import com.guohead.sdk.BaseAdapter;
import com.guohead.sdk.GHView;
import com.guohead.sdk.utils.Logger;
import com.guohead.sdk.utils.Utils;

public class YoumiAdapter extends BaseAdapter implements AdViewListener {
	private AdView mAdView;

	public YoumiAdapter(GHView view, String params) {
		super(view, params, "Youmi");
	}

	@Override
	public void loadAd() {
		super.loadAd();
		boolean debug=false;
		try {
			debug=Boolean.parseBoolean(keys[2].trim());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AdManager.init(mGHView.getContext(), keys[0].trim(), keys[1].trim(), 1000, debug);
		mAdView = new AdView(mGHView.getActivity());
		mAdView.setId(Utils.TYPE_YOUMI);
		mAdView.setAdViewListener(this);
		addView(mAdView);
	}

	@Override
	public void invalidate() {
		super.invalidate();
	}

	@Override
	public void onConnectFailed(AdView arg0) {
		mAdView.setAdViewListener(null);
		failedReceiveAd(mAdView);
	}

	@Override
	public void onClick() {
	}

	@Override
	public void onAdViewSwitchedAd(AdView arg0) {
		Logger.i("youmi....onAdViewSwitchedAd=====>");
		mAdView.setAdViewListener(null);
		receiveAd(mAdView);
	}

}
