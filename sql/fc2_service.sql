-- 创建表: video_info
CREATE TABLE video_info
(
    video_id     INT UNSIGNED AUTO_INCREMENT COMMENT 'FC2 ID' PRIMARY KEY,
    title        VARCHAR(255) NOT NULL COMMENT '视频标题',
    release_date DATE         NOT NULL COMMENT '发布日期'
);

-- 创建表: covers
CREATE TABLE covers
(
    id            INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    video_info_id INT UNSIGNED NOT NULL COMMENT '关联基础信息表的FC2 ID',
    cover_url     VARCHAR(255) NOT NULL COMMENT '封面图片URL',
    -- 外键关联到video_info表的video_id字段
    FOREIGN KEY (video_info_id) REFERENCES video_info (video_id)
);

-- 对video_info_id字段创建索引
CREATE INDEX covers_video_info_id_index ON covers (video_info_id);

-- 创建表: magnet_links
CREATE TABLE magnet_links
(
    id                   INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    link                 VARCHAR(60) NOT NULL COMMENT '磁力链接',
    file_size            VARCHAR(50) NOT NULL COMMENT '文件大小',
    is_submitter_trusted TINYINT(1)  NOT NULL COMMENT '提交者是否可信'
);

-- 对id字段创建索引
CREATE INDEX magnet_links_id_index ON magnet_links (id);

-- 创建表: preview_pictures
CREATE TABLE preview_pictures
(
    id          INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    picture_url VARCHAR(255) NOT NULL COMMENT '预览图片URL'
);

-- 创建表: sellers
CREATE TABLE sellers
(
    id     INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    seller VARCHAR(50) NOT NULL COMMENT '销售者'
);

-- 创建表: tags
CREATE TABLE tags
(
    id  INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    tag VARCHAR(50) NOT NULL COMMENT '标签'
);

-- 创建表: video_info_magnet_links_mapping
CREATE TABLE video_info_magnet_links_mapping
(
    video_info_id  INT UNSIGNED NOT NULL COMMENT '关联基础信息表的FC2 ID',
    magnet_link_id INT UNSIGNED NOT NULL COMMENT '磁力链接ID',
    PRIMARY KEY (video_info_id, magnet_link_id),
    FOREIGN KEY (video_info_id) REFERENCES video_info (video_id), -- 外键关联到video_info表的video_id字段
    FOREIGN KEY (magnet_link_id) REFERENCES magnet_links (id)     -- 外键关联到magnet_links表的id字段
);

CREATE INDEX video_info_magnet_links_mapping_video_info_id_index ON video_info_magnet_links_mapping (video_id);
-- 对video_info_id字段创建索引

-- 创建表: video_info_preview_pictures_mapping
CREATE TABLE video_info_preview_pictures_mapping
(
    video_info_id INT UNSIGNED NOT NULL COMMENT '关联基础信息表的FC2 ID',
    picture_id    INT UNSIGNED NOT NULL COMMENT '预览图片ID',
    PRIMARY KEY (video_info_id, picture_id),
    FOREIGN KEY (video_info_id) REFERENCES video_info (video_id), -- 外键关联到video_info表的video_id字段
    FOREIGN KEY (picture_id) REFERENCES preview_pictures (id)     -- 外键关联到preview_pictures表的id字段
);

CREATE INDEX video_info_preview_pictures_mapping_video_info_id_index ON video_info_preview_pictures_mapping (video_id);
-- 对video_info_id字段创建索引

-- 创建表: video_info_sellers_mapping
CREATE TABLE video_info_sellers_mapping
(
    video_id  INT UNSIGNED NOT NULL COMMENT '关联基础信息表的FC2 ID',
    seller_id INT UNSIGNED NOT NULL COMMENT '销售者ID',
    PRIMARY KEY (video_id, seller_id),
    FOREIGN KEY (video_id) REFERENCES video_info (video_id), -- 外键关联到video_info表的video_id字段
    FOREIGN KEY (seller_id) REFERENCES sellers (id)          -- 外键关联到sellers表的id字段
);

CREATE INDEX video_info_sellers_mapping_video_id_index ON video_info_sellers_mapping (video_id);
-- 对video_id字段创建索引

-- 创建表: video_info_tags_mapping
CREATE TABLE video_info_tags_mapping
(
    video_id INT UNSIGNED NOT NULL COMMENT '关联基础信息表的FC2 ID',
    tag_id   INT UNSIGNED NOT NULL COMMENT '标签ID',
    PRIMARY KEY (video_id, tag_id),
    FOREIGN KEY (video_id) REFERENCES video_info (video_id), -- 外键关联到video_info表的video_id字段
    FOREIGN KEY (tag_id) REFERENCES tags (id)                -- 外键关联到tags表的id字段
);

CREATE INDEX video_info_tags_mapping_video_id_index ON video_info_tags_mapping (video_id); -- 对video_id字段创建索引
