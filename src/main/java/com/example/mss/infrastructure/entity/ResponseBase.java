package com.example.mss.infrastructure.entity;

import com.example.mss.infrastructure.constants.RETURN_TP;

/**
 * packageName  : com.example.mss.infrastructure.entity
 * fileName     : ResponseBase
 * auther       : imzero-tech
 * date         : 2025. 1. 11.
 * descriptuon  :
 * =======================================================
 * DATE         AUTHER              NOTE
 * -------------------------------------------------------
 * 2025. 1. 11.      imzero-tech             최초생성
 */
public class ResponseBase<T> {
    protected RETURN_TP code;
    protected String message;
    protected T data;

    public static <T> ResponseBase<T> of(RETURN_TP code, T data) {
        ResponseBase<T> response = new ResponseBase();
        response.setCode(code);
        response.setMessage("");
        response.setData(data);
        return response;
    }

    public static <T> ResponseBase<T> of(RETURN_TP code, String message) {
        ResponseBase<T> response = new ResponseBase();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }


    public RETURN_TP getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

    public void setCode(final RETURN_TP code) {
        this.code = code;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ResponseBase)) {
            return false;
        } else {
            ResponseBase<?> other = (ResponseBase) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47:
                {
                    Object this$code = this.getCode();
                    Object other$code = other.getCode();
                    if (this$code == null) {
                        if (other$code == null) {
                            break label47;
                        }
                    } else if (this$code.equals(other$code)) {
                        break label47;
                    }

                    return false;
                }

                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ResponseBase;
    }

    public int hashCode() {
        int result = 1;
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    public String toString() {
        return "ResponseBase(code=" + this.getCode() + ", message=" + this.getMessage() + ", data=" + this.getData() + ")";
    }

    public ResponseBase() {
        this.code = RETURN_TP.FAIL;
        this.message = "";
    }

    private ResponseBase(final RETURN_TP code, final String message, final T data) {
        this.code = RETURN_TP.FAIL;
        this.message = "";
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseBase<T> of(final RETURN_TP code, final String message, final T data) {
        return new ResponseBase(code, message, data);
    }
}