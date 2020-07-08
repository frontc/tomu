package cn.lefer.tomu.schedule;

import cn.lefer.tomu.constant.SongStatus;
import cn.lefer.tomu.entity.Song;
import cn.lefer.tomu.mapper.SongMapper;
import cn.lefer.tools.Net.LeferNet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ScheduleCenter {
    private final SongMapper songMapper;
    @Autowired
    public ScheduleCenter(SongMapper songMapper){
        this.songMapper=songMapper;
    }
    //*定时对所有的song的有效性进行验证*//
    public void mp3Check(){
        List<SongStatus> songStatuses = new ArrayList<>();
        songStatuses.add(SongStatus.NORMAL);
        songStatuses.add(SongStatus.OUTDATE);
        List<Song> songs = songMapper.selectAll(songStatuses);
        Map<SongStatus,List<Song>> songStatusSongMap = songs.parallelStream().map(this::updateSongStatus).collect(Collectors.groupingBy(Song::getSongStatus));

    }

    private Song updateSongStatus(Song song){
        try{
            if(LeferNet.isValid(song.getMp3Url())){
                song.setSongStatus(SongStatus.NORMAL);
            }else{
                song.setSongStatus(SongStatus.OUTDATE);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return song;
    }
}
