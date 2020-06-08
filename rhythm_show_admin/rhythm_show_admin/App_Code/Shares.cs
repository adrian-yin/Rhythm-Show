using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Configuration;
using System.Data;
using System.Data.SqlClient;

/// <summary>
/// 提供操作Shares数据库的方法
/// </summary>
public class Shares
{
	// 获取所有分享
	public static DataSet GetAllShares()
	{
		string connectionString = ConfigurationManager.ConnectionStrings["adonetConnectionString"].ToString();
		SqlConnection connection = new SqlConnection(connectionString);
		connection.Open();

		string commandString = "SELECT * FROM Shares";
		SqlDataAdapter adapter = new SqlDataAdapter(commandString, connection);

		DataSet dataSet = new DataSet();
		adapter.Fill(dataSet);

		connection.Close();

		return dataSet;
	}

	// 按需查询
	public static DataSet GetSharesWithConditions(string type, string isOriginal, string isPublished)
	{
		string connectionString = ConfigurationManager.ConnectionStrings["adonetConnectionString"].ToString();
		SqlConnection connection = new SqlConnection(connectionString);
		connection.Open();

		string commandString = string.Format("SELECT * FROM Shares WHERE ({0}) AND ({1}) AND ({2})", type, isOriginal, isPublished);
		SqlDataAdapter adapter = new SqlDataAdapter(commandString, connection);

		DataSet dataSet = new DataSet();
		adapter.Fill(dataSet);

		connection.Close();

		return dataSet;
	}

	// 删除分享
	public static DataSet DeleteShare(int id)
	{
		string connectionString = ConfigurationManager.ConnectionStrings["adonetConnectionString"].ToString();
		SqlConnection connection = new SqlConnection(connectionString);
		connection.Open();

		string commandString = "DELETE FROM Shares WHERE Id=" + id.ToString();
		SqlDataAdapter adapter = new SqlDataAdapter(commandString, connection);

		DataSet dataSet = new DataSet();
		adapter.Fill(dataSet);

		connection.Close();

		return dataSet;
	}
}