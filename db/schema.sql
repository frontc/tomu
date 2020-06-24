-- 1.t_channel
drop table t_channel;
create table t_channel
(
    channel_id int IDENTITY primary key,
    channel_name varchar(50),
    channel_owner_id int,
    channel_key varchar(50),
    channel_crt_date datetime
);
-- 2.t_song
drop table t_song;
create table t_song
(
    song_id bigint IDENTITY primary key ,
    song_name varchar(200),
    song_duration int,
    artist_name varchar(50),
    song_source varchar(20),
    song_url varchar(255),
    mp3_url varchar(255),
    cover_url varchar(255),
    lrc_url varchar(255),
    song_status varchar(20),
    channel_id int not null ,
    song_add_date datetime
);
CREATE INDEX idx_t_song_channel_id
ON t_song (channel_id);
-- 3.t_play_history
drop table t_play_history;
create table t_play_history
(
    play_history_id bigint IDENTITY primary key,
    channel_id int,
    song_id bigint,
    last_position int,
    play_date datetime
);

CREATE INDEX idx_t_play_history_channel_id
ON t_play_history (channel_id);

CREATE INDEX idx_t_play_history_play_date
ON t_play_history (play_date);