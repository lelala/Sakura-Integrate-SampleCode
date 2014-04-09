using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class Default : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        //這頁是測試偵錯時進入1Campus的快速網址，在正式上線時本頁應不需要存在

        string testAcc = "teacher";//允許"student"或者"teacher"，當網址帶著testacc參數時會直接已設定好的測試帳號進入系統
        string testapp = "" + System.Web.Configuration.WebConfigurationManager.AppSettings["EntryPoint"];

        Response.Redirect(
            "https://web2.ischool.com.tw/"
                + "?testacc=" + testAcc
                + "&testapp=" + HttpUtility.UrlEncodeUnicode(testapp)
        );
    }
}