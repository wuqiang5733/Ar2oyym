package org.xuxiaoxiao.myyora.services;

import android.net.Uri;

import org.xuxiaoxiao.myyora.infrastructure.ServiceResponse;
import org.xuxiaoxiao.myyora.infrastructure.User;


public final class Account {
    //这个类是不能实例化的，这个类是为了 Hold 其它类的，它存在的目的就是组织，
    // final 与 private 就是不让实例化。
    // 在 Java 当中也不允许一个文件当中有多个顶层类，这样做，也是为这个目的。
    private Account(){
    }
    public static  class UserResponse extends ServiceResponse {
        public int Id;
        public String AvatarUrl;
        public String DisplayName;
        public String UserName;
        public String Email;
        public String AuthToken;
        public boolean HasPassword;
    }

    public static class LoginWithUserNameRequest {
        public String UserName;
        public String Password;
        // 这两个对应的是 fragment_login.xml

        public LoginWithUserNameRequest(String userName, String password) {
            UserName = userName;
            Password = password;
        }
    }

//    public static class LoginWithUserNameResponse extends UserResponse {
//    }

    public static class LoginWithUserNameResponse extends ServiceResponse {
    }
    /*************************************************************************/
    public static class LoginWithLocalTokenRequest {
        public String AuthToken;

        public LoginWithLocalTokenRequest(String authToken) {
            AuthToken = authToken;
        }
    }

    public static class LoginWithLocalTokenResponse extends UserResponse {
    }
    /****************************************************************************/
    public static class LoginWithExternalTokenRequest {
        public String Provider;
        public String Token;
        public String ClientId;

        public LoginWithExternalTokenRequest(String provider, String token) {
            Provider = provider;
            Token = token;
            ClientId = "android";
        }
    }
    // Google 跟 Facebook

    public static class LoginWithExternalTokenResponse extends UserResponse {
    }
    /**************************************************************************************/
    public static class RegisterRequest {
        public String UserName;
        public String Email;
        public String Password;
        public String ClientId;

        public RegisterRequest(String userName, String email, String password) {
            UserName = userName;
            Email = email;
            Password = password;
            ClientId = "android";
        }
    }

    public static class RegisterResponse extends UserResponse  {
    }
    /*****************************************************************************************/
    public static class RegisterWithExternalTokenRequest {
        public String UserName;
        public String Email;
        public String Provider;
        public String Token;
        public String ClientId;

        public RegisterWithExternalTokenRequest(String userName, String email, String provider, String token) {
            UserName = userName;
            Email = email;
            Provider = provider;
            Token = token;
            ClientId = "android";
        }
    }

    public static class RegisterWithExternalTokenResponse extends UserResponse {
    }
    /*********************************************************************************************/
    public static class ChangeAvatarRequest {
        public Uri NewAvatarUri;

        public ChangeAvatarRequest(Uri newAvatarUri) {
            NewAvatarUri = newAvatarUri;
        }
    }

    public static class ChangeAvatarResponse extends ServiceResponse {
        public String AvatarUrl;

    }
    /*********************************************************************************************/

    public static class UpdateProfileRequest {
        public String DisplayName;
        public String Email;

        public UpdateProfileRequest(String displayName, String email) {
            DisplayName = displayName;
            Email = email;
        }
    }

    public static class UpdateProfileResponse extends ServiceResponse {
        public String DisplayName;
        public String Email;
    }
    /*********************************************************************************************/

    public static class ChangePasswordRequest {
        public String CurrentPassword;
        public String NewPassword;
        public String ConfirmNewPassword;

        public ChangePasswordRequest(String currentPassword, String newPassword, String confirmNewPassword) {
            CurrentPassword = currentPassword;
            NewPassword = newPassword;
            ConfirmNewPassword = confirmNewPassword;
        }
    }

    public static class ChangePasswordResponse extends ServiceResponse {
    }
    /*******************************************************************************/
    public static class UserDetailsUpdatedEvent {
        public User User;

        public UserDetailsUpdatedEvent(User user) {
            User = user;
        }
    }
    /*******************************************************************************/
    public static class myinterface {

    }
    public static class UpdateGcmRegistrationRequest {
        public String RegistrationId;

        public UpdateGcmRegistrationRequest(String registrationId) {
            RegistrationId = registrationId;
        }
    }

    public static class UpdateGcmRegistrationResponse extends ServiceResponse {
    }

}
