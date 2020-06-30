package cn.lefer.tomu.controller;

import cn.lefer.tomu.constant.SongSource;
import cn.lefer.tomu.exception.BizErrorCode;
import cn.lefer.tomu.exception.BizRestException;
import cn.lefer.tomu.service.ChannelService;
import cn.lefer.tomu.view.ChannelView;
import cn.lefer.tomu.view.PlayStatusView;
import cn.lefer.tomu.view.SongView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.netty.http.server.HttpServerRequest;
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
@RequestMapping(value = "/api/v1/channel")
public class ChannelController {

    ChannelService channelService;

    //创建频道
    @PostMapping(value ="")
    public ChannelView createChannel(){
        return channelService.createChannel();
    }

    //获取频道信息
    @GetMapping(value = "/{channelID}")
    public ChannelView getChannel(@PathVariable("channelID") @Validated int channelID) {
        if (channelID == -1) throw new BizRestException(BizErrorCode.CHANNEL_IS_FULL);
        ChannelView channelView = channelService.getChannel(channelID);
        if(channelView==null) throw new BizRestException(BizErrorCode.CHANNEL_NOT_EXISTS);
        return channelView;
    }

    //获取频道下的歌单
    //todo:分页
    @GetMapping(value = "/{channelID}/songs")
    public List<SongView> getSongs(@PathVariable("channelID") @Validated int channelID) {
        return channelService.getSongs(channelID);
    }

    @PostMapping(value = "/{channelID}/song")
    public SongView addSong(@PathVariable("channelID") @Validated int channelID,
                                  @RequestParam @Validated SongSource songSource,
                                  @RequestParam @Validated String songUrl,
                                  @RequestParam @Validated String mp3Url,
                                  @RequestParam @Validated String coverUrl,
                                  @RequestParam @Validated String lrcUrl,
                                  @RequestParam @Validated String artistName,
                                  @RequestParam @Validated String songName,
                                  @RequestParam @Validated int songDuration) {
        return channelService.addSong(channelID,songName,artistName,coverUrl,lrcUrl,mp3Url,songDuration,songSource,songUrl);
    }

    //TODO:接收频道状态
    /*
    * 状态变化：1. 用户切换歌曲
    */
    @PostMapping(value = "/{channelID}/status")
    public boolean changeChannelStatus(@PathVariable("channelID") @Validated int channelID,
                                       int songID,
                                       int position,
                                       ServerWebExchange exchange) {
        exchange.getSession().doOnNext(webSession -> System.out.println(webSession.getId())).subscribe();
        return true;
    }

    @GetMapping(value = "/session")
    public String testSession(ServerWebExchange exchange){
        exchange.getSession().doOnNext(webSession -> System.out.println(webSession.getId())).subscribe();
        return "OK";
    }

    //TODO:推送频道状态
    @GetMapping(value = "/{channelID}/status")
    public Flux<ServerSentEvent<PlayStatusView>> getStatus(@PathVariable("channelID") @Validated int channelID) {
        return Flux.interval(Duration.ofSeconds(1))
                .filter(l -> channelService.isChannelStatusChanged())
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
