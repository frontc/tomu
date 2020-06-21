package cn.lefer.tomu.controller;

import cn.lefer.tomu.constant.SongSource;
import cn.lefer.tomu.service.ChannelService;
import cn.lefer.tomu.view.ChannelView;
import cn.lefer.tomu.view.PlayStatusView;
import cn.lefer.tomu.view.SongView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/16
 * @Description : 频道API
 */
@RestController
@CrossOrigin(allowCredentials="true", allowedHeaders="*", methods={RequestMethod.GET,
        RequestMethod.POST, RequestMethod.DELETE, RequestMethod.OPTIONS,
        RequestMethod.HEAD, RequestMethod.PUT, RequestMethod.PATCH}, origins="*")
@RequestMapping(value = "/api/v1/channel")
public class ChannelController {

    ChannelService channelService;

    //获取频道信息
    @GetMapping(value = "/{channelID}")
    public ChannelView getChannel(@PathVariable("channelID") @Validated int channelID) {
        return channelService.getChannel(channelID);
    }

    //获取频道下的歌单
    @GetMapping(value = "/{channelID}/songs")
    public List<SongView> getSongs(@PathVariable("channelID") @Validated int channelID){
        return channelService.getSongs(channelID);
    }

    //添加歌曲
    @PostMapping(value = "/{channelID}/song")
    public List<SongView> addSong(@PathVariable("channelID") @Validated int channelID,
                              @RequestParam @Validated SongSource songSource,
                              @RequestParam @Validated String songUrl,
                              @RequestParam @Validated String songName,
                              @RequestParam @Validated int songDuration) {
        return channelService.getSongs(channelID);
    }

    //接收频道状态
    @PostMapping(value = "/{channelID}/status")
    public boolean changeChannelStatus(@PathVariable("channelID") @Validated int channelID, int songID, int position) {
        return true;
    }

    //推送频道状态
    @GetMapping(value = "/{channelID}/status")
    public Flux<ServerSentEvent<PlayStatusView>> getStatus(@PathVariable("channelID") @Validated int channelID) {
        return Flux.interval(Duration.ofSeconds(1))
                .filter(l->channelService.isChannelStatusChanged())
                .map(seq -> Tuples.of(seq, ThreadLocalRandom.current().nextInt()))
                .map(data -> (ServerSentEvent.<PlayStatusView>builder()
                        .event("status")
                        .id(Long.toString(data.getT1()))
                        .data(new PlayStatusView())
                        .build()))
                ;
    }

    @Autowired
    public void setChannelService(ChannelService channelService) {
        this.channelService = channelService;
    }
}
