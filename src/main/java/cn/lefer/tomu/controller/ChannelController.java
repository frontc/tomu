package cn.lefer.tomu.controller;

import cn.lefer.tomu.dto.ChannelStatusDTO;
import cn.lefer.tomu.dto.SongDTO;
import cn.lefer.tomu.event.ChannelEvent;
import cn.lefer.tomu.event.detail.AbstractChannelEventDetail;
import cn.lefer.tomu.exception.BasicErrorCode;
import cn.lefer.tomu.exception.BasicRestException;
import cn.lefer.tomu.exception.BizErrorCode;
import cn.lefer.tomu.exception.BizRestException;
import cn.lefer.tomu.service.ChannelService;
import cn.lefer.tomu.utils.TomuUtils;
import cn.lefer.tomu.view.ChannelView;
import cn.lefer.tomu.view.Page;
import cn.lefer.tomu.view.SongView;
import cn.lefer.tools.Net.LeferNet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuples;

import java.io.IOException;
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
    @PostMapping(value = "")
    public ChannelView createChannel() {
        return channelService.createChannel();
    }

    //获取频道信息
    @GetMapping(value = "/{channelID}")
    public ChannelView getChannel(@PathVariable("channelID") @Validated int channelID, ServerWebExchange exchange) {
        if (channelID < 1) throw new BasicRestException(BasicErrorCode.ARGUMENT_VALUE_INVALID);
        ChannelView channelView = channelService.getChannel(channelID, TomuUtils.getToken(exchange));
        if (channelView == null) throw new BizRestException(BizErrorCode.CHANNEL_NOT_EXISTS);
        return channelView;
    }

    //获取频道下的歌单
    @GetMapping(value = "/{channelID}/songs")
    public Page<SongView> getSongs(@PathVariable("channelID") @Validated int channelID,
                                   @RequestParam(defaultValue = "1") @Validated int pageNum,
                                   @RequestParam(defaultValue = "20") @Validated int pageSize) {
        if (pageNum * pageSize <= 0) throw new BasicRestException(BasicErrorCode.ARGUMENT_VALUE_INVALID);
        return channelService.getSongs(channelID, pageNum, pageSize);
    }

    @GetMapping(value = "/{channelID}/songs/all")
    public Flux<SongView> getSongsAsStream(@PathVariable("channelID") @Validated int channelID) {
        return Flux.fromStream(channelService.getSongs(channelID).stream());
    }

    //添加歌曲
    @PostMapping(value = "/{channelID}/song", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public SongView addSong(@PathVariable("channelID") @Validated int channelID,
                            @Validated SongDTO songDTO,
                            ServerWebExchange exchange) throws IOException {
        if(LeferNet.isValid(songDTO.getMp3Url())){
            return channelService.addSong(channelID,
                    TomuUtils.getToken(exchange),
                    songDTO.getSongName(),
                    songDTO.getArtistName(),
                    songDTO.getCoverUrl(),
                    songDTO.getLrcUrl(),
                    songDTO.getMp3Url(),
                    songDTO.getSongDuration(),
                    songDTO.getSongSource(),
                    songDTO.getSongUrl());
        }else{
            throw new BizRestException(BizErrorCode.URL_TEST_FAILED);
        }


    }

    //删除歌曲
    @RequestMapping(value = "/{channelID}/song/{songID}", method = RequestMethod.DELETE)
    public boolean deleteSong(@PathVariable("channelID") @Validated int channelID,
                              @PathVariable("songID") @Validated int songID,
                              ServerWebExchange exchange) {
        return channelService.deleteSong(channelID, songID, TomuUtils.getToken(exchange));
    }

    /*
     * 状态变化：用户切换歌曲
     */
    @PostMapping(value = "/{channelID}/status", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public boolean changeChannelStatus(@PathVariable("channelID") @Validated int channelID,
                                       @Validated ChannelStatusDTO channelStatusDTO,
                                       ServerWebExchange exchange) {
        return channelService.changeChannelStatus(channelID, channelStatusDTO.getSongID(), channelStatusDTO.getPosition(), TomuUtils.getToken(exchange));
    }

    /*
     * 推送事件到前端
     */
    //这里需要考虑内存溢出问题
    @GetMapping(value = "/{channelID}/status",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<ChannelEvent<? extends AbstractChannelEventDetail>>> getStatus(@PathVariable("channelID") @Validated int channelID,
                                                                                               @RequestParam @Validated String clientID) {
        return Flux.interval(Duration.ofMillis(500))
                .filter(seq -> channelService.hasNewsInChannel(channelID, clientID))
                .map(seq -> channelService.getChannelEvent(channelID, clientID, Long.toString(seq)));
    }

    //获取频道下的听众
    @GetMapping(value = "/{channelID}/audience")
    public List<String> getAudience(@PathVariable("channelID") @Validated int channelID) {
        return channelService.getAudienceWithNickName(channelID);
    }

    //听众从频道中退出
    @RequestMapping(value = "/{channelID}/audience", method = RequestMethod.DELETE)
    public boolean audienceExitFromChannel(@PathVariable("channelID") @Validated int channelID, ServerWebExchange exchange) {
        return channelService.exit(channelID, TomuUtils.getToken(exchange));
    }

    @Autowired
    public void setChannelService(ChannelService channelService) {
        this.channelService = channelService;
    }

}
