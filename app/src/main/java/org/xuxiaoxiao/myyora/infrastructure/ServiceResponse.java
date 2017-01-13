package org.xuxiaoxiao.myyora.infrastructure;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.xuxiaoxiao.myyora.R;

import java.util.HashMap;

public abstract class ServiceResponse {//注意是抽象类
    private static final String TAG = "ServiceResponse";
// _propertyErrors 比如验证错误，比如邮箱不正确 
// _isCritical 一般是程序有 Bug 了
    private String _operationError;
    private HashMap<String, String> _propertyErrors;
    private boolean _isCritical;

    public ServiceResponse () { // 三个构造函数
        _propertyErrors = new HashMap<>();
    }

    public ServiceResponse(String operationError) {
       this._operationError = operationError;
    }

    public ServiceResponse(String operationError, boolean isCritical) {
        this._operationError = operationError;
        this._isCritical = isCritical;
    }


    public String getOperationError() {
        return _operationError;
    }

    public void setOperationError(String operationError) {
        this._operationError = operationError;
    }

    public boolean isCritical() {
        return _isCritical;
    }

    public void setCritical(boolean critical) {
        this._isCritical = critical;
    }

    public void setPropertyError(String property, String error) {
        _propertyErrors.put(property, error);
    }

    public String getPropertyError(String property) {
        return _propertyErrors.get(property);
    }

    public boolean didSucceed() {
        return (_operationError == null || _operationError.isEmpty()) &&
               (_propertyErrors.size() == 0);
    }

    public void showErrorToast(Context context) {
        if (context == null || _operationError == null || _operationError.isEmpty())
            return;
        try {
            Toast.makeText(context, _operationError, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, context.getString(R.string.ServiceResponseShowErrorToast), e);
        }
    }
}
