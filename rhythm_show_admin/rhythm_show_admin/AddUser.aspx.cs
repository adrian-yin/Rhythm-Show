using System;
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;

public partial class AddUser : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        PasswordText.Attributes["value"] = PasswordText.Text;
        if (!IsPostBack)
        {
            BirthdayCalendar.TodaysDate = System.Convert.ToDateTime("2000/1/1");
        }
    }

    protected void LogoutButton_Click(object sender, EventArgs e)
    {
        Session.Clear();
        Response.Redirect("./AdminLogin.aspx");
    }

    protected void SubmitButton_Click(object sender, EventArgs e)
    {
        string email = this.EmailText.Text;
        string encodePassword = FormsAuthentication.HashPasswordForStoringInConfigFile(this.PasswordText.Text, "MD5");
        string nickname = this.NicknameText.Text;
        int gender = 0;
        if (this.GenderUnknown.Checked)
        {
            gender = 0;
        } else if (this.GenderFemale.Checked)
        {
            gender = 2;
        } else if (this.GenderMale.Checked)
        {
            gender = 1;
        }
        string birthday;
        try
        {
            birthday = this.BirthdayCalendar.SelectedDate.ToShortDateString();
        } catch
        {
            birthday = "1970-01-01";
        }
        string note = this.NoteText.Text;
        int role = 0;
        if (this.RoleNormal.Checked)
        {
            role = 0;
        } else if (this.RoleAdmin.Checked)
        {
            role = 1;
        }
        Users.InsertUser(email, encodePassword, nickname, gender, birthday, note, role);

        Response.Write("<script>alert('添加用户成功！')</script>");
        Response.Redirect("./AdminUsers.aspx");
    }
}
