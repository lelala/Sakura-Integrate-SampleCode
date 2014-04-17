package tw.com.ischool.oauthclientsample;

/**
 * 代表呼叫 me.php 後取得的 UserInfo JSON 資料。
 * @author kevinhuang
 * {"uuid":"51a2d748-db92-4c60-a00c-700e0fbdf880",
 *  "firstName":"tech",
 *  "lastName":"HDT",
 *  "mail":"teacher@demo"}
 */
public class UserInfo {

	private String uuid;
	private String firstName;
	private String lastName;
	private String mail;
	
	
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	
}
