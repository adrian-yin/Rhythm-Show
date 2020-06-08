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

public partial class AdminUsers : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        if (Session["UserId"] == null || Session["UserRole"].ToString() != "1")
        {
            Response.Redirect("./AdminLogin.aspx");
        }
        if (!IsPostBack)
        {
            FillGridView();
        }
    }

    private void FillGridView()
    {
        DataSet ds = Users.GetAllNormalUsers();
        UsersGridView.DataSource = ds;
        UsersGridView.DataBind();
    }

    protected void LogoutButton_Click(object sender, EventArgs e)
    {
        Session.Clear();
        Response.Redirect("./AdminLogin.aspx");
    }

    protected void UsersGridView_RowCommand(object sender, GridViewCommandEventArgs e)
    {
        if (e.CommandName == "ResetPassword")
        {
            int userId = Convert.ToInt32(e.CommandArgument);
            Users.ResetPassword(userId);
        }
    }

    protected void UsersGridView_RowUpdating(object sender, GridViewUpdateEventArgs e)
    {
        int userId = Convert.ToInt32(UsersGridView.Rows[e.RowIndex].Cells[0].Text);
        string email = ((TextBox)UsersGridView.Rows[e.RowIndex].Cells[1].Controls[0]).Text;
        string nickname = ((TextBox)UsersGridView.Rows[e.RowIndex].Cells[3].Controls[0]).Text;
        int gender = Convert.ToInt32(((DropDownList)UsersGridView.Rows[e.RowIndex].Cells[4].FindControl("GenderDropDownList")).SelectedValue);
        string birthday = ((TextBox)UsersGridView.Rows[e.RowIndex].Cells[5].Controls[0]).Text;
        string note = ((TextBox)UsersGridView.Rows[e.RowIndex].Cells[6].Controls[0]).Text;
        Users.UpdateUser(userId, email, nickname, gender, birthday, note);
        
        UsersGridView.EditIndex = -1;
        FillGridView();
    }

    protected void UsersGridView_RowDeleting(object sender, GridViewDeleteEventArgs e)
    {
        int userId = Convert.ToInt32(UsersGridView.Rows[e.RowIndex].Cells[0].Text);
        Users.DeleteUser(userId);

        FillGridView();
    }

    protected void UsersGridView_RowEditing(object sender, GridViewEditEventArgs e)
    {
        UsersGridView.EditIndex = e.NewEditIndex;
        FillGridView();
    }

    protected void UsersGridView_RowCancelingEdit(object sender, GridViewCancelEditEventArgs e)
    {
        UsersGridView.EditIndex = -1;
        FillGridView();
    }

    protected string GetGender(int genderNum)
    {
        if (genderNum == 1)
        {
            return "男";
        } else if (genderNum == 2)
        {
            return "女";
        } else
        {
            return "未知";
        }
    }
}
