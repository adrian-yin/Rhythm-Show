<%@ Page Language="C#" AutoEventWireup="true" CodeFile="AdminUsers.aspx.cs" Inherits="AdminUsers" %>

<!DOCTYPE html>

<html lang="zh-hans">
<head>
    <meta charset="utf-8" />
    <title>用户管理</title>
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
            <button class="adduser-button" onclick="window.location.href='./AddUser.aspx';window.event.returnValue=false;">新增用户</button>
            <asp:GridView CssClass="users-gridview" ID="UsersGridView" runat="server"
                AutoGenerateColumns="False"
                AllowPaging="True"
                GridLines="None"
                OnRowDeleting="UsersGridView_RowDeleting"
                OnRowEditing="UsersGridView_RowEditing"
                OnRowUpdating="UsersGridView_RowUpdating"
                OnRowCancelingEdit="UsersGridView_RowCancelingEdit"
                OnRowCommand="UsersGridView_RowCommand"
                PagerStyle-CssClass="pager"
                AlternatingRowStyle-CssClass="alternating-row"
            >
                <PagerSettings Visible="False" />
                <Columns>
                    <asp:BoundField DataField="Id" HeaderText="用户ID" ReadOnly="True" SortExpression="Id"/>
                    <asp:BoundField DataField="Email" HeaderText="电子邮箱" SortExpression="Email" />
                    <asp:TemplateField HeaderText="密码">
                        <ItemTemplate>
                            <asp:LinkButton ID="ResetPasswordButton" runat="server" CommandName="ResetPassword" CommandArgument='<%# Eval("Id") %>' OnClientClick="return confirm('确认要重置密码吗')">重置密码</asp:LinkButton>
                        </ItemTemplate>
                    </asp:TemplateField>
                    <asp:BoundField DataField="Nickname" HeaderText="昵称" SortExpression="Nickname" />
                    <asp:TemplateField HeaderText="性别">
                        <EditItemTemplate>
                            <asp:DropDownList ID="GenderDropDownList" runat="server">
                                <asp:ListItem Selected="True" Value="0">未知</asp:ListItem>
                                <asp:ListItem Value="2">女</asp:ListItem>
                                <asp:ListItem Value="1">男</asp:ListItem>
                            </asp:DropDownList>
                        </EditItemTemplate>
                        <ItemTemplate>
                            <asp:Label ID="GenderLabel" runat="server" Text='<%# GetGender(Convert.ToInt32(Eval("Gender").ToString())) %>'></asp:Label>
                        </ItemTemplate>
                    </asp:TemplateField>
                    <asp:BoundField DataField="Birthday" HeaderText="生日" SortExpression="Birthday" DataFormatString="{0:yyyy-MM-dd}" />
                    <asp:BoundField DataField="Note" HeaderText="个性签名" SortExpression="Note" />        
                    <asp:CommandField ShowEditButton="True" />
                    <asp:CommandField ShowDeleteButton="True" />   
                </Columns>
            </asp:GridView>
        </section>
    </form>
</body>
</html>
