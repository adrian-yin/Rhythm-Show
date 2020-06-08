using System;
using System.Data;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using System.Data.SqlClient;
using System.ComponentModel.Design;

/// <summary>
/// 提供Users数据表的操作方法
/// </summary>
public class Users
{

	// 获取所有普通用户
	public static DataSet GetAllNormalUsers()
    {
		string connectionString = ConfigurationManager.ConnectionStrings["adonetConnectionString"].ToString();
		SqlConnection connection = new SqlConnection(connectionString);
		connection.Open();

		string commandString = "SELECT * FROM Users WHERE Role=0";
		SqlDataAdapter adapter = new SqlDataAdapter(commandString, connection);

		DataSet dataSet = new DataSet();
		adapter.Fill(dataSet);

		connection.Close();

		return dataSet;
	}

	// 新增用户
	public static DataSet InsertUser(string email, string password, string nickname="", int gender=0, string date="00-00-00", string note="", int role=0)
    {
		string connectionString = ConfigurationManager.ConnectionStrings["adonetConnectionString"].ToString();
		SqlConnection connection = new SqlConnection(connectionString);
		connection.Open();

		string commandString = string.Format("INSERT INTO Users(Email, Password, Nickname, Gender, Birthday, Note, Role) " +
			"VALUES('{0}', '{1}', '{2}', {3}, '{4}', '{5}', {6})", email, password, nickname, gender.ToString(), date, note, role.ToString());
		SqlDataAdapter adapter = new SqlDataAdapter(commandString, connection);

		DataSet dataSet = new DataSet();
		adapter.Fill(dataSet);

		connection.Close();

		return dataSet;
	}

	// 修改用户
	public static DataSet UpdateUser(int id, string email, string nickname = "", int gender = 0, string date = "00-00-00", string note = "")
	{
		string connectionString = ConfigurationManager.ConnectionStrings["adonetConnectionString"].ToString();
		SqlConnection connection = new SqlConnection(connectionString);
		connection.Open();

		string commandString = string.Format("UPDATE Users SET Email='{0}', Nickname='{1}', Gender={2}, Birthday='{3}', Note='{4}' WHERE Id={5}",
			email, nickname, gender.ToString(), date, note, id.ToString());
		SqlDataAdapter adapter = new SqlDataAdapter(commandString, connection);

		DataSet dataSet = new DataSet();
		adapter.Fill(dataSet);

		connection.Close();

		return dataSet;
	}

	// 删除用户
	public static DataSet DeleteUser(int id)
	{
		string connectionString = ConfigurationManager.ConnectionStrings["adonetConnectionString"].ToString();
		SqlConnection connection = new SqlConnection(connectionString);
		connection.Open();

		string commandString = "DELETE FROM Users WHERE Id=" + id.ToString();
		SqlDataAdapter adapter = new SqlDataAdapter(commandString, connection);

		DataSet dataSet = new DataSet();
		adapter.Fill(dataSet);

		connection.Close();

		return dataSet;
	}

	// 检查管理员用户账号密码
	public static bool CheckAdminUser(string email, string password)
	{
		string connectionString = ConfigurationManager.ConnectionStrings["adonetConnectionString"].ToString();
		SqlConnection connection = new SqlConnection(connectionString);
		connection.Open();

		string commandString = "SELECT COUNT(*) FROM Users WHERE Email='" + email + "' AND Password='" + password + "'" + " AND Role=1";
		SqlCommand command = new SqlCommand(commandString, connection);
		int count = Convert.ToInt32(command.ExecuteScalar());

		connection.Close();

		if (count == 1)
        {
			return true;
        } else
        {
			return false;
        }
	}

	// 由邮箱获取用户id
	public static int GetUserId(string email)
    {
		string connectionString = ConfigurationManager.ConnectionStrings["adonetConnectionString"].ToString();
		SqlConnection connection = new SqlConnection(connectionString);
		connection.Open();

		string commandString = "SELECT Id FROM Users WHERE Email='" + email + "'";
		SqlCommand command = new SqlCommand(commandString, connection);
		int userId = Convert.ToInt32(command.ExecuteScalar());

		connection.Close();

		return userId;
	}

	// 重置密码为000000
	public static string ResetPassword(int userId)
    {
		string connectionString = ConfigurationManager.ConnectionStrings["adonetConnectionString"].ToString();
		SqlConnection connection = new SqlConnection(connectionString);
		connection.Open();

		string encodePassword = FormsAuthentication.HashPasswordForStoringInConfigFile("000000", "MD5");
		string commandString = string.Format("UPDATE USERS SET Password='{0}' WHERE Id={1}", encodePassword, userId);
		SqlCommand command = new SqlCommand(commandString, connection);
		command.ExecuteNonQuery();

		connection.Close();

		return "success";
	}
}
