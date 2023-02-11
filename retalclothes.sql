USE [master]
GO
/****** Object:  Database [RentalClothes]    Script Date: 11/02/2023 4:04:06 PM ******/
CREATE DATABASE [RentalClothes]

GO
ALTER DATABASE [RentalClothes] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [RentalClothes].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [RentalClothes] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [RentalClothes] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [RentalClothes] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [RentalClothes] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [RentalClothes] SET ARITHABORT OFF 
GO
ALTER DATABASE [RentalClothes] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [RentalClothes] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [RentalClothes] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [RentalClothes] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [RentalClothes] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [RentalClothes] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [RentalClothes] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [RentalClothes] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [RentalClothes] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [RentalClothes] SET  DISABLE_BROKER 
GO
ALTER DATABASE [RentalClothes] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [RentalClothes] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [RentalClothes] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [RentalClothes] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [RentalClothes] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [RentalClothes] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [RentalClothes] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [RentalClothes] SET RECOVERY FULL 
GO
ALTER DATABASE [RentalClothes] SET  MULTI_USER 
GO
ALTER DATABASE [RentalClothes] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [RentalClothes] SET DB_CHAINING OFF 
GO
ALTER DATABASE [RentalClothes] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [RentalClothes] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [RentalClothes] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [RentalClothes] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'RentalClothes', N'ON'
GO
ALTER DATABASE [RentalClothes] SET QUERY_STORE = OFF
GO
USE [RentalClothes]
GO
/****** Object:  Table [dbo].[brand]    Script Date: 11/02/2023 4:04:06 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[brand](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[name] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[category]    Script Date: 11/02/2023 4:04:06 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[category](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[name] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[image]    Script Date: 11/02/2023 4:04:06 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[image](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[name] [varchar](255) NULL,
	[url] [varchar](255) NULL,
	[product_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[notification]    Script Date: 11/02/2023 4:04:06 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[notification](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [varchar](255) NULL,
	[created_date] [datetime2](7) NULL,
	[description] [varchar](255) NULL,
	[is_read] [bit] NULL,
	[modified_by] [varchar](255) NULL,
	[modified_date] [datetime2](7) NULL,
	[sort_descripsion] [varchar](255) NULL,
	[status] [varchar](255) NULL,
	[title] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[order_details]    Script Date: 11/02/2023 4:04:06 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order_details](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[name] [varchar](255) NULL,
	[price] [float] NULL,
	[quantity] [int] NULL,
	[order_id] [bigint] NULL,
	[product_id] [bigint] NULL,
 CONSTRAINT [PK__order_de__3213E83FA770C079] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[orders]    Script Date: 11/02/2023 4:04:06 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[orders](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [varchar](255) NULL,
	[created_date] [datetime2](7) NULL,
	[modified_by] [varchar](255) NULL,
	[modified_date] [datetime2](7) NULL,
	[order_brorrow_date] [datetime2](7) NULL,
	[order_return_date] [datetime2](7) NULL,
	[status] [varchar](255) NULL,
	[total_price] [float] NULL,
	[total_quantity] [int] NULL,
	[user_id] [bigint] NULL,
	[voucher_id] [bigint] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product]    Script Date: 11/02/2023 4:04:06 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [varchar](255) NULL,
	[created_date] [datetime2](7) NULL,
	[description] [varchar](255) NULL,
	[modified_by] [varchar](255) NULL,
	[modified_date] [datetime2](7) NULL,
	[name] [varchar](255) NULL,
	[price] [float] NULL,
	[quantity] [int] NULL,
	[sort_description] [varchar](255) NULL,
	[status] [varchar](255) NULL,
	[brand_id] [bigint] NULL,
	[category_id] [bigint] NULL,
 CONSTRAINT [PK__product__3213E83F48E254CE] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[role]    Script Date: 11/02/2023 4:04:06 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[role](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[name] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[user__notification]    Script Date: 11/02/2023 4:04:06 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[user__notification](
	[user_id] [bigint] NOT NULL,
	[notification_id] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[user_id] ASC,
	[notification_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[user_role]    Script Date: 11/02/2023 4:04:06 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[user_role](
	[user_id] [bigint] NOT NULL,
	[role_id] [bigint] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[user_id] ASC,
	[role_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[users]    Script Date: 11/02/2023 4:04:06 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[users](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[address] [varchar](255) NULL,
	[avatar] [varchar](255) NULL,
	[birthday] [datetime2](7) NULL,
	[created_by] [varchar](255) NULL,
	[created_date] [datetime2](7) NULL,
	[email] [varchar](254) NULL,
	[first_name] [varchar](50) NULL,
	[image_url] [varchar](256) NULL,
	[last_name] [varchar](50) NULL,
	[modified_by] [varchar](255) NULL,
	[modified_date] [datetime2](7) NULL,
	[password_hash] [varchar](60) NOT NULL,
	[phone] [varchar](255) NULL,
	[username] [varchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[voucher]    Script Date: 11/02/2023 4:04:06 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[voucher](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[created_by] [varchar](255) NULL,
	[created_date] [datetime2](7) NULL,
	[discount] [float] NULL,
	[end_date] [datetime2](7) NULL,
	[modified_by] [varchar](255) NULL,
	[modified_date] [datetime2](7) NULL,
	[name] [varchar](255) NULL,
	[start_date] [datetime2](7) NULL,
	[status] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[brand] ON 
GO
INSERT [dbo].[brand] ([id], [name]) VALUES (1, N'Proactive Island Strategist')
GO
INSERT [dbo].[brand] ([id], [name]) VALUES (2, N'customized interface')
GO
INSERT [dbo].[brand] ([id], [name]) VALUES (3, N'Concrete')
GO
INSERT [dbo].[brand] ([id], [name]) VALUES (4, N'copy')
GO
INSERT [dbo].[brand] ([id], [name]) VALUES (5, N'deposit')
GO
INSERT [dbo].[brand] ([id], [name]) VALUES (6, N'Intelligent')
GO
INSERT [dbo].[brand] ([id], [name]) VALUES (7, N'Central Movies')
GO
INSERT [dbo].[brand] ([id], [name]) VALUES (8, N'logistical Principal neural')
GO
INSERT [dbo].[brand] ([id], [name]) VALUES (9, N'up')
GO
INSERT [dbo].[brand] ([id], [name]) VALUES (10, N'Refined mindshare')
GO
SET IDENTITY_INSERT [dbo].[brand] OFF
GO
SET IDENTITY_INSERT [dbo].[category] ON 
GO
INSERT [dbo].[category] ([id], [name]) VALUES (1, N'Table')
GO
INSERT [dbo].[category] ([id], [name]) VALUES (2, N'open-source')
GO
INSERT [dbo].[category] ([id], [name]) VALUES (3, N'Integration')
GO
INSERT [dbo].[category] ([id], [name]) VALUES (4, N'Refined metrics e-commerce')
GO
INSERT [dbo].[category] ([id], [name]) VALUES (5, N'Chief')
GO
INSERT [dbo].[category] ([id], [name]) VALUES (6, N'XML Chips Kentucky')
GO
INSERT [dbo].[category] ([id], [name]) VALUES (7, N'Steel transmitting Nevada')
GO
INSERT [dbo].[category] ([id], [name]) VALUES (8, N'harness Georgia Hat')
GO
INSERT [dbo].[category] ([id], [name]) VALUES (9, N'Program mesh')
GO
INSERT [dbo].[category] ([id], [name]) VALUES (10, N'Designer array')
GO
SET IDENTITY_INSERT [dbo].[category] OFF
GO
SET IDENTITY_INSERT [dbo].[image] ON 
GO
INSERT [dbo].[image] ([id], [name], [url], [product_id]) VALUES (1, N'Cambridgeshire International', N'https://marquise.net', NULL)
GO
INSERT [dbo].[image] ([id], [name], [url], [product_id]) VALUES (2, N'Spurs Front-line Idaho', N'https://gillian.info', NULL)
GO
INSERT [dbo].[image] ([id], [name], [url], [product_id]) VALUES (3, N'Health flexibility auxiliary', N'https://nathan.name', NULL)
GO
INSERT [dbo].[image] ([id], [name], [url], [product_id]) VALUES (4, N'fuchsia blockchains Steel', N'https://dayana.org', NULL)
GO
INSERT [dbo].[image] ([id], [name], [url], [product_id]) VALUES (5, N'quantify Clothing', N'https://hazel.info', NULL)
GO
INSERT [dbo].[image] ([id], [name], [url], [product_id]) VALUES (6, N'users utilisation Central', N'https://vella.net', NULL)
GO
INSERT [dbo].[image] ([id], [name], [url], [product_id]) VALUES (7, N'Thailand', N'http://titus.org', NULL)
GO
INSERT [dbo].[image] ([id], [name], [url], [product_id]) VALUES (8, N'Up-sized Quality', N'http://hardy.net', NULL)
GO
INSERT [dbo].[image] ([id], [name], [url], [product_id]) VALUES (9, N'Motorway program open', N'http://brooks.info', NULL)
GO
INSERT [dbo].[image] ([id], [name], [url], [product_id]) VALUES (10, N'Ireland Cambridgeshire', N'https://margaret.biz', NULL)
GO
SET IDENTITY_INSERT [dbo].[image] OFF
GO
SET IDENTITY_INSERT [dbo].[notification] ON 
GO
INSERT [dbo].[notification] ([id], [created_by], [created_date], [description], [is_read], [modified_by], [modified_date], [sort_descripsion], [status], [title]) VALUES (1, N'viral Executive invoice', CAST(N'2023-02-09T20:12:08.0000000' AS DateTime2), N'systematic Switzerland Granite', 1, N'invoice payment', CAST(N'2023-02-10T00:34:33.0000000' AS DateTime2), N'1080p', N'REJECTED', N'Rustic Throughway')
GO
INSERT [dbo].[notification] ([id], [created_by], [created_date], [description], [is_read], [modified_by], [modified_date], [sort_descripsion], [status], [title]) VALUES (2, N'generation Fresh payment', CAST(N'2023-02-09T18:57:01.0000000' AS DateTime2), N'Table compressing Gloves', 1, N'FTP reintermediate', CAST(N'2023-02-10T04:39:02.0000000' AS DateTime2), N'Specialist', N'APPROVED', N'web-readiness calculate partnerships')
GO
INSERT [dbo].[notification] ([id], [created_by], [created_date], [description], [is_read], [modified_by], [modified_date], [sort_descripsion], [status], [title]) VALUES (3, N'Associate', CAST(N'2023-02-10T00:49:58.0000000' AS DateTime2), N'generate auxiliary Sports', 0, N'navigate Engineer', CAST(N'2023-02-09T11:33:03.0000000' AS DateTime2), N'multi-byte Specialist', N'PENDING', N'Berkshire AGP')
GO
INSERT [dbo].[notification] ([id], [created_by], [created_date], [description], [is_read], [modified_by], [modified_date], [sort_descripsion], [status], [title]) VALUES (4, N'Account Borders Barbados', CAST(N'2023-02-09T07:12:12.0000000' AS DateTime2), N'infrastructures', 1, N'cross-platform SMTP bandwidth-monitored', CAST(N'2023-02-09T12:00:07.0000000' AS DateTime2), N'Myanmar architectures bandwidth', N'APPROVED', N'Skyway bluetooth')
GO
INSERT [dbo].[notification] ([id], [created_by], [created_date], [description], [is_read], [modified_by], [modified_date], [sort_descripsion], [status], [title]) VALUES (5, N'multi-byte', CAST(N'2023-02-09T08:46:08.0000000' AS DateTime2), N'Outdoors payment', 1, N'Incredible Ball', CAST(N'2023-02-10T04:16:44.0000000' AS DateTime2), N'utilize ivory GB', N'APPROVED', N'HDD Arizona Car')
GO
INSERT [dbo].[notification] ([id], [created_by], [created_date], [description], [is_read], [modified_by], [modified_date], [sort_descripsion], [status], [title]) VALUES (6, N'Afghani human-resource', CAST(N'2023-02-09T23:36:30.0000000' AS DateTime2), N'Tennessee Franc', 0, N'e-commerce Markets', CAST(N'2023-02-09T14:05:39.0000000' AS DateTime2), N'ivory Unbranded', N'REJECTED', N'Tasty panel Handcrafted')
GO
INSERT [dbo].[notification] ([id], [created_by], [created_date], [description], [is_read], [modified_by], [modified_date], [sort_descripsion], [status], [title]) VALUES (7, N'Object-based alarm Forward', CAST(N'2023-02-09T20:27:35.0000000' AS DateTime2), N'driver connecting', 0, N'evolve programming Lira', CAST(N'2023-02-09T10:58:52.0000000' AS DateTime2), N'Buckinghamshire withdrawal', N'APPROVED', N'white Ports Ergonomic')
GO
INSERT [dbo].[notification] ([id], [created_by], [created_date], [description], [is_read], [modified_by], [modified_date], [sort_descripsion], [status], [title]) VALUES (8, N'Cambridgeshire scale', CAST(N'2023-02-09T08:02:22.0000000' AS DateTime2), N'Berkshire', 0, N'Cotton Movies Belize', CAST(N'2023-02-10T03:41:04.0000000' AS DateTime2), N'utilisation matrix', N'APPROVED', N'azure IB Cotton')
GO
INSERT [dbo].[notification] ([id], [created_by], [created_date], [description], [is_read], [modified_by], [modified_date], [sort_descripsion], [status], [title]) VALUES (9, N'Chips United', CAST(N'2023-02-09T12:56:42.0000000' AS DateTime2), N'Diverse', 0, N'Louisiana', CAST(N'2023-02-09T21:13:08.0000000' AS DateTime2), N'world-class quantifying Greenland', N'PENDING', N'Lights Ergonomic Profit-focused')
GO
INSERT [dbo].[notification] ([id], [created_by], [created_date], [description], [is_read], [modified_by], [modified_date], [sort_descripsion], [status], [title]) VALUES (10, N'French B2C Savings', CAST(N'2023-02-10T00:33:03.0000000' AS DateTime2), N'Accounts Plastic Applications', 1, N'driver Account end-to-end', CAST(N'2023-02-09T11:03:14.0000000' AS DateTime2), N'Kids Ball International', N'APPROVED', N'AI Program orange')
GO
SET IDENTITY_INSERT [dbo].[notification] OFF
GO
SET IDENTITY_INSERT [dbo].[order_details] ON 
GO
INSERT [dbo].[order_details] ([id], [name], [price], [quantity], [order_id], [product_id]) VALUES (1, N'circuit multi-tasking', 96554, 66221, 1, 6)
GO
INSERT [dbo].[order_details] ([id], [name], [price], [quantity], [order_id], [product_id]) VALUES (2, N'Vermont', 52580, 5747, 1, 1)
GO
INSERT [dbo].[order_details] ([id], [name], [price], [quantity], [order_id], [product_id]) VALUES (3, N'ivory Industrial dynamic', 10420, 43063, 1, 3)
GO
INSERT [dbo].[order_details] ([id], [name], [price], [quantity], [order_id], [product_id]) VALUES (4, N'hack Delaware', 94142, 49783, 1, 5)
GO
INSERT [dbo].[order_details] ([id], [name], [price], [quantity], [order_id], [product_id]) VALUES (5, N'New Profit-focused Texas', 80964, 86338, 1, 4)
GO
INSERT [dbo].[order_details] ([id], [name], [price], [quantity], [order_id], [product_id]) VALUES (6, N'withdrawal Account', 15140, 39823, 1, 7)
GO
INSERT [dbo].[order_details] ([id], [name], [price], [quantity], [order_id], [product_id]) VALUES (7, N'Executive Chicken Outdoors', 19282, 53231, NULL, NULL)
GO
INSERT [dbo].[order_details] ([id], [name], [price], [quantity], [order_id], [product_id]) VALUES (8, N'gold encoding parsing', 40693, 13686, NULL, NULL)
GO
INSERT [dbo].[order_details] ([id], [name], [price], [quantity], [order_id], [product_id]) VALUES (9, N'Rand Analyst ivory', 79642, 10145, NULL, NULL)
GO
INSERT [dbo].[order_details] ([id], [name], [price], [quantity], [order_id], [product_id]) VALUES (10, N'Lead high-level', 70790, 86086, NULL, NULL)
GO
SET IDENTITY_INSERT [dbo].[order_details] OFF
GO
SET IDENTITY_INSERT [dbo].[orders] ON 
GO
INSERT [dbo].[orders] ([id], [created_by], [created_date], [modified_by], [modified_date], [order_brorrow_date], [order_return_date], [status], [total_price], [total_quantity], [user_id], [voucher_id]) VALUES (1, N'Rupiah payment Marks', CAST(N'2023-02-09T06:58:24.0000000' AS DateTime2), N'upward-trending Borders', CAST(N'2023-02-09T23:44:19.0000000' AS DateTime2), CAST(N'2023-02-09T10:56:35.0000000' AS DateTime2), CAST(N'2023-02-09T10:55:23.0000000' AS DateTime2), N'DELIVERED', 32217, 48469, 2, 1)
GO
INSERT [dbo].[orders] ([id], [created_by], [created_date], [modified_by], [modified_date], [order_brorrow_date], [order_return_date], [status], [total_price], [total_quantity], [user_id], [voucher_id]) VALUES (2, N'circuit Account Outdoors', CAST(N'2023-02-09T13:56:44.0000000' AS DateTime2), N'Tools Account', CAST(N'2023-02-09T14:26:34.0000000' AS DateTime2), CAST(N'2023-02-09T10:01:04.0000000' AS DateTime2), CAST(N'2023-02-09T16:43:45.0000000' AS DateTime2), N'CANCELLED', 48734, 39702, 2, 1)
GO
INSERT [dbo].[orders] ([id], [created_by], [created_date], [modified_by], [modified_date], [order_brorrow_date], [order_return_date], [status], [total_price], [total_quantity], [user_id], [voucher_id]) VALUES (3, N'deliverables', CAST(N'2023-02-09T17:11:44.0000000' AS DateTime2), N'Virginia Finland', CAST(N'2023-02-10T01:10:33.0000000' AS DateTime2), CAST(N'2023-02-09T17:15:48.0000000' AS DateTime2), CAST(N'2023-02-09T15:26:06.0000000' AS DateTime2), N'CANCELLED', 61533, 70320, 2, 1)
GO
INSERT [dbo].[orders] ([id], [created_by], [created_date], [modified_by], [modified_date], [order_brorrow_date], [order_return_date], [status], [total_price], [total_quantity], [user_id], [voucher_id]) VALUES (4, N'olive Response Auto', CAST(N'2023-02-09T19:27:16.0000000' AS DateTime2), N'initiative tan', CAST(N'2023-02-09T15:04:10.0000000' AS DateTime2), CAST(N'2023-02-09T09:23:30.0000000' AS DateTime2), CAST(N'2023-02-09T07:19:05.0000000' AS DateTime2), N'PENDING', 3657, 7374, 6, NULL)
GO
INSERT [dbo].[orders] ([id], [created_by], [created_date], [modified_by], [modified_date], [order_brorrow_date], [order_return_date], [status], [total_price], [total_quantity], [user_id], [voucher_id]) VALUES (5, N'Practical impactful protocol', CAST(N'2023-02-10T03:32:25.0000000' AS DateTime2), N'Health Dam Shoes', CAST(N'2023-02-09T08:53:46.0000000' AS DateTime2), CAST(N'2023-02-09T21:52:05.0000000' AS DateTime2), CAST(N'2023-02-09T07:00:34.0000000' AS DateTime2), N'DELIVERING', 9978, 45390, 6, 3)
GO
INSERT [dbo].[orders] ([id], [created_by], [created_date], [modified_by], [modified_date], [order_brorrow_date], [order_return_date], [status], [total_price], [total_quantity], [user_id], [voucher_id]) VALUES (6, N'Pizza', CAST(N'2023-02-09T15:36:01.0000000' AS DateTime2), N'generate Auto', CAST(N'2023-02-09T18:42:20.0000000' AS DateTime2), CAST(N'2023-02-09T09:49:05.0000000' AS DateTime2), CAST(N'2023-02-09T12:22:44.0000000' AS DateTime2), N'CANCELLED', 73833, 95921, 6, NULL)
GO
INSERT [dbo].[orders] ([id], [created_by], [created_date], [modified_by], [modified_date], [order_brorrow_date], [order_return_date], [status], [total_price], [total_quantity], [user_id], [voucher_id]) VALUES (7, N'Internal state', CAST(N'2023-02-09T12:35:00.0000000' AS DateTime2), N'Table', CAST(N'2023-02-10T02:20:29.0000000' AS DateTime2), CAST(N'2023-02-09T17:26:47.0000000' AS DateTime2), CAST(N'2023-02-09T22:10:49.0000000' AS DateTime2), N'DELIVERED', 14750, 34916, NULL, NULL)
GO
INSERT [dbo].[orders] ([id], [created_by], [created_date], [modified_by], [modified_date], [order_brorrow_date], [order_return_date], [status], [total_price], [total_quantity], [user_id], [voucher_id]) VALUES (8, N'Ball Executive', CAST(N'2023-02-09T07:30:14.0000000' AS DateTime2), N'GB Ergonomic', CAST(N'2023-02-10T01:26:39.0000000' AS DateTime2), CAST(N'2023-02-09T11:08:45.0000000' AS DateTime2), CAST(N'2023-02-09T05:28:24.0000000' AS DateTime2), N'DELIVERING', 28712, 99117, NULL, NULL)
GO
INSERT [dbo].[orders] ([id], [created_by], [created_date], [modified_by], [modified_date], [order_brorrow_date], [order_return_date], [status], [total_price], [total_quantity], [user_id], [voucher_id]) VALUES (9, N'product red', CAST(N'2023-02-09T05:02:04.0000000' AS DateTime2), N'Account', CAST(N'2023-02-09T16:13:12.0000000' AS DateTime2), CAST(N'2023-02-10T01:54:43.0000000' AS DateTime2), CAST(N'2023-02-10T00:13:17.0000000' AS DateTime2), N'DELIVERING', 18225, 28162, NULL, NULL)
GO
INSERT [dbo].[orders] ([id], [created_by], [created_date], [modified_by], [modified_date], [order_brorrow_date], [order_return_date], [status], [total_price], [total_quantity], [user_id], [voucher_id]) VALUES (10, N'orchid Dynamic SSL', CAST(N'2023-02-09T04:53:59.0000000' AS DateTime2), N'Cambridgeshire Denar Belarus', CAST(N'2023-02-10T03:09:01.0000000' AS DateTime2), CAST(N'2023-02-09T10:09:15.0000000' AS DateTime2), CAST(N'2023-02-10T03:58:01.0000000' AS DateTime2), N'CANCELLED', 19737, 14956, NULL, NULL)
GO
SET IDENTITY_INSERT [dbo].[orders] OFF
GO
SET IDENTITY_INSERT [dbo].[product] ON 
GO
INSERT [dbo].[product] ([id], [created_by], [created_date], [description], [modified_by], [modified_date], [name], [price], [quantity], [sort_description], [status], [brand_id], [category_id]) VALUES (1, N'magenta THX', CAST(N'2023-02-09T06:29:55.0000000' AS DateTime2), N'calculating cross-platform Principal', N'deposit Centers Metal', CAST(N'2023-02-10T03:07:52.0000000' AS DateTime2), N'Steel', 31485, 89830, N'wireless Computer', N'REJECTED', 1, 1)
GO
INSERT [dbo].[product] ([id], [created_by], [created_date], [description], [modified_by], [modified_date], [name], [price], [quantity], [sort_description], [status], [brand_id], [category_id]) VALUES (2, N'digital', CAST(N'2023-02-09T23:07:42.0000000' AS DateTime2), N'compressing withdrawal green', N'Identity throughput Village', CAST(N'2023-02-09T15:07:35.0000000' AS DateTime2), N'models partnerships Infrastructure', 91058, 4184, N'Bedfordshire Lao neutral', N'PENDING', 1, 2)
GO
INSERT [dbo].[product] ([id], [created_by], [created_date], [description], [modified_by], [modified_date], [name], [price], [quantity], [sort_description], [status], [brand_id], [category_id]) VALUES (3, N'De-engineered', CAST(N'2023-02-09T05:27:18.0000000' AS DateTime2), N'Implementation Awesome', N'Center black pixel', CAST(N'2023-02-09T17:23:09.0000000' AS DateTime2), N'distributed indigo JSON', 55869, 86573, N'homogeneous Crossroad', N'PENDING', 2, 3)
GO
INSERT [dbo].[product] ([id], [created_by], [created_date], [description], [modified_by], [modified_date], [name], [price], [quantity], [sort_description], [status], [brand_id], [category_id]) VALUES (4, N'Sleek metrics', CAST(N'2023-02-09T19:58:08.0000000' AS DateTime2), N'cross-platform', N'Data', CAST(N'2023-02-09T05:46:03.0000000' AS DateTime2), N'Account', 68319, 17130, N'South Forward open-source', N'PENDING', 3, 4)
GO
INSERT [dbo].[product] ([id], [created_by], [created_date], [description], [modified_by], [modified_date], [name], [price], [quantity], [sort_description], [status], [brand_id], [category_id]) VALUES (5, N'back-end National Operations', CAST(N'2023-02-09T16:17:40.0000000' AS DateTime2), N'Ergonomic Ergonomic', N'Chicken', CAST(N'2023-02-10T03:03:27.0000000' AS DateTime2), N'sticky Harbors Automotive', 41021, 27583, N'Handcrafted', N'APPROVED', 3, 5)
GO
INSERT [dbo].[product] ([id], [created_by], [created_date], [description], [modified_by], [modified_date], [name], [price], [quantity], [sort_description], [status], [brand_id], [category_id]) VALUES (6, N'wireless Practical Solutions', CAST(N'2023-02-09T22:25:31.0000000' AS DateTime2), N'Account', N'and multi-byte Frozen', CAST(N'2023-02-09T15:45:15.0000000' AS DateTime2), N'Usability multi-byte', 34414, 67786, N'Global Industrial Concrete', N'PENDING', 4, 5)
GO
INSERT [dbo].[product] ([id], [created_by], [created_date], [description], [modified_by], [modified_date], [name], [price], [quantity], [sort_description], [status], [brand_id], [category_id]) VALUES (7, N'Technician', CAST(N'2023-02-09T11:34:55.0000000' AS DateTime2), N'e-services Security Colombian', N'Borders Pula', CAST(N'2023-02-09T10:17:06.0000000' AS DateTime2), N'Product', 64290, 46693, N'project bypassing', N'PENDING', 5, 6)
GO
INSERT [dbo].[product] ([id], [created_by], [created_date], [description], [modified_by], [modified_date], [name], [price], [quantity], [sort_description], [status], [brand_id], [category_id]) VALUES (8, N'generating', CAST(N'2023-02-09T22:11:55.0000000' AS DateTime2), N'focus Phased infomediaries', N'benchmark killer', CAST(N'2023-02-09T10:10:08.0000000' AS DateTime2), N'capability synthesizing pink', 16465, 45868, N'sky', N'REJECTED', 6, 1)
GO
INSERT [dbo].[product] ([id], [created_by], [created_date], [description], [modified_by], [modified_date], [name], [price], [quantity], [sort_description], [status], [brand_id], [category_id]) VALUES (9, N'Producer', CAST(N'2023-02-09T21:01:20.0000000' AS DateTime2), N'Account Account', N'Frozen Salad Concrete', CAST(N'2023-02-09T16:29:48.0000000' AS DateTime2), N'Chicken', 19638, 18371, N'Functionality Small adapter', N'PENDING', 6, NULL)
GO
INSERT [dbo].[product] ([id], [created_by], [created_date], [description], [modified_by], [modified_date], [name], [price], [quantity], [sort_description], [status], [brand_id], [category_id]) VALUES (10, N'Producer Triple-buffered', CAST(N'2023-02-09T16:09:59.0000000' AS DateTime2), N'time-frame', N'Orchestrator Directives payment', CAST(N'2023-02-09T08:56:44.0000000' AS DateTime2), N'Agent Technician', 20806, 33555, N'redefine', N'APPROVED', NULL, NULL)
GO
SET IDENTITY_INSERT [dbo].[product] OFF
GO
INSERT [dbo].[user__notification] ([user_id], [notification_id]) VALUES (2, 1)
GO
INSERT [dbo].[user__notification] ([user_id], [notification_id]) VALUES (2, 2)
GO
INSERT [dbo].[user__notification] ([user_id], [notification_id]) VALUES (2, 4)
GO
INSERT [dbo].[user__notification] ([user_id], [notification_id]) VALUES (6, 1)
GO
SET IDENTITY_INSERT [dbo].[users] ON 
GO
INSERT [dbo].[users] ([id], [address], [avatar], [birthday], [created_by], [created_date], [email], [first_name], [image_url], [last_name], [modified_by], [modified_date], [password_hash], [phone], [username]) VALUES (2, N'1', NULL, NULL, NULL, NULL, N'2', NULL, NULL, NULL, NULL, NULL, N'3', NULL, N'1')
GO
INSERT [dbo].[users] ([id], [address], [avatar], [birthday], [created_by], [created_date], [email], [first_name], [image_url], [last_name], [modified_by], [modified_date], [password_hash], [phone], [username]) VALUES (6, NULL, NULL, NULL, NULL, NULL, N'1', N'2', NULL, NULL, NULL, NULL, N'4', NULL, N'3')
GO
SET IDENTITY_INSERT [dbo].[users] OFF
GO
SET IDENTITY_INSERT [dbo].[voucher] ON 
GO
INSERT [dbo].[voucher] ([id], [created_by], [created_date], [discount], [end_date], [modified_by], [modified_date], [name], [start_date], [status]) VALUES (1, N'deposit Alaska Connecticut', CAST(N'2023-02-09T10:38:51.0000000' AS DateTime2), 58691, CAST(N'2023-02-09T09:15:19.0000000' AS DateTime2), N'convergence Bedfordshire Practical', CAST(N'2023-02-09T17:01:33.0000000' AS DateTime2), N'indigo Metrics', CAST(N'2023-02-09T23:49:00.0000000' AS DateTime2), N'FREE_SHIPPING')
GO
INSERT [dbo].[voucher] ([id], [created_by], [created_date], [discount], [end_date], [modified_by], [modified_date], [name], [start_date], [status]) VALUES (2, N'Georgia Producer', CAST(N'2023-02-09T10:09:38.0000000' AS DateTime2), 30762, CAST(N'2023-02-09T18:32:06.0000000' AS DateTime2), N'withdrawal invoice Forward', CAST(N'2023-02-10T00:02:27.0000000' AS DateTime2), N'whiteboard', CAST(N'2023-02-09T08:36:58.0000000' AS DateTime2), N'FREE_SHIPPING')
GO
INSERT [dbo].[voucher] ([id], [created_by], [created_date], [discount], [end_date], [modified_by], [modified_date], [name], [start_date], [status]) VALUES (3, N'Unbranded monetize', CAST(N'2023-02-09T14:46:10.0000000' AS DateTime2), 93089, CAST(N'2023-02-09T14:11:45.0000000' AS DateTime2), N'FTP', CAST(N'2023-02-09T21:14:41.0000000' AS DateTime2), N'Program withdrawal virtual', CAST(N'2023-02-09T15:49:52.0000000' AS DateTime2), N'DISCOUNT')
GO
INSERT [dbo].[voucher] ([id], [created_by], [created_date], [discount], [end_date], [modified_by], [modified_date], [name], [start_date], [status]) VALUES (4, N'Wooden Buckinghamshire auxiliary', CAST(N'2023-02-09T17:21:13.0000000' AS DateTime2), 53008, CAST(N'2023-02-09T06:45:45.0000000' AS DateTime2), N'Samoa olive Mobility', CAST(N'2023-02-09T12:38:00.0000000' AS DateTime2), N'Dynamic Guatemala orchestrate', CAST(N'2023-02-09T16:49:56.0000000' AS DateTime2), N'FREE_SHIPPING')
GO
INSERT [dbo].[voucher] ([id], [created_by], [created_date], [discount], [end_date], [modified_by], [modified_date], [name], [start_date], [status]) VALUES (5, N'Vision-oriented payment Multi-lateral', CAST(N'2023-02-09T20:51:41.0000000' AS DateTime2), 56827, CAST(N'2023-02-09T12:36:51.0000000' AS DateTime2), N'bypass Islands Estonia', CAST(N'2023-02-10T00:46:51.0000000' AS DateTime2), N'Fantastic', CAST(N'2023-02-10T02:40:49.0000000' AS DateTime2), N'FREE_SHIPPING')
GO
INSERT [dbo].[voucher] ([id], [created_by], [created_date], [discount], [end_date], [modified_by], [modified_date], [name], [start_date], [status]) VALUES (6, N'International', CAST(N'2023-02-10T02:13:51.0000000' AS DateTime2), 91492, CAST(N'2023-02-09T13:28:53.0000000' AS DateTime2), N'budgetary array', CAST(N'2023-02-09T07:09:31.0000000' AS DateTime2), N'Visionary Mouse Chips', CAST(N'2023-02-10T04:38:29.0000000' AS DateTime2), N'FREE_SHIPPING')
GO
INSERT [dbo].[voucher] ([id], [created_by], [created_date], [discount], [end_date], [modified_by], [modified_date], [name], [start_date], [status]) VALUES (7, N'revolutionize Savings Investment', CAST(N'2023-02-09T23:39:56.0000000' AS DateTime2), 70925, CAST(N'2023-02-09T21:00:08.0000000' AS DateTime2), N'Vanuatu', CAST(N'2023-02-09T12:10:30.0000000' AS DateTime2), N'Mississippi primary Compatible', CAST(N'2023-02-09T06:04:11.0000000' AS DateTime2), N'DISCOUNT')
GO
INSERT [dbo].[voucher] ([id], [created_by], [created_date], [discount], [end_date], [modified_by], [modified_date], [name], [start_date], [status]) VALUES (8, N'Shoes deposit Upgradable', CAST(N'2023-02-09T17:38:35.0000000' AS DateTime2), 11987, CAST(N'2023-02-09T11:27:14.0000000' AS DateTime2), N'Multi-lateral Malaysia', CAST(N'2023-02-10T02:49:56.0000000' AS DateTime2), N'reinvent', CAST(N'2023-02-09T04:43:50.0000000' AS DateTime2), N'DISCOUNT')
GO
INSERT [dbo].[voucher] ([id], [created_by], [created_date], [discount], [end_date], [modified_by], [modified_date], [name], [start_date], [status]) VALUES (9, N'dot-com', CAST(N'2023-02-09T20:50:21.0000000' AS DateTime2), 43694, CAST(N'2023-02-09T22:32:05.0000000' AS DateTime2), N'monetize Mexican Money', CAST(N'2023-02-10T00:06:08.0000000' AS DateTime2), N'Incredible deposit', CAST(N'2023-02-10T00:45:14.0000000' AS DateTime2), N'FREE_SHIPPING')
GO
INSERT [dbo].[voucher] ([id], [created_by], [created_date], [discount], [end_date], [modified_by], [modified_date], [name], [start_date], [status]) VALUES (10, N'parsing Borders withdrawal', CAST(N'2023-02-09T08:48:24.0000000' AS DateTime2), 37781, CAST(N'2023-02-09T21:37:07.0000000' AS DateTime2), N'Enterprise-wide', CAST(N'2023-02-09T23:56:15.0000000' AS DateTime2), N'New', CAST(N'2023-02-09T12:47:04.0000000' AS DateTime2), N'FREE_SHIPPING')
GO
SET IDENTITY_INSERT [dbo].[voucher] OFF
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UK_6dotkott2kjsp8vw4d0m25fb7]    Script Date: 11/02/2023 4:04:07 PM ******/
ALTER TABLE [dbo].[users] ADD  CONSTRAINT [UK_6dotkott2kjsp8vw4d0m25fb7] UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UK_r43af9ap4edm43mmtq01oddj6]    Script Date: 11/02/2023 4:04:07 PM ******/
ALTER TABLE [dbo].[users] ADD  CONSTRAINT [UK_r43af9ap4edm43mmtq01oddj6] UNIQUE NONCLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[image]  WITH CHECK ADD  CONSTRAINT [FKgpextbyee3uk9u6o2381m7ft1] FOREIGN KEY([product_id])
REFERENCES [dbo].[product] ([id])
GO
ALTER TABLE [dbo].[image] CHECK CONSTRAINT [FKgpextbyee3uk9u6o2381m7ft1]
GO
ALTER TABLE [dbo].[order_details]  WITH CHECK ADD  CONSTRAINT [FKinivj2k1370kw224lavkm3rqm] FOREIGN KEY([product_id])
REFERENCES [dbo].[product] ([id])
GO
ALTER TABLE [dbo].[order_details] CHECK CONSTRAINT [FKinivj2k1370kw224lavkm3rqm]
GO
ALTER TABLE [dbo].[order_details]  WITH CHECK ADD  CONSTRAINT [FKjyu2qbqt8gnvno9oe9j2s2ldk] FOREIGN KEY([order_id])
REFERENCES [dbo].[orders] ([id])
GO
ALTER TABLE [dbo].[order_details] CHECK CONSTRAINT [FKjyu2qbqt8gnvno9oe9j2s2ldk]
GO
ALTER TABLE [dbo].[orders]  WITH CHECK ADD  CONSTRAINT [FK32ql8ubntj5uh44ph9659tiih] FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([id])
GO
ALTER TABLE [dbo].[orders] CHECK CONSTRAINT [FK32ql8ubntj5uh44ph9659tiih]
GO
ALTER TABLE [dbo].[orders]  WITH CHECK ADD  CONSTRAINT [FKrx5vk9ur428660yp19hw98nr2] FOREIGN KEY([voucher_id])
REFERENCES [dbo].[voucher] ([id])
GO
ALTER TABLE [dbo].[orders] CHECK CONSTRAINT [FKrx5vk9ur428660yp19hw98nr2]
GO
ALTER TABLE [dbo].[product]  WITH CHECK ADD  CONSTRAINT [FK1mtsbur82frn64de7balymq9s] FOREIGN KEY([category_id])
REFERENCES [dbo].[category] ([id])
GO
ALTER TABLE [dbo].[product] CHECK CONSTRAINT [FK1mtsbur82frn64de7balymq9s]
GO
ALTER TABLE [dbo].[product]  WITH CHECK ADD  CONSTRAINT [FKs6cydsualtsrprvlf2bb3lcam] FOREIGN KEY([brand_id])
REFERENCES [dbo].[brand] ([id])
GO
ALTER TABLE [dbo].[product] CHECK CONSTRAINT [FKs6cydsualtsrprvlf2bb3lcam]
GO
ALTER TABLE [dbo].[user__notification]  WITH CHECK ADD  CONSTRAINT [FK1t7y79mcapqte31mr2igvslbr] FOREIGN KEY([notification_id])
REFERENCES [dbo].[notification] ([id])
GO
ALTER TABLE [dbo].[user__notification] CHECK CONSTRAINT [FK1t7y79mcapqte31mr2igvslbr]
GO
ALTER TABLE [dbo].[user__notification]  WITH CHECK ADD  CONSTRAINT [FKsv0uwh2qplq58d0itcfn72nw5] FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([id])
GO
ALTER TABLE [dbo].[user__notification] CHECK CONSTRAINT [FKsv0uwh2qplq58d0itcfn72nw5]
GO
ALTER TABLE [dbo].[user_role]  WITH CHECK ADD  CONSTRAINT [FKa68196081fvovjhkek5m97n3y] FOREIGN KEY([role_id])
REFERENCES [dbo].[role] ([id])
GO
ALTER TABLE [dbo].[user_role] CHECK CONSTRAINT [FKa68196081fvovjhkek5m97n3y]
GO
ALTER TABLE [dbo].[user_role]  WITH CHECK ADD  CONSTRAINT [FKj345gk1bovqvfame88rcx7yyx] FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([id])
GO
ALTER TABLE [dbo].[user_role] CHECK CONSTRAINT [FKj345gk1bovqvfame88rcx7yyx]
GO
USE [master]
GO
ALTER DATABASE [RentalClothes] SET  READ_WRITE 
GO
