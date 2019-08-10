
GO
CREATE TABLE [dbo].[transportation](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ddid] [nvarchar](50) NOT NULL,
	[fyid] [nvarchar](50) NOT NULL,
	[invoice] [varchar](50) NOT NULL,
	[subscribe] [varchar](50) NOT NULL,
	[coname] [varchar](100) NOT NULL,
	[trans_com] [varchar](100) NULL,
	[aimport] [varchar](350) NULL,
	[fy_num] [nvarchar](50) NULL,
	[fy_yf] [decimal](18, 2) NULL,
	[fy_bf] [decimal](18, 2) NULL,
	[fy_js] [nvarchar](50) NULL,
	[fy_sz] [nvarchar](50) NULL,
	[transportation] [varchar](90) NULL,
	[mode] [nvarchar](50) NULL,
	[datet] [int] NULL,
	[mbdate] [varchar](50) NULL,
	[sjdate] [varchar](50) NULL,
	[linkman] [varchar](50) NULL,
	[linktel] [varchar](50) NULL,
	[sendcompany] [nvarchar](100) NULL,
	[slinkman] [nvarchar](50) NULL,
	[slinktel] [nvarchar](50) NULL,
	[sate] [varchar](50) NULL,
	[sale_man] [char](15) NULL,
	[sale_dept] [char](30) NULL,
	[deptjb] [char](10) NULL,
	[remark] [nvarchar](350) NULL,
	[co_number] [char](30) NULL,
	[tr_types] [char](15) NULL,
	[fy_date] [char](15) NULL,
	[yf_types] [char](50) NULL,
	[yf_money] [decimal](18, 2) NULL,
	[in_no] [int] NULL,
	[tr_print] [char](10) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[trade_policy]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[trade_policy](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[number] [char](10) NULL,
	[rg_datetime] [datetime] NULL,
	[man] [char](10) NULL,
	[dept] [char](20) NULL,
	[deptjb] [char](10) NULL,
	[trade_subject] [nvarchar](140) NULL,
	[trade_name] [nvarchar](100) NULL,
	[trade_content] [ntext] NULL,
	[bring_date] [datetime] NULL,
	[dis_date] [datetime] NULL,
	[trade_extent] [nvarchar](300) NULL,
	[trade_source] [nvarchar](100) NULL,
	[spman] [char](10) NULL,
	[states] [char](10) NULL,
	[spyj] [nvarchar](200) NULL,
	[in_no] [int] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[total_pro]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[total_pro](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[pro_id] [nvarchar](50) NOT NULL,
	[pro_model] [char](80) NOT NULL,
	[pro_name] [nvarchar](80) NULL,
	[pro_num] [int] NULL,
	[pro_unit] [char](10) NULL,
	[pro_date] [nvarchar](50) NULL,
	[pro_remark] [nvarchar](100) NULL,
	[wid] [char](10) NULL,
	[sid] [char](10) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[pd]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[pd](
	[pro_model] [varchar](500) NULL,
	[a] [int] NULL,
	[b] [int] NULL,
	[c] [int] NULL,
	[d] [int] NULL,
	[e] [int] NULL,
	[f] [int] NULL,
	[g] [int] NULL,
	[h] [int] NULL,
	[i] [int] NULL,
	[pro_addr] [varchar](50) NULL,
	[status] [int] NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[payway_cg]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[payway_cg](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[payment] [varchar](50) NOT NULL,
	[remark] [varchar](300) NULL,
	[pay_date] [datetime] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[schdtable]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[schdtable](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[hdztname] [nvarchar](60) NOT NULL,
	[hdlx] [nvarchar](20) NOT NULL,
	[dd] [nvarchar](20) NOT NULL,
	[yzzj] [nvarchar](20) NOT NULL,
	[md] [ntext] NOT NULL,
	[hdnr] [nvarchar](60) NOT NULL,
	[ggxc] [ntext] NOT NULL,
	[zj] [ntext] NOT NULL,
	[sj] [nvarchar](20) NOT NULL,
	[fzr] [nvarchar](20) NOT NULL,
	[cyry] [nvarchar](20) NOT NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[subscribe]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[subscribe](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[number] [varchar](50) NOT NULL,
	[man] [varchar](30) NOT NULL,
	[subck] [nvarchar](50) NULL,
	[sub] [nvarchar](100) NULL,
	[coname] [varchar](100) NOT NULL,
	[yj] [decimal](18, 0) NOT NULL,
	[money] [char](10) NOT NULL,
	[item] [varchar](150) NULL,
	[mode] [varchar](150) NULL,
	[source] [int] NOT NULL,
	[trade] [varchar](50) NULL,
	[habitus] [varchar](10) NOT NULL,
	[datetime] [datetime] NOT NULL,
	[senddate] [nvarchar](100) NULL,
	[tbyq] [ntext] NULL,
	[remarks] [ntext] NULL,
	[state] [varchar](10) NOT NULL,
	[spman] [varchar](50) NULL,
	[spdate] [nvarchar](50) NULL,
	[spyj] [nvarchar](300) NULL,
	[fif] [char](10) NULL,
	[cwman] [char](15) NULL,
	[cwdate] [nvarchar](50) NULL,
	[cwyj] [varchar](300) NULL,
	[dept] [char](60) NULL,
	[deptjb] [char](30) NULL,
	[tr] [nvarchar](50) NULL,
	[tr_addr] [nvarchar](300) NULL,
	[tr_man] [nvarchar](50) NULL,
	[tr_tel] [nvarchar](80) NULL,
	[yf_types] [nvarchar](50) NULL,
	[yf_money] [decimal](18, 2) NULL,
	[fy_number] [char](20) NULL,
	[ware_remark] [nvarchar](300) NULL,
	[in_no] [int] NULL,
	[p_states] [nvarchar](50) NULL,
	[sub_time] [char](30) NULL,
	[s_check] [char](10) NULL,
	[ware_man] [char](30) NULL,
	[cg_man] [char](15) NULL,
	[send_date] [char](15) NULL,
	[other_fy] [decimal](18, 2) NULL,
	[custno] [varchar](50) NULL,
	[del_remark] [ntext] NULL,
	[return_remark] [ntext] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[stotal_pro]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[stotal_pro](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[pro_id] [nvarchar](50) NOT NULL,
	[pro_model] [char](80) NOT NULL,
	[pro_name] [nvarchar](80) NULL,
	[pro_num] [int] NULL,
	[pro_unit] [char](10) NULL,
	[pro_date] [nvarchar](50) NULL,
	[pro_remark] [nvarchar](100) NULL,
	[wid] [char](10) NULL,
	[sid] [char](10) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[startport]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[startport](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[startportname] [varchar](80) NULL,
	[remark] [varchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[spbmsp]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[spbmsp](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[dept] [char](30) NULL,
	[role] [char](15) NULL,
	[dd_man] [char](15) NOT NULL,
	[fif] [char](10) NOT NULL,
	[fspman] [varchar](50) NOT NULL,
	[firspif] [char](10) NULL,
	[firspman] [char](20) NULL,
	[remark] [varchar](350) NULL,
	[dd_date] [datetime] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[soutview]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[soutview](
	[number] [varchar](50) NOT NULL,
	[coname] [varchar](100) NOT NULL,
	[yj] [numeric](18, 0) NOT NULL,
	[datetime] [datetime] NOT NULL,
	[selljg] [numeric](18, 2) NULL,
	[money] [varchar](50) NULL,
	[epro] [varchar](150) NOT NULL,
	[num] [int] NOT NULL,
	[unit] [varchar](50) NULL,
	[salejg] [numeric](18, 2) NOT NULL,
	[pricehb] [nvarchar](30) NOT NULL,
	[rale_types] [varchar](50) NULL,
	[rale] [numeric](10, 2) NULL,
	[supplier] [varchar](150) NULL,
	[fypronum] [char](10) NULL,
	[man] [varchar](30) NOT NULL,
	[habitus] [varchar](10) NOT NULL,
	[state] [varchar](10) NOT NULL,
	[deptjb] [char](30) NULL,
	[dept] [char](60) NULL,
	[p_states] [char](15) NULL,
	[wid] [char](10) NULL,
	[fy_states] [char](10) NULL,
	[s_num] [int] NULL,
	[s_c_num] [int] NULL,
	[s_tr_date] [char](15) NULL,
	[id] [int] NOT NULL,
	[s_check] [char](10) NULL,
	[senddate] [nvarchar](100) NULL,
	[pro_model] [char](80) NOT NULL,
	[pro_name] [nvarchar](80) NULL,
	[pro_num] [int] NULL,
	[sid] [char](10) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[warehouse_test]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[warehouse_test](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[pro_model] [varchar](60) NOT NULL,
	[pro_gg] [nvarchar](50) NULL,
	[pro_name] [nvarchar](80) NULL,
	[pro_number] [varchar](60) NULL,
	[pro_type] [varchar](70) NULL,
	[saleprice] [decimal](18, 2) NULL,
	[price_hb] [char](10) NULL,
	[pro_s_num] [decimal](10, 0) NULL,
	[pro_num] [decimal](10, 0) NULL,
	[pro_unit] [varchar](20) NULL,
	[pro_price] [decimal](18, 2) NULL,
	[pro_supplier] [varchar](50) NULL,
	[pro_addr] [varchar](60) NULL,
	[pro_remark] [text] NULL,
	[sjnum] [int] NULL,
	[yqnum] [int] NULL,
	[yqdate] [nvarchar](50) NULL,
	[pro_secid] [char](10) NULL,
	[pro_sup_number] [char](30) NULL,
	[pro_min_num] [int] NULL,
	[pro_max_num] [int] NULL,
	[sale_states] [char](30) NULL,
	[sale_min_price] [decimal](18, 2) NULL,
	[sale_max_price] [decimal](18, 2) NULL,
	[pro_date] [char](30) NULL,
	[pro_man] [char](10) NULL,
	[pro_ms] [text] NULL,
	[pro_jstx] [text] NULL,
	[pro_yyfw] [text] NULL,
	[pin] [int] NULL,
	[js_price] [decimal](18, 2) NULL,
	[zk_price] [decimal](18, 2) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[warehouse_in_out_log]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[warehouse_in_out_log](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[number] [varchar](50) NOT NULL,
	[orgin_num] [int] NOT NULL,
	[change_num] [int] NOT NULL,
	[final_num] [int] NOT NULL,
	[operateTime] [datetime] NOT NULL,
	[operater] [varchar](50) NOT NULL,
	[wid] [int] NOT NULL,
	[rk_id] [int] NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[warehouse]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[warehouse](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[pro_model] [varchar](90) NOT NULL,
	[pro_gg] [nvarchar](50) NULL,
	[pro_name] [nvarchar](80) NULL,
	[pro_number] [varchar](60) NULL,
	[pro_type] [varchar](70) NULL,
	[saleprice] [decimal](18, 6) NULL,
	[price_hb] [char](10) NULL,
	[pro_s_num] [decimal](10, 0) NULL,
	[pro_num] [decimal](10, 0) NULL,
	[pro_unit] [varchar](20) NULL,
	[pro_price] [decimal](18, 6) NULL,
	[pro_supplier] [varchar](90) NULL,
	[pro_addr] [varchar](60) NULL,
	[pro_remark] [varchar](400) NULL,
	[sjnum] [nvarchar](50) NULL,
	[yqnum] [nvarchar](50) NULL,
	[yqdate] [nvarchar](90) NULL,
	[pro_secid] [nvarchar](50) NULL,
	[pro_sup_number] [char](30) NULL,
	[pro_min_num] [int] NULL,
	[pro_max_num] [int] NULL,
	[sale_states] [char](30) NULL,
	[sale_min_price] [nvarchar](50) NULL,
	[sale_max_price] [nvarchar](50) NULL,
	[pro_date] [char](30) NULL,
	[pro_man] [char](10) NULL,
	[pro_ms] [ntext] NULL,
	[pro_jstx] [ntext] NULL,
	[pro_yyfw] [ntext] NULL,
	[pin] [int] NULL,
	[js_price] [decimal](18, 2) NULL,
	[zk_price] [decimal](18, 2) NULL,
	[th_number] [varchar](50) NULL,
	[pro_weight_unit] [varchar](50) NULL,
	[batch_no] [varchar](50) NULL,
	[finishStoking] [int] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[wage_menber]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[wage_menber](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[wage_name] [nvarchar](50) NOT NULL,
	[wage_year] [char](10) NULL,
	[wage_month] [char](10) NULL,
	[wage_date] [datetime] NOT NULL,
	[wage_rgman] [nvarchar](50) NULL,
	[wage_m_dates] [decimal](18, 1) NULL,
	[wage_date_price] [decimal](18, 2) NULL,
	[wage_month_dates] [decimal](18, 2) NULL,
	[wage_awork_price] [decimal](18, 0) NULL,
	[wage_awork_h] [decimal](18, 2) NULL,
	[wage_zf_price] [decimal](18, 0) NULL,
	[wage_jt_price] [decimal](18, 0) NULL,
	[wage_wc_price] [decimal](18, 0) NULL,
	[wage_sb_price] [decimal](18, 0) NULL,
	[wage_zdy_sub] [nvarchar](50) NULL,
	[wage_zdy_price] [decimal](18, 0) NULL,
	[wage_zdy_qy] [char](10) NULL,
	[wage_zdy_zj] [char](10) NULL,
	[wage_zdy_sub2] [nvarchar](50) NULL,
	[wage_zdy_price2] [decimal](18, 0) NULL,
	[wage_zdy_qy2] [char](10) NULL,
	[wage_zdy_zj2] [char](10) NULL,
	[wage_zdy_sub3] [nvarchar](50) NULL,
	[wage_zdy_price3] [decimal](18, 0) NULL,
	[wage_zdy_qy3] [char](10) NULL,
	[wage_zdy_zj3] [char](10) NULL,
	[wage_zdy_sub4] [nvarchar](50) NULL,
	[wage_zdy_price4] [decimal](18, 0) NULL,
	[wage_zdy_qy4] [char](10) NULL,
	[wage_zdy_zj4] [char](10) NULL,
	[wage_zdy_sub5] [nvarchar](50) NULL,
	[wage_zdy_price5] [decimal](18, 0) NULL,
	[wage_zdy_qy5] [char](10) NULL,
	[wage_zdy_zj5] [char](10) NULL,
	[remark] [ntext] NULL,
	[dept] [nvarchar](50) NULL,
	[deptjb] [char](10) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[wage_mb]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[wage_mb](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[wage_name] [nvarchar](50) NOT NULL,
	[wage_date] [datetime] NOT NULL,
	[wage_rgman] [nvarchar](50) NULL,
	[wage_m_dates] [decimal](18, 1) NULL,
	[wage_date_price] [decimal](18, 2) NULL,
	[wage_awork_price] [decimal](18, 0) NULL,
	[wage_zf_price] [decimal](18, 0) NULL,
	[wage_jt_price] [decimal](18, 0) NULL,
	[wage_wc_price] [decimal](18, 0) NULL,
	[wage_sb_price] [decimal](18, 0) NULL,
	[wage_zdy_sub] [nvarchar](50) NULL,
	[wage_zdy_price] [decimal](18, 0) NULL,
	[wage_zdy_qy] [char](10) NULL,
	[wage_zdy_zj] [char](10) NULL,
	[wage_zdy_sub2] [nvarchar](50) NULL,
	[wage_zdy_price2] [decimal](18, 0) NULL,
	[wage_zdy_qy2] [char](10) NULL,
	[wage_zdy_zj2] [char](10) NULL,
	[wage_zdy_sub3] [nvarchar](50) NULL,
	[wage_zdy_price3] [decimal](18, 0) NULL,
	[wage_zdy_qy3] [char](10) NULL,
	[wage_zdy_zj3] [char](10) NULL,
	[wage_zdy_sub4] [nvarchar](50) NULL,
	[wage_zdy_price4] [decimal](18, 0) NULL,
	[wage_zdy_qy4] [char](10) NULL,
	[wage_zdy_zj4] [char](10) NULL,
	[wage_zdy_sub5] [nvarchar](50) NULL,
	[wage_zdy_price5] [decimal](18, 0) NULL,
	[wage_zdy_qy5] [char](10) NULL,
	[wage_zdy_zj5] [char](10) NULL,
	[remark] [ntext] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[wage]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[wage](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](50) NULL,
	[wagedate] [nvarchar](50) NULL,
	[wagemonth] [nvarchar](50) NULL,
	[jbwage] [nvarchar](50) NULL,
	[gwwage] [nvarchar](50) NULL,
	[zfbt] [nvarchar](50) NULL,
	[wcbt] [nvarchar](50) NULL,
	[halfjj] [nvarchar](50) NULL,
	[nzjj] [nvarchar](50) NULL,
	[shbx] [nvarchar](50) NULL,
	[daglf] [nvarchar](50) NULL,
	[sfwage] [nvarchar](50) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[wxtable]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[wxtable](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[wxid] [nvarchar](30) NULL,
	[sales_man] [varchar](50) NULL,
	[coname] [varchar](150) NULL,
	[coaddr] [nvarchar](150) NULL,
	[cotel] [nvarchar](50) NULL,
	[coman] [char](20) NULL,
	[cofax] [char](20) NULL,
	[copost] [nvarchar](50) NULL,
	[man] [varchar](50) NULL,
	[wxman] [varchar](50) NULL,
	[remarks] [ntext] NULL,
	[f_states] [nvarchar](50) NULL,
	[s_states] [nvarchar](50) NULL,
	[t_states] [nvarchar](50) NULL,
	[state] [char](10) NULL,
	[in_no] [int] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[wx_pro]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[wx_pro](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[wxid] [char](10) NULL,
	[pro_model] [nvarchar](90) NULL,
	[pro_number] [nvarchar](50) NULL,
	[pro_gzxx] [nvarchar](300) NULL,
	[pro_clfs] [char](10) NULL,
	[pro_yf] [char](10) NULL,
	[pro_tr_types] [char](20) NULL,
	[pro_num] [int] NULL,
	[pro_unit] [char](10) NULL,
	[pro_gzyy] [nvarchar](300) NULL,
	[pro_wxjl] [nvarchar](20) NULL,
	[pro_fy] [decimal](18, 2) NULL,
	[pro_fyzf] [char](15) NULL,
	[wid] [char](10) NULL,
	[pro_sk] [decimal](18, 2) NULL,
	[pro_s_date] [char](30) NULL,
	[pro_zt] [nvarchar](50) NULL,
	[pro_rg_date] [datetime] NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[workreport_r]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[workreport_r](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[w_menber] [nvarchar](25) NOT NULL,
	[w_datetime] [datetime] NULL,
	[w_man] [nvarchar](15) NULL,
	[w_dept] [varchar](50) NULL,
	[w_deptjb] [char](10) NULL,
	[w_coname] [nvarchar](90) NULL,
	[w_xmid] [char](10) NULL,
	[w_programer] [nvarchar](90) NULL,
	[w_remark] [nvarchar](100) NULL,
	[w_coname1] [nvarchar](90) NULL,
	[w_xmid1] [char](10) NULL,
	[w_programer1] [nvarchar](90) NULL,
	[w_remark1] [nvarchar](100) NULL,
	[w_coname2] [nvarchar](90) NULL,
	[w_xmid2] [char](10) NULL,
	[w_programer2] [nvarchar](90) NULL,
	[w_remark2] [nvarchar](100) NULL,
	[w_coname3] [nvarchar](90) NULL,
	[w_xmid3] [char](10) NULL,
	[w_programer3] [nvarchar](90) NULL,
	[w_remark3] [nvarchar](100) NULL,
	[w_coname4] [nvarchar](90) NULL,
	[w_xmid4] [char](10) NULL,
	[w_programer4] [nvarchar](90) NULL,
	[w_remark4] [nvarchar](100) NULL,
	[w_coname5] [nvarchar](90) NULL,
	[w_xmid5] [char](10) NULL,
	[w_programer5] [nvarchar](90) NULL,
	[w_remark5] [nvarchar](100) NULL,
	[w_coname6] [nvarchar](90) NULL,
	[w_xmid6] [char](10) NULL,
	[w_programer6] [nvarchar](90) NULL,
	[w_remark6] [nvarchar](100) NULL,
	[w_coname7] [nvarchar](90) NULL,
	[w_xmid7] [char](10) NULL,
	[w_programer7] [nvarchar](90) NULL,
	[w_remark7] [nvarchar](100) NULL,
	[w_coname8] [nvarchar](90) NULL,
	[w_xmid8] [char](10) NULL,
	[w_programer8] [nvarchar](90) NULL,
	[w_remark8] [nvarchar](100) NULL,
	[w_coname9] [nvarchar](90) NULL,
	[w_xmid9] [char](10) NULL,
	[w_programer9] [nvarchar](90) NULL,
	[w_remark9] [nvarchar](100) NULL,
	[w_jh] [ntext] NULL,
	[w_spman] [char](20) NULL,
	[w_states] [char](20) NULL,
	[w_spyj] [nvarchar](200) NULL,
	[in_no] [int] NULL,
	[m_ps] [nvarchar](350) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[workreport]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[workreport](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[w_menber] [nvarchar](25) NOT NULL,
	[w_datetime] [datetime] NULL,
	[w_man] [nvarchar](15) NULL,
	[w_dept] [varchar](50) NULL,
	[w_deptjb] [char](10) NULL,
	[w_saleje] [decimal](18, 2) NULL,
	[w_coname] [varchar](500) NULL,
	[w_product] [nvarchar](500) NULL,
	[sdate] [char](10) NULL,
	[edate] [char](10) NULL,
	[w_gz] [nvarchar](200) NULL,
	[w_remark] [nvarchar](100) NULL,
	[w_coname2] [varchar](100) NULL,
	[w_product2] [nvarchar](100) NULL,
	[sdate2] [char](10) NULL,
	[edate2] [char](10) NULL,
	[w_gz2] [nvarchar](200) NULL,
	[w_remark2] [nvarchar](100) NULL,
	[w_coname3] [varchar](100) NULL,
	[w_product3] [nvarchar](100) NULL,
	[sdate3] [char](10) NULL,
	[edate3] [char](10) NULL,
	[w_gz3] [nvarchar](200) NULL,
	[w_remark3] [nvarchar](100) NULL,
	[w_coname4] [varchar](100) NULL,
	[w_product4] [nvarchar](100) NULL,
	[sdate4] [char](10) NULL,
	[edate4] [char](10) NULL,
	[w_gz4] [nvarchar](200) NULL,
	[w_remark4] [nvarchar](100) NULL,
	[w_coname5] [varchar](100) NULL,
	[w_product5] [nvarchar](100) NULL,
	[sdate5] [char](10) NULL,
	[edate5] [char](10) NULL,
	[w_gz5] [nvarchar](200) NULL,
	[w_remark5] [nvarchar](100) NULL,
	[zd_gz1] [nvarchar](200) NULL,
	[fdate1] [char](10) NULL,
	[f_gz1] [nvarchar](50) NULL,
	[w_remark6] [nvarchar](150) NULL,
	[zd_gz2] [nvarchar](200) NULL,
	[fdate2] [char](10) NULL,
	[f_gz2] [nvarchar](50) NULL,
	[w_remark7] [nvarchar](150) NULL,
	[zd_gz3] [nvarchar](200) NULL,
	[fdate3] [char](10) NULL,
	[f_gz3] [nvarchar](50) NULL,
	[w_remark8] [nvarchar](150) NULL,
	[zd_gz4] [nvarchar](200) NULL,
	[fdate4] [char](10) NULL,
	[f_gz4] [nvarchar](50) NULL,
	[w_remark9] [nvarchar](150) NULL,
	[zd_gz5] [nvarchar](200) NULL,
	[fdate5] [char](10) NULL,
	[f_gz5] [nvarchar](50) NULL,
	[w_remark10] [nvarchar](150) NULL,
	[w_jh] [text] NULL,
	[w_spman] [char](20) NULL,
	[w_states] [char](20) NULL,
	[w_spyj] [nvarchar](200) NULL,
	[in_no] [int] NULL,
	[m_ps] [nvarchar](350) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[workm]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[workm](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[rwnumber] [nvarchar](20) NULL,
	[rwdate] [datetime] NULL,
	[fdate] [datetime] NULL,
	[rwcontent] [nvarchar](80) NULL,
	[rwnr] [nvarchar](500) NULL,
	[rwzman] [nvarchar](30) NULL,
	[rwman] [nvarchar](80) NULL,
	[rwgman] [nvarchar](120) NULL,
	[man] [nvarchar](50) NULL,
	[remark] [ntext] NULL,
	[states] [nvarchar](25) NULL,
	[sfdate] [datetime] NULL,
	[rwjg] [nvarchar](300) NULL,
	[dept] [nvarchar](50) NULL,
	[deptjb] [nvarchar](50) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[work_report_rm]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[work_report_rm](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[w_menber] [nvarchar](25) NULL,
	[w_datetime] [datetime] NULL,
	[w_man] [nvarchar](15) NULL,
	[w_dept] [varchar](50) NULL,
	[w_deptjb] [char](10) NULL,
	[w_year] [char](4) NULL,
	[w_month] [char](4) NULL,
	[w_programer] [ntext] NULL,
	[w_progj] [ntext] NULL,
	[w_hjcs] [nvarchar](500) NULL,
	[w_jh_gzsx] [nvarchar](400) NULL,
	[w_jh_gznr] [nvarchar](450) NULL,
	[w_jh_gzmb] [nvarchar](350) NULL,
	[w_jh_lscs] [nvarchar](450) NULL,
	[w_jh] [ntext] NULL,
	[w_spman] [char](20) NULL,
	[w_states] [char](20) NULL,
	[w_spyj] [nvarchar](200) NULL,
	[in_no] [int] NULL,
	[m_ps] [nvarchar](350) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[work_report_rdm]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[work_report_rdm](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[w_menber] [nvarchar](25) NOT NULL,
	[w_datetime] [datetime] NULL,
	[w_man] [nvarchar](15) NULL,
	[w_dept] [varchar](50) NULL,
	[w_deptjb] [char](10) NULL,
	[w_a_coname] [ntext] NULL,
	[w_a_qt] [ntext] NULL,
	[w_a_remark] [nvarchar](450) NULL,
	[w_f_coname] [ntext] NULL,
	[w_f_qt] [ntext] NULL,
	[w_f_remark] [nvarchar](450) NULL,
	[w_jh] [text] NULL,
	[w_spman] [char](20) NULL,
	[w_states] [char](20) NULL,
	[w_spyj] [nvarchar](200) NULL,
	[in_no] [int] NULL,
	[m_ps] [nvarchar](350) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[work_report_rd]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[work_report_rd](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[w_menber] [nvarchar](25) NOT NULL,
	[w_datetime] [datetime] NULL,
	[w_man] [nvarchar](15) NULL,
	[w_dept] [varchar](50) NULL,
	[w_deptjb] [char](10) NULL,
	[w_coname] [nvarchar](90) NULL,
	[w_xmid] [char](10) NULL,
	[w_programer] [nvarchar](90) NULL,
	[w_remark] [nvarchar](100) NULL,
	[w_coname1] [nvarchar](90) NULL,
	[w_xmid1] [char](10) NULL,
	[w_programer1] [nvarchar](90) NULL,
	[w_remark1] [nvarchar](100) NULL,
	[w_coname2] [nvarchar](90) NULL,
	[w_xmid2] [char](10) NULL,
	[w_programer2] [nvarchar](90) NULL,
	[w_remark2] [nvarchar](100) NULL,
	[w_coname3] [nvarchar](90) NULL,
	[w_xmid3] [char](10) NULL,
	[w_programer3] [nvarchar](90) NULL,
	[w_remark3] [nvarchar](100) NULL,
	[w_coname4] [nvarchar](90) NULL,
	[w_xmid4] [char](10) NULL,
	[w_programer4] [nvarchar](90) NULL,
	[w_remark4] [nvarchar](100) NULL,
	[w_jh] [ntext] NULL,
	[w_spman] [char](20) NULL,
	[w_states] [char](20) NULL,
	[w_spyj] [nvarchar](200) NULL,
	[in_no] [int] NULL,
	[m_ps] [nvarchar](350) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[work_report_r]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[work_report_r](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[w_menber] [nvarchar](25) NULL,
	[w_datetime] [datetime] NULL,
	[w_man] [nvarchar](15) NULL,
	[w_dept] [varchar](50) NULL,
	[w_deptjb] [char](10) NULL,
	[w_year] [char](4) NULL,
	[w_month] [char](4) NULL,
	[w_saleje] [decimal](18, 2) NULL,
	[w_topic] [nvarchar](300) NULL,
	[r_topic] [varchar](300) NULL,
	[w_xmgz] [nvarchar](500) NULL,
	[w_jzxx] [nvarchar](360) NULL,
	[w_jy] [nvarchar](330) NULL,
	[w_ktzk] [nvarchar](450) NULL,
	[w_njy] [nvarchar](300) NULL,
	[w_jh] [ntext] NULL,
	[w_spman] [char](20) NULL,
	[w_states] [char](20) NULL,
	[w_spyj] [nvarchar](200) NULL,
	[in_no] [int] NULL,
	[m_ps] [nvarchar](350) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[work_report]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[work_report](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[w_menber] [nvarchar](25) NOT NULL,
	[w_datetime] [datetime] NULL,
	[w_man] [nvarchar](15) NULL,
	[w_dept] [varchar](50) NULL,
	[w_deptjb] [char](10) NULL,
	[w_saleje] [nvarchar](50) NULL,
	[w_coname] [varchar](500) NULL,
	[w_product] [nvarchar](500) NULL,
	[sdate] [nvarchar](350) NULL,
	[edate] [nvarchar](350) NULL,
	[w_gz] [nvarchar](400) NULL,
	[w_remark] [nvarchar](400) NULL,
	[w_coname2] [varchar](300) NULL,
	[w_product2] [nvarchar](400) NULL,
	[sdate2] [nvarchar](350) NULL,
	[edate2] [nvarchar](350) NULL,
	[w_gz2] [nvarchar](400) NULL,
	[w_remark2] [nvarchar](400) NULL,
	[w_coname3] [varchar](350) NULL,
	[w_product3] [nvarchar](450) NULL,
	[sdate3] [nvarchar](350) NULL,
	[edate3] [nvarchar](350) NULL,
	[w_gz3] [nvarchar](400) NULL,
	[w_remark3] [nvarchar](400) NULL,
	[w_coname4] [varchar](400) NULL,
	[w_product4] [nvarchar](400) NULL,
	[sdate4] [nvarchar](350) NULL,
	[edate4] [nvarchar](350) NULL,
	[w_gz4] [nvarchar](400) NULL,
	[w_remark4] [nvarchar](400) NULL,
	[w_coname5] [varchar](400) NULL,
	[w_product5] [nvarchar](400) NULL,
	[sdate5] [nvarchar](350) NULL,
	[edate5] [nvarchar](350) NULL,
	[w_gz5] [nvarchar](400) NULL,
	[w_remark5] [nvarchar](100) NULL,
	[w_jh] [ntext] NULL,
	[w_spman] [char](20) NULL,
	[w_states] [char](20) NULL,
	[w_spyj] [nvarchar](200) NULL,
	[in_no] [int] NULL,
	[m_ps] [nvarchar](350) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[zzsp]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[zzsp](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[dept] [char](30) NULL,
	[role] [char](15) NULL,
	[dd_man] [char](15) NOT NULL,
	[fif] [char](10) NOT NULL,
	[fspman] [varchar](50) NOT NULL,
	[firspif] [char](10) NULL,
	[firspman] [char](20) NULL,
	[remark] [varchar](350) NULL,
	[dd_date] [datetime] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[yptransportation]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[yptransportation](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ddid] [nvarchar](50) NOT NULL,
	[fyid] [nvarchar](50) NOT NULL,
	[invoice] [varchar](50) NOT NULL,
	[subscribe] [varchar](50) NOT NULL,
	[coname] [varchar](100) NOT NULL,
	[trans_com] [varchar](100) NULL,
	[aimport] [varchar](100) NULL,
	[fy_num] [nvarchar](50) NULL,
	[fy_yf] [decimal](18, 2) NULL,
	[fy_bf] [decimal](18, 2) NULL,
	[fy_js] [nvarchar](50) NULL,
	[fy_sz] [nvarchar](50) NULL,
	[transportation] [varchar](90) NULL,
	[mode] [nvarchar](50) NULL,
	[datet] [int] NULL,
	[mbdate] [varchar](50) NULL,
	[sjdate] [varchar](50) NULL,
	[linkman] [varchar](50) NULL,
	[linktel] [varchar](50) NULL,
	[sendcompany] [nvarchar](100) NULL,
	[slinkman] [nvarchar](50) NULL,
	[slinktel] [nvarchar](50) NULL,
	[sate] [varchar](50) NULL,
	[sale_man] [char](15) NULL,
	[sale_dept] [char](30) NULL,
	[deptjb] [char](10) NULL,
	[remark] [nvarchar](350) NULL,
	[co_number] [char](30) NULL,
	[tr_types] [char](15) NULL,
	[fy_date] [char](15) NULL,
	[yf_types] [char](15) NULL,
	[yf_money] [decimal](18, 2) NULL,
	[in_no] [int] NULL,
	[tr_print] [char](10) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[yjsp]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[yjsp](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[dept] [char](30) NULL,
	[role] [char](15) NULL,
	[dd_man] [char](15) NOT NULL,
	[fif] [char](10) NOT NULL,
	[fspman] [varchar](50) NOT NULL,
	[firspif] [char](10) NULL,
	[firspman] [char](20) NULL,
	[remark] [varchar](350) NULL,
	[dd_date] [datetime] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[yftable]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[yftable](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[aimport] [nvarchar](90) NULL,
	[startport] [nvarchar](90) NULL,
	[creatdate] [nvarchar](90) NULL,
	[hdcompany] [nvarchar](90) NULL,
	[hycompany] [nvarchar](90) NULL,
	[enddate] [nvarchar](50) NULL,
	[skymin] [nvarchar](50) NULL,
	[sky1] [nvarchar](50) NULL,
	[sky2] [nvarchar](50) NULL,
	[sky3] [nvarchar](50) NULL,
	[sky4] [nvarchar](50) NULL,
	[sky5] [nvarchar](50) NULL,
	[sky6] [nvarchar](50) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[xzsp]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[xzsp](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[dept] [char](30) NULL,
	[role] [char](15) NULL,
	[dd_man] [char](15) NOT NULL,
	[fif] [char](10) NOT NULL,
	[fspman] [varchar](50) NOT NULL,
	[firspif] [char](10) NULL,
	[firspman] [char](20) NULL,
	[remark] [varchar](350) NULL,
	[dd_date] [datetime] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[xyjb]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[xyjb](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[xyjb_name] [varchar](50) NOT NULL,
	[remark] [varchar](300) NULL,
	[xy_date] [datetime] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[xtable]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[xtable](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ddnumber] [varchar](50) NOT NULL,
	[cpid] [int] NOT NULL,
	[xid] [int] NULL,
	[productmodel] [nvarchar](200) NULL,
	[num] [varchar](50) NULL,
	[num1] [int] NULL,
	[unit] [varchar](50) NULL,
	[proms] [nvarchar](300) NULL,
	[projz] [varchar](50) NULL,
	[promz] [varchar](50) NULL,
	[prosize] [varchar](50) NULL,
	[madein] [nvarchar](50) NULL,
	[in_no] [varchar](50) NULL,
	[cid] [char](10) NULL,
	[toPrint] [int] NULL,
	[pack_num] [int] NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[xlist]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[xlist](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[xid] [varchar](50) NOT NULL,
	[xtableid] [varchar](50) NOT NULL,
	[number] [varchar](50) NULL,
	[model] [nvarchar](500) NULL,
	[num] [int] NULL,
	[unit] [varchar](50) NULL,
	[projz] [varchar](50) NULL,
	[promz] [varchar](50) NULL,
	[prosize] [varchar](50) NULL,
	[madein] [varchar](50) NULL,
	[in_no] [int] NULL,
	[proms] [nvarchar](300) NULL,
	[coname] [nvarchar](300) NULL,
	[tr_addr] [nvarchar](550) NULL,
	[hnumber] [varchar](50) NULL,
	[sdate] [datetime] NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[xjproduct]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[xjproduct](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[proid] [varchar](50) NULL,
	[supplierid] [varchar](50) NULL,
	[number] [varchar](50) NOT NULL,
	[cdatetime] [varchar](50) NULL,
	[coname] [varchar](150) NULL,
	[cotel] [varchar](50) NULL,
	[coaddr] [varchar](150) NULL,
	[cofax] [varchar](50) NULL,
	[linkman] [varchar](50) NULL,
	[linktel] [varchar](50) NULL,
	[linkwap] [varchar](50) NULL,
	[linkemail] [varchar](50) NULL,
	[topic] [varchar](150) NULL,
	[content] [ntext] NULL,
	[man] [varchar](50) NULL,
	[jhport] [varchar](50) NULL,
	[zzbb] [varchar](50) NULL,
	[bbnumber] [varchar](50) NULL,
	[pack] [varchar](150) NULL,
	[suttle] [varchar](50) NULL,
	[weight] [varchar](50) NULL,
	[vol] [varchar](50) NULL,
	[currentdate] [varchar](50) NULL,
	[payfs] [varchar](50) NULL,
	[addrate] [varchar](50) NULL,
	[rate] [varchar](50) NULL,
	[transportation] [varchar](50) NULL,
	[verify] [varchar](50) NULL,
	[relapse] [varchar](50) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[xjpro]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[xjpro](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ddid] [varchar](10) NOT NULL,
	[epro] [varchar](300) NOT NULL,
	[cpro] [nvarchar](100) NULL,
	[cpro2] [nvarchar](100) NULL,
	[num] [int] NOT NULL,
	[unit] [varchar](50) NULL,
	[selljg] [decimal](18, 6) NULL,
	[money] [varchar](50) NULL,
	[cgpro_ydatetime] [nvarchar](50) NULL,
	[cgpro_num] [int] NOT NULL,
	[cgpro_sdatetime] [nvarchar](15) NULL,
	[remark] [nvarchar](300) NULL,
	[supplier] [nvarchar](50) NULL,
	[rate] [int] NULL,
	[wid] [char](10) NULL,
	[pro_sup_no] [nvarchar](300) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[xclient_linkman]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[xclient_linkman](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[client_id] [int] NULL,
	[name] [varchar](50) NULL,
	[position] [varchar](50) NULL,
	[tel] [varchar](50) NULL,
	[mobile] [varchar](50) NULL,
	[email] [varchar](100) NULL,
	[qq] [varchar](50) NULL,
	[msn] [varchar](100) NULL,
	[skype] [varchar](100) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[username]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[username](
	[nameid] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](50) NOT NULL,
	[sex] [varchar](50) NULL,
	[password] [varchar](50) NULL,
	[name_en] [nvarchar](50) NOT NULL,
	[workj] [varchar](90) NULL,
	[email] [varchar](90) NULL,
	[worktel] [varchar](50) NULL,
	[userdate] [varchar](50) NULL,
	[yjxs] [varchar](50) NULL,
	[hometel] [nvarchar](50) NULL,
	[user_ip] [varchar](50) NULL,
	[ipbd] [char](10) NULL,
	[err] [int] NULL,
	[mail_user] [nvarchar](50) NULL,
	[mail_password] [nvarchar](50) NULL,
	[mail_smtp] [nvarchar](90) NULL,
	[restrain_id] [int] NULL,
	[department_id] [int] NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
EXEC sys.sp_addextendedproperty @name=N'MS_Description', @value=N'权限ID' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'TABLE',@level1name=N'username', @level2type=N'COLUMN',@level2name=N'restrain_id'
GO
/****** Object:  Table [dbo].[quoteproduct]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[quoteproduct](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[quoteid] [varchar](50) NOT NULL,
	[product] [varchar](150) NULL,
	[cpro] [varchar](70) NULL,
	[cpro2] [nvarchar](70) NULL,
	[pro_gg] [nvarchar](50) NULL,
	[quantity] [int] NULL,
	[unit] [varchar](50) NULL,
	[price] [decimal](18, 4) NULL,
	[pricehb] [varchar](50) NULL,
	[selljg] [decimal](18, 4) NULL,
	[money] [char](10) NULL,
	[supplier] [varchar](150) NULL,
	[wid] [nvarchar](50) NULL,
	[rate_types] [char](10) NULL,
	[rate] [int] NULL,
	[remark] [varchar](300) NULL,
	[moq] [varchar](50) NULL,
	[mpq] [varchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[quote_pic]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[quote_pic](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[pro_model] [nvarchar](120) NULL,
	[pro_name] [nvarchar](150) NULL,
	[pro_pic] [nvarchar](50) NULL,
	[pro_num] [decimal](10, 0) NULL,
	[pro_unit] [char](10) NULL,
	[pro_price] [decimal](18, 2) NULL,
	[pro_man] [nvarchar](50) NULL,
	[pro_date] [nvarchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[quote]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[quote](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[number] [varchar](50) NOT NULL,
	[quotedatetime] [varchar](50) NULL,
	[in_number] [nvarchar](50) NULL,
	[co_number] [char](30) NULL,
	[coname] [varchar](150) NULL,
	[cotel] [varchar](50) NULL,
	[coaddr] [varchar](150) NULL,
	[cofax] [varchar](50) NULL,
	[linkman] [varchar](50) NULL,
	[linktel] [varchar](50) NULL,
	[linkwap] [varchar](50) NULL,
	[linkemail] [varchar](50) NULL,
	[airport] [varchar](260) NULL,
	[tr_types] [nvarchar](90) NULL,
	[q_tr_date] [nvarchar](90) NULL,
	[payment] [nvarchar](100) NULL,
	[bz] [nvarchar](150) NULL,
	[content] [ntext] NULL,
	[man] [varchar](50) NULL,
	[spman] [nvarchar](50) NULL,
	[states] [char](10) NULL,
	[spyj] [nvarchar](200) NULL,
	[hb] [char](10) NULL,
	[fveight] [decimal](18, 2) NULL,
	[insurance] [decimal](18, 2) NULL,
	[commission] [decimal](18, 2) NULL,
	[discount] [decimal](18, 2) NULL,
	[in_no] [int] NULL,
	[dept] [nvarchar](50) NULL,
	[deptjb] [char](15) NULL,
	[acct] [nvarchar](50) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[qlinkman]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[qlinkman](
	[nameid] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](50) NOT NULL,
	[job] [varchar](50) NULL,
	[mr] [varchar](50) NULL,
	[email] [varchar](50) NULL,
	[tel] [varchar](50) NULL,
	[department] [varchar](50) NULL,
	[bornde] [varchar](50) NULL,
	[school] [varchar](150) NULL,
	[degree] [varchar](50) NULL,
	[born] [varchar](50) NULL,
	[co_number] [char](30) NULL,
	[coname] [varchar](150) NULL,
	[coaddr] [varchar](150) NULL,
	[cotel] [varchar](50) NULL,
	[cofax] [varchar](50) NULL,
	[prorole] [varchar](50) NULL,
	[evaluate] [varchar](100) NULL,
	[artifice] [varchar](100) NULL,
	[waptel] [varchar](50) NULL,
	[myaddr] [nvarchar](100) NULL,
	[interest] [varchar](300) NULL,
	[username] [varchar](50) NULL,
	[rg_date] [datetime] NULL,
	[dept] [nvarchar](50) NULL,
	[deptjb] [char](10) NULL,
	[share] [varchar](50) NULL,
	[modman] [char](15) NULL,
	[mod_date] [datetime] NULL,
	[beizhu] [varchar](300) NULL,
	[qq] [varchar](50) NULL,
	[msn] [ntext] NULL,
	[skype] [varchar](50) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[qclient_linkman]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[qclient_linkman](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[client_id] [int] NULL,
	[name] [varchar](50) NULL,
	[position] [varchar](50) NULL,
	[tel] [varchar](50) NULL,
	[mobile] [varchar](50) NULL,
	[email] [varchar](100) NULL,
	[qq] [varchar](50) NULL,
	[msn] [varchar](100) NULL,
	[skype] [varchar](100) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[qc_item]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[qc_item](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[qc_date] [datetime] NULL,
	[number] [varchar](50) NULL,
	[result] [varchar](50) NULL,
	[remark] [varchar](500) NULL,
	[model] [varchar](200) NULL,
 CONSTRAINT [PK_qc_item] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[qc_check_result]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[qc_check_result](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[qc_item_id] [int] NOT NULL,
	[qc_check_item_id] [int] NULL,
	[result] [varchar](200) NULL,
 CONSTRAINT [PK_qc_check_result] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[qc_check_item]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[qc_check_item](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[display] [varchar](50) NOT NULL,
	[name] [varchar](50) NULL,
 CONSTRAINT [PK_qc_check_item] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[q_mb]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[q_mb](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[q_topic] [varchar](100) NULL,
	[q_company] [varchar](80) NULL,
	[q_addr] [varchar](100) NULL,
	[q_tel] [varchar](60) NULL,
	[q_fax] [varchar](80) NULL,
	[q_email] [varchar](100) NULL,
	[q_net] [varchar](100) NULL,
	[q_tk] [varchar](1000) NULL,
	[remark] [varchar](300) NULL,
	[q_date] [datetime] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[punit_type]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[punit_type](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[punit_name] [nvarchar](20) NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[publicity]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[publicity](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[number] [char](10) NOT NULL,
	[pub_area] [nvarchar](200) NULL,
	[pub_addr] [nvarchar](100) NULL,
	[start_date] [datetime] NOT NULL,
	[end_date] [datetime] NOT NULL,
	[pub_name] [nvarchar](100) NULL,
	[pub_lm] [nvarchar](100) NULL,
	[pub_subject] [nvarchar](100) NULL,
	[pub_content] [ntext] NULL,
	[pub_product] [nvarchar](200) NULL,
	[pub_price] [decimal](18, 2) NULL,
	[man] [char](10) NOT NULL,
	[dept] [char](10) NOT NULL,
	[deptjb] [char](10) NOT NULL,
	[rg_date] [datetime] NOT NULL,
	[spman] [char](10) NOT NULL,
	[spyj] [nvarchar](200) NULL,
	[states] [char](10) NULL,
	[in_no] [int] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[pub_table]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[pub_table](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[titel] [nvarchar](150) NULL,
	[content] [ntext] NULL,
	[username] [nvarchar](50) NULL,
	[fvdate] [nvarchar](50) NULL,
	[dept] [nvarchar](50) NULL,
	[datet] [int] NULL,
	[view_date] [datetime] NULL,
	[spman] [char](10) NULL,
	[states] [char](15) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[province]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[province](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[province_name] [varchar](100) NULL,
	[province_num] [char](3) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[prosupplier]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[prosupplier](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[proid] [varchar](50) NULL,
	[coname] [varchar](150) NULL,
	[cotel] [varchar](50) NULL,
	[cofax] [varchar](50) NULL,
	[epro] [varchar](150) NULL,
	[cpro] [varchar](150) NULL,
	[quality] [varchar](150) NULL,
	[pack] [varchar](150) NULL,
	[suttle] [varchar](50) NULL,
	[grossweight] [varchar](50) NULL,
	[vol] [varchar](50) NULL,
	[num] [varchar](50) NULL,
	[unit] [varchar](50) NULL,
	[rate] [varchar](50) NULL,
	[credit] [varchar](50) NULL,
	[secret] [varchar](50) NULL,
	[hb] [nvarchar](15) NULL,
	[saleprice] [varchar](50) NULL,
	[saleshb] [nvarchar](15) NULL,
	[remark] [ntext] NULL,
	[createdt] [varchar](50) NULL,
	[mark] [nvarchar](50) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[rkhouse]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[rkhouse](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[pro_model] [nvarchar](90) NOT NULL,
	[pro_name] [nvarchar](80) NULL,
	[pro_number] [nvarchar](90) NULL,
	[pro_num] [int] NOT NULL,
	[pro_unit] [varchar](20) NOT NULL,
	[pro_price] [decimal](18, 6) NOT NULL,
	[pro_hb] [char](10) NULL,
	[pro_supplier] [varchar](90) NULL,
	[pro_addr] [varchar](50) NULL,
	[pro_id] [nvarchar](50) NOT NULL,
	[pro_datetime] [datetime] NOT NULL,
	[remark] [varchar](300) NULL,
	[pro_types] [nvarchar](50) NULL,
	[pro_sup_number] [char](30) NULL,
	[pro_rate] [char](15) NULL,
	[pro_rk_num] [char](20) NULL,
	[pro_cgy] [char](30) NULL,
	[status] [int] NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[rk_info]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[rk_info](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[pro_model] [varchar](100) NULL,
	[operateTime] [datetime] NULL,
	[operateMan] [varchar](100) NULL,
	[remark] [ntext] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[right]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[right](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](50) NULL,
	[display] [varchar](200) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[restrain_name]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[restrain_name](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[r_name] [char](35) NOT NULL,
	[dept] [nvarchar](90) NULL,
	[deptjb] [char](10) NULL,
	[qzmod] [char](10) NULL,
	[qzdel] [char](10) NULL,
	[qzview] [char](10) NULL,
	[qzview_yes] [char](10) NULL,
	[qzzt] [char](10) NULL,
	[xhadd] [char](10) NULL,
	[xhmod] [char](10) NULL,
	[xhdel] [char](10) NULL,
	[xhview] [char](10) NULL,
	[xhview_yes] [char](10) NULL,
	[xhzt] [char](10) NULL,
	[linkadd] [char](10) NULL,
	[linkmod] [char](10) NULL,
	[linkdel] [char](10) NULL,
	[linkview] [char](10) NULL,
	[linkview_yes] [char](10) NULL,
	[linkzt] [char](10) NULL,
	[hzadd] [char](10) NULL,
	[hzmod] [char](10) NULL,
	[hzdel] [char](10) NULL,
	[hzview] [char](10) NULL,
	[hzview_yes] [char](10) NULL,
	[hzzt] [char](10) NULL,
	[supadd] [char](10) NULL,
	[supmod] [char](10) NULL,
	[supdel] [char](10) NULL,
	[supview] [char](10) NULL,
	[supview_yes] [char](10) NULL,
	[supzt] [char](10) NULL,
	[actadd] [char](10) NULL,
	[actmod] [char](10) NULL,
	[actdel] [char](10) NULL,
	[actview] [char](10) NULL,
	[actview_yes] [char](10) NULL,
	[actzt] [char](10) NULL,
	[opadd] [char](10) NULL,
	[opmod] [char](10) NULL,
	[opdel] [char](10) NULL,
	[opview] [char](10) NULL,
	[opview_yes] [char](10) NULL,
	[opzt] [char](10) NULL,
	[quoteadd] [char](10) NULL,
	[quotemod] [char](10) NULL,
	[quotedel] [char](10) NULL,
	[quoteview] [char](10) NULL,
	[quoteview_yes] [char](10) NULL,
	[quotezt] [char](10) NULL,
	[profl] [nvarchar](100) NULL,
	[proadd] [char](10) NULL,
	[promod] [char](10) NULL,
	[prodel] [char](10) NULL,
	[proview] [char](10) NULL,
	[proview_yes] [char](10) NULL,
	[prozt] [char](10) NULL,
	[cgadd] [char](10) NULL,
	[cgmod] [char](10) NULL,
	[cgdel] [char](10) NULL,
	[cgview] [char](10) NULL,
	[cgview_yes] [char](10) NULL,
	[cgzt] [char](10) NULL,
	[tradd] [char](10) NULL,
	[trmod] [char](10) NULL,
	[trdel] [char](10) NULL,
	[trview] [char](10) NULL,
	[trview_yes] [char](10) NULL,
	[trzt] [char](10) NULL,
	[hdadd] [char](10) NULL,
	[hdmod] [char](10) NULL,
	[hddel] [char](10) NULL,
	[hdview] [char](10) NULL,
	[hdview_yes] [char](10) NULL,
	[hdzt] [char](10) NULL,
	[r_yfgladd] [char](10) NULL,
	[r_yfglmod] [char](10) NULL,
	[r_yfgldel] [char](10) NULL,
	[r_yfglview] [char](10) NULL,
	[r_yfglview_yes] [char](10) NULL,
	[r_yfglzt] [char](10) NULL,
	[workadd] [char](10) NULL,
	[workmod] [char](10) NULL,
	[workdel] [char](10) NULL,
	[workview] [char](10) NULL,
	[workview_yes] [char](10) NULL,
	[workzt] [char](10) NULL,
	[r_month_jh_add] [char](10) NULL,
	[r_month_jh_mod] [char](10) NULL,
	[r_month_jh_del] [char](10) NULL,
	[r_month_jh_view] [char](10) NULL,
	[r_month_jh_view_yes] [char](10) NULL,
	[r_month_jh_zt] [char](10) NULL,
	[r_date_r_add] [char](10) NULL,
	[r_date_r_mod] [char](10) NULL,
	[r_date_r_del] [char](10) NULL,
	[r_date_r_view] [char](10) NULL,
	[r_date_r_view_yes] [char](10) NULL,
	[r_date_r_zt] [char](10) NULL,
	[r_week_r_add] [char](10) NULL,
	[r_week_r_mod] [char](10) NULL,
	[r_week_r_del] [char](10) NULL,
	[r_week_r_view] [char](10) NULL,
	[r_week_r_view_yes] [char](10) NULL,
	[r_week_r_zt] [char](10) NULL,
	[r_month_r_add] [char](10) NULL,
	[r_month_r_mod] [char](10) NULL,
	[r_month_r_del] [char](10) NULL,
	[r_month_r_view] [char](10) NULL,
	[r_month_r_view_yes] [char](10) NULL,
	[r_month_r_zt] [char](10) NULL,
	[xmadd] [char](10) NULL,
	[xmmod] [char](10) NULL,
	[xmdel] [char](10) NULL,
	[xmview] [char](10) NULL,
	[xmview_yes] [char](10) NULL,
	[xmzt] [char](10) NULL,
	[r_htgl_add] [char](10) NULL,
	[r_htgl_mod] [char](10) NULL,
	[r_htgl_del] [char](10) NULL,
	[r_htgl_view] [char](10) NULL,
	[r_htgl_view_yes] [char](10) NULL,
	[r_htgl_zt] [char](10) NULL,
	[subadd] [char](10) NULL,
	[submod] [char](10) NULL,
	[subdel] [char](10) NULL,
	[subview] [char](10) NULL,
	[subview_yes] [char](10) NULL,
	[subzt] [char](10) NULL,
	[manadd] [char](10) NULL,
	[manmod] [char](10) NULL,
	[mandel] [char](10) NULL,
	[manview] [char](10) NULL,
	[manview_yes] [char](10) NULL,
	[manzt] [char](10) NULL,
	[rwadd] [char](10) NULL,
	[rwmod] [char](10) NULL,
	[rwdel] [char](10) NULL,
	[rwview] [char](10) NULL,
	[rwview_yes] [char](10) NULL,
	[rwzt] [char](10) NULL,
	[fyadd] [char](10) NULL,
	[fymod] [char](10) NULL,
	[fydel] [char](10) NULL,
	[fyview] [char](10) NULL,
	[fyview_yes] [char](10) NULL,
	[fyzt] [char](10) NULL,
	[r_jkgl_add] [char](10) NULL,
	[r_jkgl_mod] [char](10) NULL,
	[r_jkgl_del] [char](10) NULL,
	[r_jkgl_view] [char](10) NULL,
	[r_jkgl_view_yes] [char](10) NULL,
	[r_jkgl_zt] [char](10) NULL,
	[r_fksq_add] [char](10) NULL,
	[r_fksq_mod] [char](10) NULL,
	[r_fksq_del] [char](10) NULL,
	[r_fksq_view] [char](10) NULL,
	[r_fksq_view_yes] [char](10) NULL,
	[r_fksq_zt] [char](10) NULL,
	[thadd] [char](10) NULL,
	[thmod] [char](10) NULL,
	[thdel] [char](10) NULL,
	[thview] [char](10) NULL,
	[thview_yes] [char](10) NULL,
	[thzt] [char](10) NULL,
	[serveradd] [char](10) NULL,
	[servermod] [char](10) NULL,
	[serverdel] [char](10) NULL,
	[serverview] [char](10) NULL,
	[serverview_yes] [char](10) NULL,
	[serverzt] [char](10) NULL,
	[tsadd] [char](10) NULL,
	[tsmod] [char](10) NULL,
	[tsdel] [char](10) NULL,
	[tsview] [char](10) NULL,
	[tsview_yes] [char](10) NULL,
	[tszt] [char](10) NULL,
	[wxadd] [char](10) NULL,
	[wxmod] [char](10) NULL,
	[wxdel] [char](10) NULL,
	[wxview] [char](10) NULL,
	[wxview_yes] [char](10) NULL,
	[wxzt] [char](10) NULL,
	[r_hyzc_add] [char](10) NULL,
	[r_hyzc_mod] [char](10) NULL,
	[r_hyzc_del] [char](10) NULL,
	[r_hyzc_view] [char](10) NULL,
	[r_hyzc_view_yes] [char](10) NULL,
	[r_hyzc_zt] [char](10) NULL,
	[r_mtxc_add] [char](10) NULL,
	[r_mtxc_mod] [char](10) NULL,
	[r_mtxc_del] [char](10) NULL,
	[r_mtxc_view] [char](10) NULL,
	[r_mtxc_view_yes] [char](10) NULL,
	[r_mtxc_zt] [char](10) NULL,
	[mactadd] [char](10) NULL,
	[mactmod] [char](10) NULL,
	[mactdel] [char](10) NULL,
	[mactview] [char](10) NULL,
	[mactview_yes] [char](10) NULL,
	[mactzt] [char](10) NULL,
	[jzdsadd] [char](10) NULL,
	[jzdsmod] [char](10) NULL,
	[jzdsdel] [char](10) NULL,
	[jzdsview] [char](10) NULL,
	[jzdsview_yes] [char](10) NULL,
	[jzdszt] [char](10) NULL,
	[zskadd] [char](10) NULL,
	[zskmod] [char](10) NULL,
	[zskdel] [char](10) NULL,
	[zskview] [char](10) NULL,
	[zskview_yes] [char](10) NULL,
	[zskzt] [char](10) NULL,
	[flfgadd] [char](10) NULL,
	[flfgmod] [char](10) NULL,
	[flfgdel] [char](10) NULL,
	[flfgview] [char](10) NULL,
	[flfgview_yes] [char](10) NULL,
	[flfgzt] [char](10) NULL,
	[r_wlz_add] [char](10) NULL,
	[r_wlz_mod] [char](10) NULL,
	[r_wlz_del] [char](10) NULL,
	[r_wlz_view] [char](10) NULL,
	[r_wlz_view_yes] [char](10) NULL,
	[r_wlz_zt] [char](10) NULL,
	[zjadd] [char](10) NULL,
	[zjmod] [char](10) NULL,
	[zjdel] [char](10) NULL,
	[zjview] [char](10) NULL,
	[zjview_yes] [char](10) NULL,
	[zjzt] [char](10) NULL,
	[fkhzadd] [char](10) NULL,
	[fkhzmod] [char](10) NULL,
	[fkhzdel] [char](10) NULL,
	[fkhzview] [char](10) NULL,
	[fkhzview_yes] [char](10) NULL,
	[fkhzzt] [char](10) NULL,
	[skhzadd] [char](10) NULL,
	[skhzmod] [char](10) NULL,
	[skhzdel] [char](10) NULL,
	[skhzview] [char](10) NULL,
	[skhzview_yes] [char](10) NULL,
	[skhzzt] [char](10) NULL,
	[fkmod] [char](10) NULL,
	[fkdel] [char](10) NULL,
	[fkview] [char](10) NULL,
	[fkview_yes] [char](10) NULL,
	[fkzt] [char](10) NULL,
	[skmod] [char](10) NULL,
	[skdel] [char](10) NULL,
	[skview] [char](10) NULL,
	[skview_yes] [char](10) NULL,
	[skzt] [char](10) NULL,
	[qkmod] [char](10) NULL,
	[qkdel] [char](10) NULL,
	[qkview] [char](10) NULL,
	[qkview_yes] [char](10) NULL,
	[qkzt] [char](10) NULL,
	[r_thje_mod] [char](10) NULL,
	[r_thje_del] [char](10) NULL,
	[r_thje_view] [char](10) NULL,
	[r_thje_view_yes] [char](10) NULL,
	[r_thje_zt] [char](10) NULL,
	[intadd] [char](10) NULL,
	[intmod] [char](10) NULL,
	[intdel] [char](10) NULL,
	[intview] [char](10) NULL,
	[intview_yes] [char](10) NULL,
	[intzt] [char](10) NULL,
	[outadd] [char](10) NULL,
	[outmod] [char](10) NULL,
	[outdel] [char](10) NULL,
	[outview] [char](10) NULL,
	[outview_yes] [char](10) NULL,
	[outzt] [char](10) NULL,
	[r_dzzx_zt] [char](10) NULL,
	[r_xscp_zt] [char](10) NULL,
	[syszt] [char](10) NULL,
	[jcfxzt] [char](10) NULL,
	[custzyzt] [char](10) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[restrain_gp]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[restrain_gp](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[restrain_name] [nvarchar](50) NULL,
	[pro_ck] [nvarchar](50) NULL,
	[pro_zt] [char](10) NULL,
	[pro_add] [char](10) NULL,
	[pro_mod] [char](10) NULL,
	[pro_del] [char](10) NULL,
	[pro_view] [char](10) NULL,
	[proview_price] [char](10) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[restrain]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[restrain](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[restrain_name] [char](65) NOT NULL,
	[qzadd] [char](10) NULL,
	[qzmod] [char](10) NULL,
	[qzdel] [char](10) NULL,
	[qzview] [char](10) NULL,
	[qzview_yes] [char](10) NULL,
	[qzzt] [char](10) NULL,
	[xhadd] [char](10) NULL,
	[xhmod] [char](10) NULL,
	[xhdel] [char](10) NULL,
	[xhview] [char](10) NULL,
	[xhview_yes] [char](10) NULL,
	[xhzt] [char](10) NULL,
	[linkadd] [char](10) NULL,
	[linkmod] [char](10) NULL,
	[linkdel] [char](10) NULL,
	[linkview] [char](10) NULL,
	[linkview_yes] [char](10) NULL,
	[linkzt] [char](10) NULL,
	[hzadd] [char](10) NULL,
	[hzmod] [char](10) NULL,
	[hzdel] [char](10) NULL,
	[hzview] [char](10) NULL,
	[hzview_yes] [char](10) NULL,
	[hzzt] [char](10) NULL,
	[supadd] [char](10) NULL,
	[supmod] [char](10) NULL,
	[supdel] [char](10) NULL,
	[supview] [char](10) NULL,
	[supview_yes] [char](10) NULL,
	[supzt] [char](10) NULL,
	[actadd] [char](10) NULL,
	[actmod] [char](10) NULL,
	[actdel] [char](10) NULL,
	[actview] [char](10) NULL,
	[actview_yes] [char](10) NULL,
	[actzt] [char](10) NULL,
	[opadd] [char](10) NULL,
	[opmod] [char](10) NULL,
	[opdel] [char](10) NULL,
	[opview] [char](10) NULL,
	[opview_yes] [char](10) NULL,
	[opzt] [char](10) NULL,
	[quoteadd] [char](10) NULL,
	[quotemod] [char](10) NULL,
	[quotedel] [char](10) NULL,
	[quoteview] [char](10) NULL,
	[quoteview_yes] [char](10) NULL,
	[quotezt] [char](10) NULL,
	[profl] [nvarchar](100) NULL,
	[proadd] [char](10) NULL,
	[promod] [char](10) NULL,
	[prodel] [char](10) NULL,
	[proview] [char](10) NULL,
	[proview_yes] [char](10) NULL,
	[prozt] [char](10) NULL,
	[r_sup_xj_add] [char](10) NULL,
	[r_sup_xj_mod] [char](10) NULL,
	[r_sup_xj_del] [char](10) NULL,
	[r_sup_xj_view] [char](10) NULL,
	[r_sup_xj_zt] [char](10) NULL,
	[cgadd] [char](10) NULL,
	[cgmod] [char](10) NULL,
	[cgdel] [char](10) NULL,
	[cgview] [char](10) NULL,
	[cgview_yes] [char](10) NULL,
	[cgzt] [char](10) NULL,
	[tradd] [char](10) NULL,
	[trmod] [char](10) NULL,
	[trdel] [char](10) NULL,
	[trview] [char](10) NULL,
	[trview_yes] [char](10) NULL,
	[trzt] [char](10) NULL,
	[hdadd] [char](10) NULL,
	[hdmod] [char](10) NULL,
	[hddel] [char](10) NULL,
	[hdview] [char](10) NULL,
	[hdview_yes] [char](10) NULL,
	[hdzt] [char](10) NULL,
	[r_yfgladd] [char](10) NULL,
	[r_yfglmod] [char](10) NULL,
	[r_yfgldel] [char](10) NULL,
	[r_yfglview] [char](10) NULL,
	[r_yfglview_yes] [char](10) NULL,
	[r_yfglzt] [char](10) NULL,
	[workadd] [char](10) NULL,
	[workmod] [char](10) NULL,
	[workdel] [char](10) NULL,
	[workview] [char](10) NULL,
	[workview_yes] [char](10) NULL,
	[workzt] [char](10) NULL,
	[r_month_jh_add] [char](10) NULL,
	[r_month_jh_mod] [char](10) NULL,
	[r_month_jh_del] [char](10) NULL,
	[r_month_jh_view] [char](10) NULL,
	[r_month_jh_view_yes] [char](10) NULL,
	[r_month_jh_zt] [char](10) NULL,
	[r_date_r_add] [char](10) NULL,
	[r_date_r_mod] [char](10) NULL,
	[r_date_r_del] [char](10) NULL,
	[r_date_r_view] [char](10) NULL,
	[r_date_r_view_yes] [char](10) NULL,
	[r_date_r_zt] [char](10) NULL,
	[r_week_r_add] [char](10) NULL,
	[r_week_r_mod] [char](10) NULL,
	[r_week_r_del] [char](10) NULL,
	[r_week_r_view] [char](10) NULL,
	[r_week_r_view_yes] [char](10) NULL,
	[r_week_r_zt] [char](10) NULL,
	[r_month_r_add] [char](10) NULL,
	[r_month_r_mod] [char](10) NULL,
	[r_month_r_del] [char](10) NULL,
	[r_month_r_view] [char](10) NULL,
	[r_month_r_view_yes] [char](10) NULL,
	[r_month_r_zt] [char](10) NULL,
	[xmadd] [char](10) NULL,
	[xmmod] [char](10) NULL,
	[xmdel] [char](10) NULL,
	[xmview] [char](10) NULL,
	[xmview_yes] [char](10) NULL,
	[xmzt] [char](10) NULL,
	[r_cus_xj_add] [char](10) NULL,
	[r_cus_xj_mod] [char](10) NULL,
	[r_cus_xj_del] [char](10) NULL,
	[r_cus_xj_view] [char](10) NULL,
	[r_cus_xj_zt] [char](10) NULL,
	[r_htgl_add] [char](10) NULL,
	[r_htgl_mod] [char](10) NULL,
	[r_htgl_del] [char](10) NULL,
	[r_htgl_view] [char](10) NULL,
	[r_htgl_view_yes] [char](10) NULL,
	[r_htgl_zt] [char](10) NULL,
	[r_sam_add] [char](10) NULL,
	[r_sam_mod] [char](10) NULL,
	[r_sam_del] [char](10) NULL,
	[r_sam_view] [char](10) NULL,
	[r_sam_zt] [char](10) NULL,
	[subadd] [char](10) NULL,
	[submod] [char](10) NULL,
	[subdel] [char](10) NULL,
	[subview] [char](10) NULL,
	[subview_yes] [char](10) NULL,
	[subzt] [char](10) NULL,
	[manadd] [char](10) NULL,
	[manmod] [char](10) NULL,
	[mandel] [char](10) NULL,
	[manview] [char](10) NULL,
	[manview_yes] [char](10) NULL,
	[manzt] [char](10) NULL,
	[rwadd] [char](10) NULL,
	[rwmod] [char](10) NULL,
	[rwdel] [char](10) NULL,
	[rwview] [char](10) NULL,
	[rwview_yes] [char](10) NULL,
	[rwzt] [char](10) NULL,
	[fyadd] [char](10) NULL,
	[fymod] [char](10) NULL,
	[fydel] [char](10) NULL,
	[fyview] [char](10) NULL,
	[fyview_yes] [char](10) NULL,
	[fyzt] [char](10) NULL,
	[r_jkgl_add] [char](10) NULL,
	[r_jkgl_mod] [char](10) NULL,
	[r_jkgl_del] [char](10) NULL,
	[r_jkgl_view] [char](10) NULL,
	[r_jkgl_view_yes] [char](10) NULL,
	[r_jkgl_zt] [char](10) NULL,
	[r_fksq_add] [char](10) NULL,
	[r_fksq_mod] [char](10) NULL,
	[r_fksq_del] [char](10) NULL,
	[r_fksq_view] [char](10) NULL,
	[r_fksq_view_yes] [char](10) NULL,
	[r_fksq_zt] [char](10) NULL,
	[r_zzsq_add] [char](10) NULL,
	[r_zzsq_mod] [char](10) NULL,
	[r_zzsq_del] [char](10) NULL,
	[r_zzsq_view] [char](10) NULL,
	[r_zzsq_zt] [char](10) NULL,
	[r_jcsq_add] [char](10) NULL,
	[r_jcsq_mod] [char](10) NULL,
	[r_jcsq_del] [char](10) NULL,
	[r_jcsq_view] [char](10) NULL,
	[r_jcsq_zt] [char](10) NULL,
	[r_xzyd_add] [char](10) NULL,
	[r_xzyd_mod] [char](10) NULL,
	[r_xzyd_del] [char](10) NULL,
	[r_xzyd_view] [char](10) NULL,
	[r_xzyd_zt] [char](10) NULL,
	[r_ccsq_add] [char](10) NULL,
	[r_ccsq_mod] [char](10) NULL,
	[r_ccsq_del] [char](10) NULL,
	[r_ccsq_view] [char](10) NULL,
	[r_ccsq_zt] [char](10) NULL,
	[r_bgyp_add] [char](10) NULL,
	[r_bgyp_mod] [char](10) NULL,
	[r_bgyp_del] [char](10) NULL,
	[r_bgyp_view] [char](10) NULL,
	[r_bgyp_zt] [char](10) NULL,
	[r_nbqc_add] [char](10) NULL,
	[r_nbqc_mod] [char](10) NULL,
	[r_nbqc_del] [char](10) NULL,
	[r_nbqc_view] [char](10) NULL,
	[r_nbqc_zt] [char](10) NULL,
	[r_yjsq_add] [char](10) NULL,
	[r_yjsq_mod] [char](10) NULL,
	[r_yjsq_del] [char](10) NULL,
	[r_yjsq_view] [char](10) NULL,
	[r_yjsq_zt] [char](10) NULL,
	[r_spbm_add] [char](10) NULL,
	[r_spbm_mod] [char](10) NULL,
	[r_spbm_del] [char](10) NULL,
	[r_spbm_view] [char](10) NULL,
	[r_spbm_zt] [char](10) NULL,
	[thadd] [char](10) NULL,
	[thmod] [char](10) NULL,
	[thdel] [char](10) NULL,
	[thview] [char](10) NULL,
	[thview_yes] [char](10) NULL,
	[thzt] [char](10) NULL,
	[r_xphh_add] [char](10) NULL,
	[r_xphh_mod] [char](10) NULL,
	[r_xphh_del] [char](10) NULL,
	[r_xphh_view] [char](10) NULL,
	[r_xphh_zt] [char](10) NULL,
	[serveradd] [char](10) NULL,
	[servermod] [char](10) NULL,
	[serverdel] [char](10) NULL,
	[serverview] [char](10) NULL,
	[serverview_yes] [char](10) NULL,
	[serverzt] [char](10) NULL,
	[tsadd] [char](10) NULL,
	[tsmod] [char](10) NULL,
	[tsdel] [char](10) NULL,
	[tsview] [char](10) NULL,
	[tsview_yes] [char](10) NULL,
	[tszt] [char](10) NULL,
	[wxadd] [char](10) NULL,
	[wxmod] [char](10) NULL,
	[wxdel] [char](10) NULL,
	[wxview] [char](10) NULL,
	[wxview_yes] [char](10) NULL,
	[wxzt] [char](10) NULL,
	[r_hyzc_add] [char](10) NULL,
	[r_hyzc_mod] [char](10) NULL,
	[r_hyzc_del] [char](10) NULL,
	[r_hyzc_view] [char](10) NULL,
	[r_hyzc_view_yes] [char](10) NULL,
	[r_hyzc_zt] [char](10) NULL,
	[r_mtxc_add] [char](10) NULL,
	[r_mtxc_mod] [char](10) NULL,
	[r_mtxc_del] [char](10) NULL,
	[r_mtxc_view] [char](10) NULL,
	[r_mtxc_view_yes] [char](10) NULL,
	[r_mtxc_zt] [char](10) NULL,
	[mactadd] [char](10) NULL,
	[mactmod] [char](10) NULL,
	[mactdel] [char](10) NULL,
	[mactview] [char](10) NULL,
	[mactview_yes] [char](10) NULL,
	[mactzt] [char](10) NULL,
	[jzdsadd] [char](10) NULL,
	[jzdsmod] [char](10) NULL,
	[jzdsdel] [char](10) NULL,
	[jzdsview] [char](10) NULL,
	[jzdsview_yes] [char](10) NULL,
	[jzdszt] [char](10) NULL,
	[zskadd] [char](10) NULL,
	[zskmod] [char](10) NULL,
	[zskdel] [char](10) NULL,
	[zskview] [char](10) NULL,
	[zskview_yes] [char](10) NULL,
	[zskzt] [char](10) NULL,
	[flfgadd] [char](10) NULL,
	[flfgmod] [char](10) NULL,
	[flfgdel] [char](10) NULL,
	[flfgview] [char](10) NULL,
	[flfgview_yes] [char](10) NULL,
	[flfgzt] [char](10) NULL,
	[r_wlz_add] [char](10) NULL,
	[r_wlz_mod] [char](10) NULL,
	[r_wlz_del] [char](10) NULL,
	[r_wlz_view] [char](10) NULL,
	[r_wlz_view_yes] [char](10) NULL,
	[r_wlz_zt] [char](10) NULL,
	[zjadd] [char](10) NULL,
	[zjmod] [char](10) NULL,
	[zjdel] [char](10) NULL,
	[zjview] [char](10) NULL,
	[zjview_yes] [char](10) NULL,
	[zjzt] [char](10) NULL,
	[r_wage_add] [char](10) NULL,
	[r_wage_mod] [char](10) NULL,
	[r_wage_del] [char](10) NULL,
	[r_wage_view] [char](10) NULL,
	[r_wage_zt] [char](10) NULL,
	[fkhzadd] [char](10) NULL,
	[fkhzmod] [char](10) NULL,
	[fkhzdel] [char](10) NULL,
	[fkhzview] [char](10) NULL,
	[fkhzview_yes] [char](10) NULL,
	[fkhzzt] [char](10) NULL,
	[skhzadd] [char](10) NULL,
	[skhzmod] [char](10) NULL,
	[skhzdel] [char](10) NULL,
	[skhzview] [char](10) NULL,
	[skhzview_yes] [char](10) NULL,
	[skhzzt] [char](10) NULL,
	[fkmod] [char](10) NULL,
	[fkdel] [char](10) NULL,
	[fkview] [char](10) NULL,
	[fkview_yes] [char](10) NULL,
	[fkzt] [char](10) NULL,
	[r_wx_gt_view] [char](10) NULL,
	[r_wx_gt_zt] [char](10) NULL,
	[skmod] [char](10) NULL,
	[skdel] [char](10) NULL,
	[skview] [char](10) NULL,
	[skview_yes] [char](10) NULL,
	[skzt] [char](10) NULL,
	[qkmod] [char](10) NULL,
	[qkdel] [char](10) NULL,
	[qkview] [char](10) NULL,
	[qkview_yes] [char](10) NULL,
	[qkzt] [char](10) NULL,
	[r_thje_mod] [char](10) NULL,
	[r_thje_del] [char](10) NULL,
	[r_thje_view] [char](10) NULL,
	[r_thje_view_yes] [char](10) NULL,
	[r_thje_zt] [char](10) NULL,
	[intadd] [char](10) NULL,
	[intmod] [char](10) NULL,
	[intdel] [char](10) NULL,
	[intview] [char](10) NULL,
	[intview_yes] [char](10) NULL,
	[intzt] [char](10) NULL,
	[outadd] [char](10) NULL,
	[outmod] [char](10) NULL,
	[outdel] [char](10) NULL,
	[outview] [char](10) NULL,
	[outview_yes] [char](10) NULL,
	[outzt] [char](10) NULL,
	[r_dzzx_zt] [char](10) NULL,
	[r_xscp_zt] [char](10) NULL,
	[syszt] [char](10) NULL,
	[jcfxzt] [char](10) NULL,
	[custzyzt] [char](10) NULL,
	[cgxjxx] [nchar](10) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[requireHandle]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[requireHandle](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[requireId] [int] NULL,
	[username] [varchar](50) NULL,
	[handleText] [varchar](500) NULL,
	[handleTime] [datetime] NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[require]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[require](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[username] [varchar](50) NULL,
	[dept] [varchar](50) NULL,
	[title] [varchar](50) NULL,
	[content] [varchar](5000) NULL,
	[requireTime] [datetime] NULL,
	[state] [varchar](10) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[sam_ware]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[sam_ware](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[pro_number] [nvarchar](50) NULL,
	[pro_name] [nvarchar](90) NULL,
	[pro_supplier] [nvarchar](50) NULL,
	[pro_model] [nvarchar](90) NULL,
	[pro_ms] [nvarchar](90) NULL,
	[man] [char](10) NULL,
	[pro_datetime] [char](30) NULL,
	[remark] [nvarchar](200) NULL,
	[states] [char](10) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[sam_sp]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[sam_sp](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[dept] [char](30) NULL,
	[role] [char](15) NULL,
	[price_min] [int] NULL,
	[price_max] [int] NULL,
	[dd_man] [char](15) NOT NULL,
	[fif] [char](10) NOT NULL,
	[fspman] [varchar](50) NOT NULL,
	[remark] [varchar](350) NULL,
	[dd_date] [datetime] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[sam_pro]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[sam_pro](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ddid] [varchar](50) NOT NULL,
	[epro] [varchar](150) NOT NULL,
	[num] [int] NOT NULL,
	[fypronum] [int] NULL,
	[unit] [varchar](50) NULL,
	[cpro] [varchar](150) NULL,
	[cpro2] [nvarchar](50) NULL,
	[rale_types] [varchar](50) NULL,
	[rale] [decimal](10, 2) NULL,
	[supplier] [varchar](150) NULL,
	[pricehb] [nvarchar](30) NOT NULL,
	[salejg] [decimal](18, 2) NOT NULL,
	[selljg] [decimal](18, 2) NULL,
	[money] [varchar](50) NULL,
	[fyproall] [char](15) NULL,
	[wid] [char](10) NULL,
	[pro_r_date] [nvarchar](50) NULL,
	[pro_sr_date] [nvarchar](50) NULL,
	[remark] [nvarchar](350) NULL,
	[pro_snum] [int] NULL,
	[pro_sc_num] [int] NULL,
	[xj_date] [nvarchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[sam_dhpro]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[sam_dhpro](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ddid] [varchar](50) NOT NULL,
	[epro] [varchar](150) NOT NULL,
	[num] [int] NOT NULL,
	[fypronum] [int] NULL,
	[unit] [varchar](50) NULL,
	[cpro] [varchar](150) NULL,
	[cpro2] [nvarchar](50) NULL,
	[rale_types] [varchar](50) NULL,
	[rale] [decimal](10, 2) NULL,
	[supplier] [varchar](150) NULL,
	[pricehb] [nvarchar](30) NOT NULL,
	[salejg] [decimal](18, 6) NOT NULL,
	[selljg] [decimal](18, 6) NULL,
	[money] [varchar](50) NULL,
	[fyproall] [char](15) NULL,
	[wid] [char](10) NULL,
	[pro_r_date] [nvarchar](50) NULL,
	[pro_sr_date] [nvarchar](50) NULL,
	[remark] [nvarchar](350) NULL,
	[pro_snum] [int] NULL,
	[pro_sc_num] [int] NULL,
	[pro_gg] [varchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[sales_bank]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[sales_bank](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[bname] [varchar](50) NULL,
	[bn] [nvarchar](200) NULL,
	[baddress] [varchar](50) NULL,
	[ba] [nvarchar](200) NULL,
	[scode] [varchar](50) NULL,
	[sc] [nvarchar](200) NULL,
	[bename] [varchar](50) NULL,
	[ben] [nvarchar](200) NULL,
	[ano] [varchar](50) NULL,
	[an] [nvarchar](200) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[sale_tc]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[sale_tc](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[s_name] [char](20) NULL,
	[s_m_min] [int] NULL,
	[s_m_max] [int] NULL,
	[s_m_tc] [decimal](18, 3) NULL,
	[s_y_min] [int] NULL,
	[s_y_max] [int] NULL,
	[s_y_tc] [decimal](18, 4) NULL,
	[remark] [nvarchar](350) NULL,
	[s_date] [datetime] NULL,
	[s_types] [nvarchar](50) NULL,
	[s_m_tc2] [int] NULL,
	[s_m_rate_min] [decimal](18, 3) NULL,
	[s_m_rate_max] [decimal](18, 3) NULL,
	[s_m_rate_tc] [decimal](18, 2) NULL,
	[s_m_rote_min] [int] NULL,
	[s_m_rote_max] [int] NULL,
	[s_m_rote_money] [int] NULL,
	[s_y_rate_min] [decimal](18, 3) NULL,
	[s_y_rate_max] [decimal](18, 3) NULL,
	[s_y_rate_tc] [decimal](18, 2) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[rulestable]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[rulestable](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[titel] [nvarchar](150) NULL,
	[content] [ntext] NULL,
	[username] [nvarchar](50) NULL,
	[fvdate] [nvarchar](50) NULL,
	[dept] [nvarchar](50) NULL,
	[deptjb] [nvarchar](50) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[role_right_rel]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[role_right_rel](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[roleId] [int] NOT NULL,
	[rightId] [int] NOT NULL,
 CONSTRAINT [PK_role_right_rel] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[role_right]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[role_right](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[role_id] [int] NULL,
	[role_name] [varchar](50) NULL,
	[right_name] [varchar](50) NULL,
	[right_add] [int] NULL,
	[right_update] [int] NULL,
	[right_delete] [int] NULL,
	[right_enable] [int] NULL,
	[pro_addr] [varchar](15) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[role]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[role](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[role_name] [nvarchar](50) NULL,
	[remark] [nvarchar](200) NULL,
	[role_date] [datetime] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[invoice]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[invoice](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ddid] [nvarchar](50) NULL,
	[fyid] [nvarchar](50) NULL,
	[intoname] [nvarchar](80) NULL,
	[po] [nvarchar](50) NULL,
	[innumber] [nvarchar](50) NULL,
	[indate] [nvarchar](50) NULL,
	[inddnumber] [nvarchar](50) NULL,
	[startport] [nvarchar](80) NULL,
	[minport] [nvarchar](80) NULL,
	[aimport] [nvarchar](80) NULL,
	[inpayment] [nvarchar](100) NULL,
	[indates] [nvarchar](50) NULL,
	[incredit] [nvarchar](50) NULL,
	[inmarks] [nvarchar](100) NULL,
	[states] [nvarchar](15) NULL,
	[scno] [nvarchar](50) NULL,
	[htnumber] [nvarchar](50) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[sample_dh]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[sample_dh](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[number] [varchar](50) NOT NULL,
	[man] [varchar](30) NOT NULL,
	[sub] [nvarchar](60) NULL,
	[coname] [varchar](60) NOT NULL,
	[yj] [varchar](50) NOT NULL,
	[money] [nvarchar](50) NOT NULL,
	[habitus] [varchar](10) NOT NULL,
	[datetime] [datetime] NOT NULL,
	[senddate] [nvarchar](100) NULL,
	[delivery_terms] [nvarchar](200) NULL,
	[delivery_date] [nvarchar](200) NULL,
	[airport] [varchar](200) NULL,
	[tbyq] [nvarchar](200) NULL,
	[remarks] [text] NULL,
	[state] [varchar](50) NOT NULL,
	[spman] [varchar](50) NULL,
	[spdate] [nvarchar](50) NULL,
	[spyj] [nvarchar](300) NULL,
	[fif] [char](10) NULL,
	[cwman] [char](15) NULL,
	[cwdate] [nvarchar](50) NULL,
	[cwyj] [varchar](300) NULL,
	[dept] [char](30) NULL,
	[deptjb] [char](10) NULL,
	[linkman] [nvarchar](50) NULL,
	[fveight] [nvarchar](50) NULL,
	[insurance] [decimal](18, 2) NULL,
	[in_no] [int] NULL,
	[syyj] [nvarchar](200) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[sample]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[sample](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[number] [varchar](50) NOT NULL,
	[man] [varchar](30) NOT NULL,
	[sub] [nvarchar](60) NULL,
	[coname] [varchar](60) NOT NULL,
	[yj] [varchar](50) NOT NULL,
	[money] [char](10) NOT NULL,
	[habitus] [varchar](10) NOT NULL,
	[datetime] [datetime] NOT NULL,
	[senddate] [nvarchar](100) NULL,
	[delivery_terms] [nvarchar](200) NULL,
	[delivery_date] [nvarchar](200) NULL,
	[airport] [nvarchar](200) NULL,
	[tbyq] [nvarchar](200) NULL,
	[remarks] [ntext] NULL,
	[state] [varchar](50) NOT NULL,
	[spman] [varchar](50) NULL,
	[spdate] [nvarchar](50) NULL,
	[spyj] [nvarchar](300) NULL,
	[fif] [char](10) NULL,
	[cwman] [char](15) NULL,
	[cwdate] [nvarchar](50) NULL,
	[cwyj] [varchar](300) NULL,
	[dept] [char](30) NULL,
	[deptjb] [char](10) NULL,
	[linkman] [nvarchar](50) NULL,
	[fveight] [decimal](18, 2) NULL,
	[insurance] [decimal](18, 2) NULL,
	[in_no] [int] NULL,
	[syyj] [nvarchar](200) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[sjsp]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[sjsp](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[dept] [char](30) NULL,
	[role] [char](15) NULL,
	[dd_man] [char](15) NOT NULL,
	[fif] [char](10) NOT NULL,
	[fspman] [varchar](50) NOT NULL,
	[firspif] [char](10) NULL,
	[firspman] [char](20) NULL,
	[remark] [varchar](350) NULL,
	[dd_date] [datetime] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[shtsp]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[shtsp](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[dept] [char](30) NULL,
	[role] [char](15) NULL,
	[price_min] [int] NULL,
	[price_max] [int] NULL,
	[ifsp] [char](10) NULL,
	[dd_man] [char](15) NOT NULL,
	[iffsp] [char](10) NULL,
	[fsp_man] [char](20) NULL,
	[firsp] [char](10) NULL,
	[fir_man] [char](20) NULL,
	[remark] [varchar](350) NULL,
	[dd_date] [datetime] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[serverquest]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[serverquest](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[record] [varchar](50) NULL,
	[fwlx] [varchar](50) NULL,
	[name] [varchar](50) NULL,
	[tel] [varchar](50) NULL,
	[coname] [varchar](150) NULL,
	[import] [varchar](50) NULL,
	[mqwc] [varchar](50) NULL,
	[state] [varchar](50) NULL,
	[descripts] [text] NULL,
	[starttime] [varchar](50) NULL,
	[endtime] [varchar](50) NULL,
	[deptjb] [varchar](50) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[sendmail]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[sendmail](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[mail_to] [nvarchar](100) NULL,
	[mail_to2] [nvarchar](100) NULL,
	[mail_to3] [nvarchar](100) NULL,
	[mail_sub] [nvarchar](500) NULL,
	[mail_nr] [ntext] NULL,
	[mail_man] [char](10) NULL,
	[deptjb] [char](15) NULL,
	[dept] [nvarchar](50) NULL,
	[mail_datetime] [char](30) NULL,
	[states] [char](10) NULL,
	[form_to] [nvarchar](200) NULL,
	[form_to2] [nvarchar](200) NULL,
	[form_to3] [nvarchar](200) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[secprofl]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[secprofl](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[firstkind] [nvarchar](10) NULL,
	[proflname] [nvarchar](45) NULL,
	[eprofl] [nvarchar](45) NULL,
	[price_gk] [decimal](18, 2) NULL,
	[price_jysj] [decimal](18, 2) NULL,
	[price_min] [decimal](18, 2) NULL,
	[price_dljg] [decimal](18, 2) NULL,
	[price_wf] [decimal](18, 2) NULL,
	[remark] [nvarchar](150) NULL,
	[snumber] [char](8) NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[seacarriage]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[seacarriage](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[aimport] [nvarchar](80) NULL,
	[startport] [nvarchar](80) NULL,
	[hdcompany] [nvarchar](100) NULL,
	[hycompany] [nvarchar](150) NULL,
	[dxdj] [nvarchar](50) NULL,
	[kjg] [nvarchar](50) NULL,
	[creatdate] [nvarchar](50) NULL,
	[enddate] [nvarchar](50) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[scrwtable]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[scrwtable](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[rztname] [nvarchar](60) NOT NULL,
	[cjsj] [nvarchar](20) NOT NULL,
	[wcsj] [nvarchar](20) NOT NULL,
	[zxyg] [nvarchar](20) NOT NULL,
	[bzr] [nvarchar](20) NOT NULL,
	[fy] [nvarchar](20) NOT NULL,
	[fsfy] [nvarchar](20) NOT NULL,
	[bz] [ntext] NOT NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[thsp_supplier]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[thsp_supplier](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[dept] [char](30) NULL,
	[role] [char](15) NULL,
	[dd_man] [char](15) NOT NULL,
	[fif] [char](10) NOT NULL,
	[fspman] [varchar](50) NOT NULL,
	[firspif] [char](10) NULL,
	[firspman] [char](20) NULL,
	[remark] [varchar](350) NULL,
	[dd_date] [datetime] NOT NULL,
	[price_min] [decimal](18, 0) NULL,
	[price_max] [decimal](18, 0) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[thsp]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[thsp](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[dept] [char](30) NULL,
	[role] [char](15) NULL,
	[dd_man] [char](15) NOT NULL,
	[fif] [char](10) NOT NULL,
	[fspman] [varchar](50) NOT NULL,
	[firspif] [char](10) NULL,
	[firspman] [char](20) NULL,
	[remark] [varchar](350) NULL,
	[dd_date] [datetime] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[th_table_supplier]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[th_table_supplier](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[number] [varchar](50) NOT NULL,
	[man] [varchar](30) NOT NULL,
	[xiaoshou] [nvarchar](60) NULL,
	[sub] [nvarchar](60) NULL,
	[coname] [varchar](60) NOT NULL,
	[co_number] [nvarchar](20) NULL,
	[money] [char](10) NOT NULL,
	[habitus] [varchar](10) NOT NULL,
	[datetime] [datetime] NOT NULL,
	[remarks] [nvarchar](200) NULL,
	[state] [varchar](10) NOT NULL,
	[spman] [varchar](50) NULL,
	[spdate] [nvarchar](50) NULL,
	[spyj] [nvarchar](300) NULL,
	[fif] [char](10) NULL,
	[cwman] [char](15) NULL,
	[cwdate] [nvarchar](50) NULL,
	[cwyj] [varchar](300) NULL,
	[firspif] [char](10) NULL,
	[firspman] [char](15) NULL,
	[fspyj] [nvarchar](300) NULL,
	[dept] [char](30) NULL,
	[deptjb] [char](10) NULL,
	[in_no] [int] NULL,
	[w_remark] [nvarchar](350) NULL,
	[skfs] [varchar](50) NULL,
	[colxr] [varchar](max) NULL,
	[coaddr] [varchar](max) NULL,
	[cotel] [varchar](max) NULL,
	[cofax] [varchar](max) NULL,
	[xsman] [varchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[th_table]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[th_table](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[number] [varchar](50) NOT NULL,
	[man] [varchar](30) NOT NULL,
	[sub] [nvarchar](60) NULL,
	[coname] [varchar](60) NOT NULL,
	[senddate] [nvarchar](20) NULL,
	[money] [char](10) NOT NULL,
	[habitus] [varchar](10) NOT NULL,
	[datetime] [datetime] NOT NULL,
	[remarks] [ntext] NULL,
	[state] [varchar](10) NOT NULL,
	[spman] [varchar](50) NULL,
	[spdate] [nvarchar](50) NULL,
	[spyj] [nvarchar](300) NULL,
	[fif] [char](10) NULL,
	[cwman] [char](15) NULL,
	[cwdate] [nvarchar](50) NULL,
	[cwyj] [varchar](300) NULL,
	[firspif] [char](10) NULL,
	[firspman] [char](15) NULL,
	[fspyj] [nvarchar](300) NULL,
	[dept] [char](30) NULL,
	[deptjb] [char](10) NULL,
	[in_no] [int] NULL,
	[w_remark] [nvarchar](350) NULL,
	[return_way] [varchar](50) NULL,
	[payway] [varchar](50) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[th_supplier_out_bind]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[th_supplier_out_bind](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[th_number] [varchar](20) NULL,
	[ddid] [int] NULL,
	[wid] [int] NULL,
	[bind_time] [datetime] NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[th_pro_supplier]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[th_pro_supplier](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ddid] [int] NOT NULL,
	[epro] [varchar](150) NOT NULL,
	[num] [int] NOT NULL,
	[fypronum] [char](10) NULL,
	[unit] [varchar](50) NULL,
	[cpro] [varchar](150) NULL,
	[rale_types] [varchar](50) NULL,
	[rale] [decimal](10, 2) NULL,
	[supplier] [varchar](150) NULL,
	[pricehb] [nvarchar](30) NOT NULL,
	[salejg] [decimal](18, 4) NOT NULL,
	[selljg] [decimal](18, 4) NULL,
	[money] [varchar](50) NULL,
	[fyproall] [char](15) NULL,
	[wid] [varchar](50) NULL,
	[s_num] [int] NULL,
	[s_c_num] [int] NULL,
	[s_tr_date] [nvarchar](50) NULL,
	[fy_states] [char](150) NULL,
	[th_number] [varchar](50) NULL,
	[cg_number] [varchar](50) NULL,
	[remark] [varchar](350) NULL,
	[csdhs] [nvarchar](max) NULL,
	[th_num] [int] NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[th_pro]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[th_pro](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ddid] [varchar](50) NOT NULL,
	[epro] [varchar](150) NOT NULL,
	[num] [int] NOT NULL,
	[fypronum] [varchar](50) NULL,
	[unit] [varchar](50) NULL,
	[cpro] [varchar](150) NULL,
	[rale_types] [varchar](50) NULL,
	[rale] [decimal](10, 2) NULL,
	[supplier] [varchar](150) NULL,
	[pricehb] [nvarchar](30) NOT NULL,
	[salejg] [decimal](18, 2) NOT NULL,
	[selljg] [decimal](18, 2) NULL,
	[money] [varchar](50) NULL,
	[fyproall] [char](15) NULL,
	[wid] [char](10) NULL,
	[s_num] [int] NULL,
	[s_c_num] [int] NULL,
	[s_tr_date] [nvarchar](50) NULL,
	[fy_states] [char](150) NULL,
	[remark] [nvarchar](500) NULL,
	[ddpro_id] [int] NULL,
	[subscribe_id] [int] NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[th_info]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[th_info](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[operateTime] [datetime] NULL,
	[operateMan] [varchar](100) NULL,
	[pro_model] [varchar](100) NULL,
	[remark] [ntext] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[talk_e]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[talk_e](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[to_man] [nvarchar](50) NULL,
	[send_man] [nvarchar](50) NULL,
	[dept] [nvarchar](90) NULL,
	[deptjb] [char](20) NULL,
	[t_datetime] [nvarchar](50) NULL,
	[talk_nr] [ntext] NULL,
	[ifr] [char](10) NULL,
	[r_datetime] [nvarchar](50) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[system_info]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[system_info](
	[companyName] [varchar](50) NULL,
	[copyright] [varchar](500) NULL,
	[version] [varchar](10) NULL,
	[website] [varchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[supplier_attachment]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[supplier_attachment](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[supplierid] [int] NULL,
	[filename] [varchar](200) NULL,
	[filepath] [varchar](320) NULL,
	[remark] [ntext] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[supplier]    Script Date: 08/20/2015 22:31:19 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[supplier](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[co_number] [char](30) NOT NULL,
	[coname] [varchar](150) NOT NULL,
	[coaddr] [varchar](150) NULL,
	[copost] [varchar](50) NULL,
	[city] [varchar](50) NULL,
	[country] [varchar](50) NULL,
	[province] [varchar](50) NULL,
	[cofrdb] [varchar](50) NULL,
	[cozzxs] [varchar](50) NULL,
	[cozczb] [varchar](50) NULL,
	[coyyzz] [varchar](50) NULL,
	[cotypes] [varchar](50) NULL,
	[tradetypes] [nvarchar](50) NULL,
	[cokhjb] [varchar](50) NULL,
	[cokhyh] [varchar](150) NULL,
	[coyhzh] [varchar](50) NULL,
	[coclrq] [varchar](50) NULL,
	[ifjckq] [varchar](50) NULL,
	[cotel] [varchar](50) NULL,
	[cofax] [varchar](50) NULL,
	[codzyj] [varchar](50) NULL,
	[conet] [varchar](50) NULL,
	[cosyhb] [varchar](50) NULL,
	[cojsfs] [varchar](50) NULL,
	[nshm] [varchar](50) NULL,
	[number] [varchar](50) NULL,
	[username] [varchar](50) NULL,
	[dept] [varchar](50) NULL,
	[deptjb] [char](10) NULL,
	[modman] [varchar](50) NULL,
	[mod_date] [varchar](50) NULL,
	[share] [varchar](50) NULL,
	[yearearning] [varchar](50) NULL,
	[valueco] [nvarchar](50) NULL,
	[rg_date] [nvarchar](50) NULL,
	[annual_sales] [nvarchar](200) NULL,
	[sales_ability] [nvarchar](200) NULL,
	[qlty_control] [nvarchar](200) NULL,
	[companymt] [nvarchar](200) NULL,
	[scale] [nvarchar](4000) NULL,
	[warehouse] [nvarchar](200) NULL,
	[describee] [nvarchar](4000) NULL,
	[in_no] [int] NULL,
	[certification] [varchar](30) NULL,
	[bank_addr] [ntext] NULL,
	[swift_code] [varchar](50) NULL,
	[iban] [varchar](50) NULL,
	[route] [varchar](50) NULL,
	[bic] [varchar](50) NULL,
	[receiver] [varchar](50) NULL,
	[receiver_tel] [varchar](50) NULL,
	[receiver_addr] [varchar](50) NULL,
	[freight] [varchar](50) NULL,
	[express_company] [varchar](50) NULL,
	[acct] [varchar](50) NULL,
	[service_type] [varchar](50) NULL,
	[pay_type] [varchar](50) NULL,
	[pf] [float] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[documentsp]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[documentsp](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[dept] [char](30) NULL,
	[role] [char](15) NULL,
	[dd_man] [char](15) NOT NULL,
	[fif] [char](10) NOT NULL,
	[fspman] [varchar](50) NOT NULL,
	[firspif] [char](10) NULL,
	[firspman] [char](20) NULL,
	[remark] [varchar](350) NULL,
	[dd_date] [datetime] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[dhtransportation]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[dhtransportation](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ddid] [nvarchar](50) NOT NULL,
	[fyid] [nvarchar](50) NOT NULL,
	[invoice] [varchar](50) NOT NULL,
	[subscribe] [varchar](50) NOT NULL,
	[coname] [varchar](100) NOT NULL,
	[trans_com] [varchar](100) NULL,
	[aimport] [varchar](100) NULL,
	[fy_num] [nvarchar](50) NULL,
	[fy_yf] [decimal](18, 2) NULL,
	[fy_bf] [decimal](18, 2) NULL,
	[fy_js] [nvarchar](50) NULL,
	[fy_sz] [nvarchar](50) NULL,
	[transportation] [varchar](90) NULL,
	[mode] [nvarchar](50) NULL,
	[datet] [int] NULL,
	[mbdate] [varchar](50) NULL,
	[sjdate] [varchar](50) NULL,
	[linkman] [varchar](50) NULL,
	[linktel] [varchar](50) NULL,
	[sendcompany] [nvarchar](100) NULL,
	[slinkman] [nvarchar](50) NULL,
	[slinktel] [nvarchar](50) NULL,
	[sate] [varchar](50) NULL,
	[sale_man] [char](15) NULL,
	[sale_dept] [char](30) NULL,
	[deptjb] [char](10) NULL,
	[remark] [ntext] NULL,
	[co_number] [char](30) NULL,
	[tr_types] [char](15) NULL,
	[fy_date] [char](15) NULL,
	[yf_types] [char](15) NULL,
	[yf_money] [decimal](18, 2) NULL,
	[in_no] [int] NULL,
	[tr_print] [char](10) NULL,
	[pro_addr] [nvarchar](50) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[dhsp]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[dhsp](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[dept] [char](30) NULL,
	[role] [char](15) NULL,
	[dd_man] [char](15) NOT NULL,
	[fif] [char](10) NOT NULL,
	[fspman] [varchar](50) NOT NULL,
	[remark] [varchar](350) NULL,
	[dd_date] [datetime] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[dept_restrain]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[dept_restrain](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[dept] [nvarchar](50) NULL,
	[deptjb] [int] NULL,
	[qzadd] [char](10) NULL,
	[qzmod] [char](10) NOT NULL,
	[qzdel] [char](10) NOT NULL,
	[qzview] [char](10) NULL,
	[qzzt] [char](10) NULL,
	[xhadd] [char](10) NULL,
	[xhmod] [char](10) NOT NULL,
	[xhdel] [char](10) NOT NULL,
	[xhview] [char](10) NULL,
	[xhzt] [char](10) NULL,
	[linkadd] [char](10) NULL,
	[linkmod] [char](10) NOT NULL,
	[linkdel] [char](10) NOT NULL,
	[linkview] [char](10) NULL,
	[linkzt] [char](10) NULL,
	[hzadd] [char](10) NULL,
	[hzmod] [char](10) NULL,
	[hzdel] [char](10) NULL,
	[hzview] [char](10) NULL,
	[hzzt] [char](10) NULL,
	[supadd] [char](10) NULL,
	[supmod] [char](10) NULL,
	[supdel] [char](10) NULL,
	[supview] [char](10) NULL,
	[supzt] [char](10) NULL,
	[actadd] [char](10) NULL,
	[actmod] [char](10) NULL,
	[actdel] [char](10) NULL,
	[actview] [char](10) NULL,
	[actzt] [char](10) NULL,
	[opadd] [char](10) NULL,
	[opmod] [char](10) NULL,
	[opdel] [char](10) NULL,
	[opview] [char](10) NULL,
	[opzt] [char](10) NULL,
	[quoteadd] [char](10) NULL,
	[quotemod] [char](10) NULL,
	[quotedel] [char](10) NULL,
	[quoteview] [char](10) NULL,
	[quotezt] [char](10) NULL,
	[proadd] [char](10) NULL,
	[promod] [char](10) NULL,
	[prodel] [char](10) NULL,
	[proview] [char](10) NULL,
	[prozt] [char](10) NULL,
	[cgadd] [char](10) NULL,
	[cgmod] [char](10) NULL,
	[cgdel] [char](10) NULL,
	[cgview] [char](10) NULL,
	[cgzt] [char](10) NULL,
	[tradd] [char](10) NULL,
	[trmod] [char](10) NULL,
	[trdel] [char](10) NULL,
	[trview] [char](10) NULL,
	[trzt] [char](10) NULL,
	[hdadd] [char](10) NULL,
	[hdmod] [char](10) NULL,
	[hddel] [char](10) NULL,
	[hdview] [char](10) NULL,
	[hdzt] [char](10) NULL,
	[workadd] [char](10) NULL,
	[workmod] [char](10) NULL,
	[workdel] [char](10) NULL,
	[workview] [char](10) NULL,
	[workzt] [char](10) NULL,
	[xmadd] [char](10) NULL,
	[xmmod] [char](10) NULL,
	[xmdel] [char](10) NULL,
	[xmview] [char](10) NULL,
	[xmzt] [char](10) NULL,
	[subadd] [char](10) NULL,
	[submod] [char](10) NULL,
	[subdel] [char](10) NULL,
	[subview] [char](10) NULL,
	[subzt] [char](10) NULL,
	[manadd] [char](10) NULL,
	[manmod] [char](10) NULL,
	[mandel] [char](10) NULL,
	[manview] [char](10) NULL,
	[manzt] [char](10) NULL,
	[rwadd] [char](10) NULL,
	[rwmod] [char](10) NULL,
	[rwdel] [char](10) NULL,
	[rwview] [char](10) NULL,
	[rwzt] [char](10) NULL,
	[fyadd] [char](10) NULL,
	[fymod] [char](10) NULL,
	[fydel] [char](10) NULL,
	[fyview] [char](10) NULL,
	[fyzt] [char](10) NULL,
	[hhadd] [char](10) NULL,
	[hhmod] [char](10) NULL,
	[hhdel] [char](10) NULL,
	[hhview] [char](10) NULL,
	[hhzt] [char](10) NULL,
	[thadd] [char](10) NULL,
	[thmod] [char](10) NULL,
	[thdel] [char](10) NULL,
	[thview] [char](10) NULL,
	[thzt] [char](10) NULL,
	[serveradd] [char](10) NULL,
	[servermod] [char](10) NULL,
	[serverdel] [char](10) NULL,
	[serverview] [char](10) NULL,
	[serverzt] [char](10) NULL,
	[tsadd] [char](10) NULL,
	[tsmod] [char](10) NULL,
	[tsdel] [char](10) NULL,
	[tsview] [char](10) NULL,
	[tszt] [char](10) NULL,
	[wxadd] [char](10) NULL,
	[wxmod] [char](10) NULL,
	[wxdel] [char](10) NULL,
	[wxview] [char](10) NULL,
	[wxzt] [char](10) NULL,
	[mactadd] [char](10) NULL,
	[mactmod] [char](10) NULL,
	[mactdel] [char](10) NULL,
	[mactview] [char](10) NULL,
	[mactzt] [char](10) NULL,
	[jzdsadd] [char](10) NULL,
	[jzdsmod] [char](10) NULL,
	[jzdsdel] [char](10) NULL,
	[jzdsview] [char](10) NULL,
	[jzdszt] [char](10) NULL,
	[zskadd] [char](10) NULL,
	[zskmod] [char](10) NULL,
	[zskdel] [char](10) NULL,
	[zskview] [char](10) NULL,
	[zskzt] [char](10) NULL,
	[flfgadd] [char](10) NULL,
	[flfgmod] [char](10) NULL,
	[flfgdel] [char](10) NULL,
	[flfgview] [char](10) NULL,
	[flfgzt] [char](10) NULL,
	[zjadd] [char](10) NULL,
	[zjmod] [char](10) NULL,
	[zjdel] [char](10) NULL,
	[zjview] [char](10) NULL,
	[zjzt] [char](10) NULL,
	[fkhzadd] [char](10) NULL,
	[fkhzmod] [char](10) NULL,
	[fkhzdel] [char](10) NULL,
	[fkhzview] [char](10) NULL,
	[fkhzzt] [char](10) NULL,
	[skhzadd] [char](10) NULL,
	[skhzmod] [char](10) NULL,
	[skhzdel] [char](10) NULL,
	[skhzview] [char](10) NULL,
	[skhzzt] [char](10) NULL,
	[fkadd] [char](10) NULL,
	[fkmod] [char](10) NULL,
	[fkdel] [char](10) NULL,
	[fkview] [char](10) NULL,
	[fkzt] [char](10) NULL,
	[skadd] [char](10) NULL,
	[skmod] [char](10) NULL,
	[skdel] [char](10) NULL,
	[skview] [char](10) NULL,
	[skzt] [char](10) NULL,
	[qkadd] [char](10) NULL,
	[qkmod] [char](10) NULL,
	[qkdel] [char](10) NULL,
	[qkview] [char](10) NULL,
	[qkzt] [char](10) NULL,
	[intadd] [char](10) NULL,
	[intmod] [char](10) NULL,
	[intdel] [char](10) NULL,
	[intview] [char](10) NULL,
	[intzt] [char](10) NULL,
	[outadd] [char](10) NULL,
	[outmod] [char](10) NULL,
	[outdel] [char](10) NULL,
	[outview] [char](10) NULL,
	[outzt] [char](10) NULL,
	[sysadd] [char](10) NULL,
	[sysmod] [char](10) NULL,
	[sysdel] [char](10) NULL,
	[sysview] [char](10) NULL,
	[syszt] [char](10) NULL,
	[jcfx] [char](10) NULL,
	[custzyzt] [char](10) NULL,
	[salestc] [int] NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[department]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[department](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[departname] [nvarchar](80) NOT NULL,
	[dept_types] [int] NULL,
	[dept_code] [nvarchar](50) NOT NULL,
	[remark] [ntext] NULL,
	[leader] [varchar](50) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ddtransportation]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ddtransportation](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ddid] [varchar](50) NOT NULL,
	[coname] [varchar](150) NOT NULL,
	[invoice] [nvarchar](20) NOT NULL,
	[transportation] [varchar](150) NOT NULL,
	[aimport] [varchar](80) NULL,
	[startport] [varchar](80) NULL,
	[package] [varchar](150) NULL,
	[linkman] [varchar](60) NULL,
	[linktel] [nvarchar](50) NULL,
	[sendcompany] [nvarchar](80) NULL,
	[slinkman] [nvarchar](50) NULL,
	[slinktel] [nvarchar](50) NULL,
	[remark] [ntext] NULL,
	[co_number] [char](30) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ddsp]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ddsp](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[dept] [nvarchar](50) NULL,
	[role] [char](15) NULL,
	[price_min] [decimal](18, 0) NULL,
	[price_max] [decimal](18, 0) NULL,
	[if_sp] [char](10) NULL,
	[dd_man] [char](15) NOT NULL,
	[fif] [char](10) NOT NULL,
	[fspman] [varchar](50) NOT NULL,
	[remark] [varchar](350) NULL,
	[dd_date] [datetime] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ddpro]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ddpro](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ddid] [varchar](50) NOT NULL,
	[epro] [varchar](150) NOT NULL,
	[num] [int] NOT NULL,
	[fypronum] [varchar](50) NULL,
	[unit] [varchar](50) NULL,
	[cpro] [varchar](150) NULL,
	[rale_types] [varchar](50) NULL,
	[rale] [decimal](10, 2) NULL,
	[supplier] [varchar](150) NULL,
	[pricehb] [nvarchar](30) NOT NULL,
	[salejg] [decimal](18, 6) NOT NULL,
	[selljg] [decimal](18, 6) NULL,
	[money] [varchar](50) NULL,
	[fyproall] [char](55) NULL,
	[wid] [nvarchar](90) NULL,
	[x_no] [nvarchar](90) NULL,
	[remark] [nvarchar](350) NULL,
	[s_num] [int] NULL,
	[s_c_num] [int] NULL,
	[s_tr_date] [char](15) NULL,
	[fy_states] [char](10) NULL,
	[p_check] [nvarchar](150) NULL,
	[money2] [decimal](18, 6) NULL,
	[mpn] [varchar](50) NULL,
	[pro_jz] [varchar](50) NULL,
	[pro_mz] [varchar](50) NULL,
	[pro_cc] [varchar](50) NULL,
	[pro_cd] [varchar](50) NULL,
	[th_num] [int] NULL,
	[hl] [varchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ddfypro]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ddfypro](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ddnumber] [nvarchar](20) NOT NULL,
	[fynumber] [nvarchar](20) NULL,
	[epro] [nvarchar](150) NULL,
	[cpro] [nvarchar](150) NULL,
	[num] [int] NULL,
	[unit] [nvarchar](50) NULL,
	[saleprice] [nvarchar](50) NULL,
	[salehb] [nvarchar](15) NULL,
	[rale_types] [nvarchar](50) NULL,
	[rale] [int] NULL,
	[supplier] [nvarchar](150) NULL,
	[remark] [text] NULL,
	[proid] [char](15) NULL,
	[wid] [char](15) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[dd_del_log]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[dd_del_log](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ddid] [varchar](50) NULL,
	[operator] [varchar](50) NULL,
	[operateTime] [datetime] NULL,
	[remark] [ntext] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[customer_gj]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[customer_gj](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[c_date] [datetime] NULL,
	[c_time] [char](15) NULL,
	[man] [char](10) NULL,
	[dept] [nvarchar](50) NULL,
	[deptjb] [char](10) NULL,
	[co_number] [char](30) NULL,
	[c_name] [nvarchar](100) NULL,
	[actypes] [nvarchar](50) NULL,
	[c_nr] [ntext] NULL,
	[remark] [ntext] NULL,
	[iftx] [char](10) NULL,
	[txtime] [datetime] NULL,
	[coid] [char](10) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[customer_fa]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[customer_fa](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[co_number] [char](15) NULL,
	[coname] [nvarchar](80) NULL,
	[cf_number] [nvarchar](50) NULL,
	[cf_name] [nvarchar](100) NULL,
	[cf_gy] [ntext] NULL,
	[remark] [varchar](300) NULL,
	[cf_date] [datetime] NOT NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[cust_sale_total]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cust_sale_total](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[man] [char](15) NULL,
	[coname] [nvarchar](200) NULL,
	[sub_num] [int] NULL,
	[pro_number] [nvarchar](50) NULL,
	[pro_num] [int] NULL,
	[pro_sale_money] [nvarchar](150) NULL,
	[pro_cg_money] [nvarchar](150) NULL,
	[pro_lr_money] [nvarchar](150) NULL,
	[pro_lr_rate] [nvarchar](150) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[credit_debit_sp]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[credit_debit_sp](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[dept] [char](30) NULL,
	[role] [char](15) NULL,
	[dd_man] [char](15) NOT NULL,
	[fif] [char](10) NOT NULL,
	[fspman] [varchar](50) NOT NULL,
	[remark] [varchar](350) NULL,
	[dd_date] [datetime] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[credit_debit]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[credit_debit](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[l_menber] [nvarchar](25) NOT NULL,
	[l_man] [nvarchar](25) NULL,
	[reference] [nvarchar](50) NULL,
	[l_topic] [nvarchar](80) NULL,
	[l_dept] [char](50) NULL,
	[l_deptjb] [char](10) NULL,
	[l_c_d] [nvarchar](90) NULL,
	[l_date] [datetime] NULL,
	[l_coname] [nvarchar](90) NULL,
	[co_number] [char](80) NULL,
	[l_sqje] [decimal](18, 4) NOT NULL,
	[l_sje] [decimal](18, 4) NULL,
	[l_hb] [char](10) NULL,
	[compendium] [nvarchar](300) NULL,
	[g_bank] [nvarchar](300) NULL,
	[g_banknum] [nvarchar](50) NULL,
	[remarks] [ntext] NOT NULL,
	[in_no] [int] NULL,
	[st] [int] NULL,
	[sy] [char](10) NULL,
	[km] [nvarchar](90) NULL,
	[kms] [nvarchar](90) NULL,
	[rale] [decimal](18, 5) NULL,
	[states] [char](20) NULL,
	[spman] [char](20) NULL,
	[spdate] [nvarchar](50) NULL,
	[spyj] [nvarchar](350) NULL,
	[fif] [char](10) NULL,
	[fspman] [char](20) NULL,
	[fspdate] [char](20) NULL,
	[fspyj] [nvarchar](350) NULL,
	[xsdh] [varchar](100) NULL,
	[cgdh] [varchar](100) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[credit]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[credit](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[number] [nvarchar](50) NULL,
	[coname] [nvarchar](150) NULL,
	[credit] [nvarchar](150) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[country]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[country](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[coun_name] [nvarchar](100) NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[contract]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[contract](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ddid] [varchar](50) NULL,
	[ddnumber] [varchar](50) NULL,
	[htnumber] [varchar](50) NULL,
	[supplier] [varchar](150) NULL,
	[sate] [varchar](50) NULL,
	[htspyj] [nvarchar](100) NULL,
	[man] [nvarchar](15) NULL,
	[qydate] [varchar](50) NULL,
	[companyname] [varchar](150) NULL,
	[companyaddr] [varchar](150) NULL,
	[companybm] [varchar](50) NULL,
	[companybank] [varchar](150) NULL,
	[companynumber] [varchar](50) NULL,
	[companytel] [varchar](50) NULL,
	[companyfax] [varchar](50) NULL,
	[companysh] [varchar](50) NULL,
	[colxr] [varchar](50) NULL,
	[companylxr] [varchar](50) NULL,
	[standard] [nvarchar](300) NULL,
	[jhaddr] [nvarchar](150) NULL,
	[jhdate] [nvarchar](50) NULL,
	[ystypes] [nvarchar](300) NULL,
	[backyq] [nvarchar](300) NULL,
	[supplierinfo] [nvarchar](300) NULL,
	[jsfs] [nvarchar](300) NULL,
	[htzxman] [nvarchar](50) NULL,
	[htzxdate] [nvarchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[company]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[company](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[companyname] [nvarchar](150) NULL,
	[companyname2] [nvarchar](150) NULL,
	[companyaddr] [nvarchar](150) NULL,
	[companyaddr2] [nvarchar](150) NULL,
	[companytel] [nvarchar](100) NULL,
	[companyfax] [nvarchar](100) NULL,
	[companybank] [nvarchar](100) NULL,
	[companybank2] [nvarchar](100) NULL,
	[bank_dm] [varchar](100) NULL,
	[bank_dm2] [varchar](100) NULL,
	[companynumber] [nvarchar](100) NULL,
	[companynumber2] [nvarchar](100) NULL,
	[com_bank_code] [nvarchar](120) NULL,
	[com_bank_code2] [nvarchar](120) NULL,
	[com_sy_code] [nvarchar](120) NULL,
	[com_sy_code2] [nvarchar](120) NULL,
	[com_sy_name] [nvarchar](120) NULL,
	[com_sy_name2] [nvarchar](120) NULL,
	[bankroll] [nvarchar](50) NULL,
	[companyman] [nvarchar](50) NULL,
	[companydm] [nvarchar](50) NULL,
	[companysh] [nvarchar](50) NULL,
	[companybm] [nvarchar](50) NULL,
	[companylxr] [nvarchar](50) NULL,
	[companyemail] [nvarchar](50) NULL,
	[companynet] [nvarchar](50) NULL,
	[username] [nvarchar](50) NULL,
	[datetime] [nvarchar](50) NULL,
	[remark] [ntext] NULL,
	[picpath] [nvarchar](50) NULL,
	[type] [varchar](50) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[client]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[client](
	[clientid] [int] IDENTITY(1,1) NOT NULL,
	[co_number] [char](15) NOT NULL,
	[coname] [varchar](150) NOT NULL,
	[coaddr] [varchar](160) NULL,
	[copost] [varchar](50) NULL,
	[city] [varchar](50) NULL,
	[province] [varchar](50) NULL,
	[cofrdb] [varchar](50) NULL,
	[cozzxs] [varchar](50) NULL,
	[cozczb] [varchar](50) NULL,
	[coyyzz] [varchar](50) NULL,
	[cotypes] [varchar](50) NULL,
	[tradetypes] [nvarchar](50) NULL,
	[cokhjb] [varchar](50) NULL,
	[cokhyh] [varchar](150) NULL,
	[coyhzh] [varchar](50) NULL,
	[coclrq] [varchar](50) NULL,
	[cotel] [varchar](50) NULL,
	[cofax] [varchar](50) NULL,
	[codzyj] [varchar](50) NULL,
	[conet] [varchar](50) NULL,
	[cosyhb] [varchar](50) NULL,
	[cojsfs] [varchar](50) NULL,
	[link_zw1] [char](30) NULL,
	[paydate] [nvarchar](50) NULL,
	[link_wap1] [char](30) NULL,
	[link_mail1] [nvarchar](90) NULL,
	[linkman2] [char](50) NULL,
	[link_zw2] [char](30) NULL,
	[link_tel2] [nvarchar](50) NULL,
	[link_wap2] [char](30) NULL,
	[link_mail2] [nvarchar](90) NULL,
	[linkman3] [char](50) NULL,
	[link_zw3] [char](30) NULL,
	[link_tel3] [nvarchar](50) NULL,
	[link_wap3] [char](30) NULL,
	[link_mail3] [nvarchar](90) NULL,
	[linkman4] [char](50) NULL,
	[link_zw4] [char](30) NULL,
	[link_tel4] [nvarchar](50) NULL,
	[link_wap4] [char](30) NULL,
	[link_mail4] [nvarchar](90) NULL,
	[linkman5] [char](50) NULL,
	[link_zw5] [char](30) NULL,
	[link_tel5] [nvarchar](50) NULL,
	[link_wap5] [char](30) NULL,
	[link_mail5] [nvarchar](90) NULL,
	[limited_credit] [nvarchar](50) NULL,
	[rg_date] [datetime] NULL,
	[nsnumber] [nvarchar](50) NULL,
	[number] [varchar](50) NULL,
	[yearearning] [varchar](50) NULL,
	[username] [varchar](50) NULL,
	[dept] [nvarchar](50) NULL,
	[deptjb] [char](30) NULL,
	[modman] [char](10) NULL,
	[mod_date] [datetime] NULL,
	[share] [varchar](50) NULL,
	[mproduct] [varchar](1000) NULL,
	[product] [nvarchar](260) NULL,
	[company_management] [varchar](360) NULL,
	[describee] [ntext] NULL,
	[in_no] [int] NULL,
	[co_code] [char](10) NULL,
	[bank_name] [ntext] NULL,
	[bank_addr] [ntext] NULL,
	[swift_code] [varchar](50) NULL,
	[iban] [varchar](50) NULL,
	[route] [varchar](50) NULL,
	[bic] [varchar](50) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[gathering_refund]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[gathering_refund](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[fyid] [char](50) NULL,
	[invoice] [varchar](50) NOT NULL,
	[orderform] [varchar](50) NOT NULL,
	[imoney] [decimal](18, 4) NULL,
	[ymoney] [decimal](18, 4) NULL,
	[smoney] [decimal](18, 4) NULL,
	[coname] [varchar](80) NULL,
	[bank] [decimal](18, 4) NULL,
	[bankaccounts] [varchar](80) NULL,
	[yjskdate] [varchar](30) NULL,
	[sjdate] [varchar](30) NULL,
	[sjskdate] [datetime] NULL,
	[skdate] [char](15) NULL,
	[mode] [varchar](80) NULL,
	[datet] [int] NULL,
	[note] [varchar](80) NULL,
	[moneytypes] [varchar](50) NULL,
	[rate] [varchar](20) NULL,
	[sendcompany] [nvarchar](80) NULL,
	[i_man] [decimal](18, 4) NULL,
	[remark] [nvarchar](200) NULL,
	[states] [nvarchar](50) NULL,
	[sale_man] [nvarchar](50) NULL,
	[sale_dept] [nvarchar](50) NULL,
	[deptjb] [char](15) NULL,
	[co_number] [char](30) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[gathering_customer]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[gathering_customer](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[coname] [nvarchar](100) NULL,
	[coaddr] [nvarchar](100) NULL,
	[cofrdb] [nvarchar](50) NULL,
	[cotel] [nvarchar](50) NULL,
	[cofax] [nvarchar](50) NULL,
	[bank] [nvarchar](100) NULL,
	[payment_je] [decimal](18, 4) NULL,
	[payment_sje] [decimal](18, 4) NULL,
	[money] [char](10) NULL,
	[remark] [ntext] NULL,
	[payment_date] [datetime] NULL,
	[co_number] [nvarchar](50) NULL,
	[dept] [nvarchar](90) NULL,
	[deptjb] [char](20) NULL,
	[pname] [char](15) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[gathering]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[gathering](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[fyid] [char](50) NULL,
	[invoice] [varchar](50) NOT NULL,
	[orderform] [varchar](50) NOT NULL,
	[imoney] [decimal](18, 4) NULL,
	[ymoney] [decimal](18, 4) NULL,
	[smoney] [decimal](18, 4) NULL,
	[coname] [varchar](80) NULL,
	[bank] [decimal](18, 4) NULL,
	[bankaccounts] [varchar](80) NULL,
	[yjskdate] [varchar](30) NULL,
	[sjdate] [varchar](30) NULL,
	[sjskdate] [datetime] NULL,
	[skdate] [char](15) NULL,
	[mode] [varchar](80) NULL,
	[datet] [int] NULL,
	[note] [varchar](80) NULL,
	[moneytypes] [varchar](50) NULL,
	[rate] [varchar](20) NULL,
	[sendcompany] [nvarchar](80) NULL,
	[i_man] [decimal](18, 4) NULL,
	[remark] [nvarchar](200) NULL,
	[states] [nvarchar](50) NULL,
	[sale_man] [nvarchar](50) NULL,
	[sale_dept] [nvarchar](50) NULL,
	[deptjb] [char](15) NULL,
	[co_number] [char](30) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[gather_mx_mx]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[gather_mx_mx](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[g_m_id] [char](10) NOT NULL,
	[g_m_types] [int] NOT NULL,
	[g_m_number] [char](30) NOT NULL,
	[g_m_coname] [nvarchar](80) NOT NULL,
	[g_m_smoney] [decimal](18, 4) NOT NULL,
	[g_m_hb] [char](10) NULL,
	[g_m_salesman] [char](15) NOT NULL,
	[g_m_date] [datetime] NULL,
	[g_m_man] [char](10) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[gather_mx]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[gather_mx](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[gid] [nvarchar](80) NOT NULL,
	[g_coname] [nvarchar](100) NULL,
	[g_num] [char](20) NOT NULL,
	[g_date] [datetime] NOT NULL,
	[g_je] [decimal](18, 4) NOT NULL,
	[g_sje] [decimal](18, 4) NULL,
	[g_bank] [varchar](80) NULL,
	[g_banknum] [varchar](50) NULL,
	[g_pjh] [varchar](50) NULL,
	[g_man] [char](15) NULL,
	[remark] [varchar](300) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[fysp]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[fysp](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[dept] [char](30) NULL,
	[role] [char](15) NULL,
	[price_min] [int] NULL,
	[price_max] [int] NULL,
	[dd_man] [char](15) NOT NULL,
	[fif] [char](10) NOT NULL,
	[fspman] [varchar](50) NOT NULL,
	[firspif] [char](10) NULL,
	[firspman] [char](20) NULL,
	[remark] [varchar](350) NULL,
	[dd_date] [datetime] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[f_pic]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[f_pic](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[oid] [char](10) NULL,
	[pic_sm] [nvarchar](100) NULL,
	[pic_path] [nvarchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[EMAIL]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[EMAIL](
	[EMAIL_ID] [int] IDENTITY(1,1) NOT NULL,
	[MAILBOX_ID] [int] NULL,
	[EMAIL_FROM] [varchar](50) NULL,
	[EMAIL_CC] [varchar](300) NULL,
	[EMAIL_TO] [varchar](300) NULL,
	[EMAIL_TITLE] [varchar](200) NULL,
	[EMAIL_DA] [varchar](50) NULL,
	[EMAIL_CONT] [text] NULL,
	[EMAIL_LXSTR] [varchar](30) NULL,
	[EMAIL_SDA] [datetime] NULL,
	[EMAIL_TYPE] [smallint] NULL,
	[EMAIL_FLAG] [tinyint] NULL,
	[EMAIL_ATTNUM] [int] NOT NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[cg_opro]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cg_opro](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ddid] [varchar](10) NOT NULL,
	[epro] [varchar](80) NOT NULL,
	[num] [int] NOT NULL,
	[unit] [varchar](50) NULL,
	[selljg] [decimal](18, 2) NULL,
	[money] [varchar](50) NULL,
	[remark] [nvarchar](300) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ccsp]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ccsp](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[dept] [char](30) NULL,
	[role] [char](15) NULL,
	[dd_man] [char](15) NOT NULL,
	[fif] [char](10) NOT NULL,
	[fspman] [varchar](50) NOT NULL,
	[firspif] [char](10) NULL,
	[firspman] [char](20) NULL,
	[remark] [varchar](350) NULL,
	[dd_date] [datetime] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[c_pic]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[c_pic](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[oid] [char](10) NULL,
	[pic_sm] [nvarchar](100) NULL,
	[pic_path] [nvarchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[bxtable]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[bxtable](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[wxid] [nvarchar](30) NULL,
	[sales_man] [varchar](50) NULL,
	[coname] [varchar](150) NULL,
	[coaddr] [nvarchar](150) NULL,
	[cotel] [nvarchar](50) NULL,
	[coman] [char](20) NULL,
	[cofax] [char](20) NULL,
	[copost] [nvarchar](50) NULL,
	[man] [varchar](50) NULL,
	[wxman] [varchar](50) NULL,
	[pro_model] [char](50) NULL,
	[pro_number] [char](30) NULL,
	[pro_model2] [char](50) NULL,
	[pro_number2] [char](30) NULL,
	[pro_model3] [char](30) NULL,
	[pro_number3] [char](30) NULL,
	[pro_model4] [char](50) NULL,
	[pro_number4] [char](30) NULL,
	[pro_model5] [char](50) NULL,
	[pro_number5] [char](30) NULL,
	[pro_model6] [char](50) NULL,
	[pro_number6] [char](30) NULL,
	[pro_model7] [char](50) NULL,
	[pro_number7] [char](30) NULL,
	[pro_model8] [char](50) NULL,
	[pro_number8] [char](30) NULL,
	[pro_model9] [char](50) NULL,
	[pro_number9] [char](30) NULL,
	[pro_model10] [char](50) NULL,
	[pro_number10] [char](30) NULL,
	[pro_model11] [char](50) NULL,
	[pro_number11] [char](30) NULL,
	[pro_model12] [char](50) NULL,
	[pro_number12] [char](30) NULL,
	[pro_model13] [char](50) NULL,
	[pro_number13] [char](30) NULL,
	[pro_model14] [char](50) NULL,
	[pro_number14] [char](30) NULL,
	[pro_model15] [char](50) NULL,
	[pro_number15] [char](30) NULL,
	[pro_model16] [char](30) NULL,
	[pro_number16] [char](30) NULL,
	[remarks] [ntext] NULL,
	[state] [char](10) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[borrow_goods]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[borrow_goods](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[number] [char](20) NOT NULL,
	[man] [char](10) NOT NULL,
	[dept] [nvarchar](80) NULL,
	[deptjb] [char](10) NULL,
	[coname] [nvarchar](80) NULL,
	[co_number] [char](10) NULL,
	[co_addr] [nvarchar](100) NULL,
	[co_man] [char](20) NULL,
	[co_tel] [nvarchar](20) NULL,
	[coname2] [nvarchar](80) NULL,
	[co_addr2] [nvarchar](100) NULL,
	[co_man2] [char](20) NULL,
	[co_tel2] [char](20) NULL,
	[money] [char](10) NULL,
	[bg_date] [datetime] NULL,
	[pro] [nvarchar](30) NULL,
	[pro_name] [nvarchar](50) NULL,
	[num] [int] NULL,
	[punit] [char](10) NULL,
	[price] [char](18) NULL,
	[s_price] [char](10) NULL,
	[pro2] [char](30) NULL,
	[pro_name2] [nvarchar](50) NULL,
	[num2] [int] NULL,
	[punit2] [char](10) NULL,
	[price2] [char](18) NULL,
	[s_price2] [char](10) NULL,
	[pro3] [char](30) NULL,
	[pro_name3] [nvarchar](50) NULL,
	[num3] [int] NULL,
	[punit3] [char](10) NULL,
	[price3] [char](18) NULL,
	[s_price3] [char](10) NULL,
	[pro4] [char](30) NULL,
	[pro_name4] [nvarchar](50) NULL,
	[num4] [int] NULL,
	[punit4] [char](10) NULL,
	[price4] [char](18) NULL,
	[s_price4] [char](10) NULL,
	[pro5] [char](30) NULL,
	[pro_name5] [nvarchar](50) NULL,
	[num5] [int] NULL,
	[punit5] [char](10) NULL,
	[price5] [char](18) NULL,
	[s_price5] [char](10) NULL,
	[pro6] [char](30) NULL,
	[pro_name6] [nvarchar](50) NULL,
	[num6] [int] NULL,
	[punit6] [char](10) NULL,
	[price6] [char](18) NULL,
	[s_price6] [char](10) NULL,
	[pro7] [char](30) NULL,
	[pro_name7] [nvarchar](50) NULL,
	[num7] [int] NULL,
	[punit7] [char](10) NULL,
	[price7] [char](18) NULL,
	[s_price7] [char](10) NULL,
	[pro8] [char](30) NULL,
	[pro_name8] [nvarchar](50) NULL,
	[num8] [int] NULL,
	[punit8] [char](10) NULL,
	[price8] [char](18) NULL,
	[s_price8] [char](10) NULL,
	[pro9] [char](30) NULL,
	[pro_name9] [nvarchar](50) NULL,
	[num9] [int] NULL,
	[punit9] [char](10) NULL,
	[price9] [char](18) NULL,
	[s_price9] [char](10) NULL,
	[pro10] [char](30) NULL,
	[pro_name10] [nvarchar](50) NULL,
	[num10] [int] NULL,
	[punit10] [char](10) NULL,
	[price10] [char](18) NULL,
	[s_price10] [char](10) NULL,
	[pro11] [char](30) NULL,
	[pro_name11] [nvarchar](50) NULL,
	[num11] [int] NULL,
	[punit11] [char](10) NULL,
	[price11] [char](18) NULL,
	[s_price11] [char](10) NULL,
	[pro12] [char](30) NULL,
	[pro_name12] [nvarchar](50) NULL,
	[num12] [int] NULL,
	[punit12] [char](10) NULL,
	[price12] [char](18) NULL,
	[s_price12] [char](10) NULL,
	[pro13] [char](30) NULL,
	[pro_name13] [nvarchar](50) NULL,
	[num13] [int] NULL,
	[punit13] [char](10) NULL,
	[price13] [char](18) NULL,
	[s_price13] [char](10) NULL,
	[pro14] [char](30) NULL,
	[pro_name14] [nvarchar](50) NULL,
	[num14] [int] NULL,
	[punit14] [char](10) NULL,
	[price14] [char](18) NULL,
	[s_price14] [char](10) NULL,
	[pro15] [char](30) NULL,
	[pro_name15] [nvarchar](50) NULL,
	[num15] [int] NULL,
	[punit15] [char](10) NULL,
	[price15] [char](18) NULL,
	[s_price15] [char](10) NULL,
	[remarks] [text] NULL,
	[states] [char](10) NULL,
	[sp_man] [char](10) NULL,
	[sp_date] [char](10) NULL,
	[sp_yj] [nvarchar](100) NULL,
	[fif] [char](10) NULL,
	[fsp_man] [char](10) NULL,
	[fsp_date] [char](10) NULL,
	[fsp_yj] [nvarchar](100) NULL,
	[in_no] [int] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[board]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[board](
	[boardid] [int] IDENTITY(1,1) NOT NULL,
	[boardname] [varchar](200) NULL,
	[boardmaster] [varchar](200) NULL,
	[masterpwd] [varchar](50) NULL,
	[masterword] [varchar](50) NULL,
	[masteremail] [varchar](50) NULL,
	[boardhits] [int] NULL,
	[boardtopics] [int] NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[bank]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[bank](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[com_bank] [varchar](90) NOT NULL,
	[bank] [varchar](90) NOT NULL,
	[bank_num] [varchar](50) NOT NULL,
	[bank_je] [decimal](18, 2) NULL,
	[bank_hb] [char](10) NULL,
	[remark] [nvarchar](300) NULL,
	[bank_date] [datetime] NOT NULL,
	[dept] [nvarchar](90) NULL,
	[deptjb] [char](10) NULL,
	[hiddens] [varchar](10) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ATTACHMENT]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ATTACHMENT](
	[ATTACHMENT_ID] [int] IDENTITY(1,1) NOT NULL,
	[EMAIL_ID] [int] NULL,
	[ATTACHMENT_FILENAME] [varchar](100) NULL,
	[ATTACHMENT_URL] [varchar](100) NULL,
	[ATTACHMENT_SIZE] [int] NULL,
	[ATTACHMENT_CONT] [ntext] NULL,
	[ATTACHMENT_TYPE] [smallint] NOT NULL,
	[ATTACHMENT_DESC] [varchar](100) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[article]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[article](
	[serial_no] [int] IDENTITY(1,1) NOT NULL,
	[title] [nvarchar](150) NULL,
	[speaker] [nvarchar](50) NULL,
	[provide_time] [datetime] NULL,
	[reply_num] [int] NULL,
	[click_num] [int] NULL,
	[parent_no] [int] NULL,
	[content] [text] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[appeal]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[appeal](
	[aid] [int] IDENTITY(1,1) NOT NULL,
	[numeration] [varchar](50) NOT NULL,
	[appealfl] [varchar](50) NULL,
	[appealdj] [varchar](50) NULL,
	[state] [varchar](50) NULL,
	[creatdt] [varchar](50) NULL,
	[enddt] [varchar](50) NULL,
	[receipt] [varchar](50) NULL,
	[groupnumber] [varchar](50) NULL,
	[ordernumber] [varchar](50) NULL,
	[contract] [varchar](50) NULL,
	[epro] [varchar](150) NULL,
	[cpro] [varchar](150) NULL,
	[number] [varchar](50) NULL,
	[unit] [varchar](50) NULL,
	[standard] [varchar](260) NULL,
	[transport] [varchar](260) NULL,
	[supplier] [varchar](150) NULL,
	[sdate] [varchar](50) NULL,
	[customer] [varchar](150) NULL,
	[cdate] [varchar](50) NULL,
	[appeallr] [text] NULL,
	[checknr] [text] NULL,
	[checkman] [varchar](50) NULL,
	[checkdate] [varchar](50) NULL,
	[clfa] [text] NULL,
	[clfaman] [varchar](50) NULL,
	[clfadate] [varchar](50) NULL,
	[feedback] [text] NULL,
	[feedbackman] [varchar](50) NULL,
	[feedbackdate] [varchar](50) NULL,
	[remarks] [text] NULL,
	[spmanger] [char](10) NULL,
	[spdate] [char](10) NULL,
	[spyj] [nvarchar](300) NULL,
	[cml] [char](10) NULL,
	[cfk] [nvarchar](300) NULL,
	[in_no] [int] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[aimport]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[aimport](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[aimportname] [varchar](200) NULL,
	[remark] [varchar](260) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[admin_xz]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[admin_xz](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [char](20) NULL,
	[man_password] [char](20) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[admin]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[admin](
	[adminname] [varchar](50) NOT NULL,
	[password] [varchar](50) NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[activity_type]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[activity_type](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[act_name] [nvarchar](100) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[activity]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[activity](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[actypes] [varchar](50) NULL,
	[coname] [varchar](150) NULL,
	[coaddr] [nvarchar](100) NULL,
	[coman] [char](30) NULL,
	[cotel] [char](60) NULL,
	[cofax] [char](30) NULL,
	[conet] [nvarchar](80) NULL,
	[coemail] [nvarchar](80) NULL,
	[name] [varchar](50) NULL,
	[clewtime] [varchar](50) NULL,
	[ytime] [varchar](50) NULL,
	[iftx] [char](10) NULL,
	[txtime] [varchar](50) NULL,
	[state] [varchar](50) NULL,
	[pri] [char](10) NULL,
	[descripta] [ntext] NULL,
	[remark] [ntext] NULL,
	[deptjb] [char](30) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[cgo_sp]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cgo_sp](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[dept] [char](30) NULL,
	[role] [char](15) NULL,
	[price_min] [decimal](18, 2) NULL,
	[price_max] [decimal](18, 2) NULL,
	[dd_man] [char](15) NOT NULL,
	[fif] [char](10) NOT NULL,
	[fspman] [varchar](50) NOT NULL,
	[remark] [varchar](350) NULL,
	[dd_date] [datetime] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[city]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[city](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[province] [nvarchar](100) NULL,
	[city_name] [nvarchar](100) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[checkwork_time]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[checkwork_time](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[am_startchs] [char](10) NOT NULL,
	[am_start] [char](10) NOT NULL,
	[am_startche] [char](10) NOT NULL,
	[am_startla] [int] NOT NULL,
	[if_am_start] [int] NOT NULL,
	[am_endchs] [char](10) NOT NULL,
	[am_end] [char](10) NOT NULL,
	[am_endche] [char](10) NOT NULL,
	[am_endla] [int] NOT NULL,
	[if_am_end] [int] NOT NULL,
	[pm_startchs] [char](10) NOT NULL,
	[pm_start] [char](10) NOT NULL,
	[pm_startche] [char](10) NOT NULL,
	[pm_startla] [int] NOT NULL,
	[if_pm_start] [int] NOT NULL,
	[pm_endchs] [char](10) NOT NULL,
	[pm_end] [char](10) NOT NULL,
	[pm_endche] [char](10) NOT NULL,
	[pm_endla] [int] NOT NULL,
	[if_pm_end] [int] NOT NULL,
	[monday] [char](10) NOT NULL,
	[tuesday] [int] NOT NULL,
	[wednesday] [int] NOT NULL,
	[thirsday] [int] NOT NULL,
	[friday] [int] NOT NULL,
	[saturday] [int] NOT NULL,
	[sunday] [int] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[checkwork_scope]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[checkwork_scope](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[department] [char](40) NOT NULL,
	[role] [char](40) NOT NULL,
	[newdate] [char](20) NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[checkwork_record]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[checkwork_record](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[am_start] [char](10) NULL,
	[am_end] [char](10) NULL,
	[pm_start] [char](10) NULL,
	[pm_end] [char](10) NULL,
	[latetimes] [int] NOT NULL,
	[earlytimes] [int] NOT NULL,
	[uncheck] [int] NOT NULL,
	[leavetimes] [int] NOT NULL,
	[if_finish] [int] NOT NULL,
	[newdate] [char](20) NOT NULL,
	[username] [char](30) NOT NULL,
	[department] [char](30) NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[checkwork_leave]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[checkwork_leave](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[leavetime] [char](10) NOT NULL,
	[cometime] [char](10) NOT NULL,
	[content] [nchar](800) NOT NULL,
	[state] [char](30) NOT NULL,
	[sp_yj] [nvarchar](300) NULL,
	[sp_man] [char](30) NULL,
	[newdate] [char](10) NOT NULL,
	[username] [char](30) NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[cgsp]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cgsp](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[dept] [char](30) NULL,
	[role] [char](15) NULL,
	[price_min] [decimal](18, 2) NULL,
	[price_max] [decimal](18, 2) NULL,
	[dd_man] [char](15) NOT NULL,
	[fif] [char](10) NOT NULL,
	[fspman] [varchar](50) NOT NULL,
	[remark] [varchar](350) NULL,
	[dd_date] [datetime] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[cgpro_sales]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cgpro_sales](
	[epro] [varchar](50) NULL,
	[ddid] [varchar](50) NULL,
	[supplier] [nvarchar](50) NULL,
	[remark] [nvarchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[cgpro]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[cgpro](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ddid] [varchar](10) NOT NULL,
	[epro] [varchar](80) NOT NULL,
	[cpro] [nvarchar](100) NULL,
	[pro_number] [nvarchar](50) NULL,
	[num] [int] NOT NULL,
	[unit] [varchar](50) NULL,
	[selljg] [decimal](18, 6) NULL,
	[money] [varchar](50) NULL,
	[cgpro_ydatetime] [nvarchar](50) NULL,
	[cgpro_num] [int] NOT NULL,
	[cgpro_sdatetime] [nvarchar](30) NULL,
	[remark] [nvarchar](300) NULL,
	[supplier] [nvarchar](50) NULL,
	[rate] [int] NULL,
	[wid] [nvarchar](90) NULL,
	[sale_supplier] [varchar](50) NULL,
	[sale_remark] [varchar](350) NULL,
	[sale_rate] [varchar](50) NULL,
	[sale_finance] [varchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[paysp]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[paysp](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[dept] [char](30) NULL,
	[role] [char](15) NULL,
	[dd_man] [char](15) NOT NULL,
	[fif] [char](10) NOT NULL,
	[fspman] [varchar](50) NOT NULL,
	[firspif] [char](10) NULL,
	[firspman] [char](20) NULL,
	[fourspif] [char](10) NULL,
	[fourspman] [char](15) NULL,
	[remark] [varchar](350) NULL,
	[dd_date] [datetime] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[payment_sp]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[payment_sp](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[p_menber] [nvarchar](50) NOT NULL,
	[p_datetime] [datetime] NULL,
	[p_man] [nvarchar](20) NULL,
	[p_dept] [nvarchar](50) NULL,
	[p_deptjb] [char](10) NULL,
	[p_company] [nvarchar](100) NULL,
	[p_banknumber] [nvarchar](50) NULL,
	[p_bankname] [nvarchar](80) NULL,
	[p_payment] [nvarchar](30) NULL,
	[p_je] [varchar](50) NULL,
	[p_hb] [varchar](10) NULL,
	[p_sm] [nvarchar](50) NULL,
	[p_states] [nvarchar](50) NULL,
	[p_spman] [char](10) NULL,
	[fif] [char](10) NULL,
	[fspman] [char](10) NULL,
	[firspif] [char](10) NULL,
	[firspman] [char](10) NULL,
	[fourspif] [char](10) NULL,
	[fourspman] [char](10) NULL,
	[spyj] [nvarchar](300) NULL,
	[in_no] [int] NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[payment_mx]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[payment_mx](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[gid] [char](60) NOT NULL,
	[g_num] [char](20) NOT NULL,
	[g_date] [datetime] NOT NULL,
	[g_je] [decimal](18, 4) NOT NULL,
	[g_bank] [varchar](80) NULL,
	[g_banknum] [varchar](50) NULL,
	[g_pjh] [varchar](50) NULL,
	[g_man] [char](15) NULL,
	[remark] [varchar](300) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[payment_fs]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[payment_fs](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[payment] [varchar](50) NOT NULL,
	[remark] [varchar](300) NULL,
	[pay_date] [datetime] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[payment_customer]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[payment_customer](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[co_number] [nvarchar](50) NULL,
	[coname] [nvarchar](100) NULL,
	[coaddr] [nvarchar](100) NULL,
	[cofrdb] [char](15) NULL,
	[cotel] [nvarchar](50) NULL,
	[cofax] [nvarchar](50) NULL,
	[bank] [nvarchar](100) NULL,
	[payment_je] [decimal](18, 2) NULL,
	[payment_sje] [decimal](18, 2) NULL,
	[money] [char](10) NULL,
	[salesman] [char](15) NULL,
	[remark] [ntext] NULL,
	[payment_date] [datetime] NULL,
	[dept] [nvarchar](100) NULL,
	[deptjb] [char](15) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[payment]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[payment](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[contract] [varchar](50) NOT NULL,
	[orderform] [varchar](50) NULL,
	[sup_number] [char](30) NULL,
	[supplier] [nvarchar](100) NULL,
	[pay_je] [nvarchar](50) NULL,
	[htmoney] [decimal](18, 1) NULL,
	[yjfkdate] [varchar](50) NULL,
	[sjfkdate] [varchar](50) NULL,
	[moneytypes] [varchar](50) NULL,
	[note] [varchar](50) NULL,
	[bank] [varchar](150) NULL,
	[bankaccounts] [decimal](18, 2) NULL,
	[moneyty] [varchar](50) NULL,
	[wtfk] [varchar](10) NULL,
	[paynr] [nvarchar](300) NULL,
	[remark] [varchar](50) NULL,
	[states] [nvarchar](50) NULL,
	[skfs] [varchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[PackingList]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[PackingList](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[pid] [varchar](50) NULL,
	[xlistids] [varchar](200) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[p_pic]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[p_pic](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[oid] [char](10) NULL,
	[pic_sm] [nvarchar](100) NULL,
	[pic_path] [nvarchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[outhouse]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[outhouse](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[pro_fynum] [varchar](50) NOT NULL,
	[pro_coname] [nvarchar](120) NULL,
	[pro_model] [nvarchar](80) NULL,
	[pro_name] [nvarchar](80) NULL,
	[pro_num] [int] NULL,
	[pro_unit] [char](10) NULL,
	[pro_supplier] [nvarchar](80) NULL,
	[pro_datetime] [char](15) NULL,
	[pro_number] [varchar](50) NULL,
	[slinkman] [nvarchar](90) NULL,
	[slinktel] [char](30) NULL,
	[states] [char](30) NULL,
	[ddid] [char](10) NULL,
	[remark] [varchar](300) NULL,
	[wid] [char](10) NULL,
	[pro_coname_num] [char](30) NULL,
	[pro_sales_price] [decimal](18, 6) NULL,
	[pro_price_hb] [char](15) NULL,
	[pro_rate_types] [char](30) NULL,
	[pro_rate] [varchar](50) NULL,
	[pro_out_num] [char](15) NOT NULL,
	[in_no] [int] NULL,
	[salejg] [decimal](18, 4) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[out_sp]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[out_sp](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[dept] [char](30) NULL,
	[role] [char](15) NULL,
	[dd_man] [char](15) NOT NULL,
	[fif] [char](10) NOT NULL,
	[fspman] [varchar](50) NOT NULL,
	[remark] [varchar](350) NULL,
	[dd_date] [datetime] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[out_bind]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[out_bind](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ddid] [int] NULL,
	[wid] [int] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[opportunity]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[opportunity](
	[sellid] [int] IDENTITY(1,1) NOT NULL,
	[sell] [varchar](80) NOT NULL,
	[description] [ntext] NULL,
	[xplan] [varchar](80) NULL,
	[origin] [varchar](80) NULL,
	[salem] [varchar](50) NULL,
	[saledt] [varchar](50) NULL,
	[coname] [varchar](150) NULL,
	[coaddr] [varchar](150) NULL,
	[copost] [varchar](50) NULL,
	[city] [varchar](50) NULL,
	[cotel] [varchar](50) NULL,
	[cofax] [varchar](50) NULL,
	[forecast] [numeric](19, 2) NULL,
	[creatdt] [varchar](50) NULL,
	[enddt] [varchar](50) NULL,
	[succeed] [varchar](50) NULL,
	[state] [varchar](50) NULL,
	[stop] [varchar](150) NULL,
	[username] [varchar](50) NULL,
	[usertime] [varchar](50) NULL,
	[dept] [nvarchar](50) NULL,
	[deptjb] [char](15) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[oa_sale_refund_log]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[oa_sale_refund_log](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[number] [varchar](50) NOT NULL,
	[remark] [text] NOT NULL,
	[created_time] [datetime] NOT NULL,
	[operateType] [varchar](50) NOT NULL,
	[operator] [varchar](50) NULL,
 CONSTRAINT [PK_oa_sale_refund_log] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[o_pic]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[o_pic](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[oid] [char](10) NULL,
	[pic_sm] [nvarchar](100) NULL,
	[pic_path] [nvarchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[nproduct]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[nproduct](
	[npid] [int] IDENTITY(1,1) NOT NULL,
	[epro] [varchar](150) NULL,
	[profl] [varchar](50) NULL,
	[cpro] [varchar](150) NULL,
	[casnumber] [varchar](50) NULL,
	[warename] [varchar](50) NULL,
	[chemistryname] [varchar](50) NULL,
	[zlgg] [varchar](150) NULL,
	[userdatetime] [varchar](50) NULL,
	[patent] [varchar](200) NULL,
	[supplier] [varchar](300) NULL,
	[customer] [varchar](150) NULL,
	[prosm] [text] NULL,
	[salexq] [text] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[notice]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[notice](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[pro_man] [varchar](100) NULL,
	[nType] [varchar](10) NULL,
	[title] [varchar](200) NULL,
	[content] [ntext] NULL,
	[pro_time] [datetime] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[name_sale_total]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[name_sale_total](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[man] [char](15) NULL,
	[coname] [nvarchar](100) NULL,
	[sub_num] [int] NULL,
	[pro_number] [decimal](18, 2) NULL,
	[pro_num] [int] NULL,
	[pro_sale_money] [decimal](18, 2) NULL,
	[pro_sk_money] [decimal](18, 0) NULL,
	[pro_cg_money] [decimal](18, 2) NULL,
	[pro_other_money] [decimal](18, 2) NULL,
	[pro_lr_money] [decimal](18, 2) NULL,
	[pro_lr_rate] [decimal](18, 2) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[mt]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[mt](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[user_number] [varchar](22) NOT NULL,
	[want_report] [tinyint] NOT NULL,
	[message_length] [int] NOT NULL,
	[message_coding] [tinyint] NOT NULL,
	[message] [varchar](160) NULL,
	[seq_sp_number] [int] NULL,
	[seq_date] [int] NULL,
	[seq_no] [int] NULL,
	[phase] [tinyint] NULL,
	[state] [tinyint] NULL,
	[err_code] [tinyint] NULL,
	[create_time] [datetime] NOT NULL,
	[sending_time] [datetime] NULL,
	[sended_time] [datetime] NULL,
	[report_time] [datetime] NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[month_report_rdm]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[month_report_rdm](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[w_menber] [nvarchar](25) NOT NULL,
	[w_datetime] [datetime] NULL,
	[w_man] [nvarchar](15) NULL,
	[w_dept] [varchar](50) NULL,
	[w_deptjb] [char](10) NULL,
	[w_a_coname] [ntext] NULL,
	[w_a_jh] [ntext] NULL,
	[w_a_qt] [ntext] NULL,
	[w_a_remark] [ntext] NULL,
	[w_ycqk] [nvarchar](450) NULL,
	[w_jh] [ntext] NULL,
	[w_spman] [char](20) NULL,
	[w_states] [char](20) NULL,
	[w_spyj] [nvarchar](200) NULL,
	[in_no] [int] NULL,
	[m_ps] [nvarchar](350) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[messageuser]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[messageuser](
	[id] [varchar](50) NOT NULL,
	[username] [varchar](50) NULL,
	[userpassword] [varchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[menberzj]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[menberzj](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[menberid] [nvarchar](50) NULL,
	[zjname] [nvarchar](100) NULL,
	[blstate] [nvarchar](50) NULL,
	[startdate] [nvarchar](50) NULL,
	[stopdate] [nvarchar](50) NULL,
	[bzjg] [nvarchar](100) NULL,
	[syfw] [nvarchar](200) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[menbersb]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[menbersb](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[menberid] [nvarchar](50) NULL,
	[sbname] [nvarchar](50) NULL,
	[cbdate] [nvarchar](50) NULL,
	[sbnumber] [nvarchar](50) NULL,
	[ifhk] [nvarchar](50) NULL,
	[tbaddr] [nvarchar](50) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[menberht]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[menberht](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[menberid] [nvarchar](50) NULL,
	[htnumber] [nvarchar](50) NULL,
	[chtdate] [nvarchar](50) NULL,
	[sydate] [nvarchar](50) NULL,
	[sywage] [nvarchar](50) NULL,
	[zzdate] [nvarchar](50) NULL,
	[zzwage] [nvarchar](50) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[menber]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[menber](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[pic_path] [nvarchar](50) NULL,
	[name] [nvarchar](50) NULL,
	[dept] [nvarchar](90) NULL,
	[deptjb] [char](10) NULL,
	[business] [nvarchar](50) NULL,
	[jiondt] [nvarchar](50) NULL,
	[borndt] [nvarchar](50) NULL,
	[sex] [char](10) NULL,
	[man_zzmm] [nvarchar](50) NULL,
	[nation] [nvarchar](50) NULL,
	[man_h] [nvarchar](50) NULL,
	[idcard] [nvarchar](50) NULL,
	[man_tc] [nvarchar](50) NULL,
	[nativeplace] [nvarchar](90) NULL,
	[xzaddr] [nvarchar](90) NULL,
	[born_addr] [nvarchar](90) NULL,
	[hunif] [char](10) NULL,
	[man_tel] [nvarchar](50) NULL,
	[man_school] [nvarchar](100) NULL,
	[school_date] [nvarchar](50) NULL,
	[school_zy] [nvarchar](50) NULL,
	[school_year] [nvarchar](50) NULL,
	[school_age] [nvarchar](50) NULL,
	[degree] [nvarchar](50) NULL,
	[dimissiom_date] [nvarchar](50) NULL,
	[man_english] [nvarchar](90) NULL,
	[man_computator] [nvarchar](90) NULL,
	[man_jj_lxr] [char](50) NULL,
	[man_jj_tel] [nvarchar](50) NULL,
	[man_jj_addr] [nvarchar](90) NULL,
	[family_name1] [nvarchar](50) NULL,
	[family_gx1] [nvarchar](50) NULL,
	[family_work_name1] [nvarchar](150) NULL,
	[family_zw1] [nvarchar](50) NULL,
	[family_tel1] [nvarchar](50) NULL,
	[family_name2] [nvarchar](50) NULL,
	[family_gx2] [nvarchar](50) NULL,
	[family_work_name2] [nvarchar](150) NULL,
	[family_zw2] [nvarchar](50) NULL,
	[family_tel2] [nvarchar](50) NULL,
	[family_name3] [nvarchar](50) NULL,
	[family_gx3] [nvarchar](50) NULL,
	[family_work_name3] [nvarchar](150) NULL,
	[family_zw3] [nvarchar](50) NULL,
	[family_tel3] [nvarchar](50) NULL,
	[work_sdate] [char](15) NULL,
	[work_edate] [char](15) NULL,
	[work_company] [nvarchar](150) NULL,
	[work_zw] [char](30) NULL,
	[work_tel] [nvarchar](50) NULL,
	[work_sdate1] [char](15) NULL,
	[work_edate1] [char](15) NULL,
	[work_company1] [nvarchar](150) NULL,
	[work_zw1] [char](30) NULL,
	[work_tel1] [nvarchar](50) NULL,
	[work_sdate2] [char](15) NULL,
	[work_edate2] [char](15) NULL,
	[work_company2] [nvarchar](150) NULL,
	[work_zw2] [char](30) NULL,
	[work_tel2] [nvarchar](50) NULL,
	[man_zz] [nvarchar](200) NULL,
	[man_cb] [nvarchar](200) NULL,
	[man_ht] [nvarchar](200) NULL,
	[remark] [ntext] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[man_xz]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[man_xz](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[menberid] [char](10) NULL,
	[name] [nvarchar](50) NULL,
	[man_kdate] [nvarchar](50) NULL,
	[man_klx] [nvarchar](150) NULL,
	[man_kh] [ntext] NULL,
	[remark] [ntext] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[man_kh]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[man_kh](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[menberid] [char](10) NULL,
	[name] [nvarchar](50) NULL,
	[man_kdate] [nvarchar](50) NULL,
	[man_klx] [nvarchar](150) NULL,
	[man_kh] [ntext] NULL,
	[remark] [ntext] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[man_jf]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[man_jf](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[menberid] [char](10) NULL,
	[name] [nvarchar](50) NULL,
	[man_kdate] [nvarchar](50) NULL,
	[man_klx] [nvarchar](150) NULL,
	[man_kh] [ntext] NULL,
	[remark] [ntext] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[man_dd]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[man_dd](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[menberid] [char](10) NULL,
	[name] [nvarchar](50) NULL,
	[man_kdate] [nvarchar](50) NULL,
	[man_klx] [nvarchar](150) NULL,
	[man_kh] [ntext] NULL,
	[remark] [ntext] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[mailman]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[mailman](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[hostname] [varchar](90) NULL,
	[name] [varchar](90) NULL,
	[password] [varchar](50) NULL,
	[frommail] [varchar](90) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[MAILBOX]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[MAILBOX](
	[MAILBOX_ID] [int] IDENTITY(1,1) NOT NULL,
	[USERID] [int] NULL,
	[MAILBOX_PWD] [varchar](50) NULL,
	[MAILBOX_USER] [varchar](50) NULL,
	[MAILBOX_SMTP] [varchar](50) NULL,
	[MAILBOX_SPORT] [int] NOT NULL,
	[MAILBOX_POP] [varchar](50) NULL,
	[MAILBOX_PPORT] [int] NOT NULL,
	[MAILBOX_FLAG] [tinyint] NULL,
	[MAILBOX_EMAIL] [varchar](50) NULL,
	[MAILBOX_DEL] [tinyint] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[mail_date]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[mail_date](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[man] [char](10) NULL,
	[dept] [nvarchar](50) NULL,
	[deptjb] [char](10) NULL,
	[mail_date] [datetime] NULL,
	[mail_to] [ntext] NULL,
	[mail_sub] [nvarchar](90) NULL,
	[mail_nr] [text] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[mact_act]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[mact_act](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[number] [char](10) NOT NULL,
	[mact_subject] [nvarchar](80) NULL,
	[start_date] [datetime] NULL,
	[end_date] [datetime] NULL,
	[mact_content] [ntext] NULL,
	[mact_com] [nvarchar](90) NULL,
	[mact_customer] [nvarchar](200) NULL,
	[mact_addr] [nvarchar](100) NULL,
	[mact_product] [nvarchar](200) NULL,
	[mact_num] [int] NULL,
	[mact_price] [decimal](18, 2) NULL,
	[mact_xg] [ntext] NULL,
	[man] [char](10) NOT NULL,
	[dept] [char](20) NOT NULL,
	[deptjb] [char](10) NOT NULL,
	[rg_date] [datetime] NOT NULL,
	[spman] [char](10) NOT NULL,
	[spyj] [nvarchar](200) NULL,
	[states] [char](10) NOT NULL,
	[in_no] [int] NOT NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[m_zzgl]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[m_zzgl](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[l_menber] [char](20) NOT NULL,
	[man] [char](10) NULL,
	[l_man] [char](10) NOT NULL,
	[l_dept] [nvarchar](80) NULL,
	[l_deptjb] [char](10) NULL,
	[l_role] [nvarchar](90) NULL,
	[l_dg_date] [char](15) NULL,
	[l_date] [datetime] NOT NULL,
	[l_zz_date] [char](15) NULL,
	[l_jcyy] [ntext] NULL,
	[remarks] [ntext] NULL,
	[l_spqk] [nvarchar](70) NULL,
	[l_spyj] [nvarchar](370) NULL,
	[l_fqr] [nvarchar](25) NULL,
	[l_spman] [nvarchar](50) NULL,
	[l_fif] [char](10) NULL,
	[l_fspman] [char](15) NULL,
	[l_firspif] [char](10) NULL,
	[l_firspman] [char](15) NULL,
	[in_no] [int] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[m_stipend]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[m_stipend](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[l_menber] [char](20) NOT NULL,
	[man] [char](10) NULL,
	[l_man] [char](10) NOT NULL,
	[l_dept] [nvarchar](80) NULL,
	[l_deptjb] [char](10) NULL,
	[l_role] [nvarchar](90) NULL,
	[l_dg_date] [char](15) NULL,
	[l_date] [datetime] NOT NULL,
	[l_zz_date] [char](15) NULL,
	[l_tzsx] [nvarchar](50) NULL,
	[l_ylxz] [nvarchar](50) NULL,
	[l_tzbl] [nvarchar](50) NULL,
	[l_tzje] [nvarchar](50) NULL,
	[l_tzhxz] [nvarchar](50) NULL,
	[l_jcyy] [ntext] NULL,
	[remarks] [ntext] NULL,
	[l_spqk] [nvarchar](70) NULL,
	[l_spyj] [nvarchar](370) NULL,
	[l_fqr] [nvarchar](25) NULL,
	[l_spman] [nvarchar](50) NULL,
	[l_fif] [char](10) NULL,
	[l_fspman] [char](15) NULL,
	[l_firspif] [char](10) NULL,
	[l_firspman] [char](15) NULL,
	[in_no] [int] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[m_spbm]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[m_spbm](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[l_menber] [char](20) NOT NULL,
	[l_man] [char](10) NOT NULL,
	[l_dept] [nvarchar](80) NULL,
	[l_deptjb] [char](10) NULL,
	[l_date] [nvarchar](90) NULL,
	[l_dg_date] [char](15) NULL,
	[l_zz_date] [char](15) NULL,
	[l_jcyy] [ntext] NULL,
	[l_pic] [nvarchar](150) NULL,
	[remarks] [ntext] NULL,
	[l_spqk] [nvarchar](70) NULL,
	[l_spyj] [nvarchar](370) NULL,
	[l_fqr] [nvarchar](25) NULL,
	[l_spman] [nvarchar](50) NULL,
	[l_fif] [char](10) NULL,
	[l_fspman] [char](15) NULL,
	[l_firspif] [char](10) NULL,
	[l_firspman] [char](15) NULL,
	[in_no] [int] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[m_pic]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[m_pic](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[oid] [char](10) NULL,
	[pic_sm] [nvarchar](100) NULL,
	[pic_path] [nvarchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[m_jcgl]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[m_jcgl](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[l_menber] [char](20) NOT NULL,
	[l_name] [char](30) NULL,
	[l_company] [nvarchar](80) NULL,
	[l_role] [char](20) NULL,
	[l_types] [nvarchar](50) NULL,
	[l_jcyj] [nvarchar](150) NULL,
	[l_man] [char](15) NULL,
	[l_dept] [nvarchar](80) NULL,
	[l_deptjb] [char](15) NULL,
	[l_date] [datetime] NULL,
	[l_jcyy] [ntext] NULL,
	[remarks] [ntext] NULL,
	[l_spqk] [nvarchar](70) NULL,
	[l_spyj] [nvarchar](370) NULL,
	[l_fqr] [nvarchar](25) NULL,
	[l_spman] [nvarchar](50) NULL,
	[l_fif] [char](10) NULL,
	[l_fspman] [char](15) NULL,
	[l_firspif] [char](10) NULL,
	[l_firspman] [char](15) NULL,
	[in_no] [int] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[m_evection]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[m_evection](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[l_menber] [char](20) NOT NULL,
	[man] [char](10) NULL,
	[l_man] [char](10) NOT NULL,
	[l_dept] [nvarchar](80) NULL,
	[l_deptjb] [char](10) NULL,
	[l_role] [nvarchar](90) NULL,
	[l_dg_date] [nvarchar](350) NULL,
	[l_date] [datetime] NOT NULL,
	[l_zz_date] [nvarchar](50) NULL,
	[l_jcyy] [ntext] NULL,
	[remarks] [ntext] NULL,
	[l_spqk] [nvarchar](70) NULL,
	[l_spyj] [nvarchar](370) NULL,
	[l_fqr] [nvarchar](25) NULL,
	[l_spman] [nvarchar](50) NULL,
	[l_fif] [char](10) NULL,
	[l_fspman] [char](15) NULL,
	[l_firspif] [char](10) NULL,
	[l_firspman] [char](15) NULL,
	[in_no] [int] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[m_document]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[m_document](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[l_menber] [char](20) NOT NULL,
	[l_man] [char](10) NOT NULL,
	[l_dept] [nvarchar](80) NULL,
	[l_deptjb] [char](10) NULL,
	[l_date] [datetime] NULL,
	[l_role] [nvarchar](90) NULL,
	[l_dg_date] [nvarchar](90) NULL,
	[l_zz_date] [nvarchar](90) NULL,
	[l_tzsx] [nvarchar](90) NULL,
	[l_ylxz] [nvarchar](90) NULL,
	[l_tzbl] [nvarchar](90) NULL,
	[l_tzje] [nvarchar](90) NULL,
	[l_tzhxz] [nvarchar](90) NULL,
	[l_jcyy] [ntext] NULL,
	[remarks] [ntext] NULL,
	[l_spqk] [nvarchar](70) NULL,
	[l_spyj] [nvarchar](370) NULL,
	[l_fqr] [nvarchar](25) NULL,
	[l_spman] [nvarchar](50) NULL,
	[l_fif] [char](10) NULL,
	[l_fspman] [char](15) NULL,
	[l_firspif] [char](10) NULL,
	[l_firspman] [char](15) NULL,
	[in_no] [int] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[m_commision]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[m_commision](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[l_menber] [char](20) NOT NULL,
	[l_man] [char](10) NOT NULL,
	[l_dept] [nvarchar](80) NULL,
	[l_deptjb] [char](10) NULL,
	[l_date] [datetime] NOT NULL,
	[coname] [nvarchar](50) NULL,
	[pi_number] [nvarchar](50) NULL,
	[pro_model] [nvarchar](50) NULL,
	[price] [char](30) NULL,
	[p_num] [char](30) NULL,
	[zprice] [char](30) NULL,
	[yj] [char](30) NULL,
	[coname2] [nvarchar](50) NULL,
	[pi_number2] [nvarchar](50) NULL,
	[pro_model2] [nvarchar](50) NULL,
	[price2] [char](30) NULL,
	[p_num2] [char](30) NULL,
	[zprice2] [char](30) NULL,
	[yj2] [char](30) NULL,
	[coname3] [nvarchar](50) NULL,
	[pi_number3] [nvarchar](50) NULL,
	[pro_model3] [nvarchar](50) NULL,
	[price3] [char](30) NULL,
	[p_num3] [char](30) NULL,
	[zprice3] [char](30) NULL,
	[yj3] [char](30) NULL,
	[l_jcyy] [ntext] NULL,
	[yj_man] [nvarchar](350) NULL,
	[l_types] [nvarchar](50) NULL,
	[l_bank] [nvarchar](150) NULL,
	[l_sq_date] [nvarchar](50) NULL,
	[l_sj_date] [nvarchar](50) NULL,
	[remarks] [ntext] NULL,
	[l_spqk] [nvarchar](70) NULL,
	[l_spyj] [nvarchar](370) NULL,
	[l_fqr] [nvarchar](25) NULL,
	[l_spman] [nvarchar](50) NULL,
	[l_fif] [char](10) NULL,
	[l_fspman] [char](15) NULL,
	[l_firspif] [char](10) NULL,
	[l_firspman] [char](15) NULL,
	[in_no] [int] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[logdate]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[logdate](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[log_name] [nvarchar](50) NULL,
	[log_comname] [nvarchar](50) NULL,
	[log_chost] [nvarchar](50) NULL,
	[log_server] [nvarchar](50) NULL,
	[log_datetime] [nvarchar](50) NULL,
	[log_qxz] [nvarchar](50) NULL,
	[log_role] [char](20) NULL,
	[log_dept] [nvarchar](90) NULL,
	[log_deptjb] [char](10) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[linkman]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[linkman](
	[nameid] [int] IDENTITY(1,1) NOT NULL,
	[name] [varchar](50) NOT NULL,
	[job] [varchar](50) NULL,
	[mr] [varchar](50) NULL,
	[email] [varchar](50) NULL,
	[tel] [varchar](50) NULL,
	[department] [varchar](50) NULL,
	[bornde] [varchar](50) NULL,
	[school] [varchar](150) NULL,
	[degree] [varchar](50) NULL,
	[born] [varchar](50) NULL,
	[co_number] [char](30) NULL,
	[coname] [varchar](150) NULL,
	[coaddr] [varchar](150) NULL,
	[cotel] [varchar](50) NULL,
	[cofax] [varchar](50) NULL,
	[prorole] [varchar](50) NULL,
	[evaluate] [varchar](200) NULL,
	[artifice] [varchar](200) NULL,
	[post] [varchar](50) NULL,
	[city] [varchar](50) NULL,
	[country] [varchar](80) NULL,
	[province] [varchar](50) NULL,
	[waptel] [varchar](50) NULL,
	[interest] [varchar](300) NULL,
	[username] [varchar](50) NULL,
	[rg_date] [datetime] NULL,
	[dept] [char](30) NULL,
	[deptjb] [char](30) NULL,
	[modman] [char](15) NULL,
	[mod_date] [datetime] NULL,
	[share] [varchar](50) NULL,
	[beizhu] [varchar](300) NULL,
	[qq] [varchar](50) NULL,
	[msn] [varchar](50) NULL,
	[skype] [varchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[lending_m]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[lending_m](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[l_menber] [nvarchar](25) NOT NULL,
	[l_man] [nvarchar](25) NULL,
	[l_topic] [nvarchar](80) NULL,
	[l_dept] [char](50) NULL,
	[l_deptjb] [char](10) NULL,
	[l_date] [datetime] NULL,
	[l_sqje] [decimal](18, 2) NOT NULL,
	[l_purpose] [nvarchar](300) NULL,
	[l_spqk] [nvarchar](70) NULL,
	[l_spyj] [nvarchar](70) NULL,
	[l_fqr] [nvarchar](25) NULL,
	[remarks] [ntext] NULL,
	[l_spman] [nvarchar](50) NULL,
	[l_fif] [char](10) NULL,
	[l_fspman] [char](15) NULL,
	[l_firspif] [char](10) NULL,
	[l_firspman] [char](15) NULL,
	[in_no] [int] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[lending]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[lending](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[l_menber] [nvarchar](25) NOT NULL,
	[l_man] [nvarchar](25) NULL,
	[l_topic] [nvarchar](80) NULL,
	[l_dept] [char](50) NULL,
	[l_deptjb] [char](10) NULL,
	[l_date] [datetime] NULL,
	[l_coname] [nvarchar](80) NULL,
	[l_sqje] [decimal](18, 2) NOT NULL,
	[l_purpose] [nvarchar](300) NOT NULL,
	[l_spqk] [nvarchar](70) NOT NULL,
	[l_spyj] [nvarchar](70) NOT NULL,
	[l_fqr] [nvarchar](25) NOT NULL,
	[remarks] [ntext] NOT NULL,
	[l_spman] [nvarchar](50) NULL,
	[l_fif] [char](10) NULL,
	[l_fspman] [char](15) NULL,
	[l_firspif] [char](10) NULL,
	[l_firspman] [char](15) NULL,
	[in_no] [int] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[lawtable_r]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[lawtable_r](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[lid] [char](10) NULL,
	[content] [ntext] NULL,
	[username] [nvarchar](50) NULL,
	[fvdate] [nvarchar](50) NULL,
	[dept] [char](70) NULL,
	[deptjb] [char](10) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[lawtable]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[lawtable](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[titel] [nvarchar](150) NULL,
	[km_types] [nvarchar](90) NULL,
	[content] [ntext] NULL,
	[km_fa] [ntext] NULL,
	[username] [nvarchar](50) NULL,
	[fvdate] [nvarchar](50) NULL,
	[dept] [char](70) NULL,
	[deptjb] [char](10) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[knowledge]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[knowledge](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[topil] [nvarchar](100) NULL,
	[pass_type] [varchar](50) NULL,
	[sort] [nvarchar](50) NULL,
	[connent] [nvarchar](500) NULL,
	[solu] [nvarchar](500) NULL,
	[work_datetime] [nvarchar](15) NULL,
	[kman] [char](10) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[km_ty]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[km_ty](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[km_name] [nvarchar](100) NOT NULL,
	[remark] [nvarchar](200) NULL,
	[km_date] [datetime] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[km_mx]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[km_mx](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[km] [nvarchar](90) NULL,
	[km_mx] [nvarchar](80) NULL,
	[kmId] [int] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[km_f]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[km_f](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[coun_number] [char](20) NULL,
	[coun_name] [nvarchar](100) NULL,
	[coun_ename] [nvarchar](100) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[km]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[km](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[coun_number] [char](40) NULL,
	[km_fx] [char](10) NULL,
	[coun_name] [nvarchar](200) NULL,
	[coun_ename] [nvarchar](200) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[KeyGen]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[KeyGen](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[keyValue] [int] NULL,
	[keyName] [varchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[jzdstable]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[jzdstable](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[jzds] [nvarchar](80) NOT NULL,
	[wz] [nvarchar](60) NULL,
	[jzdslx] [nvarchar](80) NULL,
	[jzdszt] [nvarchar](80) NULL,
	[dlwx] [nvarchar](80) NULL,
	[hy] [nvarchar](80) NULL,
	[js] [nvarchar](60) NULL,
	[lxdh] [nvarchar](80) NULL,
	[cz] [nvarchar](80) NULL,
	[gsfr] [nvarchar](60) NULL,
	[hd] [nvarchar](50) NULL,
	[jd] [nvarchar](90) NULL,
	[cs] [nvarchar](90) NULL,
	[sf] [nvarchar](80) NULL,
	[yb] [nvarchar](60) NULL,
	[gj] [nvarchar](20) NULL,
	[qbxx] [nvarchar](500) NULL,
	[cpms] [nvarchar](550) NULL,
	[jzys] [nvarchar](500) NULL,
	[jzns] [nvarchar](550) NULL,
	[ryzz] [nvarchar](550) NULL,
	[remark] [text] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[jksp]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[jksp](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[dept] [char](30) NULL,
	[role] [char](15) NULL,
	[price_min] [int] NULL,
	[price_max] [int] NULL,
	[dd_man] [char](15) NOT NULL,
	[fif] [char](10) NOT NULL,
	[fspman] [varchar](50) NOT NULL,
	[firspif] [char](10) NULL,
	[firspman] [char](20) NULL,
	[remark] [varchar](350) NULL,
	[dd_date] [datetime] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[jcsp]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[jcsp](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[dept] [char](30) NULL,
	[role] [char](15) NULL,
	[dd_man] [char](15) NOT NULL,
	[fif] [char](10) NOT NULL,
	[fspman] [varchar](50) NOT NULL,
	[firspif] [char](10) NULL,
	[firspman] [char](20) NULL,
	[remark] [varchar](350) NULL,
	[dd_date] [datetime] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[inquirys]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[inquirys](
	[ctype] [char](30) NULL,
	[orders] [char](10) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Inquiry_product]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Inquiry_product](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[quoteid] [varchar](50) NOT NULL,
	[supplier] [nvarchar](90) NULL,
	[pro_sup_no] [nvarchar](200) NULL,
	[pro_p_year] [nvarchar](50) NULL,
	[pro_pro_no] [nvarchar](50) NULL,
	[product] [varchar](500) NULL,
	[cpro] [varchar](80) NULL,
	[cpro2] [nvarchar](80) NULL,
	[quantity] [int] NULL,
	[punit] [varchar](50) NULL,
	[price] [decimal](18, 4) NULL,
	[price_hb] [char](15) NULL,
	[pro_tr] [nvarchar](50) NULL,
	[pro_report] [nvarchar](200) NULL,
	[pro_remark] [varchar](150) NULL,
	[pro_states] [char](15) NULL,
	[wid] [char](10) NULL,
	[xh] [int] NULL,
	[p_product] [nvarchar](500) NULL,
	[p_pro_p_year] [nvarchar](50) NULL,
	[p_quantity] [int] NULL,
	[p_price] [decimal](18, 4) NULL,
	[p_supplier] [nvarchar](120) NULL,
	[p_pro_tr] [nvarchar](50) NULL,
	[q_price] [decimal](18, 2) NULL,
	[p_hb] [char](10) NULL,
 CONSTRAINT [PK_Inquiry_product] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[Inquiry]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[Inquiry](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[number] [varchar](50) NOT NULL,
	[quotedatetime] [varchar](50) NULL,
	[co_number] [char](30) NULL,
	[coname] [varchar](150) NULL,
	[cotel] [varchar](50) NULL,
	[coaddr] [varchar](150) NULL,
	[cofax] [varchar](50) NULL,
	[linkman] [varchar](50) NULL,
	[linktel] [varchar](50) NULL,
	[linkwap] [varchar](50) NULL,
	[linkemail] [varchar](50) NULL,
	[man] [varchar](50) NULL,
	[dept] [nvarchar](50) NULL,
	[deptjb] [nvarchar](50) NULL,
	[content] [ntext] NULL,
	[states] [nvarchar](50) NULL,
	[in_no] [int] NULL,
	[cgman] [char](15) NULL,
	[xj_date] [nvarchar](50) NULL,
	[country] [nvarchar](50) NULL,
	[province] [nvarchar](50) NULL,
	[city] [nvarchar](50) NULL,
	[hf_date] [nvarchar](50) NULL,
	[copost] [char](10) NULL,
	[c_types] [char](30) NULL,
	[in_man] [nvarchar](390) NULL,
	[in_f_datetime] [nvarchar](50) NULL,
	[courier] [nvarchar](50) NULL,
	[acct] [nvarchar](50) NULL,
	[terms] [nvarchar](50) NULL,
 CONSTRAINT [PK_Inquiry] PRIMARY KEY CLUSTERED 
(
	[id] DESC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[input_load]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[input_load](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[customer] [int] NULL,
	[product] [int] NULL,
	[proadd] [int] NULL,
	[load_exc] [nvarchar](50) NULL,
	[load_pexc] [char](30) NULL,
	[load_pro] [nvarchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[info]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[info](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[pro_date] [datetime] NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[in_warehouse]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[in_warehouse](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[number] [nvarchar](20) NOT NULL,
	[cg_number] [nvarchar](50) NULL,
	[supplier] [nvarchar](90) NULL,
	[sp_number] [nvarchar](50) NULL,
	[int_types] [char](15) NULL,
	[money] [char](10) NOT NULL,
	[int_date] [datetime] NOT NULL,
	[g_man] [char](10) NULL,
	[man] [char](10) NOT NULL,
	[dept] [nvarchar](50) NULL,
	[deptjb] [char](10) NULL,
	[remark] [ntext] NULL,
	[in_no] [int] NOT NULL,
	[states] [char](10) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[hylxtable]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[hylxtable](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[hyname] [nvarchar](50) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[hycompany]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[hycompany](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[companyname] [varchar](150) NULL,
	[remark] [varchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[htsp]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[htsp](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[dept] [char](30) NULL,
	[role] [char](15) NULL,
	[ifsp] [char](10) NULL,
	[dd_man] [char](15) NOT NULL,
	[remark] [varchar](350) NULL,
	[dd_date] [datetime] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ht_pro]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ht_pro](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ddid] [varchar](50) NOT NULL,
	[epro] [varchar](150) NOT NULL,
	[num] [int] NOT NULL,
	[fypronum] [int] NULL,
	[unit] [varchar](50) NULL,
	[cpro] [varchar](150) NULL,
	[rale_types] [varchar](50) NULL,
	[rale] [decimal](10, 2) NULL,
	[supplier] [varchar](150) NULL,
	[pricehb] [nvarchar](30) NOT NULL,
	[salejg] [decimal](18, 2) NOT NULL,
	[selljg] [decimal](18, 2) NULL,
	[money] [varchar](50) NULL,
	[fyproall] [char](15) NULL,
	[wid] [char](10) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ht_mb]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ht_mb](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[q_name] [nvarchar](50) NULL,
	[q_topic] [varchar](100) NULL,
	[q_company] [varchar](80) NULL,
	[q_addr] [varchar](100) NULL,
	[q_man] [nvarchar](50) NULL,
	[q_tel] [varchar](60) NULL,
	[q_fax] [varchar](80) NULL,
	[q_email] [varchar](100) NULL,
	[q_net] [varchar](100) NULL,
	[q_post] [nvarchar](50) NULL,
	[q_number] [nvarchar](50) NULL,
	[q_tk] [ntext] NULL,
	[remark] [ntext] NULL,
	[q_date] [datetime] NOT NULL,
	[dept] [varchar](50) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[ht]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[ht](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[ht_topic] [nvarchar](200) NULL,
	[ht_mycom] [nvarchar](100) NOT NULL,
	[ht_addr] [nvarchar](100) NULL,
	[ht_customer] [nvarchar](90) NOT NULL,
	[ht_number] [nvarchar](30) NOT NULL,
	[ht_datetime] [datetime] NOT NULL,
	[ht_tk] [ntext] NULL,
	[ht_mycom_addr] [nvarchar](100) NULL,
	[ht_cust_addr] [nvarchar](100) NULL,
	[ht_mycom_man] [char](50) NULL,
	[ht_cust_man] [char](50) NULL,
	[ht_mycom_tel] [nvarchar](50) NULL,
	[ht_cust_tel] [nvarchar](50) NULL,
	[ht_mycom_bank] [nvarchar](50) NULL,
	[ht_cust_bank] [nvarchar](50) NULL,
	[ht_mycom_banknumber] [nvarchar](30) NULL,
	[ht_cust_banknumber] [nvarchar](50) NULL,
	[ht_mycom_post] [char](50) NULL,
	[ht_cust_post] [char](50) NULL,
	[ht_mycom_nsnumber] [nvarchar](50) NULL,
	[ht_cust_nsnumber] [nvarchar](50) NULL,
	[states] [char](15) NULL,
	[username] [char](15) NULL,
	[dept] [nvarchar](50) NULL,
	[deptjb] [char](30) NULL,
	[spman] [char](15) NULL,
	[spyj] [nvarchar](350) NULL,
	[fspman] [char](30) NULL,
	[fspyj] [nvarchar](450) NULL,
	[co_number] [char](15) NULL,
	[in_no] [int] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[hltable]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[hltable](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[currname] [nvarchar](80) NOT NULL,
	[currrate] [decimal](18, 5) NULL,
	[creatdate] [nvarchar](20) NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[hhsp]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[hhsp](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[dept] [char](30) NULL,
	[role] [char](15) NULL,
	[dd_man] [char](15) NOT NULL,
	[fif] [char](10) NOT NULL,
	[fspman] [varchar](50) NOT NULL,
	[firspif] [char](10) NULL,
	[firspman] [char](20) NULL,
	[fourspif] [char](10) NULL,
	[fourspman] [char](15) NULL,
	[remark] [varchar](350) NULL,
	[dd_date] [datetime] NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[hhmanager]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[hhmanager](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[number] [varchar](50) NULL,
	[hhgoods] [varchar](150) NULL,
	[goodsdt] [varchar](80) NULL,
	[epro] [varchar](150) NULL,
	[cpro] [varchar](150) NULL,
	[batch] [varchar](150) NULL,
	[quantity] [varchar](150) NULL,
	[unit] [varchar](50) NULL,
	[supplier] [varchar](150) NULL,
	[orderform] [varchar](50) NULL,
	[contract] [varchar](50) NULL,
	[invoice] [varchar](50) NULL,
	[qydate] [varchar](50) NULL,
	[ifhx] [varchar](50) NULL,
	[ifbg] [varchar](50) NULL,
	[other] [varchar](150) NULL,
	[remark] [ntext] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[hh_goods]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[hh_goods](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[l_menber] [nvarchar](50) NULL,
	[l_man] [char](10) NULL,
	[l_dept] [nvarchar](50) NULL,
	[l_deptjb] [char](15) NULL,
	[l_date] [char](15) NULL,
	[hh_from] [nvarchar](90) NULL,
	[hh_to] [nvarchar](90) NULL,
	[hh_from_tel] [nvarchar](50) NULL,
	[hh_from_fax] [nvarchar](50) NULL,
	[hh_to_add] [nvarchar](90) NULL,
	[hh_from_add] [nvarchar](90) NULL,
	[hh_to_tel] [nvarchar](50) NULL,
	[hh_to_fax] [nvarchar](50) NULL,
	[hh_from_zip] [char](10) NULL,
	[hh_from_contact] [char](30) NULL,
	[hh_to_zip] [char](10) NULL,
	[hh_pi] [nvarchar](50) NULL,
	[pro_model] [nvarchar](50) NULL,
	[pro_number] [char](30) NULL,
	[hh_yy] [nvarchar](90) NULL,
	[hh_hj] [nvarchar](50) NULL,
	[hh_date] [char](15) NULL,
	[pro_model2] [nvarchar](50) NULL,
	[pro_number2] [char](30) NULL,
	[hh_yy2] [nvarchar](90) NULL,
	[hh_hj2] [nvarchar](50) NULL,
	[hh_date2] [char](15) NULL,
	[pro_model3] [nvarchar](50) NULL,
	[pro_number3] [char](30) NULL,
	[hh_yy3] [nvarchar](90) NULL,
	[hh_hj3] [nvarchar](50) NULL,
	[hh_date3] [char](15) NULL,
	[pro_model4] [nvarchar](50) NULL,
	[pro_number4] [char](30) NULL,
	[hh_yy4] [nvarchar](90) NULL,
	[hh_hj4] [nvarchar](50) NULL,
	[hh_date4] [char](15) NULL,
	[pro_model5] [nvarchar](50) NULL,
	[pro_number5] [char](30) NULL,
	[hh_yy5] [nvarchar](90) NULL,
	[hh_hj5] [nvarchar](50) NULL,
	[hh_date5] [char](15) NULL,
	[hh_sj_date] [nvarchar](50) NULL,
	[hh_xh_date] [nvarchar](50) NULL,
	[l_spman] [char](10) NULL,
	[hh_jy_date] [nvarchar](90) NULL,
	[hh_xxms] [nvarchar](550) NULL,
	[hh_jy_jl] [nvarchar](350) NULL,
	[hh_js_yj] [nvarchar](350) NULL,
	[l_fspman] [char](10) NULL,
	[hh_dept_yj] [nvarchar](350) NULL,
	[l_firspman] [char](10) NULL,
	[hh_zg_yj] [nvarchar](350) NULL,
	[hh_if_sd] [char](10) NULL,
	[hh_m_man] [char](10) NULL,
	[hh_m_yj] [nvarchar](150) NULL,
	[hh_po_man] [char](10) NULL,
	[hh_po_yj] [nvarchar](150) NULL,
	[remark] [text] NULL,
	[l_spqk] [char](10) NULL,
	[in_no] [int] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[hdcompany_attachment]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[hdcompany_attachment](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[hdcompanyid] [int] NULL,
	[filename] [varchar](200) NULL,
	[filepath] [varchar](320) NULL,
	[remark] [ntext] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[hdcompany]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[hdcompany](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[companyname] [varchar](150) NULL,
	[companytel] [varchar](50) NULL,
	[companyaddr] [varchar](150) NULL,
	[companyfax] [varchar](50) NULL,
	[companynet] [varchar](50) NULL,
	[companyemail] [varchar](50) NULL,
	[linkman] [varchar](50) NULL,
	[linkmantel] [varchar](50) NULL,
	[remark] [ntext] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[gz_pic]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[gz_pic](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[oid] [char](10) NULL,
	[pic_sm] [nvarchar](100) NULL,
	[pic_path] [nvarchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[getmail]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[getmail](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[mail_to] [nvarchar](100) NULL,
	[mail_to2] [nvarchar](100) NULL,
	[mail_to3] [nvarchar](100) NULL,
	[mail_sub] [nvarchar](500) NULL,
	[mail_nr] [ntext] NULL,
	[mail_man] [char](15) NULL,
	[deptjb] [char](15) NULL,
	[dept] [nvarchar](80) NULL,
	[mail_datetime] [nvarchar](30) NULL,
	[getman] [char](15) NULL,
	[form_datetime] [char](30) NULL,
	[states] [char](15) NULL,
	[sid] [int] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[programer]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[programer](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[xmnumber] [char](30) NULL,
	[starttime] [nvarchar](50) NULL,
	[application] [varchar](50) NULL,
	[spman] [varchar](50) NULL,
	[programername] [varchar](60) NULL,
	[prog_je] [char](50) NULL,
	[xm_types] [nvarchar](50) NULL,
	[prog_coname] [nvarchar](90) NULL,
	[prog_coman] [char](30) NULL,
	[prog_cotel] [nvarchar](50) NULL,
	[prog_nr] [ntext] NULL,
	[situation] [ntext] NULL,
	[progress] [ntext] NULL,
	[prog_jg] [varchar](300) NULL,
	[remark] [varchar](500) NULL,
	[states] [nvarchar](50) NULL,
	[spdate] [nvarchar](50) NULL,
	[spyj] [nvarchar](500) NULL,
	[dept] [char](35) NULL,
	[deptjb] [char](15) NULL,
	[in_no] [int] NULL,
	[m_ps] [nvarchar](500) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[progjh]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[progjh](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[xmid] [nvarchar](20) NULL,
	[jhnumber] [nvarchar](30) NULL,
	[jhname] [nvarchar](80) NULL,
	[startdt] [nvarchar](50) NULL,
	[enddt] [nvarchar](50) NULL,
	[menber] [nvarchar](50) NULL,
	[content] [ntext] NULL,
	[remark] [nvarchar](300) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[proggz]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[proggz](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[xmid] [nvarchar](20) NULL,
	[hbnumber] [nvarchar](20) NULL,
	[startdt] [nvarchar](50) NULL,
	[topic] [nvarchar](60) NULL,
	[menber] [nvarchar](30) NULL,
	[content] [ntext] NULL,
	[remark] [nvarchar](300) NULL,
	[spman] [nvarchar](10) NULL,
	[spdate] [nvarchar](10) NULL,
	[spyj] [nvarchar](500) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[profll]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[profll](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[oid] [char](10) NULL,
	[seccode] [nvarchar](10) NULL,
	[cpro] [nvarchar](80) NULL,
	[epro] [nvarchar](80) NULL,
	[remark] [nvarchar](500) NULL,
	[tnumber] [char](10) NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[profl]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[profl](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[number] [char](2) NOT NULL,
	[proflname] [nvarchar](80) NULL,
	[cpro] [varchar](80) NULL,
	[remark] [nvarchar](300) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[products]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[products](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[pro_model] [varchar](60) NOT NULL,
	[pro_gg] [nvarchar](50) NULL,
	[pro_name] [nvarchar](80) NULL,
	[pro_number] [varchar](60) NULL,
	[pro_type] [varchar](70) NULL,
	[saleprice] [decimal](18, 2) NULL,
	[price_hb] [char](10) NULL,
	[pro_s_num] [decimal](10, 0) NULL,
	[pro_num] [decimal](10, 0) NULL,
	[pro_unit] [varchar](20) NULL,
	[pro_price] [decimal](18, 3) NULL,
	[pro_supplier] [varchar](50) NULL,
	[pro_addr] [varchar](60) NULL,
	[pro_remark] [ntext] NULL,
	[sjnum] [int] NULL,
	[yqnum] [int] NULL,
	[yqdate] [nvarchar](90) NULL,
	[pro_secid] [nvarchar](50) NULL,
	[pro_sup_number] [char](30) NULL,
	[pro_min_num] [int] NULL,
	[pro_max_num] [int] NULL,
	[sale_states] [char](30) NULL,
	[sale_min_price] [decimal](18, 2) NULL,
	[sale_max_price] [decimal](18, 2) NULL,
	[pro_date] [char](30) NULL,
	[pro_man] [char](10) NULL,
	[pro_ms] [ntext] NULL,
	[pro_jstx] [ntext] NULL,
	[pro_yyfw] [ntext] NULL,
	[pin] [int] NULL,
	[js_price] [decimal](18, 2) NULL,
	[zk_price] [decimal](18, 2) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[product]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[product](
	[pid] [int] IDENTITY(1,1) NOT NULL,
	[epro] [varchar](150) NULL,
	[cpro] [nvarchar](200) NULL,
	[p_unit] [nvarchar](50) NULL,
	[p_value] [nvarchar](50) NULL,
	[profl] [varchar](50) NULL,
	[hgbm] [nvarchar](80) NULL,
	[eprosm] [nvarchar](350) NULL,
	[cprosm] [nvarchar](350) NULL,
	[prosm] [nvarchar](300) NULL,
	[man] [nvarchar](50) NULL,
	[datetime] [nvarchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[procure_xj]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[procure_xj](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[number] [varchar](50) NOT NULL,
	[man] [varchar](30) NULL,
	[linkman] [nvarchar](60) NULL,
	[co_number] [char](30) NULL,
	[coname] [varchar](60) NULL,
	[senddate] [varchar](90) NULL,
	[sup_tel] [nvarchar](90) NULL,
	[sup_fax] [nvarchar](90) NULL,
	[datetime] [varchar](60) NULL,
	[money] [char](15) NULL,
	[tbyq] [nvarchar](200) NULL,
	[remarks] [ntext] NULL,
	[state] [varchar](50) NULL,
	[sp_man] [char](15) NULL,
	[spyj] [nvarchar](200) NULL,
	[in_number] [nvarchar](50) NULL,
	[in_no] [int] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[procure_of]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[procure_of](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[number] [varchar](50) NOT NULL,
	[man] [varchar](30) NULL,
	[coname] [varchar](60) NULL,
	[senddate] [varchar](90) NULL,
	[pay_if] [nvarchar](90) NULL,
	[pay_je] [nvarchar](90) NULL,
	[datetime] [varchar](60) NULL,
	[money] [char](15) NULL,
	[remarks] [ntext] NULL,
	[l_spqk] [varchar](50) NULL,
	[l_spman] [char](15) NULL,
	[l_fif] [char](10) NULL,
	[l_fspman] [char](15) NULL,
	[l_firspif] [char](10) NULL,
	[l_firspman] [char](15) NULL,
	[l_spyj] [nvarchar](200) NULL,
	[l_dept] [nvarchar](60) NULL,
	[l_deptjb] [char](10) NULL,
	[in_no] [int] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[procure]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[procure](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[number] [varchar](50) NOT NULL,
	[man] [varchar](30) NULL,
	[sub] [nvarchar](60) NULL,
	[subck] [nvarchar](50) NULL,
	[co_number] [char](30) NULL,
	[coname] [varchar](60) NULL,
	[senddate] [varchar](90) NULL,
	[pay_if] [nvarchar](90) NULL,
	[pay_je] [decimal](18, 2) NULL,
	[datetime] [varchar](60) NULL,
	[money] [char](15) NULL,
	[tbyq] [ntext] NULL,
	[remarks] [ntext] NULL,
	[l_spqk] [varchar](50) NULL,
	[l_spman] [char](15) NULL,
	[l_fif] [char](10) NULL,
	[l_fspman] [char](15) NULL,
	[l_firspif] [char](10) NULL,
	[l_firspman] [char](15) NULL,
	[l_spyj] [nvarchar](200) NULL,
	[l_dept] [nvarchar](60) NULL,
	[l_deptjb] [char](10) NULL,
	[in_no] [int] NULL,
	[ponum] [varchar](50) NULL,
	[lxr] [nvarchar](50) NULL,
	[receiver] [varchar](50) NULL,
	[receiver_tel] [varchar](50) NULL,
	[receiver_addr] [varchar](300) NULL,
	[freight] [varchar](50) NULL,
	[express_company] [varchar](50) NULL,
	[acct] [varchar](50) NULL,
	[service_type] [varchar](50) NULL,
	[pay_type] [varchar](50) NULL,
	[coaddr] [varchar](350) NULL,
	[cotel] [varchar](350) NULL,
	[cofax] [varchar](350) NULL,
	[rate] [varchar](350) NULL,
	[self_carry] [int] NULL,
	[yfmoney] [varchar](50) NULL,
	[jydd] [varchar](50) NULL,
	[thremark] [varchar](500) NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[pro_wx_total]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[pro_wx_total](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[man] [char](15) NULL,
	[pro_model] [nvarchar](200) NULL,
	[pro_number] [nvarchar](50) NULL,
	[pro_num] [int] NULL,
	[pro_sale_money] [decimal](18, 2) NULL,
	[pro_cg_money] [decimal](18, 2) NULL,
	[pro_lr_money] [decimal](18, 2) NULL,
	[pro_lr_rate] [decimal](18, 2) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[pro_sup]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[pro_sup](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[pro_id] [nvarchar](50) NOT NULL,
	[pro_sup_id] [char](30) NOT NULL,
	[pro_sup_name] [nvarchar](80) NULL,
	[pro_sup_price] [decimal](18, 2) NULL,
	[pro_sup_hb] [char](10) NULL,
	[pro_sup_rate] [char](20) NULL,
	[pro_sup_date] [nvarchar](50) NULL,
	[pro_sup_remark] [nvarchar](100) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[pro_sale_total]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[pro_sale_total](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[man] [char](50) NULL,
	[pro_model] [nvarchar](200) NULL,
	[pro_number] [nvarchar](50) NULL,
	[pro_num] [int] NULL,
	[pro_sale_money] [nvarchar](50) NULL,
	[pro_cg_money] [nvarchar](50) NULL,
	[pro_lr_money] [nvarchar](50) NULL,
	[pro_lr_rate] [nvarchar](50) NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[pro_model_list]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[pro_model_list](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[pro_model] [varchar](300) NOT NULL
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[print_log]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[print_log](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[operator] [varchar](100) NULL,
	[number] [varchar](50) NULL,
	[created_time] [datetime] NULL,
	[type] [varchar](50) NULL,
 CONSTRAINT [PK_print_log] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[pf_xclient]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[pf_xclient](
	[clientid] [int] NULL,
	[co_number] [varchar](30) NULL,
	[username] [varchar](30) NULL,
	[rfq] [int] NULL,
	[gmjl] [int] NULL,
	[fk] [int] NULL,
	[th] [int] NULL,
	[remark] [ntext] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[pf_supplier]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[pf_supplier](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[co_number] [varchar](30) NULL,
	[username] [varchar](30) NULL,
	[rfq] [float] NULL,
	[bj] [float] NULL,
	[ch] [float] NULL,
	[fh] [float] NULL,
	[th] [float] NULL,
	[remark] [ntext] NULL,
	[pf_datetime] [datetime] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[pf_client]    Script Date: 08/20/2015 22:31:18 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[pf_client](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[co_number] [varchar](30) NULL,
	[username] [varchar](30) NULL,
	[rfq] [float] NULL,
	[gmjl] [float] NULL,
	[fk] [float] NULL,
	[th] [float] NULL,
	[remark] [ntext] NULL,
	[pf_datetime] [datetime] NULL
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
SET ANSI_PADDING OFF
GO
/****** Object:  View [dbo].[pdzview]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[pdzview]
AS
SELECT dbo.payment.id, dbo.payment.contract, dbo.payment.orderform, 
      dbo.payment.supplier, dbo.payment.pay_je, dbo.payment.htmoney, 
      dbo.payment.yjfkdate, dbo.payment.moneytypes, dbo.payment.moneyty, 
      dbo.payment.states, dbo.cgpro.ddid, dbo.cgpro.epro, dbo.cgpro.num, dbo.cgpro.unit, 
      dbo.cgpro.selljg, dbo.cgpro.cgpro_num, dbo.cgpro.wid, dbo.payment.note, 
      dbo.cgpro.money
FROM dbo.payment INNER JOIN
      dbo.cgpro ON dbo.payment.orderform = dbo.cgpro.ddid
GO
/****** Object:  View [dbo].[gatherview]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[gatherview]
AS
SELECT dbo.gathering.*, dbo.subscribe.sub AS sub
FROM dbo.gathering INNER JOIN
      dbo.subscribe ON dbo.gathering.orderform = dbo.subscribe.number
GO
/****** Object:  View [dbo].[ixjview]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[ixjview]
AS
SELECT dbo.Inquiry.id, dbo.Inquiry.number, dbo.Inquiry.coname, 
      dbo.Inquiry.quotedatetime, dbo.Inquiry_product.price, dbo.Inquiry_product.price_hb, 
      dbo.Inquiry_product.product, dbo.Inquiry_product.quantity, 
      dbo.Inquiry_product.p_quantity, dbo.Inquiry_product.punit, 
      dbo.Inquiry_product.supplier, dbo.Inquiry.states, dbo.Inquiry.man, dbo.Inquiry.cgman, 
      dbo.Inquiry_product.wid, dbo.Inquiry_product.pro_tr, dbo.Inquiry_product.pro_p_year, 
      dbo.Inquiry_product.pro_pro_no, dbo.Inquiry.dept, dbo.Inquiry.deptjb, 
      dbo.Inquiry.linktel, dbo.Inquiry.linkman, dbo.Inquiry.cofax, 
      dbo.Inquiry_product.p_price, dbo.Inquiry_product.p_supplier, 
      dbo.Inquiry_product.p_pro_tr, dbo.Inquiry.xj_date, dbo.Inquiry_product.p_product, 
      dbo.Inquiry_product.p_pro_p_year, dbo.Inquiry_product.q_price, 
      dbo.Inquiry_product.p_hb, dbo.Inquiry.co_number, dbo.Inquiry_product.cpro, 
      dbo.Inquiry_product.cpro2, dbo.Inquiry.country, dbo.Inquiry.province, 
      dbo.Inquiry.hf_date, dbo.Inquiry.city, dbo.Inquiry_product.pro_report, 
      dbo.Inquiry_product.pro_remark, dbo.Inquiry_product.pro_states, dbo.Inquiry.c_types, 
      dbo.Inquiry.copost, dbo.Inquiry.in_man, dbo.Inquiry.in_f_datetime, 
      dbo.Inquiry_product.pro_sup_no, dbo.Inquiry.cotel
FROM dbo.Inquiry INNER JOIN
      dbo.Inquiry_product ON dbo.Inquiry.id = dbo.Inquiry_product.quoteid
GO
/****** Object:  View [dbo].[cgoview]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[cgoview]
AS
SELECT dbo.procure_of.id, dbo.procure_of.number, dbo.procure_of.coname, 
      dbo.procure_of.datetime, dbo.cg_opro.selljg, dbo.cg_opro.money, dbo.cg_opro.epro, 
      dbo.cg_opro.num, dbo.cg_opro.unit, dbo.procure_of.man, dbo.procure_of.l_spqk, 
      dbo.procure_of.l_dept, dbo.procure_of.l_deptjb
FROM dbo.procure_of INNER JOIN
      dbo.cg_opro ON dbo.procure_of.id = dbo.cg_opro.ddid
GO
/****** Object:  View [dbo].[cgview]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[cgview]
AS
SELECT dbo.procure.id, dbo.procure.number, dbo.procure.coname, dbo.procure.datetime, 
      dbo.cgpro.selljg, dbo.cgpro.money, dbo.cgpro.epro, dbo.cgpro.num, 
      dbo.cgpro.cgpro_num, dbo.cgpro.unit, dbo.procure.man, dbo.cgpro.cgpro_ydatetime, 
      dbo.procure.l_spqk, dbo.cgpro.wid, dbo.cgpro.cgpro_sdatetime, dbo.cgpro.cpro, 
      dbo.procure.l_dept, dbo.procure.l_deptjb, dbo.procure.sub, dbo.procure.senddate, 
      dbo.procure.pay_je, dbo.procure.pay_if, dbo.cgpro.rate, dbo.cgpro.remark, 
      dbo.cgpro.supplier
FROM dbo.procure INNER JOIN
      dbo.cgpro ON dbo.procure.id = dbo.cgpro.ddid
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1[50] 2[25] 3) )"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1 [56] 4 [18] 2))"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "procure"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 116
               Right = 179
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "cgpro"
            Begin Extent = 
               Top = 6
               Left = 217
               Bottom = 116
               Right = 388
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
      RowHeights = 240
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'cgview'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'cgview'
GO
/****** Object:  View [dbo].[cg_th_cw]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[cg_th_cw] as SELECT dbo.th_table_supplier.id, dbo.th_table_supplier.number,       dbo.th_table_supplier.coname, dbo.th_table_supplier.sub, dbo.th_table_supplier.man,       dbo.th_table_supplier.spman, dbo.th_table_supplier.state,       dbo.th_table_supplier.spdate, dbo.th_table_supplier.remarks,       dbo.th_table_supplier.deptjb, dbo.payment.statesFROM dbo.th_table_supplier INNER JOIN      dbo.payment ON dbo.th_table_supplier.number = dbo.payment.contract
GO
/****** Object:  View [dbo].[dzview]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[dzview]
AS
SELECT dbo.gathering.id, dbo.gathering.invoice, dbo.gathering.orderform, 
      dbo.gathering.coname, dbo.gathering.sjdate, dbo.ddpro.epro, dbo.ddpro.num, 
      dbo.ddpro.unit, dbo.ddpro.salejg, dbo.ddpro.pricehb, dbo.ddpro.supplier, 
      dbo.ddpro.wid, dbo.gathering.note, dbo.gathering.states, dbo.gathering.fyid, 
      dbo.ddpro.s_num, dbo.ddpro.s_tr_date, dbo.ddpro.s_c_num, dbo.gathering.i_man, 
      dbo.gathering.yjskdate, dbo.ddpro.remark AS Expr1
FROM dbo.gathering INNER JOIN
      dbo.ddpro ON dbo.gathering.fyid = dbo.ddpro.ddid
GO
/****** Object:  View [dbo].[ckview]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[ckview]
AS
SELECT     dbo.outhouse.id, dbo.outhouse.pro_fynum, dbo.outhouse.pro_coname, dbo.outhouse.pro_model, dbo.outhouse.pro_name, dbo.outhouse.pro_num, 
                      dbo.outhouse.pro_unit, dbo.outhouse.pro_supplier, dbo.outhouse.pro_datetime, dbo.outhouse.pro_number, dbo.outhouse.slinkman, dbo.outhouse.slinktel, 
                      dbo.outhouse.states, dbo.outhouse.ddid, dbo.outhouse.remark, dbo.outhouse.wid, dbo.outhouse.pro_coname_num, dbo.outhouse.pro_sales_price, 
                      dbo.outhouse.pro_price_hb, dbo.outhouse.pro_rate_types, dbo.outhouse.pro_rate, dbo.outhouse.pro_out_num, dbo.outhouse.in_no, dbo.subscribe.sub, 
                      dbo.subscribe.item, dbo.subscribe.man, dbo.outhouse.salejg
FROM         dbo.outhouse INNER JOIN
                      dbo.subscribe ON dbo.outhouse.pro_fynum = dbo.subscribe.number
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "subscribe"
            Begin Extent = 
               Top = 6
               Left = 250
               Bottom = 125
               Right = 408
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "outhouse"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 125
               Right = 212
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
      Begin ColumnWidths = 9
         Width = 284
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'ckview'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'ckview'
GO
/****** Object:  View [dbo].[subview2]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[subview2]
AS
SELECT     dbo.subscribe.number, dbo.subscribe.coname, dbo.subscribe.yj, dbo.subscribe.datetime, dbo.ddpro.selljg, dbo.ddpro.epro, dbo.ddpro.num, dbo.ddpro.unit, 
                      dbo.ddpro.salejg, dbo.ddpro.pricehb, dbo.ddpro.rale_types, dbo.ddpro.rale, dbo.ddpro.supplier, dbo.ddpro.fypronum, dbo.subscribe.man, dbo.subscribe.habitus, 
                      dbo.subscribe.state, dbo.subscribe.deptjb, dbo.subscribe.dept, dbo.subscribe.p_states, dbo.ddpro.wid, dbo.ddpro.fy_states, dbo.ddpro.s_num, dbo.ddpro.s_c_num, 
                      dbo.ddpro.s_tr_date, dbo.subscribe.id, dbo.subscribe.s_check, dbo.subscribe.senddate, dbo.ddpro.cpro, dbo.subscribe.cg_man, dbo.subscribe.send_date, 
                      dbo.subscribe.yf_money, dbo.subscribe.yf_types, dbo.subscribe.spman, dbo.subscribe.spdate, dbo.subscribe.item, dbo.ddpro.money, dbo.subscribe.sub, 
                      dbo.subscribe.fy_number, dbo.subscribe.other_fy, dbo.ddpro.x_no, dbo.ddpro.p_check, dbo.ddpro.money2, dbo.subscribe.custno, dbo.subscribe.mode, 
                      dbo.ddpro.remark
FROM         dbo.subscribe LEFT OUTER JOIN
                      dbo.ddpro ON dbo.subscribe.id = dbo.ddpro.ddid
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "subscribe"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 125
               Right = 196
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "ddpro"
            Begin Extent = 
               Top = 6
               Left = 234
               Bottom = 125
               Right = 376
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'subview2'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'subview2'
GO
/****** Object:  View [dbo].[subview]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[subview]
AS
SELECT dbo.subscribe.number, dbo.subscribe.coname, dbo.subscribe.yj, 
      dbo.subscribe.datetime, dbo.ddpro.selljg, dbo.ddpro.epro, dbo.ddpro.num, 
      dbo.ddpro.unit, dbo.ddpro.salejg, dbo.ddpro.pricehb, dbo.ddpro.rale_types, 
      dbo.ddpro.rale, dbo.ddpro.supplier, dbo.ddpro.fypronum, dbo.subscribe.man, 
      dbo.subscribe.habitus, dbo.subscribe.state, dbo.subscribe.deptjb, 
      dbo.subscribe.dept, dbo.subscribe.p_states, dbo.ddpro.wid, dbo.ddpro.fy_states, 
      dbo.ddpro.s_num, dbo.ddpro.s_c_num, dbo.ddpro.s_tr_date, dbo.subscribe.id, 
      dbo.subscribe.s_check, dbo.subscribe.senddate, dbo.ddpro.cpro, 
      dbo.subscribe.cg_man, dbo.subscribe.send_date, dbo.subscribe.yf_money, 
      dbo.subscribe.yf_types, dbo.subscribe.spman, dbo.subscribe.spdate, 
      dbo.subscribe.item, dbo.ddpro.money, dbo.subscribe.sub, dbo.subscribe.fy_number, 
      dbo.subscribe.other_fy, dbo.ddpro.x_no, dbo.ddpro.p_check, dbo.ddpro.money2, 
      dbo.subscribe.custno, dbo.subscribe.mode, dbo.ddpro.remark
FROM dbo.subscribe INNER JOIN
      dbo.ddpro ON dbo.subscribe.id = dbo.ddpro.ddid
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1[50] 2[25] 3) )"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1 [56] 4 [18] 2))"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "subscribe"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 116
               Right = 185
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "ddpro"
            Begin Extent = 
               Top = 6
               Left = 223
               Bottom = 116
               Right = 364
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
      RowHeights = 240
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'subview'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'subview'
GO
/****** Object:  View [dbo].[scoutview]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[scoutview]
AS
SELECT dbo.outhouse.pro_fynum, dbo.outhouse.pro_datetime, dbo.outhouse.pro_model, 
      dbo.outhouse.pro_num, dbo.outhouse.pro_unit, dbo.outhouse.pro_sales_price, 
      dbo.outhouse.pro_price_hb, dbo.outhouse.pro_rate, dbo.outhouse.pro_supplier, 
      dbo.outhouse.pro_number, dbo.client.username, dbo.client.deptjb, dbo.client.dept, 
      dbo.outhouse.ddid, dbo.outhouse.pro_coname_num, dbo.client.clientid, 
      dbo.client.co_number, dbo.client.coname, dbo.client.city, dbo.client.province, 
      dbo.client.cofrdb, dbo.client.cozczb, dbo.client.cotypes, dbo.client.coyyzz, 
      dbo.client.tradetypes, dbo.client.cokhjb, dbo.outhouse.pro_name, 
      dbo.outhouse.wid
FROM dbo.outhouse INNER JOIN
      dbo.client ON dbo.outhouse.pro_coname_num = dbo.client.co_number
GO
/****** Object:  View [dbo].[samdhview]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[samdhview]
AS
SELECT dbo.sample_dh.id, dbo.sample_dh.number, dbo.sample_dh.coname, 
      dbo.sample_dh.datetime, dbo.sam_dhpro.selljg, dbo.sam_dhpro.epro, 
      dbo.sam_dhpro.num, dbo.sam_dhpro.unit, dbo.sam_dhpro.salejg, 
      dbo.sam_dhpro.pricehb, dbo.sam_dhpro.rale_types, dbo.sam_dhpro.rale, 
      dbo.sam_dhpro.supplier, dbo.sam_dhpro.fypronum, dbo.sample_dh.man, 
      dbo.sample_dh.habitus, dbo.sample_dh.state, dbo.sample_dh.deptjb, 
      dbo.sample_dh.dept, dbo.sample_dh.senddate, dbo.sam_dhpro.wid, 
      dbo.sam_dhpro.cpro, dbo.sample_dh.sub, dbo.sample_dh.spman, 
      dbo.sample_dh.fveight, dbo.sample_dh.insurance, dbo.sample_dh.linkman, 
      dbo.sam_dhpro.pro_snum, dbo.sam_dhpro.pro_sc_num, dbo.sam_dhpro.cpro2, 
      dbo.sam_dhpro.fyproall, dbo.sample_dh.money
FROM dbo.sample_dh INNER JOIN
      dbo.sam_dhpro ON dbo.sample_dh.id = dbo.sam_dhpro.ddid
GO
/****** Object:  View [dbo].[inview]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[inview]
AS
SELECT dbo.cgpro.epro, dbo.cgpro.cpro, dbo.procure.number, dbo.procure.co_number, 
      dbo.procure.coname, dbo.procure.senddate, dbo.procure.sub, dbo.procure.man, 
      dbo.procure.subck, dbo.cgpro.pro_number, dbo.cgpro.num, dbo.cgpro.unit, 
      dbo.cgpro.rate, dbo.cgpro.supplier, dbo.procure.l_spqk, dbo.procure.datetime, 
      dbo.cgpro.cgpro_ydatetime, dbo.procure.id
FROM dbo.procure INNER JOIN
      dbo.cgpro ON dbo.procure.id = dbo.cgpro.ddid
GO
/****** Object:  View [dbo].[csdh]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[csdh]
AS
SELECT     dbo.th_table.number AS thnumber, dbo.th_table.sub AS xiaoshou, dbo.procure.number AS caigu, dbo.procure.id
FROM         dbo.th_table INNER JOIN
                      dbo.procure ON dbo.th_table.sub = dbo.procure.sub
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1[50] 2[25] 3) )"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1 [56] 4 [18] 2))"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "th_table"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 116
               Right = 179
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "procure"
            Begin Extent = 
               Top = 6
               Left = 217
               Bottom = 116
               Right = 388
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'csdh'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'csdh'
GO
/****** Object:  View [dbo].[rkview]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[rkview]
AS
SELECT dbo.in_warehouse.id, dbo.in_warehouse.number, dbo.in_warehouse.supplier, 
      dbo.in_warehouse.int_date, dbo.in_warehouse.man, dbo.in_warehouse.states, 
      dbo.rkhouse.pro_model, dbo.rkhouse.pro_name, dbo.rkhouse.pro_num, 
      dbo.rkhouse.pro_unit, dbo.rkhouse.pro_price, dbo.rkhouse.pro_hb, 
      dbo.in_warehouse.g_man, dbo.in_warehouse.deptjb, dbo.in_warehouse.dept, 
      dbo.in_warehouse.int_types, dbo.rkhouse.pro_number, dbo.rkhouse.pro_addr, 
      dbo.rkhouse.pro_datetime, dbo.rkhouse.pro_id, dbo.rkhouse.pro_supplier,dbo.rkhouse.remark,dbo.rkhouse.pro_types
FROM dbo.in_warehouse INNER JOIN
      dbo.rkhouse ON dbo.in_warehouse.id = dbo.rkhouse.pro_rk_num
GO
/****** Object:  View [dbo].[quoteview]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[quoteview]
AS
SELECT dbo.quote.id, dbo.quote.number, dbo.quote.in_number, dbo.quote.quotedatetime, 
      dbo.quote.coname, dbo.quote.man, dbo.quote.spman, dbo.quote.states, 
      dbo.quote.dept, dbo.quote.deptjb, dbo.quoteproduct.product, 
      dbo.quoteproduct.quantity, dbo.quoteproduct.unit, dbo.quoteproduct.price, 
      dbo.quoteproduct.pricehb
FROM dbo.quote INNER JOIN
      dbo.quoteproduct ON dbo.quote.id = dbo.quoteproduct.quoteid
GO
/****** Object:  View [dbo].[programerview]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[programerview]
AS
SELECT dbo.programer.id, dbo.programer.programername, dbo.programer.application, 
      dbo.proggz.startdt, dbo.proggz.content, dbo.programer.xm_types
FROM dbo.programer INNER JOIN
      dbo.proggz ON dbo.programer.id = dbo.proggz.xmid
GO
/****** Object:  View [dbo].[thview]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[thview]
AS
SELECT dbo.th_table.id, dbo.th_table.number, dbo.th_table.coname, dbo.th_table.datetime, 
      dbo.th_pro.selljg, dbo.th_pro.money, dbo.th_pro.epro, dbo.th_pro.num, 
      dbo.th_pro.unit, dbo.th_pro.salejg, dbo.th_pro.pricehb, dbo.th_pro.supplier, 
      dbo.th_table.man, dbo.th_table.habitus, dbo.th_table.state, dbo.th_table.deptjb, 
      dbo.th_table.dept, dbo.th_pro.s_c_num, dbo.th_pro.s_num, 
      dbo.th_table.remarks
FROM dbo.th_table INNER JOIN
      dbo.th_pro ON dbo.th_table.id = dbo.th_pro.ddid
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1[50] 2[25] 3) )"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1 [56] 4 [18] 2))"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "th_table"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 116
               Right = 179
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "th_pro"
            Begin Extent = 
               Top = 6
               Left = 217
               Bottom = 116
               Right = 370
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
      RowHeights = 240
      Begin ColumnWidths = 21
         Width = 284
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
         Width = 1440
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'thview'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'thview'
GO
/****** Object:  View [dbo].[trview]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[trview]
AS
SELECT dbo.subscribe.id,  
      dbo.subscribe.coname, dbo.ddpro.epro, 
      dbo.ddpro.cpro,  dbo.ddpro.num, dbo.ddpro.unit, 
      dbo.subscribe.state
FROM dbo.subscribe INNER JOIN
      dbo.ddpro ON dbo.subscribe.id = dbo.ddpro.ddid
GO
/****** Object:  View [dbo].[wxview]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[wxview]
AS
SELECT dbo.wxtable.id, dbo.wxtable.wxid, dbo.wxtable.coname, dbo.wxtable.coman, 
      dbo.wxtable.wxman, dbo.wx_pro.pro_model, dbo.wx_pro.pro_gzxx, 
      dbo.wx_pro.pro_fy, dbo.wxtable.state, dbo.wx_pro.pro_clfs, dbo.wx_pro.pro_tr_types, 
      dbo.wxtable.cotel, dbo.wxtable.copost, dbo.wxtable.sales_man, 
      dbo.wx_pro.pro_number, dbo.wx_pro.pro_num, dbo.wx_pro.pro_unit, dbo.wx_pro.wid, 
      dbo.wxtable.man, dbo.wx_pro.pro_sk, dbo.wx_pro.pro_s_date, dbo.wx_pro.pro_zt, 
      dbo.wx_pro.pro_rg_date, dbo.wx_pro.pro_yf
FROM dbo.wxtable INNER JOIN
      dbo.wx_pro ON dbo.wxtable.id = dbo.wx_pro.wxid
GO
/****** Object:  View [dbo].[xjview2]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[xjview2]
AS
SELECT dbo.Inquiry.id, dbo.Inquiry.number, dbo.Inquiry.coname, 
      dbo.Inquiry.quotedatetime, dbo.Inquiry_product.price, dbo.Inquiry_product.price_hb, 
      dbo.Inquiry_product.product, dbo.Inquiry_product.quantity, 
      dbo.Inquiry_product.p_quantity, dbo.Inquiry_product.punit, 
      dbo.Inquiry_product.supplier, dbo.Inquiry.states, dbo.Inquiry.man, dbo.Inquiry.cgman, 
      dbo.Inquiry_product.wid, dbo.Inquiry_product.pro_tr, dbo.Inquiry_product.pro_p_year, 
      dbo.Inquiry_product.pro_pro_no, dbo.Inquiry.dept, dbo.Inquiry.deptjb, 
      dbo.Inquiry.linktel, dbo.Inquiry.linkman, dbo.Inquiry.cofax, 
      dbo.Inquiry_product.p_price, dbo.Inquiry_product.p_supplier, 
      dbo.Inquiry_product.p_pro_tr, dbo.Inquiry.xj_date, dbo.Inquiry_product.p_product, 
      dbo.Inquiry_product.p_pro_p_year, dbo.Inquiry_product.q_price, 
      dbo.Inquiry_product.p_hb, dbo.Inquiry.co_number, dbo.Inquiry_product.cpro, 
      dbo.Inquiry_product.cpro2, dbo.Inquiry.country, dbo.Inquiry.province, 
      dbo.Inquiry.hf_date, dbo.Inquiry.city, dbo.Inquiry_product.pro_report, 
      dbo.Inquiry_product.pro_remark, dbo.Inquiry_product.pro_states, dbo.Inquiry.c_types, 
      dbo.Inquiry.copost, dbo.Inquiry.in_man, dbo.Inquiry.in_f_datetime, 
      dbo.Inquiry_product.pro_sup_no, dbo.Inquiry.cotel, dbo.Inquiry.coaddr, 
      dbo.Inquiry.linkemail, dbo.Inquiry.linkwap, dbo.Inquiry.courier, dbo.Inquiry.acct, 
      dbo.Inquiry.terms
FROM dbo.Inquiry INNER JOIN
      dbo.Inquiry_product ON dbo.Inquiry.id = dbo.Inquiry_product.quoteid
GO
/****** Object:  View [dbo].[xjview]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[xjview]
AS
SELECT dbo.procure_xj.id, dbo.procure_xj.number, dbo.procure_xj.coname, 
      dbo.procure_xj.datetime, dbo.xjpro.selljg, dbo.xjpro.money, dbo.xjpro.epro, 
      dbo.xjpro.num, dbo.xjpro.cgpro_num, dbo.xjpro.unit, dbo.procure_xj.man, 
      dbo.procure_xj.state, dbo.xjpro.wid, dbo.xjpro.cgpro_sdatetime, dbo.xjpro.cpro, 
      dbo.xjpro.remark, dbo.xjpro.supplier, dbo.xjpro.pro_sup_no
FROM dbo.procure_xj INNER JOIN
      dbo.xjpro ON dbo.procure_xj.id = dbo.xjpro.ddid
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1[50] 2[25] 3) )"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1 [56] 4 [18] 2))"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "procure_xj"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 116
               Right = 173
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "xjpro"
            Begin Extent = 
               Top = 6
               Left = 211
               Bottom = 116
               Right = 382
            End
            DisplayFlags = 280
            TopColumn = 13
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
      RowHeights = 240
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'xjview'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'xjview'
GO
/****** Object:  View [dbo].[warepicview]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[warepicview]
AS
SELECT dbo.warehouse.id, dbo.warehouse.pro_model, dbo.warehouse.pro_type, 
      dbo.warehouse.pro_num, dbo.o_pic.oid, dbo.o_pic.pic_sm, dbo.o_pic.pic_path, 
      dbo.warehouse.pro_gg, dbo.warehouse.pro_name, dbo.warehouse.pro_number, 
      dbo.warehouse.saleprice, dbo.warehouse.price_hb, dbo.warehouse.pro_s_num, 
      dbo.warehouse.pro_price, dbo.warehouse.pro_unit, dbo.warehouse.pro_supplier, 
      dbo.warehouse.pro_addr, dbo.warehouse.pro_remark, dbo.warehouse.sjnum, 
      dbo.warehouse.yqnum, dbo.warehouse.yqdate, dbo.warehouse.pro_sup_number, 
      dbo.warehouse.pro_secid, dbo.warehouse.pro_min_num, 
      dbo.warehouse.pro_max_num, dbo.warehouse.pin
FROM dbo.warehouse INNER JOIN
      dbo.o_pic ON dbo.warehouse.id = dbo.o_pic.oid
GO
/****** Object:  View [dbo].[view_subscribe_search]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[view_subscribe_search]
AS
SELECT dbo.subscribe.number, dbo.subscribe.coname, dbo.subscribe.yj, 
      dbo.subscribe.datetime, dbo.subscribe.item, dbo.ddpro.selljg, dbo.ddpro.epro, 
      dbo.ddpro.num, dbo.ddpro.unit, dbo.ddpro.salejg, dbo.ddpro.pricehb, 
      dbo.ddpro.rale_types, dbo.ddpro.rale, dbo.ddpro.supplier, dbo.ddpro.fypronum, 
      dbo.subscribe.man, dbo.subscribe.habitus, dbo.subscribe.state, 
      dbo.subscribe.deptjb, dbo.subscribe.dept, dbo.subscribe.p_states, dbo.ddpro.wid, 
      dbo.ddpro.fy_states, dbo.ddpro.s_num, dbo.ddpro.s_c_num, dbo.ddpro.s_tr_date, 
      dbo.subscribe.id, dbo.subscribe.s_check, dbo.subscribe.senddate, dbo.ddpro.cpro, 
      dbo.subscribe.cg_man, dbo.subscribe.send_date, dbo.subscribe.yf_money, 
      dbo.subscribe.yf_types, dbo.subscribe.spman, dbo.subscribe.spdate, 
      dbo.subscribe.item AS Expr1, dbo.ddpro.money, dbo.subscribe.sub, 
      dbo.subscribe.fy_number, dbo.subscribe.other_fy, dbo.ddpro.x_no, 
      dbo.ddpro.p_check, dbo.ddpro.money2, dbo.ddpro.remark
FROM dbo.subscribe left outer  JOIN
      dbo.ddpro ON dbo.subscribe.id = dbo.ddpro.ddid
GO
/****** Object:  View [dbo].[skview]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[skview]
AS
SELECT dbo.subscribe.man, dbo.gathering.id, dbo.gathering.invoice, dbo.gathering.fyid, 
      dbo.gathering.orderform, dbo.gathering.coname, dbo.gathering.smoney, 
      dbo.gathering.sjdate, dbo.gathering.sjskdate, dbo.gathering.mode, 
      dbo.gathering.datet, dbo.gathering.moneytypes, dbo.gathering.states
FROM dbo.subscribe INNER JOIN
      dbo.gathering ON dbo.subscribe.number = dbo.gathering.orderform
GO
/****** Object:  View [dbo].[samview]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[samview]
AS
SELECT dbo.sample.id, dbo.sample.number, dbo.sample.coname, dbo.sample.datetime, 
      dbo.sam_pro.selljg, dbo.sam_pro.money, dbo.sam_pro.epro, dbo.sam_pro.num, 
      dbo.sam_pro.unit, dbo.sam_pro.salejg, dbo.sam_pro.pricehb, 
      dbo.sam_pro.rale_types, dbo.sam_pro.rale, dbo.sam_pro.supplier, 
      dbo.sam_pro.fypronum, dbo.sample.man, dbo.sample.habitus, dbo.sample.state, 
      dbo.sample.deptjb, dbo.sample.dept, dbo.sample.senddate, dbo.sam_pro.wid, 
      dbo.sam_pro.cpro, dbo.sample.sub, dbo.sample.spman, dbo.sample.fveight, 
      dbo.sample.insurance, dbo.sample.linkman, dbo.sam_pro.pro_snum, 
      dbo.sam_pro.pro_sc_num, dbo.sam_pro.cpro2, dbo.sam_pro.pro_r_date, 
      dbo.sam_pro.pro_sr_date
FROM dbo.sample INNER JOIN
      dbo.sam_pro ON dbo.sample.id = dbo.sam_pro.ddid
GO
/****** Object:  View [dbo].[payview]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[payview]
AS
SELECT dbo.payment.*, dbo.procure.ponum AS ponum, dbo.procure.sub AS sub
FROM dbo.payment INNER JOIN
      dbo.procure ON dbo.payment.contract = dbo.procure.number
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1[50] 2[25] 3) )"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1 [56] 4 [18] 2))"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "payment"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 116
               Right = 191
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "procure"
            Begin Extent = 
               Top = 6
               Left = 229
               Bottom = 116
               Right = 370
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
      RowHeights = 240
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'payview'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'payview'
GO
/****** Object:  View [dbo].[topIxjview]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[topIxjview]
AS
SELECT     TOP (100) id, number, coname, quotedatetime, price, price_hb, product, quantity, p_quantity, punit, supplier, states, man, cgman, wid, pro_tr, 
                      pro_p_year, pro_pro_no, dept, deptjb, linktel, linkman, cofax, p_price, p_supplier, p_pro_tr, xj_date, p_product, p_pro_p_year, q_price, p_hb, 
                      co_number, cpro, cpro2, country, province, hf_date, city, pro_report, pro_remark, pro_states, c_types, copost, in_man, in_f_datetime, pro_sup_no, 
                      cotel
FROM         dbo.ixjview
ORDER BY id DESC
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "ixjview"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 121
               Right = 189
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
      Begin ColumnWidths = 48
         Width = 284
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         G' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'topIxjview'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane2', @value=N'roupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'topIxjview'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=2 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'topIxjview'
GO
/****** Object:  View [dbo].[csubview]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[csubview]
AS
SELECT dbo.subview.number, dbo.subview.yj, dbo.subview.datetime, dbo.subview.selljg, 
      dbo.subview.money, dbo.subview.epro, dbo.subview.num, dbo.subview.unit, 
      dbo.subview.salejg, dbo.subview.pricehb, dbo.subview.rale_types, dbo.subview.rale, 
      dbo.subview.supplier, dbo.subview.fypronum, dbo.subview.man, 
      dbo.subview.habitus, dbo.subview.state, dbo.subview.deptjb, dbo.subview.dept, 
      dbo.subview.wid, dbo.subview.id, dbo.subview.senddate, dbo.client.clientid, 
      dbo.client.co_number, dbo.client.coname, dbo.client.city, dbo.client.province, 
      dbo.client.cofrdb, dbo.client.cozczb, dbo.client.cotypes, dbo.client.coyyzz, 
      dbo.client.tradetypes, dbo.client.cokhjb, dbo.subview.cpro, dbo.subview.s_num, 
      dbo.subview.s_c_num
FROM dbo.subview INNER JOIN
      dbo.client ON dbo.subview.coname = dbo.client.coname
GO
/****** Object:  View [dbo].[csdhs]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[csdhs]
AS
SELECT     dbo.csdh.thnumber, dbo.csdh.xiaoshou, dbo.csdh.caigu, dbo.cgpro.epro AS pro
FROM         dbo.csdh INNER JOIN
                      dbo.cgpro ON dbo.csdh.id = dbo.cgpro.ddid
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "csdh"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 121
               Right = 169
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "cgpro"
            Begin Extent = 
               Top = 6
               Left = 207
               Bottom = 121
               Right = 369
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'csdhs'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'csdhs'
GO
/****** Object:  View [dbo].[cg_th_cwsear]    Script Date: 08/20/2015 22:31:21 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[cg_th_cwsear]
AS
SELECT dbo.cg_th_cw.id, dbo.cg_th_cw.number, dbo.cg_th_cw.coname, dbo.cg_th_cw.sub, 
      dbo.cg_th_cw.man, dbo.cg_th_cw.spman, dbo.cg_th_cw.state, dbo.cg_th_cw.spdate, 
      dbo.cg_th_cw.remarks, dbo.cg_th_cw.deptjb, dbo.cg_th_cw.states, 
      dbo.th_pro_supplier.epro
FROM dbo.cg_th_cw INNER JOIN
      dbo.th_pro_supplier ON dbo.cg_th_cw.id = dbo.th_pro_supplier.ddid
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPane1', @value=N'[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin DesignProperties = 
   Begin PaneConfigurations = 
      Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
      End
      Begin PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
      End
      Begin PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1[50] 2[25] 3) )"
      End
      Begin PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
      End
      Begin PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
      End
      Begin PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
      End
      Begin PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
      End
      Begin PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
      End
      Begin PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1 [56] 4 [18] 2))"
      End
      Begin PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
      End
      Begin PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
      End
      Begin PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
      End
      Begin PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
      End
      Begin PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
      End
      Begin PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
      End
      ActivePaneConfig = 0
   End
   Begin DiagramPane = 
      Begin Origin = 
         Top = 0
         Left = 0
      End
      Begin Tables = 
         Begin Table = "cg_th_cw"
            Begin Extent = 
               Top = 6
               Left = 38
               Bottom = 116
               Right = 173
            End
            DisplayFlags = 280
            TopColumn = 0
         End
         Begin Table = "th_pro_supplier"
            Begin Extent = 
               Top = 6
               Left = 211
               Bottom = 116
               Right = 352
            End
            DisplayFlags = 280
            TopColumn = 0
         End
      End
   End
   Begin SQLPane = 
   End
   Begin DataPane = 
      Begin ParameterDefaults = ""
      End
      RowHeights = 240
   End
   Begin CriteriaPane = 
      Begin ColumnWidths = 11
         Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
      End
   End
End
' , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'cg_th_cwsear'
GO
EXEC sys.sp_addextendedproperty @name=N'MS_DiagramPaneCount', @value=1 , @level0type=N'SCHEMA',@level0name=N'dbo', @level1type=N'VIEW',@level1name=N'cg_th_cwsear'
GO
/****** Object:  Default [DF__ddpro__th_num__6383C8BA]    Script Date: 08/20/2015 22:31:18 ******/
ALTER TABLE [dbo].[ddpro] ADD  DEFAULT ((0)) FOR [th_num]
GO
