using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class OAuthCallBack : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        //本頁是完成OAuth授權後進入實際資料整合的頁面

        //註冊的ClientID 資料
        string client_id = System.Web.Configuration.WebConfigurationManager.AppSettings["ClientId"];
        //註冊的ClientID 資料
        string client_secret = System.Web.Configuration.WebConfigurationManager.AppSettings["ClientSecret"];
        //註冊的RedirectURI 資料
        string redirect_uri = System.Web.Configuration.WebConfigurationManager.AppSettings["RedirectURI"];
        //由傳入的state參數取得application
        string application = "" + Request["state"];
        //取得code
        string code = "" + Request["code"];
        //透過Server to Server呼叫由code換取AccessToken
        string accessToken = "";


        //取得AccessToken
        HttpWebRequest req = (HttpWebRequest)HttpWebRequest.Create(
            "https://auth.ischool.com.tw/oauth/token.php"
                + "?client_id=" + client_id
                + "&client_secret=" + client_secret
                + "&code=" + code
                + "&grant_type=authorization_code"
                + "&redirect_uri=" + HttpUtility.UrlEncodeUnicode(redirect_uri)
        );
        req.SetRequestContex();
        req.Method = "GET";
        accessToken = req.GetJsonResponseAs<OAuthTokenResult>().AccessToken;

        //取得UserInfo
        HttpWebRequest reqUserInfo = (HttpWebRequest)HttpWebRequest.Create(
            "https://auth.ischool.com.tw/services/me.php"
                + "?access_token=" + accessToken
        );
        reqUserInfo.SetRequestContex();
        reqUserInfo.Method = "GET";
        TextArea1.InnerText = new StreamReader(reqUserInfo.GetResponse().GetResponseStream()).ReadToEnd();

        //取得Group
        HttpWebRequest reqGroup = (HttpWebRequest)HttpWebRequest.Create(
            "https://dsns.1campus.net/" + application + "/sakura/GetMyGroup"
                + "?stt=PassportAccessToken"
                + "&AccessToken=" + accessToken
        );
        reqGroup.SetRequestContex();
        reqGroup.Method = "GET";
        //設定自動處理http redirect，https://dsns.1campus.net 會redirect到真正的位置
        reqGroup.AllowAutoRedirect = true;
        
        TextArea2.InnerText = new StreamReader(reqGroup.GetResponse().GetResponseStream()).ReadToEnd();

        //取得GetGroupMember
        HttpWebRequest reqGroupMember = (HttpWebRequest)HttpWebRequest.Create(
            "https://dsns.1campus.net/" + application + "/sakura/GetGroupMember"
                + "?stt=PassportAccessToken"
                + "&AccessToken=" + accessToken
        );
        reqGroupMember.SetRequestContex();
        reqGroupMember.Method = "GET";
        //設定自動處理http redirect，https://dsns.1campus.net 會redirect到真正的位置
        reqGroupMember.AllowAutoRedirect = true;

        TextArea3.InnerText = new StreamReader(reqGroupMember.GetResponse().GetResponseStream()).ReadToEnd();
    }
}
public static class HttpClientExt
{
    public static T GetJsonResponseAs<T>(this HttpWebRequest req)
    {
        T result = default(T);
        var response = req.GetResponse();
        System.IO.MemoryStream ms = new MemoryStream();
        Stream receiveStream = response.GetResponseStream();
        receiveStream.CopyTo(ms);
        ms.Seek(0, SeekOrigin.Begin);
        try
        {
            result = (T)new System.Runtime.Serialization.Json.DataContractJsonSerializer(typeof(T)).ReadObject(ms);
        }
        catch (System.Runtime.Serialization.SerializationException exc)
        {
            ms.Seek(0, SeekOrigin.Begin);
            StreamReader readStream = new StreamReader(ms, Encoding.UTF8);
            string data = readStream.ReadToEnd();
            exc.Data.Add("data", data);
            throw exc;
        }
        ms.Close();
        receiveStream.Close();
        response.Close();
        return result;
    }
    public static void SetRequestContex(this HttpWebRequest req)
    {
        SetRequestContex(req, "");
    }
    public static void SetRequestContex(this HttpWebRequest req, string body, params string[] headers)
    {
        //連線最大等待時間
        req.Timeout = 30000;
        req.Method = "POST";
        req.KeepAlive = false;
        //設定Header模擬瀏覽器
        req.UserAgent = "Mozilla/5.0 (Windows; U; Windows NT 6.0; zh-TW; rv:1.9.1.2) "
                  + "Gecko/20090729 Firefox/3.5.2 GTB5 (.NET CLR 3.5.30729)";
        req.Accept = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";

        req.Headers.Set("Accept-Encoding",
            "gzip,deflate,sdch");
        req.Headers.Set("Accept-Language",
            "zh-tw,en-us;q=0.7,en;q=0.3");
        req.Headers.Set("Accept-Charse",
            "Big5,utf-8;q=0.7,*;q=0.7");
        req.AutomaticDecompression = DecompressionMethods.GZip | DecompressionMethods.Deflate;

        foreach (var header in headers)
        {
            req.Headers.Add(header);
        }

        //如需傳送檔案,則需用multipart/form-data
        req.ContentType = "application/x-www-form-urlencoded";

        if (body != "" && body != null)
        {
            byte[] bytes = Encoding.UTF8.GetBytes(body);
            req.ContentLength = bytes.Length;

            Stream oStreamOut = req.GetRequestStream();
            oStreamOut.Write(bytes, 0, bytes.Length);
        }
    }
}
[System.Runtime.Serialization.DataContract]
public class OAuthTokenResult
{
    //{
    //    "access_token":"37a32978bed41eaa20241b5b4a52058c"
    //    ,"expires_in":3600
    //    ,"token_type":"bearer"
    //    ,"scope":"User.Mail,User.BasicInfo"
    //    ,"refresh_token":"2020daa00970d7f6957e088b88001640"
    //}
    [System.Runtime.Serialization.DataMember(Name = "access_token")]
    public string AccessToken { get; set; }

    [System.Runtime.Serialization.DataMember(Name = "scope")]
    public string Scope { get; set; }


}