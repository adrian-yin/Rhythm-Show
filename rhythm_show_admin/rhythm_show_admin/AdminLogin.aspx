<%@ Page Language="C#" AutoEventWireup="true" CodeFile="AdminLogin.aspx.cs" Inherits="AdminLogin" %>

<!DOCTYPE html>

<html lang="zh-hans">
<head runat="server">
    <meta charset="utf-8" />
    <title>管理后台登录</title>
    <link rel="stylesheet" href="./css/login_page.css" />
</head>
<body>
    <form runat="server">
        <div class="login-box">
            <h1 class="login-title">“炫·律”管理后台登录</h1>
            <div class="login-items">
                <div class="item">
                    <label>
                        邮箱：
                        <asp:TextBox CssClass="item-input" ID="EmailText" runat="server"></asp:TextBox>
                    </label>
                </div>
                <div class="item">
                    <label>
                        密码：
                        <asp:TextBox CssClass="item-input" ID="PasswordText" runat="server" TextMode="Password"></asp:TextBox>
                    </label>
                </div>
            </div>
            <asp:Button CssClass="login-button" ID="LoginButton" runat="server" Text="登录" OnClick="LoginButton_Click" />
        </div>
    </form>
</body>
</html>
