-- 重构T_SONG的表结构，将频道信息剥离出来，独立映射
-- 1.新增映射表
drop table if exists t_channel_song;
create table t_channel_song
(
    id  bigint IDENTITY primary key,
    channel_id int,
    song_id bigint,
    add_date datetime,
    valid_flag int,
    foreign key (channel_id) references t_channel(channel_id),
    foreign key (song_id) references t_song(song_id)
);
CREATE INDEX idx_t_channel_song_channel_id
    ON t_channel_song (channel_id);
CREATE INDEX idx_t_channel_song_song_id
    ON t_channel_song (song_id);
CREATE INDEX idx_t_channel_song_valid_flag
    ON t_channel_song (valid_flag);

-- 2.更改T_SONG表的表结构，去除channel_id
alter table t_song drop column if exists channel_id;
