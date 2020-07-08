package cn.lefer.tomu.controller;

import cn.lefer.tomu.cache.OnlineStatus;
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
import cn.lefer.tomu.view.PlayStatusView;
import cn.lefer.tomu.view.SongView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
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
@RequestMapping(value = "/api/v1/channel")
public class ChannelController {

    ChannelService channelService;
    OnlineStatus onlineStatus;

    //创建频道
    @PostMapping(value = "")
    public ChannelView createChannel() {
        return channelService.createChannel();
    }

    //获取频道信息
    @GetMapping(value = "/{channelID}")
    public ChannelView getChannel(@PathVariable("channelID") @Validated int channelID) {
        if (channelID == -1) throw new BizRestException(BizErrorCode.CHANNEL_IS_FULL);
        ChannelView channelView = channelService.getChannel(channelID);
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
                            ServerWebExchange exchange) {
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
    }

    //删除歌曲
    @RequestMapping(value = "/{channelID}/song/{songID}", method = RequestMethod.DELETE)
    public boolean deleteSong(@PathVariable("channelID") @Validated int channelID,
                              @PathVariable("songID") @Validated int songID) {
        return channelService.deleteSong(channelID, songID);
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

    //todo:将添加歌曲，删除歌曲，其他用户进入，其他用户退出，播放状态变化统一推送到前台。后台缓存改用HashMap<User,Queue<Event>>的方式
    //TODO:通过队列深度去判断有无可推送，如果用户切换频道原本队列销毁。实际持久化交给disruptor的消费者去做
    @GetMapping(value = "/{channelID}/status")
    public Flux<ServerSentEvent<ChannelEvent<? extends AbstractChannelEventDetail>>> getStatus(@PathVariable("channelID") @Validated int channelID,
                                                                                               @RequestParam @Validated String clientID) {
        return Flux.interval(Duration.ofSeconds(1))
                .filter(l -> channelService.hasNewsInChannel(channelID,clientID))
                .map(seq -> Tuples.of(seq, ThreadLocalRandom.current().nextInt()))
                .map(data -> channelService.getChannelEvent(channelID,clientID,Long.toString(data.getT1())));
    }

    //获取频道下的听众
    @GetMapping(value = "/{channelID}/audience")
    public List<String> getAudience(@PathVariable("channelID") @Validated int channelID) {
        return onlineStatus.getAudienceWithNickName(channelID);
    }

    //听众从频道中退出
    @RequestMapping(value = "/{channelID}/audience", method = RequestMethod.DELETE)
    public boolean audienceExitFromChannel(@PathVariable("channelID") @Validated int channelID, ServerWebExchange exchange) {
        return onlineStatus.exit(TomuUtils.getToken(exchange), channelID);
    }

    @Autowired
    public void setChannelService(ChannelService channelService) {
        this.channelService = channelService;
    }

    @Autowired
    public void setOnlineStatus(OnlineStatus onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
}
