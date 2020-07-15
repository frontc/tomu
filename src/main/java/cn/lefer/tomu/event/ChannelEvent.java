package cn.lefer.tomu.event;


/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/7/7
 * @Description : 频道事件类
 */
public class ChannelEvent<T> {
    private ChannelEventType type;
    private T detail;

    private ChannelEvent(Builder<T> builder) {
        this.type = builder.type;
        this.detail = builder.detail;
    }

    public ChannelEventType getType() {
        return type;
    }

    public void setType(ChannelEventType type) {
        this.type = type;
    }

    public T getDetail() {
        return detail;
    }

    public void setDetail(T detail) {
        this.detail = detail;
    }

    public static class Builder<T> {
        private ChannelEventType type;
        private T detail;

        public Builder<T> withType(ChannelEventType type) {
            this.type = type;
            return this;
        }

        public Builder<T> withDetail(T detail) {
            this.detail = detail;
            return this;
        }

        public ChannelEvent<T> build() {
            return new ChannelEvent<T>(this);
        }
    }

    @Override
    public String toString() {
        return "ChannelEvent{" +
                "type=" + type +
                ", detail=" + detail +
                '}';
    }
}
