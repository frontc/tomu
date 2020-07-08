package cn.lefer.tomu.schedule;

import cn.lefer.tomu.constant.SongStatus;
import cn.lefer.tomu.entity.Song;
import cn.lefer.tomu.mapper.SongMapper;
import cn.lefer.tools.Date.LeferDate;
import cn.lefer.tools.Net.LeferNet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ScheduleCenter {
    private final SongMapper songMapper;
    private final Log log = LogFactory.getLog(this.getClass());
    @Autowired
    public ScheduleCenter(SongMapper songMapper){
        this.songMapper=songMapper;
    }
    //*定时对所有的song的有效性进行验证*//
    @Scheduled(cron = "0 0 3 * * ?")
    public void mp3Check(){
        log.info("开始刷新歌曲状态...");
        List<SongStatus> songStatuses = new ArrayList<>();
        songStatuses.add(SongStatus.NORMAL);
        songStatuses.add(SongStatus.OUTDATE);
        List<Song> songs = songMapper.selectAll(songStatuses);
        Map<SongStatus,List<Song>> songStatusSongMap = songs.parallelStream().map(this::updateSongStatus).collect(Collectors.groupingBy(Song::getSongStatus));
        for(SongStatus songStatus :songStatusSongMap.keySet()){
            List<Integer> songIDs= songStatusSongMap.get(songStatus).stream().map(Song::getSongID).collect(Collectors.toList());
            int rows = songMapper.batchUpdateSongStatus(songStatus,songIDs);
            log.info("歌曲状态刷新："+songStatus+"-"+rows+"行.");
        }
        log.info("刷新歌曲状态结束.");
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
