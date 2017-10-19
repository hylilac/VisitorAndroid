package com.example.visitorandroid.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.visitorandroid.bean.WeiXin;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import static com.example.visitorandroid.LoginActivity.APP_ID;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	private IWXAPI wxAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		wxAPI = WXAPIFactory.createWXAPI(this,APP_ID);
		wxAPI.handleIntent(getIntent(), this);
    }
    
    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
		setIntent(intent);
        wxAPI.handleIntent(intent, this);
    }

	@Override
	public void onReq(BaseReq baseReq) {}

	@Override
	public void onResp(BaseResp resp) {
		Log.i("ansen", "微信支付回调 返回错误码:"+resp.errCode+" 错误名称:"+resp.errStr);
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX){//微信支付
			WeiXin weiXin=new WeiXin(3,resp.errCode,"");
			EventBus.getDefault().post(weiXin);
		}
		finish();
	}
}
