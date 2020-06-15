<%@ Page Language="C#" AutoEventWireup="true" CodeFile="AdminShares.aspx.cs" Inherits="AdminShares" %>

<!DOCTYPE html>

<html lang="zh-hans">
<head>
    <meta charset="utf-8" />
    <title>分享内容管理</title>
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
                <li onclick="window.location.href='./AdminUsers.aspx'">用户管理</li>
                <li class="active" onclick="window.location.href='./AdminShares.aspx'">分享内容管理</li>
            </ul>
        </nav>
        <section>
            <div class="choose-area">
                <div class="type-radio">
                    <asp:RadioButton ID="TypeAll" Text="不限" Checked="true" GroupName="Type" OnCheckedChanged="ChooseChecked" AutoPostBack="true" runat="server" />&nbsp;
                    <asp:RadioButton ID="TypeRecord" Text="录音" GroupName="Type" OnCheckedChanged="ChooseChecked" AutoPostBack="true" runat="server" />&nbsp;
                    <asp:RadioButton ID="TypeScore" Text="曲谱" GroupName="Type" OnCheckedChanged="ChooseChecked" AutoPostBack="true" runat="server" />
                </div>
                <div class="isoriginal-radio">
                    <asp:RadioButton ID="IsOriginalAll" Text="不限" Checked="true" GroupName="IsOriginal" OnCheckedChanged="ChooseChecked" AutoPostBack="true" runat="server" />&nbsp;
                    <asp:RadioButton ID="IsOriginalTrue" Text="原创" GroupName="IsOriginal" OnCheckedChanged="ChooseChecked" AutoPostBack="true" runat="server" />&nbsp;
                    <asp:RadioButton ID="IsOriginalFalse" Text="非原创" GroupName="IsOriginal" OnCheckedChanged="ChooseChecked" AutoPostBack="true" runat="server" />
                </div>
                <div class="ispublished-radio">
                    <asp:RadioButton ID="IsPublishedAll" Text="不限" Checked="true" GroupName="IsPublished" OnCheckedChanged="ChooseChecked" AutoPostBack="true" runat="server" />&nbsp;
                    <asp:RadioButton ID="IsPublishedTrue" Text="已发布" GroupName="IsPublished" OnCheckedChanged="ChooseChecked" AutoPostBack="true" runat="server" />&nbsp;
                    <asp:RadioButton ID="IsPublishedFalse" Text="未发布" GroupName="IsPublished" OnCheckedChanged="ChooseChecked" AutoPostBack="true" runat="server" />
                </div>
            </div>
            <asp:GridView CssClass="gridview" ID="SharesGridView" runat="server"
                AutoGenerateColumns="False"
                AllowPaging="False"
                GridLines="None"
                OnRowDeleting="SharesGridView_RowDeleting"
            >
                <PagerSettings Visible="False" />
                <Columns>
                    <asp:TemplateField HeaderText="播放">
                        <ItemTemplate>
                            <svg class="play-icon" style="cursor: pointer" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" width="32" height="32" onclick="playAudio(this)">
                                <path d="M514 114.3c-219.9 0-398.9 178.9-398.9 398.9 0.1 219.9 179 398.8 398.9 398.8 219.9 0 398.8-178.9 398.8-398.8S733.9 114.3 514 114.3z m173 421.9L437.1 680.5c-17.7 10.2-39.8-2.6-39.8-23V368.9c0-20.4 22.1-33.2 39.8-23L687 490.2c17.7 10.2 17.7 35.8 0 46z" fill="#60646d"></path>
                            </svg>
                            <audio id="audioToPlay" src='<%# "/resources/records/record_" + Eval("Id").ToString() + ".wav"%>' hidden="hidden" onended="changeIconColor(this)"></audio>
                        </ItemTemplate>
                    </asp:TemplateField>
                    <asp:BoundField DataField="Id" HeaderText="分享ID" SortExpression="Id"/>
                    <asp:BoundField DataField="UserId" HeaderText="用户ID" SortExpression="UserId"/>
                    <asp:BoundField DataField="Name" HeaderText="作品名" SortExpression="Name" />
                    <asp:BoundField DataField="Describe" HeaderText="作品描述" SortExpression="Discribe" />
                    <asp:TemplateField HeaderText="分享类型">
                        <ItemTemplate>
                            <asp:Label ID="TypeLabel" runat="server" Text='<%# Eval("Type").ToString() == "0" ? "录音" : "曲谱" %>'></asp:Label>
                        </ItemTemplate>
                    </asp:TemplateField>
                    <asp:TemplateField HeaderText="作品类型">
                        <ItemTemplate>
                            <asp:Label ID="IsOriginalLabel" runat="server" Text='<%# Eval("IsOriginal").ToString() == "0" ? "非原创" : "原创" %>'></asp:Label>
                        </ItemTemplate>
                    </asp:TemplateField>
                    <asp:TemplateField HeaderText="发布状态">
                        <ItemTemplate>
                            <asp:Label ID="IsPublishedLabel" runat="server" Text='<%# Eval("IsPublished").ToString() == "0" ? "未发布" : "已发布" %>'></asp:Label>
                        </ItemTemplate>
                    </asp:TemplateField>
                    <asp:BoundField DataField="PublishTime" HeaderText="发布时间" SortExpression="PublishTime" />
                    <asp:BoundField DataField="LikeNum" HeaderText="获赞数" SortExpression="LikeNum" />
                    <asp:BoundField DataField="CollectNum" HeaderText="收藏数" SortExpression="CollectNum" />
                    <asp:BoundField DataField="MusicScore" HeaderText="曲谱" SortExpression="MusicScore" /> 
                    <asp:CommandField ShowDeleteButton="True" />   
                </Columns>
            </asp:GridView>
        </section>
    </form>
    <script>
        function playAudio(element) {
            let audio = element.nextElementSibling;
            if (audio.paused) {
                var playPromise = audio.play();
                if (playPromise !== undefined) {
                    playPromise.then(_ => {
                        audio.play();
                        element.style.backgroundColor = '#fff3cf';
                    }).catch(error => {
                        alert("音频不存在！");
                        element.style.backgroundColor = '#ffffff00';
                    });
                }
            } else {
                audio.pause();
                element.style.backgroundColor = '#ffffff00';
            }
        }

        function changeIconColor(element) {
            let icon = element.previousElementSibling;
            icon.style.backgroundColor = '#ffffff00'
        }
    </script>
</body>
</html>
