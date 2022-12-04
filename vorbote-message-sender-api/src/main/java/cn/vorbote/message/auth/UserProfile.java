package cn.vorbote.message.auth;

/**
 * UserProfile<br>
 * Created at 03/12/2022 18:35
 *
 * @author vorbote
 */
public record UserProfile(
        String secretId,
        String secretKey
) {

    public UserProfile(String secretId, String secretKey) {
        this.secretId = secretId;
        this.secretKey = secretKey;
    }

    public static UserProfile createProfile(String secretId, String secretKey) {
        return new UserProfile(secretId, secretKey);
    }
}
