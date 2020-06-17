package cn.lefer.tomu.controller;

import cn.lefer.tomu.entity.Channel;
import cn.lefer.tomu.entity.Song;
import cn.lefer.tomu.service.ChannelService;
import cn.lefer.tomu.vo.PlayStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/16
 * @Description : 频道API
 */
@RestController
@RequestMapping(value = "/api/v1/channel")
public class ChannelController {

    ChannelService channelService;

    @GetMapping(value = "/{channelID}")
    public Channel getChannel(@PathVariable("channelID") @Validated int channelID) {
        return channelService.getChannel(channelID);
    }

    @GetMapping(value = "/{channelID}/songs")
    public List<Song> getSongs(@PathVariable("channelID") @Validated int channelID){
        return channelService.getSongs(channelID);
    }

    @PostMapping(value = "/{channelID}/song")
    public List<Song> addSong(@PathVariable("channelID") @Validated int channelID, @RequestParam @Validated String songUrl) {
        return new ArrayList<>();
    }

    @PostMapping(value = "/{channelID}/status")
    public boolean changeChannelStatus(@PathVariable("channelID") @Validated int channelID, int songID, int position) {
        return true;
    }

    @GetMapping(value = "/{channelID}/status")
    public Flux<ServerSentEvent<PlayStatus>> getStatus(@PathVariable("channelID") @Validated int channelID) {
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> Tuples.of(seq, ThreadLocalRandom.current().nextInt()))
                .map(data -> ServerSentEvent.<PlayStatus>builder()
                        .event("status")
                        .id(Long.toString(data.getT1()))
                        .data(new PlayStatus())
                        .build());
    }

    @Autowired
    public void setChannelService(ChannelService channelService) {
        this.channelService = channelService;
    }
}
