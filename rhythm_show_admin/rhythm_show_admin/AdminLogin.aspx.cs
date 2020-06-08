using System;
using System.Collections.Generic;
using System.Configuration;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.Security;

public partial class AdminLogin : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {

    }

    protected void LoginButton_Click(object sender, EventArgs e)
    {
        string email = this.EmailText.Text;
        // 密码加密
        string encodePassword = FormsAuthentication.HashPasswordForStoringInConfigFile(this.PasswordText.Text, "MD5");
 
        bool checkResult = Users.CheckAdminUser(email, encodePassword);

        if (checkResult)
        {
            // Session记录当前用户
            Session["UserId"] = Users.GetUserId(email).ToString();
            Session["UserRole"] = "1";

            Response.Redirect("./AdminUsers.aspx");
        } else
        {
            Response.Write("<script>alert('用户名或密码错误');</script>");
        }
    }
}