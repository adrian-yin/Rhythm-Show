using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Data;

public partial class AdminShares : System.Web.UI.Page
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
        DataSet ds = Shares.GetAllShares();
        SharesGridView.DataSource = ds;
        SharesGridView.DataBind();
    }

    protected void LogoutButton_Click(object sender, EventArgs e)
    {
        Session.Clear();
        Response.Redirect("./AdminLogin.aspx");
    }

    protected void SharesGridView_RowDeleting(object sender, GridViewDeleteEventArgs e)
    {
        int userId = Convert.ToInt32(SharesGridView.Rows[e.RowIndex].Cells[0].Text);
        Users.DeleteUser(userId);

        FillGridView();
    }

    protected void ChooseChecked(object sender, EventArgs e)
    {
        string type, isOriginal, isPublished;
        
        if (this.TypeRecord.Checked)
        {
            type = "Type=0";
        } else if (this.TypeScore.Checked)
        {
            type = "Type=1";
        } else
        {
            type = "Type=0 OR Type=1";
        }

        if (this.IsOriginalTrue.Checked)
        {
            isOriginal = "IsOriginal=1";
        }
        else if (this.IsOriginalFalse.Checked)
        {
            isOriginal = "IsOriginal=0";
        }
        else
        {
            isOriginal = "IsOriginal=0 OR IsOriginal=1";
        }

        if (this.IsPublishedTrue.Checked)
        {
            isPublished = "IsPublished=1";
        }
        else if (this.IsPublishedFalse.Checked)
        {
            isPublished = "IsPublished=0";
        }
        else
        {
            isPublished = "IsPublished=0 OR IsPublished=1";
        }

        DataSet ds = Shares.GetSharesWithConditions(type, isOriginal, isPublished);
        SharesGridView.DataSource = ds;
        SharesGridView.DataBind();
    }
}