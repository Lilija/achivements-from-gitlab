/****** Object:  User test    Script Date: 11/30/2017 22:59:20 ******/

IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'game') AND type in (N'U'))
BEGIN
CREATE TABLE game(
	id varchar(255) NOT NULL,
	display_name varchar(255) NOT NULL,
 CONSTRAINT PK_game_id PRIMARY KEY CLUSTERED (id ASC)
END
GO
INSERT game (id, display_name) VALUES (N'aca4f039-f4af-4151-ad03-5760b306ec70', N'Test game 1')
INSERT game (id, display_name) VALUES (N'e22d896d-921c-42f5-9063-47fcd7c05f19', N'Test game 2')
/****** Object:  Table dbo.achievement    Script Date: 11/30/2017 22:59:20 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'achievement') AND type in (N'U'))
BEGIN
CREATE TABLE achievement(
	id varchar(255) NOT NULL,
	created datetime2(7) NOT NULL,
	description varchar(500) NOT NULL,
	display_name varchar(100) NOT NULL,
	display_order int ,
	icon varchar(255) ,
	updated datetime2(7) NOT NULL,
	game_id varchar(255) NOT NULL,
 CONSTRAINT PK_achievement_id PRIMARY KEY CLUSTERED (id ASC)
END
GO
INSERT achievement (id, created, description, display_name, display_order, icon, updated, game_id) VALUES (N'098b8745-fb39-4854-990a-afb9031036dc', CAST(0x07B0BF9359AC963D0B AS DateTime2), N'Test new achievement desription', N'Test new achievement 1', 1, NULL, CAST(0x07B0BF9359AC963D0B AS DateTime2), N'aca4f039-f4af-4151-ad03-5760b306ec70')
INSERT achievement (id, created, description, display_name, display_order, icon, updated, game_id) VALUES (N'c12b420f-922d-4b67-81c7-1ac638d1e4fc', CAST(0x0790A078B582963D0B AS DateTime2), N'Test desc updated', N'Test updated', 3, NULL, CAST(0x07B0E2F989AC963D0B AS DateTime2), N'aca4f039-f4af-4151-ad03-5760b306ec70')
INSERT achievement (id, created, description, display_name, display_order, icon, updated, game_id) VALUES (N'c5de4cee-b22e-406e-94ae-832e085c31da', CAST(0x07E05DA46C8D963D0B AS DateTime2), N'Test desc updated', N'Test achivement to delete', 1, NULL, CAST(0x07E05DA46C8D963D0B AS DateTime2), N'aca4f039-f4af-4151-ad03-5760b306ec70')

IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'achievement_game_fk') AND parent_object_id = OBJECT_ID(N'achievement'))
ALTER TABLE achievement  WITH CHECK ADD  CONSTRAINT achievement_game_fk FOREIGN KEY(game_id)
REFERENCES game (id)
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'achievement_game_fk') AND parent_object_id = OBJECT_ID(N'achievement'))
ALTER TABLE achievement CHECK CONSTRAINT achievement_game_fk
GO
IF NOT EXISTS (SELECT * FROM sys.indexes WHERE object_id = OBJECT_ID(N'achievement_game_name_unique') )
ALTER TABLE achievement  ADD  CONSTRAINT achievement_game_name_unique UNIQUE (game_id, display_name)
GO
