<%@ Page Language="C#" AutoEventWireup="true" CodeFile="AddUser.aspx.cs" Inherits="AddUser" %>

<!DOCTYPE html>

<html lang="zh-hans">
<head>
    <meta charset="utf-8" />
    <title>新增用户</title>
    <link rel="stylesheet" href="./css/admin_pages.css" />
</head>
<body>
    <form runat="server">
        <header runat="server">
            <h1>“炫·律”管理后台</h1>
            <div class="user-name">
                欢迎您，管理员
                <asp:Button class="logout-button" ID="LogoutButton" runat="server" OnClick="LogoutButton_Click" Text="退出登录" />
            </div>
        </header>
        <nav>
            <ul>
                <li class="active" onclick="window.location.href='./AdminUsers.aspx'">用户管理</li>
                <li onclick="window.location.href='./AdminShares.aspx'">分享内容管理</li>
            </ul>
        </nav>
        <section>
            <button class="adduser-button" onclick="window.location.href='./AdminUsers.aspx';window.event.returnValue=false;">返回</button>
            <div class="adduser-form">
                <label class="item">
                    邮箱：
                    <asp:TextBox CssClass="item-input" ID="EmailText" runat="server"></asp:TextBox>
                </label>
                <label class="item">
                    密码：
                    <asp:TextBox CssClass="item-input" ID="PasswordText" runat="server" TextMode="Password"></asp:TextBox>
                </label>
                <label class="item">
                    昵称：
                    <asp:TextBox CssClass="item-input" ID="NicknameText" runat="server"></asp:TextBox>
                </label>
                <label class="item">
                    性别：
                    <span class="item-input">
                        <asp:RadioButton ID="GenderUnknown" Text="未知" GroupName="Gender" Checked="true" runat="server" />&nbsp;
                        <asp:RadioButton ID="GenderFemale" Text="女" GroupName="Gender" runat="server" />&nbsp;
                        <asp:RadioButton ID="GenderMale" Text="男" GroupName="Gender" runat="server" />
                    </span>
                </label>
                <label class="item">
                    生日：
                    <span class="calendar">
                        <asp:Calendar ID="BirthdayCalendar" runat="server"></asp:Calendar>
                    </span>
                </label>
                <label class="item under-calendar">
                    个性签名：
                    <asp:TextBox CssClass="item-input" ID="NoteText" runat="server"></asp:TextBox>
                </label>
                <label class="item under-calendar">
                    角色类型：
                    <span class="item-input">
                        <asp:RadioButton ID="RoleNormal" Text="普通用户" GroupName="Role" Checked="true" runat="server" />&nbsp;
                        <asp:RadioButton ID="RoleAdmin" Text="管理员" GroupName="Role" runat="server" />
                    </span>
                </label>
                <div class="button-area under-calendar">
                    <asp:Button CssClass="submit-button" ID="SubmitButton" runat="server" Text="提交" OnClick="SubmitButton_Click" />
                </div>
            </div>
        </section>
    </form>
</body>
</html>
